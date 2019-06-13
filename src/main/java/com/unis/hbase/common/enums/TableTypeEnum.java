package com.unis.hbase.common.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * @author xuli
 * @date 2019/3/26
 */
public enum TableTypeEnum {
    /**
     * 动态人脸
     */
    FaceSnap("facesnap", "viid_facesnap", "viid_facesnap:facesnapstructured"
    ,"recordid,faceid,deviceid,tollgateid,lefttopx,lefttopy,rightbtmx,rightbtmy,imageurlpart,imageurlfull,passtime,gendercode,ageuplimit,agelowerlimit,appearlevel,ethiccode,accompanynumber,skincolor,hairstyle,haircolor,facestyle,facialfeature,physicalfeature,isrespirator,respiratorcolor,iscap,capstyle,capcolor,isglasses,glassesstyletype,glasscolor,isdriver,eyebrowstyle,isbeard,nosestyle,mustachestyle,lipstyle,wrinklepouch,acnestain,frecklebirthmark,scardimple,otherfeature,issmile,isopeneyes,isopenmouth,imagereliability,isiadisposed,extendid"),
    /**
     * 人员
     */
    Person("person", "viid_person", "viid_person:personstructured"
    ,"recordid,personid,deviceid,snaptimestamp,lefttopx,lefttopy,rightbtmx,rightbtmy,personappeartime,persondisappeartime,gendercode,ageuplimit,agelowerlimit,ethiccode,accompanynumber,skincolor,hairstyle,haircolor,facestyle,facialfeature,physicalfeature,umbrellacolor,respiratorcolor,capstyle,capcolor,glassstyle,glasscolor,scarfcolor,bagstyle,bagcolor,coatstyle,coatcolor,trousersstyle,trouserscolor,shoesstyle,shoescolor,heightuplimit,heightlowerlimit,bodytype,gesture,status,bodyfeature,habitualmovement,behavior,behaviordescription,appendant,appendantdescription,coatlength,trouserslen,isdriver,vehicleclass,isforeigner,passporttype,immigranttypecode,bodyspecialmark,imagereliability,imageurl,imageurlpart,storagetime,extendid,hairlen,coatgrain,isholdbaby,iscarrybaby,ishandcart,iscarrythings,isumbrellaopen,isrespirator,isglasses,iscap,orientation,bagsstyle,bagscolor"),
    /**
     * 车辆
     */
    Vehicle("vehicle", "viid_vehicle", "viid_vehicle:vehiclestructured"
    ,"recordid,motorvehicleid,tollgateid,deviceid,storageurlcloseshot,storageurlplate,storageurldistantshot,storageurlcompound,storageurlbreviary,laneno,hasplate,plateclass,platecolor,plateno,speed,direction,drivingstatuscode,vehiclelefttopx,vehiclelefttopy,vehiclerightbtmx,vehiclerightbtmy,vehicleclass,vehiclebrand,vehiclemodel,vehiclestyles,vehiclecolor,vehiclecolordepth,passtime,vehicleappeartime,vehicledisappeartime,platereliability,platecharreliability,brandreliability,driverface,vicedriverface,sunvisor,safetybelt,calling,vehicleattitude,isperfumebottle,isornament,istissue,isinspectionmark,issparewheel,ismuckcar,ishazardoustanker,issunroof,vehiclefrontitem,descoffrontitem,hitmarkinfo,rearviewmirror,platenoattach,vehiclewindow,isluggagerack,cosunvisor,cosafetybelt,isyellowlabel"),
    /**
     * 人脸归档
     */
    FaceArchive("face_archive", "viid_facestatic", "viid_facestatic:face_archive"
    ,"face_id,people_id,type,tollgate_id,device_id,image_url,face_url,timestamp,cur_date"),
    /**
     * Wifi:终端功能
     */
    TerminalFeature("terminal_feature", "viid_iot", "viid_iot:terminal_feature"
    ,"record_id,mac,brand,cache_ssid,capture_time,terminal_field_strength,identification_type,certificate_code,ssid_position,access_ap_mac,access_ap_channel,access_ap_encryption_type,x_coordinate,y_coordinate,netbar_wacode,collection_equipment_id,collection_equipment_longitude,collection_equipment_latitude");

    private String type;
    private String nameSpace;
    private String tableName;
    private String columns;

    TableTypeEnum(String type, String nameSpace, String tableName,String columns) {
        this.type = type;
        this.nameSpace = nameSpace;
        this.tableName = tableName;
        this.columns = columns;
    }

    public String getType() {
        return type;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumns() {
        return columns;
    }

    /**
     * 根据type来获取表名
     *
     * @param type 类型
     * @return String
     */
    public static String getTableNameByType(String type) {
        for (TableTypeEnum tableTypeEnum : TableTypeEnum.values()) {
            if (type.equals(tableTypeEnum.getType())) {
                return tableTypeEnum.getTableName();
            }
        }
        return null;
    }

    /**
     * 根据type来获取字段
     * @param type 类型
     * @return 字段数组
     */
    public static String[] getColumnsByType(String type){
        for (TableTypeEnum tableTypeEnum : TableTypeEnum.values()) {
            if (type.equals(tableTypeEnum.getType())) {
               return tableTypeEnum.getColumns().split(",");
            }
        }
        return null;
    }

}
