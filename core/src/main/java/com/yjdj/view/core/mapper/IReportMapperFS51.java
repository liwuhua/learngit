package com.yjdj.view.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.MdmProductModel;
import com.yjdj.view.core.entity.mybeans.THM_FS51;

/**
 * create on 2016/12/29
 * @author yangzhijie
 *
 */
@MyBatisRepository
public interface IReportMapperFS51 extends BaseMapper{
	
	 //和mybatis中的Mapper.xml文件对应  sql
		List<THM_FS51> selectData(
				@Param("productModel") List<String> productModel) throws DataAccessException;
    
		List<MdmProductModel> selectCpxh(
				@Param("cpxhLike") String cpxhLike) throws DataAccessException;
	
}











