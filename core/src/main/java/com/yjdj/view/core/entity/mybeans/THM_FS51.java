package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * FS51实体类
 * Created by yangzhijie on 16/12/29.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS51 {
	
	@Description(value = "产品型号")
	private String cpxh;	
	
	@Description(value = "产品生命阶段")
	private String cpsmjd;
	
	@Description(value = "故障部位")
	private String gzbw;

	@Description(value = "数量")
	private String num;
	
	@Override
	public String toString() {
		return "THM_FS51 [cpxh=" + cpxh +  ", cpsmjd=" + cpsmjd +", gzbw=" + gzbw + ", num=" + num +"]";
	}

}


