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

import com.yjdj.view.core.service.IReportServiceFS55;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelExportUtil55;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yangzhijie on 17/03/02.
 */
@Controller
@RequestMapping("/crrc/reportFS55")
public class ReportControllerFS55{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS01.class);
	@Autowired
	private IReportServiceFS55 iReportServiceFS55;


    @Autowired
    private GenericService genericService;

    private String tableName = "THM_FS55_01";

    private static final String FS55 = "crrc/reportPageFS55";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS55() {
        return FS55;
    }

    /**
     * 获取权限字段tplant
     * @param request
     * @return 
     */
    @RequestMapping(value = "/tplant", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getTplant(HttpServletRequest request) {
        List<String> list = (List<String>)request.getSession().getAttribute("FS55-"+ CommonConstant.PLANT);
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
     * 获取权限字段salesOrg
     * @param request
     * @return
     */
    @RequestMapping(value = "/salesOrg", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getTplant01(HttpServletRequest request) {
        List<String> list = (List<String>) request.getSession().getAttribute("FS55-"+ CommonConstant.SALESORG);
        if (CollectionUtils.isNotEmpty(list)) {
            try {
            	return genericService.getSalesOrgJson(list);
            }catch (JsonProcessingException e) {
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
     * 客户
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/tcustomer", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTcustomerParam(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                                        @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        try {
            return genericService.getAllTcustomer(tableName, id, name);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 物料多值多区间
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/tmaterial", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTmaterialParam(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                                      @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        try {
            return genericService.getAllTmaterial(tableName, id, name);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
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
            Object resultJson = iReportServiceFS55.getReportJson(queryBean);
            logger.info("FS55 resultJson+++++++++++"+resultJson);
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
    	String vkorgValue = request.getParameter("vkorgValue");//销售组织多值
    	String plantValue = request.getParameter("plantValue");//工厂多值
    	String kunnrValue = request.getParameter("kunnrValue");//客户编码多值
    	String matnrValue = request.getParameter("matnrValue");//物料多值
    	String matnrInterval = request.getParameter("matnrInterval");//物料区间
    	String startTime = request.getParameter("startTime");//起始时间
    	String endTime = request.getParameter("endTime");//结束时间    	
    	Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
    	
    	
    	if("".equals(vkorgValue)){
    		queryBean.setVkorgValue(null);
    	}else{
    		queryBean.setVkorgValue(Lists.newArrayList(vkorgValue.split(",")));
    	}
    	if("".equals(plantValue)){
    		queryBean.setPlantValue(null);
    	}else{
    		queryBean.setPlantValue(Lists.newArrayList(plantValue.split(",")));
    	}
    	if("".equals(kunnrValue)){
    		queryBean.setKunnrValue(null);
    	}else{
    		queryBean.setKunnrValue(Lists.newArrayList(kunnrValue.split(",")));
    	}
    	if("".equals(matnrValue)){
    		queryBean.setMatnrValue(null);
    	}else{
    		queryBean.setMatnrValue(Lists.newArrayList(matnrValue.split(",")));
    	}
    	if ("".equals(matnrInterval)) {
            queryBean.setMatnrInterval(null);
        } else {
            queryBean.setMatnrInterval(Lists.newArrayList(matnrInterval.split(",")));
        }
    	
    	queryBean.setExport(isExport);
    	queryBean.setStartTime(startTime);
    	queryBean.setEndTime(endTime);
    	Object resultJson = null;
    	try {
    		//isExport 为true则是导出数据，为false为查询数据 
    		resultJson = iReportServiceFS55.getReportJson(queryBean);
            if (resultJson != null && queryBean.isExport()){
                ExcelExportUtil55 result = (ExcelExportUtil55) resultJson;
            	result.write(request,response, "检修合同情况统计表.xlsx");
        	}
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
    	 return null;
    }

}
