package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * 产品型号
 * Created by wangkai on 2016/11/3.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class MdmProductModel {
    @Description(value = "产品型号")
    private String productModel;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("MdmProductModel : [productModel='");
        sb.append(productModel);
        sb.append("']");

        return sb.toString();
    }
}
