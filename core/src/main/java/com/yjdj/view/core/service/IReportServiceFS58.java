package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

/**
 * Created by sunwan on 2017/4/24.
 */
public interface IReportServiceFS58 {
    //查询数据
    public Object selectData(QueryBean queryBean) throws JsonProcessingException,IOException,DataAccessException;
    //查询电机型号
    public String getGroesJson(String id) throws JsonProcessingException,DataAccessException;
    //查询维修级别 
    public String getZlevelJson(String id) throws JsonProcessingException,DataAccessException;
    //查询配件物料编码
    public String getPjwlbmJson(String id, String name, int startpage, int endpage) throws JsonProcessingException,DataAccessException;

    
    
}
