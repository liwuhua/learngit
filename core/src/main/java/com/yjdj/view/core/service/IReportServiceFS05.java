package com.yjdj.view.core.service;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

/**
 * Created by zhangwenguo on 2016/11/1.
 */
public interface IReportServiceFS05 {
    /**
     * 查询THM_FS05表返回报表json
     * @param queryBean
     * @return
     */
    public Object getReportJson(QueryBean queryBean, List<String> compCodeCheck) throws JsonProcessingException, IOException ,DataAccessException;

    public Object importData(String filePath, List<String> compCodeCheck) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
}
