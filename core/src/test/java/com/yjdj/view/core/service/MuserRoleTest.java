package com.yjdj.view.core.service;

import com.yjdj.view.core.entity.MuserRole;
import com.yjdj.view.core.mapper.IReportMapperFS08;
import com.yjdj.view.core.mapper.MuserRoleMapper;
import com.yjdj.view.core.util.NumProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

/**
 * Created by wangkai on 16/10/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class MuserRoleTest {

    @Autowired
    private MuserRoleMapper muserRoleMapper;


    @Test
    public void testMa(){
        List<MuserRole> roles = muserRoleMapper.getRoles("40");

        for(MuserRole role : roles){
            System.out.println(role.getId()+"--"+role.getName());
        }
    }

}
