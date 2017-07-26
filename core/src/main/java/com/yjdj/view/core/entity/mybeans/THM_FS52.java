package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * FS52实体类
 * Created by yangzhijie on 17/01/17.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS52 {
	
	@Description(value = "故障时间")
	private String yearMonth;

	@Description(value = "数量")
	private String num;
	
	@Override
	public String toString() {
		return "THM_FS51 [yearMonth=" + yearMonth + ", num=" + num +"]";
	}

}


