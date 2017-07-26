package com.yjdj.view.core.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 2017/02/10.
 */
@MyBatisRepository
public interface IReportMapperFS39 {
    //产成品
    public List<String> getGroes(@Param("groes") String groes) throws DataAccessException;

    //工序名称
    public List<String> getLtxa1(@Param("ltxa1") String ltxa1) throws DataAccessException;

    //销售订单号
    public List<String> getKdauf(@Param("kdauf") String kdauf) throws DataAccessException;

    //销售订单行号
    public List<String> getKdpos(@Param("kdpos") String kdpos) throws DataAccessException;

    //工作中心
    public List<Map<String,Object>> getArbpl(@Param("arbpl") String arbpl,
                                             @Param("ktext") String ktext) throws DataAccessException;

    //需求单位
    public List<String> getXqdw(@Param("xqdw") String xqdw) throws DataAccessException;

    //生产订单类型
    public List<String> getAuart(@Param("auart") String auart) throws DataAccessException;

    public List<Map<String, Object>> getDateFS39(
            @Param("groesValue") List<String> groesValue,   //产成品
            @Param("ltxa1Value") List<String> ltxa1Value,   //工序名称
            @Param("matnrValue") List<String> matnrValue,   //物料
            @Param("matnrInterval") List<String> matnrInterval,
            @Param("kdaufValue") List<String> kdaufValue,   //销售订单
            @Param("kdposValue") List<String> kdposValue,   //行项目
            @Param("dwerkValue") List<String> dwerkValue,
            @Param("arbplValue") List<String> arbplValue,   //工作中心
            @Param("xqdwValue") List<String> xqdwValue,
            @Param("auartValue") List<String> auartValue,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    ) throws DataAccessException;

    public List<Map<String, Object>> getDateFS3902(
            @Param("kdfsValue") List<String> kdfsValue,
            @Param("ltxa1Value") List<String> ltxa1Value,   //工序名称
            @Param("matnrValue") List<String> matnrValue,   //物料
            @Param("matnrInterval") List<String> matnrInterval,
            @Param("kdaufValue") List<String> kdaufValue,   //销售订单
            @Param("kdposValue") List<String> kdposValue,   //行项目
            @Param("dwerkValue") List<String> dwerkValue,
            @Param("arbplValue") List<String> arbplValue,   //工作中心
            @Param("xqdwValue") List<String> xqdwValue,
            @Param("auartValue") List<String> auartValue,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    ) throws DataAccessException;

    public List<String> getKdfs(
            @Param("groesValue") List<String> groesValue,   //产成品
            @Param("ltxa1Value") List<String> ltxa1Value,   //工序名称
            @Param("matnrValue") List<String> matnrValue,   //物料
            @Param("matnrInterval") List<String> matnrInterval,
            @Param("dwerkValue") List<String> dwerkValue,
            @Param("arbplValue") List<String> arbplValue,   //工作中心
            @Param("xqdwValue") List<String> xqdwValue,
            @Param("auartValue") List<String> auartValue
    ) throws DataAccessException;

}