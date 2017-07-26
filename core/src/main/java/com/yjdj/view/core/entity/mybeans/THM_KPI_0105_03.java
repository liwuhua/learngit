package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengli on 2017/01/10.
 * 
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_KPI_0105_03 extends MybatisBase {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double zt1;//销售收入(万元)T1指标
	private double zt2;//销售收入(万元)T2指标
	private double zt3;//销售收入(万元)T3指标
	private double zzc;//销售收入(万元)考核指标
	private double zlt1;//净利润(万元)T1指标
	private double zlt2;//净利润(万元)T2指标
	private double zlt3;//净利润(万元)T3指标
	private double zlkh;//净利润(万元)考核指标
	private double zzcp;//销售收入调整比值
	private double zlkp;//净利润调整比值
	
	
	/**
	 * 新的指标
	 */
	private double zsmx;//营业收入(万元)奋斗目标
	private double zlmx;//利润收入(万元)奋斗目标
	private double zhmx;//合同签订奋斗目标
	
	/**
	 * 传到前端的值
	 */
	private double sellFirst;//营收第一指标
	private double sellAdjust;//营收第二指标
	private double sellMax;//营收刻度盘最大值
	
	private double profitfirst;//净利润第一指标
	private double profitAdjust;//净利润第二指标
	private double profitMax;//净利润刻度盘最大值
	

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("THM_KPI_02:[");
        sb.append("{");
        sb.append("sellFirst:");
        sb.append(sellFirst);
        sb.append(",sellAdjust:");
        sb.append(sellAdjust);
        sb.append(",sellMax:");
        sb.append(sellMax);
        
        sb.append(",profitfirst:");
        sb.append(profitfirst);
        sb.append(",profitAdjust:");
        sb.append(profitAdjust);
        sb.append(",profitMax:");
        sb.append(profitMax);
        
        sb.append("}");
        sb.append("]");
        return sb.toString();
    }
}
