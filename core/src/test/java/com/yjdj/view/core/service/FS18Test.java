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
 * Created by zcyj on 1816/10/24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS18Test {
    @Autowired
    private IReportServiceFS18 iReportServiceFS18;

    @Test
    public void marorderdeliver() throws Exception{
    	//fs18的条件都不是必须的
    	List<String> plantList = new ArrayList<>();
    	plantList.add("1000");
    	plantList.add("1100");
    	plantList.add("1900");
        QueryBean queryBean = new QueryBean();
        String listFs18 = iReportServiceFS18.getListFs18(queryBean,plantList);
        System.out.println(listFs18+"  listFs18 json数值");
    }

}


