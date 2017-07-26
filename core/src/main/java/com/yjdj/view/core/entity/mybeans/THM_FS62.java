package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * FS55实体类
 * Created by yangzhijie on 17/03/07
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS62 {
	
	@Description(value = "工厂")
	private String iwerk;
	
	@Description(value = "进厂日期")
	private String zdate;
	
	@Description(value = "工作中心")
	private String arbpl;
	
	@Description(value = "电机型号")
	private String groes;
	
	@Description(value = "电机编号")
	private String sernr;
	
	@Description(value = "维修级别")
	private String zlevel;
	
	@Override
	public String toString() {
		return "THM_FS53 [iwerk=" + iwerk + ", zdate=" + zdate + ", arbpl=" + arbpl + 
				", groes=" + groes + ", sernr=" + sernr +	", zlevel=" + zlevel +"]";
	}

}


