package com.yjdj.view.core.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

/**
 * Created by liwuhua on 16/11/7.
 */
@MyBatisRepository
public interface IReportMapperKPI10 extends BaseMapper{

	
	//计算得到正点交付数量
	public Map  punDeliNum(HashMap  map)throws DataAccessException;
	 
	
	//当月正点交付率  : 
	public Map  punDeliRate(HashMap  map)throws DataAccessException;
	
	// 正点率第一层下钻
	public List<Map>  firstLayer(HashMap  map)throws DataAccessException;
	  
	// 正点率第二层下钻
	public List<Map>  secondLayer(HashMap  map)throws DataAccessException;
	
	// 正点率第三层下钻
	public List<Map>  thirdLayer(HashMap  map)throws DataAccessException;

}
