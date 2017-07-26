package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.util.ExcelExportUtil55;

import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


/**
 * create on 2017/03/08
 * @author yangzhijie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS62Test {

    @Autowired
    private IReportServiceFS62 IReportServiceFS62;

    @Test
    public void getReportJson(){
//        List<String> plantValue = new ArrayList<String>();
//        plantValue.add("1000");
//        plantValue.add("3000");
        
//        List<String> arbplValue = new ArrayList<String>();
//        arbplValue.add("GCS001");
//        arbplValue.add("SCS006");

//        List<String> groesValue = new ArrayList<String>();
		
//		   List<String> sernrValue = new ArrayList<String>();
		
//		   List<String> zlevelValue = new ArrayList<String>();

        boolean isExport = true; 
//        String starttime = "20170101";
//        String endtime = "20170301";
        

        QueryBean queryBean = new QueryBean();
//        queryBean.setPlantValue(plantValue);
        
//        queryBean.setStartTime(starttime);
//        queryBean.setEndTime(endtime);
        queryBean.setExport(isExport);

        if(isExport){
        	try {
            	Log.info("queryBean ++++++++"+queryBean.toString());
                Object resultJson = IReportServiceFS62.getReportJson(queryBean);
                System.out.println("resultJson++++++++++++++++"+resultJson.toString());
            	ExcelExportUtil55 ee = (ExcelExportUtil55) resultJson;
        		ee.writeFile("d:/export62.xlsx");
        		ee.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
        	try {
            	Log.info("queryBean ++++++++"+queryBean.toString());
                Object resultJson = IReportServiceFS62.getReportJson(queryBean);
                System.out.println("resultJson++++++++++++++++"+resultJson.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Test
    public void test() throws DataAccessException, JsonProcessingException{
    	    	
    	String arbplLike ="001";
    	String groesLike = "140";
    	String sernrLike = "DALIAN";
    	String zlevelLike = "10";
    	int start = 0;
    	int limit = 20;
    	Object list = IReportServiceFS62.getArbpl(arbplLike);
    	Object list2 = IReportServiceFS62.getGroes(groesLike);
    	Object list3 = IReportServiceFS62.getSernr(sernrLike,start,limit);
    	Object list4 = IReportServiceFS62.getZlevel(zlevelLike);
    	
    	System.out.println("++++++"+list.toString()+list2.toString()+"list3"+list3.toString()+"list4"+list4.toString());
    }

}
