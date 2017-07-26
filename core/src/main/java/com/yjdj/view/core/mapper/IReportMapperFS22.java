package com.yjdj.view.core.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.MdmSecondCost;
import com.yjdj.view.core.entity.mybeans.THM_FS22_01;
import com.yjdj.view.core.entity.mybeans.THM_FS22_02;

/**
 * Created by liuchengli on 16/11/27.
 */
@MyBatisRepository
public interface IReportMapperFS22 extends BaseMapper{

    /**
     * 根据条件查询研发费用明细
     * @param BUKRS  公司代码
     * @param 起始年月区间 201601#201605  年月-年月
     * @param 订单类型  AUART
     * @param 内部定单 多选，订单号  AUFNR  ,'A524150702,A524151502'
     * 
     */
	
  public   List<THM_FS22_01>  getYanFaCost(HashMap<String,String> map);
  
  /**
   * 
   * @param map
   * @return
   * 订单号 内部定单 单个，由一级明细传入，订单号  AUFNR
   * 订单类型  AUART
   * 起始年月区间 201601#201605  年月-年月
   * BUKRS  公司代码
   * 
   */
  public  THM_FS22_02 getYanFaCostDetail(HashMap<String,String> map);
  
  
  /**
   * 查询二级明细字典表
   * @param map
   * @return
   */
  public   List<MdmSecondCost>  getSecondCostCode();
  
  /**
   * 模糊查询的内部订单号 
   * @param map
   * @return
   */
  public List<String> getInnerOrder(HashMap<String,String> map);
  
	/**
   * 
   * @param id 
   * @param year
   * @throws DataAccessException
   */
	public void saveOrUpdateData(HashMap<String,String> map) throws DataAccessException;
	
	
	
	/**
	 * 查询当前项目号的公司代码
	 * @param map
	 * @return
	 */
	public String getAuth(HashMap<String,String> map);
  
}
