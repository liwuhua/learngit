package com.yjdj.view.core.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 2016/11/28.
 */
@MyBatisRepository
public interface IReportMapperFS25 {

    public List<Map<String,Object>> getListMap(@Param("aufnr") String aufnr);
}
