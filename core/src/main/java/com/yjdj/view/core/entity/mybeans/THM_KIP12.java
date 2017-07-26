package com.yjdj.view.core.entity.mybeans;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_KIP12 {
	
	private double on_time_rate;
	private double tiqian_complete_sum;
	private double dangqi_complete_sum;
	private double yanqi_complete_sum;
	private double product_complete_sum;
	private double qianqi_uncomplete_sum;
	private double dangqi_uncomplete_sum;
	private double product_uncomplete_sum;
	private String arktx;
	private double plan_menge;
	private double on_time_menge;
	private String txtmd;
	private double uncomplete_sum;
	private String bukrs;
	private double complete_sum;
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("THM_KIP12:[on_time_rate='");
        sb.append(on_time_rate);
        sb.append("',tiqian_complete_sum='");
        sb.append(tiqian_complete_sum);
        sb.append("',dangqi_complete_sum='");
        sb.append(dangqi_complete_sum);
        sb.append("',yanqi_complete_sum='");
        sb.append(yanqi_complete_sum);
        sb.append("',product_complete_sum='");
        sb.append(product_complete_sum);
        sb.append("',qianqi_uncomplete_sum='");
        sb.append(qianqi_uncomplete_sum);
        sb.append("',dangqi_uncomplete_sum='");
        sb.append(dangqi_uncomplete_sum);
        sb.append("',product_uncomplete_sum='");
        sb.append(product_uncomplete_sum);
        sb.append("',arktx='");
        sb.append(arktx);
        sb.append("',plan_menge='");
        sb.append(plan_menge);
        sb.append("',on_time_menge='");
        sb.append(on_time_menge);
        sb.append("',txtmd='");
        sb.append(txtmd);
        sb.append("',uncomplete_sum='");
        sb.append(uncomplete_sum);
        sb.append("',bukrs='");
        sb.append(bukrs);
        sb.append("',complete_sum='");
        sb.append(complete_sum);
        
        sb.append("']");

        return sb.toString();
	}
	
}
