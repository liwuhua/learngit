package com.yjdj.view.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.THM_FS52;

/**
 * create on 2017/01/17
 * @author yangzhijie
 *
 */
@MyBatisRepository
public interface IReportMapperFS52 extends BaseMapper{
	
	 //和mybatis中的Mapper.xml文件对应  sql
		List<THM_FS52> selectData(
				@Param("yearMonthList") List<String> yearMonthList) throws DataAccessException;
	
}











