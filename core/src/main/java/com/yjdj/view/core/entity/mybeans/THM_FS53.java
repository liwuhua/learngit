package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * FS52实体类
 * Created by yangzhijie on 17/02/14.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS53 {
	
	@Description(value = "故障月份")
	private String month;

	@Description(value = "数量")
	private String num;
	
	@Description(value = "故障类型")
	private String gzlx;
	
	@Description(value = "产品型号")
	private String cpxh;
	
	@Override
	public String toString() {
		return "THM_FS53 [month=" + month + ", num=" + num + ", gzlx=" + gzlx +", cpxh=" + cpxh +"]";
	}

}


