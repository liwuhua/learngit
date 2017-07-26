package com.yjdj.view.core.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.THM_FS12;
import com.yjdj.view.core.entity.mybeans.THM_MSEG_06;
import com.yjdj.view.core.entity.mybeans.THM_VBAP_01;

/**
 * Created by liwuhua on 16/11/21.
 */
@MyBatisRepository
public interface IReportMapperFS20 extends BaseMapper{

    /**
     * 根据条件查询期间费用   
     * @param map
     */
	
    public   List<HashMap>  getOrderMarket(HashMap<String,Object> map) throws DataAccessException;


    public List<Map<String,Object>> getDataByArfe(@Param("kdauf") String kdauf,
                                       @Param("kdpos") String kdpos,
                                       @Param("baugr") String baugr,
                                       @Param("aufnr") String aufnr) throws DataAccessException;


    public Map<String,Object> getDataByMa(@Param("kdauf") String kdauf,
                                     @Param("kdpos") String kdpos,
                                     @Param("baugr") String baugr,
                                     @Param("aufnr") String aufnr) throws DataAccessException;

    public Map<String,Object> getDataBySon(@Param("kdauf") String kdauf,
                                          @Param("kdpos") String kdpos,
                                          @Param("baugr") String baugr,
                                          @Param("aufnr") String aufnr) throws DataAccessException;


    //获取子节点单号
    public Set<String> getChildList(@Param("kdauf") String kdauf,
                                     @Param("kdpos") String kdpos,
                                     @Param("baugr") String baugr,
                                     @Param("aufnr") String aufnr) throws DataAccessException;
    
}
