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
 * Created by zcyj on 4516/10/24.
 * @param
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS45Test {
	
	@Autowired
	private IReportServiceFS45 ireportservicefs45;

    @Test
    public void getListFs45() throws Exception{
    	 
        List<String> plantList = new ArrayList<>();
//      werksList.add("1000");
//      plantList.add("1000");
//      plantList.add("2000");
//      plantList.add("3000");
//      plantList.add("4000"); 
        QueryBean queryBean = new QueryBean();
        queryBean.setStartTimeThree("20160101");
        queryBean.setStartTime("20160101");
        queryBean.setEndTime("20170505");
        queryBean.setExclu1(false);
        queryBean.setExport(false);
        queryBean.setShow(false);
        
        String listFs45 = ireportservicefs45.getListFs45(queryBean,plantList);
        System.out.println(listFs45+"  listFs45 json数值");
    }


}


