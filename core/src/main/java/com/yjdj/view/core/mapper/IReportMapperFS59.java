package com.yjdj.view.core.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chengxuan on 17/3/6.
 */
@MyBatisRepository
public interface IReportMapperFS59 extends BaseMapper{

    public List<Map<String,Object>> getDataFs59(@Param("compCodeValue") String compCodeValue,
                                                  @Param("dateYearMonthStart") String dateYearMonthStart,
                                                  @Param("dateYearMonthEnd") String dateYearMonthEnd) throws DataAccessException;

}
