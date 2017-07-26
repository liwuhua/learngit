package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.shiro.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhuxuan on 2017/3/29.
 */
public interface IReportServiceFS47 {
    /**
     * 查询数据/导出数据
     * @param queryPojo
     * @return
     * @throws IOException
     * @throws DataAccessException
     */
    public Object getReportJson(QueryPojo queryPojo, int action) throws IOException,DataAccessException;

    /**
     * 导入数据
     * @param filePath
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws org.springframework.dao.DataAccessException
     * @throws InvalidFormatException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object importData(String filePath)throws JsonProcessingException, IOException , org.springframework.dao.DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;

    /* 以下为查询条件，以后换主数据 */
    //模糊查询车型
    public List<String> getListTrainModel(String train_type)throws JsonProcessingException, org.springframework.dao.DataAccessException;
    //模糊查询车号
    public List<String> getAllTrainNum(String train_num)throws JsonProcessingException, org.springframework.dao.DataAccessException;
    //造修厂家
    public List<String> getAllManufacturer(String manufacturer)throws JsonProcessingException, org.springframework.dao.DataAccessException;
    //隶属路局
    public List<String> getAllBureau_subjection(String bureau_subjection)throws JsonProcessingException, org.springframework.dao.DataAccessException;
    //配属段所
    public List<String> getAllPlace_subjection(String place_subjection)throws JsonProcessingException, org.springframework.dao.DataAccessException;
    //车修程类别
    public List<String> getAllRepair_level(String repair_level)throws JsonProcessingException, org.springframework.dao.DataAccessException;
    //产品编号
    public List<String> getAllMotor_serial_num(String motor_serial_num)throws JsonProcessingException, org.springframework.dao.DataAccessException;

}
