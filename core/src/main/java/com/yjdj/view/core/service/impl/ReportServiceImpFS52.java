package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yjdj.view.core.entity.mybeans.THM_FS52;
import com.yjdj.view.core.mapper.IReportMapperFS52;
import com.yjdj.view.core.service.IReportServiceFS52;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * create on 2017/01/17
 * @author yangzhijie
 *
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS52 implements IReportServiceFS52{

//	private final Logger log = Logger.getLogger(ReportServiceImpFS52.class);
	
    @Autowired
    private IReportMapperFS52 iReportMapperFS52;
//	private boolean isExport = false;
    
	
	@Override
	public Object getReportJson()
			throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		List<String> yearMonthList = new ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		yearMonthList = getYearMonthList();		

		List<THM_FS52> result = iReportMapperFS52.selectData(yearMonthList);	
		
		for(int i=0,j=0;i<yearMonthList.size();i++){
			for(THM_FS52 value:result){
				if(value.getYearMonth().equals(yearMonthList.get(i))){
					map.put(yearMonthList.get(i), value.getNum());
					j = j+1;
				}
			}
			if(j==0){
				map.put(yearMonthList.get(i), null);
			}else{
				j=0;
			}
			resultList.add(map);
			map = new HashMap<String,String>();
		}
		
		json = mapper.writeValueAsString(resultList);
		return json;	
	}
	
	
	private List<String> getYearMonthList(){
		Date d =new Date();
		List<String> yearMonthList = new ArrayList<String>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
		String monthNowStr =  sdf1.format(d);
		int monthNowInt = Integer.parseInt(monthNowStr);
		String yearNowStr =  sdf2.format(d) + "0";
		String lastYearStr = (Integer.parseInt(yearNowStr.substring(0,4))-1) + "0";
		
		for(int i=0;i<10;i++){
			if(i<9){
				yearMonthList.add(lastYearStr + (i+1));
			}else{
				yearMonthList.add(lastYearStr.substring(0,4)+"10");
				yearMonthList.add(lastYearStr.substring(0,4)+"11");
				yearMonthList.add(lastYearStr.substring(0,4)+"12");
			}
		}
		
		if(monthNowInt<10){
			for(int i=0;i<monthNowInt;i++){
				yearMonthList.add(yearNowStr + (i+1));
			}
		}else{
			for(int i=0;i<9;i++){
				yearMonthList.add(yearNowStr + (i+1));
			}
			for(int i=9;i<monthNowInt;i++){
				yearMonthList.add(yearNowStr.substring(0,4) + (i+1));
			}
		}
		return yearMonthList;
	}


}