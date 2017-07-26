package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * 销售组织
 * Created by wangkai on 16/10/17.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class TordType {
    @Description(value = "订单类型编号")
    public String ordTypeCode; 
    
    @Description(value = "长文本描述")
    public String txtmd;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TordType : [ordTypeCode='");
        sb.append(ordTypeCode);
        sb.append("',txtmd='");
        sb.append(txtmd);
        sb.append("']");

        
        return sb.toString();
    }
}
