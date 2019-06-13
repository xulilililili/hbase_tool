package com.unis.hbase.service;

/**
 * @author xuli
 * @date 2019/4/22
 */
public interface PersonService {
    /**
     * 生成人体数据
     *
     * @param passTime       时间戳
     * @param recordID       主键
     * @return 数据
     */
    String makePersonData(long passTime, long recordID);
}
