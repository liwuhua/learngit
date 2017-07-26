package com.yjdj.view.core.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.impl.ReportServiceImpKPI0609;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Created by wangkai on 16/0609/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class KPI0609Test {

    
    @Autowired
    private ReportServiceImpKPI0609 reportserviceimpkpi0609;


    @Test
    public void incomeAndComple()throws DataAccessException, JsonProcessingException{
    	String yearMonth="201304";
		String json = reportserviceimpkpi0609.incomeAndComple(yearMonth);
         System.out.println("incomeIndicatorAndComple   =!!!  "+json);
    }
    
    @Test
    public void saleIncomeFiLayer()throws DataAccessException, JsonProcessingException{
    	String yearMonth="201704";
    	String json = reportserviceimpkpi0609.saleIncomeFiLayer(yearMonth);
         System.out.println("firstLayer   =!!!  "+json);
    }
    
    @Test
    public void getcustoByKdgrp()throws DataAccessException, JsonProcessingException{
    	String year="201511";
    	String kdgrp="3000";
    	String json = reportserviceimpkpi0609.getcustoByKdgrp(year,kdgrp,"1");
        System.out.println("getcustoByKdgrp   =!!!  "+json);
    }
    
    
    @Test
    public void anSignedLayer()throws DataAccessException, JsonProcessingException{
    	String year="201512";
    	String json = reportserviceimpkpi0609.anSignedLayer(year);
        System.out.println("signedLayer   =!!!  "+json);
    }
    
    @Test
    public void getRatePie()throws DataAccessException, JsonProcessingException{
    	String year="201704";
    	String json = reportserviceimpkpi0609.getRatePie(year);
        System.out.println("signedLayer   =!!!  "+json);
    }
    
    
    @Test
    public void getBussiApart()throws DataAccessException, JsonProcessingException{
    	String year="2016";
    	String znum="1";
    	String json = reportserviceimpkpi0609.getBussiApart(year,znum);
        System.out.println("signedLayer   =!!!  "+json);
    }
    
    @Test
    public void getCustByApart()throws DataAccessException, JsonProcessingException{
    	String year="2016";
    	String znum="1";
    	String vkbur="5002";
    	String json = reportserviceimpkpi0609.getCustByApart(year,znum,vkbur);
        System.out.println("signedLayer   =!!!  "+json);
    }
    
    @Test
    public void test()throws DataAccessException, JsonProcessingException{
    	
					       Map map= new HashMap<>();
					       map.put("ZZC","811,644.83" );
					       map.put("SIGNKPI","65.87" );
					       map.put("TOTALACTUAL","711,622.83" );
					       map.put("TOTALSIGNED","64.08" );
					       map.put("rate","87.68%" );
					       
//				        	NumberFormat decimalFormat = new DecimalFormat("###.##");
//				    	  	Set indicators = indicatorAndComple.keySet();
//				    	  	for(Object indicator:indicators){
//				    	  		String value = decimalFormat.format(Double.parseDouble((indicatorAndComple.get(indicator)+"").replace(",", "")));
//				    	  		indicatorAndComple.put(indicator,value);
//				    	  	}     
					       
    }
    
}
