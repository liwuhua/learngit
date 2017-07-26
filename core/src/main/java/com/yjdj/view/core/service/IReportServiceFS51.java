package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryPojo;

public interface IReportServiceFS51 {

    /**
     * 解析json查询THM_FS51表返回报表json
     * @param queryPojo
     * @return
     */
    public Object getReportJson(QueryPojo queryPojo) throws JsonProcessingException, IOException ,DataAccessException;
    
    public List<String> getCpxh(String cpxhLike) throws DataAccessException, JsonProcessingException;

}
