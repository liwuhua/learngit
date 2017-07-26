package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * 采购组
 * Created by wangkai on 16/10/14.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class MdmTpurGroup {
    @Description("采购组")
    private String pur_group;

    @Description("简要描述")
    private String txtsh;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("MdmTpurGroup : [pur_group='");
        sb.append(pur_group);
        sb.append("',txtsh='");
        sb.append(txtsh);
        sb.append("']");

        return sb.toString();
    }
}
