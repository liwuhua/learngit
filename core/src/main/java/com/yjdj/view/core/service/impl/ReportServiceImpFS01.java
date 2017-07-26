package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yjdj.view.core.entity.mybeans.QueryBean;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.THM_MSEG_03;
import com.yjdj.view.core.mapper.IReportMapperFS01;
import com.yjdj.view.core.service.IReportServiceFS01;
import com.yjdj.view.core.util.ExcelExportUtil;

import org.springframework.dao.DataAccessException;

/**
 * 采购入库金额统计表 create on 2016/10/21
 *
 * @author zhangwenguo
 *
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS01 implements IReportServiceFS01 {

    private final Logger log = Logger.getLogger(ReportServiceImpFS01.class);

    @Autowired
    private IReportMapperFS01 iReportMapperFS01;
    private String resultJson = null;
    private int startitem = 0;
    private int pageitem = 0;
    private List<String> plantValue = null;
    private List<String> matnrValue = null;
    private List<String> vendorValue = null;
    private List<String> purGroupValue = null;
    private String startTime = null;
    private String endTime = null;
    private boolean isShow = false;
    private boolean isExport = false; //如果是true则为导数据，false则为查数据
    private List<THM_MSEG_03> data = null;
    private List<List<Object>> resultDataRowList = null;
    private List<String> headerList = null;
    private ExcelExportUtil ee = null;
    private List<Map<String, String>> matnrInterval = null;
    private List<THM_MSEG_03> totalMengeAndBualt = null;

    @Override
    public Object getReportJson(QueryBean queryBean) throws JsonProcessingException, IOException ,DataAccessException{
    	Row row = null;
        init(queryBean);
        int flag = 0;//查询flag为0，导出flag为1
        if (isExport) {
            flag = 1;
        }
        if (queryBean.isShow()) {
            //如果“显示公司间交易业务”复选为是
            data = iReportMapperFS01.selectDataTrue(
                    plantValue, matnrValue, vendorValue, purGroupValue,
                    startTime, endTime, matnrInterval, startitem, pageitem, flag);
            //统计金额和数量总和
            totalMengeAndBualt = iReportMapperFS01.selectTotalMengeAndBualtTrue(plantValue, 
            		matnrValue, vendorValue, purGroupValue, startTime, endTime, matnrInterval);
        } else {
            //如果“显示公司间交易业务”复选为否
            data = iReportMapperFS01.selectDataFalse(
                    plantValue, matnrValue, vendorValue, purGroupValue,
                    startTime, endTime, matnrInterval, startitem, pageitem, flag);
            //统计金额和数量总和
            totalMengeAndBualt = iReportMapperFS01.selectTotalMengeAndBualtFalse(plantValue, 
            		matnrValue, vendorValue, purGroupValue, startTime, endTime, matnrInterval);
        }
        if (!isExport) {
            resultJson = getMapListByThmMseg03(data, totalMengeAndBualt);
            return resultJson;
        } else {
            resultDataRowList = export(data);
            String secondTitle = "入库期间：" + startTime.substring(0, 4) + "年"
                    + startTime.substring(4, 6) + "月" + startTime.substring(6, 8) + "日 — "
                    + endTime.substring(0, 4) + "年" + endTime.substring(4, 6)
                    + "月" + endTime.substring(6, 8) + "日";
            ee = new ExcelExportUtil("采购入库金额统计表", secondTitle, headerList);
            List<List<Object>> dataList = Lists.newArrayList();
            for (int j = 0; j < resultDataRowList.size(); j++) {
                dataList.add(resultDataRowList.get(j));
            }
            
            for (int i = 0; i < resultDataRowList.size(); i++) {
            	row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
                    ee.addCell(row, j, dataList.get(i).get(j));
                }
            }
            row = ee.addRow();
            ee.addCell(row, 0, "合计");
            for(int k=1; k < headerList.size(); k++){
            	ee.addCell(row, k, null);
            }
            row = ee.addRow();
            ee.addCell(row, 0, "入库数量：");//MENGE入库数量
            ee.addCell(row, 1, totalMengeAndBualt.get(0).getSum_menge());//MENGE入库数量
            for(int k=2; k < headerList.size(); k++){
            	ee.addCell(row, k, null);
            }
            row = ee.addRow();
            ee.addCell(row, 0, "入库金额：");//本位币金额总量
            ee.addCell(row, 1, totalMengeAndBualt.get(0).getSum_bualt());//本位币金额总量
            for(int k=2; k < headerList.size(); k++){
            	ee.addCell(row, k, null);
            }
            return ee;
        }
    }

    /**
     * 初始化参数
     *
     * @param queryBean
     */
    private void init(QueryBean queryBean) {
        startitem = queryBean.getStartitem();
        pageitem = queryBean.getPageitem();
        plantValue = queryBean.getPlantValue();
        matnrValue = queryBean.getMatnrValue();
        vendorValue = queryBean.getVendorValue();
        purGroupValue = queryBean.getPurGroupValue();
        startTime = queryBean.getStartTime();
        endTime = queryBean.getEndTime();
        isShow = queryBean.isShow();
        isExport = queryBean.isExport();
        matnrInterval = queryBean.getMatnrIntervalMap();
        headerList = Lists.newArrayList();
        headerList.add("序号");
        headerList.add("工厂");
        headerList.add("物料编码");
        headerList.add("物料描述");
        headerList.add("入库数量");
        headerList.add("计量单位");
        headerList.add("入库金额(不含税)");
        headerList.add("价格单位");
        headerList.add("供应商编码");
        headerList.add("供应商描述");
        headerList.add("采购组");

    }

    /**
     * 如果“显示公司间交易业务”复选为是
     *
     * @throws JsonProcessingException
     */
    public String getMapListByThmMseg03(List<THM_MSEG_03> dataList, List<THM_MSEG_03> totalMengeAndBualt) throws JsonProcessingException {
        // TODO Auto-generated method stub
        String json = null;
        Map<String, Object> listMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Double> sumMap = new HashMap<String, Double>();
        if(totalMengeAndBualt.size() == 1 && totalMengeAndBualt.get(0) != null){
        	sumMap.put("bualt",totalMengeAndBualt.get(0).getSum_bualt());
        	sumMap.put("menge",totalMengeAndBualt.get(0).getSum_menge());
        }else{
        	sumMap.put("bualt",0.00D);
        	sumMap.put("menge",0.00D);
        }
        headerList.remove(0);
        listMap.put("SUM", sumMap);
        listMap.put("headersList", headerList);
        listMap.put("procurementStorage", dataList);
        json = mapper.writeValueAsString(listMap);
        return json;
    }

    /**
     * 导出数据
     *
     * @param dataList
     * @throws IOException
     */
    private List<List<Object>> export(List<THM_MSEG_03> data) throws IOException {
        // TODO Auto-generated method stub
        //
        int num = 1;
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list = new ArrayList<Object>();
        for (THM_MSEG_03 value : data) {
            list.add(num);  //序号
            list.add(value.getWerks()); //工厂
            list.add(value.getMatnr());  //物料编码
            list.add(value.getTxtmd()); //物料描述
            list.add(value.getSum_menge());  //MENGE入库数量
            list.add(value.getMeins()); //基本计量单位
            list.add(value.getSum_bualt());  //本位币金额总量
            list.add(value.getPrice_base()); //价格单位
            list.add(value.getLifnr());  //供应商编码
            list.add(value.getVendor()); //供应商描述
            list.add(value.getEkgrp()); //采购组
            num++;
            dataRowList.add(list);
            list = new ArrayList<Object>();
        }
        return dataRowList;
    }

}
