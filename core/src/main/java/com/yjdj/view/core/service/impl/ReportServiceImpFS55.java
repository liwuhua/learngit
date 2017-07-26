package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;

import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS55;
import com.yjdj.view.core.mapper.IReportMapperFS55;
import com.yjdj.view.core.service.IReportServiceFS55;
import com.yjdj.view.core.util.ExcelExportUtil55;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
/**
 * create on 2017/03/02
 * @author yangzhijie
 *
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS55 implements IReportServiceFS55{

 	private final Logger log = Logger.getLogger(ReportServiceImpFS55.class);
	
    @Autowired
    private IReportMapperFS55 iReportMapperFS55;
    private int flag = 0;
    private List<THM_FS55> data = null;
    private List<List<Object>> resultDataRowList = null;
    private List<String> headerList = null;
    private List<String> secondTitleList = null;
    private List<String> vkorgValue = null;//销售组织多值
	private List<String> plantValue = null;//工厂多值
	private List<String> kunnrValue = null;//客户编码多值
	private List<String> matnrValue = null;//物料多值
	private List<Map<String, String>> matnrIntervalMap = null;//物料多区间
	private String startTime = null;//起始时间
	private String endTime = null;//结束时间
	private int startitem = 0;//起始页
	private int pageitem = 0;//分页页大小
 	private boolean isExport = false;
    
	
	@Override
	public Object getReportJson(QueryBean queryBean)
			throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		String json = null;
		Object mapList = null;
		init(queryBean);
		
		mapList = getMapList(vkorgValue,plantValue,kunnrValue,matnrValue,matnrIntervalMap,startTime,endTime,startitem,pageitem,flag,isExport);
		
		
		return mapList;	
	}
	
	/**
	 * create on 2017.03
	 * 初始化数据
	 * @param queryBean
	 */
	private void init(QueryBean queryBean){
		vkorgValue = queryBean.getVkorgValue();//销售组织多值
		plantValue = queryBean.getPlantValue();//工厂多值
		kunnrValue = queryBean.getKunnrValue();//客户编码多值
		matnrValue = queryBean.getMatnrValue();//物料多值
		matnrIntervalMap = queryBean.getMatnrIntervalMap(); //物料多区间
		startTime = queryBean.getStartTime();//起始时间
		endTime = queryBean.getEndTime();//结束时间
		startitem = queryBean.getStartitem();//起始页
		pageitem = queryBean.getPageitem();//分页页大小
		isExport = queryBean.isExport();
		headerList = Lists.newArrayList();
		headerList.add("序号");
		headerList.add("客户编码");
		headerList.add("客户名称");
		headerList.add("物料编码");
		headerList.add("物料描述");
		headerList.add("工厂");
		headerList.add("进厂日期");
		headerList.add("合同号");
        headerList.add("合同行项目");
		headerList.add("采购合同编号");
		headerList.add("合同数量");
		headerList.add("合同剩余数量");
        headerList.add("已发货未开票数量");
		headerList.add("开票数量");
		headerList.add("开票金额");
	}
	
	
	/**
	 * create on 2017.03
	 * @param vkorgValue
	 * @param plantValue
	 * @param kunnrValue
	 * @param matnrValue
	 * @param startTime
	 * @param endTime
	 * @param startitem
	 * @param pageitem
	 * @param flag
	 * @param isExport
	 * @return
	 * @throws IOException  
	 */
	public Object getMapList(
			List<String> vkorgValue,List<String> plantValue,List<String> kunnrValue,
			List<String> matnrValue, List<Map<String, String>> matnrIntervalMap, String startTime, String endTime,int startitem, int pageitem, 
			int flag, boolean isExport) throws IOException {
		// TODO Auto-generated method stub
		String json = null;
		//如果是导出,则不分页,flag为1,如果不是导出则分页,flag为0
		if(isExport){
			flag = 1;
			data = iReportMapperFS55.selectData(vkorgValue, plantValue, kunnrValue, matnrValue, matnrIntervalMap, 
	                startTime, endTime, startitem, pageitem, flag);
		}else{
			data = iReportMapperFS55.selectData(vkorgValue, plantValue, kunnrValue, matnrValue, matnrIntervalMap, 
	                startTime, endTime, startitem, pageitem, flag);
		}
        ObjectMapper mapper = new ObjectMapper();

       if(isExport){
		   resultDataRowList = export(data);
		   secondTitleList = getSecondTitleList(startTime,endTime);
	   	   ExcelExportUtil55 ee = new ExcelExportUtil55("检修合同情况统计表", secondTitleList, headerList);
    	   List<List<Object>> dataList = Lists.newArrayList();
    	   for(int j=0; j<resultDataRowList.size(); j++){
    		   dataList.add(resultDataRowList.get(j));
    	   }
    	   for (int i = 0; i < resultDataRowList.size(); i++) {
    		   Row row = ee.addRow();
    		   for (int j = 0; j < dataList.get(i).size(); j++) {
    			   ee.addCell(row, j, dataList.get(i).get(j));
    		   }
    	   }
    	   return ee;
       }else{
		   Map<String,Object> listMap = new HashMap<String,Object>();
		   headerList.remove(0);
		   listMap.put("headersList", headerList);
		   listMap.put("procurementStorage", data);
		   json = mapper.writeValueAsString(listMap);
		   return json;
       }
		
	}
	
	
	/**
	 * 导出数据
	 * create on 2016.11
	 * @param data
	 * @throws IOException
	 */
	private List<List<Object>> export(List<THM_FS55> data) throws IOException {
		// TODO Auto-generated method stub
		int num = 0;
		List<List<Object>> dataRowList = Lists.newArrayList();
		List<Object> list = new ArrayList<Object>();
		for(THM_FS55 value:data){
			list.add(num);  //序号
			list.add(value.getParnr());  //客户编码
			list.add(value.getTxtmd()); //客户名称
			list.add(value.getMatnr()); //物料编码
			list.add(value.getMaktx());  //物料描述
			list.add(value.getIwerk());//工厂
			list.add(value.getZdate()); //进厂日期
            list.add(value.getContnbr()); //合同号
			list.add(value.getContitm());//合同行项目
			list.add(value.getBstkd());  //采购合同编号
			list.add(value.getConnum()); //合同数量
            list.add(value.getLastconnum()); //剩余合同数量
			list.add(value.getYfhwkpnum());  //已发货未开票数量
			list.add(value.getFkimg()); //开票数量
			list.add(value.getNetwr()); //开票金额		
			num ++;
			list = new ArrayList<Object>();
			dataRowList.add(list);
		}
		return dataRowList;
	}
	
	private List<String> getSecondTitleList(String startTime,String endTime){
		List<String> tmpList = new ArrayList<String>();
		tmpList.add("");
		tmpList.add("");
		tmpList.add("");
		tmpList.add("进厂日期：");		
		tmpList.add("");
		tmpList.add("");
		tmpList.add(startTime.substring(0, 4)+"年 "+startTime.substring(4,6)+"月"+ " - "+endTime.substring(0,4)+"年 "+endTime.substring(4,6)+"月");
		return tmpList;
	}


}