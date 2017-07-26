package com.yjdj.view.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.MdmArbpl;
import com.yjdj.view.core.entity.mybeans.MdmGroes;
import com.yjdj.view.core.entity.mybeans.MdmSernr;
import com.yjdj.view.core.entity.mybeans.MdmZlevel;
import com.yjdj.view.core.entity.mybeans.THM_FS62;

/**
 * create on 2017/03/02
 * @author yangzhijie
 *
 */
@MyBatisRepository
public interface IReportMapperFS62 extends BaseMapper{
	
	 //和mybatis中的Mapper.xml文件对应  sql
		List<THM_FS62> selectData(
				@Param("plantValue") List<String> plantValue,  
				@Param("arbplValue") List<String> arbplValue,
				@Param("groesValue") List<String> groesValue,
				@Param("sernrValue") List<String> sernrValue,
				@Param("zlevelValue") List<String> zlevelValue,
				@Param("startTime") String startTime, 
				@Param("endTime") String endTime,
				@Param("startitem") int startitem,
				@Param("pageitem") int pageitem,
				@Param("flag") int flag) throws DataAccessException;
		
		List<MdmArbpl> selectArbpl(
				@Param("arbplLike") String arbplLike) throws DataAccessException;
		
		List<MdmGroes> selectGroes(
				@Param("groesLike") String groesLike) throws DataAccessException;
		
		List<MdmSernr> selectSernr(
				@Param("sernrLike") String sernrLike,
				@Param("start") int start,
				@Param("limit") int limit) throws DataAccessException;
		
		List<MdmZlevel> selectZlevel(
				@Param("zlevelLike") String zlevelLike) throws DataAccessException;
	
}











