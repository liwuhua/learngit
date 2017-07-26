package com.yjdj.view.core.entity.mybeans;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_KIP1721 {
	
	private String lifnr;
	private String ekgrp;
	private double order_count;
	private double ratio_count;
	private double ontime_count;
	private String txtmd;
	private String stock_money;
	private String budat;
	private String days;
	private String time;
	private String rate;
	private String yearmonth;
	private double nodynamic_money;
	private double halftoone_year_money;
	private double onetotwo_year_money;
	private double morethantwo_year_money;
	private String val_class;
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("THM_KIP1721:[lifnr='");
        sb.append(lifnr);
        sb.append("',ekgrp='");
        sb.append(ekgrp);
        sb.append("',order_count='");
        sb.append(order_count);
        sb.append("',ratio_count='");
        sb.append(ratio_count);
        sb.append("',txtmd='");
        sb.append(txtmd);
        sb.append("',stock_money='");
        sb.append(stock_money);
        sb.append("',budat='");
        sb.append(budat);
        sb.append("',days='");
        sb.append(days);
        sb.append("',time='");
        sb.append(time);
        sb.append("',yearmonth='");
        sb.append(yearmonth);
        sb.append("',rate='");
        sb.append(rate);
        sb.append("',ontime_count='");
        sb.append(ontime_count);
        sb.append("',nodynamic_money='");
        sb.append(nodynamic_money);
        sb.append("',halftoone_year_money='");
        sb.append(halftoone_year_money);
        sb.append("',onetotwo_year_money='");
        sb.append(onetotwo_year_money);
        sb.append("',morethantwo_year_money='");
        sb.append(morethantwo_year_money);
        sb.append("',val_class='");
        sb.append(val_class);
        sb.append("']");

        return sb.toString();
	}
	
}
