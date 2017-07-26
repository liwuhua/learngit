package com.yjdj.view.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.THM_MSEG_04;

/**
 * create on 2016/11/01
 * @author yangzhijie
 *
 */
@MyBatisRepository
public interface IReportMapperFS04 extends BaseMapper{
	
	/**
     * 
     * @param plantValue
     * @param ebelnValue
     * @param matnrValue
     * @param vendorValue
     * @param purGroupValue
     * @param timediffValue
     * @param startTime
     * @param endTime
     * @return
     */
	 //和mybatis中的Mapper.xml文件对应  sql
		List<THM_MSEG_04> selectData(
				@Param("plantValue") List<String> plantValue,
				@Param("ebelnValue") List<String> ebelnValue,  
				@Param("matnrValue") List<String> matnrValue, 
				@Param("vendorValue") List<String> vendorValue,
				@Param("purGroupValue") List<String> purGroupValue, 
				@Param("timediffValue") List<String> timediffValue,
				@Param("matnrInterval") List<Map<String,String>> matnrInterval, 
				@Param("startTime") String startTime, 
				@Param("endTime") String endTime,
				@Param("startitem") int startitem,
				@Param("pageitem") int pageitem,
				@Param("flag") int flag) throws DataAccessException;
    
     
	
	
}











