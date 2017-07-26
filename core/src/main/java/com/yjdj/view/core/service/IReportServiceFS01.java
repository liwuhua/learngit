package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

import java.io.IOException;
import org.springframework.dao.DataAccessException;

public interface IReportServiceFS01 {

    /**
     * 解析json查询THM_MSEG_03表返回报表json
     * @param queryBean
     * @return
     * @throws JsonProcessingException, IOException ,DataAccessException
     */
    public Object getReportJson(QueryBean queryBean) throws JsonProcessingException, IOException ,DataAccessException;


}
