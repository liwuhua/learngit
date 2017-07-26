package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * 销售订单需求统计表实体类
 * Created by liwuhua on 16/10/21.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_VBAP_01 {
	@Description(value = "销售组织")
	private String vkorg;
	
	@Description(value="销售部门")
	private String vkbur;

	@Description(value="排产业务书编号")
	private String bname;
	
	@Description(value="销售订单")
	private String vbeln;
	
	@Description(value="行项目")
	private String posnr;
	
	@Description(value="物料编码")
	private String matnr;
	
	@Description(value="物料描述")
	private String arktx;
	
	
	@Description(value="排产日期")
	private String erdat;
	
	@Description(value="要求交付日期")
	private String edatu;
	
	@Description(value="计划数量")
	private double wmeng;

	@Override
	public String toString() {
		return "THM_VBAP_01 [vkorg=" + vkorg + ", vkbur=" + vkbur + ", bname=" + bname + ", vbeln=" + vbeln + ", posnr="
				+ posnr + ", matnr=" + matnr + ", arktx=" + arktx + ", erdat=" + erdat + ", edatu=" + edatu + ", wmeng="
				+ wmeng + "]";
	}
	


	
}
