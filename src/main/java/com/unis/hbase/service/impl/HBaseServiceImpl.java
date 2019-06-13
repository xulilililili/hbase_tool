package com.unis.hbase.service.impl;

import com.unis.hbase.common.enums.TableTypeEnum;
import com.unis.hbase.common.utils.*;
import com.unis.hbase.controller.dto.UseByConditions;
import com.unis.hbase.service.HBaseService;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author xuli
 * @date 2019/5/21
 */
@Service
public class HBaseServiceImpl implements HBaseService {

    private static final Logger logger = LoggerFactory.getLogger(HBaseServiceImpl.class);
    /**
     * 抽样数目
     */
    @Value("${generate.base-count}")
    private int baseCount;

    @Autowired
    private HBaseUtils hBaseUtils;

    @Autowired
    private ThreadUtils threadUtils;

    @Override
    public Boolean makeData(UseByConditions useByConditions) {
        String algorithm = useByConditions.getAlgorithm();
        String date = useByConditions.getStartDate();
        String type = useByConditions.getType();
        String[] columnFamily = useByConditions.getColumnFamily().split(",");
        int days = useByConditions.getDays();
        int prepareRegionCount = useByConditions.getPrepareRegionCount();
        int loop = useByConditions.getLoop();
        int batchSize = useByConditions.getBatchSize();
        int threadNum = useByConditions.getThreadNum();
        String baseTableName = TableTypeEnum.getTableNameByType(type);
        //根据天数循环
        for (int j = 0; j < days; j++) {
            String tableName = String.format("%s_%s_%s", baseTableName, algorithm, date);
            if (type.equals(TableTypeEnum.TerminalFeature.getType())) {
                tableName = String.format("%s_%s", baseTableName, date);
            }
            //指定splitKeys，创建表
            byte[][] splitKeys = SplitUtils.getHashSplitKeys(baseCount, prepareRegionCount, date);
            hBaseUtils.createTable(tableName, columnFamily, splitKeys);
            Long begin = System.currentTimeMillis();
            //使用线程池插入数据
            for (int i = 0; i < threadNum; i++) {
                PutDataService task = new PutDataService(tableName, columnFamily, date, type, loop, batchSize);
                threadUtils.submit(task);
            }
            if (threadUtils.waitTask(threadNum)) {
                double cost = (System.currentTimeMillis() - begin) / 1000.0;
                int count = loop * batchSize * threadNum;
                logger.info("[ INSERT DATA ] :{} finished with {}s and insert {} pieces of data ", tableName, cost, count);
            } else {
                return false;
            }
        }
        return true;
    }


    @Override
    public Boolean getRowKeys(String rowKeys) {
        return null;
    }


}
