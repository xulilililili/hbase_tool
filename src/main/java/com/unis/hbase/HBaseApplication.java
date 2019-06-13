package com.unis.hbase;

import com.unis.hbase.common.utils.HBaseUtils;
import com.unis.hbase.dao.impl.HBaseMapperImpl;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuli
 * @date 2019/5/18
 */
@SpringBootApplication
public class HBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(HBaseApplication.class, args);
    }

}
