package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by zcyj on 2016/10/25.
 */
public interface IReportServiceFS03 {

    /**
     * 解析json查询THM_MSEG_03表返回报表json
     * @param json
     * @return
     */

//    List<THM_FS03> selectDataTrue(List<String> werksValue,List<String> matnrValue,List<String> lifnrValue,List<String> ekgrpValue,String start,String end);
//    List<THM_FS03> selectDataTrue(List<String> werksValue,List<String> matnrValue,List<String> lifnrValue,List<String> ekgrpValue,String start,String end);
//    String selectDataTrue(QueryBean queryBean) throws IOException,ParseException;
    public Object selectDataTrue(QueryBean queryBean) throws JsonProcessingException,IOException,DataAccessException;

}
