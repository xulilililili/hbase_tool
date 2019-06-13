package com.unis.hbase.service;

import com.unis.hbase.controller.dto.UseByConditions;

/**
 * @author xuli
 * @date 2019/5/18
 */
public interface HBaseService {

    /**
     * 造数据
     * @param useByConditions 条件
     * @return T or F
     */
    Boolean makeData(UseByConditions useByConditions);

    /**
     * 根据RowKeys来查询
     * @param rowKeys 行键
     * @return T of F
     */
    Boolean getRowKeys(String rowKeys);

}
