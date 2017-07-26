package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * FS09实体类
 * Created by yangzhijie on 16/12/22.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS09 {
	
	@Description(value = "行号")
	private String itemNo;	
	
	@Description(value = "本年数")
	private String thisYearDmbtr;
	
	@Description(value = "上年数")
	private String lastYearDmbtr;

	@Override
	public String toString() {
		return "THM_FS12 [itemNo=" + itemNo +  ", thisYearDmbtr=" + thisYearDmbtr +", lastYearDmbtr=" + lastYearDmbtr +"]";
	}

}


