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
 * Created by liwuhua on 16/11/7.
 */
@MyBatisRepository
public interface IReportMapperFS18 extends BaseMapper{

    /**
     * 根据条件查询营销订单交付追踪表
     * @param BUKRS    公司代码
     * @param FISCPER  年月期间
     */

	public List<Map>  getMarorderdeliver(HashMap<String,Object> map)throws DataAccessException;
    
}
