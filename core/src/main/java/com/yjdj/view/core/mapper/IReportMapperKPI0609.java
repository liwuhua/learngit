package com.yjdj.view.core.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * Created by liwuhua on 16/11/7.
 */
@MyBatisRepository
public interface IReportMapperKPI0609 extends BaseMapper{

	// 销售收入指标及完成情况
	public Map  incomeAndCompleIndex(HashMap  map)throws DataAccessException;
	
	//销售收入实际下钻  下钻1层  
	public List<Map>  saleIncomeFiLayer(HashMap  map)throws DataAccessException;
	  
	// 点选某市场板块下钻至客户	
	public  List<Map>  getcustoByKdgrp(HashMap  map)throws DataAccessException;
	
	 //年度合同签订金额下钻 
	public List<Map>  signedLayer(HashMap  map)throws DataAccessException;
	
	//获取饼图数据
	public List<Map>  getRatePie(HashMap  map)throws DataAccessException;
	
	//饼图钻取获得业务部门金额及比率
	public List<Map>  getBussiApart(HashMap  map)throws DataAccessException;
	  
	//饼图饼图钻取通过业务部门获得客户金额和占比
	public List<Map>  getCustByApart(HashMap  map)throws DataAccessException;

}
