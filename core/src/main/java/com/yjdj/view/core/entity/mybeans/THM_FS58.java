package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by sunwan on 2017/4/25.
 */
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@Setter
@Getter
public class THM_FS58 {

    private String iwerk;//工厂
    private String matnr;//产品编码
    private String groes;//电机型号
    private String zlevel;//维修级别
    private String pjwlbm;//配件物料编码
    private String pjwlms;//配件物料描述
    private String ohjs;//偶换件数
    private String jxts;//检修台数
    private String ohts;//偶换台数
    private String ohtl;//偶换台率
    private String tyl;//台用量
    private String ohl;//偶换率

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("THM_FS58 : [iwerk='");
        sb.append(iwerk);
        sb.append("',matnr='");
        sb.append(matnr);
        sb.append("',groes='");
        sb.append(groes);
        sb.append("',zlevel='");
        sb.append(zlevel);
        sb.append("',pjwlbm='");
        sb.append(pjwlbm);
        sb.append("',pjwlms='");
        sb.append(pjwlms);
        sb.append("',ohjs='");
        sb.append(ohjs);
        sb.append("',jxts='");
        sb.append(jxts);
        sb.append("',ohts='");
        sb.append(ohts);
        sb.append("',ohtl='");
        sb.append(ohtl);
        sb.append("',tyl='");
        sb.append(tyl);
        sb.append("',ohl='");
        sb.append(ohl);
        sb.append("']");

        return sb.toString();
    }

}
