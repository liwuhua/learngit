package com.yjdj.view.core.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 2017/02/10.
 */
@MyBatisRepository
public interface IReportMapperFS13 {
    /**
     * 获取管库员
     * @param lgpbe
     * @return
     */
    public List<String> getLgpbe(@Param("lgpbe") String lgpbe) throws DataAccessException;

    public List<Map<String,Object>> getData(@Param("plantValue") List<String> plantValue,//工厂
                                             @Param("matnrValue") List<String> matnrValue,
                                             @Param("matnrInterval") List<Map<String,String>> matnrInterval,
                                             @Param("lgortValue") List<String> lgortValue,//库存地点
                                             @Param("lgpbeValue") List<String> lgpbeValue,//管库员
                                             @Param("vendorValue") List<String> vendorValue,//供应商
                                             @Param("vendorTypeValue") List<String> vendorTypeValue,//供应商类别
                                             @Param("dateTime") String dateTime,//查询日期
                                             @Param("dayNumStart") String dayNumStart,//起始库存天数
                                             @Param("dayNumEnd") String dayNumEnd,//结束库存天数
                                             @Param("blog") String blog,
                                             @Param("startitem") int startitem,
                                             @Param("pageitem") int pageitem) throws DataAccessException;

    public Map<String,Object> getSumData(@Param("plantValue") List<String> plantValue,//工厂
                                            @Param("matnrValue") List<String> matnrValue,
                                            @Param("matnrInterval") List<Map<String,String>> matnrInterval,
                                            @Param("lgortValue") List<String> lgortValue,//库存地点
                                            @Param("lgpbeValue") List<String> lgpbeValue,//管库员
                                            @Param("vendorValue") List<String> vendorValue,//供应商
                                            @Param("vendorTypeValue") List<String> vendorTypeValue,//供应商类别
                                            @Param("dateTime") String dateTime,//查询日期
                                            @Param("dayNumStart") String dayNumStart,//起始库存天数
                                            @Param("dayNumEnd") String dayNumEnd,//结束库存天数
                                            @Param("blog") String blog) throws DataAccessException;
}