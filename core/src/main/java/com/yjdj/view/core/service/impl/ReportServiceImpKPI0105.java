package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.yjdj.view.core.entity.mybeans.THM_FS22_01;
import com.yjdj.view.core.entity.mybeans.THM_KPI_0105_01;
import com.yjdj.view.core.entity.mybeans.THM_KPI_0105_02;
import com.yjdj.view.core.entity.mybeans.THM_KPI_0105_03;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperKPI0105;
import com.yjdj.view.core.service.IReportServiceKPI0105;
import com.yjdj.view.core.util.DateUtils;

/**
 * Created by liuchengli on 16/11/27.
 * 
 * 管理驾驶舱
 * 
 * 
 * 
 * 
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpKPI0105 implements IReportServiceKPI0105{

    @Autowired
    private IReportMapperKPI0105 ireportmapperkpi01;
    
    @Autowired
    private GenericMapper genericMapper;
    private final Logger log = Logger.getLogger(ReportServiceImpKPI0105.class);
    /**
     * 营业收入金额（当年累计）
     * 净利润金额（当年累计）
     */
	@Override
	public String getYearAll(QueryBean queryBean) throws JsonProcessingException {
		
		THM_KPI_0105_03 yearPlan= getYearPlan(queryBean);
		
		String enddate= queryBean.getDateYearMonthStart();
		String startdate = enddate.substring(0,4)+"01";
		HashMap<String,String> map = new HashMap<String, String>();
		
		
//		String latestDate = "";
//		/**
//		 * 查询当前系统中外部公司表485(THM_KPI0105_03) 最新录入记录的日期,
//		 * 并且和enddate比较，如果小于enddate,那么将返回前端，给出提示
//		 */
//		latestDate = getLatestDate().get("latestYM").toString();
//
//
//		map.put("startdate",startdate);
//		THM_KPI_0105_01 all = new THM_KPI_0105_01();
//
//		if(Integer.valueOf(latestDate)<Integer.valueOf(enddate)){
//			map.put("enddate",latestDate);
//        }else{
//        	map.put("enddate",enddate);
//        }

		map.put("startdate",startdate);
		map.put("enddate",enddate);
		THM_KPI_0105_01 all = new THM_KPI_0105_01();
		
		    all = ireportmapperkpi01.getYearAll(map);
		
 		    ObjectMapper mapper = new ObjectMapper();
	        String resultJson = "";
            HashMap resultMap=new HashMap<>(); //结果集  转换成json格式 
			resultMap.put("yearAll", all);
			resultMap.put("yearPlan", yearPlan);
//			if(Integer.valueOf(enddate)>Integer.valueOf(latestDate)){
//				resultMap.put("latestdate",latestDate);
//			}else{
//				resultMap.put("latestdate","");
//			}
		resultMap.put("latestdate",enddate);
            resultJson = mapper.writeValueAsString(resultMap);
		    return resultJson;
	}
	
	
	  /**
     * 营业收入金额（当年累计）
     * 净利润金额（当年累计）
     * 按公司一级钻取
     */
	@Override
	public String getYearAllBukrs(QueryBean queryBean) throws JsonProcessingException{

		String enddate= queryBean.getDateYearMonthStart();

		String startdate = enddate.substring(0,4)+"01";
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("startdate",startdate);
		map.put("enddate",enddate);
		
//		String latestDate = "";
//		/**
//		 * 查询当前系统中外部公司表485(THM_KPI0105_03) 最新录入记录的日期,
//		 * 并且和enddate比较，如果小于enddate,那么将返回前端，给出提示
//		 */
//		latestDate = getLatestDate().get("latestYM").toString();
//
//
//
//		if(Integer.valueOf(latestDate)<Integer.valueOf(enddate)){
//			map.put("enddate",latestDate);
//        }else{
//        	map.put("enddate",enddate);
//        }
		
		List<THM_KPI_0105_01> all = ireportmapperkpi01.getYearAllBukrs(map);
		
		  ObjectMapper mapper = new ObjectMapper();
	        String resultJson = "";
            HashMap resultMap=new HashMap<>(); //结果集  转换成json格式 
			resultMap.put("yearAllBukrs", all);
            resultJson = mapper.writeValueAsString(resultMap);
            
		    return resultJson;
	}
	
	
	/**
	 * 应收账款，本期新增，本期回款  
	 */
	@Override
	public String getDueTo(QueryBean queryBean) {
		//获取时间必选
    	String  enddate= queryBean.getDateYearMonthStart();
    	
    	//去年同期
    	String  startdate=(Integer.valueOf(enddate.substring(0,4))-1)+enddate.substring(4,6);
    	
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("startdate",startdate);
		map.put("enddate",startdate); 
		
		
        Date date =null;
		
		try {
			date = (new SimpleDateFormat("yyyyMM")).parse(startdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
        SimpleDateFormat s = (new SimpleDateFormat("yyyyMM"));
		
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		LinkedHashMap<String,THM_KPI_0105_02> dateList = new LinkedHashMap<String,THM_KPI_0105_02>();
		
		
		//计算去年同期
		THM_KPI_0105_02 first = ireportmapperkpi01.getDueTo(map).get(0);
		double badLoanF = getBadAccountValue(startdate);
		first.setDmbtr_sh(first.getDmbtr_sh()-badLoanF);
		first.setDmbtr_sh_all(first.getDmbtr_sh_all());
		first.setS_date(startdate);
		dateList.put(startdate, first);
		
		/**
		 * 计算每一个月的值
		 * */
		HashMap<String,String> map1 = new HashMap<String,String>();
		List<THM_KPI_0105_02> allDueto = new ArrayList<THM_KPI_0105_02>();
		
		
		for (int i = 1; i <= 12; i++) {
			ca.add(Calendar.MONTH,1);
			THM_KPI_0105_02 kpi = new THM_KPI_0105_02();
			String dateKpi =  s.format(ca.getTime());
			kpi.setS_date(dateKpi);
			dateList.put(dateKpi, kpi);
			
			map1.put("enddate",dateKpi);
			
			allDueto.add(ireportmapperkpi01.getDueTo(map1).get(0));
			map1.clear();
		}
		
		
		for (int i = 0; i < allDueto.size(); i++) {
			THM_KPI_0105_02 thm = allDueto.get(i);
			String  dateStr = thm.getS_date();
			double badLoan = getBadAccountValue(dateStr);
			thm.setDmbtr_sh(thm.getDmbtr_sh()-badLoan);
			thm.setDmbtr_sh_all(thm.getDmbtr_sh_all());
			dateList.put(dateStr, thm);
		}
			
		
		List<THM_KPI_0105_02> result = new ArrayList<THM_KPI_0105_02>();
		for (String key : dateList.keySet()) {
			result.add(dateList.get(key));
		}
		
		  ObjectMapper mapper = new ObjectMapper();
	        String resultJson = "";
            HashMap resultMap=new HashMap<>(); //结果集  转换成json格式 
			resultMap.put("allDueto",result);
			
			
			String latestDate = "";
			/**
			 * 查询当前系统中外部公司表485(THM_KPI0105_03) 最新录入记录的日期,
			 * 并且和enddate比较，如果小于enddate,那么将返回前端，给出提示
			 */
			latestDate = getLatestDate().get("latestYM").toString();
			
			if(Integer.valueOf(enddate)>Integer.valueOf(latestDate)){
				resultMap.put("latestdate",latestDate);
			}else{
				resultMap.put("latestdate","");
			}
			
			/**
			 * 
			 */
          try {
			resultJson = mapper.writeValueAsString(resultMap);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return resultJson;
		
	}
 
    
	@Override
	public List<THM_KPI_0105_02> getFirstBukrsType(QueryBean queryBean) {
		//获取时间必选,具体年月
    	String startdate = queryBean.getDateYearMonthStart();
    	 
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("startdate",startdate);
		map.put("enddate",startdate);
		map.putAll(accountAge(startdate));
		
		List<THM_KPI_0105_02> bukrsDueto = ireportmapperkpi01.getFirstBukrsType(map);
		
		return bukrsDueto;
        
	}

	
	@Override
	public List<THM_KPI_0105_02> getHYFirstBukrsType(QueryBean queryBean) {
		//获取时间必选,具体年月
    	String startdate = queryBean.getDateYearMonthStart();
    	 
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("startdate",startdate);
		map.put("enddate",startdate);
		
		ObjectMapper mapper = new ObjectMapper();
	    String resultJson = "";
		List<THM_KPI_0105_02> hybukrsDueto = ireportmapperkpi01.getHYFirstBukrsType(map);
		
        return hybukrsDueto;
			
	}

	@Override
	public String getSecondBukrsType(QueryBean queryBean) {
		//获取时间必选,具体年月
    	String startdate = queryBean.getDateYearMonthStart();
    	List<String> compValues  = queryBean.getCompCodeValue();
    	String bukrs = null;//公司代码
    	if(compValues!=null&&compValues.size()>0){
    		bukrs = compValues.get(0);
    	}
    	 
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("startdate",startdate);
		map.put("enddate",startdate);
		map.putAll(accountAge(startdate));
		map.put("bukrs",bukrs);
		
		List<THM_KPI_0105_02> bukrsSecondDueto = ireportmapperkpi01.getSecondBukrsType(map);
		
		  ObjectMapper mapper = new ObjectMapper();
	        String resultJson = "";
            HashMap resultMap=new HashMap<>(); //结果集  转换成json格式 
			resultMap.put("bukrsSecondDueto",bukrsSecondDueto);
          try {
			resultJson = mapper.writeValueAsString(resultMap);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return resultJson;
	}


	@Override
	public List<THM_KPI_0105_02> getFirstCustType(QueryBean queryBean) {
		//获取时间必选,具体年月
    	String startdate = queryBean.getDateYearMonthStart();
    	 
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("startdate",startdate);
		map.put("enddate",startdate);
		map.putAll(accountAge(startdate));
		
		
		List<THM_KPI_0105_02> custDueto = ireportmapperkpi01.getFirstCustType(map);
		
		return custDueto;
		
	}


	@Override
	public String getSecondCustType(QueryBean queryBean) {
		//获取时间必选,具体年月
    	String startdate = queryBean.getDateYearMonthStart();
    	
    	//获取客户类型
    	String cust_type = queryBean.getProductType();
    	 
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("startdate",startdate);
		map.put("enddate",startdate);
		map.putAll(accountAge(startdate));
		map.put("cust_type",cust_type);
		
		List<THM_KPI_0105_02> custDueto = ireportmapperkpi01.getSecondCustType(map);
		
		  ObjectMapper mapper = new ObjectMapper();
	        String resultJson = "";
            HashMap resultMap=new HashMap<>(); //结果集  转换成json格式 
			resultMap.put("custDueto",custDueto);
          try {
			resultJson = mapper.writeValueAsString(resultMap);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return resultJson;
	}
    
	
	/***
	 * 将 按公司一级钻取和按客户类型的一级钻取合并返回
	 */
	@Override
	public String getFirstType(QueryBean queryBean) {
		
		List<THM_KPI_0105_02>	bukrsDueto = getFirstBukrsType(queryBean);
		List<THM_KPI_0105_02>	custDueto = getFirstCustType(queryBean);
		List<THM_KPI_0105_02>	hybukrsDueto =	getHYFirstBukrsType(queryBean) ;
		
		//添加合计
		bukrsDueto.add(getFirstSum(bukrsDueto, 1));
		custDueto.add(getFirstSum(custDueto, 1));
		hybukrsDueto.add(getFirstSum(hybukrsDueto, 2));
		// TODO Auto-generated method stub
		 ObjectMapper mapper = new ObjectMapper();
	        String resultJson = "";
            HashMap resultMap=new HashMap<>(); //结果集  转换成json格式 
			resultMap.put("bukrsDueto",bukrsDueto);
			resultMap.put("custDueto",custDueto);
			resultMap.put("hybukrsDueto",hybukrsDueto);
         try {
			resultJson = mapper.writeValueAsString(resultMap);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       
		return resultJson;
	}


	@Override
	public THM_KPI_0105_03  getYearPlan(QueryBean queryBean) {
		//获取时间必选,具体年月
    	String startdate = queryBean.getDateYearMonthStart();
    	ObjectMapper mapper = new ObjectMapper();
        String resultJson = "";
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("year",startdate.substring(0,4));
		
		THM_KPI_0105_03 yearPlan = ireportmapperkpi01.getYearPlan(map);
		
		THM_KPI_0105_03 yearPlanResult = new THM_KPI_0105_03();
		
		
		double zt1 = 0.00;
		double zt2 = 0.00;
		double zt3 = 0.00;
		double zzc = 0.00;
		double zzcp = 0.00;
		double zsmx = 0.00;
		if(yearPlan!=null){
		 zt1 = yearPlan.getZt1();
		 zt2 = yearPlan.getZt2();
		 zt3 = yearPlan.getZt3();
		 zzc = yearPlan.getZzc();
		 zsmx= yearPlan.getZsmx();
		 zzcp = yearPlan.getZzcp();
		}
		double sellFirst=zzc;
		double sellAdjust=0.00;
		double sellMax=0.00;
		/*if(zt1==zzc){
		   sellAdjust=zt2;
		   
		}else if(zt2==zzc){
			sellAdjust=zt3;
		}else if(zt3==zzc){
			sellAdjust=new BigDecimal(zt3*(1+zzcp)).round(new MathContext(2)).doubleValue();
		}*/
		sellAdjust=zsmx;
		/**
		 * 重新调整最大值  
		 */
		 /*double  sell= sellAdjust;
	     int sellInt = (((int)sell)/10)*10;
	      sellMax = sellInt;
	     if(sellInt%20!=0){
	     while(sellMax%20!=0&&((sellMax-sellInt)<20)){
	    	 sellMax=sellMax+10;
	     }
	     }else{
	    	 sellMax=sellInt+20;
	     }*/
		
		sellMax=zsmx;
		
		double zlt1 = 0.00;
		double zlt2 = 0.00;
		double zlt3 = 0.00;
		double zlkh = 0.00;
		double zlkp = 0.00;
		double zlmx = 0.00;
		if(yearPlan!=null){
			 zlt1 = yearPlan.getZt1();
			 zlt2 = yearPlan.getZlt2();
			 zlt3 = yearPlan.getZlt3();
			 zlkh = yearPlan.getZlkh();
			 zlmx = yearPlan.getZlmx();
			 zlkp = yearPlan.getZlkp();
		}

		double profitfirst=zlkh;
		double profitAdjust=0.00;
		double profitMax=0.00;
	/*	if(zlt1==zlkh){
			profitAdjust=zlt2;
		}else if(zlt2==zlkh){
			profitAdjust=zlt3;
		}else if(zlt3==zlkh){
			profitAdjust=zlt3*(1+zlkp);
		}*/
		
		profitAdjust=zlmx;
		/**
		 * 重新调整最大值  
		 */
		 double  profit= profitAdjust;
	     int profitInt = (((int)profit)/6)*6;
	     profitMax = profitInt+6;
	   
		
		
		yearPlanResult.setSellFirst(sellFirst);
		yearPlanResult.setSellAdjust(sellAdjust);
		yearPlanResult.setSellMax(sellMax);
		yearPlanResult.setProfitfirst(profitfirst);
		yearPlanResult.setProfitAdjust(profitAdjust);
		yearPlanResult.setProfitMax(profitMax);
		
		
		return yearPlanResult;
		
	}


	@Override
	public HashMap getLatestDate()  {
		List<HashMap> hashList = ireportmapperkpi01.getLatestDate();
		
		HashMap map = hashList.get(0);
		return map;
	}

    /**
     * 应收月度总计坏账
     */
	@Override
	public double getBadAccountValue(String date) {
		
		 
        /**
         * 根据传入日期，生成九个查询日期
         */
        HashMap<String,String> map = accountAge(date);

        /**
         * 根据该日期生成的日期区间，计算该日期对应的坏账
         */

        List<HashMap> listMap = ireportmapperkpi01.getBadAccountValue(map);
        double badAcc=0.0;
        if(listMap!=null&&listMap.size()>0){
        		badAcc = Double.valueOf((listMap.get(0).get("badloan")).toString());
        }
		
		return badAcc;
}


	@Override
	public HashMap<String, String> accountAge(String date) {
		
		HashMap<String, String>  map = new HashMap<String, String>();
		try {
    	    date = DateUtils.getDateStr(date);
    	    map.put("date0",date);
    	    map.put("date1",DateUtils.getDayBeforeStr(date, 361));
	        map.put("date2",DateUtils.getDayBeforeStr(date,720));
	        
	        map.put("date3",DateUtils.getDayBeforeStr(date,721));
	        map.put("date4",DateUtils.getDayBeforeStr(date, 1080));
	        
	        map.put("date5",DateUtils.getDayBeforeStr(date, 1081));
	        map.put("date6",DateUtils.getDayBeforeStr(date, 1440));
	        
	        map.put("date7",DateUtils.getDayBeforeStr(date,1441));
	        map.put("date8",DateUtils.getDayBeforeStr(date,1800));
	        
	        
	        
	} catch (ParseException e) {
		e.printStackTrace();
	}
		
		return map;
	}


	@Override
	public THM_KPI_0105_02 getFirstSum(List<THM_KPI_0105_02> sum, int flag) {
		THM_KPI_0105_02 s = new THM_KPI_0105_02();
		
		//按公司自己下钻
		if(flag==1){
			double dmbtr_sh=0.0;
			double dmbtr_sh_all=0.0;
			double dmbtr_s=0.0;
			double dmbtr_h=0.0;
			double badloan=0.0;
			for(THM_KPI_0105_02 thm : sum) {
				dmbtr_sh+=thm.getDmbtr_sh();
				dmbtr_sh_all+=thm.getDmbtr_sh_all();
				dmbtr_s+=thm.getDmbtr_s();
				dmbtr_h+=thm.getDmbtr_h();
				badloan+=thm.getBadloan();
			}
			s.setDmbtr_sh(dmbtr_sh);
			s.setDmbtr_sh_all(dmbtr_sh_all);
			s.setDmbtr_s(dmbtr_s);
			s.setDmbtr_h(dmbtr_h);
			s.setBadloan(badloan);
			
			s.setComName("合计");
			s.setCust_type("合计");
			
		}else if(flag==2){//海外公司
			double hyys=0.0;
			double hyhz=0.0;
			double hyys_all=0.0;
			double dmbtr_s=0.0;
			double dmbtr_h=0.0;
			for(THM_KPI_0105_02 thm : sum) {
				hyys+=thm.getHyys();
				hyhz+=thm.getHyhz();
				hyys_all+=thm.getHyys_all();
				dmbtr_s+=thm.getDmbtr_s();
				dmbtr_h+=thm.getDmbtr_h();
			}
			s.setComName("合计");
			s.setHyys(hyys);
			s.setHyhz(hyhz);
			s.setHyys_all(hyys_all);
			s.setDmbtr_s(dmbtr_s);
			s.setDmbtr_h(dmbtr_h);
		}
				
		
		return s;
	}


	

}
