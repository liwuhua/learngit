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
 * create on 2016/11/11
 * @author yangzhijie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS16Test {
	
    @Autowired
    private IReportServiceFS16 iReportServiceFS16;
    @Test
    public void getReportJson(){
    	
        String dateYearMonth = "201606";
        List<String> compCodeValue = new ArrayList<String>();
        compCodeValue.add("5003");
        QueryBean queryBean = new QueryBean();
        queryBean.setDateYearMonth(dateYearMonth);
        queryBean.setCompCodeValue(compCodeValue);
        boolean isExport = false;
        queryBean.setExport(isExport);
        try {
        	Object resultJson = null;
        	resultJson = iReportServiceFS16.getReportJson(queryBean);
        	Log.info("queryBean ++++++++"+queryBean.toString());
        	if(isExport){
            	
            	ExcelExportUtil ee = (ExcelExportUtil) resultJson;
        		ee.writeFile("C://Users//xiaozhang//Desktop//临时文件//export16.xlsx");
        		ee.dispose();
                System.out.println("resultJson++++++++++++++="+resultJson.toString());
                return ;
        	}
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void importTest() throws DataAccessException, JsonProcessingException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException{
    	String filePath = "C://Users//xiaozhang//Desktop//合并利润表.xlsx";
    	
    	Object result = iReportServiceFS16.importData(filePath);
    	System.out.println("result++++++"+result.toString());
    }
}
