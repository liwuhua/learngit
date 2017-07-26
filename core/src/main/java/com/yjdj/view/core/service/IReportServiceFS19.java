package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * FS19 货币情况表
 * Created by zhuxuan on 2016/11/11.
 */
public interface IReportServiceFS19 {
    /**
     * 获取最终报表或导出
     * @param queryBean
     * @return
     */
    public Object getReportJson(QueryBean queryBean, List<String> compCodeCheck) throws JsonProcessingException, IOException,DataAccessException;

    /**
     * 获取所有公司代码（剔除不显示的公司代码）
     * @return
     */
//    public String getCompCode(String comp_code, String txtmd) throws JsonProcessingException, IOException ,DataAccessException;

    /**
     * 用户导入
     * @param filePath
     * @param compCodeCheck
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws DataAccessException
     * @throws InvalidFormatException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object importData(String filePath, List<String>compCodeCheck) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
}
