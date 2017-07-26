package com.yjdj.view.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yjdj.view.core.entity.mybeans.THM_MSEG_03;

import org.springframework.dao.DataAccessException;

/**
 * create on 2016/10/21
 * @author zhangwenguo
 *
 */
@MyBatisRepository
public interface IReportMapperFS01 extends BaseMapper{


	/**
	 * @param plantValue
	 * @param matnrValue
	 * @param vendorValue
	 * @param purGroupValue
	 * @return
	 */
    //和mybatis中的Mapper.xml文件对应  sql
	List<THM_MSEG_03> selectDataTrue(
			@Param("plantValue") List<String> plantValue, 
			@Param("matnrValue") List<String> matnrValue, 
			@Param("vendorValue") List<String> vendorValue,
			@Param("purGroupValue") List<String> purGroupValue, 
			@Param("startTime") String startTime, 
			@Param("endTime") String endTime,
			@Param("matnrInterval") List<Map<String,String>> matnrInterval,
			@Param("startitem") int startitem, 
			@Param("pageitem") int pageitem,
			@Param("flag") int flag)throws DataAccessException;

    /**
     * @param plantValue
     * @param matnrValue
     * @param vendorValue
     * @param purGroupValue
     * @return
     */
	List<THM_MSEG_03> selectDataFalse(
			@Param("plantValue") List<String> plantValue, 
			@Param("matnrValue") List<String> matnrValue, 
			@Param("vendorValue") List<String> vendorValue,
			@Param("purGroupValue") List<String> purGroupValue, 
			@Param("startTime") String startTime, 
			@Param("endTime") String endTime,
			@Param("matnrInterval") List<Map<String,String>> matnrInterval,
			@Param("startitem") int startitem, 
			@Param("pageitem") int pageitem,
			@Param("flag") int flag)throws DataAccessException;
	/**
	 * 金额和数量统计
	 * @param plantValue
	 * @param matnrValue
	 * @param vendorValue
	 * @param purGroupValue
	 * @param startTime
	 * @param endTime
	 * @param matnrInterval
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_MSEG_03> selectTotalMengeAndBualtTrue(
			@Param("plantValue") List<String> plantValue, 
			@Param("matnrValue") List<String> matnrValue, 
			@Param("vendorValue") List<String> vendorValue,
			@Param("purGroupValue") List<String> purGroupValue, 
			@Param("startTime") String startTime, 
			@Param("endTime") String endTime,
			@Param("matnrInterval") List<Map<String,String>> matnrInterval)throws DataAccessException;
	/**
	 * 金额和数量统计
	 * @param plantValue
	 * @param matnrValue
	 * @param vendorValue
	 * @param purGroupValue
	 * @param startTime
	 * @param endTime
	 * @param matnrInterval
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_MSEG_03> selectTotalMengeAndBualtFalse(
			@Param("plantValue") List<String> plantValue, 
			@Param("matnrValue") List<String> matnrValue, 
			@Param("vendorValue") List<String> vendorValue,
			@Param("purGroupValue") List<String> purGroupValue, 
			@Param("startTime") String startTime, 
			@Param("endTime") String endTime,
			@Param("matnrInterval") List<Map<String,String>> matnrInterval)throws DataAccessException;
	
}











