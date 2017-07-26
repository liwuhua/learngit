package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_MSEG_06;
import com.yjdj.view.core.entity.mybeans.THM_VBAP_01;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS02;
import com.yjdj.view.core.service.IReportServiceFS02;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

import org.springframework.dao.DataAccessException;

/**
 * Created by liwuhua on 16/10/21.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
@SuppressWarnings("all")
public class ReportServiceImpFS02 implements IReportServiceFS02 {

    @Autowired
    private IReportMapperFS02 ireportmapperfs02;

    @Autowired
    private GenericMapper genericMapper;

    private final Logger log = Logger.getLogger(ReportServiceImpFS02.class);

    private ExcelExportUtil ee = null;

    @Override
    public Object getListFs02Tb1(QueryBean queryBean,List<String> salesorgList) throws JsonProcessingException,DataAccessException {
        ObjectMapper mapper = new ObjectMapper();
        String resultJson = null;

        //改为各自用各自的  可以两者中的一个区间不为空
        if ((StringUtils.isNotBlank(queryBean.getStartTime()) && StringUtils.isNotBlank(queryBean.getEndTime()))) {

            List<String> vkorgValue = queryBean.getVkorgValue(); //销售组织
            if(vkorgValue==null){
            	vkorgValue=new ArrayList<>();
            }
           	// 此处需做判断  空值情况下  如果有权限工厂那么查询的是权限范围内所有销售组织    如果没有权限销售组织  那么返回没有权限
 	     	if(CollectionUtils.isEmpty(vkorgValue)&&CollectionUtils.isEmpty(salesorgList)){
 	     	     //权限中没有工厂
 	     		return AjaxObject.newError("没有分配权限销售组织").setCallbackType("").toString();
 	     	}else if(CollectionUtils.isEmpty(vkorgValue)&&(!CollectionUtils.isEmpty(salesorgList))){
                  //将查询条件中的工厂置为查询出所有的权限工厂
 	     		vkorgValue.clear();
 	     		vkorgValue.addAll(salesorgList);
 	     	}
            
            
            List<String> vkburValue = queryBean.getVkburValue();
            List<String> vbelnValue = queryBean.getVbelnValue();
            List<String> matnrValue = queryBean.getMatnrValue();
            List<String> matnrInterval = queryBean.getMatnrInterval(); //物料多区间

            //获取时间必选
            String startTime = queryBean.getStartTime();
            String endTime = queryBean.getEndTime();
            String productType = queryBean.getProductType();
            int startitem = queryBean.getStartitem(); //起始记录
            int pageitem = queryBean.getPageitem();  //每页显示记录数

            HashMap<String, Object> completecMap = new HashMap<>();
            HashMap resultMap = new HashMap<>();
            //"''" 替换成null
            Object vkorgValue_complete = (vkorgValue == null || vkorgValue.size() == 0) ? null : vkorgValue.toString().replace("[", "").replace("]", "").replace(", ", ",");
            Object vkburValue_complete = (vkburValue == null || vkburValue.size() == 0) ? null : vkburValue.toString().replace("[", "").replace("]", "").replace(", ", ",");
            Object vbelnValue_complete = (vbelnValue == null || vbelnValue.size() == 0) ? null : vbelnValue.toString().replace("[", "").replace("]", "").replace(", ", ",");
            Object matnrValue_complete = (matnrValue == null || matnrValue.size() == 0) ? null : matnrValue.toString().replace("[", "").replace("]", "").replace(", ", ",");
            Object matnrInter_complete = (matnrInterval == null || matnrInterval.size() == 0) ? null : matnrInterval.toString().replace("[", "").replace("]", "").replace(", ", ",");

            completecMap.put("vkorgValue", vkorgValue_complete);
            completecMap.put("vkburValue", vkburValue_complete);
            completecMap.put("vbelnValue", vbelnValue_complete);
            completecMap.put("matnrValue", matnrValue_complete);
            completecMap.put("matnrInterv", matnrInter_complete);

            completecMap.put("startTime", startTime);
            completecMap.put("endTime", endTime);
            completecMap.put("productType", StringUtils.isEmpty(productType) ? null : productType);
            completecMap.put("startitem", startitem);
            completecMap.put("pageitem", pageitem);

            List<THM_MSEG_06> orderComplete = ireportmapperfs02.orderComplete(completecMap);
            List<String> headersList = Lists.newArrayList();
            if (queryBean.isExport()) {
                return orderComplete;
            }
            headersList.add("销售部门");
            headersList.add("排产业务书编号");
            headersList.add("销售订单号");
            headersList.add("行项目");
            headersList.add("物料编码");
            headersList.add("物料描述");
            headersList.add("排产日期");
            headersList.add("要求交付日期");
            headersList.add("计划数量");
            headersList.add("前期累计完成数量");
            headersList.add("当期完成数量");
            headersList.add("准时完成数量");
            headersList.add("准时完成率");
            headersList.add("备注");

            resultMap.put("orderComplete", orderComplete);
            resultMap.put("headersList", headersList);
            resultJson = mapper.writeValueAsString(resultMap);
            log.info(resultJson);
        }
        return resultJson;
    }

    @Override
    public Object getListFs02Tb2(QueryBean queryBean,List<String> salesorgList) throws JsonProcessingException,DataAccessException{
        ObjectMapper mapper = new ObjectMapper();
        String resultJson = null; 
        if ((StringUtils.isNotBlank(queryBean.getStartTimeTwo()) && StringUtils.isNotBlank(queryBean.getEndTimeTwo()))) {
            List<String> vkorgValue = queryBean.getVkorgValue();
            if(vkorgValue==null){
            	vkorgValue=new ArrayList<>();
            }
         	// 此处需做判断  空值情况下  如果有权限工厂那么查询的是权限范围内所有销售组织    如果没有权限销售组织  那么返回没有权限
 	     	if(CollectionUtils.isEmpty(vkorgValue)&&CollectionUtils.isEmpty(salesorgList)){
 	     	     //权限中没有工厂
 	     		return AjaxObject.newError("没有分配权限销售组织").setCallbackType("").toString();
 	     	}else if(CollectionUtils.isEmpty(vkorgValue)&&(!CollectionUtils.isEmpty(salesorgList))){
                  //将查询条件中的工厂置为查询出所有的权限工厂
 	     		vkorgValue.clear();
 	     		vkorgValue.addAll(salesorgList);
 	     	}
            
            List<String> vkburValue = queryBean.getVkburValue();
            List<String> vbelnValue = queryBean.getVbelnValue();
            List<String> matnrValue = queryBean.getMatnrValue();
            List<Map<String, String>> matnrInterval = queryBean.getMatnrIntervalMap();
            //获取时间必选
            String startTimeTwo = queryBean.getStartTimeTwo();
            String endTimeTwo = queryBean.getEndTimeTwo();
            String productType = queryBean.getProductType();
            int startitem = queryBean.getStartitem(); //起始记录
            int pageitem = queryBean.getPageitem();  //每页显示记录数
            boolean export = queryBean.isExport();   //默认不是导出 false

            HashMap<String, Object> staticMap = new HashMap<>();
            HashMap resultMap = new HashMap<>();
            staticMap.put("vkorgValue", vkorgValue);
            staticMap.put("vkburValue", vkburValue);
            staticMap.put("vbelnValue", vbelnValue);
            staticMap.put("matnrValue", matnrValue);
            staticMap.put("matnrInterval", matnrInterval);

            //时间
            staticMap.put("startTimeTwo", startTimeTwo);
            staticMap.put("endTimeTwo", endTimeTwo);
            staticMap.put("productType", productType);
            staticMap.put("startitem", startitem);
            staticMap.put("pageitem", pageitem);
            staticMap.put("flag", export == false ? 0 : 1);  //false(查询)--0查询   

            List<THM_VBAP_01> deStatistics = ireportmapperfs02.deStatistics(staticMap);
            ArrayList<Object> headersList = new ArrayList<>();
            if (queryBean.isExport()) {
                return deStatistics;
            }

            headersList.add("销售部门");
            headersList.add("排产业务书编号");
            headersList.add("销售订单号");
            headersList.add("行项目");
            headersList.add("物料编码");
            headersList.add("物料描述");
            headersList.add("排产日期");
            headersList.add("要求交付日期");
            headersList.add("计划数量");
            headersList.add("备注");
            resultMap.put("deStatistics", deStatistics);
            resultMap.put("headersList", headersList);

            resultJson = mapper.writeValueAsString(resultMap);
        }
        return resultJson;
    }

    @Override
    public ExcelExportUtil getexportExcel1(QueryBean queryBean,List<String> salesorgList) throws JsonProcessingException,DataAccessException{

        List<String> headerList = Lists.newArrayList();
        headerList.add("序号");
        headerList.add("销售部门");
        headerList.add("排产业务书编号");
        headerList.add("销售订单号");
        headerList.add("行项目");
        headerList.add("物料编码");
        headerList.add("物料描述");
        headerList.add("排产日期");
        headerList.add("要求交付日期");
        headerList.add("计划数量");
        headerList.add("前期累计完成数量");
        headerList.add("当期完成数量");
        headerList.add("准时完成数量");
        headerList.add("准时完成率");
        headerList.add("备注");

        List<Object> dataRowList = Lists.newArrayList();
        List<List<Object>> dataList = Lists.newArrayList();
        String ocr = "";
        String onr = "";
        List<THM_MSEG_06> orderComplete = (List<THM_MSEG_06>) getListFs02Tb1(queryBean,salesorgList);
        if (orderComplete != null) {
            if (orderComplete.isEmpty()) {
                //查询数据为空的时候的处理
                for (int i = 1; i <= 1000000; i++) {
                    dataList.add(dataRowList);
                }

            } else {
                //查询数据不为空的时候的处理	
                for (int h = 1; h <= 1000000; h++) {
                    if (h == orderComplete.size() + 1) {
                        break;
                    }
                    dataRowList.add(h);
                    THM_MSEG_06 mseg_06 = orderComplete.get(h - 1); //单个实体类对象
                    
                    ocr = mseg_06.getOcr();
                    onr = mseg_06.getOnr();
                    
                    dataRowList.add(mseg_06.getVkbur());
                    dataRowList.add(mseg_06.getBname());
                    dataRowList.add(mseg_06.getVbeln());
                    dataRowList.add(mseg_06.getPosnr());
                    dataRowList.add(mseg_06.getMatnr());
                    dataRowList.add(mseg_06.getArktx());
                    dataRowList.add(mseg_06.getErdat());
                    dataRowList.add(mseg_06.getEdatu());
                    dataRowList.add(mseg_06.getWmeng());
                    dataRowList.add(mseg_06.getBefore_complete_amount_total());
                    dataRowList.add(mseg_06.getCurrent_complete_amount());
                    dataRowList.add(mseg_06.getOntime_complete_amount());
                    dataRowList.add(mseg_06.getOntime_rate());

                    //1000000行数据  在这里讲数据填充  虽然填充100000次 后续的数值 处理  add添加dataRowList 没值那么dataList也会为空
                    dataList.add(dataRowList);
                    dataRowList = new ArrayList<>();
                }
            }

            String secondTitle = "区间：" + queryBean.getStartTime().substring(0, 4) + "年"
                    + queryBean.getStartTime().substring(4, 6) + "月" + queryBean.getStartTime().substring(6, 8) + "日 — "
                    + queryBean.getEndTime().substring(0, 4) + "年" + queryBean.getEndTime().substring(4, 6)
                    + "月" + queryBean.getEndTime().substring(6, 8) + "日";

            ee = new ExcelExportUtil("销售订单完成统计表", secondTitle, headerList);

            for (int i = 0; i < orderComplete.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
                    ee.addCell(row, row.getRowNum(), j, false, dataList.get(i).get(j));
                }
            }
            Row row = ee.addRow();
            ee.addCell(row, row.getRowNum(), 0, false, "合计");
            ee.addCell(row, row.getRowNum(), 1, false, "订单完成率");
            ee.addCell(row, row.getRowNum(), 2, false, ocr);
            ee.addCell(row, row.getRowNum(), 3, false, "订单正点率");
            ee.addCell(row, row.getRowNum(), 4, false, onr);
        }

        return ee;
    }

    @Override
    public ExcelExportUtil getexportExcel2(QueryBean queryBean,List<String> salesorgList) throws JsonProcessingException,DataAccessException{

        List<String> headerList = Lists.newArrayList();
        headerList.add("序号");
        headerList.add("销售部门");
        headerList.add("排产业务书编号");
        headerList.add("销售订单号");
        headerList.add("行项目");
        headerList.add("物料编码");
        headerList.add("物料描述");
        headerList.add("排产日期");
        headerList.add("要求交付日期");
        headerList.add("计划数量");
        headerList.add("备注");

        List<Object> dataRowList = Lists.newArrayList();
        List<List<Object>> dataList = Lists.newArrayList();
        List<THM_VBAP_01> deStatistics = (List<THM_VBAP_01>) getListFs02Tb2(queryBean,salesorgList);
        if (deStatistics != null) {
            if (deStatistics.isEmpty()) {
                //查询数据为空的时候的处理
                for (int i = 1; i <= 1000000; i++) {
                    dataList.add(dataRowList);
                }

            } else {
                for (int h = 1; h <= 1000000; h++) {
                    if (h == deStatistics.size() + 1) {
                        break;
                    }
                    dataRowList.add(h);
                    THM_VBAP_01 vbap = deStatistics.get(h - 1); //单个实体类对象
                    dataRowList.add(vbap.getVkbur());
                    dataRowList.add(vbap.getBname());
                    dataRowList.add(vbap.getVbeln());
                    dataRowList.add(vbap.getPosnr());
                    dataRowList.add(vbap.getMatnr());
                    dataRowList.add(vbap.getArktx());
                    dataRowList.add(vbap.getErdat());
                    dataRowList.add(vbap.getEdatu());
                    dataRowList.add(vbap.getWmeng());

                    dataList.add(dataRowList);
                    dataRowList = new ArrayList<>();
                }
            }

            String secondTitle = "区间：" + queryBean.getStartTimeTwo().substring(0, 4) + "年"
                    + queryBean.getStartTimeTwo().substring(4, 6) + "月" + queryBean.getStartTimeTwo().substring(6, 8) + "日 — "
                    + queryBean.getEndTimeTwo().substring(0, 4) + "年" + queryBean.getEndTimeTwo().substring(4, 6)
                    + "月" + queryBean.getEndTimeTwo().substring(6, 8) + "日";

            ee = new ExcelExportUtil("销售订单需求统计表", secondTitle, headerList);

            for (int i = 0; i < deStatistics.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
                	ee.addCell(row, row.getRowNum(), j, false, dataList.get(i).get(j));
                }
            }
        }
        return ee;
    }

}
