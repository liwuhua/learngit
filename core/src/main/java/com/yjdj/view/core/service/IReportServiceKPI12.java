package com.yjdj.view.core.service;

import java.io.IOException;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IReportServiceKPI12 {
	
	/**
	 * 产品订单正点交付率
	 * @return
	 * @throws DataAccessException
	 */
	public Object getProOTDeliverRateAndProCompCount(String yearMonth) throws JsonProcessingException, IOException ,DataAccessException;
	
	/**
	 * 产品正点交付钻取下钻
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	public Object getProOTDeliverGoDown(String yearMonth) throws JsonProcessingException, IOException ,DataAccessException;
	/**
	 * 产品正点交付钻取下钻
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	public Object getProOTDeliverByBukrsGoDown(String yearMonth, String bukrs) throws JsonProcessingException, IOException ,DataAccessException;
	
	
	/**
	 * 产品累计未完成总数
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	public Object getProUnCompSumGoDown(String yearMonth) throws JsonProcessingException, IOException ,DataAccessException;
	/**
	 * 按公司下钻产品累计未完成总数
	 * @param yearMonth
	 * @param bukrs
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	public Object getProUnCompSumByBukrsGoDown(String yearMonth, String bukrs) throws JsonProcessingException, IOException ,DataAccessException;
	
	/**
	 * 产品完成总数钻取
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	public Object getProCompSumGoDown(String yearMonth) throws JsonProcessingException, IOException ,DataAccessException;
	/**
	 * 按公司下钻产品完成总数钻取
	 * @param yearMonth
	 * @param bukrs
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	public Object getProCompSumByBukrsGoDown(String yearMonth, String bukrs) throws JsonProcessingException, IOException ,DataAccessException;
	
	
}
