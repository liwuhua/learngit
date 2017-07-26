package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by sunwan on 2016/10/24.
 */

//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@Setter
@Getter
public class THM_FS03 {

    private String matnr;
    private String txtmd;
    private String meins;
    private String peinh;
    private String mwskz;
    private String ekgrp;
    private double sum_menge;
    private double dqnotax;
    private double dqtax;
    private double dnnotax;
    private double dntax;
    private double snnotax;
    private double sntax;
    private double lsnotax;
    private double lstax;
    private double cjnotax;
    private double cjtax;
//   private  String lsbudat;


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("THM_FS03 : [matnr='");
        sb.append(matnr);
        sb.append("',txtmd='");
        sb.append(txtmd);
        sb.append("',meins='");
        sb.append(meins);
        sb.append("',peinh='");
        sb.append(peinh);
        sb.append("',mwskz='");
        sb.append(mwskz);
        sb.append("',ekgrp='");
        sb.append(ekgrp);
        sb.append("',sum_menge='");
        sb.append(sum_menge);
        sb.append("',dqnotax='");
        sb.append(dqnotax);
        sb.append("',dqtax='");
        sb.append(dqtax);
        sb.append("',dnnotax='");
        sb.append(dnnotax);
        sb.append("',dntax='");
        sb.append(dntax);
        sb.append("',snnotax='");
        sb.append(snnotax);
        sb.append("',sntax='");
        sb.append(sntax);
        sb.append("',lsnotax='");
        sb.append(lsnotax);
        sb.append("',lstax='");
        sb.append(lstax);
        sb.append("',cjnotax='");
        sb.append(cjnotax);
        sb.append("',cjtax='");
        sb.append(cjtax);
//        sb.append("',lsbudat='");
//        sb.append(lsbudat);
        sb.append("']");


        return sb.toString();
    }
}
