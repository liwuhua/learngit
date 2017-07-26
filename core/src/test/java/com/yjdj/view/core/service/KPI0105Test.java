package com.yjdj.view.core.service;



import com.yjdj.view.core.entity.mybeans.QueryBean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.text.ParseException;
import java.util.*;

/**
 * Created by wangkai on 16/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class KPI0105Test {

    
    @Autowired
    private IReportServiceKPI0105 ireportServiceKPI0105;

    


    @Test
    public void deStatistics() throws Exception, ParseException{
    	QueryBean queryBean = new QueryBean();
    	queryBean.setDateYearMonthStart("201701");
    	queryBean.setDateYearMonthEnd("201701");
    	queryBean.setCompCodeValue(Arrays.asList("5003"));
    	queryBean.setProductType("0008");
    	
        String result= null;
//        result = ireportServiceKPI0105.getDueTo(queryBean);
        //result = ireportServiceKPI0105.getFirstBukrsType(queryBean).toString();
      // result = ireportServiceKPI0105.getHYFirstBukrsType(queryBean).toString();
     // result = ireportServiceKPI0105.getSecondBukrsType(queryBean);
      //result = ireportServiceKPI0105.getSecondCustType(queryBean);
      // result = ireportServiceKPI0105.getFirstCustType(queryBean).toString();
     // result = ireportServiceKPI0105.getYearAll(queryBean);
     // result = ireportServiceKPI0105.getYearAllBukrs(queryBean);
     // result = ireportServiceKPI0105.getFirstType(queryBean);
       //result = ireportServiceKPI0105.getYearPlan(queryBean).toString();
       //  HashMap a = ireportServiceKPI0105.getLatestDate();
       result = ireportServiceKPI0105.getBadAccountValue("201703")+"";
        //result = ireportServiceKPI0105.accountAge("201612").toString();
        
        
       System.out.println("result   =  "+result);
        
        //System.out.println("a   =  "+a);
    	
    }
    
   
  
}
