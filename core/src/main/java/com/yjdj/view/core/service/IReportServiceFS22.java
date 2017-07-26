package com.yjdj.view.core.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.CarSalesCount;
import com.yjdj.view.core.entity.mybeans.MdmSecondCost;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS22_01;
import com.yjdj.view.core.entity.mybeans.THM_MSEG_06;
import com.yjdj.view.core.entity.mybeans.THM_VBAP_01;
import com.yjdj.view.core.util.ExcelExportUtil22;
import com.yjdj.view.core.util.ExcelExportUtil24;

/**研发费用明细报表
 * Created by liuchengli on 16/11/27.
 */

public interface IReportServiceFS22 {
     /**
      * 
      * @param queryBean
      * @return
      * @throws IOException
      * @throws ParseException
      * 查询一级明细返回json字符串
      */
     public String getListFs22(QueryBean queryBean) throws IOException, ParseException;
     /**
      * 
      * @param queryBean
      * @return
      * @throws IOException
      * @throws ParseException
      * 查询一级类型返回对象List
      */
     public List<THM_FS22_01> getListFsFirstDetail(QueryBean queryBean) throws IOException,IllegalAccessException,ParseException; 
     
     /**
      * 
      * @param queryBean
      * @return
      * @throws IOException
      * @throws ParseException
      * 查询二级明细中的后面的部分，不包含报表前面的总计项
      * 
      */
     public String getListFsDetail22(QueryBean queryBean) throws IOException, ParseException;
     /**
      * 
      * @param queryBeanList
      * @return
      * @throws IOException
      * @throws ParseException
      * 组装二级查询结果，返回json字符串
      */
     public String getListFsSecondDetail22(QueryBean queryBean) throws IOException, ParseException;
     /**
      * 
      * @return
      * @throws IOException
      * @throws ParseException
      * 二级明细字典表查询
      */
     public List<MdmSecondCost> getListFsSecondCode();
     
     /**
      * 
      * @param flag 导出标志 1，一级明细；2，二级明细
      * @return 
      */
     public List<List<Object>> exportFS22(int flag,QueryBean queryBean);
     
     /**
      * 查询内部订单号
      * @param aufnr
      * @return
      */
     public String  getInnerOrder(String aufnr);
     
     /**
      * 数据导出
      * @param queryBean
      * @param flag
      * @return
      */
     public ExcelExportUtil22 exportExcel(QueryBean queryBean, int flag);
     
     /**
      * 二级类型数据导出
      * @param queryBean
      * @return
      */
     public List<List<Object>> getListFsSecondDetailExport(QueryBean queryBean) throws IOException, ParseException;
     
     /**
      * 数据导入
      * @param filePath
      * @param compCodeList 当前登录用户的权限列表
      * @return
      * @throws JsonProcessingException
      * @throws IOException
      * @throws DataAccessException
      * @throws InvalidFormatException
      * @throws InstantiationException
      * @throws IllegalAccessException
      */
     public Object importData(String filePath,List<String> compCodeList) throws JsonProcessingException,InvalidFormatException,IOException, InstantiationException, IllegalAccessException;
     
     
     /**
      * 计算一级合计
      * @param list
      * @return
      */
     public  Object  sumFirstAll(List<THM_FS22_01> list,int flag);
     /**
      * 计算二级合计
      * @param list
      * @return
      */
     public Object sumSecondAll(List<List<Object>> list,int flag);
     
     /**
      * 
      * @param queryBean
      * @param flag
      * @return
      */
     public String returnSum(QueryBean queryBean,int flag);
     
     
     /**
      * 编制单位
      * @param compValues
      * @return
      */
     public String getComName(List<String> compValues);
     
     
}
