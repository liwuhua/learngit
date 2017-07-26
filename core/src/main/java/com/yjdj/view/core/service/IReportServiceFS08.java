package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by wangkai on 16/10/26.
 */
public interface IReportServiceFS08 {
    /**
     * 根据时间区间渲染fs08
     * @param queryBean
     * @return
     */
    public String getListFs08(QueryBean queryBean) throws IOException, ParseException;
}
