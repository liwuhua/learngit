package com.yjdj.view.core.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 2017/02/22.
 */
@MyBatisRepository
public interface IReportMapperFS14 {

    /**
     * 获取特殊库存标识
     * @param sttyp
     * @return
     */
    public List<String> getSttyp(@Param("sttyp") String sttyp) throws DataAccessException;
    /**
     * 获取特殊库存标识
     * @param sobkz
     * @return
     */
    public List<String> getSobkz(@Param("sobkz") String sobkz) throws DataAccessException;
    /**
     * 获取管库员
     * @param lgpbe
     * @return
     */
    public List<String> getLgpbe(@Param("lgpbe") String lgpbe) throws DataAccessException;

    public List<Map<String,Object>> getDataFir(@Param("plantValue") List<String> plantValue,//工厂
                                            @Param("matnrValue") List<String> matnrValue,
                                            @Param("matnrInterval") List<Map<String, String>> matnrInterval,
                                            @Param("lgortValue") List<String> lgortValue,//库存地点
                                            @Param("lgpbeValue") List<String> lgpbeValue,//管库员
                                            @Param("sobkzValue") List<String> sobkzValue,//特殊库存标识
                                            @Param("sttypValue") List<String> sttypValue,//库存状态
                                            @Param("matlGroupValue") List<String> matlGroupValue,//物料组
                                            @Param("valClassValue") List<String> valClassValue,//评估类
                                            @Param("dateTime") String dateTime,//查询日期
                                            @Param("startTime") String startTime,//无动态日期
                                            @Param("blog") String blog,
                                            @Param("startitem") int startitem,
                                            @Param("pageitem") int pageitem) throws DataAccessException;

    List<Map<String,Object>> getDataSec(@Param("plantValue") List<String> plantValue,//工厂
                                        @Param("matnrValue") List<String> matnrValue,
                                        @Param("matnrInterval") List<Map<String, String>> matnrInterval,
                                        @Param("lgortValue") List<String> lgortValue,//库存地点
                                        @Param("lgpbeValue") List<String> lgpbeValue,//管库员
                                        @Param("sobkzValue") List<String> sobkzValue,//特殊库存标识
                                        @Param("sttypValue") List<String> sttypValue,//库存状态
                                        @Param("matlGroupValue") List<String> matlGroupValue,//物料组
                                        @Param("valClassValue") List<String> valClassValue,//评估类
                                        @Param("dateTime") String dateTime,//查询日期
                                        @Param("startTime") String startTime,//无动态日期
                                        @Param("blog") String blog,
                                        @Param("startitem") int startitem,
                                        @Param("pageitem") int pageitem) throws DataAccessException;

    public Map<String,Object> getSumDataFir(@Param("plantValue") List<String> plantValue,//工厂
                                            @Param("matnrValue") List<String> matnrValue,
                                            @Param("matnrInterval") List<Map<String, String>> matnrInterval,
                                            @Param("lgortValue") List<String> lgortValue,//库存地点
                                            @Param("lgpbeValue") List<String> lgpbeValue,//管库员
                                            @Param("sobkzValue") List<String> sobkzValue,//特殊库存标识
                                            @Param("sttypValue") List<String> sttypValue,//库存状态
                                            @Param("matlGroupValue") List<String> matlGroupValue,//物料组
                                            @Param("valClassValue") List<String> valClassValue,//评估类
                                            @Param("dateTime") String dateTime,//查询日期
                                            @Param("startTime") String startTime,//无动态日期
                                            @Param("blog") String blog) throws DataAccessException;

    public Map<String,Object> getSumDataSec(@Param("plantValue") List<String> plantValue,//工厂
                                            @Param("matnrValue") List<String> matnrValue,
                                            @Param("matnrInterval") List<Map<String, String>> matnrInterval,
                                            @Param("lgortValue") List<String> lgortValue,//库存地点
                                            @Param("lgpbeValue") List<String> lgpbeValue,//管库员
                                            @Param("sobkzValue") List<String> sobkzValue,//特殊库存标识
                                            @Param("sttypValue") List<String> sttypValue,//库存状态
                                            @Param("matlGroupValue") List<String> matlGroupValue,//物料组
                                            @Param("valClassValue") List<String> valClassValue,//评估类
                                            @Param("dateTime") String dateTime,//查询日期
                                            @Param("startTime") String startTime,//无动态日期
                                            @Param("blog") String blog) throws DataAccessException;


}