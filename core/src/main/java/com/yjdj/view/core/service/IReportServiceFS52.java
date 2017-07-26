package com.yjdj.view.core.service;

import java.io.IOException;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IReportServiceFS52 {

    /**
     * 解析json查询THM_FS51表返回报表json
     * @return
     */
    public Object getReportJson() throws JsonProcessingException, IOException ,DataAccessException;

}
