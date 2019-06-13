package com.unis.hbase.common.utils;

import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author xuli
 * @date 2019/5/30
 */
public class SplitUtils {

    private static final Logger logger = LoggerFactory.getLogger(SplitUtils.class);

    /**
     * 随机抽样得到SplitKeys
     *
     * @param prepareRegionCount 预分区数量
     * @param date               表的日期(影响分区的临界值)
     * @return SplitKeys
     */
    public static byte[][] getHashSplitKeys(int baseCount, int prepareRegionCount, String date) {
        Long begin = System.currentTimeMillis();
        //由取样数目及region数相除所得的数量
        int splitKeysBase = baseCount / prepareRegionCount;
        //splitKeys个数
        int splitKeysNumber = prepareRegionCount - 1;
        //由抽样计算出来的splitKeys结果
        byte[][] splitKeys = new byte[splitKeysNumber][];
        //使用treeSet保存抽样数据，升序
        TreeSet<byte[]> rows = new TreeSet<>(Bytes.BYTES_COMPARATOR);
        for (int i = 0; i < baseCount; i++) {
            long passTime = RandomUtils.getRandomPassTime(date, DateUtils.getDateByAdd(date, 1));
            long recordID = RandomUtils.getRandomRecordID(passTime);
            rows.add(Bytes.toBytes(CastUtils.castString(recordID)));
        }
        int pointer = 0;
        Iterator<byte[]> rowKeyIter = rows.iterator();
        int index = 0;
        //抽样取到每个边界值
        while (rowKeyIter.hasNext()) {
            byte[] tempRow = rowKeyIter.next();
            rowKeyIter.remove();
            if ((pointer != 0) && (pointer % splitKeysBase == 0) && (index < splitKeysNumber)) {
                splitKeys[index++] = tempRow;
            }
            pointer++;
        }
        double cost = (System.currentTimeMillis() - begin) / 1000.0;
        logger.info("[ SPLIT KEYS ] : finished with {}s and the sample size is {}", baseCount, cost);
        rows.clear();
        return splitKeys;
    }

    /**
     * 根据自定义keys，生成有序的SplitKeys
     *
     * @param keys 自定义keys
     * @return SplitKeys
     */
    public static byte[][] getSplitKeys(String[] keys) {
        if (keys.length == 0) {
            logger.error("keys's length less than 0");
            return null;
        } else {
            byte[][] splitKeys = new byte[keys.length][];
            //升序
            TreeSet<byte[]> rows = new TreeSet<>(Bytes.BYTES_COMPARATOR);
            for (String key : keys) {
                rows.add(Bytes.toBytes(key));
            }
            Iterator<byte[]> rowKeyIter = rows.iterator();
            int i = 0;
            while (rowKeyIter.hasNext()) {
                byte[] tempRow = rowKeyIter.next();
                rowKeyIter.remove();
                splitKeys[i++] = tempRow;
            }
            return splitKeys;
        }
    }
}
