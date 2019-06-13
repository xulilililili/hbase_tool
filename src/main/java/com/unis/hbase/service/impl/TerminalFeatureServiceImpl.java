package com.unis.hbase.service.impl;

import com.unis.hbase.common.utils.DateUtils;
import com.unis.hbase.common.utils.RandomUtils;
import com.unis.hbase.model.TerminalFeature;
import com.unis.hbase.service.TerminalFeatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author xuli
 * @date 2019/5/9
 */
@Service
public class TerminalFeatureServiceImpl implements TerminalFeatureService {
    private static final Logger logger = LoggerFactory.getLogger(TerminalFeatureServiceImpl.class);

    private TerminalFeature terminalFeature = new TerminalFeature();
    private Random random = new Random();
    /**
     * 身份内容certificateCode
     */
    private static String[] CERTIFICATE_CODE = new String[]{"1234ds", "xxccfdeww", "xww3ed", "737dhsyegd6y3", "ddr3edrf4fd",
            "334455fdctvt", "xe3fdrfdf3", "xedf43dwqf54f", "xed32ded", "xe33434f5g", "we332dsdfr"};

    @Override
    public String makeTerminalFeatureData(long passTime, long recordID) {
        terminalFeature.setRecordID(recordID);
        terminalFeature.setMac(RandomUtils.getRandomMac());
        terminalFeature.setBrand("unis");
        terminalFeature.setCacheSsid("unis|huawei");
        terminalFeature.setCaptureTime(passTime / 1000);
        terminalFeature.setTerminalFieldStrength("-23");
        terminalFeature.setIdentificationType(1);
        terminalFeature.setCertificateCode(CERTIFICATE_CODE[random.nextInt(CERTIFICATE_CODE.length)]);
        terminalFeature.setSsidPosition("unis");
        terminalFeature.setAccessApMac(RandomUtils.getRandomMac());
        terminalFeature.setAccessApChannel("xx");
        terminalFeature.setAccessApEncryptionType("2");
        terminalFeature.setxCoordinate("");
        terminalFeature.setyCoordinate("");
        terminalFeature.setNetbarWacode("1");
        terminalFeature.setCollectionEquipmentID("000000");
        terminalFeature.setCollectionEquipmentLongitude(Double.toString(random.nextInt(9000000) / 100000.0));
        terminalFeature.setCollectionEquipmentLatitude(Double.toString(random.nextInt(18000000) / 100000.0));

        return  terminalFeature.toString();
    }
}
