package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.MdmTdocType;
import com.yjdj.view.core.entity.mybeans.MdmTperson;
import com.yjdj.view.core.mapper.GenericMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 16/10/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class GenericServicTest {
    @Autowired
    private GenericService genericService;

    @Autowired
    private GenericMapper genericMapper;

    @Test
    public void getStorlog(){
        try {
            System.out.println(genericService.getListLgort("","仓库",0,10));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getXasuppty(){
        try {
            System.out.println(genericService.getListTvendorType("B","",0,10));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

//        System.out.println(genericMapper.getListTxasuppty("","",0,10));
    }

    @Test
    public void getVbeln(){
        try {
            System.out.println(genericService.getVbeln("THM_VBAP_02", null, 0, 15));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getListPerson(){
        try {
            String personList = genericService.getListPerson(null,null);
            System.out.print(personList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getListTcompCodeJson(){
        List<String> list = new ArrayList<String>();
        list.add("0MB1");
        list.add("5003");
        list.add("5004");
        try {
            String json = genericService.getListTcompCodeJson(list);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getListDoc(){
        List<MdmTdocType> list = genericMapper.getListPurDoc(null,null);
        System.err.println(list.size());
    }

    @Test
    public void getStringDoc(){
        try {
            String json = genericService.getListSalesDoc(null,null);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getListAuart(){
        try {
            String json = genericService.getAllAuart("THM_VBAP_02");
            System.err.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getListTcustomer(){
        try {
            String json = genericService.getAllTcustomer("THM_VBAP_02",null,null);
            System.err.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询物料列表
     */
    @Test
    public void getListTmaterial(){
        try {
            String json = genericService.getListTmaterial(0,10);
            System.err.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getListTmaterialLike(){
        try {
            String json = genericService.getListTmaterial("1",null);
            System.err.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getListTmaterialLikeLimit(){
        try {
            String json = genericService.getListTmaterial("1","电",0,2);
            System.err.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询销售部门
     */
    @Test
    public void getListTsalesOff(){
        try {
            String json = genericService.getListTsalesOff(0,10);
            System.err.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    /**
     * 模糊查询销售部门
     */
    @Test
    public void getListTsalesOffLike(){
        try {
            String json = genericService.getListTsalesOff(null,"部门");
            System.err.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模糊查询销售部门
     */
    @Test
    public void getListTsalesOffLikeLimit(){
        try {
            String json = genericService.getListTsalesOff("001",null,0,2);
            System.err.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询销售组织
     */
    @Test
    public void getListTsalesOrg(){
        try {
            String json = genericService.getListTsalesOrg(0,10);
            System.err.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    /**
     * 模糊查询销售组织
     */
    @Test
    public void getListTsalesOrgLike(){
        try {
            String json = genericService.getListTsalesOrg("1","组织");
            System.err.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模糊查询销售组织
     */
    @Test
    public void getListTsalesOrgLikeLimit(){
        try {
            String json = genericService.getListTsalesOrg(null,"组织",0,2);
            System.err.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getListTplant(){
        List<String> list = new ArrayList<String>();
        list.add("1000");
        list.add("2000");

        String strList = list.toString().replace("[","\'").replace("]","\'").replace(" ","");

        System.out.println(genericMapper.getListTplantByList(strList));
    }

}
