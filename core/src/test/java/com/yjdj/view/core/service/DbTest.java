package com.yjdj.view.core.service;


import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.yjdj.view.core.entity.mybeans.CarSalesCount;
import com.yjdj.view.core.util.JacksonUtil;


/**
 * Created by Tao.Xu on 2014/9/29.
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class DbTest {

//    @Autowired
//    private DataSource dataSource;
    
    
    @Autowired
    private ChartService chartService;

   /* @Test
    public void test() {

       List<String> channelList= new ArrayList<String>();
        channelList.add("1001");
        channelList.add("1002");
        List<String> osList= new ArrayList<String>();
        osList.add("1");
        osList.add("2");
        Map<String,List<MybatisBase>> list=statPlayVvService.getDimDetail1("20150204","20150209", "DAY","channel","os",osList,channelList,5);

        System.out.println("-------------------------test----"+list.size());

    }*/
  /*  @Test
    public void testStatisticUV(){
    	String lessDay = ViewUtil.getWeekLessDay();
        List<MybatisBase> list=birtService.statisticUVByDaily(ViewUtil.getLastWeekStartDay(),ViewUtil.getLastWeekEndDay(),lessDay,ViewUtil.getLessDay(1));
        System.out.println("--statisticUVByDaily---"+lessDay+"---"+ViewUtil.getLessDay(1)+"--test----"+list.size());
    }
    */

//    @Test
//    public void testKipShow(){
//        List<MybatisBase> list=testService.selectDailyCounts();
//        System.out.println("--------------------test----"+list.size());
//    }
//    
//
//    @Test
//    public void selectCountsAll(){
//    	Integer all = testService.selectCountsAll();
//    	System.out.println(all);
//    }
    
    
    @Test
    public void selecSalescount(){
    	List<CarSalesCount> salesCounts = chartService.selecSalescount();
    	List<Object> listJson = new ArrayList<>();
    	
    	for(CarSalesCount salesCount:salesCounts){
    		String json = JacksonUtil.obj2Json(salesCount);
    		listJson.add(json);
    		System.out.println(json);
    	}
    	
    	System.out.println(listJson);
    }

}

