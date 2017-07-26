package com.yjdj.view.core.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.FileUtils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xerces.impl.xs.identity.Selector.Matcher;
import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;



/**
 * create on 2016/11/07
 * @author yangzhijie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS15Test {

    @Autowired
    private IReportServiceFS15 IReportServiceFS15;

    @Test
    public void getReportJson(){
    	
    	boolean isExport = false;
        String dateYearMonth = "201601";
        List<String> compCodeValue = new ArrayList<String>();
        compCodeValue.add("5003");
        
        QueryBean queryBean = new QueryBean();
        queryBean.setDateYearMonth(dateYearMonth);
        queryBean.setCompCodeValue(compCodeValue);
        queryBean.setExport(isExport);
        
        try {
        	
            Object resultJson = IReportServiceFS15.getReportJson(queryBean);
            Log.info("resultJson ++++++++"+resultJson.toString());
            if(isExport){
                System.out.println("resultJson++++++++++++++++"+resultJson.toString());
            	ExcelExportUtil ee = (ExcelExportUtil) resultJson;
        		ee.writeFile("C://Users//xiaozhang//Desktop//临时文件//合并资产负债表.xlsx");
        		ee.dispose();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void importTest() throws DataAccessException, JsonProcessingException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException{
    	String filePath = "C://Users//xiaozhang//Desktop//合并报表//合并资产负债表.xlsx";
    	//C:\Users\xiaozhang\Desktop\临时文件\合并报表
    	Object result = IReportServiceFS15.importData(filePath);
    	System.out.println("result+++++++++++++++"+result.toString());
    	
    }
    @Test
    public void testExcel() throws FileNotFoundException, IOException{
    	List<String> list = new ArrayList<String>();
    	list.add("中国");
    	list.add("美国");
    	ExcelExportUtil eeList = new ExcelExportUtil(list);    	
    	eeList.addMergedRegion(1, 1, 0, 5, "China");
    	eeList.addMergedRegion(2, 2, 0, 5, "China");
    	eeList.addMergedRegion(3, 4, 0, 5, "China");
    	eeList.addMergedRegion(5, 7, 0, 5, "China");
    	
    	eeList.writeFile("C://Users//xiaozhang//Desktop//importFile//export.xlsx");
    	eeList.dispose();
//    	ExcelExportUtil ee = new ExcelExportUtil("合并资产负债表",12);
//		ee.writeFile("C://Users//xiaozhang//Desktop//importFile//export.xlsx");
//		ee.dispose();

        //int firstRow, int lastRow, int firstCol, int lastCol, String content
//        ee.addMergedRegion(2, 2, 0, 7, "content");
    }

    public List<String> getData(){
    	List<String> valids = new ArrayList<String>();
    	valids.add("qqq");
    	valids.add("www");
    	return valids;
    }
    @Test
    public void isValidDate(){
//    	String date = "201612";
//    	System.out.println(FileUtils.isValidDate(date));
    	List<String> valids = new ArrayList<String>();
    	valids.add("1212");
    	valids.add("hhhe");
    	valids.addAll(getData());
//    	valids = getData();
    	
    	System.out.println(valids);
    	String num = "9999999999";
    	System.out.println(FileUtils.isNumeric(num));
    }  
}
