package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * FS12期间费用表实体类
 * Created by liwuhua on 16/11/7.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS12 {
	
	//第一张表的查询字段
	@Description(value = "行次号")
	private String rownum;
	
	@Description(value = "项目名称")
	private String proname;
	
	@Description(value = "本年累计数")
//	private  double curryearcount;   
	private  String curryearcount;
	
	@Description(value = "上年同期数")
//	private double lastyearcount;
	private String lastyearcount;
	
	@Override
	public String toString() {
		return "THM_FS12 [rownum=" + rownum + ", proname=" + proname + ", curryearcount=" + curryearcount
				+ ", lastyearcount=" + lastyearcount + "]";
	}

}


