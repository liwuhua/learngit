package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.util.ExcelExportUtil;

import java.util.List;

import org.springframework.dao.DataAccessException;

/**销售订单完成统计表及需求统计表
 * Created by liwuhua on 16/10/21.
 */

public interface IReportServiceFS02 {
     
     public Object getListFs02Tb1(QueryBean queryBean,List<String> salesorgList) throws JsonProcessingException,DataAccessException;
     
     public Object getListFs02Tb2(QueryBean queryBean,List<String> salesorgList) throws JsonProcessingException,DataAccessException;
     
     public ExcelExportUtil getexportExcel1(QueryBean queryBean,List<String> salesorgList) throws JsonProcessingException,DataAccessException;
     
     public ExcelExportUtil getexportExcel2(QueryBean queryBean,List<String> salesorgList) throws JsonProcessingException,DataAccessException;
     
	
}
