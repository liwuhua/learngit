package com.yjdj.view.core.entity.mybeans;

import com.yjdj.view.core.util.ExcelField;

public class InsertFieldTHM_FS16 {
	//项目	行次	本月金额	本年累计 上年同期累计
	private String itemNameOne;
	private String itemNoOne;
	private String monthMoneyOne;
	private String thisYearSumOne;
	private String lastYearSumOne;
	private String itemNameTwo;
	private String itemNoTwo;
	private String monthMoneyTwo;
	private String thisYearSumTwo;
	private String lastYearSumTwo;
	private String yearMonth;
	private String companyCode;
	
	
	@ExcelField(title = "项目",type=0,sort=10)
	public String getItemNameOne() {
		return itemNameOne;
	}
	public void setItemNameOne(String itemNameOne) {
		this.itemNameOne = itemNameOne;
	}
	@ExcelField(title = "行次",type=0,sort=20)
	public String getItemNoOne() {
		return itemNoOne;
	}
	public void setItemNoOne(String itemNoOne) {
		this.itemNoOne = itemNoOne;
	}
	
	@ExcelField(title = "本月金额",type=0,sort=30)
	public String getMonthMoneyOne() {
		return monthMoneyOne;
	}
	public void setMonthMoneyOne(String monthMoneyOne) {
		this.monthMoneyOne = monthMoneyOne;
	}
	@ExcelField(title = "本年累计",type=0,sort=40)
	public String getThisYearSumOne() {
		return thisYearSumOne;
	}
	public void setThisYearSumOne(String thisYearSumOne) {
		this.thisYearSumOne = thisYearSumOne;
	}
	@ExcelField(title = "上年同期累计",type=0,sort=50)
	public String getLastYearSumOne() {
		return lastYearSumOne;
	}
	public void setLastYearSumOne(String lastYearSumOne) {
		this.lastYearSumOne = lastYearSumOne;
	}
	@ExcelField(title = "项目",type=0,sort=60)
	public String getItemNameTwo() {
		return itemNameTwo;
	}
	public void setItemNameTwo(String itemNameTwo) {
		this.itemNameTwo = itemNameTwo;
	}
	@ExcelField(title = "行次",type=0,sort=70)
	public String getItemNoTwo() {
		return itemNoTwo;
	}
	public void setItemNoTwo(String itemNoTwo) {
		this.itemNoTwo = itemNoTwo;
	}
	@ExcelField(title = "本月金额",type=0,sort=80)
	public String getMonthMoneyTwo() {
		return monthMoneyTwo;
	}
	public void setMonthMoneyTwo(String monthMoneyTwo) {
		this.monthMoneyTwo = monthMoneyTwo;
	}
	@ExcelField(title = "本年累计",type=0,sort=90)
	public String getThisYearSumTwo() {
		return thisYearSumTwo;
	}
	public void setThisYearSumTwo(String thisYearSumTwo) {
		this.thisYearSumTwo = thisYearSumTwo;
	}
	@ExcelField(title = "上年同期累计",type=0,sort=100)
	public String getLastYearSumTwo() {
		return lastYearSumTwo;
	}
	public void setLastYearSumTwo(String lastYearSumTwo) {
		this.lastYearSumTwo = lastYearSumTwo;
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
