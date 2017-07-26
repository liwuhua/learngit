package com.yjdj.view.core.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.impl.ReportServiceImpKPI10;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * Created by wangkai on 16/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class KPI10Test {

    
    @Autowired
    private ReportServiceImpKPI10 reportserviceimpkpi10;


    @Test
    public void punDeliRate()throws DataAccessException, JsonProcessingException{
    	String dateYearMonth="201210";
		String json = reportserviceimpkpi10.punDeliRate(dateYearMonth);
         System.out.println("punDeliRate   =!!!  "+json);
    }
    
    @Test
    public void firstLayer()throws DataAccessException, JsonProcessingException{
    	String dateYearMonth="201610";
    	String json = reportserviceimpkpi10.firstLayer(dateYearMonth);
         System.out.println("firstLayer   =!!!  "+json);
    }
    
    @Test
    public void secondLayer()throws DataAccessException, JsonProcessingException{
    	QueryBean queryBean = new QueryBean();
    	
    	String vbtypcode="2";
    	String dateYearMonth="201610";
    	String json = reportserviceimpkpi10.secondLayer(vbtypcode,dateYearMonth);
        System.out.println("secondLayer   =!!!  "+json);
    }
    
    
    @Test
    public void thirdLayer()throws DataAccessException, JsonProcessingException{
    	
    	String vbtypcode="2";
    	String matnr="Z500000202742";
    	String dateYearMonth="201610";
//    	Integer  startitem=0;
//    	Integer  pageitem=10;
//    	String json = reportserviceimpkpi10.thirdLayer(vbtyp,matnr,dateYearMonth,startitem,pageitem);
    	String json = reportserviceimpkpi10.thirdLayer(vbtypcode,matnr,dateYearMonth);
        System.out.println("secondLayer   =!!!  "+json);
    }
    
    public static void main(String[] args) {
		
//		 String str = "201601";  
//		 System.out.println(str.substring(4, 6));
//		 System.out.println(str.substring(4, 6).replaceFirst("^0*", ""));
		Calendar a=Calendar.getInstance();
		  System.out.println(a.get(Calendar.YEAR));//得到年
		  System.out.println(a.get(Calendar.YEAR)+"12");
		  System.out.println(a.get(Calendar.MONTH)+1);//由于月份是从0开始的所以加1
		  System.out.println(a.get(Calendar.DATE));
		  System.out.println(a.get(Calendar.YEAR)+a.get(Calendar.MONTH)+1);
		  System.out.println("201611".substring(0, 4));
		  System.out.println(new BigDecimal((2358.98/(2358.98+779.36))*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"%");
		  System.out.println(new BigDecimal((2358.98/(2358.98+779.36))));
	}
    
}
