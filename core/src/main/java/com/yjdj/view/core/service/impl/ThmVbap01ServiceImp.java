package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.mapper.IReportMapperThmVbap01;
import com.yjdj.view.core.service.IThmVbap01Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

/**
 * Created by wangkai on 2016/11/4.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ThmVbap01ServiceImp implements IThmVbap01Service{

    @Autowired
    private IReportMapperThmVbap01 iReportMapperThmVbap01;

    @Override
    public String getVbeln(String tableName,String vbeln, int start, int limit) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> stringList = iReportMapperThmVbap01.getVbeln(tableName,vbeln,start,limit);
        String json = mapper.writeValueAsString(stringList);
        return json;
    }
    
    @Override
    public String getAufnr(String tableName,String aufnr, int start, int limit) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> stringList = iReportMapperThmVbap01.getAufnr(tableName,aufnr,start,limit);
        String json = mapper.writeValueAsString(stringList);
        return json;
    }
    
    
    @Override
    public String getLinenum(String tableName,String linenum, int start, int limit) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> stringList = iReportMapperThmVbap01.getLinenum(tableName,linenum,start,limit);
        String json = mapper.writeValueAsString(stringList);
        return json;
    }
    
    @Override
    public String getProductmodel(String tableName,String productmodel, int start, int limit) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> stringList = iReportMapperThmVbap01.getProductmodel(tableName,productmodel,start,limit);
        String json = mapper.writeValueAsString(stringList);
        return json;
    }

    @Override
    public String getVbelnByOrgAndBur(List<String> vkorgValue, List<String> vkburValue, String vbeln, Integer start, Integer limit) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> stringList = iReportMapperThmVbap01.getVbelnByOrgAndBur(vkorgValue,vkburValue,vbeln,start,limit);
        String json = mapper.writeValueAsString(stringList);
        return json;
    }
    
    
}
