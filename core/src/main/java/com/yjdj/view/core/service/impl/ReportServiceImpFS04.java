package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.THM_MSEG_04;
import com.yjdj.view.core.mapper.IReportMapperFS04;
import com.yjdj.view.core.service.IReportServiceFS04;
import com.yjdj.view.core.util.ExcelExportUtil;
/**
 * create on 2016/11/01
 * @author yanngzhijie
 *
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS04 implements IReportServiceFS04{

	private final Logger log = Logger.getLogger(ReportServiceImpFS04.class);
	
    @Autowired
    private IReportMapperFS04 iReportMapperFS04;
    private int flag = 0;
    private List<THM_MSEG_04> data = null;
    private List<List<Object>> resultDataRowList = null;
    private List<String> headerList = null;
    private List<String> secondTitleList = null;
	private List<String> plantValue = null;//工厂多值
	private List<String> ebelnValue = null;//采购订单多值
	private List<String> matnrValue = null;//物料多值
	private List<String> vendorValue = null;//供应商多值
	private List<String> purGroupValue = null;//采购组多值
	private String startTime = null;//起始时间
	private String endTime = null;//结束时间
	private List<String> timediffValue = null;//时间差异多值
	private int startitem = 0;//其实页
	private int pageitem = 0;//分页页大小
	private List<Map<String,String>> matnrInterval = null;
	private boolean isExport = false;
    
	@Override
	public Object getReportJson(QueryBean queryBean) throws IOException  {
        Object mapList = null;
        init(queryBean);
        log.debug("+++++++++++++++++++ FS04 getReportJson");
        //第一步
        mapList = getMapListByThmMseg04_05(plantValue,ebelnValue,matnrValue,vendorValue,purGroupValue,
        		matnrInterval,startTime,endTime,timediffValue,startitem,pageitem,flag,isExport);
       
		return mapList;
	}
	/**
	 * create on 2016.11
	 * 初始化数据
	 * @param queryBean
	 */
	private void init(QueryBean queryBean){
		plantValue = queryBean.getPlantValue();//工厂多值
		ebelnValue = queryBean.getEbelnValue();//采购订单多值
		matnrValue = queryBean.getMatnrValue();//物料多值
		vendorValue = queryBean.getVendorValue();//供应商多值
		purGroupValue = queryBean.getPurGroupValue();//采购组多值
		startTime = queryBean.getStartTime();//起始时间
		endTime = queryBean.getEndTime();//结束时间
		timediffValue = queryBean.getTimediffValue();//时间差异多值
		startitem = queryBean.getStartitem();//其实页
		pageitem = queryBean.getPageitem();//分页页大小
		matnrInterval = queryBean.getMatnrIntervalMap();
		isExport = queryBean.isExport();
		headerList = Lists.newArrayList();
		headerList.add("序号");
		headerList.add("物料编码");
		headerList.add("物料描述");
		headerList.add("采购订单");
		headerList.add("采购订单行号");
		headerList.add("采购申请");
		headerList.add("采购申请交货日期");
                headerList.add("采购订单交货日期");
		headerList.add("实际收货数量");
		headerList.add("实际收货日期");
		headerList.add("申请交货时间差异");
                headerList.add("订单交货时间差异");
		headerList.add("采购组");
		headerList.add("工厂");
		headerList.add("供应商编号");
		headerList.add("供应商描述");
	}
	/**
	 * create on 2016.11
	 * @param plantValue
	 * @param ebelnValue
	 * @param matnrValue
	 * @param vendorValue
	 * @param purGroupValue
	 * @param matnrInterval
	 * @param startTime
	 * @param endTime
	 * @param timediffValue
	 * @param startitem
	 * @param pageitem
	 * @param flag
	 * @param isExport
	 * @return
	 * @throws IOException  
	 */
	public Object getMapListByThmMseg04_05(
			List<String> plantValue,List<String> ebelnValue,List<String> matnrValue,
			List<String> vendorValue, List<String> purGroupValue,List<Map<String,String>> matnrInterval,
            String startTime, String endTime,List<String> timediffValue,
            int startitem, int pageitem, int flag, boolean isExport) throws IOException {
		// TODO Auto-generated method stub
		String json = null;
		//如果是导出,则不分页,flag为1,如果不是导出则分页,flag为0
		if(isExport){
			flag = 1;
			data = iReportMapperFS04.selectData(plantValue, ebelnValue, matnrValue, vendorValue,
	                purGroupValue, timediffValue, matnrInterval, startTime, endTime, startitem, pageitem, flag);
		}else{
			data = iReportMapperFS04.selectData(plantValue, ebelnValue, matnrValue, vendorValue,
	                purGroupValue, timediffValue, matnrInterval, startTime, endTime, startitem, pageitem, flag);
		}
        ObjectMapper mapper = new ObjectMapper();
        
        List<THM_MSEG_04> thmList = new ArrayList<THM_MSEG_04>();
        if(CollectionUtils.isNotEmpty(data)){
        		thmList = data;
        }
       if(isExport){
		   resultDataRowList = export(data);
	   	   ExcelExportUtil ee = new ExcelExportUtil("物料分批次到货统计表", secondTitleList, headerList);
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
		   listMap.put("procurementStorage", thmList);
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
	private List<List<Object>> export(List<THM_MSEG_04> data) throws IOException {
		// TODO Auto-generated method stub
		int num = 0;
		List<List<Object>> dataRowList = Lists.newArrayList();
		List<Object> list = new ArrayList<Object>();
		for(THM_MSEG_04 value:data){
			list.add(num);  //序号
			list.add(value.getMatnr());  //物料编码
			list.add(value.getMatnrtxtmd()); //物料描述
			list.add(value.getEbeln()); //采购订单
			list.add(value.getEbelp());  //行项目
			list.add(value.getBanfn());//采购申请
			list.add(value.getLfdat()); //采购申请交货日期
                        list.add(value.getEindt()); //采购订单交货日期
			list.add(value.getMenge());//实际收货数量
			list.add(value.getBudat());  //实际收货日期
			list.add(value.getTimediff()); //申请交货时间差异
                        list.add(value.getTimediffeindt()); //订单交货时间差异
			list.add(value.getEkgrp());  //采购组
			list.add(value.getWerks()); //工厂
			list.add(value.getLifnr()); //供应商编码
			list.add(value.getLifnrtxtmd()); //供应商描述			
			num ++;
			list = new ArrayList<Object>();
			dataRowList.add(list);
		}
		return dataRowList;
	}

}
