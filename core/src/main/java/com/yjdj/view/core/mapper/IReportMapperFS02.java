package com.yjdj.view.core.mapper;

import java.util.HashMap;
import java.util.List;

import com.yjdj.view.core.entity.mybeans.THM_MSEG_06;
import com.yjdj.view.core.entity.mybeans.THM_VBAP_01;
import org.springframework.dao.DataAccessException;

/**
 * Created by liwuhua on 16/10/21.
 */
@MyBatisRepository
public interface IReportMapperFS02 extends BaseMapper{

    /**
     * 根据条件查询THM_VBAP_01,THM_MSEG_06 表
     * @param VKORG  销售组织查询条件
     * @param VKBUR  销售部门查询条件
     * @param VBELN  销售订单查询条件
     * @param MATNR  物料编码查询条件
     * @param EDATU  下周/月交付日期查询条件
     * @return  销售订单需求统计表
     */
   
   List<THM_VBAP_01> deStatistics (HashMap<String,Object> map)throws DataAccessException; 
   List<THM_MSEG_06> orderComplete(HashMap<String,Object> map)throws DataAccessException;
    
}
