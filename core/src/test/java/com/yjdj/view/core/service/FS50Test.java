package com.yjdj.view.core.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.mapper.IReportMapperFS50;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelExportUtil2227;

/**
 * Created by sunwan on 2017/1/4.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS50Test {
    @Autowired
    private IReportServiceFS50 iReportServiceFS50;
    
    @Autowired
    private IReportMapperFS50 iReportMapperFS50;
    
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Class.class);
//通用
    
    @Test 
    public void getLossTypeCode() throws DataAccessException, JsonProcessingException, IOException {
    	String text = "10"; //10%23
    	String searchtext01 = URLDecoder.decode(text);
    	//URLDecoder.decode(str,"utf-8");
    	System.out.println(searchtext01);
    	String searchtext02 = java.net.URLDecoder.decode(text,"UTF-8");
    	
    	
//    	  List<String> lossTypeCode = iReportMapperFS50.getLossTypeCode(null,0,10);
//    	logger.debug(lossTypeCode+"accid");
	}
    
    @Test 
    public void getAccitype() throws DataAccessException, JsonProcessingException, IOException {
    	String accitype = iReportServiceFS50.getAccitype(null);
    	logger.debug(accitype+"accid");
	}
    
    
    @Test
    public void select_01() throws IOException {

        List<String> xxbh = new ArrayList<>();
        List<String> psjd = new ArrayList<>();
        List<String> cpsmjd = new ArrayList<>();
        List<String> jxdd = new ArrayList<>();
        String startTime = "201703";
        String endTime = "201704";
        List<String> cpxh = new ArrayList<>();
//        cpxh.add("YJ87A");
        List<String> cpbh = new ArrayList<>();
        List<String> jcbh = new ArrayList<>();
        List<String> gzbw = new ArrayList<>();
        List<String> sglb = new ArrayList<>();
        List<String> yzd = new ArrayList<>();
        List<String> jnw = new ArrayList<>();
        List<String> xzjx = new ArrayList<>();
        List<String> lifnr = new ArrayList<>();
        List<String> lbbh = new ArrayList<>();
        List<String> bgbh = new ArrayList<>();
        QueryPojo queryPojo = new QueryPojo();
        queryPojo.setXxbh(xxbh);
        queryPojo.setPsjd(psjd);
        queryPojo.setCpsmjd(cpsmjd);
        queryPojo.setJxdd(jxdd);
        queryPojo.setStartTime(startTime);
        queryPojo.setEndTime(endTime);
        queryPojo.setCpxh(cpxh);
        queryPojo.setCpbh(cpbh);
        queryPojo.setJcbh(jcbh);
        queryPojo.setGzbw(gzbw);
        queryPojo.setSglb(sglb);
        queryPojo.setYzd(yzd);
        queryPojo.setJnw(jnw);
        queryPojo.setXzjx(xzjx);
        queryPojo.setLifnr(lifnr);
        queryPojo.setLbbh(lbbh);
        queryPojo.setBgbh(bgbh);
        queryPojo.setStartitem(0);
        queryPojo.setPageitem(20);
//查询
        queryPojo.setExport(false);
        Object resultJson = iReportServiceFS50.selectData_01(queryPojo);
        Object result_a = resultJson.toString();
        System.out.println(result_a);

        //数据导出
//        queryPojo.setExport(true);
//        Object resultJson = iReportServiceFS50.selectData_01(queryPojo);
//        if (resultJson != null && queryPojo.isExport()) {
//            ExcelExportUtil ee = (ExcelExportUtil) resultJson;
//            ee.writeFile("C://Users//zcyj//Desktop//export//厂外铁路产品质量信息表-通用.xlsx");
//            ee.dispose();
//            System.out.println("通用Export success.");
//        }

    }
//国铁服务中心
    @Test
    public void select_02() throws IOException {

        List<String> xxbh = new ArrayList<>();
        List<String> psjd = new ArrayList<>();
        List<String> cpsmjd = new ArrayList<>();
        List<String> jxdd = new ArrayList<>();
        String startTime = "";
        String endTime = "";
        List<String> cpxh = new ArrayList<>();
//        cpxh.add("YJ87A");
        List<String> cpbh = new ArrayList<>();
        List<String> jcbh = new ArrayList<>();
        List<String> gzbw = new ArrayList<>();
        List<String> sglb = new ArrayList<>();
        List<String> yzd = new ArrayList<>();
        List<String> jnw = new ArrayList<>();
        List<String> xzjx = new ArrayList<>();
        List<String> lifnr = new ArrayList<>();
        List<String> lbbh = new ArrayList<>();
        List<String> bgbh = new ArrayList<>();
        QueryPojo queryPojo = new QueryPojo();
        queryPojo.setXxbh(xxbh);
        queryPojo.setPsjd(psjd);
        queryPojo.setCpsmjd(cpsmjd);
        queryPojo.setJxdd(jxdd);
        queryPojo.setStartTime(startTime);
        queryPojo.setEndTime(endTime);
        queryPojo.setCpxh(cpxh);
        queryPojo.setCpbh(cpbh);
        queryPojo.setJcbh(jcbh);
        queryPojo.setGzbw(gzbw);
        queryPojo.setSglb(sglb);
        queryPojo.setYzd(yzd);
        queryPojo.setJnw(jnw);
        queryPojo.setXzjx(xzjx);
        queryPojo.setLifnr(lifnr);
        queryPojo.setLbbh(lbbh);
        queryPojo.setBgbh(bgbh);
        queryPojo.setStartitem(0);
        queryPojo.setPageitem(20);
//数据查询
//        queryPojo.setExport(false);
//        Object resultJson = iReportServiceFS50.selectData_02(queryPojo);
//        Object result_a = resultJson.toString();
//        System.out.println(result_a);

        //数据导出
        queryPojo.setExport(true);
        Object resultJson = iReportServiceFS50.selectData_02(queryPojo);
        if (resultJson != null && queryPojo.isExport()) {
            ExcelExportUtil ee = (ExcelExportUtil) resultJson;
            ee.writeFile("C://Users//zcyj//Desktop//export//厂外铁路产品质量信息表-国铁服务中心.xlsx");
            ee.dispose();
            System.out.println("国铁服务中心Export success.");
        }

    }
//质保部
    @Test
    public void select_03() throws IOException {

        List<String> xxbh = new ArrayList<>();
        List<String> psjd = new ArrayList<>();
        List<String> cpsmjd = new ArrayList<>();
        List<String> jxdd = new ArrayList<>();
        String startTime = "";
        String endTime = "";
        List<String> cpxh = new ArrayList<>();
        cpxh.add("YJ87A");
        List<String> cpbh = new ArrayList<>();
        List<String> jcbh = new ArrayList<>();
        List<String> gzbw = new ArrayList<>();
        List<String> sglb = new ArrayList<>();
        List<String> yzd = new ArrayList<>();
        List<String> jnw = new ArrayList<>();
        List<String> xzjx = new ArrayList<>();
        List<String> lifnr = new ArrayList<>();
        List<String> lbbh = new ArrayList<>();
        List<String> bgbh = new ArrayList<>();
        QueryPojo queryPojo = new QueryPojo();
        queryPojo.setXxbh(xxbh);
        queryPojo.setPsjd(psjd);
        queryPojo.setCpsmjd(cpsmjd);
        queryPojo.setJxdd(jxdd);
        queryPojo.setStartTime(startTime);
        queryPojo.setEndTime(endTime);
        queryPojo.setCpxh(cpxh);
        queryPojo.setCpbh(cpbh);
        queryPojo.setJcbh(jcbh);
        queryPojo.setGzbw(gzbw);
        queryPojo.setSglb(sglb);
        queryPojo.setYzd(yzd);
        queryPojo.setJnw(jnw);
        queryPojo.setXzjx(xzjx);
        queryPojo.setLifnr(lifnr);
        queryPojo.setLbbh(lbbh);
        queryPojo.setBgbh(bgbh);
        queryPojo.setStartitem(0);
        queryPojo.setPageitem(20);

//        查询
//        queryPojo.setExport(false);
//        Object resultJson = iReportServiceFS50.selectData_03(queryPojo);
//        Object result_a = resultJson.toString();
//        System.out.println(result_a);

        //数据导出
        queryPojo.setExport(true);
        Object resultJson = iReportServiceFS50.selectData_03(queryPojo);
        if (resultJson != null && queryPojo.isExport()) {
            ExcelExportUtil ee = (ExcelExportUtil) resultJson;
            ee.writeFile("C:\\Users\\Administrator\\Desktop\\temp//厂外铁路产品质量信息表-质保部3.xlsx");
                         
            ee.dispose();
            System.out.println("质保部Export success.");
        }
    }

    //04
    @Test
    public void select_04() throws IOException {

        List<String> xxbh = new ArrayList<>();
        List<String> psjd = new ArrayList<>();
        List<String> cpsmjd = new ArrayList<>(); 
        List<String> jxdd = new ArrayList<>();
        String startTime = "";
        String endTime = "";
        List<String> cpxh = new ArrayList<>();
//        cpxh.add("YJ87A");
        List<String> cpbh = new ArrayList<>();
        List<String> jcbh = new ArrayList<>();
        List<String> gzbw = new ArrayList<>();
        List<String> sglb = new ArrayList<>();
        List<String> yzd = new ArrayList<>();
        List<String> jnw = new ArrayList<>();
        List<String> xzjx = new ArrayList<>();
        List<String> lifnr = new ArrayList<>();
        List<String> lbbh = new ArrayList<>();
        List<String> bgbh = new ArrayList<>();
        QueryPojo queryPojo = new QueryPojo();
        queryPojo.setXxbh(xxbh);
        queryPojo.setPsjd(psjd);
        queryPojo.setCpsmjd(cpsmjd);
        queryPojo.setJxdd(jxdd);
        queryPojo.setStartTime(startTime);
        queryPojo.setEndTime(endTime);
        queryPojo.setCpxh(cpxh);
        queryPojo.setCpbh(cpbh);
        queryPojo.setJcbh(jcbh);
        queryPojo.setGzbw(gzbw);
        queryPojo.setSglb(sglb);
        queryPojo.setYzd(yzd);
        queryPojo.setJnw(jnw);
        queryPojo.setXzjx(xzjx);
        queryPojo.setLifnr(lifnr);
        queryPojo.setLbbh(lbbh);
        queryPojo.setBgbh(bgbh);
        queryPojo.setStartitem(0);
        queryPojo.setPageitem(20);
//数据查询
//        queryPojo.setExport(true);
//        Object resultJson = iReportServiceFS50.selectData_02(queryPojo);
//        Object result_a = resultJson.toString();
//        System.out.println(result_a);

        //数据导出
        queryPojo.setExport(true);
        Object resultJson = iReportServiceFS50.selectData_04(queryPojo);
        if (resultJson != null && queryPojo.isExport()) {
            ExcelExportUtil ee = (ExcelExportUtil) resultJson;
            ee.writeFile("C://Users//Administrator//Desktop//temp//厂外铁路产品质量信息表-国铁服务中心总表666.xlsx");
            ee.dispose();
            System.out.println("国铁服务中心总表Export success.");
        }

    }

@Test
    public void importData() throws DataAccessException, org.apache.poi.openxml4j.exceptions.InvalidFormatException, ParseException {

	String filePath = "C:\\Users\\Administrator\\Desktop\\最新FS功能包\\KPI\\质量模板改之后的"
			+ "\\导入成功的模板\\厂外铁路产品质量信息表-2017年数据.xlsx";
        
        try {
            String result = (String) iReportServiceFS50.importData(filePath);
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


    //导出模板测试
    @Test
    public void downloadTemplate() throws IOException {
        Object resultJson = iReportServiceFS50.downloadTemplate();
        if (resultJson != null) {
        	ExcelExportUtil2227 ee = (ExcelExportUtil2227) resultJson;
            ee.writeFile("C://Users//Administrator//Desktop//5555555.xlsx");
            ee.dispose();
            System.out.println("模板Export success.");
        }

    }
    
    
    //导出模板测试
    @Test
    public void test() throws IOException {
    	String aString="12";
    	System.out.println(StringUtils.isNotEmpty(aString));
    	System.out.println(StringUtils.isNotEmpty(aString)?0.00:Double.parseDouble(aString));
        
    }
}
