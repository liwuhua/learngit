package com.yjdj.view.core.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * 故障类型
 * Created by yangzhijie on 2016/03/08
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class MuserRole {
    @Description(value = "角色id")
    private Long id;

    @Description(value = "角色名称")
    private String name;

    @Description(value = "角色描述")
    private String description;
}
