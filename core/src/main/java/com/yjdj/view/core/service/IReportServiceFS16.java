package com.yjdj.view.core.service;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

public interface IReportServiceFS16 {

	/**
	 * create on 2016/11/09
	 * @author yangzhijie
     * @param queryBean
     * @return 
     * @throws com.fasterxml.jackson.core.JsonProcessingException
	 *
	 */
    public Object getReportJson(QueryBean queryBean) throws JsonProcessingException, IOException ,DataAccessException;

    public Object importData(String filePath) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
}
