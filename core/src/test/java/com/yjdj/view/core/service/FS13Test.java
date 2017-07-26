package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.IReportMapperFS12;
import com.yjdj.view.core.mapper.IReportMapperFS13;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 16/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS13Test {

    
    @Autowired
    private IReportMapperFS13 iReportMapperFS13;

    @Autowired IReportServiceFS13 iReportServiceFS13;


    @Test
    public void testLgpbe(){
        try {
            System.out.println(iReportServiceFS13.getLgpbe(""));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deStatistics() throws Exception, ParseException{
        List<String> plantValue = new ArrayList<String>();
        plantValue.add("1000");

        List<String> matnrValue = new ArrayList<String>();

        List<Map<String,String>> matnrInterval = new ArrayList<Map<String,String>>();

        List<String> lgortValue = new ArrayList<String>();

        List<String> lgpbeValue = new ArrayList<String>();

        List<String> vendorValue = new ArrayList<String>();

        List<String> vendorTypeValue = new ArrayList<String>();

        String dateTime = "20170213";

        String dayNumStart = "517";

        String dayNumEnd = "518";

        String blog = "1";

        List<Map<String,Object>> mapList = iReportMapperFS13.getData(plantValue,matnrValue,matnrInterval,lgortValue,lgpbeValue
                        ,vendorValue,vendorTypeValue,dateTime,dayNumStart,dayNumEnd,blog,0,20);

        System.out.println(mapList);
    }

    @Test
    public void getDateTest(){
        QueryBean qb = new QueryBean();

        List<String> plantValue = new ArrayList<String>();
        plantValue.add("1000");

        List<String> matnrValue = new ArrayList<String>();

        List<Map<String,String>> matnrInterval = new ArrayList<Map<String,String>>();

        List<String> lgortValue = new ArrayList<String>();

        List<String> lgpbeValue = new ArrayList<String>();

        List<String> vendorValue = new ArrayList<String>();

        List<String> vendorTypeValue = new ArrayList<String>();

        String dateTime = "20170213";

        String dayNumStart = "0";

        String dayNumEnd = "518";

        String blog = "1";

        qb.setPlantValue(plantValue);
        qb.setMatnrValue(matnrValue);
        qb.setDateTime("20170212");
        qb.setStartTime(dayNumStart);
        qb.setEndTime(dayNumEnd);

        try {
            System.out.println(iReportServiceFS13.getData(qb));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
  
}
