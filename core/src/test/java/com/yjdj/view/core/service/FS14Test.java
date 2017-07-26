package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.IReportMapperFS13;
import com.yjdj.view.core.mapper.IReportMapperFS14;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FS14Test {

    
    @Autowired
    private IReportMapperFS14 iReportMapperFS14;

    @Autowired IReportServiceFS14 iReportServiceFS14;

    @Test
    public void deStatistics() throws Exception, ParseException{
        List<String> plantValue = new ArrayList<String>();
        plantValue.add("1000");

        List<String> matnrValue = new ArrayList<String>();

        List<Map<String,String>> matnrInterval = new ArrayList<Map<String,String>>();

        List<String> lgortValue = new ArrayList<String>();

        List<String> lgpbeValue = new ArrayList<String>();

        String dateTime = "20170222";

        String blog = "1";

        List<Map<String,Object>> mapList = iReportMapperFS14.getDataFir(plantValue,matnrValue,matnrInterval,lgortValue,lgpbeValue
                        ,null,null,null,null,dateTime,null,blog,0,20);

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

        String dateTime = "20170222";

        String blog = "1";

        qb.setPlantValue(plantValue);
        qb.setDateTime(dateTime);
        qb.setYlzd("a");

        try {
            System.out.println(iReportServiceFS14.getData(qb));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
  
}
