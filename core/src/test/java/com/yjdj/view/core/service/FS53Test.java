package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryPojo;


/**
 * create on 2017/01/17
 * @author yangzhijie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS53Test {

    @Autowired
    private  IReportServiceFS53 iReportServiceFS53;
    
    @Test
    public void getReportJson() throws IOException{
    	List<String> gzlxList = new ArrayList<String>();
    	
    	gzlxList.add("是的");
    	gzlxList.add("开始的");
    	
        
        QueryPojo queryPojo = new QueryPojo();
        queryPojo.setGzlx(gzlxList);
//        queryPojo.setYearInput("2017");
		
		 try {
		        
			 Object resultJson = iReportServiceFS53.getReportJson(queryPojo);
				
			 Log.info("resultJson ++++++++"+resultJson);
	         System.out.println("+++++++++++++"+resultJson.toString());	            

	        } catch (IOException e) {
	        	e.printStackTrace();
	        } 
    }
    
    @Test
    public void test() throws DataAccessException, JsonProcessingException{
    	    	
    	String gzlxLike ="";
    	Object list = iReportServiceFS53.getGzlx(gzlxLike);
    	
    	System.out.println("++++++"+list.toString());
    }
}
