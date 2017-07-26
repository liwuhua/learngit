package com.yjdj.view.core.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.CarSalesCount;
import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS24;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS24_01;
import com.yjdj.view.core.entity.mybeans.THM_FS24_Result;
import com.yjdj.view.core.mapper.IReportMapperFS24;
import com.yjdj.view.core.mapper.IReportMapperFS26;
import com.yjdj.view.core.mapper.IReportMapperFS28;
import com.yjdj.view.core.util.ExcelExportUtil24;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.reflection.wrapper.BaseWrapper;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangkai on 16/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS24Test {

    
    @Autowired
    private IReportServiceFS24 ireportservicefs24;
    @Autowired
    private IReportMapperFS24 ireportmapper24;
    
    @Autowired
    private IReportServiceFS26 ireportservicefs26;
    @Autowired
    private IReportMapperFS26 ireportmapper26;
    
    @Autowired
    private IReportServiceFS28 ireportservicefs28;
    @Autowired
    private IReportMapperFS28 ireportmapper28;


    @Test
    public void deStatistics() throws Exception, ParseException{
    	ObjectMapper objectMapper = new ObjectMapper();
    	QueryBean queryBean = new QueryBean();
    	queryBean.setDateYearMonthStart("201701");
    	queryBean.setDateYearMonthEnd("201703");
    	
    	
    	//queryBean.setCompCodeValue(Arrays.asList("5003"));
    	queryBean.setCompCodeValue(Arrays.asList("5003","5004","5005","5006","5500","5025","5026","5142"));
    	//queryBean.setCompCodeValue(Arrays.asList("5500"));
    	
    	//ireportservicefs24.init();
    	
    	/*List<THM_FS24_01> result =  ireportservicefs24.getFirstDetail(queryBean);*/
     	/*List<THM_FS24_Result>result =  ireportservicefs24.getFinalDetail(queryBean,2);
    	for (THM_FS24_Result thm_FS24_01 : result) {
    		System.out.println("---AAA--"+thm_FS24_01);
		}*/
		
    		
    		
   		
   		 /*  String title="销售费用统计分析表";
        	
        	String bukrs="编制单位:";
        	String time="201601-201605";
        	String secondTitle=bukrs+"###"+time;
        	
            List<String> headerList = Lists.newArrayList();
            headerList.add("项目");
            headerList.add("行次");
            headerList.add("内部订单");
            headerList.add("描述");
            headerList.add("上年发生额");
            headerList.add("本年计划");
            headerList.add("本期计划");
            headerList.add("本期发生额");
            headerList.add("执行率");


            ExcelExportUtil24 ee = ireportservicefs24.exportExcel(queryBean,2);

            Random a= new Random();
            int i = a.nextInt();
            ee.writeFile("C://Users//Administrator//Desktop//永济项目组//export"+i+".xlsx");
            ee.dispose();
            System.out.println("Export success.");*/
    	/*List<InsertFieldTHM_FS24> list= (List<InsertFieldTHM_FS24>) ireportservicefs24.importData("C://Users//Administrator//Desktop//永济项目组//export-2079956727.xlsx");
        for (InsertFieldTHM_FS24 insertFieldTHM_FS24 : list) {
        	System.out.println("insertFieldTHM_FS24  =====   "+insertFieldTHM_FS24.toString());
		}*/
        /*List<String> comCodeList = Arrays.asList("5003","5004","5005","5006","5500","5025","5026","5142");
    	Object a= ireportservicefs26.importData("C://Users//Administrator//Desktop//永济项目组//export-789434775.xlsx",comCodeList);
    	
    	System.out.println(a.toString());*/
    	
    	
    	
    	List<THM_FS24_Result> result =  ireportservicefs28.getFinalDetail(queryBean,2);
    	for (THM_FS24_Result thm_FS24_01 : result) {
    		System.out.println("---AAA--"+thm_FS24_01);
        }
    	/*String a =  ireportservicefs28.returnFinalResult(queryBean,2);
    	System.out.println(a);*/
    	
    	
    	
    }
    
   
  
}
