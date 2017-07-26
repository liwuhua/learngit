package com.yjdj.view.core.service;

import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_LIPS_02;
import com.yjdj.view.core.entity.mybeans.THM_VBAP_02;
import com.yjdj.view.core.mapper.IReportMapperFS06;
import com.yjdj.view.core.util.ExcelExportUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zcyj on 2016/11/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS06Test {
    @Autowired
//    private IReportServiceFS06 iReportServiceFS06;
    private IReportMapperFS06 iReportMapperFS06;

    @Test
    public void selectData1(){

        List<String> vkorgValue = new ArrayList<String>();
//        vkorgValue.add("5002");

        List<String> vkburValue = new ArrayList<String>();
//        vkburValue.add("5003");

        List<String> matnrValue = new ArrayList<String>();
//        matnrValue.add("Z500000082876");

        List<Map<String,String>> matnrInterval = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("000000005020003190","000000005020004583");
        matnrInterval.add(map);
        Map<String,String> map1 = new HashMap<>();
        map1.put("Z500000203652","Z500000203896");
        matnrInterval.add(map1);

        String startTime="";

        String endTime="";

//        String startTime="20160101";

//        String endTime="20161108";

        List<String> kunnrValue = new ArrayList<String>();
//        kunnrValue.add("1000200000");

        List<String> auartValue = new ArrayList<String>();
        auartValue.add("ZCQ2");

        int startitem = 0;
        int pageitem = 20;
        int flag = 0;
//        boolean isExport=false;

        List<THM_VBAP_02> list1 =iReportMapperFS06.selectData_01(null,vkorgValue,vkburValue,matnrValue,matnrInterval,startTime,endTime,kunnrValue,auartValue,startitem,pageitem,flag);
        System.out.println(list1.size());
        for(THM_VBAP_02 f : list1){
            System.out.println(f.toString());
        }

    }
    @Test
    public void selectData2(){
        String vbeln="4000000753";
        String posnr="000020";
        int start = 0;
        int limit = 20;
        List<THM_LIPS_02> list2 =iReportMapperFS06.selectData_02(vbeln,posnr,start,limit);
        System.out.println(list2.size());
        for(THM_LIPS_02 f : list2){
            System.out.println(f.toString());
        }
    }

    @Autowired
    IReportServiceFS06 iReportServiceFS06;

    QueryBean queryBean = new QueryBean();

    @Test
    public void serviceTest1()throws IOException{
        List<String> vkorgValue = new ArrayList<String>();

//        vkorgValue.add("5002");

        List<String> vkburValue = new ArrayList<String>();

        vkburValue.add("5003");

        List<String> matnrValue = new ArrayList<String>();
//        matnrValue.add("Z500000082876");

//        List<Map<String,String>> matnrInterval = new ArrayList<>();

        List<String> matnrInterval = new ArrayList<>();
//        matnrInterval.add("Z500000203652:Z500000203896");
//        matnrInterval.add("000000005020003190:000000005020004583");

        String startTime="";

        String endTime="";

        List<String> kunnrValue = new ArrayList<String>();

        kunnrValue.add("1000200000");

        List<String> auartValue = new ArrayList<String>();
       auartValue.add("ZCQ2");

        queryBean.setVkorgValue(vkorgValue);
        queryBean.setVkburValue(vkburValue);
        queryBean.setMatnrValue(matnrValue);
        queryBean.setMatnrInterval(matnrInterval);
        queryBean.setStartTime(startTime);
        queryBean.setEndTime(endTime);
        queryBean.setKunnrValue(kunnrValue);
        queryBean.setAuartValue(auartValue);
        queryBean.setStartitem(0);
        queryBean.setPageitem(20);

//测试数据查询
//        queryBean.setExport(false);
//        Object result = iReportServiceFS06.selectData_01(queryBean);
//        Object result_a = result.toString();
//        System.out.println(result_a);

//测试数据导出
        queryBean.setExport(true);
        Object resultJson = iReportServiceFS06.selectData_01(queryBean);
        if (resultJson != null && queryBean.isExport()) {
            ExcelExportUtil ee = (ExcelExportUtil) resultJson;
            ee.writeFile("C://Users//zcyj//Desktop//export//合同执行情况.xlsx");
            ee.dispose();
            System.out.println("Export success.");
        }


    }

    @Test
    public void serviceTest2()throws IOException{
        String vbeln = "4000000753";
        String posnr = "000020";
        int start = 0;
        int limit = 20;
        String result = iReportServiceFS06.selectData_02(vbeln,posnr,start,limit);
        System.out.println(result);
    }
}
