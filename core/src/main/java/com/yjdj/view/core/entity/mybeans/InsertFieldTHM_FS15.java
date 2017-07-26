package com.yjdj.view.core.entity.mybeans;

import com.yjdj.view.core.util.ExcelField;

public class InsertFieldTHM_FS15 {
	//项目	行次	期末金额	年初金额	项目	行次	期末金额	年初金额
	private String projectOne;
	private String lineNumOne;
	private String endAccountOne;
	private String startAccountOne;
	private String projectTwo;
	private String lineNumTwo;
	private String endAccountTwo;
	private String startAccountTwo;
	private String yearMonth;
	private String companyCode;
	
	@ExcelField(title = "项目",type=0,sort=10)
	public String getProjectOne() {
		return projectOne;
	}
	public void setProjectOne(String projectOne) {
		this.projectOne = projectOne;
	}
	@ExcelField(title = "行次",type=0,sort=20)
	public String getLineNumOne() {
		return lineNumOne;
	}
	public void setLineNumOne(String lineNumOne) {
		this.lineNumOne = lineNumOne;
	}
	@ExcelField(title = "期末金额",type=0,sort=30)
	public String getEndAccountOne() {
		return endAccountOne;
	}
	public void setEndAccountOne(String endAccountOne) {
		this.endAccountOne = endAccountOne;
	}
	@ExcelField(title = "年初金额",type=0,sort=40)
	public String getStartAccountOne() {
		return startAccountOne;
	}
	public void setStartAccountOne(String startAccountOne) {
		this.startAccountOne = startAccountOne;
	}
	@ExcelField(title = "项目",type=0,sort=50)
	public String getProjectTwo() {
		return projectTwo;
	}
	public void setProjectTwo(String projectTwo) {
		this.projectTwo = projectTwo;
	}
	@ExcelField(title = "行次",type=0,sort=60)
	public String getLineNumTwo() {
		return lineNumTwo;
	}
	public void setLineNumTwo(String lineNumTwo) {
		this.lineNumTwo = lineNumTwo;
	}
	@ExcelField(title = "期末金额",type=0,sort=70)
	public String getEndAccountTwo() {
		return endAccountTwo;
	}
	public void setEndAccountTwo(String endAccountTwo) {
		this.endAccountTwo = endAccountTwo;
	}
	@ExcelField(title = "年初金额",type=0,sort=80)
	public String getStartAccountTwo() {
		return startAccountTwo;
	}
	public void setStartAccountTwo(String startAccountTwo) {
		this.startAccountTwo = startAccountTwo;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	
}
