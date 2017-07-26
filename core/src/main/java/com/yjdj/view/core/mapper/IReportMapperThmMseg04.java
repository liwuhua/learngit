package com.yjdj.view.core.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangkai on 2016/11/3.
 */
@MyBatisRepository
public interface IReportMapperThmMseg04 {
    /**
     * 获取THM_MSEG_04_05中所有的销售订单vbeln
     * @return
     */
    public List<String> getEbeln(@Param("ebeln") String ebeln,
                                 @Param("start") Integer start,
                                 @Param("limit") Integer limit);
}
