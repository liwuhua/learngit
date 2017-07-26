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
public class THM_FS07 {

    private String bukrs;
    private String gs_txt;
    private String kunnr;
    private String kh_txt;
    private String hkont;
    private double dqxssr;
    private double qcye;
    private double xj_s;
    private double zyywsr;
    private double qtywsr;
    private double xxsj;
    private double qt;
    private double xj_h;
    private double xianjin;
    private double yhcd;
    private double sycd;
    private double yunxin;
    private double dizhang;
    private double qmye;
    private double ys_sixmonth;
    private double ys_sixone;
    private double ys_onetwo;
    private double ys_twothree;
    private double ys_threefour;
    private double ys_fourfive;
    private double ys_fiveyear;
    private double zb_sixmonth;
    private double zb_sixone;
    private double zb_onetwo;
    private double zb_twothree;
    private double zb_threefour;
    private double zb_fourfive;
    private double zb_fiveyear;
    private double zb_xiaoji;


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("THM_FS07 : [bukrs='");
        sb.append(bukrs);
        sb.append("',gs_txt='");
        sb.append(gs_txt);
        sb.append("',kunnr='");
        sb.append(kunnr);
        sb.append("',kh_txt='");
        sb.append(kh_txt);
        sb.append("',hkont='");
        sb.append(hkont);
        sb.append("',dqxssr='");
        sb.append(dqxssr);
        sb.append("',qcye='");
        sb.append(qcye);
        sb.append("',xj_s='");
        sb.append(xj_s);
        sb.append("',zyywsr='");
        sb.append(zyywsr);
        sb.append("',qtywsr='");
        sb.append(qtywsr);
        sb.append("',xxsj='");
        sb.append(xxsj);
        sb.append("',qt='");
        sb.append(qt);
        sb.append("',xj_h='");
        sb.append(xj_h);
        sb.append("',xianjin='");
        sb.append(xianjin);
        sb.append("',yhcd='");
        sb.append(yhcd);
        sb.append("',sycd='");
        sb.append(sycd);
        sb.append("',yunxin='");
        sb.append(yunxin);
        sb.append("',dizhang='");
        sb.append(dizhang);
        sb.append("',qmye='");
        sb.append(qmye);
        sb.append("',ys_sixmonth='");
        sb.append(ys_sixmonth);
        sb.append("',ys_sixone='");
        sb.append(ys_sixone);
        sb.append("',ys_onetwo='");
        sb.append(ys_onetwo);
        sb.append("',ys_twothree='");
        sb.append(ys_twothree);
        sb.append("',ys_threefour='");
        sb.append(ys_threefour);
        sb.append("',ys_fourfive='");
        sb.append(ys_fourfive);
        sb.append("',ys_fiveyear='");
        sb.append(ys_fiveyear);
        sb.append("',zb_sixmonth='");
        sb.append(zb_sixmonth);
        sb.append("',zb_sixone='");
        sb.append(zb_sixone);
        sb.append("',zb_onetwo='");
        sb.append(zb_onetwo);
        sb.append("',zb_twothree='");
        sb.append(zb_twothree);
        sb.append("',zb_threefour='");
        sb.append(zb_threefour);
        sb.append("',zb_fourfive='");
        sb.append(zb_fourfive);
        sb.append("',zb_fiveyear='");
        sb.append(zb_fiveyear);
        sb.append("',zb_xiaoji='");
        sb.append(zb_xiaoji);
        sb.append("']");


        return sb.toString();
    }
}
