package com.yjdj.view.core.service;

import java.io.IOException;

import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


/**
 * create on 2017/01/17
 * @author yangzhijie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS52Test {

    @Autowired
    private  IReportServiceFS52 iReportServiceFS52;

    @Test
    public void getReportJson() throws IOException{
        
        
		
		 try {
		        Object resultJson = iReportServiceFS52.getReportJson();
				
				Log.info("resultJson ++++++++"+resultJson);
	            System.out.println("+++++++++++++"+resultJson.toString());

	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
    }
}
