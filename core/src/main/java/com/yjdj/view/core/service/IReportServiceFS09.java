package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

/**
 * Created by wangkai on 2016/11/8.
 */
public interface IReportServiceFS09 {
    /**
     *
     * @param queryBean
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public Object getListFs09(QueryBean queryBean, List<String> compCodeList) throws IOException, ParseException;

	public Object importData(String filePath, List<String> compCodeList)
			throws JsonProcessingException, IOException, DataAccessException,
			InvalidFormatException, InstantiationException,
			IllegalAccessException;
}
