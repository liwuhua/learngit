package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS29;
import com.yjdj.view.core.service.IReportServiceFS29;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by liwuhua on 16/11/7.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
@SuppressWarnings("all")
public class ReportServiceImpFS29 implements IReportServiceFS29 {

    @Autowired
    private IReportMapperFS29 ireportmapperfs29;

    @Autowired
    private GenericMapper genericMapper;
    private final Logger log = Logger.getLogger(ReportServiceImpFS29.class);

    @Override
    public Object getListFs29(QueryBean queryBean, List<String> plantList) throws DataAccessException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String resultJson = "{\"lackorder\":[]}";
        String mingstrp = queryBean.getStartTimeThree(); // AND GSTRP >= 起始基本开始日期 
        String maxgstrp = queryBean.getEndTimeThree();  //AND GSTRP <=  结束基本开始日期
        String delidatetime = queryBean.getStartTime();// DELIDATE = 查询条件中的报告日期 
        String analinbs = queryBean.getYlzd(); //模拟库存量的标识   1代表大于等于0   2代表大于0  3代表两种都包括
        if ((!"1".equals(analinbs)) && (!"2".equals(analinbs)) && (!"3".equals(analinbs))) {
            return resultJson;
        }

        List<String> matlgroupvalue = queryBean.getMatlGroupValue(); //组件物料号 多值
        List<String> aufnrValue = queryBean.getVkorgValue();  // and AUFNR  in  生产订单多值
        List<String> werksValue = queryBean.getWerksValue(); //and WERKS  in 工厂多值

	     	// 此处需做判断  空值情况下  如果有权限工厂那么查询的是权限范围内所有工厂    如果没有权限工厂  那么返回没有权限
        //werksValue工厂   plantList权限工厂
        if (CollectionUtils.isEmpty(werksValue) && CollectionUtils.isEmpty(plantList)) {
            //权限中没有工厂
            return AjaxObject.newError("没有分配权限工厂").setCallbackType("").toString();
        } else if (CollectionUtils.isEmpty(werksValue) && (!CollectionUtils.isEmpty(plantList))) {
            //将查询条件中的工厂置为查询出所有的权限工厂
            werksValue = new ArrayList<String>();
            werksValue.addAll(plantList);
        }

        List<String> modelValue = queryBean.getKunnrValue();  //产品型号
        List<String> vbelnValue = queryBean.getVbelnValue();   // and KDAUF  in 销售订单 
        List<String> linenumValue = queryBean.getDwerkValue();  //and LINENUM  in  销售订单行项目    
        List<String> matnrValue = queryBean.getMatnrValue(); //    AND MATNR  IN   生产订单物料编码 
        List<String> zhwcxValue = queryBean.getVendorValue(); //  AND ZHWCX  IN  工作中心

        List<String> purGroupValue = queryBean.getPurGroupValue(); //采购组   and EKGRP in
        List<String> lifnrValue = queryBean.getOneValue();   //供应商编码
        List<String> zpsfsValue = queryBean.getTwoValue();  // 配送方式
        List<String> dauatValue = queryBean.getDauatValue();//生产订单类型

        boolean isExport = queryBean.isExport();  //是否是导
        boolean isExclu1 = queryBean.isExclu1();  //isExclu1   and EKGRP in   采购组        排他的标识
        boolean isExclu2 = queryBean.isExclu2();   //isExclu2   and LIFNR in 供应商编码   排他的标识
        boolean isExclu3 = queryBean.isShow();    //isExclu3   and ZPSFS  in  配送方式  排他的标识

        List<String> comppurtypeValue = queryBean.getComppurtypeValue(); // AND COMPPURTYPE IN 组件采购类型
        Integer startitem = queryBean.getStartitem();
        Integer pageitem = queryBean.getPageitem();
        List<String> headerList = Lists.newArrayList();
//            int flag = 0;//查询flag为0，导出flag为1
        if (isExport) {
// 	            flag = 1;

            headerList.add("序号");
            headerList.add("报告日期");
            headerList.add("工厂");
            headerList.add("工作中心");
            headerList.add("销售订单/行号");
            headerList.add("产品型号");
            headerList.add("生产订单");
            headerList.add("生产订单物料编码");
            headerList.add("生产订单物料编码描述");
            headerList.add("基本开始日期");
            headerList.add("组件物料编码");
            headerList.add("组件物料描述");
            headerList.add("需求量");
            headerList.add("模拟库存量");
            headerList.add("计量单位");
            headerList.add("单据号/行号");
            headerList.add("订单数量");
            headerList.add("计划交货日期");
            headerList.add("实际交货日期");
            headerList.add("收货数量");
            headerList.add("采购组");
            headerList.add("MRP控制者");
//            headerList.add("原因分析");
//            headerList.add("预计到货日期");
//            headerList.add("是否满足生产计划要求");
        }

        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("matlgroupValue", matlgroupvalue);
        paraMap.put("analinbs", analinbs);
        paraMap.put("delidatetime", delidatetime);
        paraMap.put("aufnrValue", aufnrValue);
        paraMap.put("werksValue", werksValue);
        paraMap.put("modelValue", modelValue);
        paraMap.put("vbelnValue", vbelnValue);
        paraMap.put("linenumValue", linenumValue);
        paraMap.put("matnrValue", matnrValue);
        paraMap.put("zhwcxValue", zhwcxValue);
        paraMap.put("mingstrp", mingstrp);
        paraMap.put("maxgstrp", maxgstrp);

        paraMap.put("purGroupValue", purGroupValue);
        paraMap.put("lifnrValue", lifnrValue);
        paraMap.put("zpsfsValue", zpsfsValue);
        paraMap.put("dauatValue", dauatValue);
        paraMap.put("comppurtypeValue", comppurtypeValue);
        paraMap.put("startitem", startitem);
        paraMap.put("pageitem", pageitem);

        paraMap.put("isExport", isExport);
        paraMap.put("isExclu1", isExclu1);
        paraMap.put("isExclu2", isExclu2);
        paraMap.put("isExclu3", isExclu3);
//	        	paraMap.put("flag", flag);

        // 报告日期 DELIDATE  基本开始日期  mingstrp  maxgstrp   必选的  其他不适必选的
        if (StringUtils.isNotBlank(delidatetime)) {
            HashMap resultMap = new HashMap<>();
            paraMap.put("tableNameSuffix", delidatetime.substring(0, 6));
            List<HashMap> lackorder = ireportmapperfs29.getLackorder(paraMap);
            if (!isExport) {
                resultMap.put("lackorder", lackorder);
                resultJson = mapper.writeValueAsString(resultMap);
                log.debug(resultMap + " resultMap !!");
                return resultJson;
            } else {

                List<List<Object>> resultDataRowList = exportExcel(lackorder);
                String secondTitle = null;
                ExcelExportUtil ee = new ExcelExportUtil("生产订单缺料统计分析表", secondTitle, headerList);
                List<List<Object>> dataList = Lists.newArrayList();
                for (int j = 0; j < resultDataRowList.size(); j++) {
                    dataList.add(resultDataRowList.get(j));
                }
                for (int i = 0; i < resultDataRowList.size(); i++) {
                    Row row = ee.addRow();
                    for (int j = 0; j < dataList.get(i).size(); j++) {
//	                              ee.addCell(row, j, dataList.get(i).get(j));
                        ee.addCell(row, row.getRowNum(), j, dataList.get(i).get(j), 2, true, String.class);
                    }
                }
                return ee;

            }

        }
        return resultJson;

    }

    //导出数据通用 (数据排序)
    private List<List<Object>> exportExcel(List<HashMap> result) throws IOException {
        int num = 1;
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list_ex = new ArrayList<Object>();
        for (Map value : result) {
            list_ex.add(num + "");  //序号	 
            list_ex.add(value.get("DELIDATE"));
            list_ex.add(value.get("WERKS"));
            list_ex.add(value.get("ZHWCX"));
            list_ex.add(value.get("VBLINE"));
            list_ex.add(value.get("RT_SIZE2"));
            list_ex.add(value.get("AUFNR"));
            list_ex.add(value.get("MATNR"));
            list_ex.add(value.get("MATNRTXTMD"));
            list_ex.add(value.get("GSTRP"));
            list_ex.add(value.get("COPOMATNUM"));
            list_ex.add(value.get("ARKTX"));
            list_ex.add(value.get("MNG01"));
            list_ex.add(value.get("ANALINVEN") + "");
            list_ex.add(value.get("MEINS"));
            list_ex.add(value.get("DUCUMNUM"));
            list_ex.add(value.get("ORDERNUM"));
            list_ex.add(value.get("PLANDELIDATE"));
            list_ex.add(value.get("ACTUDELIDATE"));
            list_ex.add(value.get("DELIVNUM") + "");
            list_ex.add(value.get("EKGRP"));
            list_ex.add(value.get("DISPO"));
//            list_ex.add(value.get("CAUSEANALY"));
//            list_ex.add(value.get("ECPECARRIVDATE"));
//            list_ex.add(value.get("ISREQUIPLAN"));
            num++;
            dataRowList.add(list_ex);
            list_ex = new ArrayList<Object>();
        }

        return dataRowList;
    }

}
