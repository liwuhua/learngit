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
import com.yjdj.view.core.entity.mybeans.THM_KIP1721;
import com.yjdj.view.core.mapper.IReportMapperKPI1721;
import com.yjdj.view.core.service.IReportServiceKPI1721;

/**
 * KPI-采购仓储管理目标
 * @author zhangwenguo
 * create by 20161226
 *
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpKPI1721 implements IReportServiceKPI1721{

	/*占用总额：SUM_MONEY   
	仓库类型：STOCK_TYPE    
	物料：MATNR
	占用资金 ： SUM_MONEY
	过质保：QUALITYPASS*/
	private final Logger log = Logger.getLogger(ReportServiceImpKPI1721.class);
	
	@Autowired
	private IReportMapperKPI1721 iReportMapperKPI1721;
	/**
	 * 采购到货正点率
	 */
	@Override
	public Object getOrdersSumRate(String time) throws JsonProcessingException,
			IOException, DataAccessException {
		// TODO Auto-generated method stub
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String date = df.format(new Date());
        Map<String,Object> map = null;
		String json = null;
		if(time != null && !"".equals(time)){
			time = time+"01";
		}
		System.out.println(date.substring(0, 6));
		if(date.substring(1, 6).equals(time.substring(1, 6))){
			String time1 = time;
			String time2 = date;
			map = iReportMapperKPI1721.selectOrdersSumRateNow(time1, time2);
		}else{
			map = iReportMapperKPI1721.selectOrdersSumRate(time);
		}
		if(!map.isEmpty()){
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(map);
		}
		
		return json;
	}

	@Override
	public Object getEkgrpByRate(String label,String time) throws JsonProcessingException, IOException,
			DataAccessException {
		// TODO Auto-generated method stub
		String json = null;
		if(time != null && !"".equals(time)){
			time = time+"01";
		}
        int putRatio1 = 0, putRatio2 = 0;
        String[] parameter = label.split("and");
        if(parameter.length == 2){
        	putRatio1 = Integer.parseInt(parameter[0]);
        	putRatio2 = Integer.parseInt(parameter[1]);
        }else if(parameter.length == 1){
        	putRatio1 = Integer.parseInt(parameter[0]);
        }
        
		List<THM_KIP1721> list = null;
		if(putRatio2 ==0 ){
			list = iReportMapperKPI1721.selectEkgrpByRateSingle(time,putRatio1, putRatio2);
		}else{
			list = iReportMapperKPI1721.selectEkgrpByRate(time,putRatio1, putRatio2);
		}
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<Object> result = new ArrayList<Object>();
		for(THM_KIP1721 value:list){
			//EKGRP,TXTMD,RATIO_COUNT
			resultMap.put("EKGRP", value.getEkgrp());
			resultMap.put("TXTMD", value.getTxtmd());
			resultMap.put("ONTIME_COUNT", Double.valueOf(value.getOntime_count()));
			result.add(resultMap);
			resultMap = new HashMap<String, Object>();
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("DATA", result);
		json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		return json;
	}
	
	@Override
	public Object getLifnrRateAndEkgrpRate(String time) throws JsonProcessingException,
			IOException, DataAccessException {
		// TODO Auto-generated method stub
		String json = null;
		if(time != null && !"".equals(time)){
			time = time+"01";
		}
		List<THM_KIP1721> listLifnrRate = iReportMapperKPI1721.selectEkgrpGoDown(time);

		double resultCount20 = 0.00;
		double resultCount60 = 0.00;
		double resultCount80 = 0.00;
		double resultCount90 = 0.00;
		double resultCount100 = 0.00;
		double resultCount100_ = 0.00;
		
		for(THM_KIP1721 value:listLifnrRate){
			//EKGRP,TXTMD,ONTIME_COUNT,ORDER_COUNT,RATIO2,'>=0 and <20' AS LABLE
			if(value.getRate() != null){
				if(Double.valueOf(value.getRate()) >=0 && Double.valueOf(value.getRate()) < 20){
					resultCount20 += Double.valueOf(value.getOntime_count());
				}else if(Double.valueOf(value.getRate()) >=20 && Double.valueOf(value.getRate()) < 60){
					resultCount60 += Double.valueOf(value.getOntime_count());
				}else if(Double.valueOf(value.getRate()) >=60 && Double.valueOf(value.getRate()) < 80){
					resultCount80 += Double.valueOf(value.getOntime_count());
				}else if(Double.valueOf(value.getRate()) >=80 && Double.valueOf(value.getRate()) < 90){
					resultCount90 += Double.valueOf(value.getOntime_count());
				}else if(Double.valueOf(value.getRate()) >=90 && Double.valueOf(value.getRate()) < 100){
					resultCount100 += Double.valueOf(value.getOntime_count());
				}else if(Double.valueOf(value.getRate()) == 100){
					resultCount100_ += Double.valueOf(value.getOntime_count());
				}
			}
			
		}
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		map.put("1", resultCount20); //>=0 and <20
		map.put("2", resultCount60); //>=20 and <60
		map.put("3", resultCount80);  //>=60 and <80
		map.put("4", resultCount90);  //>=80 and <90
		map.put("5", resultCount100);  //>=90 and <100
		map.put("6", resultCount100_);  //=100
		resultMap.put("DATALIFNRRATE", map);
		
		List<THM_KIP1721> listLifnr = iReportMapperKPI1721.selectLifnrOnTimeRate(time);
		Map<String,Object> resultMapLifnr = new HashMap<String, Object>();
		List<Object> result = new ArrayList<Object>();
		for(THM_KIP1721 value:listLifnr){
			//LIFNR,TXTMD,RATIO1
			resultMapLifnr.put("LIFNR", value.getLifnr());
			resultMapLifnr.put("TXTMD", value.getTxtmd());
			resultMapLifnr.put("RATE", Double.valueOf(value.getRate()));
			result.add(resultMapLifnr);
			resultMapLifnr = new HashMap<String, Object>();
		}
		ObjectMapper mapper = new ObjectMapper();
		resultMap.put("DATAEKGRP", result);
		json = mapper.writeValueAsString(resultMap);
		log.info("+++++++++"+json);
		
		return json;
	}
	
	
	//日期计算，按月做相减
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
	//原材料资金占用额和周转天数
	@Override
	public Object getMaterial(String time) throws JsonProcessingException, IOException,
			DataAccessException {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		Map<String,Object> resultMapMaterialMoney = new HashMap<String, Object>();
		List<Object> resultMaterialMoney = new ArrayList<Object>();
		Map<String,Object> resultMapMaterialDay = new HashMap<String, Object>();
		List<Object> resultMaterialDay = new ArrayList<Object>();
		
		for(int i=12; i>=0; i--){
			list.add(evaluate(time,-i));
		}
		List<String> listTmp = new ArrayList<String>();
		for(int i=0; i<list.size(); i++){
			listTmp.add(list.get(i).substring(0, 6));
		}
		//原材料总金额
		List<THM_KIP1721> listMaterialMoney = iReportMapperKPI1721.selectMaterialMoney(
				list.get(0),list.get(1), list.get(2), list.get(3), list.get(4), 
				list.get(5), list.get(6), list.get(7), list.get(8), 
				list.get(9), list.get(10), list.get(11), list.get(12));
		List<THM_KIP1721> tmp = new ArrayList<THM_KIP1721>();
		THM_KIP1721 kpi = new THM_KIP1721();
		List<String> listYearMonth = new ArrayList<String>();
		for(THM_KIP1721 value:listMaterialMoney){
			listYearMonth.add(value.getYearmonth());
		}
		int num1 = 0;
		int num2 = 0;
		while(true){
			if(num1 == listTmp.size()){
				break;
			}
			if(listYearMonth.contains(listTmp.get(num1))){
				kpi.setStock_money(listMaterialMoney.get(num2).getStock_money());
				kpi.setYearmonth(listMaterialMoney.get(num2).getYearmonth());
				num2++;
			}else{
				kpi.setStock_money(null);
				kpi.setYearmonth(listTmp.get(num1));
			}
			num1++;
			tmp.add(kpi);
			kpi = new THM_KIP1721();
		}
		/*for(int i=0; i < listTmp.size(); i++){
			if(listYearMonth.contains(listTmp.get(i))){
				kpi.setStock_money(listMaterialMoney.get(i).getStock_money());
				kpi.setYearmonth(listMaterialMoney.get(i).getYearmonth());
			}else{
				kpi.setStock_money(null);
				kpi.setYearmonth(listTmp.get(i));
			}
			tmp.add(kpi);
			kpi = new THM_KIP1721();
		}*/
		for(THM_KIP1721 value:tmp){
			//EKGRP,TXTMD,RATIO_COUNT
			resultMapMaterialMoney.put("STOCK_MONEY",value.getStock_money());
			resultMapMaterialMoney.put("YEARMONTH", value.getYearmonth());
			resultMaterialMoney.add(resultMapMaterialMoney);
			resultMapMaterialMoney = new HashMap<String, Object>();
		}
		
		List<THM_KIP1721> listMaterialDays = iReportMapperKPI1721.selectMaterialDays(
				list.get(0), list.get(1), list.get(2), list.get(3), 
				list.get(4), list.get(5), list.get(6), list.get(7), 
				list.get(8), list.get(9), list.get(10), list.get(11),
				list.get(12));
		listYearMonth = new ArrayList<String>();
		for(THM_KIP1721 value:listMaterialDays){
			listYearMonth.add(value.getTime());
		}
		tmp = new ArrayList<THM_KIP1721>();
		kpi = new THM_KIP1721();
		for(int i=0; i < listTmp.size(); i++){
			if(listYearMonth.contains(listTmp.get(i))){
				kpi.setDays(listMaterialDays.get(i).getDays());
				kpi.setTime(listMaterialDays.get(i).getTime());
			}else{
				kpi.setDays(null);
				kpi.setTime(listTmp.get(i));
			}
			tmp.add(kpi);
			kpi = new THM_KIP1721();
		}
		for(THM_KIP1721 value:listMaterialDays){
			//EKGRP,TXTMD,RATIO_COUNT
			if(value.getDays() != null){
				resultMapMaterialDay.put("DAYS", Double.valueOf(value.getDays()));
			}else{
				resultMapMaterialDay.put("DAYS", value.getDays());
			}
			resultMapMaterialDay.put("TIME", value.getTime());
			resultMaterialDay.add(resultMapMaterialDay);
			resultMapMaterialDay = new HashMap<String, Object>();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("MATERIALMONEY", resultMaterialMoney);
		mapTmp.put("MATERIALDAYS", resultMaterialDay);
		map.put("DATA", mapTmp);
		String json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		
		return json;
	}
	//产成品资金占用额和周转天数
	@Override
	public Object getProduct(String time) throws JsonProcessingException, IOException,
			DataAccessException {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		Map<String,Object> resultMapProductMoney = new HashMap<String, Object>();
		List<Object> resultProductMoney = new ArrayList<Object>();
		
		Map<String,Object> resultMapProductDay = new HashMap<String, Object>();
		List<Object> resultProductDay = new ArrayList<Object>();
		for(int i=12; i>=0; i--){
			list.add(evaluate(time,-i));
		}
		//产成品总金额
		List<THM_KIP1721> listProduct = iReportMapperKPI1721.selectProductMoney(
				list.get(0),list.get(1), list.get(2), list.get(3), list.get(4), 
				list.get(5), list.get(6), list.get(7), list.get(8), 
				list.get(9), list.get(10), list.get(11), list.get(12));
		for(THM_KIP1721 value:listProduct){
			//EKGRP,TXTMD,RATIO_COUNT
			if(value.getStock_money() != null){
				resultMapProductMoney.put("STOCK_MONEY", Double.valueOf(value.getStock_money()));
			}else{
				resultMapProductMoney.put("STOCK_MONEY", value.getStock_money());
			}
			resultMapProductMoney.put("YEARMONTH", value.getYearmonth());
			resultProductMoney.add(resultMapProductMoney);
			resultMapProductMoney = new HashMap<String, Object>();
		}
		
		List<THM_KIP1721> listMaterialDays = iReportMapperKPI1721.selectProductDays(
				list.get(0), list.get(1), list.get(2), list.get(3), 
				list.get(4), list.get(5), list.get(6), list.get(7), 
				list.get(8), list.get(9), list.get(10), list.get(11),
				list.get(12));
		for(THM_KIP1721 value:listMaterialDays){
			//EKGRP,TXTMD,RATIO_COUNT
			if(value.getDays() != null){
				resultMapProductDay.put("DAYS", Double.valueOf(value.getDays()));
			}else{
				resultMapProductDay.put("DAYS", value.getDays());
			}
			resultMapProductDay.put("TIME", value.getTime());
			resultProductDay.add(resultMapProductDay);
			resultMapProductDay = new HashMap<String, Object>();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("PRODUCTMONEY", resultProductMoney);
		mapTmp.put("PRODUCTDAYS", resultProductDay);
		map.put("DATA", mapTmp);
		String json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		
		return json;
	}
	
	//原材料评估类下钻
	@Override
	public Object getMaterialGoDown(String time,String flag)throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		Map<String,Object> resultMapMaterialGoDown = new HashMap<String, Object>();
		List<Object> resultMaterialGoDown = new ArrayList<Object>();
		Map<String,Object> resultMapDynamicGoDown = new HashMap<String, Object>();
		List<Object> resultDynamicGoDown = new ArrayList<Object>();
		//库存资金占用 下钻(评估类下钻)
		List<THM_KIP1721> listMaterialGoDown = iReportMapperKPI1721.selectMaterialGoDown(time,flag);
		for(THM_KIP1721 value:listMaterialGoDown){
			//EKGRP,TXTMD,RATIO_COUNT
			if(value.getStock_money() != null){
				resultMapMaterialGoDown.put("STOCK_MONEY", Double.valueOf(value.getStock_money()));
			}else{
				resultMapMaterialGoDown.put("STOCK_MONEY", value.getStock_money());
			}
			resultMapMaterialGoDown.put("TXTMD", value.getTxtmd());
			resultMapMaterialGoDown.put("VAL_CLASS", value.getVal_class());
			resultMaterialGoDown.add(resultMapMaterialGoDown);
			resultMapMaterialGoDown = new HashMap<String, Object>();
		}
		//库存资金占用 下钻 , 无动态下钻
		List<THM_KIP1721> listDynamicGoDown = iReportMapperKPI1721.selectMaterialDynamicGoDown(time);
		for(THM_KIP1721 value:listDynamicGoDown){
			//TXTMD,NODYNAMIC_MONNEY,HALFTOONE_YEAR_MONEY,ONETOTWO_YEAR_MONEY,MORETHANTWO_YEAR_MONEY 
			resultMapDynamicGoDown.put("TXTMD", value.getTxtmd());
			resultMapDynamicGoDown.put("NODYNAMIC_MONNEY", Double.valueOf(value.getNodynamic_money()));
			resultMapDynamicGoDown.put("HALFTOONE_YEAR_MONEY", Double.valueOf(value.getHalftoone_year_money()));
			resultMapDynamicGoDown.put("ONETOTWO_YEAR_MONEY", Double.valueOf(value.getOnetotwo_year_money()));
			resultMapDynamicGoDown.put("MORETHANTWO_YEAR_MONEY", Double.valueOf(value.getMorethantwo_year_money()));
			resultDynamicGoDown.add(resultMapDynamicGoDown);
			resultMapDynamicGoDown = new HashMap<String, Object>();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("MATERIALGODOWN", resultMaterialGoDown);
		mapTmp.put("DYNAMICGODOWN", resultDynamicGoDown);
		map.put("DATA", mapTmp);
		String json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		
		return json;
	}
	//产成品无动态下钻
	@Override
	public Object getProductDynamicGoDown(String time)throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		Map<String,Object> resultMapDynamicGoDown = new HashMap<String, Object>();
		List<Object> resultDynamicGoDown = new ArrayList<Object>();
		//库存资金占用 下钻 , 无动态下钻
		List<THM_KIP1721> listDynamicGoDown = iReportMapperKPI1721.selectProductDynamicGoDown(time);
		for(THM_KIP1721 value:listDynamicGoDown){
			//TXTMD,NODYNAMIC_MONNEY,HALFTOONE_YEAR_MONEY,ONETOTWO_YEAR_MONEY,MORETHANTWO_YEAR_MONEY 
			resultMapDynamicGoDown.put("TXTMD", value.getTxtmd());
			resultMapDynamicGoDown.put("NODYNAMIC_MONNEY", Double.valueOf(value.getNodynamic_money()));
			resultMapDynamicGoDown.put("HALFTOONE_YEAR_MONEY", Double.valueOf(value.getHalftoone_year_money()));
			resultMapDynamicGoDown.put("ONETOTWO_YEAR_MONEY", Double.valueOf(value.getOnetotwo_year_money()));
			resultMapDynamicGoDown.put("MORETHANTWO_YEAR_MONEY", Double.valueOf(value.getMorethantwo_year_money()));
			resultDynamicGoDown.add(resultMapDynamicGoDown);
			resultMapDynamicGoDown = new HashMap<String, Object>();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("DYNAMICGODOWN", resultDynamicGoDown);
		map.put("DATA", mapTmp);
		String json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		
		return json;
	}
	

}
