package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * Created by sunwan on 2017/1/3.
 */
public interface IReportServiceFS50 {
//通用
    public Object selectData_01(QueryPojo queryPojo) throws JsonProcessingException,IOException,DataAccessException;
//国铁服务中心
    public Object selectData_02(QueryPojo queryPojo) throws JsonProcessingException,IOException,DataAccessException;
//质保部
    public Object selectData_03(QueryPojo queryPojo) throws JsonProcessingException,IOException,DataAccessException;
//04表
    public Object selectData_04(QueryPojo queryPojo) throws JsonProcessingException,IOException,DataAccessException;
//导入
    public Object importData(String filePath)throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
//模板导出
    public Object downloadTemplate() throws JsonProcessingException,IOException,DataAccessException;
	
    //查询条件-----事故类别
    public String getAccitype(String name) throws JsonProcessingException, IOException, DataAccessException;
	
    //故障部位
    public String getDefalocation(String name)throws JsonProcessingException, IOException, DataAccessException;
    
    //产品寿命阶段
	public String getProductLife(String name)throws JsonProcessingException, IOException, DataAccessException;
	
	//产品型号
	public String getProductModel(String name)throws JsonProcessingException, IOException, DataAccessException;
	
	//配属局段
	public String getAttaburea(String name)throws JsonProcessingException, IOException, DataAccessException;
	
	//严重度
	public String getSeverity(String name)throws JsonProcessingException, IOException, DataAccessException;
	
	//信息编号
	public String getInfocode(String name,Integer start,Integer limit)throws JsonProcessingException, IOException, DataAccessException;
	
	//产品编号
	public String getProducCode(String name,Integer start,Integer limit)throws JsonProcessingException, IOException, DataAccessException;
	
	//机车编号
	public String getMotorCode(String name,Integer start,Integer limit)throws JsonProcessingException, IOException, DataAccessException;
	
	//供应商
	public String getTvendor(String name,Integer start,Integer limit)throws JsonProcessingException, IOException, DataAccessException;
	
	//报告编号
	public String getReportCode(String name, Integer start, Integer limit)throws JsonProcessingException, IOException, DataAccessException;

	//质量损失类别编号
	public String getLossTypeCode(String name, Integer start, Integer limit)throws JsonProcessingException, IOException, DataAccessException;

}
