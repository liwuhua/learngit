package com.yjdj.view.core.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS09;
import com.yjdj.view.core.util.ExcelExportUtil09;

/**
 * Created by wangkai on 2016/11/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS09Test {
    @Autowired
    private IReportMapperFS09 iReportMapperFS09;
    @Autowired
    private IReportServiceFS09 iReportServiceFS09;
    @Autowired
    private GenericMapper genericMapper;

    @Test
    public void t(){
        System.out.println(genericMapper.getAllTcompCode("THM_FS09_01","5","永济"));
    }

    @Test
    public void test(){
        List<String> compCodeValue = new ArrayList<String>();
        compCodeValue.add("5003");
        compCodeValue.add("5004");
        compCodeValue.add("5005");

        String str = compCodeValue.toString().replace("[","\'").replace("]","\'").replace(" ","");

        System.out.println(str);

//        System.out.println(iReportMapperFS09.test(str,"2016002","2016006",false));
    }

    @Test
    public void testService(){
//        List<String> compCodeValue = new ArrayList<String>();
//        compCodeValue.add("5003");
//        compCodeValue.add("5004");
//        compCodeValue.add("5005");
//        compCodeValue.add("5006");

        QueryBean queryBean = new QueryBean();
//        queryBean.setCompCodeValue(compCodeValue);
        boolean isExport = true;
        queryBean.setExport(isExport);
        queryBean.setDateYearMonthStart("201610");
        queryBean.setDateYearMonthEnd("201610");

        try {
            System.out.println(iReportServiceFS09.getListFs09(queryBean,null));
            ExcelExportUtil09 ee = (ExcelExportUtil09) iReportServiceFS09.getListFs09(queryBean,null);
            ee.writeFile("C://Users//xiaozhang//Desktop//表清单统计//export09.xlsx");
    		ee.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void importTest() throws DataAccessException, JsonProcessingException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException{
    	String filePath = "C://Users//xiaozhang//Desktop//临时文件//制造费用明细表.xlsx";
        List<String> compCodeList = new ArrayList<String>();
        compCodeList.add("5003");
        compCodeList.add("5004");
        compCodeList.add("5005");
    	Object result = iReportServiceFS09.importData(filePath, compCodeList);
    	System.out.println("result+++++++++++++++"+result.toString());
    	
    }
    
    @Test
    public void test2(){
        Set<String> set1 = new HashSet<String>();
        set1.add("a");
        Set<String> set2 = new HashSet<String>();
        set2.add("a");
        set2.add("b");

        set1.removeAll(set2);

        System.err.println(set1.size());
    }
    
    @Test
    public void test3(){
    	System.out.println(iReportMapperFS09.test( "5003,5004,5005,5006,5025,5026,5142", "2015010", "2015010", true, "year"));
    }

    
}
