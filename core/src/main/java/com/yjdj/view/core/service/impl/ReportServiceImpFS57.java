package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.MdmTcustomer;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS57;
import com.yjdj.view.core.mapper.IReportMapperFS57;
import com.yjdj.view.core.service.IReportServiceFS57;
import com.yjdj.view.core.util.ExcelExportUtil;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunwan on 2017/3/3.
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS57 implements IReportServiceFS57{

    @Autowired
    private IReportMapperFS57 iReportMapperFS57;
    private boolean isExport = false; //如果是true则为导数据，false则为查数据
    private List<List<Object>> resultDataRowList = null;
    private List<String> headerList = null;
    private ExcelExportUtil ee = null;

    @Override
    public Object selectData(QueryBean queryBean) throws JsonProcessingException,IOException,DataAccessException {

        headerList = Lists.newArrayList();
        headerList.add("序号");
        headerList.add("销售组织");
        headerList.add("工厂");
        headerList.add("客户");
        headerList.add("客户名称");
        headerList.add("级别描述");
        headerList.add("物料编码");
        headerList.add("物料描述");
        headerList.add("期初在产台数");
        headerList.add("期初在产成本");
        headerList.add("期间进厂台数");
        headerList.add("期间出厂台数");
        headerList.add("期间出厂成本");
        headerList.add("期间在产台数");
        headerList.add("期间在产成本");
        headerList.add("期间销售台数");
        headerList.add("期间销售成本");
        headerList.add("期间销售金额");
        headerList.add("已发货未开票台数");
        headerList.add("已发货未开票成本");

        int flag = 0;//查询flag为0，导出flag为1
        isExport = queryBean.isExport();
        if (isExport) {
            flag = 1;
        }
        ObjectMapper mapper = new ObjectMapper();
        //查数
        List<THM_FS57> list = iReportMapperFS57.selectData(queryBean.getVkorgValue(),queryBean.getWerksValue(),queryBean.getKunnrValue(),
                                                                queryBean.getDateYearMonth(),queryBean.getStartitem(),queryBean.getPageitem(),flag);

        if (!isExport) {
            String resultJson = mapper.writeValueAsString(list);
            return resultJson;
        } else {
            resultDataRowList = export(list);
            String secondTitle = "统计期间：" + queryBean.getDateYearMonth().substring(0,4) + "年" + queryBean.getDateYearMonth().substring(4,6) + "月";
            ee = new ExcelExportUtil("检修统计表", secondTitle, headerList);
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

    private List<List<Object>> export(List<THM_FS57> list) throws IOException {
        // TODO Auto-generated method stub
        int num = 1;
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list_ex = new ArrayList<Object>();
        for (THM_FS57 value : list) {
            list_ex.add(num);  //序号
            list_ex.add(value.getVkorg());
            list_ex.add(value.getIwerk());
            list_ex.add(value.getParnr());
            list_ex.add(value.getTxtmd());
            list_ex.add(value.getDdtext());
            list_ex.add(value.getMatnr());
            list_ex.add(value.getMaktx());
            list_ex.add(value.getQczcts());
            list_ex.add(value.getQczccb());
            list_ex.add(value.getQjjcts());
            list_ex.add(value.getQjccts());
            list_ex.add(value.getQjcccb());
            list_ex.add(value.getQjzcts());
            list_ex.add(value.getQjzccb());
            list_ex.add(value.getQjxsts());
            list_ex.add(value.getQjxscb());
            list_ex.add(value.getQjxsje());
            list_ex.add(value.getYfhwkpts());
            list_ex.add(value.getYfhwkpcb());
            num++;
            dataRowList.add(list_ex);
            list_ex = new ArrayList<Object>();
        }

        return dataRowList;
    }

    @Override
    public String getAllTcustomer(String tableName, String customer, String txtmd) throws JsonProcessingException,DataAccessException {
        List<MdmTcustomer> ccList = iReportMapperFS57.getAllTcustomer(tableName,customer,txtmd);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }

}
