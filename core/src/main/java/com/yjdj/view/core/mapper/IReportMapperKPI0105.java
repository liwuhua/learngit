package com.yjdj.view.core.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.MdmSecondCost;
import com.yjdj.view.core.entity.mybeans.THM_FS22_01;
import com.yjdj.view.core.entity.mybeans.THM_FS22_02;
import com.yjdj.view.core.entity.mybeans.THM_KPI_0105_01;
import com.yjdj.view.core.entity.mybeans.THM_KPI_0105_02;
import com.yjdj.view.core.entity.mybeans.THM_KPI_0105_03;

/**
 * Created by liuchengli on 16/12/29.
 */
@MyBatisRepository
public interface IReportMapperKPI0105 extends BaseMapper{

 /**
  * 查询 营业收入金额（当年累计），净利润金额（当年累计）
  * @param map
  * @return
  */
 public   THM_KPI_0105_03  getYearPlan(HashMap<String,String> map);
	  
 /**
  * 查询 营业收入金额（当年累计），净利润金额（当年累计）
  * @param map
  * @return
  */
  public   THM_KPI_0105_01  getYearAll(HashMap<String,String> map);
  
  /**
   * 查询 营业收入金额，净利润金额,按照公司钻取
   * @param map
   * @return
   */
   public   List<THM_KPI_0105_01>  getYearAllBukrs(HashMap<String,String> map);
  
  
  /**
   * 应收账款,按月总计
   * @param map
   * @return
   */
   public   List<THM_KPI_0105_02>  getDueTo(HashMap<String,String> map);
   
   
   /**
    * 应收账款,按月总计,查询具体某一个月,按照客户类型分组，一级钻取
    * @param map
    * @return
    */
    public   List<THM_KPI_0105_02>  getFirstCustType(HashMap<String,String> map);
  
    /**
     * 应收账款,按月总计,查询具体某一个月,具体某一个客户类型，下面的客户明细，二级钻取
     * @param map
     * @return
     */
     public   List<THM_KPI_0105_02>  getSecondCustType(HashMap<String,String> map);
   
    
     /**
      * 应收账款,按月总计,查询具体某一个月,按照公司类型分组，一级钻取
      * @param map
      * @return
      */
      public   List<THM_KPI_0105_02>  getFirstBukrsType(HashMap<String,String> map);
    
      
      /**
       * 应收账款,按月总计,查询具体某一个月,按照公司类型分组，一级钻取
       * @param map
       * @return
       */
      public   List<THM_KPI_0105_02>  getHYFirstBukrsType(HashMap<String,String> map);
      
      /**
       * 应收账款,按月总计,查询具体某一个月,具体某一个公司类型，下面的客户明细，二级钻取
       * @param map
       * @return
       */
       public   List<THM_KPI_0105_02>  getSecondBukrsType(HashMap<String,String> map);
       
       /**
        * 查询外部公司最新记录的日期
        * @param map
        * @return
        * @throws DataAccessException
        */
       public   List<HashMap>  getLatestDate();
       
       /**
        * 查询外部公司最新记录的日期
        * @param map
        * @return
        * @throws DataAccessException
        */
       public   List<HashMap>  getBadAccountValue(HashMap<String,String> map);
       

}
