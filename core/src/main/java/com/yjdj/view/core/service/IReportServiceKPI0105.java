package com.yjdj.view.core.service;

import java.text.ParseException;


import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_KPI_0105_02;
import com.yjdj.view.core.entity.mybeans.THM_KPI_0105_03;

/**研发费用明细报表
 * Created by liuchengli on 16/11/27.
 */

public interface IReportServiceKPI0105 {
    
	 /**
	  * 查询 营业收入金额（当年累计），净利润金额（当年累计）
	  * @param map
	  * @return
	  */
	 public   THM_KPI_0105_03  getYearPlan(QueryBean queryBean);
	
	/**
	  * 查询 营业收入金额（当年累计），净利润金额（当年累计）
	  * @return
	  */
     public String getYearAll(QueryBean queryBean) throws JsonProcessingException, ParseException;
     
     
     /**
      * 查询 营业收入金额，净利润金额,按照公司钻取
      * @param map
      * @return
      */
      public   String getYearAllBukrs(QueryBean queryBean) throws JsonProcessingException, ParseException;
     
     /**
      * 应收账款
      * @param map
      * @return
      */
      public  String  getDueTo(QueryBean queryBean);
      
      
      /**
       * 应收账款,按月总计,查询具体某一个月,按照公司类型分组,一级钻取
       * @param map
       * @return
       */
       public  String  getFirstType(QueryBean queryBean);
       
      /**
       * 应收账款,按月总计,查询具体某一个月,按照公司类型分组,一级钻取
       * @param map
       * @return
       */
       public  List<THM_KPI_0105_02>  getFirstBukrsType(QueryBean queryBean);
       
       
       /**
        * 应收账款,按月总计,查询具体某一个月,按照公司类型分组,一级钻取
        * @param map
        * @return
        */
        public  List<THM_KPI_0105_02>   getHYFirstBukrsType(QueryBean queryBean);
       
       
       /**
        * 应收账款,按月总计,查询具体某一个月,具体某一个公司类型，下面的客户明细，二级钻取
        * @param map
        * @return
        */
        public   String  getSecondBukrsType(QueryBean queryBean);
        
        
        /**
         * 应收账款,按月总计,查询具体某一个月,按照客户类型分组，一级钻取
         * @param map
         * @return
         */
         public  List<THM_KPI_0105_02>  getFirstCustType(QueryBean queryBean);
         
         
         /**
          * 应收账款,按月总计,查询具体某一个月,具体某一个客户类型，下面的客户明细，二级钻取
          * @param map
          * @return
          */
          public   String  getSecondCustType(QueryBean queryBean);
        
         
          /**
           * 查询外部公司最新记录的日期
           * @param map
           * @return
           * @throws DataAccessException
           */
          public   HashMap  getLatestDate();
          
          /**
           * 计算账龄的日期生成方法
           */
          public HashMap<String,String> accountAge(String date);
          /**
           * 根据传入时间，计算出坏账值
           */
          public double getBadAccountValue(String date);
          
          
        /**
         * 一级下钻计算明细
         * @param sum  需要计算的合计的集合
         * @param flag 所计算的加和项
         * @return
         */
          public THM_KPI_0105_02  getFirstSum(List<THM_KPI_0105_02> sum,int flag);
      
}
