package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryPojo;

public interface IReportServiceFS53 {

    /**
     * 解析json查询THM_FS50表返回报表json
     * @return
     */
    public Object getReportJson(QueryPojo queryPojo) throws JsonProcessingException, IOException ,DataAccessException;
    
    public List<String> getGzlx(String gzlxLike) throws DataAccessException, JsonProcessingException;

}
