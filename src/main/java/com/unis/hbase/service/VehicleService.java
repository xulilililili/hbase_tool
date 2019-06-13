package com.unis.hbase.service;

/**
 * @author xuli
 * @date 2019/4/17
 */
public interface VehicleService {
    /**
     * 生成车辆数据
     *
     * @param passTime       时间戳
     * @param recordID       主键
     * @return 数据
     */
    String makeVehicleData(long passTime, long recordID);

}
