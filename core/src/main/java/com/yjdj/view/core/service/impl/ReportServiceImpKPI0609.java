package com.yjdj.view.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.mapper.IReportMapperKPI0609;
import com.yjdj.view.core.service.IReportServiceKPI0609;

/**
 * Created by liwuhua on 16/11/7.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
@SuppressWarnings("all")
public class ReportServiceImpKPI0609 implements IReportServiceKPI0609 {

    @Autowired
    private IReportMapperKPI0609 ireportmapperkpi0609;

    private final Logger log = Logger.getLogger(ReportServiceImpKPI0609.class);

    /**
     * KPI-销售收入指标及完成情况
     *
     * @return json
     */
    @Override
    public String incomeAndComple(String yearmonth) throws DataAccessException, JsonProcessingException {
        String json = "{}";
        if (StringUtils.isNotEmpty(yearmonth)) {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap yearMap = new HashMap<>();
            yearMap.put("YEARMONTH", yearmonth);
            Map incomeAndCompleIndex = ireportmapperkpi0609.incomeAndCompleIndex(yearMap);
            if(incomeAndCompleIndex!=null){
                return objectMapper.writeValueAsString(incomeAndCompleIndex);
            }
            return json;
        }
        return json;
    }

    /**
     * KPI-销售收入实际下钻 下钻1层
     *
     * @return json
     */
    @Override
    public String saleIncomeFiLayer(String yearmonth) throws JsonProcessingException, DataAccessException {
        String json = "{}";
        if (StringUtils.isNotEmpty(yearmonth)) {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap yearMap = new HashMap<>();
            yearMap.put("YEARMONTH", yearmonth);
            List<Map> saleIncomeFiLayer = ireportmapperkpi0609.saleIncomeFiLayer(yearMap);
            json = objectMapper.writeValueAsString(saleIncomeFiLayer);
            return json;
        }
        return json;
    }

    /**
     * KPI-点选某市场板块下钻至客户
     *
     * @return json
     */
    @Override
    public String getcustoByKdgrp(String yearmonth, String kdgrp,String flag) throws JsonProcessingException, DataAccessException {

        String json = "{}";
        if (StringUtils.isNotEmpty(yearmonth) && StringUtils.isNotEmpty(kdgrp)) {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap yearAndkdgrp = new HashMap<>();
            yearAndkdgrp.put("YEARMONTH", yearmonth);
            yearAndkdgrp.put("KDGRP", kdgrp);
            yearAndkdgrp.put("FLAG", flag);
            List<Map> custoByKdgrp = ireportmapperkpi0609.getcustoByKdgrp(yearAndkdgrp);
            json = objectMapper.writeValueAsString(custoByKdgrp);
            return json;
        }
        return json;
    }

    /**
     * KPI-年度合同签订金额下钻
     *
     * @return json
     */
    @Override
    public String anSignedLayer(String yearmonth) throws JsonProcessingException, DataAccessException {
        String json = null;

        if (StringUtils.isNotEmpty(yearmonth)) {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap monthMap = new HashMap<>();
            monthMap.put("YEARMONTH", yearmonth);
            List<Map> signedLayer = ireportmapperkpi0609.signedLayer(monthMap);
            json = objectMapper.writeValueAsString(signedLayer);
            return json;
        }
        return json;
    }
    /**
     * KPI-饼图的数据
     *
     * @return json
     */
    @Override
    public String getRatePie(String yearmonth) throws JsonProcessingException, DataAccessException {
        String json = "[]";
        boolean flag = true;
        if (StringUtils.isNotEmpty(yearmonth)) {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap yearMap = new HashMap<>();
            yearMap.put("YEARMONTH", yearmonth);
            List<Map> signedLayer = ireportmapperkpi0609.getRatePie(yearMap);
            List<Map> signedLayerData = new ArrayList<Map>();
            for(Map map:signedLayer){
            	if(map != null){
            		if("0".equals(map.get("ZNUM").toString().trim())){
            			flag = false;
            		}
            	}
            }
            if(flag){
            	Map<String,String> map = new HashMap<String, String>();
            	map.put("ZNUM", "0");
            	map.put("", "未指定销售部门");
            	map.put("BUDGET", "0");
            	map.put("TOTALACTUAL", "0");
            	signedLayerData.add(map);
            }
            for(Map map:signedLayer){
            	signedLayerData.add(map);
            }
            json = objectMapper.writeValueAsString(signedLayerData);
            return json;
        }
        return json;
    }

    /**
     * KPI-饼图钻取获得业务部门金额及比率 param: 年+市场板块
     *
     * @return json
     */
    @Override
    public String getBussiApart(String year, String znum) throws JsonProcessingException, DataAccessException {
        String json = null;
        if (StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(znum)) {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap yearMap = new HashMap<>();
            yearMap.put("YEAR", year);
            yearMap.put("ZNUM", znum);
            List<Map> bussiApart = ireportmapperkpi0609.getBussiApart(yearMap);
            json = objectMapper.writeValueAsString(bussiApart);
            return json;
        }
        return json;

    }

    /**
     * KPI-饼图饼图钻取通过业务部门获得客户金额和占比
     *
     * @return json
     */
    @Override
    public String getCustByApart(String year, String znum, String vkbur) throws JsonProcessingException, DataAccessException {
        String json = null;
        if (StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(znum) && StringUtils.isNotEmpty(vkbur)) {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap yearMap = new HashMap<>();
            yearMap.put("YEAR", year);
            yearMap.put("ZNUM", znum);
            yearMap.put("VKBUR", vkbur);
            List<Map> custByApart = ireportmapperkpi0609.getCustByApart(yearMap);
            json = objectMapper.writeValueAsString(custByApart);
            return json;
        }
        return json;

    }

}
