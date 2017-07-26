package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by sunwan on 2016/11/7.
 */

//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@Setter
@Getter
public class THM_VBAP_02 {

    private String vkorg;   //销售组织

    private String vkbur;   //销售部门

    private String kunnr;   //售达方

    private String txtmd;   //客户描述

    private String matnr;   //物料

    private String arktx;   //物料描述

    private String vbeln;   //销售凭证订单号

    private String posnr;   //项目

    private String erdat;   //创建日期

    private double netwr;   //净价值

    private String werks;   //工厂

    private String auart;   //销售凭证类型

    private double zmeng;   //目标数量

    private double unit;   //单价

    private double sum_unit;   //合同金额

    private double ddcount;   //订单数量

    private double fhcount;   //发货数量

    private double kpcount;   //开票数量

    private double ddstock;   //订单库存

    private double uddstock;   //非订单库存



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("THM_VBAP_02 : [vkorg='");
        sb.append(vkorg);
        sb.append("',vkbur='");
        sb.append(vkbur);
        sb.append("',kunnr='");
        sb.append(kunnr);
        sb.append("',txtmd='");
        sb.append(txtmd);
        sb.append("',matnr='");
        sb.append(matnr);
        sb.append("',arktx='");
        sb.append(arktx);
        sb.append("',vbeln='");
        sb.append(vbeln);
        sb.append("',posnr='");
        sb.append(posnr);
        sb.append("',erdat='");
        sb.append(erdat);
        sb.append("',netwr='");
        sb.append(netwr);
        sb.append("',werks='");
        sb.append(werks);
        sb.append("',auart='");
        sb.append(auart);
        sb.append("',zmeng='");
        sb.append(zmeng);
        sb.append("',unit='");
        sb.append(unit);
        sb.append("',sum_unit='");
        sb.append(sum_unit);
        sb.append("',ddcount='");
        sb.append(ddcount);
        sb.append("',fhcount='");
        sb.append(fhcount);
        sb.append("',kpcount='");
        sb.append(kpcount);
        sb.append("',ddstock='");
        sb.append(ddstock);
        sb.append("',uddstock='");
        sb.append(uddstock);
        sb.append("']");


        return sb.toString();

    }


}
