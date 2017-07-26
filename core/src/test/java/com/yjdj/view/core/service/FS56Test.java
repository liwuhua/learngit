package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS56;
import com.yjdj.view.core.entity.mybeans.THM_FS56_MAP;
import com.yjdj.view.core.mapper.IReportMapperFS56;
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
import java.util.*;

/**
 * Created by zhuxuan on 2017/2/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS56Test {
    @Autowired
    private IReportMapperFS56 iReportMapperFS56;
    @Autowired
    private IReportServiceFS56 iReportServiceFS56;
    ObjectMapper mapper = new ObjectMapper();

    //跟程序无关的测试
    @Test
    public void testA() throws JsonProcessingException{

        StringBuffer str = new StringBuffer();
        String a = "检修";
        String b = "`";
        String c = "";
        str.append(a);
        str.append(b);
        str.append(c);
        String[] s = str.toString().split("`");
        System.out.println(str.toString());
    }

    @Test
    //查询
    public void testChaXun() throws IOException{
        QueryBean q = new QueryBean();
        q.setMotormodel("YJ92A");
        String result = (String) iReportServiceFS56.getReportJson(q,1);
        System.out.println(result);
    }
    @Test
    public void testMapper() throws IOException{
        String str = "YJ92A";
        List<THM_FS56_MAP> columnList = (List) iReportMapperFS56.getColumn(str);
        System.out.println("1");
        String name = "code1,code2,code3,code4";
        List<THM_FS56> list2 = iReportMapperFS56.getData(name,str,"20170101",null,0,20,1);
        System.out.println(mapper.writeValueAsString(list2));
    }
    @Test
    public void testDownload() throws IOException{
        QueryBean q = new QueryBean();
        q.setMotormodel("YJ92A");
        ExcelExportUtil result = (ExcelExportUtil) iReportServiceFS56.getReportJson(q,2);
        result.writeFile("C://Users//zhuxuan//Desktop//FS56表格测试//下载模板测试.xlsx");
    }
    @Test
    public void testUpload() throws DataAccessException, JsonProcessingException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException{
        String filepath = "C://Users//zhuxuan//Desktop//FS56表格测试//测试合并.xlsx";
        Object result = iReportServiceFS56.importData(filepath,0);
        System.out.println("result++++++"+result.toString());
    }
    @Test
    public void a() throws JsonProcessingException{
        String s = "联轴节（安全装置、齿轮）检修";
        char a = 65288;
        s = s.replace(a,'1');
        System.out.println(s);
    }
    @Test
    public void SUXIAOJIESB() throws JsonProcessingException{
        List<String> dianji = iReportServiceFS56.getMotorModel("");
        System.out.println(dianji.get(1)+"++++++++++++++++++++++++++");
    }

}
