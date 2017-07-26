package com.yjdj.view.core.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.CarSalesCount;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_MSEG_06;
import com.yjdj.view.core.entity.mybeans.THM_VBAP_01;

/**销售订单完成统计表及需求统计表
 * Created by liwuhua on 16/11/5.
 */

public interface IReportServiceFS18 {
     
     public String getListFs18(QueryBean queryBean,List<String> plantList) throws JsonProcessingException,DataAccessException;
	
	
}
