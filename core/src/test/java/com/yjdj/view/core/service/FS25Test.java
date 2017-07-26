package com.yjdj.view.core.service;

import com.yjdj.view.core.mapper.IReportMapperFS25;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 2016/11/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS25Test {
    @Autowired
    private IReportMapperFS25 iReportMapperFS25;

    @Test
    public void getListMap(){
        List<Map<String,Object>> listMap = iReportMapperFS25.getListMap("000010045648");
        System.err.println(listMap.size()+"----------");
        for(Map<String,Object> map : listMap){
            System.out.println(map);
        }
    }
}
