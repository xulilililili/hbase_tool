package com.unis.hbase.common.utils;

/**
 * 封装工具类
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.regionserver.BloomType;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xuli
 * @date 2019/4/16
 */
@Component
public class HBaseUtils {
    private static final Logger logger = LoggerFactory.getLogger(HBaseUtils.class);
    /**
     * 用HBaseConfiguration初始化配置信息时会自动加载当前应用classpath下的hbase-site.xml
     */
    private static Configuration conf = HBaseConfiguration.create();
    private static ExecutorService pool = Executors.newScheduledThreadPool(20);
    private static Connection connection = null;

    private static Admin admin = null;

    /**
     * 构造函数
     */
    public HBaseUtils() {
        System.setProperty("hadoop.home.dir", "C:\\xl\\hadoop-2.7.6");
        if (connection == null) {
            try {
                connection = ConnectionFactory.createConnection(conf, pool);
                admin = connection.getAdmin();
            } catch (IOException e) {
                logger.error("HBaseUtils实例初始化失败！错误信息为：" + e.getMessage(), e);
            }
        }
    }

    /**
     * 创建命名空间
     *
     * @param nameSpace 命名空间
     * @throws IOException 异常
     */
    public void createNamespace(String nameSpace) throws IOException {
        try {
            admin.getNamespaceDescriptor(nameSpace);
        } catch (NamespaceNotFoundException e) {
            //若发生特定的异常，即找不到命名空间，则创建命名空间
            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(nameSpace).build();
            admin.createNamespace(namespaceDescriptor);
        }
    }

    /**
     * 删除表
     *
     * @param tableName 表名
     */
    public void dropTable(String tableName) {
        TableName name = TableName.valueOf(tableName);
        try {
            if (admin.tableExists(name)) {
                admin.disableTable(name);
                admin.deleteTable(name);
                logger.info("table {} was deleted successfully", name);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 创建表 无splitKeys
     *
     * @param tableName    表名
     * @param columnFamily 列族（数组）
     */
    public void createTable(String tableName, String[] columnFamily) {
        createTable(tableName, columnFamily, null);
    }

    /**
     * @param tableName    表名
     * @param columnFamily 列族（数组）
     * @param splitKeys    splitKeys
     */
    public void createTable(String tableName, String[] columnFamily, byte[][] splitKeys) {
        TableName name = TableName.valueOf(tableName);
        try {
            if (admin.tableExists(TableName.valueOf(tableName))) {
                logger.info("create table exists! then drop!", name);
                dropTable(tableName);
            }
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            for (String cf : columnFamily) {
                HColumnDescriptor descriptor = new HColumnDescriptor(cf);
                //数据最大版本
                descriptor.setMaxVersions(1);
                descriptor.setBloomFilterType(BloomType.ROW);
                descriptor.setInMemory(true);
                tableDescriptor.addFamily(descriptor);
            }
            /**
             * create()方法有两个静态方法,无参和有参数
             * 无参数:hadoop的core-site.xml配置的参数会被后面的覆盖
             * 有参数:hadoop的core-site.xml配置的参数无法修改
             */
            if (splitKeys != null) {
                admin.createTable(tableDescriptor, splitKeys);
            } else {
                admin.createTable(tableDescriptor);
            }
            logger.info("create table {} success", tableName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 批量导入，table.put(List<Put>);
     *
     * @param tableName    表名
     * @param columnFamily 列簇 这个方法 默认每个列簇大小一样
     * @param columns      列名
     * @param valueList    值
     * @return 插入条数
     */
    public int insert(String tableName, String[] columnFamily, String[] columns, List<String> valueList) {
        List<Put> putList = new ArrayList<>();
        Put put;
        try {
            TableName name = TableName.valueOf(tableName);
            Table table = connection.getTable(name);
            for (String values : valueList) {
                String[] valueArr = values.split(",");
                put = new Put(Bytes.toBytes(valueArr[0]));
                for (int index = 1; index < valueArr.length; index++) {
                    for (String cf : columnFamily) {
                        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(columns[index]), Bytes.toBytes(valueArr[index]));
                    }
                }
                putList.add(put);
            }
            table.put(putList);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return putList.size();
    }

    /**
     * 插入记录（单行单列族-多列多值）
     *
     * @param tableName    表名
     * @param rowKey       行名
     * @param columnFamily 列族名
     * @param columns      列名（数组）
     * @param values       值（数组）（且需要和列一一对应）
     */
    public void insertRecords(String tableName, String rowKey, String columnFamily, String[] columns, String[] values) {
        try {
            TableName name = TableName.valueOf(tableName);
            Table table = connection.getTable(name);
            Put put = new Put(Bytes.toBytes(rowKey));
            for (int i = 0; i < columns.length; i++) {
                put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columns[i]), Bytes.toBytes(values[i]));
                table.put(put);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 插入记录（单行单列族-单列单值）
     *
     * @param tableName    表名
     * @param rowKey       行名
     * @param columnFamily 列族名
     * @param column       列名
     * @param value        值
     */
    public void insertOneRecord(String tableName, String rowKey, String columnFamily, String column, String value) {
        try {
            TableName name = TableName.valueOf(tableName);
            Table table = connection.getTable(name);
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
            table.put(put);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 删除一行记录
     *
     * @param tableName 表名
     * @param rowKey    行名
     */
    public void deleteRow(String tableName, String rowKey) {
        try {
            TableName name = TableName.valueOf(tableName);
            Table table = connection.getTable(name);
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 删除单行单列族记录
     *
     * @param tableName    表名
     * @param rowKey       行名
     * @param columnFamily 列族名
     */
    public void deleteColumnFamily(String tableName, String rowKey, String columnFamily) {
        try {
            TableName name = TableName.valueOf(tableName);
            Table table = connection.getTable(name);
            Delete delete = new Delete(Bytes.toBytes(rowKey)).addFamily(Bytes.toBytes(columnFamily));
            table.delete(delete);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 删除单行单列族单列记录
     *
     * @param tableName    表名
     * @param rowKey       行名
     * @param columnFamily 列族名
     * @param column       列名
     */
    public void deleteColumn(String tableName, String rowKey, String columnFamily, String column) {
        try {
            TableName name = TableName.valueOf(tableName);
            Table table = connection.getTable(name);
            Delete delete = new Delete(Bytes.toBytes(rowKey)).addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
            table.delete(delete);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static List<String> readLineForFile(String filePath, int lineNumber) {
        try {
            //使用缓冲区将数据读入到缓冲区中
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(filePath)));
            String line = reader.readLine();
            if (lineNumber <= 0) {
                logger.error("lines out of range");
            }
            int num = 0;
            while (line != null) {
                if (lineNumber == (++num)) {
                    String[] rowKeyArr = line.split(",");
                    List<String> rowKeyList = Arrays.asList(rowKeyArr);
                    reader.close();
                    return rowKeyList;
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


}
