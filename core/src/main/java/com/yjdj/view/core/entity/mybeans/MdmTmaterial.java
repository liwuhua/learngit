package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * 物料类
 * Created by wangkai on 16/10/14.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class MdmTmaterial {
    @Description(value = "物料")
    private String material;

    @Description(value = "中间的说明")
    private String txtmd;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("MdmTmaterial : [material='");
        sb.append(material);
        sb.append("',txtmd='");
        sb.append(txtmd);
        sb.append("']");

        return sb.toString();
    }
}
