package com.yjdj.view.core.service;

import java.text.ParseException;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_KPI_0105_02;



public interface IReportServiceKPI10 {
	
	    //当月正点交付率  :  
		public String  punDeliRate(String dateYearMonth)throws JsonProcessingException,DataAccessException;
		
		// 正点率第一层下钻
		public String  firstLayer(String dateYearMonth)throws JsonProcessingException,DataAccessException;
		  
		// 正点率第二层下钻
		//public String  secondLayer(String vbtyp,String dateYearMonth,Integer startitem,Integer pageitem)throws JsonProcessingException,DataAccessException;
		public String  secondLayer(String vbtyp,String dateYearMonth)throws JsonProcessingException,DataAccessException;
		
		// 正点率第三层下钻
       //public String  thirdLayer (String vbtyp,String matnr,String dateYearMonth,Integer startitem,Integer pageitem)throws JsonProcessingException,DataAccessException;
        public String  thirdLayer (String vbtyp,String matnr,String dateYearMonth)throws JsonProcessingException,DataAccessException;
		
}
