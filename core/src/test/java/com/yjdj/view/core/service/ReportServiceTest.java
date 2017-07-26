package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.util.*;

/**
 * Created by wangkai on 16/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceTest {

    @Autowired
    private IReportServiceFS00 reportService;



    @Test
    public void getListMapByThmlips01(){
        List<String> matnrValue = new ArrayList<String>();
        matnrValue.add("Z500000007230");
        matnrValue.add("Z500000069198");

        List<String> matnrInterval = new ArrayList<String>();
        matnrInterval.add("Z500000007230:Z500000069198");
        matnrInterval.add("Z500000035715:Z500000083055");

        List<String> vkorgValue = new ArrayList<String>();
        vkorgValue.add("5005");

        List<String> vkburValue = new ArrayList<String>();
        vkburValue.add("5002");
        vkburValue.add("5005");
        vkburValue.add("5011");


//        List<Map<String,Object>> mapList = reportService.getMapListByThmlips01(matnrValue,matnrInterval,vkorgValue,vkburValue);
//        System.err.println(mapList.size()+"-----------------------------");
        /*for(Map<String,Object> map : mapList){
            for(Map.Entry<String,Object> entry : map.entrySet()){
                System.err.println(entry.getKey()+" : "+entry.getValue());
            }
            System.err.println("----------------------------------------------");
        }*/
    }

    @Test
    public void getReportJson(){
        ObjectMapper mapper = new ObjectMapper();

        Map<String,List<String>> map = new HashMap<String,List<String>>();
        List<String> matnrValue = new ArrayList<String>();
        matnrValue.add("Z500000007230");
        matnrValue.add("Z500000069198");

        List<String> matnrInterval = new ArrayList<String>();
        matnrInterval.add("Z500000007230:Z500000069198");
        matnrInterval.add("Z500000035715:Z500000083055");

        List<String> vkorgValue = new ArrayList<String>();
        vkorgValue.add("5005");
//        vkorgValue.add("5003");
//        vkorgValue.add("5002");

        List<String> vkburValue = new ArrayList<String>();
        vkburValue.add("5002");
        vkburValue.add("5005");
        vkburValue.add("5011");

//        map.put("matnrValue",matnrValue);
//        map.put("matnrInterval",matnrInterval);
//        map.put("vkorgValue",vkorgValue);
//        map.put("vkburValue",vkburValue);

        QueryBean queryBean = new QueryBean();
        queryBean.setMatnrInterval(matnrInterval);
        queryBean.setMatnrValue(matnrValue);
        queryBean.setVkorgValue(vkorgValue);
        queryBean.setVkburValue(vkburValue);

        try {
            String json = mapper.writeValueAsString(map);
            System.out.println(json);
            try {
                String resultJson = reportService.getReportJson(queryBean);
                System.out.println(resultJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetCompare(){
        Set<String> set1 = new HashSet<String>();
        set1.add("1");
        set1.add("3");
        Set<String> set2 = new HashSet<String>();
        set2.add("1");
        set2.add("2");

        set1.removeAll(set2);

        for(String str : set1){
            System.out.println(str);
        }
    }
}
