package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.IReportMapperFS39;
import org.apache.commons.collections.CollectionUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.text.ParseException;
import java.util.*;
import com.yjdj.view.core.entity.mybeans.QueryBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zcyj on 2016/10/24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS39Test {

    @Autowired
    private IReportServiceFS39 iReportServiceFS39;

    @Test
    public void testService(){
        List<String> groesValue = new ArrayList<String>();
        groesValue.add("YJ85A1");

        List<String> ltxa1Value = new ArrayList<String>();
        ltxa1Value.add("下料-成型-焊接");

        List<String> kdaufValue = new ArrayList<String>();
        kdaufValue.add("5000000352");

        List<String> kdposValue = new ArrayList<String>();
        kdposValue.add("000010");


        List<String> dwerkValue = new ArrayList<String>();
        dwerkValue.add("2000");

        List<String> arbplValue = new ArrayList<String>();
        arbplValue.add("50030061");

        List<String> xqdwValue = new ArrayList<String>();
        xqdwValue.add("电镀");

        List<String> matnrValue = new ArrayList<String>();
        matnrValue.add("Z500000010034");
        matnrValue.add("Z500000050031");
        matnrValue.add("Z500000085753");

        QueryBean queryBean = new QueryBean();
        queryBean.setGroesValue(groesValue);
//        queryBean.setLtxa1Value(ltxa1Value);
//        queryBean.setKdaufValue(kdaufValue);
//        queryBean.setKdposValue(kdposValue);
//        queryBean.setArbplValue(arbplValue);
//        queryBean.setDwerkValue(dwerkValue);
//        queryBean.setXqdwValue(xqdwValue);
//        queryBean.setMatnrValue(matnrValue);
        queryBean.setStartTime("20170501");
        queryBean.setEndTime("20170529");

        try {
            System.out.println(iReportServiceFS39.getDataFS39(queryBean));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testList(){
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("3");

        Set<String> set = new HashSet<String>(list);
        Map<Integer,List<String>> map = new HashMap<Integer,List<String>>();
        int max = Integer.MIN_VALUE;
        for(String str : set){
            int num = Collections.frequency(list,str);
            max = max > num ? max : num;

            List<String> ll = map.get(num);
            if(CollectionUtils.isEmpty(ll)){
                ll = new ArrayList<String>();
            }
            ll.add(str);
            map.put(Collections.frequency(list,str), ll);
        }

        List<String> ll = map.get(max);
        Collections.reverse(ll);
        System.out.println(ll.get(0));


//        System.out.println(max);
    }
}


