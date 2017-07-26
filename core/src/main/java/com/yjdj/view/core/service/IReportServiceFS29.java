package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

/**营销订单交付追踪表-甘特图（单个E件）
 * Created by liwuhua on 16/11/28.
 */

public interface IReportServiceFS29 {
     
	 public Object getListFs29(QueryBean queryBean,List<String> plantList) throws JsonProcessingException,DataAccessException, IOException;
	
}
