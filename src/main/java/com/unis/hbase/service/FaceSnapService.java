package com.unis.hbase.service;

/**
 * @author xuli
 * @date 2019/4/18
 */
public interface FaceSnapService {

    /**
     * 生成抓拍人脸数据
     * @param passTime 时间戳
     * @param recordID 主键
     * @return 数据
     */
    String makeFaceSnapData(long passTime, long recordID);
}
