package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.shiro.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhuxuan on 2017/2/17.
 */
public interface IReportServiceFS56 {
    /**
     * 查询或导出报表/导出上传模板
     * @param queryBean
     * @param action 0导出模板;1查询;2下载;
     * @return
     * @throws java.io.IOException
     * @throws org.apache.shiro.dao.DataAccessException
     */
    public Object getReportJson(QueryBean queryBean, int action) throws IOException,DataAccessException;

    /**
     * 导入数据
     * @param filePath
     * @param flag 0为上传模板，1为上传数据
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws org.springframework.dao.DataAccessException
     * @throws InvalidFormatException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object importData(String filePath, int flag)throws JsonProcessingException, IOException , org.springframework.dao.DataAccessException,
            InvalidFormatException, InstantiationException, IllegalAccessException;

    /**
     * 用户查询条件，电机型号
     * @return
     * @throws JsonProcessingException
     */
    public List<String> getMotorModel(String motorModel)throws JsonProcessingException;
}
