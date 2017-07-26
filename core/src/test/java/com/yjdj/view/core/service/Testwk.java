package com.yjdj.view.core.service;

import com.yjdj.view.core.entity.mybeans.Wktest;
import com.yjdj.view.core.mapper.WkMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

/**
 * Created by wangkai on 16/10/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class Testwk {
    @Autowired
    private WkMapper test2Mapper;

    @Test
    public void test(){
        List<Wktest> list = test2Mapper.testQuery();
        for(Wktest wt : list){
            System.out.println(wt.getMaterial());
        }
    }
}
