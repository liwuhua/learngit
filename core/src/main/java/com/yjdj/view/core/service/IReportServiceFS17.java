package com.yjdj.view.core.service;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

public interface IReportServiceFS17 {

	/**
	 * create on 2016/11/21
	 * @author yangzhijie
	 * @param queryBean
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
    public Object getReportJson(QueryBean queryBean) throws JsonProcessingException, IOException ,DataAccessException;
    /**
     * 
     * @param filePath
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws DataAccessException
     * @throws InvalidFormatException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object importData(String filePath) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
}
