package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * 销售订单完成统计表实体类
 * Created by liwuhua on 16/10/21.
 */


@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_MSEG_06 {
	
	//第一张表的查询字段
	@Description(value = "销售部门")
	private String vkbur;
	
	@Description(value = "排产业务书编号")
	private String bname;
	
	@Description(value = "销售订单号")
	private String vbeln;
	
	@Description(value = "行项目")
	private String posnr;
	
	@Description(value = "物料编码")
	private String matnr;
	
	@Description(value = "物料描述")
	private String arktx;
	
	@Description(value = "排产日期")
	private String erdat;
	
	@Description(value = "要求交付日期")
	private String edatu;
	
	@Description(value = "计划数量")  
//	private double WMENG;
	private String wmeng;
        
        @Description(value = "生产订单")  
//	private double WMENG;
	private String aufnr;
	
	@Description(value = "前期累计完成数量")
//	private double before_complete_amount_total;
	private String before_complete_amount_total;
	
	@Description(value = "当期完成数量")
//	private double current_complete_amount;
	private String current_complete_amount;
	
	@Description(value = "准时完成数量")
//	private double ontime_complete_amount;
	private String ontime_complete_amount;
	
	@Description(value = "准时完成率")
//	private int ontime_rate;  
	private String ontime_rate;
        
        @Description(value = "订单完成率")
//	private double ontime_complete_amount;
	private String ocr;
	
	@Description(value = "订单正点率")
//	private int ontime_rate;  
	private String onr;

	@Override
	public String toString() {
		return "THM_MSEG_06 [vkbur=" + vkbur + ", bname=" + bname + ", vbeln=" + vbeln + ", posnr=" + posnr + ", matnr="
				+ matnr + ", arktx=" + arktx + ", erdat=" + erdat + ", edatu=" + edatu + ", wmeng=" + wmeng + ",aufnr=" + aufnr
				+ ", before_complete_amount_total=" + before_complete_amount_total + ", current_complete_amount="
				+ current_complete_amount + ", ontime_complete_amount=" + ontime_complete_amount + ", ontime_rate="
				+ ontime_rate+ ", ocr=" + ocr+ ", onr=" + onr + "]";
	}
   

}
