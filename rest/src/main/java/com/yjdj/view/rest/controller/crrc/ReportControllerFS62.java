package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yjdj.view.core.service.IReportServiceFS62;
import com.yjdj.view.core.util.ExcelExportUtil55;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yangzhijie on 17/03/08
 */
@Controller
@RequestMapping("/crrc/reportFS62")
public class ReportControllerFS62{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS01.class);
	@Autowired
	private IReportServiceFS62 iReportServiceFS62;


    @Autowired
    private GenericService genericService;

    private String tableName = "THM_FS62";

    private static final String FS62 = "crrc/reportPageFS62";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS62() {
        return FS62;
    }

    /**
     * 获取权限字段tplant
     * @param request
     * @return 
     */
    @RequestMapping(value = "/tplant", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getTplant(HttpServletRequest request) {
        List<String> list = (List<String>)request.getSession().getAttribute("FS62-"+ CommonConstant.PLANT);
        if(CollectionUtils.isNotEmpty(list)){
            try {
                String json = genericService.getTplantJson(list);
                return json;
            } catch (JsonProcessingException e) {
                logger.error("Json 数据封装异常" + e.getMessage());
                return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
            } catch (DataAccessException e) {
                logger.error("数据查询异常" + e.getMessage());
                return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
            }
        }
        return AjaxObject.newError("没有分配权限").setCallbackType("").toString();
    }

    /**
     * 检修地点
     */
    @RequestMapping(value = "/arbpl", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getArbpl(String id) {
    	
    		List<String> result = null;
			try {
				result = iReportServiceFS62.getArbpl(id);
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("FS62 Arbpl+++++++++++"+result);
            return result;
        
    }
    
    /**
     * 电机型号
     */
    @RequestMapping(value = "/groes", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getGroes(String id) {
    	
    		List<String> result = null;
			try {
				result = iReportServiceFS62.getGroes(id);
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("FS62 Groes+++++++++++"+result);
            return result;
        
    }

    /**
     * 电机编号
     */
    @RequestMapping(value = "/sernr", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getSernr(String id,int start,int limit) {
    	
    		List<String> result = null;
			try {
				result = iReportServiceFS62.getSernr(id,start,limit);
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("FS62 Sernr+++++++++++"+result);
            return result;
        
    }
    
    /**
     * 维修级别
     */
    @RequestMapping(value = "/zlevel", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getZlevel(String id) {
    	
    		List<String> result = null;
			try {
				result = iReportServiceFS62.getZlevel(id);
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("FS62 Zlevel+++++++++++"+result);
            return result;
        
    }
    

    /**
     * 根据查询条件返回表单json
     * @param queryBean
     * @return
     */
	/*
	 * pas:采购入库金额统计简称(Purchase amount statistics) 
	 * Purchase amount statistics
	 */
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean){
        try {
            Object resultJson = iReportServiceFS62.getReportJson(queryBean);
            logger.info("FS62 resultJson+++++++++++"+resultJson);
            return (String)resultJson;
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
    }
    
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request,HttpServletResponse response){
    	QueryBean queryBean = new QueryBean();
    	
		String plantValue = request.getParameter("plantValue");//工厂多值
    	String arbplValue = request.getParameter("arbplValue");//检修地点多值
    	String groesValue = request.getParameter("groesValue");//电机型号多值
    	String sernrValue = request.getParameter("sernrValue");//电机编号多值
    	String zlevelValue = request.getParameter("zlevelValue");//维修级别多值
    	String startTime = request.getParameter("startTime");//起始时间
    	String endTime = request.getParameter("endTime");//结束时间    	
    	Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
    	
    	
    	if("".equals(plantValue)){
    		queryBean.setPlantValue(null);
    	}else{
    		queryBean.setPlantValue(Lists.newArrayList(plantValue.split(",")));
    	}
    	if("".equals(arbplValue)){
    		queryBean.setArbplValue(null);
    	}else{
    		queryBean.setArbplValue(Lists.newArrayList(arbplValue.split(",")));
    	}
    	if("".equals(groesValue)){
    		queryBean.setGroesValue(null);
    	}else{
    		queryBean.setGroesValue(Lists.newArrayList(groesValue.split(",")));
    	}
    	if("".equals(sernrValue)){
    		queryBean.setSernrValue(null);
    	}else{
    		queryBean.setSernrValue(Lists.newArrayList(sernrValue.split(",")));
    	}
    	if ("".equals(zlevelValue)) {
            queryBean.setZlevelValue(null);
        } else {
            queryBean.setZlevelValue(Lists.newArrayList(zlevelValue.split(",")));
        }
    	
    	queryBean.setExport(isExport);
    	queryBean.setStartTime(startTime);
    	queryBean.setEndTime(endTime);
    	Object resultJson = null;
    	try {
    		//isExport 为true则是导出数据，为false为查询数据 
    		resultJson = iReportServiceFS62.getReportJson(queryBean);
            if (resultJson != null && queryBean.isExport()){
                ExcelExportUtil55 result = (ExcelExportUtil55) resultJson;
            	result.write(request,response, "检修电机基础数据导出表.xlsx");
        	}
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
    	 return null;
    }

}
