package com.yjdj.view.core.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangkai on 2016/11/3.
 */
@MyBatisRepository
public interface IReportMapperThmVbap01 {
    /**
     * 获取tableName中所有的销售订单vbeln
     * @return
     */
    public List<String> getVbeln(
    		               @Param("tableName") String tableName,
    		 			   @Param("vbeln") String vbeln,
                           @Param("start") Integer start,
                           @Param("limit") Integer limit);

    
    /**
     * 获取tableName中所有的生产订单aufnr
     * @return
     */
    public List<String> getAufnr(
    		               @Param("tableName") String tableName,
    		 			   @Param("aufnr") String aufnr,
                           @Param("start") Integer start,
                           @Param("limit") Integer limit);
    
    
    /**
     * 获取tableName中所有的销售订单行号  linenum
     * @return
     */
    public List<String> getLinenum(
    		               @Param("tableName") String tableName,
    		 			   @Param("linenum") String linenum,
                           @Param("start") Integer start,
                           @Param("limit") Integer limit);
    
    
    /**
     * 获取tableName中所有的产品型号  productmodel
     * @return
     */
    public List<String> getProductmodel(
    		               @Param("tableName") String tableName,
    		 			   @Param("productmodel") String productmodel,
                           @Param("start") Integer start,
                           @Param("limit") Integer limit);
    
    
    /**
     * 根据销售组织和销售部门获取THM_VBAP_01中销售订单vbeln
     * @param vkorgValue
     * @param vkburValue
     * @return
     */
    public List<String> getVbelnByOrgAndBur(@Param("vkorgValue") List<String> vkorgValue,
                                            @Param("vkburValue") List<String> vkburValue,
                                            @Param("vbeln") String vbeln,
                                            @Param("start") Integer start,
                                            @Param("limit") Integer limit);
}
