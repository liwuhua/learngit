package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import org.springframework.dao.DataAccessException;

import java.io.IOException;

/**
 * Created by sunwan on 2017/3/3.
 */
public interface IReportServiceFS57 {
//查询数据
    public Object selectData(QueryBean queryBean) throws JsonProcessingException,IOException,DataAccessException;

    /**
     * 获取所有的客户代码
     * @param tableName
     * @param customer
     * @param txtmd
     * @return
     * @throws JsonProcessingException
     * @throws DataAccessException
     */
    public String getAllTcustomer(String tableName,String customer,String txtmd) throws JsonProcessingException,DataAccessException;
}
