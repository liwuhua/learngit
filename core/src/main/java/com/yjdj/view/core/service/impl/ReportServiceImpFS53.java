package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yjdj.view.core.entity.mybeans.MdmGzlx;
import com.yjdj.view.core.entity.mybeans.MdmProductModel;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.entity.mybeans.THM_FS52;
import com.yjdj.view.core.entity.mybeans.THM_FS53;
import com.yjdj.view.core.mapper.IReportMapperFS53;
import com.yjdj.view.core.service.IReportServiceFS53;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * create on 2017/02/14
 * @author yangzhijie
 *
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS53 implements IReportServiceFS53{

	private final Logger log = Logger.getLogger(ReportServiceImpFS53.class);
	
    @Autowired
    private IReportMapperFS53 iReportMapperFS53;
    
	
	@Override
	public Object getReportJson(QueryPojo queryPojo)
			throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		Map<String,String> yearMonthMap = new HashMap<String,String>();
		String yearInput = null;
		
		List<String> gzlxListQ = queryPojo.getGzlx();
		List<String> gzlxList = new ArrayList<String>();
		
		String gzlx = null;
		List<String> gzlxL = new ArrayList<String>();//对应gzlx的list
		List<String> contentList = new ArrayList<String>();//每种故障类型每行的内容
		List<String> xjList = new ArrayList<String>();//小计List
		List<String> totalList = new ArrayList<String>();//总计List
		List<String> cpxhList = new ArrayList<String>();//每种故障类型所包含的产品型号
		
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();//contentMap
		Map<String,Object> map2 = new HashMap<String,Object>();//总的contentMap
		
		yearMonthMap = getYearMonthMap();
		String monthNowStr =  yearMonthMap.get("month");
		String yearNowStr =  yearMonthMap.get("year");
		
		if(queryPojo.getYearInput()!=null){
			yearInput = queryPojo.getYearInput();
			if(!yearInput.equals(yearNowStr)){
				yearNowStr = yearInput;
				monthNowStr = "12";
			}
		}
//		String yearNowStr = "2016";//测试数据，待删
//		String monthNowStr = "12";//测试数据，待删
		
		int monthNowInt = Integer.parseInt(monthNowStr);
		List<String> titleList = getTitleList();
		
		map.put("title", titleList);
		
		List<THM_FS53> result = iReportMapperFS53.selectData(gzlxListQ,yearNowStr);
		
		List<THM_FS53> resultXj = null;//小计result
		
		List<THM_FS53> resultTotal = null;//总计result
		
		int total=0;
		
		for(THM_FS53 value:result){
			if(gzlxList.contains(value.getGzlx())){
				continue;
			}else{
				gzlxList.add(value.getGzlx());
			}
		}
		
		for(int i=0;i<gzlxList.size();i++){
			gzlx = gzlxList.get(i);
			gzlxL.add(gzlx);
			resultXj = iReportMapperFS53.selectDataWithMonth(gzlxL,yearNowStr);
			int z=0;//每组小计的合计
			for(THM_FS53 value:result){
				if(cpxhList.contains(value.getCpxh())){
					continue;
				}else{
					if(value.getGzlx().equals(gzlx)){
						cpxhList.add(value.getCpxh());
					}					
				}
			}
			
			for(int j=0;j<cpxhList.size();j++){
				String cpxh = cpxhList.get(j);
				contentList.add(gzlx);
				contentList.add(cpxh);
				int y=0;//每行最后的合计
				for(int k=1;k<=monthNowInt;k++){
					int x=0;
					for(THM_FS53 value:result){
						if(value.getGzlx().equals(gzlx) && Integer.parseInt(value.getMonth())==k && value.getCpxh().equals(cpxh)){
							contentList.add(value.getNum());
							x=x+1;
							y=y+Integer.parseInt(value.getNum());
						}
					}
					if(x==0){
						contentList.add(" ");
					}
				}
				for(int k=monthNowInt+1;k<=12;k++){
					contentList.add("/");
				}
				contentList.add(y + "");
				z = y+z;
				map1.put(j + "", contentList);
				contentList = new ArrayList<String>();
			}
			
			xjList.add("小计");
			xjList.add(" ");
			
			for(int m=1;m<=monthNowInt;m++){
				int n=0;
				for(THM_FS53 value:resultXj){
					if(Integer.parseInt(value.getMonth())==m){
						xjList.add(value.getNum());
						n=n+1;
					}
				}
				if(n==0){
					xjList.add("0");
				}
			}
			for(int l=monthNowInt+1;l<=12;l++){
				xjList.add("/");
			}
			xjList.add(z + "");
			total = total+z;
			map1.put("xj" + i, xjList);
			xjList = new ArrayList<String>();
			gzlxL = new ArrayList<String>();
			map2.put("content" + i, map1);
			map1 = new HashMap<String,Object>();		
			
			
		}
		totalList.add("总计");
		totalList.add(" ");
		resultTotal = iReportMapperFS53.selectDataTotal(gzlxList,yearNowStr);
		for(int m=1;m<=monthNowInt;m++){
			int n=0;
			for(THM_FS53 value:resultTotal){
				if(Integer.parseInt(value.getMonth())==m){
					totalList.add(value.getNum());
					n=n+1;
				}
			}
			if(n==0){
				totalList.add("0");
			}
		}
		for(int l=monthNowInt+1;l<=12;l++){
			totalList.add("/");
		}
		totalList.add(total + "");
		map.put("total", totalList);
		map.put("content", map2);
		
		json = mapper.writeValueAsString(map);
		return json;	
	}
	
	
	private Map<String,String> getYearMonthMap(){
		Date d =new Date();
		Map<String,String> yearMonthMap = new HashMap<String,String>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
		String monthNowStr =  sdf1.format(d);
		String yearNowStr =  sdf2.format(d);		
		yearMonthMap.put("year", yearNowStr);
		yearMonthMap.put("month", monthNowStr);
		
		return yearMonthMap;
	}
	
	private List<String> getTitleList(){
		List<String> titleList = new ArrayList<String>();
		titleList.add("故障类型");
		titleList.add("产品型号");
		for(int i=1;i<=12;i++){
			titleList.add(i + "月");
		}
		titleList.add("合计");
		return titleList;
	}
	
	@Override
	public List<String> getGzlx(String gzlxLike) throws DataAccessException, JsonProcessingException {
		List<MdmGzlx> list  = iReportMapperFS53.selectGzlx(gzlxLike);
		List<String> list2 = new ArrayList<String>();
		for(MdmGzlx value:list){
			list2.add(value.getGzlx());
		}
		log.info(list2.toString());
		
		return list2;
	}	


}