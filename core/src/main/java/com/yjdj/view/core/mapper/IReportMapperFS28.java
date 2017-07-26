package com.yjdj.view.core.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS15;
import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS24;
import com.yjdj.view.core.entity.mybeans.THM_FS24_01;
import com.yjdj.view.core.entity.mybeans.THM_IMP_FS15;

/**
 * create on 2016/12/07
 * @author lcl
 *
 */
@MyBatisRepository
public interface IReportMapperFS28 extends BaseMapper{
	
    /**
     * 
     * @param date 判断当天是否更新过hive传来的新数据
     */
	public void init(HashMap<String,String> map);
	
	/**
	 * 原始数据一级明细查询
	 * @param map
	 * @return
	 */
	public List<THM_FS24_01> getFirstDetail(HashMap<String,String> map);
	/**
	 * 原始数据二级明细查询
	 * @param map
	 * @return
	 */
	public List<THM_FS24_01> getSecondDetail(HashMap<String,String> map);
	
	/**
	 * 从导入表THM_FS24_TEMP_IMP中获取一级明细
	 * @param map
	 * @return
	 */
	public List<THM_FS24_01> getFirstDetailImp(HashMap<String,String> map);
	/**
	 * 从导入表THM_FS24_TEMP_IMP中获取二级明细
	 * @param map
	 * @return
	 */
	public List<THM_FS24_01> getSecondDetailImp(HashMap<String,String> map);
	
	/**
	 * 验证公司是否输入正确
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,String>> selectCompanyCode(@Param("txtmd") String txtmd) throws DataAccessException; 
    
	/**
	 * 获取所有的 字典表中的名称描述，用与导入时判断是否是带有公司代码的二级明细
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,String>> getFieldName() throws DataAccessException;
   
	/**
	 * 查询导入表中是否有查询符合条件的数据
	 * @param map
	 */
	public int hiveOrImp(HashMap<String,String> map);
	
	/**
	 * 判断
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int insertOrUpdate(HashMap<String,String> map) throws DataAccessException;
	/**
    * 
    * @param yearMonth
    * @param bukrs
    * @param list
    * @throws DataAccessException
    */
	public void saveOrUpdateData(HashMap<String,String> map) throws DataAccessException;

}











