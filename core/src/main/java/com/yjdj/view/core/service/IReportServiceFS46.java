package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhuxuan on 2016/12/28.
 */
public interface IReportServiceFS46 {
    /**
     * 导出或获取报表
     * @param queryPojo
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws DataAccessException
     */
    public Object getReportJson(QueryPojo queryPojo) throws JsonProcessingException, IOException,DataAccessException;

    /**
     * 导入数据
     * @param filePath
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws DataAccessException
     * @throws InvalidFormatException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
//    public Object importData(String filePath)throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;

    /**
     * 获取所有车型
     * @return
     */
    public List<String> getAllTrianModel(String trainmodel) throws JsonProcessingException;
}
