package com.yjdj.view.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS17;
import com.yjdj.view.core.entity.mybeans.THM_IMP_FS17;

/**
 * create on 2016/11/21
 *
 * @author yangzhijie
 *
 */
@MyBatisRepository
public interface IReportMapperFS17 extends BaseMapper {

    /**
     * 查询数据
     *
     * @param dateYearMonth
     * @param compCodeValue
     * @return
     */
    //和mybatis中的Mapper.xml文件对应  sql
    public List<THM_IMP_FS17> selectData(@Param("dateYearMonth") String dateYearMonth,
            @Param("compCodeValue") List<String> compCodeValue) throws DataAccessException;

    public List<THM_IMP_FS17> selectDataFS17(@Param("dateYearMonth") String dateYearMonth,
            @Param("compCodeValue") String compCodeValue) throws DataAccessException;

    
    /**
     * 取出公司代码
     *
     * @param compCodeValue
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, String>> selectCompany(@Param("compCodeValue") List<String> compCodeValue) throws DataAccessException;

    /**
     *
     * @param yearMonth
     * @param bukrs
     * @param list
     * @throws DataAccessException
     */
    public void saveOrUpdateData(@Param("yearMonth") String yearMonth,
            @Param("bukrs") String bukrs, @Param("list") List<InsertFieldTHM_FS17> list) throws DataAccessException;
    /**
    *
    * @param yearMonth
    * @param bukrs
    * @param list
    * @throws DataAccessException
    */
   public void saveOrUpdateDataFS17(@Param("yearMonth") String yearMonth,
           @Param("bukrs") String bukrs, @Param("list") List<InsertFieldTHM_FS17> list) throws DataAccessException;
    
    /**
     * 验证公司是否输入正确
     *
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, String>> selectCompanyCode(@Param("txtmd") String txtmd) throws DataAccessException;

    /**
     * 取数据字典
     *
     * @return
     * @throws DataAccessException
     */
    public List<THM_IMP_FS17> selectField(@Param("years") String years) throws DataAccessException;
    /**
     * 取最新模板日期
     *
     * @return
     * @throws DataAccessException
     */
    public Map<String, String> selectMaxYear() throws DataAccessException;

    
}
