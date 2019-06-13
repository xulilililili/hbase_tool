package com.unis.hbase.service.impl;


import com.unis.hbase.common.enums.TableTypeEnum;
import com.unis.hbase.common.utils.DateUtils;
import com.unis.hbase.common.utils.HBaseUtils;
import com.unis.hbase.common.utils.RandomUtils;
import com.unis.hbase.service.*;
import com.unis.hbase.service.impl.HBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author xuli
 * @date 2019/3/18
 */

public class PutDataService implements Callable {

    private VehicleService vehicleService = new VehicleServiceImpl();

    private FaceSnapService faceSnapService = new FaceSnapServiceImpl();

    private PersonService personService = new PersonServiceImpl();

    private TerminalFeatureService terminalFeatureService = new TerminalFeatureServiceImpl();

    private HBaseUtils hBaseUtils=new HBaseUtils();

    /**
     * 表名，带上命名空间
     */
    private String tableName;
    /**
     * 列簇，可以是多列簇
     */
    private String[] columnFamily;
    /**
     * 日期
     */
    private String date;
    /**
     * 类型
     */
    private String type;
    /**
     * 循环次数
     */
    private int loop;
    /**
     * 批次量
     */
    private int batchSize;


    public PutDataService(String tableName, String[] columnFamily, String date, String type, int loop, int batchSize) {
        this.tableName = tableName;
        this.columnFamily = columnFamily;
        this.date = date;
        this.type = type;
        this.loop = loop;
        this.batchSize = batchSize;
    }

    @Override
    public Boolean call() {
        List<String> dataList = new ArrayList<>(batchSize);
        String[] columns= TableTypeEnum.getColumnsByType(type);
        long passTime;
        for (int i = 0; i < loop; i++) {
            for (int j = 0; j < batchSize; j++) {
                passTime = RandomUtils.getRandomPassTime(date, DateUtils.getDateByAdd(date, 1));
                long recordID = RandomUtils.getRandomRecordID(passTime);
                switch (type) {
                    case "vehicle":
                        dataList.add(vehicleService.makeVehicleData(passTime, recordID));
                        break;
                    case "facesnap":
                        dataList.add(faceSnapService.makeFaceSnapData(passTime, recordID));
                        break;
                    case "person":
                        dataList.add(personService.makePersonData(passTime, recordID));
                        break;
                    case "terminal_feature":
                        dataList.add(terminalFeatureService.makeTerminalFeatureData(passTime, recordID));
                        break;
                    default:
                        break;
                }
            }
            hBaseUtils.insert(tableName,columnFamily,columns,dataList);
            dataList.clear();
        }
        return true;
    }
}
