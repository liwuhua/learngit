package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhuxuan on 2016/12/8.
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_IMP_FS19 extends MybatisBase{

    private String type;//类型 取值范围；0到14
    private double balance;//余额
    private String rbukrs;//公司代码 取值范围：5003,5006,5005,5500 ,5004,5025,5026,5142
    private String curtype;//原币本位币 取值范围：'00','10'

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("THM_FS19:[");
        sb.append("{");
        sb.append("type:");
        sb.append(type);
        sb.append(",balance:");
        sb.append(balance);
        sb.append(",rbukrs:");
        sb.append(rbukrs);
        sb.append(",curtype:");
        sb.append(curtype);
        sb.append("}");
        sb.append("]");

        return sb.toString();
    }
}
