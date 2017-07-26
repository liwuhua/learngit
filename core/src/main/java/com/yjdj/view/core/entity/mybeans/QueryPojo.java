package com.yjdj.view.core.entity.mybeans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangkai on 16/10/27.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class QueryPojo {

    private List<String> productModel;     //产品型号
    private String yearInput;   //用户输入年份
    
    private boolean isExport=false;     //是否是导出功能 (默认不是导出)
    private int startitem = 0;       //分页起始条件
    private int pageitem = 20;     //分页截取条件

    private List<String> xxbh;    //信息编号XXBH
    private List<String> psjd;    //配属局段PSJD
    private List<String> cpsmjd;  //产品寿命阶段CPSMJD
    private List<String> jxdd;    //检修地点JXDD
    private String startTime;           //发现时间上行
    private String endTime;             //发现时间下行
    private String startYearMonth;     //起始月份
    private String endYearMonth;       //结束月份
    private List<String> cpxh;    //产品型号CPXH
    private List<String> cpbh;    //产品编号CPBH
    private List<String> jcbh;    //机车编号JCBH
    private List<String> gzbw;    //故障部位GZBW
    private List<String> sglb;    //事故类别SGLB
    private List<String> yzd;     //严重度YZD
    private List<String> jnw;     //境内/境外JNW
    private List<String> xzjx;    //新造/检修XZJX
    private List<String> lifnr;   //供应商LIFNR
    private List<String> lbbh;    //质量损失类别编号LBBH
    private List<String> bgbh;    //报告编号BGBH
    private List<String> gzlx;    //故障类型GZLX


    //以下为FS47用
    private String train_type;  //车型 单值

    private List<String> trainmodel;  //车型 多值

    private List<String> train_num; //车号 多值

    private List<String> manufacturer;//造修厂家

    private List<String> bureau_subjection;//隶属路局

    private List<String> place_subjection;//配属段所

    private List<String> repair_level;//车修程类别

    private List<String> motor_serial_num;//产品编号

}
