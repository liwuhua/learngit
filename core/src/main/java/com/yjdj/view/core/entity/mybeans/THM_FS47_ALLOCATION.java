package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

/**
 * Created by zhuxuan on 2017/5/23.
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS47_ALLOCATION extends MybatisBase {
    private String train_type;//车型
    private String train_num;//车号
    private String manufacturer;//造修厂家
    private String bureau_subjection;//隶属路局
    private String place_subjection;//配属段、所
    private String repair_date;//车造、修日期
    private String repair_level;//车修程类别
    private String distance_reach_repairlvl;//检修时走行里程
    private String map_category;//配属类别
    private String map_position;//配属位置
    private String motor_serial_num;//电机编号
    private String remarks;//备注
    private String report_name;//信息提报人
    private String report_time;//信息提报日期

}
