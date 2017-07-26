package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.util.ExcelExportUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


/**
 * create on 2016/10/21
 * @author zhangwenguo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS01Test {

    @Autowired
    private IReportServiceFS01 IReportServiceFS01;

    @Test
    public void getReportJson(){
    	
        Map<String,String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        System.out.println(map.toString());
        //WERKS ,MATNR,             LIFNR,     EKGRP,BUDAT,KZZUG
        //1000	000000005020000003	2000007356	C03	  20160628
//    	private String matnr; //物料编码
//    	private String meins; //基本计量单位
//    	private String price_base; //价格单位
//    	private String lifnr; //供应商编码
        List<String> plantValue = new ArrayList<String>();
//        plantValue.add("1000");
//        plantValue.add("2000");

        List<String> matnrValue = new ArrayList<String>();
//        matnrValue.add("5020000003");
//        matnrValue.add("000000005020000004");
//        matnrValue.add("000000005020000014");
//        matnrValue.add("1000300000");
        
        
        List<String> vendorValue = new ArrayList<String>();
//        vendorValue.add("2000007356");
//        vendorValue.add("2000011335");
//        vendorValue.add("2000001989");

        List<String> purGroupValue = new ArrayList<String>();
//        purGroupValue.add("C03");
//        purGroupValue.add("C02");
//        purGroupValue.add("B02");
        List<String> lifnrValue = new ArrayList<String>();
//        lifnrValue.add("5020000001");
//        lifnrValue.add("");
//        lifnrValue.add("");
        
        boolean isshow = false;
        boolean isExport = false;
        String starttime = "20160101";
        String endtime = "20160110";
        
        QueryBean queryBean = new QueryBean();
        queryBean.setPlantValue(plantValue);
        queryBean.setMatnrValue(matnrValue);
        queryBean.setVendorValue(vendorValue);
        queryBean.setPurGroupValue(purGroupValue);
        queryBean.setShow(isshow);
        queryBean.setStartTime(starttime);
        queryBean.setEndTime(endtime);
        queryBean.setExport(isExport);
        
        try {
        	 Object resultJson = IReportServiceFS01.getReportJson(queryBean);
        	if(isExport){
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.writeFile("C://Users//xiaozhang//Desktop//export.xlsx");
        	}

//            System.out.println("resultJson +++++++++++ "+resultJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
