package com.yjdj.view.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS15;
import com.yjdj.view.core.entity.mybeans.THM_IMP_FS15;
import com.yjdj.view.core.entity.mybeans.THM_IMP_FS17;

/**
 * create on 2016/11/04
 * @author yangzhijie
 *
 */
@MyBatisRepository
public interface IReportMapperFS15 extends BaseMapper{
	
		/**
	     * 
	     * @param dateYearMonth
	     * @param compCodeValue
	     * @return
	     */
		 //和mybatis中的Mapper.xml文件对应  sql
		List<THM_IMP_FS15> selectData(
				@Param("dateYearMonth") String dateYearMonth, 
				@Param("compCodeValue") List<String> compCodeValue) throws DataAccessException;     
		 //和mybatis中的Mapper.xml文件对应  sql
		
		List<THM_IMP_FS15> selectDataFS15(
					@Param("dateYearMonth") String dateYearMonth, 
					@Param("compCodeValue") String compCodeValue) throws DataAccessException; 
		/**
		 * 通过公司代码取出公司编制单位
		 * @return
		 */
		public List<Map<String,String>> selectCompany(@Param("compCodeValue") List<String> compCodeValue) throws DataAccessException;
		
		/**
		 * 
		 * @param itemNo
		 * @param yearMonth
		 * @param bukrs
		 * @param list
		 * @throws DataAccessException
		 */
		public void saveOrUpdateData(@Param("yearMonth") String yearMonth, 
				@Param("bukrs") String bukrs, @Param("list") List<InsertFieldTHM_FS15> list) throws DataAccessException;
		/**
		 * 
		 * @param itemNo
		 * @param yearMonth
		 * @param bukrs
		 * @param list
		 * @throws DataAccessException
		 */
		public void saveOrUpdateDataFS15(@Param("yearMonth") String yearMonth, 
				@Param("bukrs") String bukrs, @Param("list") List<InsertFieldTHM_FS15> list) throws DataAccessException;
		
		
		/**
		 * 验证公司是否输入正确
		 * @return
		 * @throws DataAccessException
		 */
		public List<Map<String,String>> selectCompanyCode(@Param("txtmd") String txtmd) throws DataAccessException; 
	    /**
	     * 取数据字典
	     *
	     * @return
	     * @throws DataAccessException
	     */
	    public List<THM_IMP_FS15> selectField(@Param("years") String years) throws DataAccessException;
	    /**
	     * 取最新模板日期
	     *
	     * @return
	     * @throws DataAccessException
	     */
	    public Map<String, String> selectMaxYear() throws DataAccessException;

	    
}











