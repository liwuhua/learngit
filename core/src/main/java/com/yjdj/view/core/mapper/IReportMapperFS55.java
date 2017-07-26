package com.yjdj.view.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.THM_FS55;

/**
 * create on 2017/03/02
 * @author yangzhijie
 *
 */
@MyBatisRepository
public interface IReportMapperFS55 extends BaseMapper{
	
	 //和mybatis中的Mapper.xml文件对应  sql
		List<THM_FS55> selectData(
				@Param("vkorgValue") List<String> vkorgValue,
				@Param("plantValue") List<String> plantValue,  
				@Param("kunnrValue") List<String> kunnrValue, 
				@Param("matnrValue") List<String> matnrValue,
				@Param("matnrInterval") List<Map<String, String>> matnrInterval,
				@Param("startTime") String startTime, 
				@Param("endTime") String endTime,
				@Param("startitem") int startitem,
				@Param("pageitem") int pageitem,
				@Param("flag") int flag) throws DataAccessException;
	
}











