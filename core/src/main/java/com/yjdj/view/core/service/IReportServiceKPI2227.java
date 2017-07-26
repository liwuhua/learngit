package com.yjdj.view.core.service;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.QueryPojo2;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelExportUtil2227;

/**销售订单完成统计表及需求统计表
 * Created by liwuhua on 16/11/5.
 */

public interface IReportServiceKPI2227 {
     
	//质量损失类别编号
    public String getLossTypeCode(String name, Integer start, Integer limit)throws JsonProcessingException, IOException, DataAccessException;
	
    //质量问题发生单位
	public String getOccurPlant(String name, Integer start, Integer limit)throws JsonProcessingException, IOException, DataAccessException;
    
	//责任单位
    public String getDutyPlant(String name, Integer start, Integer limit)throws JsonProcessingException, IOException, DataAccessException;
		
		
 	 //厂内  厂外 一年中每个月的损失  及总数
 	 public String  getPlantAccount(QueryBean queryBean )throws JsonProcessingException,DataAccessException;
 	
 	 //得到厂内  厂外 一年中分别每个月的损失的具体占比率
 	 public String  getQualiDetail(QueryBean queryBean)throws JsonProcessingException,DataAccessException;
	
    // 厂内  厂外 各质量损失类别下钻至质量损失原因 
 	public String getCauseByType(QueryBean queryBean)throws JsonProcessingException,DataAccessException;
 	 
 	//质量损失原因下钻至责任单位    场内外
 	public String getDutyunitByCause(QueryBean queryBean)throws JsonProcessingException,DataAccessException;
 	
 	//得到所有的项目类型
 	public String  getProjecttypes( )throws JsonProcessingException,DataAccessException; 
 	
 	//产品故障统计  故障类型统计
 	public String getFaultCount(QueryBean queryBean) throws JsonProcessingException,DataAccessException; 
 	
 	
 	//产品故障统计   对具体型号下钻1层  故障类型  故障数量 
 	public String getLayerByModel(QueryBean queryBean) throws JsonProcessingException,DataAccessException; 
 	
 	//产品故障统计  故障类型点击下钻展示格式   产品型号	故障数量
 	public String getLayerByType(QueryBean queryBean) throws JsonProcessingException,DataAccessException;

 	//导入数据
 	 public Object importData(String filePath)throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;  
 	
	//模板导出
    public Object downloadTemplate() throws JsonProcessingException,IOException,DataAccessException;
   
	//获取营业收入+质量损失 截止时间
    public String  getDeadline() throws JsonProcessingException,IOException,DataAccessException;
    

	public Object getResult(QueryPojo2 querypojo)throws JsonProcessingException,DataAccessException, IOException;

	//导出文件
	public ExcelExportUtil2227 getexportExcel(QueryPojo2 queryBean);

	public String getCode(String tableName, String customer, String txtmd,Integer tablebs) throws JsonProcessingException,DataAccessException;


	
	
}
