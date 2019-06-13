package com.unis.hbase.service;

/**
 * @author xuli
 * @date 2019/5/9
 */
public interface TerminalFeatureService {
    /**
     * 生成Wifi终端数据
     * @param passTime 时间戳
     * @param recordID 主键
     * @return 数据
     */
    String makeTerminalFeatureData(long passTime, long recordID);
}
