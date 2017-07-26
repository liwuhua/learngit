package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

public interface IReportServiceFS62 {

    /**
     * 解析json查询THM_FS62表返回报表json
     * @return
     */
    public Object getReportJson(QueryBean queryBean) throws JsonProcessingException, IOException ,DataAccessException;
    
    public List<String> getArbpl(String arbplLike) throws DataAccessException, JsonProcessingException;
    
    public List<String> getGroes(String groesLike) throws DataAccessException, JsonProcessingException;
    
    public List<String> getSernr(String sernrLike,int start,int limit) throws DataAccessException, JsonProcessingException;
    
    public List<String> getZlevel(String zlevelLike) throws DataAccessException, JsonProcessingException;

}
