package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.util.ExcelExportUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**销售订单完成统计表及需求统计表
 * Created by liwuhua on 16/11/5.
 */

public interface IReportServiceFS13 {

     public Object getLgpbe(String lgpbe) throws JsonProcessingException,DataAccessException;
     
     public Object getData(QueryBean queryBean) throws JsonProcessingException,DataAccessException;

}
