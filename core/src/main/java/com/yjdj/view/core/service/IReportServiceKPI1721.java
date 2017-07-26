package com.yjdj.view.core.service;

import java.io.IOException;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IReportServiceKPI1721 {
	
	/**
	 * 当月采购到货正点率
	 * @return
	 * @throws DataAccessException
	 */
	public Object getOrdersSumRate(String time) throws JsonProcessingException, IOException ,DataAccessException;
	
	/**
	 * 取得供应商正点率 和 采购组维度钻取
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	public Object getLifnrRateAndEkgrpRate(String time) throws JsonProcessingException, IOException ,DataAccessException;
    
	/**
	 * 采购组 采购项数
	 * @return
	 * @throws DataAccessException
	 */
	public Object getEkgrpByRate(String label, String time) throws JsonProcessingException, IOException ,DataAccessException;
	/**
	 * 取得原材料
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	public Object getMaterial(String time) throws JsonProcessingException, IOException ,DataAccessException;
	
	/**
	 * 取得产品
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	public Object getProduct(String time) throws JsonProcessingException, IOException ,DataAccessException;
	/**
	 * 原材料评估类下钻
	 * @param time
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	public Object getMaterialGoDown(String time,String flag) throws JsonProcessingException, IOException ,DataAccessException;
	
	/**
	 * 产成品无动态下钻
	 * @param time
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	public Object getProductDynamicGoDown(String time) throws JsonProcessingException, IOException ,DataAccessException;
	
	
}
