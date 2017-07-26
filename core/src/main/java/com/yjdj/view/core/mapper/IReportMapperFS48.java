

package com.yjdj.view.core.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

/**
 * Created by liwuhua on 2017/03/30.
 */
@MyBatisRepository
public interface IReportMapperFS48 extends BaseMapper{
     
   /**
     * 获取所有服务站点
     * @param  
     * @return
     * @throws DataAccessException 
     */
    public List<String> getStations( ) throws DataAccessException;
    
    
    /**
     * 根据服务站获取所有段所
     * @param  服务站  
     * @return 服务站下的段所
     * @throws DataAccessException 
     */
    public List<LinkedHashMap> getDuansuos(@Param("stationid")String  stationid) throws DataAccessException;
    
	
	
    /**
     * 下钻至下钻至产品配属
     * @param  startMonth  endMonth   某一服务站id(stationid)
     * @return
     * @throws DataAccessException 
     */
    public List<Map> selecProductAttach(  @Param("startMonth") String startMonth,
                                          @Param("endMonth") String endMonth,
                                          @Param("stationid") String stationid,
                                          @Param("startitem") String startitem,
                                          @Param("pageitem") String pageitem
                                               ) throws DataAccessException;
    
    
    /** 
     * 下钻至下钻至备品配件
     * @param  startMonth  endMonth   某一服务站id(stationid)
     * @return
     * @throws DataAccessException
     */
    public List<Map> selecSparepart(      @Param("startMonth") String startMonth,
                                          @Param("endMonth") String endMonth,
                                          @Param("stationid") String stationid,
                                          @Param("startitem") String startitem,
                                          @Param("pageitem") String pageitem
                                               ) throws DataAccessException;
    
    
    
    /**
     * 下钻至下钻至产品配属(新)
     * @param   某一服务站id(stationid)
     * @return
     * @throws DataAccessException 
     */
    public List<Map> newSelecProductAttach(@Param("stationid") String stationid,
                                          @Param("startitem") String startitem,
                                          @Param("pageitem") String pageitem
                                               ) throws DataAccessException;
    
    
    
    /** 
     * 下钻至下钻至备品配件（新）
     * @param  某一服务站id(stationid)
     * @return
     * @throws DataAccessException
     */
    public List<Map> newSelecSparepart( @Param("stationid") String stationid,
                                          @Param("startitem") String startitem,
                                          @Param("pageitem") String pageitem
                                               ) throws DataAccessException;
    
   
    
}

