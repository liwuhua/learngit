package com.yjdj.view.core.service;

import java.io.IOException;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

public interface IReportServiceFS55 {

    /**
     * 解析json查询THM_FS55表返回报表json
     * @return
     */
    public Object getReportJson(QueryBean queryBean) throws JsonProcessingException, IOException ,DataAccessException;

}
