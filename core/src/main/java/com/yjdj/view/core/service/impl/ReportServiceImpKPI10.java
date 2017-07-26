package com.yjdj.view.core.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.IReportMapperKPI10;
import com.yjdj.view.core.service.IReportServiceKPI10;

/**
 * Created by liwuhua on 16/11/7.
 */

@Service
@TransactionConfiguration(defaultRollback = false)
@SuppressWarnings("all")
public class ReportServiceImpKPI10 implements IReportServiceKPI10{

    @Autowired
    private IReportMapperKPI10 ireportmapperkpi10;
    
    private final Logger log = Logger.getLogger(ReportServiceImpKPI10.class);
	
    
  //当月正点交付率  : 
    @Override
  	public String  punDeliRate(String dateYearMonth)throws DataAccessException, JsonProcessingException{
    	String json=null;
    	if(StringUtils.isNotEmpty(dateYearMonth)){
    		ObjectMapper objectMapper = new ObjectMapper();
        	HashMap  monthMap = new HashMap<>();
        	monthMap.put("dateYearMonth",dateYearMonth);
        	monthMap.put("dateYearMonth1",dateYearMonth+"01"); //通过日期获取每月的最后一天
//    		//先算出正点交付数量  然后将这个值 传入算出 	当月正点交付率
//        	double ONTIMEDELINUM=(double)ireportmapperkpi10.punDeliNum(monthMap).get("ONTIMEDELINUM");
//        	monthMap.put("ONTIMEDELINUM",ONTIMEDELINUM);
        	Map punDeliRate = ireportmapperkpi10.punDeliRate(monthMap);
        	if(punDeliRate==null){
        	    Map rate = new HashMap<>();
        	    rate.put("pundelirate", "0.00%");
        	    return objectMapper.writeValueAsString(rate);
        	}
        	
        	json = objectMapper.writeValueAsString(punDeliRate);
        	return json;
    	}
 		 return json;
  	}
  	
  	// 正点率第一层下钻
  	@Override
  	public String  firstLayer(String dateYearMonth)throws JsonProcessingException,DataAccessException{
    	String json=null;
    	
    	if(StringUtils.isNotEmpty(dateYearMonth)){
    		ObjectMapper objectMapper = new ObjectMapper();
        	HashMap  monthMap = new HashMap<>();
        	monthMap.put("dateYearMonth",dateYearMonth);
        	monthMap.put("dateYearMonth1",dateYearMonth+"01"); //通过日期获取每月的最后一天
//    		//先算出正点交付数量  然后将这个值 传入算出 	当月正点交付率
//        	double ONTIMEDELINUM=(double)ireportmapperkpi10.punDeliNum(monthMap).get("ONTIMEDELINUM");
//        	monthMap.put("ONTIMEDELINUM",ONTIMEDELINUM);
        	List<Map> firstLayer = ireportmapperkpi10.firstLayer(monthMap);
        	json = objectMapper.writeValueAsString(firstLayer);
        	return json;
    	}
 		 return json;
  	}
  	  
  	// 正点率第二层下钻
  	@Override
//  	public String  secondLayer(String vbtyp,String dateYearMonth,Integer startitem,Integer pageitem)throws JsonProcessingException,DataAccessException{
  		public String  secondLayer(String vbtypcode,String dateYearMonth)throws JsonProcessingException,DataAccessException{
    	String json=null;
    	
      //研发:1   工程化:2
    	if(StringUtils.isNotEmpty(vbtypcode)&&("1".equals(vbtypcode)||"2".equals(vbtypcode))&&StringUtils.isNotEmpty(dateYearMonth)){
    		ObjectMapper objectMapper = new ObjectMapper();
        	HashMap  monthMap = new HashMap<>();
        	monthMap.put("vbtypcode",vbtypcode);
        	monthMap.put("dateYearMonth",dateYearMonth);
        	monthMap.put("dateYearMonth1",dateYearMonth+"01"); //通过日期获取每月的最后一天
        	List<Map> secondLayer = ireportmapperkpi10.secondLayer(monthMap);
        	json = objectMapper.writeValueAsString(secondLayer);
        	return json;
    	}
 		 return json;
  	}
  	
  	// 正点率第三层下钻
  	@Override
//  	public String  thirdLayer(String vbtyp,String matnr,String dateYearMonth,Integer startitem,Integer pageitem)throws JsonProcessingException,DataAccessException{
  		public String  thirdLayer(String vbtypcode,String matnr,String dateYearMonth)throws JsonProcessingException,DataAccessException{
    	String json=null;
    	
    	//按照传过来的  产品类型下钻    VBTYP 用productType  接受
    	if(StringUtils.isNotEmpty(vbtypcode)&&StringUtils.isNotEmpty(matnr)&&StringUtils.isNotEmpty(dateYearMonth)){
    		ObjectMapper objectMapper = new ObjectMapper();
        	HashMap<Object, Object>  monthMap = new HashMap<>();
        	monthMap.put("vbtypcode",vbtypcode);
        	monthMap.put("MATNR",matnr);
        	monthMap.put("dateYearMonth",dateYearMonth);
        	monthMap.put("dateYearMonth1",dateYearMonth+"01"); //通过日期获取每月的最后一天
        	List<Map> thirdLayer = ireportmapperkpi10.thirdLayer(monthMap);
        	json = objectMapper.writeValueAsString(thirdLayer);
        	return json;
    	}
 		 return json;
  	}
  	

}
