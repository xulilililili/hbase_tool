package com.unis.hbase.service.impl;


import com.unis.hbase.common.enums.VehicleEnum;
import com.unis.hbase.common.utils.RandomUtils;
import com.unis.hbase.model.VehicleStructured;
import com.unis.hbase.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author xuli
 * @date 2019/4/17
 */
@Service
public class VehicleServiceImpl implements VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private VehicleStructured vehicle = new VehicleStructured();

    private Random random = new Random();

    @Override
    public String makeVehicleData(long passTime, long recordID) {
        vehicle.setRecordID(recordID);
        vehicle.setMotorVehicleID(Integer.toString(random.nextInt(100000000)));
        vehicle.setTollgateID(VehicleEnum.TOLLGATE_PREFIX + VehicleEnum.DEVICE_SUFFIX[random.nextInt(VehicleEnum.DEVICE_SUFFIX.length)]);
        vehicle.setDeviceID(VehicleEnum.DEVICE_PREFIX + VehicleEnum.DEVICE_SUFFIX[random.nextInt(VehicleEnum.DEVICE_SUFFIX.length)]);
        vehicle.setStorageUrlCloseShot("/picture/2018-08-17T08-29-20Z/1534495880605_3865.jpg");
        vehicle.setStorageUrlPlate("1");
        vehicle.setStorageUrlDistantShot("1");
        vehicle.setStorageUrlCompound("1");
        vehicle.setStorageUrlBreviary("1");
        vehicle.setLaneNo(random.nextInt(8) + 1);
        vehicle.setHasPlate(random.nextInt(2));
        vehicle.setPlateClass(VehicleEnum.PLATE_CLASS[random.nextInt(VehicleEnum.PLATE_CLASS.length)]);
        vehicle.setPlateColor(VehicleEnum.PLATE_COLOR[random.nextInt(VehicleEnum.PLATE_COLOR.length)]);
        vehicle.setPlateNo(RandomUtils.getRandomPlateNo());
        vehicle.setSpeed(0.0000);
        vehicle.setDirection(random.nextInt(9) + 1);
        vehicle.setDrivingStatusCode("1");
        vehicle.setVehicleLeftTopX(0.0000);
        vehicle.setVehicleLeftTopY(0.0000);
        vehicle.setVehicleRightBtmX(0.0000);
        vehicle.setVehicleRightBtmY(0.0000);
        vehicle.setVehicleClass(VehicleEnum.VEHICLE_CLASS[random.nextInt(VehicleEnum.VEHICLE_CLASS.length)]);
        vehicle.setVehicleBrand(VehicleEnum.VEHICLE_BRAND[random.nextInt(VehicleEnum.VEHICLE_BRAND.length)]);
        vehicle.setVehicleModel("1");
        vehicle.setVehicleStyles("1");
        vehicle.setVehicleColor(VehicleEnum.VEHICLE_COLOR[random.nextInt(VehicleEnum.VEHICLE_COLOR.length)]);
        vehicle.setVehicleColorDepth(random.nextInt(2));
        vehicle.setPassTime(passTime);
        vehicle.setVehicleAppearTime(0L);
        vehicle.setVehicleDisappearTime(0L);
        vehicle.setPlateReliability(0);
        vehicle.setPlateCharReliability(0);
        vehicle.setBrandReliability(0);
        vehicle.setDriverFace("1");
        vehicle.setViceDriverFace("1");
        vehicle.setSunVisor(random.nextInt(2));
        vehicle.setSafetyBelt(random.nextInt(2));
        vehicle.setCalling(random.nextInt(2));
        vehicle.setVehicleAttitude(random.nextInt(3));
        vehicle.setIsPerfumeBottle(random.nextInt(2));
        vehicle.setIsOrnament(random.nextInt(2));
        vehicle.setIsTissue(random.nextInt(2));
        vehicle.setIsInspectionMark(random.nextInt(2));
        vehicle.setIsSpareWheel(random.nextInt(2));
        vehicle.setIsMuckCar(random.nextInt(2));
        vehicle.setIsHazardousTanker(random.nextInt(2));
        vehicle.setIsSunroof(random.nextInt(2));
        vehicle.setVehicleFrontItem("" + VehicleEnum.VEHICLE_FRONT_ITEM[random.nextInt(VehicleEnum.VEHICLE_FRONT_ITEM.length)]);
        vehicle.setDescOfFrontItem("");
        vehicle.setHitMarkInfo(random.nextInt(3));
        vehicle.setRearviewMirror("");
        vehicle.setPlateNoAttach("");
        vehicle.setVehicleWindow("");
        vehicle.setIsLuggageRack(random.nextInt(2));
        vehicle.setCoSunvisor(random.nextInt(2));
        vehicle.setCoSafetyBelt(random.nextInt(2));
        vehicle.setIsYellowLabel(random.nextInt(2));

        return vehicle.toString();
    }

}
