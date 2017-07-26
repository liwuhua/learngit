package com.yjdj.view.core.entity.mybeans;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_KIP1516 {
	
	private Double bwmng_sum; //订单在产金额
	private Double days; //产资金周转天数
	private String time; //日期
	private Double balance;
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("THM_KIP1516:[bwmng_sum='");
        sb.append(bwmng_sum);
        sb.append("',days='");
        sb.append(days);
        sb.append("',time='");
        sb.append(time);
        sb.append("',balance='");
        sb.append(balance);
        sb.append("']");

        return sb.toString();
	}
	
}
