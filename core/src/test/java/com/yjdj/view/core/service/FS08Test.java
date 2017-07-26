package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.MdmTmaterial;
import com.yjdj.view.core.mapper.IReportMapperFS08;
import com.yjdj.view.core.util.NumProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 16/10/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS08Test {

    @Autowired
    private IReportMapperFS08 iReportMapperFS08;

    @Autowired
    private IReportServiceFS08 iReportServiceFS08;

//    @Test
//    public void getListFs08(){
//        List<Map<String,Object>> mapList = iReportMapperFS08.getListFs08("20160601","20161025");
//        System.out.println(mapList.size());
//        for(Map<String,Object> map : mapList){
//            for(Map.Entry<String,Object> entry : map.entrySet()){
//                System.out.println(entry.getKey()+"------"+entry.getValue());
//            }
//            System.out.println("*************************");
//        }
//    }

//    @Test
//    public void getListFs08ByServic(){
//        String jsonStr = "{\"startTime\":\"20160601\",\"endTime\":\"20161025\"}";
//        try {
//            String json = iReportServiceFS08.getListFs08(jsonStr);
//            System.out.println(json);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void num(){
        System.out.println(NumProcessor.getStrByDec("5.100604402000001E7"));
    }
}
