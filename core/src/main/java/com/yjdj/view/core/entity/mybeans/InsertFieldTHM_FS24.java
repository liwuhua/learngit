package com.yjdj.view.core.entity.mybeans;

import com.yjdj.view.core.util.ExcelField;

public class InsertFieldTHM_FS24 {
	//项目	行次	内部订单	描述	上年发生额 	本年计划	本期计划	本期发生额   执行率

	private String item_column;
	private Integer id;
	private String racct;
	private String txtmd;
	private Double last_year_cost;
	private Double year_plan;
	private Double period_plan;
	private Double period_cost;
	private Double exe_rate;
	private String startdate;
	private String enddate;
	

	@ExcelField(title = "项目",type=0,sort=10)
	public String getItem_column() {
		return item_column;
	}
	public void setItem_column(String item_column) {
		this.item_column = item_column;
	}
	@ExcelField(title = "行次",type=0,sort=20)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ExcelField(title = "内部订单",type=0,sort=30)
	public String getRacct() {
		return racct;
	}
	public void setRacct(String racct) {
		this.racct = racct;
	}
	@ExcelField(title = "描述",type=0,sort=40)
	public String getTxtmd() {
		return txtmd;
	}
	public void setTxtmd(String txtmd) {
		this.txtmd = txtmd;
	}
	@ExcelField(title = "上年发生额 ",type=0,sort=50)
	public Double getLast_year_cost() {
		return last_year_cost;
	}
	public void setLast_year_cost(Double last_year_cost) {
		this.last_year_cost = last_year_cost;
	}
	@ExcelField(title = "本年计划",type=0,sort=60)
	public Double getYear_plan() {
		return year_plan;
	}
	public void setYear_plan(Double year_plan) {
		this.year_plan = year_plan;
	}
	@ExcelField(title = "本期计划",type=0,sort=70)
	public Double getPeriod_plan() {
		return period_plan;
	}
	public void setPeriod_plan(Double period_plan) {
		this.period_plan = period_plan;
	}
	@ExcelField(title = "本期发生额",type=0,sort=80)
	public Double getPeriod_cost() {
		return period_cost;
	}
	public void setPeriod_cost(Double period_cost) {
		this.period_cost = period_cost;
	}
	@ExcelField(title = "执行率",type=0,sort=90)
	public Double getExe_rate() {
		return exe_rate;
	}
	public void setExe_rate(Double exe_rate) {
		this.exe_rate = exe_rate;
	}
	
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
	
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("THM_FS24:["); 
        sb.append("{");
        sb.append("item_column:");
        sb.append(item_column);
        sb.append(",id:");
        sb.append(id);
        sb.append(",racct:");
        sb.append(racct);
        sb.append(",txtmd:");
        sb.append(txtmd);
        sb.append(",last_year_cost:");
        sb.append(last_year_cost);
        sb.append(",year_plan:");
        sb.append(year_plan);
        sb.append(",period_plan:");
        sb.append(period_plan);
        sb.append(",period_cost:");
        sb.append(period_cost);
        sb.append(",exe_rate:");
        sb.append(exe_rate);
        sb.append(",startdate:");
        sb.append(startdate);
        sb.append(",enddate:");
        sb.append(enddate);
        sb.append("}");
        sb.append("]");

        return sb.toString();
    }
	
}
