package com.yjdj.view.core.service;

import java.io.IOException;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

public interface IReportServiceFS04 {

    /**
     * 解析json查询THM_MSEG_04_05表返回报表json
     * @param queryBean
     * @return
     */
    public Object getReportJson(QueryBean queryBean) throws JsonProcessingException, IOException ,DataAccessException;

}
