package com.yjdj.view.core.service;

import com.yjdj.view.core.entity.mybeans.QueryBean;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by wangkai on 2016/11/8.
 */
public interface IReportServiceFS25 {
    /**
     *
     * @param aufnr
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public String getListFs25(String aufnr) throws IOException, ParseException;
}
