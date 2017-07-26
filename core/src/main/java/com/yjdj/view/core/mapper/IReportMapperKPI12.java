package com.yjdj.view.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.THM_KIP12;

@MyBatisRepository
public interface IReportMapperKPI12 extends BaseMapper{
	
	/**
	 * 产品正点率
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP12> selectProOTDeliverRate(@Param("yearMonth") String yearMonth) throws DataAccessException;
	/**
	 *  产品完成数量
	 *  ProComp 是 Product Complete 的缩写
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP12> selectProCompCount(@Param("yearMonth") String yearMonth) throws DataAccessException;
	/**
	 * 产品未完成数量
	 * ProUnComp 是 Product Un Complete 的缩写
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP12> selectProUnCompSum(@Param("yearMonth") String yearMonth) throws DataAccessException;
	
	/**
	 * 产品完成正点率下钻
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP12> selectProOTDeliverGoDown(@Param("yearMonth") String yearMonth) throws DataAccessException;
	public List<THM_KIP12> selectProOTDeliverGoDownSum(@Param("yearMonth") String yearMonth) throws DataAccessException;
	
	/**
	 * 产品完成正点率下钻
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP12> selectProOTDeliverCompany(@Param("yearMonth") String yearMonth) throws DataAccessException;	
	/**
	 * 公司下产品完成正点率下钻
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP12> selectProOTRateDeliverByBukrsGoDown(@Param("bukrs") String bukrs, @Param("yearMonth") String yearMonth) throws DataAccessException;
	public List<THM_KIP12> selectProOTRateDeliverByBukrsGoDownSum(@Param("bukrs") String bukrs, @Param("yearMonth") String yearMonth) throws DataAccessException;
	
	/**
	 * 产品累计未完成总数钻取
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP12> selectProUnCompSumGoDown(@Param("yearMonth") String yearMonth) throws DataAccessException;
	/**
	 * 选择公司
	 * @param yearMonth
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP12> selectProUnCompCompany(@Param("yearMonth") String yearMonth) throws DataAccessException;
	/**
	 * 按公司下钻产品累计未完成总数
	 * @param bukrs
	 * @param yearMonth
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP12> selectProUnCompSumByBukrsGoDown(@Param("bukrs") String bukrs, @Param("yearMonth") String yearMonth) throws DataAccessException;
	
	/**
	 * 产品完成总数钻取
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP12> selectProCompSumGoDown(@Param("yearMonth") String yearMonth) throws DataAccessException;
	public List<THM_KIP12> selectProCompSumGoDownSum(@Param("yearMonth") String yearMonth) throws DataAccessException;
	/**
	 * 选择公司
	 * @param yearMonth
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP12> selectProCompCompany(@Param("yearMonth") String yearMonth) throws DataAccessException;
	/**
	 * 按公司下钻产品完成总数
	 * @param bukrs
	 * @param yearMonth
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP12> selectProCompSumByBukrsGoDown(@Param("bukrs") String bukrs, @Param("yearMonth") String yearMonth) throws DataAccessException;
	public List<THM_KIP12> selectProCompSumByBukrsGoDownSum(@Param("bukrs") String bukrs, @Param("yearMonth") String yearMonth) throws DataAccessException;
	
	
}
