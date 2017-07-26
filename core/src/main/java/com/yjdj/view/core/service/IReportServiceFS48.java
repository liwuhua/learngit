package com.yjdj.view.core.service;

import java.io.IOException;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

/**
 * Created by liwuhua on 2017/3/30.
 */
public interface IReportServiceFS48 {
     
	/**
     * 下钻至下钻至产品配属/ 下钻至下钻至备品配件
     * @param  startMonth  endMonth   某一服务站(serviStation)
     * @return
     * @throws DataAccessException
     */
    public String selecProductAttachOrSparepart(QueryBean queryBean) throws JsonProcessingException,IOException,DataAccessException;
   
    /**
     * 获取所有服务站点
     * @param  
     * @return
     * @throws DataAccessException
     */
    public String getStations( ) throws JsonProcessingException,IOException,DataAccessException;
    
    
}
