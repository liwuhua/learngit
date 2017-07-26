package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zcyj on 2017/3/3.
 */

//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@Setter
@Getter
public class THM_FS57 {

    private String vkorg;//销售组织
    private String iwerk;//工厂
    private String parnr;//客户
    private String txtmd;//客户名称
    private String ddtext;//修理级别描述
    private String matnr;//物料编码
    private String maktx;//物料描述
    private double wtg;//成本
    private double qczcts;//期初在产台数
    private double qczccb;//期初在产成本
    private double qjzcts;//期间在产台数
    private double qjzccb;//期间在产成本
    private double qjjcts;//期间进厂台数
    private double qjccts;//期间出厂台数
    private double qjcccb;//期间出厂成本
    private double qjxsts;//期间销售台数
    private double qjxscb;//期间销售成本
    private double qjxsje;//期间销售金额
    private double yfhwkpts;//已发货未开票台数
    private double yfhwkpcb;//已发货未开票成本
    private String date;//年月


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("THM_FS57 : [vkorg='");
        sb.append(vkorg);
        sb.append("',iwerk='");
        sb.append(iwerk);
        sb.append("',parnr='");
        sb.append(parnr);
        sb.append("',txtmd='");
        sb.append(txtmd);
        sb.append("',ddtext='");
        sb.append(ddtext);
        sb.append("',matnr='");
        sb.append(matnr);
        sb.append("',maktx='");
        sb.append(maktx);
        sb.append("',wtg='");
        sb.append(wtg);
        sb.append("',qczcts='");
        sb.append(qczcts);
        sb.append("',qczccb='");
        sb.append(qczccb);
        sb.append("',qjzcts='");
        sb.append(qjzcts);
        sb.append("',qjzccb='");
        sb.append(qjzccb);
        sb.append("',qjjcts='");
        sb.append(qjjcts);
        sb.append("',qjccts='");
        sb.append(qjccts);
        sb.append("',qjcccb='");
        sb.append(qjcccb);
        sb.append("',qjxsts='");
        sb.append(qjxsts);
        sb.append("',qjxscb='");
        sb.append(qjxscb);
        sb.append("',qjxsje='");
        sb.append(qjxsje);
        sb.append("',yfhwkpts='");
        sb.append(yfhwkpts);
        sb.append("',yfhwkpcb='");
        sb.append(yfhwkpcb);
        sb.append("',date='");
        sb.append(date);
        sb.append("']");


        return sb.toString();
    }



}
