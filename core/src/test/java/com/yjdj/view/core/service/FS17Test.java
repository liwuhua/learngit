package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.util.ExcelExportUtil;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


/**
 * create on 2016/11/22
 * @author yangzhijie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS17Test {

    @Autowired
    private IReportServiceFS17 iReportServiceFS17;

    @Test
    public void getReportJson(){
       
        String dateYearMonth = "201601";
        List<String> compCodeValue = new ArrayList<String>();
        compCodeValue.add("5003");
        boolean isExport = true;
        QueryBean queryBean = new QueryBean();
        queryBean.setDateYearMonth(dateYearMonth);
        queryBean.setCompCodeValue(compCodeValue);
        queryBean.setExport(isExport);
        
        try {
            Object resultJson = iReportServiceFS17.getReportJson(queryBean);
            System.out.println("resultJson+++++++++++"+resultJson.toString());
            if(isExport){
            	ExcelExportUtil ee = (ExcelExportUtil) resultJson;
        		ee.writeFile("C://Users//xiaozhang//Desktop//临时文件//export17.xlsx");
        		ee.dispose();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void importTest() throws DataAccessException, JsonProcessingException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException{
    	QueryBean queryBean = new QueryBean();
    	String filePath = "C://Users//xiaozhang//Desktop//合并报表//合并现金流量表.xlsx";
    	//C:\Users\xiaozhang\Desktop\临时文件\合并报表
    	Object result = iReportServiceFS17.importData(filePath);
    	System.out.println(result);
    	
    }
}
