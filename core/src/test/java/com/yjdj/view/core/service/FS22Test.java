package com.yjdj.view.core.service;

import EDU.oswego.cs.dl.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.CarSalesCount;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS22_01;
import com.yjdj.view.core.entity.mybeans.THM_MSEG_06;
import com.yjdj.view.core.util.ExcelExportUtil22;
import com.yjdj.view.core.util.ExcelExportUtil24;

import org.apache.ibatis.reflection.wrapper.BaseWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by wangkai on 16/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS22Test {

    
    @Autowired
    private IReportServiceFS22 ireportservicefs22;

/*    * 项目号：aufnr
    * 查询日期：start_date，end_date
    * bukrs：公司代码
    * 以上组成查询条件
    * 
    * 以下是回显的字段值
    * 项目描述：ktext
    * 项目总预算：all_buget
    * 以前年度累计发生额：bef_years_cost
    * 当年项目预算：wtjhv
    * 当年累计发生额：curr_year_cost
    * 当期发生额：peroid_cost
    * 其他：qita以上数据封成一个QueryBean，多条记录封装成多个，
    * 返回多条明细记录的json字符串*/
//
    @Test
    public void deStatistics() throws Exception, ParseException{
    	ObjectMapper objectMapper = new ObjectMapper();
    	QueryBean queryBean = new QueryBean();
    	queryBean.setDateYearMonthStart("201601");
    	queryBean.setDateYearMonthEnd("201612");
    	queryBean.setStartitem(0);
    	queryBean.setCompCodeValue(Arrays.asList("5003"));
    	queryBean.setOrderID(Arrays.asList("A524140102"));
    	 //String listFs12 = ireportservicefs22.getListFsSecondDetail22(bean);
    	 //System.out.println(listFs12+"----------");
    	
    	/*List<String> list = new ArrayList<String>();
    	list.add("aufnr:A524151502#start_date:201601#end_date:201610#bukrs:5003#ktext:香港电车永磁牵引电机研制#"
    			+ "all_buget:1521922.94#bef_years_cost:1521922.94#wtjhv:0.0#curr_year_cost:0.0#peroid_cost:0.0#qita:0.0");
    	
    	list.add("aufnr:A524150702#start_date:201601#end_date:201610#bukrs:5003#ktext:香港电车永磁牵引电机研制#"
    			+ "all_buget:100.00#bef_years_cost:1521922.94#wtjhv:0.0#curr_year_cost:0.0#peroid_cost:0.0#qita:0.0");
    	
    	String listFs12 = ireportservicefs22.getListFsDetail22(list);
    	System.out.println(listFs12+"----------");*/
    	 
    	 //String listInnerOrder = ireportservicefs22.getInnerOrder("");
    	 //System.out.println(" listInnerOrder "+listInnerOrder);
    	/*String listFs12 = ireportservicefs22.getListFsSecondDetail22(queryBean);
    	
    	System.out.println(" listFs12 =================== "+listFs12);*/
    	 
         /*ExcelExportUtil22 ee = ireportservicefs22.exportExcel(queryBean,1);

         Random a= new Random();
         int i = a.nextInt();
         ee.writeFile("C://Users//Administrator//Desktop//永济项目组//export"+i+".xlsx");
         ee.dispose();
         System.out.println("Export success.");*/
       
      /* List<String> comCodeList = Arrays.asList("5003","5004","5005","5006","5026","5142");
       Object a= ireportservicefs22.importData("F://Media//研发费用明细表(1).xlsx", comCodeList);
    	
    	System.out.println(a.toString());*/
    	
    	
	   /* List<THM_FS22_01> listFs12 = ireportservicefs22.getListFsFirstDetail(queryBean);
	    
	    for (THM_FS22_01 thm: listFs12) {
			System.out.println("测试  ================="+thm);
		}*/
    	String listFs12 = ireportservicefs22.returnSum(queryBean,2);
    	//String listFs12 = ireportservicefs22.getListFsSecondDetail22(queryBean);
    	System.out.println(" listFs12 =================== "+listFs12);
    }
  
}
