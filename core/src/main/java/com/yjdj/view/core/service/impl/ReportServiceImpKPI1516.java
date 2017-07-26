package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.THM_KIP1516;
import com.yjdj.view.core.entity.mybeans.THM_KIP1516_01;
import com.yjdj.view.core.mapper.IReportMapperKPI1516;
import com.yjdj.view.core.service.IReportServiceKPI1516;

/**
 * KPI-采购仓储管理目标
 * @author liuchengli
 * create by 20161226
 *
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpKPI1516 implements IReportServiceKPI1516{

	private final Logger log = Logger.getLogger(ReportServiceImpKPI1516.class);
	
	@Autowired
	private IReportMapperKPI1516 iReportMapperKPI1516;

	@Override
	public String getMTMandOIPM(String time) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		Map<String,Object> resultMapMaterialTotalMoney = new HashMap<String, Object>();
		List<Object> resultMaterialMoney = new ArrayList<Object>();
		
		Map<String,Object> resultMapMaterialDay = new HashMap<String, Object>();
		List<Object> resultMaterialDay = new ArrayList<Object>();
		for(int i=13; i>=0; i--){
			list.add(evaluate(time,-i));
		}
		//订单在产金额
		List<THM_KIP1516> listMaterialMoney = iReportMapperKPI1516.selectOrderInProductionMoney(
				list.get(1), list.get(2), 
				list.get(3), list.get(4), 
				list.get(5), list.get(6), 
				list.get(7), list.get(8), 
				list.get(9), list.get(10), 
				list.get(11), list.get(12),
				list.get(13));
		for(THM_KIP1516 value:listMaterialMoney){
			//EKGRP,TXTMD,RATIO_COUNT
			resultMapMaterialTotalMoney.put("balance", value.getBalance());
			resultMapMaterialTotalMoney.put("time", value.getTime());
			resultMaterialMoney.add(resultMapMaterialTotalMoney);
			resultMapMaterialTotalMoney = new HashMap<String, Object>();
		}
		//在产资金周转天数
		List<THM_KIP1516> listMaterialDays = iReportMapperKPI1516.selectInProductionMoneyDays(
			    list.get(1),list.get(2),
			    list.get(3),list.get(4),
			    list.get(5),list.get(6),
			    list.get(7),list.get(8),
			    list.get(9),list.get(10),
			    list.get(11),list.get(12),
			    list.get(13));
		
		for(THM_KIP1516 value:listMaterialDays){
			//EKGRP,TXTMD,RATIO_COUNT
			resultMapMaterialDay.put("days", value.getDays());
			resultMapMaterialDay.put("time", value.getTime());
			resultMaterialDay.add(resultMapMaterialDay);
			resultMapMaterialDay = new HashMap<String, Object>();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("productMoney", resultMaterialMoney);
		mapTmp.put("turnDays", resultMaterialDay);
		String json="";
		try {
			json = mapper.writeValueAsString(mapTmp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	private static String evaluate(String thisdate,int months) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String last = null;
		if (thisdate.length() == 6)
			format = new SimpleDateFormat("yyyyMM");
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		Date thisb = null;
		try {
			thisb = format.parse(thisdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(thisb);
		c.add(Calendar.MONTH, months);
		last = format.format(c.getTime())+"01";
		return last;
	}

	@Override
	public String getFirstDetail(String time) {
		THM_KIP1516_01 result =  iReportMapperKPI1516.getFirstDetail(time);
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("firstDetail", result);
		String json="";
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String getSecondWorkShopDetail(String time) {
		List<THM_KIP1516_01> resultWDT =  iReportMapperKPI1516.getWuDongTaiDetail(time);
		List<THM_KIP1516_01> resultOrder =  iReportMapperKPI1516.getOrderDetail(time);
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("secondWorkShopDetail", resultWDT);
		map.put("secondOrderDetail", resultOrder);
		String json="";
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}
   
	
	/**
	 * 按评估类别下钻
	 */
	@Override
	public String getVClassShopDetail(String time) {
		List<THM_KIP1516_01> result =  iReportMapperKPI1516.getVClassShopDetail(time);
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("vclasstDetail", result);
		String json="";
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String getOrderDetail(String time) {
		List<THM_KIP1516_01> result =  iReportMapperKPI1516.getOrderDetail(time);
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderTypeDetail", result);
		String json="";
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}
	
	

}
