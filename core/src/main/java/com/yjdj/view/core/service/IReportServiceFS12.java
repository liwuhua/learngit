package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelExportUtil12;

/**销售订单完成统计表及需求统计表
 * Created by liwuhua on 16/11/5.
 */

public interface IReportServiceFS12 {
     
     public Object getListFs12(QueryBean queryBean,List<String> comcodeLists) throws JsonProcessingException,DataAccessException;
    
     public ExcelExportUtil12 getexportExcel(QueryBean queryBean,List<String> compCodeList) throws JsonProcessingException,DataAccessException;

     public Object importData(String filePath,List<String> compCodeList) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
}
