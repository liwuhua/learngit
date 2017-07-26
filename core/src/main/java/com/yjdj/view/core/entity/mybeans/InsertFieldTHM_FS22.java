package com.yjdj.view.core.entity.mybeans;

import com.yjdj.view.core.util.ExcelField;

/**
 * 
 * @author liuchengli
 * 
 *
 */
public class InsertFieldTHM_FS22 {

    private String aufnr;//项目编号
    private String ktext;//项目描述
    private Double all_buget;//项目总预算
    private Double bef_years_cost;//以前年度累计发生额
    private Double wtjhv;//当年项目预算
	private String startdate;
	private String enddate;
	

	@ExcelField(title = "项目编号",type=0,sort=10)
	public String getAufnr() {
		return aufnr;
	}
	public void setAufnr(String aufnr) {
		this.aufnr = aufnr;
	}
	@ExcelField(title = "项目描述",type=0,sort=20)
	public String getKtext() {
		return ktext;
	}
	public void setKtext(String ktext) {
		this.ktext = ktext;
	}
	@ExcelField(title = "项目总预算",type=0,sort=30)
	public Double getAll_buget() {
		return all_buget;
	}
	public void setAll_buget(Double all_buget) {
		this.all_buget = all_buget;
	}
	@ExcelField(title = "以前年度累计发生额",type=0,sort=40)
	public Double getBef_years_cost() {
		return bef_years_cost;
	}
	public void setBef_years_cost(Double bef_years_cost) {
		this.bef_years_cost = bef_years_cost;
	}
	@ExcelField(title = "当年项目预算",type=0,sort=50)
	public Double getWtjhv() {
		return wtjhv;
	}
	public void setWtjhv(Double wtjhv) {
		this.wtjhv = wtjhv;
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
        sb.append("aufnr:");
        sb.append(aufnr);
        sb.append(",ktext:");
        sb.append(ktext);
        sb.append(",all_buget:");
        sb.append(all_buget);
        sb.append(",bef_years_cost:");
        sb.append(bef_years_cost);
        sb.append(",wtjhv:");
        sb.append(wtjhv);
        sb.append(",startdate:");
        sb.append(startdate);
        sb.append(",enddate:");
        sb.append(enddate);
        sb.append("}");
        sb.append("]");

        return sb.toString();
    }
	
}
