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
public class THM_LIPS_02 {

    private String lips_erdat;  //交货单创建日期交货时间

    private String kunnr;   //送达方、发送地点
    
    private String txtmd;   //送达方名称

    private String lips_vbeln;  //交货单号、发货单据号

    private String posnr;   //交货单项目

    private String kdauf;   //销售订单

    private String kdpos;   //销售订单项目

    private String vbak_erdat;  //订单创建日期

    private double kwmeng;  //订单数量

    private String contnbr;   //参考合同号

    private String contitm;   //参考合同行项目

    private double lgmng;   //数量(库存单位)

    private String vbrp_erdat;  //创建日期

    private double fklmg;   //库存单位开票量

    private String vbrp_vbeln;  //开票凭证

    private String sernr;   //序列号

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("THM_LIPS_02 : [lips_erdat='");
        sb.append(lips_erdat);
        sb.append("',kunnr='");
        sb.append(kunnr);
        sb.append("',txtmd='");
        sb.append(txtmd);
        sb.append("',lips_vbeln='");
        sb.append(lips_vbeln);
        sb.append("',posnr='");
        sb.append(posnr);
        sb.append("',kdauf='");
        sb.append(kdauf);
        sb.append("',kdpos='");
        sb.append(kdpos);
        sb.append("',vbak_erdat='");
        sb.append(vbak_erdat);
        sb.append("',kwmeng='");
        sb.append(kwmeng);
        sb.append("',contnbr='");
        sb.append(contnbr);
        sb.append("',contitm='");
        sb.append(contitm);
        sb.append("',lgmng='");
        sb.append(lgmng);
        sb.append("',vbrp_erdat='");
        sb.append(vbrp_erdat);
        sb.append("',fklmg='");
        sb.append(fklmg);
        sb.append("',vbrp_vbeln='");
        sb.append(vbrp_vbeln);
        sb.append("',sernr='");
        sb.append(sernr);
        sb.append("']");

        return sb.toString();

    }

}
