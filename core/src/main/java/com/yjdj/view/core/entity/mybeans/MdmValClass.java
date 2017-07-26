package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * 供应商类型类
 * Created by wangkai on 17/02/14.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class MdmValClass {
    @Description(value = "评估类")
    private String val_class;

    @Description(value = "中间的说明")
    private String txtmd;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("val_class : [val_class='");
        sb.append(val_class);
        sb.append("',txtmd='");
        sb.append(txtmd);
        sb.append("']");

        return sb.toString();
    }

}
