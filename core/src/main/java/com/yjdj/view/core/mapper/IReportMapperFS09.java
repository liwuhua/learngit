package com.yjdj.view.core.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.THM_FS09;

import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 2016/11/8.
 */
@MyBatisRepository
public interface IReportMapperFS09 {
    public Map<String,Object> getDmbtr(@Param("compCodeValue") List<String> compCodeValue,
                                       @Param("dateYearMonthStart") String dateYearMonthStart,
                                       @Param("dateYearMonthEnd") String dateYearMonthEnd);

    /**
     * 取5004,5005租赁费
     * @param dateYearMonthStart
     * @param dateYearMonthEnd
     * @return
     */
    public Double getTemp(@Param("dateYearMonthStart") String dateYearMonthStart,
                          @Param("dateYearMonthEnd") String dateYearMonthEnd);

    public Map<String,Object> test(@Param("compCodeValue") String compCodeValue,
                                   @Param("dateYearMonthStart") String dateYearMonthStart,
                                   @Param("dateYearMonthEnd") String dateYearMonthEnd,
                                   @Param("b") boolean b,
                                   @Param("year") String year);
    
    public Map<String,String> getTxt();
    
    /**
     * 验证公司是否输入正确
     *
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, String>> selectComCodeBytxtmd(@Param("txtmd") String txtmd) throws DataAccessException;
    
    public void saveOrUpdateData(@Param("startYearMonth") String startYearMonth,@Param("endYearMonth") String endYearMonth,
            @Param("comCode") String comCode, @Param("tmpDataList") List<THM_FS09> tmpDataList) throws DataAccessException;
    
}
