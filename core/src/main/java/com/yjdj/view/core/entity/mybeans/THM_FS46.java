package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhuxuan on 2016/12/28.
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS46 extends MybatisBase {
    private String train_type;//车型
    private String place_subjection;//配属段所
    private String trainamount;//运用车数量
    private String motoramount;//每列/台车装车数量
    private String motoramountsum;//电机总数量

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("THM_FS46:[");
        sb.append("{");
        sb.append("trainmodel:");
        sb.append(train_type);
        sb.append(",place_subjection:");
        sb.append(place_subjection);
        sb.append(",trainamount:");
        sb.append(trainamount);
        sb.append(",motoramount:");
        sb.append(motoramount);
        sb.append(",motoramountsum:");
        sb.append(motoramountsum);
        sb.append("}");
        sb.append("]");

        return sb.toString();
    }

}
