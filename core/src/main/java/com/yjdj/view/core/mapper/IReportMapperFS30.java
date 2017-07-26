package com.yjdj.view.core.mapper;

import com.yjdj.view.core.entity.mybeans.THM_FS07;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zcyj on 2016/10/21.
 */
@MyBatisRepository
public interface IReportMapperFS30 extends BaseMapper{

    List<THM_FS07> selectDataTrue_07(
            //List<String> strings, List<String> value,
            @Param("BUKRS") List<String> bukrsValue,
            @Param("KUNNR") List<String> kunnrValue,
            @Param("ZKUNNR") List<String> zkunnrValue,
            @Param("dateYearMonthStart") String dateYearMonthStart,
            @Param("dateYearMonthEnd") String dateYearMonthEnd,
            @Param("date1") String date1,
            @Param("date2") String date2,
            @Param("date3") String date3,
            @Param("date4") String date4,
            @Param("date5") String date5,
            @Param("date6") String date6,
            @Param("date7") String date7,
            @Param("date8") String date8,
            @Param("date9") String date9,
            @Param("date10") String date10,
            @Param("date11") String date11,
            @Param("date12") String date12,
            @Param("date13") String date13,
            @Param("startitem") Integer startitem,
            @Param("pageitem") Integer pageitem,
            @Param("flag") int flag
    );

    public void saveOrUpdateData(HashMap<String,String> map) throws DataAccessException;

}
