package com.yjdj.view.core.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.THM_FS12;

/**
 * Created by liwuhua on 16/11/7.
 */
@MyBatisRepository
public interface IReportMapperFS12 extends BaseMapper{

    /**
     * 根据条件查询期间费用   
     * @param BUKRS    公司代码
     * @param FISCPER  年月期间
     */
      
  public List<THM_FS12>  getPeriodexpense(HashMap map) throws DataAccessException;
  
  /**
   * 根据条件查询折旧费 
   * @param BUKRS    公司代码
   * @param FISCPER  年月期间
   */
    
public Map  getCurrlastCount65(HashMap map) throws DataAccessException;

/**
 * 根据条件查询研发费用  
 * @param BUKRS    公司代码
 * @param FISCPER  年月期间
 */
  
public Map  getCurrlastCount85(HashMap map) throws DataAccessException;
  
  /**
   * 验证公司是否输入正确
   *
   * @return
   * @throws DataAccessException
   */
  public List<Map<String, String>> selectComCodeBytxtmd(@Param("txtmd") String txtmd) throws DataAccessException;
    
  /**
  *
  * @param startyearmonth
  * @param endyearmonth
  * @param comcode
  * @param thmlist
  * @throws DataAccessException
  */
 public void saveOrUpdateData(@Param("startyearmonth") String startyearmonth,@Param("endyearmonth") String endyearmonth,
         @Param("comcode") String comcode, @Param("list") List<THM_FS12> tmpDataList) throws DataAccessException;

	public List<Map> selectLine(@Param("rownum") String rownum) throws DataAccessException;

	@SuppressWarnings("rawtypes")
	public List<Map> selectLineByObj(THM_FS12 thmFs12) throws DataAccessException;

	public void insert(THM_FS12 thmFs12) throws DataAccessException;

	public boolean exists(@Param("bukrs") String bukrs) throws DataAccessException;

	public String existsboolean(@Param("boolean") Boolean b) throws DataAccessException;

}
