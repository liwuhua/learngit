package com.yjdj.view.core.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.IReportMapperFS48;
import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;

/**
 * Created by zhuxuan on 2016/12/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
@SuppressWarnings("all")
public class FS48Test {
    @Autowired
    private IReportMapperFS48 iReportMapperFS48;
    @Autowired
    private IReportServiceFS48 iReportServiceFS48;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetStations() throws IOException {
        String stations = iReportServiceFS48.getStations();
        Log.info("resultJson ++++++++"+stations);
    }
    
    
    
    @Test
    public void testSelecProductAttachOrSparepart() throws IOException {
     	QueryBean queryBean = new QueryBean();
     	queryBean.setStartTime("201401");
     	queryBean.setEndTime("201705");
     	queryBean.setYlzd("00010");
     	queryBean.setStartitem(0);
     	queryBean.setPageitem(20);
     	queryBean.setProductType("1");
    	String stations = iReportServiceFS48.selecProductAttachOrSparepart(queryBean);
    	System.out.println(stations);
        Log.info("resultJson ++++++++"+stations);
    }
    
    
    
    @Test
    public void testSelecProductAttachOrSparepart1() throws IOException {
     	QueryBean queryBean = new QueryBean();
     	queryBean.setStartTime("201401");
     	queryBean.setEndTime("201708");
     	queryBean.setYlzd("00010");
     	queryBean.setStartitem(0);
     	queryBean.setPageitem(1000);
     	queryBean.setProductType("2");
        String stations = iReportServiceFS48.selecProductAttachOrSparepart(queryBean);
        Log.info("resultJson ++++++++"+stations);
    }

    @Test
    public void testSelecProductAttachOrSparepart2()  {
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer = stringBuffer1;
    }

}



