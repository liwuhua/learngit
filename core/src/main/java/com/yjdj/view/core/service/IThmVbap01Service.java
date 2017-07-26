package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * Created by wangkai on 2016/11/4.
 */
public interface IThmVbap01Service {
    
	/**
     * 获取销售订单
     * @return
     */
    public String getVbeln(String tableName,String vbeln,int start,int limit) throws JsonProcessingException;
    
    
	/**
     * 获取生产订单
     * @return
     */
    public String getAufnr(String tableName,String aufnr,int start,int limit) throws JsonProcessingException;
    

    /**
     * 获取销售订单行号
     * @return
     */
    public String getLinenum(String tableName,String linenum,int start,int limit) throws JsonProcessingException;

    /**
     * 获取产品型号
     * @return
     */
    public String getProductmodel(String tableName,String productmodel,int start,int limit) throws JsonProcessingException;

    /**
     * 获取销售订单
     * @param vkorgValue
     * @param vkburValue
     * @param vbeln
     * @param start
     * @param limit
     * @return
     */
    public String getVbelnByOrgAndBur(List<String> vkorgValue,
                                      List<String> vkburValue,
                                      String vbeln,
                                      Integer start,
                                      Integer limit) throws JsonProcessingException;
    
    
	
}
