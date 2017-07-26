package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS46;
import com.yjdj.view.core.mapper.IReportMapperFS46;
import com.yjdj.view.core.util.ExcelExportUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhuxuan on 2016/12/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS46Test {
    @Autowired
    private IReportMapperFS46 iReportMapperFS46;
    @Autowired
    private IReportServiceFS46 iReportServiceFS46;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testMapper(){
        List<String> trainmodel = Lists.newArrayList("CRH5A","CRH2A","CRH3");
        int start = 0;
        int limit = 100;
        int flag = 1;
        List<THM_FS46> list = iReportMapperFS46.selectUserData(trainmodel,start,limit,flag);
        for(THM_FS46 tmp:list){
            System.out.println(tmp.toString());
        }
    }

    @Test
    public void testMapper3() throws JsonProcessingException{
        List<String> l = Lists.newArrayList("1", "2", "3");
        String s1 = mapper.writeValueAsString(l);
        System.out.println(s1);

        List<THM_FS46> list = iReportMapperFS46.selectAllTrainModel("");
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(list);
        System.out.println(s);

        List<String> str = iReportServiceFS46.getAllTrianModel("");
        System.out.println(str);
    }
    @Test
    public void testService() throws JsonProcessingException,IOException{
//        QueryBean queryBean = new QueryBean();
//        List<String> trainmodel = Lists.newArrayList("CRH5A","CRH2A","CRH3");
//        int start = 0;
//        int limit = 1000;
//        boolean isExport = false;
//        queryBean.setTrainmodel(trainmodel);
//        queryBean.setStartitem(start);
//        queryBean.setPageitem(limit);
//        queryBean.setExport(isExport);
//        Object obj = iReportServiceFS46.getReportJson(queryBean);
//        System.out.println(obj.toString());
//        if (obj != null && queryBean.isExport()) {
//            ExcelExportUtil ee = (ExcelExportUtil) obj;
//            ee.writeFile("C://Users//zhuxuan//Desktop//FS56表格测试//test.xlsx");
//            ee.dispose();
//            System.out.println("Export success.");
//        }
    }
    @Test
    public void testImport() throws DataAccessException, JsonProcessingException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException{
//        String filePath = "C://Users//zhuxuan//Desktop//test.xlsx";
//        Object result = iReportServiceFS46.importData(filePath);
//        System.out.println("result++++++"+result.toString());
    }
    @Test
    public void Int(){
        String error = "";
        String a = "1.1";
        boolean is = true;
        try {
            Integer.parseInt(a);
        }catch (Exception e){
            error = e.getMessage();
            System.out.println("++++++++++++++++++++++++++++++++"+e.toString().split(":")[0]);
        }finally {
            System.out.println("++++++++++++++++++++++++++++++++"+error);
        }
    }
}
