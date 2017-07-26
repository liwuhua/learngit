package com.yjdj.view.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.util.ExcelExportUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zcyj on 0216/10/24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS02Test {
    @Autowired
    private IReportServiceFS02 ireportservicefs02;

    @Test
    public void getexportExcel1() throws Exception{
    	List<String> salesorgList=new ArrayList<>();
        QueryBean queryBean = new QueryBean();
        queryBean.setVkorgValue(null);
        queryBean.setVkburValue(null);
        queryBean.setVbelnValue(null);
        queryBean.setMatnrValue(null);
        queryBean.setMatnrInterval(null);
        queryBean.setStartTime("20001107");
        queryBean.setEndTime("20161208");
        queryBean.setProductType("");
//        queryBean.isExport();
        queryBean.setStartitem(0);
        queryBean.setPageitem(20);
        //"1:2,3:4" 多值区间
        
        System.out.println(queryBean.toString()+" queryBean   ");
//        ireportservicefs02.getexportExcel1(queryBean);
        ExcelExportUtil ee = ireportservicefs02.getexportExcel1(queryBean,salesorgList);
//		ee.write(response,"销售订单完成统计表");
		ee.writeFile("C://Users//Administrator//Desktop//export"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()))+".xlsx");
    	ee.dispose();
    }

}


