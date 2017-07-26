package com.yjdj.view.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.THM_FS37;
/**
 * create on 2017/03/28
 * @author zhangwenguo
 *
 */
@MyBatisRepository
public interface IReportMapperFS37 extends BaseMapper{
	/**
	 * 数据请求
	 * @param matnr
	 * @param werks
	 * @param dateTime
	 * @throws DataAccessException
	 */
	public void getData(@Param("matnr") String matnr, @Param("werks") String werks, @Param("dateTime") String dateTime)throws DataAccessException;
	
	/**
	 * 查询数据
	 * @return
	 */
	public List<THM_FS37> selectData()throws DataAccessException;

	
}
