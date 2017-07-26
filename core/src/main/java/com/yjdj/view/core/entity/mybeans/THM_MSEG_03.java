package com.yjdj.view.core.entity.mybeans;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;

/**
 * create on 2016/10/21
 * pas:采购入库金额统计简称(Purchase amount statistics) 
 * @author zhangwenguo
 *
 */

@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_MSEG_03 extends MybatisBase{
/**
 * Select WERKS,MATNR, MEINS, PRICE_BASE,LIFNR, EKGRP,SUM(MENGE入库数量),
 * SUM(如果BUALT>0取BUALT金额,否则取DMBTR金额) from THM_MSEG_03
 *   where WERKS in (查询条件中的工厂)  
 *   and MATNR  in (查询条件中的物料) 
 *   and LIFNR  in (查询条件中的供应商) 
 *   and  EKGRP采购组 in (查询条件中的采购组) 
 *   and BUDAT >=起始入库日期
 *   and BUDAT <= 终止入库日期
 *   group by LIFNR,WERKS,MATNR
 */
	
	private String txtmd; //物料描述
	private String vendor; //供应商描述
	private String werks; //工厂
	private String matnr; //物料编码
	private String meins; //基本计量单位
	private String price_base; //价格单位
	private String lifnr; //供应商编码
	private String ekgrp; //采购组
	
	private double sum_menge; //MENGE入库数量
	private double sum_bualt; //本位币金额总量
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("THM_MSEG_03:[werks='");
        sb.append(werks);
        sb.append("',matnr='");
        sb.append(matnr);
        sb.append("',txtmd='");
        sb.append(txtmd);
        sb.append("',vendor='");
        sb.append(vendor);
        sb.append("',meins='");
        sb.append(meins);
        sb.append("',price_base='");
        sb.append(price_base);
        sb.append("',lifnr='");
        sb.append(lifnr);
        sb.append("',sum_menge='");
        sb.append(sum_menge);
        sb.append("',sum_bualt='");
        sb.append(sum_bualt);
        sb.append("']");

        return sb.toString();
	}
}
