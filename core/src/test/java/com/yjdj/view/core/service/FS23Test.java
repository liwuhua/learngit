package com.yjdj.view.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.yjdj.view.core.entity.mybeans.QueryBean;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwuhua on 2316/11/28.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS23Test {
   
	@Autowired
    private IReportServiceFS23 iReportServiceFS23;

    @Test
    public void getOrderMarket() throws Exception{
        QueryBean queryBean = new QueryBean();
//    	queryBean.setAufnr("000010000046");
    	String AUFNR="000010000046";
        String listFs23 = iReportServiceFS23.getListFs23(AUFNR);
        System.out.println(listFs23+"  listFs23 json数值");
    }

}


