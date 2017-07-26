package com.yjdj.view.core.entity.mybeans;

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
public class MdmArbpl {
    @Description(value = "检修地点")
    private String arbpl;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("MdmArbpl : [arbpl='");
        sb.append(arbpl);
        sb.append("']");

        return sb.toString();
    }
}
