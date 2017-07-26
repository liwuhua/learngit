package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * 销售部门
 * Created by wangkai on 16/10/17.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class MdmTsalesOff {
    @Description(value = "销售部门")
    private String sales_off;

    @Description(value = "简要描述")
    private String txtsh;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("MdmTsalesOff : [sales_off='");
        sb.append(sales_off);
        sb.append("',txtsh='");
        sb.append(txtsh);
        sb.append("']");

        return sb.toString();
    }
}
