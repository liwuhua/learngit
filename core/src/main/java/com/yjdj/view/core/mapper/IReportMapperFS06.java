package com.yjdj.view.core.mapper;

import com.yjdj.view.core.entity.mybeans.THM_LIPS_02;
import com.yjdj.view.core.entity.mybeans.THM_VBAP_02;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by sunwan on 2016/11/7.
 */
@MyBatisRepository
public interface IReportMapperFS06 extends BaseMapper{
    //合同执行情况
    List<THM_VBAP_02> selectData_01(
            @Param("vbelnValue") List<String> vbelnValue,
            @Param("vkorgValue") List<String> vkorgValue,
            @Param("vkburValue") List<String> vkburValue,
            @Param("matnrValue") List<String> matnrValue,
            @Param("matnrInterval") List<Map<String, String>> matnrInterval,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("kunnrValue") List<String> kunnrValue,
            @Param("auartValue") List<String> auartValue,
            @Param("startitem") int startitem,
            @Param("pageitem") int pageitem,
            @Param("flag") int flag
    );
    //弹窗
    List<THM_LIPS_02> selectData_02(
            @Param("CONTNBR") String vbeln,
            @Param("CONTITM") String posnr,
            @Param("start") int start,
            @Param("limit") int limit
    );
}
