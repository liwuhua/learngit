package com.yjdj.view.core.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.util.ExcelExportUtil05;

/**
 * create on 2016/11/3
 * @author zhangwenguo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS05Test {

    @Autowired
    private IReportServiceFS05 iReportServiceFS05;
    
    @Test
    public void getReportJson(){
		
    	//公司代码
    	List<String> rbukrsValue = new ArrayList<String>();
//    	rbukrsValue.add("5004");
//    	rbukrsValue.add("5003");
//    	rbukrsValue.add("5005");
//    	rbukrsValue.add("5006");
//    	rbukrsValue.add("5025");
//    	rbukrsValue.add("5142");
//    	rbukrsValue.add("India");
//    	rbukrsValue.add("SouthAfrica");
//    	NumberFormat numberFormat = NumberFormat.getNumberInstance();
//    	System.out.println(numberFormat.format(11122.33455)); //结果是11,122.33
    	//年月
    	String startTime = "201612";
    	String endTime = "201612";
    	boolean isExport = true;
        QueryBean queryBean = new QueryBean();
        queryBean.setCompCodeValue(rbukrsValue);
        queryBean.setStartTime(startTime);
        queryBean.setEndTime(endTime);
        List<String> checkComp = new ArrayList<String>();
        checkComp.add("5003"); //永济公司
        checkComp.add("5004"); //西安永电
        checkComp.add("5005"); //西安捷力
        checkComp.add("5006"); //修配公司
        checkComp.add("5025"); //西安永电金风
		checkComp.add("5142"); //托克逊公司
		checkComp.add("India"); //印度公司
		checkComp.add("SouthAfrica"); //南非公司
		
        queryBean.setExport(isExport);
        try {
            Object resultJson = iReportServiceFS05.getReportJson(queryBean,checkComp);
            System.out.println("++++++++++++"+resultJson.toString());
            if(isExport){
                ExcelExportUtil05 result = (ExcelExportUtil05) resultJson;
                result.writeFile("C://Users//xiaozhang//Desktop//export.xlsx");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void importData() throws DataAccessException, org.apache.poi.openxml4j.exceptions.InvalidFormatException, ParseException{

//    	 String amount1 = "dfdf";
//    	 Number d1 = new DecimalFormat().parse(amount1); //这里使用的是parse，不是format
//    	 System.out.println(String.valueOf(d1)); //结果是13000.00
    	List<String> checkComp = new ArrayList<String>();
    	checkComp.add("5003");
//    	checkComp.add("5004");
//    	checkComp.add("5005");
		checkComp.add("India"); //印度公司
		checkComp.add("SouthAfrica"); //南非公司
    	String filePath = "C:\\Users\\xiaozhang\\Desktop\\应上交应弥补款项报表(1).xlsx";
    	//导入数据测试  C:\Users\xiaozhang\Desktop\表清单统计
    	try {
			String result = (String) iReportServiceFS05.importData(filePath, checkComp);
			if(result != null || !"".equals(result)){
				System.out.println("result++++++++++++"+result);
			}
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
