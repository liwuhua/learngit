package com.yjdj.view.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.yjdj.view.core.entity.mybeans.QueryBean;

/**
 * Created by zcyj on 2916/10/24.
 * @param
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS29Test {
	
	@Autowired
	private IReportServiceFS29 ireportservicefs29;

    @Test
    public void marorderdeliver() throws Exception{
    	
        List<String> werksList = new ArrayList<>();
        List<String> plantList = new ArrayList<>();
//        werksList.add("1000");
//        plantList.add("1000");
//        plantList.add("2000");
//        plantList.add("3000");
//        plantList.add("4000"); 
    	
        QueryBean queryBean = new QueryBean();
        queryBean.setStartTimeThree("20160101");
        queryBean.setEndTimeThree("20160101");
        queryBean.setStartTime("20150101");
        queryBean.setEndTime("20170505");
        queryBean.setExclu1(false);
        queryBean.setExport(false);
        queryBean.setShow(false);
        queryBean.setWerksValue(werksList); 
    
        
		String listFs29 = (String)ireportservicefs29.getListFs29(queryBean,plantList);
        System.out.println(listFs29+"  listFs29 json数值");
    }


}


