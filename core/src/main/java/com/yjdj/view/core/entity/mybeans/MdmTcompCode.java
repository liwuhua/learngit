package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * 公司代码
 * Created by wangkai on 2016/11/3.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class MdmTcompCode {
    @Description(value = "公司代码")
    private String comp_code;

    @Description(value = "中间的说明")
    private String txtmd;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("MdmTcompCode : [comp_code='");
        sb.append(comp_code);
        sb.append("',txtmd='");
        sb.append(txtmd);
        sb.append("']");

        return sb.toString();
    }
}
