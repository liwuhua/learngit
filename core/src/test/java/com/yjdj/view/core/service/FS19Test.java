package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.*;
import com.yjdj.view.core.mapper.IReportMapperFS19;
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
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by zhuxuan on 2016/11/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS19Test {
    @Autowired
    private IReportMapperFS19 iReportMapperFS19;
    @Autowired
    private IReportServiceFS19 iReportServiceFS19;

    @Test
    /**
     * 对Mapper测试
     */
    public void testMapper(){
//        List<String> rbukrs = new ArrayList<>();
//        rbukrs.add("5003");
//        rbukrs.add("5006");
//        rbukrs.add("5005");
//        rbukrs.add("5500");
//        rbukrs.add("5004");
//        rbukrs.add("5025");
//        rbukrs.add("5026");
//        rbukrs.add("5142");
//        String fiscper = "2016008";
//        List<THM_FS19> list = iReportMapperFS19.getResultData(rbukrs,fiscper);
//        for(THM_FS19 tmp : list){
//            System.out.println(tmp.toString());
//        }
//        List<MdmTcompCode> mdmTcompCodes = iReportMapperFS19.getCompCode(null,null);
//        for(MdmTcompCode tmp:mdmTcompCodes){
//            System.out.println(tmp.toString());
//        }
//        List<MdmTcompCode> mdmTcompCodes2 = iReportMapperFS19.getAllTcompCode();
//        for(MdmTcompCode tmp:mdmTcompCodes2){
//            System.out.println(tmp.toString());
//        }
//        List<THM_IMP_FS19> list2 = iReportMapperFS19.getUserImportData(rbukrs,fiscper);
//        for(THM_IMP_FS19 tmp:list2){
//            System.out.println(tmp.toString());
//        }
        InsertFieldTHM_FS19 list = new InsertFieldTHM_FS19();
        list.setRbukrs("1");
        list.setType("1");
        list.setCurtype("1");
        list.setFiscper("1");
        iReportMapperFS19.deleteErrorData(list);
    }

    @Test
    /**
     * 对Service进行测试
     */
    public void testService()throws JsonProcessingException,IOException{
        String fiscper = "201610";
        List<String> rbukrs = new LinkedList<>();
        rbukrs.add("5025");
        rbukrs.add("5005");
        rbukrs.add("5003");
        rbukrs.add("5006");
        rbukrs.add("5004");
        rbukrs.add("5142");
        rbukrs.add("SouthAfrica");
        rbukrs.add("India");
        QueryBean q =new QueryBean();
        q.setCompCodeValue(rbukrs);
        q.setDateYearMonth(fiscper);

        List<String> list = Lists.newArrayList("5003","5005","5025","5006","5004","5142","SouthAfrica","India");

//        try {
//            Object resultJson = iReportServiceFS19.getReportJson(q,list);
//            System.out.println(resultJson);
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        q.setExport(true);
        Object resultJson = iReportServiceFS19.getReportJson(q,list);
        if (resultJson != null && q.isExport()) {
            ExcelExportUtil ee = (ExcelExportUtil) resultJson;
            ee.writeFile("C://Users//zhuxuan//Desktop//货币情况明细表.xlsx");
            ee.dispose();
            System.out.println("Export success.");
        }

    }

    DecimalFormat decimalFormat = new DecimalFormat("0.00");//格式化设置

    @Test
    public void testEverything() throws JsonProcessingException{
        List<Map<String,Object>> t = new ArrayList<>();
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("lanmu","栏目");
        map.put("5003","永济公司");
        map.put("5006","修配公司");
        map.put("5004","西安永电");
        Map<String,Object> map1 = new LinkedHashMap<>();
        map1.put("lanmu","合计");
        List<Map<String,Object>> y = new ArrayList<>();
        Map<String,Object> yb = new LinkedHashMap<>();
        yb.put("yuanbi","123.456");
        yb.put("benweibi","121");
        y.add(yb);
        map1.put("5003", y);
        yb.clear();
        y.clear();
        yb.put("yuanbi", 666.66d);
        Double d = 5551151515.55d;
        yb.put("benweibi",decimalFormat.format(d));
        y.add(yb);
        map1.put("5004", y);
        t.add(map);
        t.add(map1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(t);
        System.out.println(mapper.writeValueAsString(t));

    }
    //测试导入
    @Test
    public void importTest() throws DataAccessException, JsonProcessingException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException{
        QueryBean queryBean = new QueryBean();
        String filePath = "C://Users//zhuxuan//Desktop//货币情况明细表.xlsx";
        List<String> compCodeCheck = Lists.newArrayList("5003","5005","5025","5006","5004","5142","SouthAfrica","India");
        Object result = iReportServiceFS19.importData(filePath,compCodeCheck);
        System.out.println("result++++++"+result.toString());
    }
    @Test
    public void testselectCompanyCodeBytxtmd(){
        List<Map<String,String>> a = iReportMapperFS19.selectCompanyCodeBytxtmd("永济中车电机电器修配有限公司,北车先锋（印度）电气有限公司");
        System.out.println(a.toString());
        String str = iReportMapperFS19.selectLanmu("1");
        System.out.println(str);

    }
}
