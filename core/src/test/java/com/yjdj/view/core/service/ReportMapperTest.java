package com.yjdj.view.core.service;

import com.yjdj.view.core.mapper.IReportMapperFS00;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 16/10/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class ReportMapperTest {

    @Autowired
    private IReportMapperFS00 reportMapper;

//    /**
//     * 查询所有的工厂
//     */
//    @Test
//    public void testPlant(){
//        List<MdmTplant> list = reportMapper.getAllTplant();
//        for(MdmTplant mt : list){
//            System.out.println(mt.toString());
//        }
//
//    }
//
//    /**
//     * 查询销售部门
//     */
//    @Test
//    public void testTsalesOffLike(){
//        List<MdmTsalesOff> list = reportMapper.getListTsalesOffLike("001","部门");
//        System.err.print(list);
//    }

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


        List<Map<String,Object>> mapList = reportMapper.getMapListByThmlips01(matnrValue,matnrInterval,vkorgValue,vkburValue);
        System.err.println(mapList.size()+"-----------------------------");
        /*for(Map<String,Object> map : mapList){
            for(Map.Entry<String,Object> entry : map.entrySet()){
                System.err.println(entry.getKey()+" : "+entry.getValue());
            }
            System.err.println("----------------------------------------------");
        }*/
    }

    @Test
    public void test(){
        List<String> matnrValue = new ArrayList<String>();
        matnrValue.add("Z500000007230");
        matnrValue.add("Z500000069198");

        List<Map<String,String>> matnrInterval = new ArrayList<Map<String,String>>();
        Map<String,String> map = new HashMap<String,String>();
        map.put("Z500000007230","Z500000069198");
        matnrInterval.add(map);
        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("Z500000035715","Z500000083055");
        matnrInterval.add(map1);
//        matnrInterval.add("Z500000007230:Z500000069198");
//        matnrInterval.add("Z500000035715:Z500000083055");

        List<String> vkorgValue = new ArrayList<String>();
        vkorgValue.add("5005");

        List<String> vkburValue = new ArrayList<String>();
        vkburValue.add("5002");
        vkburValue.add("5005");
        vkburValue.add("5011");

        List<Map<String,Object>> mapList = reportMapper.getMapListByThmlips01Test(null,matnrInterval,vkorgValue,vkburValue);
        System.err.println(mapList.size()+"-----------------------------");
    }


}
