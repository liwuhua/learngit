package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;

import com.yjdj.view.core.entity.mybeans.MdmArbpl;
import com.yjdj.view.core.entity.mybeans.MdmGroes;
import com.yjdj.view.core.entity.mybeans.MdmProductModel;
import com.yjdj.view.core.entity.mybeans.MdmSernr;
import com.yjdj.view.core.entity.mybeans.MdmZlevel;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS62;
import com.yjdj.view.core.mapper.IReportMapperFS62;
import com.yjdj.view.core.service.IReportServiceFS62;
import com.yjdj.view.core.util.ExcelExportUtil55;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
/**
 * create on 2017/03/08
 * @author yangzhijie
 *
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS62 implements IReportServiceFS62{

 	private final Logger log = Logger.getLogger(ReportServiceImpFS62.class);
	
    @Autowired
    private IReportMapperFS62 iReportMapperFS62;
    private int flag = 0;
    private List<THM_FS62> data = null;
    private List<List<Object>> resultDataRowList = null;
    private List<String> headerList = null;
    private List<String> secondTitleList = null;    
	private List<String> plantValue = null;//工厂多值
    private List<String> arbplValue = null;//检修地点多值
	private List<String> groesValue = null;//电机型号多值
	private List<String> sernrValue = null;//电机编号多值
	private List<String> zlevelValue = null;//维修级别多值
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
		
		mapList = getMapList(plantValue,arbplValue,groesValue,sernrValue,zlevelValue,startTime,endTime,startitem,pageitem,flag,isExport);
				
		return mapList;	
	}
	
	/**
	 * create on 2017.03
	 * 初始化数据
	 * @param queryBean
	 */
	private void init(QueryBean queryBean){		
		plantValue = queryBean.getPlantValue();//工厂多值
		arbplValue = queryBean.getArbplValue();//检修地点多值
		groesValue = queryBean.getGroesValue();//电机型号多值
		sernrValue = queryBean.getSernrValue();//电机编号多值		
		zlevelValue = queryBean.getZlevelValue(); //维修级别多值
		startTime = queryBean.getStartTime();//起始时间
		endTime = queryBean.getEndTime();//结束时间
		startitem = queryBean.getStartitem();//起始页
		pageitem = queryBean.getPageitem();//分页页大小
		isExport = queryBean.isExport();
		headerList = Lists.newArrayList();
		headerList.add("序号");
		headerList.add("工厂");
		headerList.add("检修日期");
		headerList.add("检修地点");
        headerList.add("电机型号");
		headerList.add("电机编号");
		headerList.add("维修级别");
	}
		
	/**
	 * create on 2017.03
	 * @param plantValue
	 * @param arbplValue
	 * @param groesValue
	 * @param sernrValue
	 * @param zlevelValue
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
			List<String> plantValue,List<String> arbplValue,List<String> groesValue,
			List<String> sernrValue,List<String> zlevelValue,String startTime,String endTime,int startitem, int pageitem, 
			int flag, boolean isExport) throws IOException {
		// TODO Auto-generated method stub
		String json = null;
		//如果是导出,则不分页,flag为1,如果不是导出则分页,flag为0
		if(isExport){
			flag = 1;
			data = iReportMapperFS62.selectData(plantValue,arbplValue,groesValue,sernrValue,zlevelValue,startTime,endTime,startitem,pageitem,flag);
		}else{
			data = iReportMapperFS62.selectData(plantValue,arbplValue,groesValue,sernrValue,zlevelValue,startTime,endTime,startitem,pageitem,flag);
		}
        ObjectMapper mapper = new ObjectMapper();

       if(isExport){
		   resultDataRowList = export(data);
		   secondTitleList = getSecondTitleList(startTime,endTime);
	   	   ExcelExportUtil55 ee = new ExcelExportUtil55("检修电机基础数据导出表", secondTitleList, headerList);
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
	 * create on 2017.03
	 * @param data
	 * @throws IOException
	 */
	private List<List<Object>> export(List<THM_FS62> data) throws IOException {
		// TODO Auto-generated method stub
		int num = 0;
		List<List<Object>> dataRowList = Lists.newArrayList();
		List<Object> list = new ArrayList<Object>();
		for(THM_FS62 value:data){
			list.add(num);  //序号
			list.add(value.getIwerk());//工厂
			list.add(value.getZdate()); //检修日期
			list.add(value.getArbpl()); //检修地点
			list.add(value.getGroes());  //电机型号
			list.add(value.getSernr());//电机编号
			list.add(value.getZlevel()); //维修级别
			num ++;
			list = new ArrayList<Object>();
			dataRowList.add(list);
		}
		return dataRowList;
	}
	
	private List<String> getSecondTitleList(String startTime,String endTime){
		List<String> tmpList = new ArrayList<String>();
		tmpList.add("");
		tmpList.add("检修日期：");	
		tmpList.add("");
		tmpList.add(startTime.substring(0, 4)+"年 "+startTime.substring(4,6)+"月"+ " - "+endTime.substring(0,4)+"年 "+endTime.substring(4,6)+"月");
		return tmpList;
	}
	
	
	@Override
	public List<String> getArbpl(String arbplLike) throws DataAccessException, JsonProcessingException {
		List<MdmArbpl> list  = iReportMapperFS62.selectArbpl(arbplLike);
		List<String> list2 = new ArrayList<String>();
		for(MdmArbpl value:list){
			list2.add(value.getArbpl());
		}
		log.info(list2.toString());
		
		return list2;
	}	
	@Override
	public List<String> getGroes(String groesLike) throws DataAccessException, JsonProcessingException {
		List<MdmGroes> list  = iReportMapperFS62.selectGroes(groesLike);
		List<String> list2 = new ArrayList<String>();
		for(MdmGroes value:list){
			list2.add(value.getGroes());
		}
		log.info(list2.toString());
		
		return list2;
	}	
	@Override
	public List<String> getSernr(String sernrLike,int start,int limit) throws DataAccessException, JsonProcessingException {
		List<MdmSernr> list  = iReportMapperFS62.selectSernr(sernrLike,start,limit);
		List<String> list2 = new ArrayList<String>();
		for(MdmSernr value:list){
			list2.add(value.getSernr());
		}
		log.info(list2.toString());
		
		return list2;
	}	
	@Override
	public List<String> getZlevel(String zlevelLike) throws DataAccessException, JsonProcessingException {
		List<MdmZlevel> list  = iReportMapperFS62.selectZlevel(zlevelLike);
		List<String> list2 = new ArrayList<String>();
		for(MdmZlevel value:list){
			list2.add(value.getZlevel());
		}
		log.info(list2.toString());
		
		return list2;
	}	


}