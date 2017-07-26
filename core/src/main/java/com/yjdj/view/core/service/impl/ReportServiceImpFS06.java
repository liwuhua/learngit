package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_LIPS_02;
import com.yjdj.view.core.entity.mybeans.THM_VBAP_02;
import com.yjdj.view.core.mapper.IReportMapperFS06;
import com.yjdj.view.core.service.IReportServiceFS06;
import com.yjdj.view.core.util.ExcelExportUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunwan on 2016/11/7.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS06 implements IReportServiceFS06 {

    @Autowired
    private IReportMapperFS06 iReportMapperFS06;
    private boolean isExport = false; //如果是true则为导数据，false则为查数据
//    private List<THM_VBAP_02> data = null;
    private List<List<Object>> resultDataRowList = null;
    private List<String> headerList = null;
    private ExcelExportUtil ee = null;
    //合同执行情况
    @Override
    public Object selectData_01(QueryBean queryBean) throws JsonProcessingException,IOException,DataAccessException{
        headerList = Lists.newArrayList();
        headerList.add("序号");
        headerList.add("业务部门");
        headerList.add("客户编码");
        headerList.add("客户描述");
        headerList.add("物料编码");
        headerList.add("物料描述");
        headerList.add("销售合同");
        headerList.add("合同行项目");
        headerList.add("合同创建日期");
        headerList.add("合同数量");
        headerList.add("合同单价");
        headerList.add("合同金额");
        headerList.add("订单数量（合计）");
        headerList.add("发货数量");
        headerList.add("开票数量");
        headerList.add("订单库存");
        headerList.add("非订单库存");
        int flag = 0;//查询flag为0，导出flag为1
        isExport = queryBean.isExport();
        if (isExport) {
            flag = 1;
        }
        ObjectMapper mapper = new ObjectMapper();
        List<THM_VBAP_02> list=iReportMapperFS06.selectData_01(queryBean.getVbelnValue(),
                                                                    queryBean.getVkorgValue(),
                                                                    queryBean.getVkburValue(),
                                                                    queryBean.getMatnrValue(),
                                                                    queryBean.getMatnrIntervalMap(),
                                                                    queryBean.getStartTime(),
                                                                    queryBean.getEndTime(),
                                                                    queryBean.getKunnrValue(),
                                                                    queryBean.getAuartValue(),
                                                                    queryBean.getStartitem(),
                                                                    queryBean.getPageitem(),
                                                                    flag);

        //处理非订单库存
        String lsmatnr = "";
        String lswerks = "";
        List<THM_VBAP_02> list01 = new ArrayList<>();
        THM_VBAP_02 newbean = new THM_VBAP_02();
        for (THM_VBAP_02 bean : list){
            newbean = bean;
            double uddstock = 0.0;
            if (bean.getMatnr().equals(lsmatnr) && bean.getWerks().equals(lswerks)){
                newbean.setUddstock(uddstock);
            }
            lsmatnr = bean.getMatnr();
            lswerks = bean.getWerks();
            list01.add(newbean);
        }

        if (!isExport) {
            String resultJson1 = mapper.writeValueAsString(list01);
            return resultJson1;
        } else {
            resultDataRowList = export(list01);
            String secondTitle = null;
            ee = new ExcelExportUtil("合同执行情况统计表", secondTitle, headerList);
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

    //弹窗
    @Override
    public String selectData_02(String vbeln,String posnr,int start,int limit) throws JsonProcessingException,IOException,DataAccessException{
        ObjectMapper mapper = new ObjectMapper();
        List<THM_LIPS_02> list02=iReportMapperFS06.selectData_02(vbeln,posnr,start,limit);
        String resultJson2 = mapper.writeValueAsString(list02);
        return resultJson2;
    }


    //导出数据
    private List<List<Object>> export(List<THM_VBAP_02> list01) throws IOException {
        // TODO Auto-generated method stub
        //
        int num = 1;
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list_ex = new ArrayList<Object>();
        for (THM_VBAP_02 value : list01) {
            list_ex.add(num);  //序号
            list_ex.add(value.getVkbur()); //业务部门
            list_ex.add(value.getKunnr());  //客户编码
            list_ex.add(value.getTxtmd()); //客户描述
            list_ex.add(value.getMatnr());  //物料编码
            list_ex.add(value.getArktx()); //物料描述
            list_ex.add(value.getVbeln());  //销售合同
            list_ex.add(value.getPosnr()); //合同行项目
            list_ex.add(value.getErdat());  //合同创建日期
            list_ex.add(value.getZmeng()); //合同数量
            list_ex.add(value.getUnit());  //合同单价
            list_ex.add(value.getSum_unit()); //合同金额
            list_ex.add(value.getDdcount()); //订单数量（合计）
            list_ex.add(value.getFhcount()); //发货数量
            list_ex.add(value.getKpcount()); //开票数量
            list_ex.add(value.getDdstock()); //订单库存
            list_ex.add(value.getUddstock()); //非订单库存
            num++;
            dataRowList.add(list_ex);
            list_ex = new ArrayList<Object>();
        }

        return dataRowList;
    }

}
