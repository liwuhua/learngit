package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.yjdj.view.core.entity.mybeans.QueryBean;

import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


/**
 * create on 2016/11/01
 * @author yangzhijie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS04Test {

    @Autowired
    private IReportServiceFS04 IReportServiceFS04;

    @Test
    public void getReportJson(){
        List<String> plantValue = new ArrayList<String>();
        plantValue.add("1000");
        plantValue.add("3000");
        
//        List<String> ebelnValue = new ArrayList<String>();
//        ebelnValue.add("4600001506");
//        ebelnValue.add("4600002043");
//        ebelnValue.add("4600002082");
//        ebelnValue.add("4600002049");
//        ebelnValue.add("4600002053");
//        ebelnValue.add("4600002050");

//        List<String> matnrValue = new ArrayList<String>();
//        matnrValue.add("Z500000138666");
//        matnrValue.add("Z500000138731");
//        matnrValue.add("Z500000138713");
//        matnrValue.add("Z500000138732");

//        List<String> vendorValue = new ArrayList<String>();
//        vendorValue.add("0000001000");
//        vendorValue.add("0000002000");

//        List<String> purGroupValue = new ArrayList<String>();
//        purGroupValue.add("G02");
//        purGroupValue.add("C02");
//        purGroupValue.add("B02");

        boolean isExport = true; 
        String starttime = "20150101";
        String endtime = "20161010";
        
        List<String> timediffValue = new ArrayList<String>();
        timediffValue.add("0");
//        timediffValue.add("2");
//        timediffValue.add("3");
//        timediffValue.add("4");
//        timediffValue.add("5");
//        timediffValue.add("6");

        QueryBean queryBean = new QueryBean();
//        queryBean.setEbelnValue(ebelnValue);
        queryBean.setPlantValue(plantValue);
//        queryBean.setMatnrValue(matnrValue);
//        queryBean.setVendorValue(vendorValue);
//        queryBean.setPurGroupValue(purGroupValue);
        queryBean.setStartTime(starttime);
        queryBean.setEndTime(endtime);
        queryBean.setTimediffValue(timediffValue);

        try {
        	Log.info("queryBean ++++++++"+queryBean.toString());
        	Log.info("queryBean ++++++++"+queryBean.getTimediffValue().toString());
            Object resultJson = IReportServiceFS04.getReportJson(queryBean);
            Log.info("resultJson ++++++++"+resultJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
