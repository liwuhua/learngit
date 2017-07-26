package com.yjdj.view.core.entity.mybeans;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;

/**
 * create on 2016/10/25
 * update on 2016/12/05
 * @author yangzhijie
 *
 */

@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_MSEG_04 extends MybatisBase{
/**
* 	select thm_mseg_04_05_.WERKS,thm_mseg_04_05_.MATNR,thm_mseg_04_05_.EBELN,thm_mseg_04_05_.EBELP,thm_mseg_04_05_.LIFNR,
	    thm_mseg_04_05_.EKGRP,thm_mseg_04_05_.LFDAT,thm_mseg_04_05_.BUDAT,thm_mseg_04_05_.timediff,thm_mseg_04_05_.MATNRTXTMD,
	    thm_mseg_04_05_.LIFNRTXTMD,thm_mseg_04_05_.MENGE,thm_mseg_04_05_.BANFN
	from THM_MSEG_04_05 thm_mseg_04_05_
	where 
	werks in(查询条件中的工厂) and ebeln in(查询条件中的采购订单) and matnr in(查询条件中的物料) and lifnr in(查询条件中的供应商) and ekgrp in(查询条件中的采购组) and budat >=起始入库日期 and budat <=终止入库日期
 * 
 *
 */

//	private String mblnr;//物料凭证
//	private String mjahr;//物料年度凭证
//	private String zeile;//物料凭证项目
	private String werks;//工厂
	private String matnr;//物料
//	private String meins;//基本计量单位
//	private String bwart;//移动类型
	private String ebeln;//采购订单
	private String ebelp;//行项目
//	private String kzzug;//收货标识
	private String lifnr;//供应商编码
	private String ekgrp;//采购组
	private String lfdat;//采购申请交货日期
	private String budat;//实际收货日期
	private String timediff;//申请交货时间差异
        private String timediffeindt;//订单交货时间差异
	private String matnrtxtmd;//物料描述
	private String lifnrtxtmd;//供应商描述
	private String menge;//实际收货数量
	private String banfn;//采购申请
        private String eindt;//采购订单交货日期
        
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("THM_MSEG_04:[werks='");
        sb.append(werks);
        sb.append("',matnr='");
        sb.append(matnr);
        sb.append("',ebeln='");
        sb.append(ebeln);
        sb.append("',ebelp='");
        sb.append(ebelp);
        sb.append("',lifnr='");
        sb.append(lifnr);
        sb.append("',ekgrp='");
        sb.append(ekgrp);
        sb.append("',lfdat='");
        sb.append(lfdat);
        sb.append("',budat='");
        sb.append(budat);
        sb.append("',timediff='");
        sb.append(timediff);
        sb.append("',timediffeindt='");
        sb.append(timediffeindt);
        sb.append("',matnrtxtmd='");
        sb.append(matnrtxtmd);
        sb.append("',lifnrtxtmd='");
        sb.append(lifnrtxtmd);
        sb.append("',menge='");
        sb.append(menge);
        sb.append("',banfn='");
        sb.append(banfn);
        sb.append("',eindt='");
        sb.append(eindt);
        sb.append("']");

        return sb.toString();
	}
	
}
