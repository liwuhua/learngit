package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS58;
import com.yjdj.view.core.mapper.IReportMapperFS58;
import com.yjdj.view.core.service.IReportServiceFS58;
import com.yjdj.view.core.util.ExcelExportUtil;

/**
 * Created by sunwan on 2017/4/24.
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS58 implements IReportServiceFS58 {

    @Autowired
    private IReportMapperFS58 iReportMapperFS58;
    private boolean isExport = false; //如果是true则为导数据，false则为查数据
    private List<List<Object>> resultDataRowList = null;
    private List<String> headerList = null;
    private ExcelExportUtil ee = null;
    
    @Override
    public Object selectData(QueryBean queryBean) throws JsonProcessingException,IOException,DataAccessException {
    	List<String> werksValue = queryBean.getWerksValue();
    	List<String> groesValue = queryBean.getGroesValue();
    	List<String> zlevelValue = queryBean.getZlevelValue();
    	String startTime = queryBean.getStartTime();
        String endTime = queryBean.getEndTime();
        List<String> matnrValue = queryBean.getMatnrValue();
        int startItem = queryBean.getStartitem();
        int pageItem = queryBean.getPageitem();
        StringBuffer werksStr = new StringBuffer();
        StringBuffer groesStr = new StringBuffer();
        StringBuffer zlevelStr = new StringBuffer();
        StringBuffer matnrStr = new StringBuffer();
        
        String werks = null;
        String groes = null;
        String zlevel = null;
        String matnr = null;
        if(werksValue != null){
        	for(int i=0; i<werksValue.size(); i++){
        		werksStr.append(werksValue.get(i)).append(",");
        	}
        	if(werksStr.length() != 0){
        		werks = werksStr.substring(0, werksStr.lastIndexOf(","));
        	}else{
        		werks = "";
        	}
        }else{
        	werks = "";
        }
    	if(groesValue != null){
        	for(int i=0; i<groesValue.size(); i++){
        		groesStr.append(groesValue.get(i)).append(",");
        	}
        	if(groesStr.length() !=0 ){
        		groes = groesStr.substring(0, groesStr.lastIndexOf(","));
        	}else{
        		groes = "";
        	}
    	}else{
    		groes = "";
    	}
    	if(zlevelValue != null){
        	for(int i=0; i<zlevelValue.size(); i++){
        		zlevelStr.append(zlevelValue.get(i)).append(",");
        	}
        	if(zlevelStr.length() != 0){
        		zlevel = zlevelStr.substring(0, zlevelStr.lastIndexOf(","));
        	}else{
        		zlevel = "";
        	}
    	}else{
    		zlevel = "";
    	}
    	if(matnrValue != null){
        	for(int i=0; i<matnrValue.size(); i++){
        		matnrStr.append(matnrValue.get(i)).append(",");
        	}
        	if(matnrStr.length() != 0){
        		matnr = matnrStr.substring(0, matnrStr.lastIndexOf(","));
        	}else{
        		matnr = "";
        	}
    	}else{
    		matnr = "";
    	}

    	
        headerList = Lists.newArrayList();
        headerList.add("序号");
        headerList.add("工厂");
        headerList.add("产品编码");
        headerList.add("电机型号");
        headerList.add("维修级别");
        headerList.add("配件物料编码");
        headerList.add("配件物料描述");
        headerList.add("偶换件数");
        headerList.add("检修台数");
        headerList.add("偶换台数");
        headerList.add("偶换台率");
        headerList.add("台用量");
        headerList.add("偶换率");

        int flag = 0;//查询flag为0，导出flag为1
        isExport = queryBean.isExport();
        if (isExport) {
            flag = 1;
        }
        
        //首次请求时调用存储过程
        if(startItem == 0){
        	iReportMapperFS58.getData(werks,groes,zlevel,matnr,startTime,endTime);
        }
        //查数
        List<THM_FS58> list = iReportMapperFS58.selectData(startItem,pageItem,flag);
        
        if (!isExport) {
        	ObjectMapper mapper = new ObjectMapper();
            String resultJson = mapper.writeValueAsString(list);
            return resultJson;
        } else {
            resultDataRowList = export(list);
            String secondTitle = "出厂时间：" + startTime.substring(0,4) + "年" + startTime.substring(4,6) + "月"
                     + startTime.substring(6,8) + "日 ----" + endTime.substring(0,4) + "年" + endTime.substring(4,6) + "月"
                    + endTime.substring(6,8) + "日";
            ee = new ExcelExportUtil("检修电机（产品）偶换件偶换率统计表", secondTitle, headerList);
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

    private List<List<Object>> export(List<THM_FS58> list) throws IOException {
        // TODO Auto-generated method stub
        int num = 1;
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list_ex = new ArrayList<Object>();
        for (THM_FS58 value : list) {
            list_ex.add(num);  //序号
            list_ex.add(value.getIwerk());
            list_ex.add(value.getMatnr());
            list_ex.add(value.getGroes());
            list_ex.add(value.getZlevel());
            list_ex.add(value.getPjwlbm());
            list_ex.add(value.getPjwlms());
            list_ex.add(value.getOhjs());
            list_ex.add(value.getJxts());
            list_ex.add(value.getOhts());
            list_ex.add(value.getOhtl());
            list_ex.add(value.getTyl());
            list_ex.add(value.getOhl());
            num++;
            dataRowList.add(list_ex);
            list_ex = new ArrayList<Object>();
        }

        return dataRowList;
    }
    //查询电机型号
	@Override
	public String getGroesJson(String id) throws JsonProcessingException,
			DataAccessException {
		// TODO Auto-generated method stub
		List<String> jsonMap = iReportMapperFS58.selectGroesJson(id);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonMap);
		return json;
	}
    //查询维修级别 
	@Override
	public String getZlevelJson(String id) throws JsonProcessingException,
			DataAccessException {
		// TODO Auto-generated method stub
		List<String> jsonMap = iReportMapperFS58.selectZlevelJson(id);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonMap);
		return json;
	}
    //查询配件物料编码
	@Override
	public String getPjwlbmJson(String id, String name, int start, int limit) throws JsonProcessingException,
			DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String,String>> jsonMap = iReportMapperFS58.selectPjwlbmJson(id, name, start, limit);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonMap);
		return json;
	}

}
