package com.yjdj.view.core.entity.mybeans;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;

/**
 * create on 2017/03/27
 * @author zhangwenguo
 *
 */

@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS37 extends MybatisBase{
	
	private String dstuf;
	private String posnr;
	private String idnrk;
	private String txtmd;
	private String werks;
	private String meins;
	private String meinb;
	private String menge;
	private String menga;
	private String ekgrp;
	private String last_price;
	private String last_cost;
	private String last_month_price;
	private String last_month_cost;
	private String last_year_price;
	private String last_year_cost;
	private String standard_price;
	private String unit_price;
	private String standard_cost;
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("THM_FS37:[dstuf='");
        sb.append(dstuf);
        sb.append("',posnr='");
        sb.append(posnr);
        sb.append("',idnrk='");
        sb.append(idnrk);
        sb.append("',txtmd='");
        sb.append(txtmd);
        sb.append("',werks='");
        sb.append(werks);
		sb.append("',meins='");
        sb.append(meins);
        sb.append("',meinb='");
        sb.append(meinb);
        sb.append("',menge='");
        sb.append(menge);
        sb.append("',menga='");
        sb.append(menga);
		sb.append("',ekgrp='");
        sb.append(ekgrp);
        sb.append("',last_price='");
        sb.append(last_price);
        sb.append("',last_cost='");
        sb.append(last_cost);
        sb.append("',last_month_price='");
        sb.append(last_month_price);	
		sb.append("',last_month_cost='");
        sb.append(last_month_cost);
        sb.append("',last_year_price='");
        sb.append(last_year_price);
        sb.append("',last_year_cost='");
        sb.append(last_year_cost);
        sb.append("',standard_price='");
        sb.append(standard_price);
		sb.append("',unit_price='");
        sb.append(unit_price);
        sb.append("',standard_cost='");
        sb.append(standard_cost);
        sb.append("']");
        
        return sb.toString();
	}

}
