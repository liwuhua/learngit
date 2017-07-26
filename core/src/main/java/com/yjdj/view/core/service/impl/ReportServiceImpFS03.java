package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.THM_FS03;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.IReportMapperFS03;
import com.yjdj.view.core.service.IReportServiceFS03;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.util.DateUtils;
import com.yjdj.view.core.util.ExcelExportUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.dao.DataAccessException;
import java.text.ParseException;

/**
 * Created by zhaomenglu on 2016/12/14.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS03 implements IReportServiceFS03 {
    @Autowired
    private IReportMapperFS03 iReportMapperFS03;
    private boolean isExport = false; //如果是true则为导数据，false则为查数据
    private List<List<Object>> resultDataRowList = null;
    private List<String> headerList = null;
    private ExcelExportUtil ee = null;
    private String startTime = null;
    private String endTime = null;
    private int startitem = 0;
    private int pageitem = 0;
    
    @Override
    public Object selectDataTrue(QueryBean queryBean) throws JsonProcessingException, IOException, DataAccessException {

        startTime = queryBean.getStartTime();
        endTime = queryBean.getEndTime();
        startitem = queryBean.getStartitem();
        pageitem = queryBean.getPageitem();
        headerList = Lists.newArrayList();
        headerList.add("序号");
        headerList.add("物料编码");
        headerList.add("物料描述");
        headerList.add("采购组");
        headerList.add("当期数量");
        headerList.add("计量单位");
        headerList.add("价格单位");
        headerList.add("含税价_追溯最近年平均价");
        headerList.add("含税价_上年平均价");
        headerList.add("含税价_当年平均价");
        headerList.add("含税价_当期平均价");
        headerList.add("含税价_价差");
        headerList.add("含税价_变动金额");
        headerList.add("不含税价_追溯最近年平均价");
        headerList.add("不含税价_上年平均价");
        headerList.add("不含税价_当年平均价");
        headerList.add("不含税价_当期平均价");
        headerList.add("不含税价_价差");
        headerList.add("不含税价_变动金额");
        int flag = 0;//查询flag为0，导出flag为1
        isExport = queryBean.isExport();
        if (isExport) {
            flag = 1;
        }
        ObjectMapper mapper = new ObjectMapper();
        
        List<THM_FS03> list2 = iReportMapperFS03.selectData(queryBean.getPlantValue(), queryBean.getMatnrValue(), queryBean.getVendorValue(), queryBean.getPurGroupValue(), startitem, pageitem, flag, startTime.substring(0, 6));
        //List<THM_FS03> result = new ArrayList<THM_FS03>();
        //计算价差
        /*THM_FS03 newbean = new THM_FS03();
        for (THM_FS03 bean : list2) {
            newbean = bean;
            Double history_dj_tax = 0.0;
            Double history_dj_no_tax = 0.0;
            if (bean.getSntax() != 0) {
                history_dj_tax = bean.getDqtax() - bean.getSntax();
            } else if (bean.getLstax() != 0) {
                history_dj_tax = bean.getDqtax() - bean.getLstax();
            } else if (bean.getDntax() != 0) {
                history_dj_tax = bean.getDqtax() - bean.getDntax();
            }
            newbean.setCjtax(history_dj_tax);
            if (bean.getSnnotax() != 0) {
                history_dj_no_tax = bean.getDqnotax() - bean.getSnnotax();
            } else if (bean.getLsnotax() != 0) {
                history_dj_no_tax = bean.getDqnotax() - bean.getLsnotax();
            } else if (bean.getDnnotax() != 0) {
                history_dj_no_tax = bean.getDqnotax() - bean.getDnnotax();
            }
            newbean.setCjnotax(history_dj_no_tax);
            result.add(newbean);
        }*/
        if (!isExport) {
            String resultJson1 = mapper.writeValueAsString(list2);
            return resultJson1;
        } else {
            resultDataRowList = export1(list2);
            String secondTitle = "期间：" + startTime.substring(0, 4) + "年"
                    + startTime.substring(4, 6) + "月" + startTime.substring(6, 8) + "日 — "
                    + endTime.substring(0, 4) + "年" + endTime.substring(4, 6)
                    + "月" + endTime.substring(6, 8) + "日";
            ee = new ExcelExportUtil("采购成本变动金额统计表", secondTitle, headerList);
            List<List<Object>> dataList = Lists.newArrayList();
            for (int j = 0; j < resultDataRowList.size(); j++) {
                dataList.add(resultDataRowList.get(j));
            }
            for (int i = 0; i < resultDataRowList.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
                    ee.addCell(row, j, dataList.get(i).get(j));
                }
            }
            return ee;
        }
    }

    private List<List<Object>> export1(List<THM_FS03> list2)throws IOException  {
        // TODO Auto-generated method stub
        //
        int num = 1;
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list_ex = new ArrayList<Object>();
        for (THM_FS03 value : list2) {
            list_ex.add(num);  //序号
            list_ex.add(value.getMatnr()); //物料编码
            list_ex.add(value.getTxtmd());  //物料描述
            list_ex.add(value.getEkgrp()); //采购组
            list_ex.add(value.getSum_menge());  //当期数量
            list_ex.add(value.getMeins()); //计量单位
            list_ex.add(value.getPeinh());  //价格单位
            list_ex.add(value.getLstax()); //含税价_追溯最近年平均价
            list_ex.add(value.getSntax());  //含税价_上年平均价
            list_ex.add(value.getDntax()); //含税价_当年平均价
            list_ex.add(value.getDqtax());  //含税价_当期平均价
            list_ex.add(value.getCjtax()); //含税价_价差
            list_ex.add(null); //含税价_变动金额
            list_ex.add(value.getLsnotax()); //不含税价_追溯最近年平均价
            list_ex.add(value.getSnnotax()); //不含税价_上年平均价
            list_ex.add(value.getDnnotax()); //不含税价_当年平均价
            list_ex.add(value.getDqnotax()); //不含税价_当期平均价
            list_ex.add(value.getCjnotax()); //不含税价_价差
            list_ex.add(null); //不含税价_变动金额
            num++;
            dataRowList.add(list_ex);
            list_ex = new ArrayList<Object>();
        }

        return dataRowList;
    }
}

