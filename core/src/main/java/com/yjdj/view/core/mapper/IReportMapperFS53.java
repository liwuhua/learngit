package com.yjdj.view.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.MdmGzlx;
import com.yjdj.view.core.entity.mybeans.MdmProductModel;
import com.yjdj.view.core.entity.mybeans.THM_FS53;

/**
 * create on 2017/02/14
 * @author yangzhijie
 *
 */
@MyBatisRepository
public interface IReportMapperFS53 extends BaseMapper{
	
	 //和mybatis中的Mapper.xml文件对应  sql
		List<THM_FS53> selectData(
				@Param("gzlxList") List<String> gzlxList,
				@Param("year") String year) throws DataAccessException;
		
		List<THM_FS53> selectDataWithMonth(
				@Param("gzlxList1") List<String> gzlxList1,
				@Param("year1") String year1) throws DataAccessException;
		
		List<THM_FS53> selectDataTotal(
				@Param("gzlxList2") List<String> gzlxList2,
				@Param("year2") String year2) throws DataAccessException;
		
		List<MdmGzlx> selectGzlx(
				@Param("gzlxLike") String gzlxLike) throws DataAccessException;
	
}











