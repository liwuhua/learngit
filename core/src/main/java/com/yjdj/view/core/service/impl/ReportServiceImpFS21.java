package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.IReportMapperFS21;
import com.yjdj.view.core.service.IReportServiceFS21;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.StringUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chengxuan on 2016/11/8.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS21 implements IReportServiceFS21 {

    @Autowired
    private IReportMapperFS21 iReportMapperFS21;

    private boolean isExport = false; //如果是true则为导数据，false则为查数据

    @Override
    public Object getDataFs21(QueryBean queryBean) throws IOException, DataAccessException {
        ObjectMapper mapper = new ObjectMapper();

        List<Map<String,Object>> resultMap = iReportMapperFS21.getDataFs21(queryBean.getMatnrValue(),
                                                                    queryBean.getMatnrIntervalMap(),
                                                                    queryBean.getPlantValue(),
                                                                    queryBean.getPurGroupValue(),
                                                                    queryBean.getMrpctlValue(),
//                                                                    queryBean.getOneValue(),
//                                                                    queryBean.getTwoValue(),
//                                                                    queryBean.getStartTime(),
//                                                                    queryBean.getEndTime(),
//                                                                    queryBean.getStartTimeTwo(),
//                                                                    queryBean.getEndTimeTwo(),
                                                                    queryBean.getStartitem(),
                                                                    queryBean.getPageitem());
        isExport = queryBean.isExport();
        if(!isExport){
            return mapper.writeValueAsString(resultMap);
        }else{
            ExcelExportUtil ee = new ExcelExportUtil("排产异常物料联查统计分析表", "", getHeaderList());
            List<List<Object>> resultDataRowList = export1(resultMap);
            for (int i = 0; i < resultDataRowList.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < resultDataRowList.get(i).size(); j++) {
                    String val = StringUtil.nullToStr(resultDataRowList.get(i).get(j));

                    ee.addCell(row, row.getRowNum(), j, val, 2, true, Double.class);

                }
            }
            return ee;
        }
    }

    private List<List<Object>> export1(List<Map<String,Object>> resultListMap) throws IOException{
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list_ex = new ArrayList<Object>();
        for(Map<String,Object> map : resultListMap){
            list_ex.add(map.get("PLWRK"));
            list_ex.add(map.get("MATNR"));
            list_ex.add(map.get("MAKTX"));
            list_ex.add(map.get("EKGRP"));
            list_ex.add(map.get("DELNR"));
            list_ex.add(map.get("DELPS"));
            list_ex.add(map.get("ZXGYL"));
            list_ex.add(map.get("ZCJRY"));
            list_ex.add(map.get("ZCJDH"));
            list_ex.add(map.get("ZCJBM"));
            list_ex.add(map.get("ZGGNR"));
            list_ex.add(map.get("ZGGDH"));
            list_ex.add(map.get("ZGGBM"));
            list_ex.add(map.get("ZCJRQ"));
            list_ex.add(map.get("ZGGRQ"));
            list_ex.add(map.get("DAT00"));
            list_ex.add(map.get("BERW1"));
            list_ex.add(map.get("ZYCXX"));
            list_ex.add(map.get("ZBZ"));

            dataRowList.add(list_ex);
            list_ex = new ArrayList<Object>();
        }

        return  dataRowList;
    }

    /**
     * 返回导出表的headerList
     *
     * @return
     */
    private List<String> getHeaderList() {
        List<String> headerList = Lists.newArrayList();
        headerList.add("工厂");
        headerList.add("物料编码");
        headerList.add("物料描述");
        headerList.add("采购组");
        headerList.add("预留号");
        headerList.add("预留行号");
        headerList.add("相关订单号");
        headerList.add("需求创建者");
        headerList.add("创建者联系电话");
        headerList.add("创建者部门");
        headerList.add("更改者");
        headerList.add("更改这联系电话");
        headerList.add("更改部门");
        headerList.add("创建日期");
        headerList.add("更改日期");
        headerList.add("需求日期");
        headerList.add("异常天数");
        headerList.add("异常消息");
        headerList.add("备注");
        return headerList;
    }
}
