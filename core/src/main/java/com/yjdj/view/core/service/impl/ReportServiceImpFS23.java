package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS23;
import com.yjdj.view.core.service.IReportServiceFS23;

/**
 * Created by liwuhua on 16/11/28.
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS23 implements IReportServiceFS23{

    @Autowired
    private IReportMapperFS23 ireportmapperfs23;
    
    @Autowired
    private GenericMapper genericMapper;
    private final Logger log = Logger.getLogger(ReportServiceImpFS23.class);

    
		@Override
	    public String getListFs23(String AUFNR) throws JsonProcessingException,DataAccessException {
	        ObjectMapper mapper = new ObjectMapper();
	        String resultJson = null;
            if (StringUtils.isNotBlank(AUFNR)){
	        	    HashMap resultMap=new HashMap<>(); 
		            HashMap paraMap=new HashMap<>();
		            paraMap.put("AUFNR", AUFNR);
		            List<HashMap> orderMarket =ireportmapperfs23.getOrderMarketByaufnr(paraMap);
		            resultMap.put("periodexpense", orderMarket);
		            resultJson = mapper.writeValueAsString(resultMap);
	           }     	

	        return resultJson;
	    }


}
