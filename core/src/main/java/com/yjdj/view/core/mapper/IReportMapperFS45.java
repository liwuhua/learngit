package com.yjdj.view.core.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.THM_FS12;
import com.yjdj.view.core.entity.mybeans.THM_MSEG_06;
import com.yjdj.view.core.entity.mybeans.THM_VBAP_01;

/**
 * Created by liwuhua on 16/11/28.
 */
@MyBatisRepository
public interface IReportMapperFS45 extends BaseMapper{

   /**
     * 根据条件查询生产订单缺件汇总表
     * @param  map
     *
     */
	
  public   List<HashMap>  getOrderShortsum(HashMap<String,Object> map) throws DataAccessException;
    
}
