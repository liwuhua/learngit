package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.util.ExcelExportUtil55;

import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


/**
 * create on 2017/03/02
 * @author yangzhijie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS55Test {

    @Autowired
    private IReportServiceFS55 IReportServiceFS55;

    @Test
    public void getReportJson(){
//        List<String> plantValue = new ArrayList<String>();
//        plantValue.add("1000");
//        plantValue.add("3000");
        
//        List<String> ebelnValue = new ArrayList<String>();
//        ebelnValue.add("4600001506");
//        ebelnValue.add("4600002043");
    	
    	List<String> matnrInterval = new ArrayList<String>();
        matnrInterval.add("5022000282:5022000319");
        matnrInterval.add("5022000320:5022000347");


//        List<String> matnrValue = new ArrayList<String>();
//        matnrValue.add("5022000320");
//        matnrValue.add("5022000347");

//        List<String> vkorgValue = new ArrayList<String>();

        boolean isExport = true; 
        String starttime = "20150101";
        String endtime = "20161210";
        

        QueryBean queryBean = new QueryBean();
//        queryBean.setEbelnValue(ebelnValue);
//        queryBean.setPlantValue(plantValue);
//        queryBean.setMatnrValue(matnrValue);
//        queryBean.setVendorValue(vendorValue);
//        queryBean.setPurGroupValue(purGroupValue);
        
        queryBean.setMatnrInterval(matnrInterval);
        queryBean.setStartTime(starttime);
        queryBean.setEndTime(endtime);
        queryBean.setExport(isExport);

        if(isExport){
        	try {
            	Log.info("queryBean ++++++++"+queryBean.toString());
                Object resultJson = IReportServiceFS55.getReportJson(queryBean);
                System.out.println("resultJson++++++++++++++++"+resultJson.toString());
            	ExcelExportUtil55 ee = (ExcelExportUtil55) resultJson;
        		ee.writeFile("d:/export55.xlsx");
        		ee.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
        	try {
            	Log.info("queryBean ++++++++"+queryBean.toString());
                Object resultJson = IReportServiceFS55.getReportJson(queryBean);
                System.out.println("resultJson++++++++++++++++"+resultJson.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
