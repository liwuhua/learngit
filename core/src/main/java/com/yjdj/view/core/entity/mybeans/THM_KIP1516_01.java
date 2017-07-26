package com.yjdj.view.core.entity.mybeans;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


/**
 * 
 * @author liuchengli
 * 订单在产金额
       车间仓材料总金额
       原材料评估类下钻
 * 
 *
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_KIP1516_01 {
	//all_sum.COMP_CODE,all_sum.TXTMD,all_sum.VAL_CLASS,all_sum.al_s,one_sum.one_s,two_sum.two_s,three_sum.three_s
	private double order_sum; //订单在产金额
	private double workshop_sum; //车间仓材料总金额
	private String val_class;//评估类别
	private String ordertype;//订单类别
	private String auart;//订单号
	private String txtmd;
	
	private String comp_name;//公司名称
	private double al_s;//占用总额
	private double wdtal_s;//无动态资金总额
	private double one_s;//半年到1年额
	private double two_s;//1年到2年额
	private double three_s;//2年以上额
	
	
	
	
	@Override
	public String toString() {
        
        StringBuffer sb = new StringBuffer();
       
        sb.append("{");
        sb.append("val_class:");
        sb.append(val_class);
        sb.append(",ordertype:");
        sb.append(ordertype);
        sb.append(",auart:");
        sb.append(auart);
        sb.append(",order_sum:");
        sb.append(order_sum);
        sb.append(",workshop_sum:");
        sb.append(workshop_sum);
        sb.append(",txtmd:");
        sb.append(txtmd);        
        
        sb.append(",comp_name:");
        sb.append(comp_name);
        sb.append(",al_s:");
        sb.append(al_s);
        sb.append(",wdtal_s:");
        sb.append(wdtal_s);
        sb.append(",one_s:");
        sb.append(one_s);
        sb.append(",two_s:");
        sb.append(two_s);
        sb.append(",three_s:");
        sb.append(three_s);
        sb.append("}");
        return sb.toString();
	}
	
}
