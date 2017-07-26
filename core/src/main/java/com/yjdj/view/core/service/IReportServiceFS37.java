package com.yjdj.view.core.service;
import java.io.IOException;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by zhangwenguo on 2016/11/1.
 */
public interface IReportServiceFS37 {
    /**
     * 查询THM_FS05表返回报表json
     * @param queryBean
     * @return
     */
    public Object getReportJson(String matnr, String werks) throws JsonProcessingException, IOException ,DataAccessException;

}
