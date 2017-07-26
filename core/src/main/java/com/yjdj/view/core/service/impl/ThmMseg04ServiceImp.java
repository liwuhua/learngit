package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.mapper.IReportMapperThmMseg04;
import com.yjdj.view.core.mapper.IReportMapperThmVbap01;
import com.yjdj.view.core.service.IThmMseg04Service;
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
public class ThmMseg04ServiceImp implements IThmMseg04Service{

    @Autowired
    private IReportMapperThmMseg04 iReportMapperThmMseg04;


    @Override
    public String getEbeln(String ebeln, int start, int limit) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> stringList = iReportMapperThmMseg04.getEbeln(ebeln,start,limit);
        String json = mapper.writeValueAsString(stringList);
        return json;
    }
}
