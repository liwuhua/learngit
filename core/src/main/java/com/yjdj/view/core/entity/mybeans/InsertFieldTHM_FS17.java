package com.yjdj.view.core.entity.mybeans;

import com.yjdj.view.core.util.ExcelField;

public class InsertFieldTHM_FS17 {
	//项目	行次	期末金额	年初金额	项目	行次	期末金额	年初金额
	private String projectOne;
	private String lineNumOne;
	private String currentAccountOne;
	private String previousAccountOne;
	private String projectTwo;
	private String lineNumTwo;
	private String currentAccountTwo;
	private String previousAccountTwo;
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
	@ExcelField(title = "本期金额",type=0,sort=30)
	public String getCurrentAccountOne() {
		return currentAccountOne;
	}
	public void setCurrentAccountOne(String currentAccountOne) {
		this.currentAccountOne = currentAccountOne;
	}
	@ExcelField(title = "上期金额",type=0,sort=40)
	public String getPreviousAccountOne() {
		return previousAccountOne;
	}
	public void setPreviousAccountOne(String previousAccountOne) {
		this.previousAccountOne = previousAccountOne;
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
	@ExcelField(title = "本期金额",type=0,sort=70)
	public String getCurrentAccountTwo() {
		return currentAccountTwo;
	}
	public void setCurrentAccountTwo(String currentAccountTwo) {
		this.currentAccountTwo = currentAccountTwo;
	}
	@ExcelField(title = "上期金额",type=0,sort=80)
	public String getPreviousAccountTwo() {
		return previousAccountTwo;
	}
	public void setPreviousAccountTwo(String previousAccountTwo) {
		this.previousAccountTwo = previousAccountTwo;
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
