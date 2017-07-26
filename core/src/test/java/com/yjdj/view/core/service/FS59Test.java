package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.IReportMapperFS20;
import com.yjdj.view.core.service.impl.ReportServiceImpFS59;
import com.yjdj.view.core.util.ExcelExportUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zcyj on 2016/10/24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS59Test {
    @Autowired
    private IReportServiceFS59 iReportServiceFS59;


    ReportServiceImpFS59 reportServiceImpFS59 = new ReportServiceImpFS59();

    @Test
    public void getFs59() throws Exception{
        QueryBean queryBean = new QueryBean();
    	String start ="201601";
    	String end="201603";
        queryBean.setDateYearMonthStart(start);
        queryBean.setDateYearMonthEnd(end);
        queryBean.setCompCodeValue(new ArrayList<String>());

        List<String> compCodeSelect = new ArrayList<String>();
        compCodeSelect.add("5003");

        queryBean.setExport(false);
        Object obj = iReportServiceFS59.getFs59(queryBean,compCodeSelect);
        if(queryBean.isExport()){
//        System.out.println(listFs59+"  listFs59 json数值");
            ExcelExportUtil ee = (ExcelExportUtil) obj;
            ee.writeFile("/Users/wangkai/Downloads/export59.xlsx");
        }else{
            String str = (String) obj;
            System.out.println(str);
        }

    }

    @Test
    public void testDownLoad(){
        ExcelExportUtil ee = null;
        try {
            ee = reportServiceImpFS59.downLoad(null,"中车");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ee.writeFile("/Users/wangkai/Downloads/export59.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ee.dispose();
    }
}


