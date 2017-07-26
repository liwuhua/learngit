package com.yjdj.view.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.THM_FS54;


/**
 * create on 2017/3/30
 * @author yangzhijie
 *
 */
@MyBatisRepository
public interface IReportMapperFS54 extends BaseMapper{
	
		public void saveData(
				@Param("list") List<List<String>> list,
				@Param("yearMonth") String yearMonth) throws DataAccessException;		
		
		public List<THM_FS54> selectData(
				@Param("cpxh") List<String> cpxh,
				@Param("aname") List<String> aname,  
				@Param("matnrValue") List<String> matnrValue,
				@Param("matnrInterval") List<Map<String, String>> matnrInterval,
				@Param("trainmodel") List<String> trainmodel,
				@Param("fwz") List<String> fwz,  
				@Param("fwd") List<String> fwd,
				@Param("yearMonth") String yearMonth) throws DataAccessException;
		
		//从主数据查询所有服务站
		public List<Map<String,String>> selectFwz(
				@Param("fwzLike") String fwzLike,
				@Param("fwzIdLike") String fwzIdLike) throws DataAccessException;
		
		//从主数据查询所有服务点
		public List<Map<String,String>> selectFwd(
				@Param("fwdLike") String fwdLike,
				@Param("fwdIdLike") String fwdIdLike) throws DataAccessException;
		
		//从主数据查询所有车型
		public List<Map<String,String>> selectCx(
				@Param("cxLike") String cxLike,
				@Param("cxIdLike") String cxIdLike) throws DataAccessException;
		
		//从54表获取产品型号
		public List<String> getCpxh(
				@Param("cpxhLike") String cpxhLike) throws DataAccessException;	
		
		//从54表获取配件名称
		public List<String> getAname(
				@Param("anameLike") String anameLike) throws DataAccessException;	
				
		//从MDM_TMATERIAL查询所给List中哪些是正确的物料编码
		public List<String> selectMaterialInList(
				@Param("materialList") List<String> materialList) throws DataAccessException;

		
}











