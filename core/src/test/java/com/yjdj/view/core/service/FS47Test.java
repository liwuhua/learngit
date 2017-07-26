package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.entity.mybeans.THM_FS47_ALLOCATION;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.util.ExcelExportUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS47_MAP;
import com.yjdj.view.core.mapper.IReportMapperFS47;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhuxuan on 2017/3/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS47Test {
    @Autowired
    private IReportMapperFS47 iReportMapperFS47;
    @Autowired
    private IReportServiceFS47 iReportServiceFS47;
    @Autowired
    private GenericMapper genericMapper;
    ObjectMapper mapper = new ObjectMapper();
    @Test
    public void testMapper(){
        THM_FS47_ALLOCATION data = new THM_FS47_ALLOCATION();
        iReportMapperFS47.saveOrUpdateData(data);
    }
    @Test
    public void testImport() throws DataAccessException, JsonProcessingException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException{
        String filePath = "C://Users//zhuxuan//Desktop//导入查询导出表样.xlsx";
        Object obj = iReportServiceFS47.importData(filePath);
        System.out.println("++++++++++++++++Test+++++++++++++++");
    }
    @Test
    public void testReport() throws IOException{
        QueryPojo q = new QueryPojo();
        q.setTrain_type("CRH2A");
        List<String> trainNumList = new LinkedList<>();
//        trainNumList.add("2001");
        q.setTrain_num(trainNumList);
        List<String> repairLvlList = new LinkedList<>();
        repairLvlList.add("二次五级修");
        repairLvlList.add("五级修");
        q.setRepair_level(repairLvlList);
        String obj = (String)iReportServiceFS47.getReportJson(q,1);
        System.out.println("+++++++++++++++++++++++++++++++"+obj);
    }
    @Test
    public void testExport() throws IOException{
        QueryPojo q = new QueryPojo();
        q.setTrain_type("CRH2A");
        List<String> trainNumList = new LinkedList<>();
        trainNumList.add("2001");
        q.setTrain_num(trainNumList);
        List<String> repairLvlList = new LinkedList<>();
        repairLvlList.add("二次五级修");
        repairLvlList.add("五级修");
        q.setRepair_level(repairLvlList);
        ExcelExportUtil ee = (ExcelExportUtil)iReportServiceFS47.getReportJson(q,0);
        ee.writeFile("C://Users//zhuxuan//Desktop//下载模板测试.xlsx");
        System.out.println("+++++++++++++++++++++++++++++++");
    }
    @Test
    public void testOthers(){
/*        List<String> pslb_List_Sort = Lists.newLinkedList();
        pslb_List_Sort.add(1,"1");
        pslb_List_Sort.add(0,"0");
        pslb_List_Sort.add(1,"1");
        System.out.println("+++++++++++++++++++++++++++++++");*/
//        String str = genericMapper.getTest();
//        System.out.println("+++++++++++++++++++++++++++++++"+str);
    }
}
