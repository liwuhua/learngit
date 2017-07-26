package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS24_01;
import com.yjdj.view.core.entity.mybeans.THM_FS24_Result;
import com.yjdj.view.core.util.ExcelExportUtil24;

public interface IReportServiceFS26 {
	/**
	 * 数据初始化，判断当天是否已经更新hive中的数据
	 * @param date
	 */
	public void init();
	
	/**
	 * 查询一级明细
	 * @param queryBean
	 * @return
	 */
	public List<THM_FS24_01> getFirstDetail(QueryBean queryBean);
	/**
	 * 查询二级级明细
	 * @param queryBean
	 * @return
	 */
	public List<THM_FS24_01> getSecondDetail(QueryBean queryBean);
	
	/**
	 * 将多个子项累加成一条记录,id 行数
	 */
	public THM_FS24_01 add(List<THM_FS24_01> list,int id,String name,String racct);
    
	/**
     * 
     * @param list
     * @return
     * 将查询出来的 group_concat() 的二级明细解析成新的一行记录
     */
    public List<THM_FS24_01> generateSecondDetail(List<THM_FS24_01> list);
    
    /**
     * 抽取子项合并公共代码
     * @param list
     * @return
     */
    public List<THM_FS24_01> listMerge(List<THM_FS24_01> list);
    /**
     * 获取导入表中数据
     * @param map
     * @return
     */
    public List<THM_FS24_01> getDetailImp(QueryBean queryBean,int flag);
    
    /**
     * 返回最终查询的JSON字符串，一级和二级明细都是调用该方法，
     * 包含的项有
     *item_column  订单描述，或者公司代码
	  id 行号
	  racct 会计号
	  TXTMD 项目描述
	  balance 上年发生额
	  year_plan 本年计划
	  period_plan 本期计划 
	  period_balance 本期发生额
	  ratio 执行率
	  startdate 开始日期
	  enddate   结束日期
     * 
     * @param queryBean
     * @param flag 一级和二级明细查询标志
     * @return
     */
    public  List<THM_FS24_Result> getFinalDetail(QueryBean queryBean,int flag);
    
    /**
     * 
     * @param list
     * @return
     */
    public List<THM_FS24_Result> convertResult(List<THM_FS24_01> list);
    
    
    /**
     * 返回一个合计行次的子项
     * @param list
     * @param id 行次
     * @return
     */
    public List<THM_FS24_01> returnSubList(List<THM_FS24_01> list,int startId,int endId);
    /**
     * 返回Json字符串
     * @param queryBean
     * @param flag
     * @return
     */
    public String returnFinalResult(QueryBean queryBean,int flag);
    
    
    /**
     * 导出数据
     * @param queryBean
     * @param flag
     * @return
     */
    public List<List<Object>> export(QueryBean queryBean,int flag);
    
    /**
     * 导出Excel
     * @param queryBean
     * @param flag
     * @return
     */
    public ExcelExportUtil24 exportExcel(QueryBean queryBean,int flag);
    
    /**
     * 数据导入
     * @param filePath
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws DataAccessException
     * @throws InvalidFormatException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object importData(String filePath,List<String> compValues) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
    
    
    /**
     * 导入表的一级明细数据查询
     * @param queryBean
     * @return
     */
    public List<THM_FS24_01> getImpFirst(QueryBean queryBean);
    /**
     * 导入表的二级明细数据查询
     * @param queryBean
     * @return
     */
    public List<THM_FS24_01> getImpSecond(QueryBean queryBean);
    
    /**
     * 编制单位
     * @param compValues
     * @return
     */
    public String getComName(List<String> compValues);
}
