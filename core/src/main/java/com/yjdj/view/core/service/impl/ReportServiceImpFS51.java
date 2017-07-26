package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yjdj.view.core.entity.mybeans.MdmProductModel;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.entity.mybeans.THM_FS51;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.mapper.IReportMapperFS51;
import com.yjdj.view.core.service.IReportServiceFS51;
import com.yjdj.view.core.util.ExcelExportUtil51;
/**
 * create on 2017/1/3
 * @author yangzhijie
 *
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS51 implements IReportServiceFS51{

	private final Logger log = Logger.getLogger(ReportServiceImpFS51.class);
	
    @Autowired
    private IReportMapperFS51 iReportMapperFS51;
	private boolean isExport = false;
    
	
	@Override
	public Object getReportJson(QueryPojo queryPojo)
			throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		List<String> productModel = queryPojo.getProductModel();
		isExport = queryPojo.isExport();
		
		
		List<THM_FS51> result = iReportMapperFS51.selectData(productModel);
		Iterator<THM_FS51> it1 = result.iterator();
		Iterator<THM_FS51> it2 = result.iterator();
		List<String> list1 = new ArrayList<String>();//产品型号（有重复数据）
		List<String> list2 = new ArrayList<String>();//生命阶段（有重复数据）
		List<String> list3 = new ArrayList<String>();//每种产品型号的故障部位(有重复)
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		Map<String,Object> map3 = new HashMap<String,Object>();
		Map<String,Object> map4 = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();

		while(it1.hasNext()){
			list1.add(it1.next().getCpxh());
		}
		while(it2.hasNext()){
			list2.add(it2.next().getCpsmjd());
		}
		List<String> list1WithoutDup = new ArrayList<String>(new HashSet<String>(list1));//产品型号（无重复数据）
		List<String> list2WithoutDup = new ArrayList<String>(new HashSet<String>(list2));//生命阶段（无重复数据）
		
		if(isExport){
			String title = "电机型号检索故障信息表";
			int column = list2WithoutDup.size()+1;
			List<List<String>> titleList = Lists.newArrayList();//每种产品的title
			List<String> titleList1 = new ArrayList<String>();
			
			
			for(int j=0;j<list1WithoutDup.size();j++){
				titleList1.add(list1WithoutDup.get(j));
				titleList1.addAll(list2WithoutDup);
				titleList.add(titleList1);
				titleList1 = new ArrayList<String>();
			}
			List<String> dataRowList = new ArrayList<String>();//每行数据
			List<List<String>> dataList = Lists.newArrayList();//每种产品型号的每行数据
			List<List<List<String>>> dataListAll = Lists.newArrayList();//所有产品型号的每行数据
			
			for(int i=0; i<titleList.size(); i++){
				for(THM_FS51 value:result){
					if(value.getCpxh().equals(titleList.get(i).get(0))){
						list3.add(value.getGzbw());//每种产品型号的故障部位
					}				
				}
				List<String> list3WithoutDup = new ArrayList<String>(new HashSet<String>(list3));//每种产品型号的故障部位(无重复)
				for(String gzbw:list3WithoutDup){
					dataRowList.add(gzbw);
					for(String smzqAll:list2WithoutDup){
						int x=0;
						for(THM_FS51 value:result){
							if(value.getCpsmjd().equals(smzqAll) && value.getGzbw().equals(gzbw) && value.getCpxh().equals(titleList.get(i).get(0))){
								dataRowList.add(value.getNum());
								x=x+1;
							}
						}
						if(x==0){
							dataRowList.add(" ");
						}
					}
					dataList.add(dataRowList);
					dataRowList = new ArrayList<String>();
				}
				dataListAll.add(dataList);
				dataList = Lists.newArrayList();
				list3 = new ArrayList<String>();
			}
			
			ExcelExportUtil51 ee = new ExcelExportUtil51(title,column);
			
			for(int i=0;i<titleList.size();i++){
				Row row1 = ee.addRow(); //增加每种产品的title
				for(int j=0;j<titleList.get(i).size();j++){
					ee.addCell(row1, j, titleList.get(i).get(j));
				}
				for(int k=0;k<dataListAll.get(i).size();k++){
					Row row2 = ee.addRow();
					for(int y=0;y<dataListAll.get(i).get(k).size();y++){
						ee.addCell(row2, y, dataListAll.get(i).get(k).get(y));
					}
				}
			}
			return ee;		
		}else{
			List<List<String>> titleList = Lists.newArrayList();//每种产品的title
			List<String> titleList1 = new ArrayList<String>();			
			
			for(int j=0;j<list1WithoutDup.size();j++){
				titleList1.add(list1WithoutDup.get(j));
				titleList1.addAll(list2WithoutDup);
				titleList.add(titleList1);
				map1.put(list1WithoutDup.get(j), titleList1);//每种产品的标题
				titleList1 = new ArrayList<String>();
			}
			
			
			List<String> dataRowList = new ArrayList<String>();
			
			for(int i=0; i<titleList.size(); i++){
				for(THM_FS51 value:result){
					if(value.getCpxh().equals(titleList.get(i).get(0))){
						list3.add(value.getGzbw());//每种产品型号的故障部位
					}				
				}
				List<String> list3WithoutDup = new ArrayList<String>(new HashSet<String>(list3));//每种产品型号的故障部位(无重复)
				int y=1;
				for(String gzbw:list3WithoutDup){
					dataRowList.add(gzbw);					
					for(String smzqAll:list2WithoutDup){
						int x=0;
						for(THM_FS51 value:result){
							if(value.getCpsmjd().equals(smzqAll) && value.getGzbw().equals(gzbw) && value.getCpxh().equals(titleList.get(i).get(0))){
								dataRowList.add(value.getNum());
								x=x+1;
							}
						}
						if(x==0){
							dataRowList.add(" ");
						}
					}
					map2.put(String.valueOf(y), dataRowList);
					y=y+1;
					dataRowList = new ArrayList<String>();
				}
				map3.put(titleList.get(i).get(0), map2);
				map2 = new HashMap<String,Object>();
				list3 = new ArrayList<String>();
			}
			
			for(int i=0; i<titleList.size(); i++){
				for(int j=0;j<map1.size();j++){
					map4.put("title", map1.get(titleList.get(i).get(0)));
					map4.put("content", map3.get(titleList.get(i).get(0)));
				}
				map.put(titleList.get(i).get(0), map4);
				map4 = new HashMap<String,Object>();
			}

			json = mapper.writeValueAsString(map);
			return json;	
		}		
	}


	
	@Override
	public List<String> getCpxh(String cpxhLike) throws DataAccessException, JsonProcessingException {
		List<MdmProductModel> list  = iReportMapperFS51.selectCpxh(cpxhLike);
		List<String> list2 = new ArrayList<String>();
		for(MdmProductModel value:list){
			list2.add(value.getProductModel());
		}
		log.info(list2.toString());
		
		return list2;
	}	

}
