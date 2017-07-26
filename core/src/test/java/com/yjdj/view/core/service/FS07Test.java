package com.yjdj.view.core.service;

import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS07;
import com.yjdj.view.core.mapper.IReportMapperFS07;
import com.yjdj.view.core.util.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcyj on 2016/10/24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)

//用于测试mapper
public class FS07Test {
    @Autowired

    private IReportServiceFS07 iReportServiceFS07;

    @Autowired
    private IReportMapperFS07 iReportMapperFS07;

    @Test
    public void selectDataTrue_07() {
        List<String> bukrsValue = new ArrayList<String>();

        bukrsValue.add("5142");
//        bukrsValue.add("5004");

        List<String> kunnrValue = new ArrayList<String>();
//        kunnrValue.add("0000003000");
//        kunnrValue.add("1000300400");
//        kunnrValue.add("5000000206");

        List<String> zkunnrValue = new ArrayList<String>();
        zkunnrValue = kunnrValue;

        String dateYearMonthStart = "2016011";
        String dateYearMonthEnd = "2016011";
        String budatstart="201611";
        String budatend="201611";
        String date1 = "20160430";
        String date2 = "20151031";
        String date3 = "20141031";
        String date4 = "20131031";
        String date5 = "20121031";
        String date6 = "20111031";
        String date7 = "20101031";
        String date8 = "20091031";
        String date9 = "20091031";
        String date10 = "20091031";
        String date11 = "20091031";
        String date12 = "20091031";
        String date13 = "20091031"; 
        int startitem = 0;
        int pageitem = 20;
        int flag = 0;

        List<THM_FS07> list = iReportMapperFS07.selectDataTrue_07(bukrsValue, kunnrValue, zkunnrValue, dateYearMonthStart, dateYearMonthEnd, budatstart,budatend,date1, date2, date3, date4, date5, date6, date7, date8,date9,date10,date11,date12,date13,startitem,pageitem,flag);
//        List<THM_FS07> list = iReportServiceFS07.selectDataTrue_07(bukrsValue,kunnrValue,zkunnrValue,dateYearMonthStart,dateYearMonthEnd,date1,date2,date3,date4,date5,date6,date7,date8);
        System.out.println(list.size());
        for (THM_FS07 f : list) {
            System.out.println(f.toString());
        }
    }
    //测试Service
    @Test
    public void TEST2()throws IOException,ParseException{
        List<String> bukrsValue = new ArrayList<String>();

//        bukrsValue.add("5003");
//        bukrsValue.add("5004");

        List<String> kunnrValue = new ArrayList<String>();
//        kunnrValue.add("0000003000");
//        kunnrValue.add("1000300400");
//        kunnrValue.add("5000000206");


        String dateYearMonthStart = "201609";
        String dateYearMonthEnd = "201610";

        QueryBean queryBean = new QueryBean();
        queryBean.setCompCodeValue(bukrsValue);
        queryBean.setKunnrValue(kunnrValue);
        queryBean.setDateYearMonthStart(dateYearMonthStart);
        queryBean.setDateYearMonthEnd(dateYearMonthEnd );
        queryBean.setStartitem(0);
        queryBean.setPageitem(20);


        Object list = iReportServiceFS07.selectDataTrue(queryBean,null);

        System.out.println(list.toString());


    }

//计算账龄日期输出测试
    @Test
    public void test(){

        try {

            String date1 = DateUtils.getDateStr("2016012");
            System.out.println(DateUtils.getDayBeforeStr(date1,0));
            String date2 = DateUtils.getDateStr("2016012");
            System.out.println(DateUtils.getDayBeforeStr(date1,30));
            String date3 = DateUtils.getDateStr("2016012");
            System.out.println(DateUtils.getDayBeforeStr(date1,360));
            String date4 = DateUtils.getDateStr("2016012");
            System.out.println(DateUtils.getDayBeforeStr(date1,720));
            String date5 = DateUtils.getDateStr("2016012");
            System.out.println(DateUtils.getDayBeforeStr(date1,1080));
            String date6 = DateUtils.getDateStr("2016012");
            System.out.println(DateUtils.getDayBeforeStr(date1,1440));
            String date7 = DateUtils.getDateStr("2016012");
            System.out.println(DateUtils.getDayBeforeStr(date1,1800));
            String date8 = DateUtils.getDateStr("2016012");
            System.out.println(DateUtils.getDayBeforeStr(date1,1801));



        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void upload(){
        try {
            List<String> list = new ArrayList<String>();
            list.add("5142");
            iReportServiceFS07.importData("/Users/wangkai/Downloads/aaa.xlsx",list);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}


