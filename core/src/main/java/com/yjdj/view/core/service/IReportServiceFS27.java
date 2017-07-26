package com.yjdj.view.core.service;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by wangkai on 2016/11/8.
 */
public interface IReportServiceFS27 {
    /**
     *
     * @param aufnr
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public String getListFs27(String aufnr,String matnr) throws IOException, ParseException;
}
