package com.yjdj.view.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;



public interface IReportServiceKPI0609 {
	
	     //KPI-销售收入指标及完成情况 : 
		public String  incomeAndComple(String year)throws JsonProcessingException,DataAccessException;
		
		//销售收入实际下钻  下钻1层  
		public String  saleIncomeFiLayer(String year)throws JsonProcessingException,DataAccessException;
		  
		//点选某市场板块下钻至客户	
	  	public String  getcustoByKdgrp(String year,String kdgrp,String flag)throws JsonProcessingException,DataAccessException;
		
		//年度合同签订金额下钻 
		public String  anSignedLayer(String year)throws JsonProcessingException,DataAccessException;
		
		// 饼图
		public String  getRatePie(String year)throws JsonProcessingException,DataAccessException;
		
		
		// 饼图钻取获得业务部门
		public String  getBussiApart(String year,String znum)throws JsonProcessingException,DataAccessException;
		
		// 饼图饼图钻取通过业务部门获得客户金额和占比 
		public String  getCustByApart(String year,String znum,String vkbur)throws JsonProcessingException,DataAccessException;
		
}
