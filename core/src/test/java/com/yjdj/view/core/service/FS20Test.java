package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.mapper.IReportMapperFS20;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.SystemProfileValueSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.yjdj.view.core.entity.mybeans.QueryBean;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zcyj on 2016/10/24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS20Test {
    @Autowired
    private IReportServiceFS20 iReportServiceFS20;

    @Autowired
    private IReportMapperFS20 iReportMapperFS20;

    @Test
    public void getOrderMarket() throws Exception{
        QueryBean queryBean = new QueryBean();
//    	queryBean.setKdauf("5000000094");
//    	queryBean.setKdpos("000030");
    	String KDAUF ="5000000094";
    	String KDPOS="000030";
        String listFs20 = iReportServiceFS20.getListFs20("Z500000071520","1000","5000002459","000030",null);
        System.out.println(listFs20+"  listFs20 json数值");
    }


    @Test
    public void test(){
        List<String> list = new ArrayList<String>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        Collections.reverse(list);

        System.out.print(list.get(0)+"-----------");
    }

    @Test
    public void testMapper(){
        System.out.println(iReportMapperFS20.getDataByMa("5000001903","0002ssss30","Z500000ss189039","000010094211"));
    }


    @Test
    public void testService() {
        try {
//            System.out.println(iReportServiceFS20.getFs20("5000003031", "000020", "Z500000042033", "000010091283"));
//            System.out.println(iReportServiceFS20.getFs20("5000001903", "000230", "Z500000189039", "000010094211"));
//            System.out.println(iReportServiceFS20.getFs20("5000000317", "000080", "Z500000047649", "000010093656"));
//            System.out.println(iReportServiceFS20.getFs20("0050000247", "000010", "Z500000079723", "000010054742"));
            System.out.println(iReportServiceFS20.getFs20("0050000534", "000010", "Z500000186988", "000010100182"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


