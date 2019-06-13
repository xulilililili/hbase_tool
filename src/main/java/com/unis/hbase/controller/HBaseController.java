package com.unis.hbase.controller;

import com.unis.hbase.controller.dto.UseByConditions;
import com.unis.hbase.service.HBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xuli
 * @date 2019/5/18
 */
@RestController
@RequestMapping(value = "/v1")
public class HBaseController {
    private static final Logger logger = LoggerFactory.getLogger(HBaseController.class);

    @Autowired
    private HBaseService hBaseService;

    @PostMapping(value = {"/make-data"})
    public Boolean makeData(
            @Validated({UseByConditions.MakeDataGroup.class}) @RequestBody UseByConditions useByConditions,
            BindingResult bindingResult) {
        bindingResultCheck(bindingResult);
        return hBaseService.makeData(useByConditions);
    }

    @GetMapping(value = {"/search-rowkeys/"})
    public Boolean getRowKeys(
           @RequestParam(value="row_keys") String rowKeys) {
        return hBaseService.getRowKeys(rowKeys);
    }

    private void bindingResultCheck(BindingResult bindingResult) {
        //捕获不合法参数
        if (bindingResult.hasErrors()) {
            // 得到全部不合法的对象
            List<ObjectError> objectErrorList = bindingResult.getAllErrors();
            for (ObjectError objectError : objectErrorList) {
                logger.error("error field is:{} ,message is : {}", ((FieldError) objectError).getField(), objectError.getDefaultMessage());
            }
        }
    }


}
