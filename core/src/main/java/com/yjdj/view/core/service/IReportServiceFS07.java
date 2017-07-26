package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS07;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by zcyj on 2016/10/25.
 */
public interface IReportServiceFS07 {

    /**
     * 解析json查询THM_FS07表返回报表json
     * @param queryBean
     * @param compCodeList
     * @return
     */

//    List<THM_FS07> selectDataTrue(List<String> bukrsValue,List<String> kunnrValue,String dateYearMonthStart,String dateYearMonthEnd,String date1,String date2,String date3,String date4,String date5,String date6,String date7,String date8);
    public Object selectDataTrue(QueryBean queryBean,List<String> compCodeList) throws JsonProcessingException,IOException,DataAccessException,ParseException;

    /**
     * 数据导入
     * @param filePath
     * @param compCodeList 当前登录用户的权限列表
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws DataAccessException
     * @throws InvalidFormatException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object importData(String filePath,List<String> compCodeList) throws JsonProcessingException,InvalidFormatException,IOException, InstantiationException, IllegalAccessException;
}
