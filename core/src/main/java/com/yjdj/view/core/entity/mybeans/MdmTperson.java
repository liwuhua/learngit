package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * 用户
 * Created by wangkai on 2016/11/3.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class MdmTperson {
    @Description(value = "人员编号")
    private String person;

    @Description(value = "人员名称")
    private String txtmd;

}
