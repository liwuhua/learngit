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
import com.yjdj.view.core.mapper.IReportMapperKPI20;
import com.yjdj.view.core.service.IReportServiceKPI20;

/**
 * KPI-生产管理目标
 * @author 
 * create by 20162026
 *
 */
@Service
@TransactionConfiguration(defaultRollback = false)
@SuppressWarnings("all")
public class ReportServiceImpKPI20 implements IReportServiceKPI20{

	private final Logger log = Logger.getLogger(ReportServiceImpKPI20.class);
	
	@Autowired
	private IReportMapperKPI20 iReportMapperKPI20;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String getLedgercheck(String yearMonth) throws JsonProcessingException, IOException, DataAccessException {
		
		//存放年月的值13个月
		List<String> list = new ArrayList<String>();
		for(int i=12; i>=0; i--){ 
			list.add(evaluate(yearMonth,-i).substring(0, 6));
		}
		
		//将13个月的值  放在一个list 
		List resultList=new ArrayList<>();
		List dingdList=new ArrayList<>();
		List shougoList=new ArrayList<>();
		List kucunList=new ArrayList<>();
		List weiftList=new ArrayList<>();
		List zongList=new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		String resultJson = null;
		@SuppressWarnings("rawtypes")
		HashMap yearMonthMap = new HashMap();
		HashMap yearMonthMap1 = new HashMap();
		
		for(int i=0;i<list.size();i++){
			yearMonthMap.clear();
			yearMonthMap.put("MONTH", list.get(i));
			yearMonthMap.put("KEMU","订单在产金额 ");
			yearMonthMap.put("CZBS","1");
			Map ledgercheck = iReportMapperKPI20.getLedgercheck(yearMonthMap);
			//查询没有记录的  那么 金额为0
			Double ledgernum  =ledgercheck==null?0: Double.valueOf(ledgercheck.get("MONEY")+"");
			Map map = new HashMap<>();
			map.put("YEARMONTH", list.get(i));  
			map.put("KEMU", "订单在产金额 ");
			map.put("MONEY", ledgernum);
			shougoList.add(map);	
		}
		
		
		for(int i=0;i<list.size();i++){
			yearMonthMap.clear();
			yearMonthMap.put("MONTH", list.get(i));
			yearMonthMap.put("KEMU","库存金额");
			yearMonthMap.put("CZBS","2");
			Map ledgercheck = iReportMapperKPI20.getLedgercheck(yearMonthMap);
			//查询没有记录的  那么 金额为0
			Double ledgernum  =ledgercheck==null?0:Double.valueOf(ledgercheck.get("MONEY")+"");
			
			Map map = new HashMap<>();
			map.put("YEARMONTH", list.get(i));  
			map.put("KEMU", "库存金额");
			map.put("MONEY", ledgernum);
			kucunList.add(map);
		}
		
		
		for(int i=0;i<list.size();i++){
			yearMonthMap.clear();
			yearMonthMap.put("MONTH", list.get(i));
			yearMonthMap.put("CZBS","3");
			Map ledgercheck = iReportMapperKPI20.getLedgercheck(yearMonthMap);
			//查询没有记录的  那么 金额为0
			Double ledgernum  =ledgercheck==null?0:Double.valueOf(ledgercheck.get("MONEY")+"");
			Map map = new HashMap<>();
			map.put("YEARMONTH", list.get(i));  
			map.put("KEMU", "其中:供应商库存金额");
			map.put("MONEY", ledgernum);
			weiftList.add(map);	
		}
		
		for(int i=0;i<list.size();i++){
			yearMonthMap.clear();
			yearMonthMap.put("MONTH", list.get(i));
			yearMonthMap.put("KEMU","总帐与分类帐差异"); 
			yearMonthMap.put("CZBS","1");
			Map ledgercheck = iReportMapperKPI20.getLedgercheck(yearMonthMap);
			//查询没有记录的  那么 金额为0                 
			Double ledgernum  =ledgercheck==null?0: Double.valueOf(ledgercheck.get("MONEY")+"");
			Map map = new HashMap<>();
			map.put("YEARMONTH", list.get(i));  
			map.put("KEMU", "总帐与分类帐差异-库存");
			map.put("MONEY", ledgernum);
			dingdList.add(map);	
		
		}
		
		 for(int i=0;i<dingdList.size();i++){

				Map object1 = (Map)dingdList.get(i);
				Map object2 = (Map)shougoList.get(i);
				Map object3 = (Map)kucunList.get(i);
				Map object4 = (Map)weiftList.get(i);
				
             double a =(Double)object1.get("MONEY")+(Double)object2.get("MONEY")+
            		 (Double)object3.get("MONEY")+(Double)object4.get("MONEY");

			Map map = new HashMap<>();
			map.put("YEARMONTH", dingdList.get(i));  
			map.put("MONEY", a);
			map.put("KEMU", "合计");
			zongList.add(map);
		}

			resultList.add(shougoList);
			resultList.add(kucunList);
			resultList.add(weiftList);
			resultList.add(dingdList);
			resultList.add(zongList);
		 resultJson= mapper.writeValueAsString(resultList);
		 log.debug(resultJson+"resultJson!!! ");
		 return resultJson;
		
	}

	
		//日期计算，按月做相减
		private static String evaluate(String yearMonth,int monthdiff) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			String last = null;
			if (yearMonth.length() == 6)
				format = new SimpleDateFormat("yyyyMM");
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			Date thisb = null;
			try {
				thisb = format.parse(yearMonth);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			c.setTime(thisb);
			c.add(Calendar.MONTH, monthdiff);
			last = format.format(c.getTime())+"01";
			return last;
		}
}
