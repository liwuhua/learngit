package com.yjdj.view.core.mapper;

import com.yjdj.view.core.entity.mybeans.THM_FS03;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zcyj on 2016/10/21.
 */
@MyBatisRepository
public interface IReportMapperFS03 extends BaseMapper{
    //第一步：统计当期不含税金额
    List<THM_FS03> selectDataTrue_01(
            //List<String> strings, List<String> value,
            @Param("werks") List<String> werksValue,
            @Param("matnr") List<String> matnrValue,
            @Param("lifnr") List<String> lifnrValue,
            @Param("ekgrp") List<String> ekgrpValue,
            @Param("budatStart") String start,
            @Param("budatEnd") String end);

    List<THM_FS03> selectDataTrue_02(
            @Param("werks") List<String> werksValue,
            @Param("matnr") List<String> matnrValue,
            @Param("lifnr") List<String> lifnrValue,
            @Param("ekgrp") List<String> ekgrpValue,
            @Param("budatStart") String start,
            @Param("budatEnd") String end);

    List<THM_FS03> selectDataTrue_03(
            @Param("werks") List<String> werksValue,
            @Param("matnr") List<String> matnrValue,
            @Param("lifnr") List<String> lifnrValue,
            @Param("ekgrp") List<String> ekgrpValue,
            @Param("budatStart") String start,
            @Param("budatEnd") String end);

    List<THM_FS03> selectDataTrue_04(
            @Param("werks") List<String> werksValue,
            @Param("matnr") List<String> matnrValue,
            @Param("lifnr") List<String> lifnrValue,
            @Param("ekgrp") List<String> ekgrpValue,
            @Param("budatStart") String start,
            @Param("budatEnd") String end);

    List<THM_FS03> selectDataTrue_05(
            @Param("werks") List<String> werksValue,
            @Param("matnr") List<String> matnrValue,
            @Param("lifnr") List<String> lifnrValue,
            @Param("ekgrp") List<String> ekgrpValue,
            @Param("budatStart") String start,
            @Param("budatEnd") String end);

    List<THM_FS03> selectDataTrue_06(
            @Param("werks") List<String> werksValue,
            @Param("matnr") List<String> matnrValue,
            @Param("lifnr") List<String> lifnrValue,
            @Param("ekgrp") List<String> ekgrpValue,
            @Param("budatStart") String start,
            @Param("budatEnd") String end);

    List<THM_FS03> selectDataTrue_07(
            @Param("werks") List<String> werksValue,
            @Param("matnr") List<String> matnrValue,
            @Param("lifnr") List<String> lifnrValue,
            @Param("ekgrp") List<String> ekgrpValue,
            @Param("budatStart") String start,
            @Param("budatEnd") String end);
    
    List<THM_FS03> selectData(
            @Param("werks") List<String> werksValue,
            @Param("matnr") List<String> matnrValue,
            @Param("lifnr") List<String> lifnrValue,
            @Param("ekgrp") List<String> ekgrpValue,
            @Param("startitem") int startitem, 
            @Param("pageitem") int pageitem,
            @Param("flag") int flag,
            @Param("tblName") String tblName);
}
