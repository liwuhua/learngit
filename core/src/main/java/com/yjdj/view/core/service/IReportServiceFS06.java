package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import org.springframework.dao.DataAccessException;

import java.io.IOException;

/**
 * Created by sunwan on 2016/11/7.
 */
public interface IReportServiceFS06 {
//合同执行情况
    public Object selectData_01(QueryBean queryBean) throws JsonProcessingException,IOException,DataAccessException;
//弹窗
    public String selectData_02(String vbeln,String posnr,int start,int limit) throws JsonProcessingException,IOException,DataAccessException;
}
