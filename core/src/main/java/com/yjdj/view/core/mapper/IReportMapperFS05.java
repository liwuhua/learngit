package com.yjdj.view.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS05;
import com.yjdj.view.core.entity.mybeans.THM_FS05;
/**
 * create on 2016/11/1
 * @author zhangwenguo
 *
 */
@MyBatisRepository
public interface IReportMapperFS05 extends BaseMapper{

	/**
	 * 根据给出的时间范围查询各项税的总值
	 * @param startTime
	 * @param endTime
	 * @param compCodeValue
	 * @return
	 */
	public List<THM_FS05> getTaxData(
			@Param("startTime") String startTime,
			@Param("endTime") String endTime,
			@Param("compCodeValue") String compCodeValue)throws DataAccessException;
	/**
	 * 取出公司代码和名称
	 * @return
	 */
	public Map<String,Object> getCompTxtmdByCode(@Param("compCode") String compCode)throws DataAccessException;
	public Map<String,String> getCompTxtmdByTxtmd(@Param("compTxtmd") String compTxtmd)throws DataAccessException;
	public Map<String,Object> getCompTxtmdNFAndYDByCode(@Param("compCode") String compCode)throws DataAccessException;
	public Map<String,String> getCompTxtmdNFAndYDByTxtmd(@Param("compTxtmd") String compTxtmd)throws DataAccessException;
	
	/**
	 * 
	 * @param itemNo
	 * @param yearMonth
	 * @param bukrs
	 * @param list
	 * @throws DataAccessException
	 */
	public void saveOrUpdateData(@Param("list") List<InsertFieldTHM_FS05> list,@Param("FISCPER") String fiscper,@Param("RBUKRS") String rbukrs) throws DataAccessException;

	
}
