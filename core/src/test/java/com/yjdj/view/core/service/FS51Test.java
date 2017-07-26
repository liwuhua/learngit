package com.yjdj.view.core.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.MdmProductModel;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.entity.mybeans.THM_FS51;
import com.yjdj.view.core.mapper.IReportMapperFS51;
import com.yjdj.view.core.service.impl.ReportServiceImpFS51;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelExportUtil51;

import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


/**
 * create on 2016/12/29
 * @author yangzhijie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS51Test {

    @Autowired
    private  IReportServiceFS51 iReportServiceFS51;

    @Test
    public void getReportJson() throws IOException{
        List<String> productModel = new ArrayList<String>();
        boolean isExport = false;
//        productModel.add("YJ92B");
//        productModel.add("YJ87A");
//        productModel.add("YJ116A");
        
        QueryPojo queryPojo = new QueryPojo();
        queryPojo.setProductModel(productModel);
        queryPojo.setExport(isExport);
        
		
		 try {
			 	Log.info("queryPojo ++++++++"+queryPojo.toString());
		        Object resultJson = iReportServiceFS51.getReportJson(queryPojo);
				
				Log.info("resultJson ++++++++"+resultJson);
				if(isExport){
					ExcelExportUtil51 ee = (ExcelExportUtil51) iReportServiceFS51.getReportJson(queryPojo);
		            ee.writeFile("D://export51.xlsx");
		    		ee.dispose();
				}
	            System.out.println("+++++++++++++"+resultJson.toString());

	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
    }

    @Test
    public void test() throws DataAccessException, JsonProcessingException{
    	    	
    	String cpxhLike ="92";
    	Object list = iReportServiceFS51.getCpxh(cpxhLike);
    	
    	System.out.println("++++++"+list.toString());
    }
}
