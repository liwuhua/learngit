package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.mapper.IReportMapperThmVbap01;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 2016/11/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class ThmVbap01Test {

    @Autowired
    private IReportMapperThmVbap01 iReportMapperThmVbap01;

    @Autowired
    private IThmVbap01Service iThmVbap01Service;

    @Test
    public void getVbeln(){
        String vbeln = "208";
//        System.out.println(iReportMapperThmVbap01.getVbeln(null,0,10));
        try {
//            System.out.println(iThmVbap01Service.getVbeln(null,0,10));
            System.out.println(iThmVbap01Service.getVbeln("THM_VBAP_01",null,0,10)+"111");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getVbelnByOrgAndBurLikeVbeln(){
        List<String> vkorgValue = new ArrayList<String>();
        vkorgValue.add("5025");
        vkorgValue.add("5003");
        List<String> vkburValue = new ArrayList<String>();
        vkburValue.add("5016");
        vkburValue.add("5001");

        String vbeln = "208";
//        List<String> list = iReportMapperThmVbap01.getVbelnByOrgAndBur(vkorgValue,vkburValue,null,0,10);

//        for(String str : list){
//            System.out.println(str);
//        }

        try {
            System.out.println(iThmVbap01Service.getVbelnByOrgAndBur(vkorgValue,vkburValue,null,0,10));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
