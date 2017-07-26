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
public class MdmTdocType {
    @Description(value = "合同编号")
    private String doc_type;

    @Description(value = "中间的说明")
    private String txtsh;

}
