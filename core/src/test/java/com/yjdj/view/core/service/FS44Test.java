package com.yjdj.view.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.yjdj.view.core.entity.mybeans.QueryBean;

/**
 * Created by zcyj on 4416/10/24.
 * @param
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS44Test {
	
	@Autowired
	private IReportServiceFS44 ireportservicefs44;

    @Test
    public void getListFs44() throws Exception{
    	 
        QueryBean queryBean = new QueryBean();
        List<String> plantList = new ArrayList<>();
//      werksList.add("1000");
        plantList.add("1000");
        plantList.add("2000");
        plantList.add("3000");
        plantList.add("4000"); 
        queryBean.setStartTimeThree("20160101");
        queryBean.setStartTime("20160101");
        queryBean.setEndTime("20170505");
//        queryBean.setExclu1(false);
//        queryBean.setExport(false);
//        queryBean.setShow(false);
        
        String listFs44 = ireportservicefs44.getListFs44(queryBean,plantList);
        System.out.println(listFs44+"  listFs44 json数值");
    }


}


