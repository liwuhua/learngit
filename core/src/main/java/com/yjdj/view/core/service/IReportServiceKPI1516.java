package com.yjdj.view.core.service;

import java.io.IOException;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IReportServiceKPI1516 {
	
	/**
	 * 车间仓材料总金额 和 订单在产金额
	 * @param time
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	public String getMTMandOIPM(String time);
	
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public String getFirstDetail(String time);
	
	 /**
	  * 
	  * @param time
	  * @return
	  */
   public String getVClassShopDetail(String time);
   
	 /**
	  * 
	  * @param time
	  * @return
	  */
   public String getOrderDetail(String time);
	
   /**
    * 
    * @param time
    * @return
    */
	public String getSecondWorkShopDetail(String time);
		

}
