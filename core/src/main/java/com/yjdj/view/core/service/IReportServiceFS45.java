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

/**营销订单交付追踪表-甘特图（单个E件）
 * Created by liwuhua on 16/11/28.
 */

public interface IReportServiceFS45 {
     
	 public String getListFs45(QueryBean queryBean,List<String> palantList) throws JsonProcessingException,DataAccessException;
	
	
}
