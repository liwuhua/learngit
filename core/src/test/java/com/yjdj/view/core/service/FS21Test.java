package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.MdmTmrpcontrl;
import com.yjdj.view.core.entity.mybeans.MdmTperson;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.GenericMapper;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chengxuan on 2016/11/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS21Test {
    @Autowired
    private IReportServiceFS21 iReportServiceFS21;
    @Autowired
    private GenericMapper genericMapper;

    @Test
    public void test(){
        QueryBean qb = new QueryBean();
//        List list1 = new ArrayList();
//        list1.add("CNR0000633001");
//        qb.setMatnrValue(list1);

        List list2 = new ArrayList();
        list2.add("1000");
        list2.add("2000");
        qb.setPlantValue(list2);

        List list7 = new ArrayList();
        list7.add("101");
        qb.setMatlGroupValue(list7);
//        List list3 = new ArrayList();
//        list3.add("A01");
//        list3.add("A02");
//        qb.setPurGroupValue(list3);

        List list4 = new ArrayList();
        list4.add("YJ_2007010");
        qb.setOneValue(list4);

//        List list5 = new ArrayList();
//        list5.add("YJ_2014068");
//        qb.setTwoValue(list5);

        qb.setStartTime("20150504");
        qb.setEndTime("20161231");

//        qb.setStartTimeTwo("20150501");
//        qb.setEndTimeTwo("20161231");


        String str = null;
        try {
            str = iReportServiceFS21.getDataFs21(qb).toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(str);

    }

    @Test
    public void testMrp(){
        List<MdmTmrpcontrl> lists = genericMapper.getListMrpctl("101", null);
        for (MdmTmrpcontrl m : lists){
            System.out.println(m.getMrp_contrl()+"---"+m.getTxtmd());
        }
    }
}
