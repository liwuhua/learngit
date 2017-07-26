package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IThmMseg04Service;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yjdj.view.core.service.IReportServiceFS04;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yangzhijie on 16/11/01.
 */
@Controller
@RequestMapping("/crrc/reportFS04")
public class ReportControllerFS04{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS01.class);
	@Autowired
	private IReportServiceFS04 iReportServiceFS04;

    @Autowired
    private IThmMseg04Service iThmMseg04Service;

    @Autowired
    private GenericService genericService;

    private String tableName = "THM_MSEG_04_05";

    private static final String FS04 = "crrc/reportPageFS04";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS04() {
        return FS04;
    }

    /**
     * 获取权限字段tplant
     * @param request
     * @return 
     */
    @RequestMapping(value = "/tplant", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getTplant(HttpServletRequest request) {
        List<String> list = (List<String>)request.getSession().getAttribute("FS04-"+ CommonConstant.PLANT);
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
     * 获取fs04 采购订单
     * @param ebeln
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/ebeln", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getEbeln(@RequestParam(value = "id", required = false, defaultValue = "") String ebeln,
                           @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                           @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        try {
            return iThmMseg04Service.getEbeln(ebeln,start,limit);
        } catch (JsonProcessingException e) {
        	logger.error("Json 数据封装异常" +e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 采购组
     * @param request
     * @return
     */
    @RequestMapping(value = "/tpurgroup", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTpurGroupParam(HttpServletRequest request,
                                        @RequestParam(value = "id", required = false, defaultValue = "") String id,
                                        @RequestParam(value = "name", required = false, defaultValue = "") String name){
        List<String> list = (List<String>)request.getSession().getAttribute("FS04-"+ CommonConstant.PUR_GROUP);
        if(CollectionUtils.isNotEmpty(list)){
            try {
                String json = genericService.getAllTpurGroup(tableName, id, name,list);
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
     * 供应商编码
     *
     * @param id
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/tvendor", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTvendorParam(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                                      @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                      @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        try {
            return genericService.getAllTvendor(tableName, id, name,start,limit);
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
            Object resultJson = iReportServiceFS04.getReportJson(queryBean);
            logger.info("FS04 resultJson+++++++++++"+resultJson);
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
    	String ebelnValue = request.getParameter("ebelnValue");//采购订单多值
    	String matnrValue = request.getParameter("matnrValue");//物料多值
    	String vendorValue = request.getParameter("vendorValue");//供应商多值
    	String purGroupValue = request.getParameter("purGroupValue");//采购组多值
    	String startTime = request.getParameter("startTime");//起始时间
    	String endTime = request.getParameter("endTime");//结束时间
    	String timediffValue = request.getParameter("timediffValue");//时间差异多值
    	Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
    	if("".equals(plantValue)){
    		queryBean.setPlantValue(null);
    	}else{
    		queryBean.setPlantValue(Lists.newArrayList(plantValue.split(",")));
    	}
    	if("".equals(ebelnValue)){
    		queryBean.setEbelnValue(null);
    	}else{
    		queryBean.setEbelnValue(Lists.newArrayList(ebelnValue.split(",")));
    	}
    	if("".equals(matnrValue)){
    		queryBean.setMatnrValue(null);
    	}else{
    		queryBean.setMatnrValue(Lists.newArrayList(matnrValue.split(",")));
    	}
    	if("".equals(vendorValue)){
    		queryBean.setVendorValue(null);
    	}else{
    		queryBean.setVendorValue(Lists.newArrayList(vendorValue.split(",")));
    	}
    	if("".equals(purGroupValue)){
    		queryBean.setPurGroupValue(null);
    	}else{
    		queryBean.setPurGroupValue(Lists.newArrayList(purGroupValue.split(",")));
    	}
    	if("".equals(timediffValue)){
    		queryBean.setTimediffValue(null);
    	}else{
    		queryBean.setTimediffValue(Lists.newArrayList(timediffValue.split(",")));
    	}
    	queryBean.setExport(isExport);
    	queryBean.setStartTime(startTime);
    	queryBean.setEndTime(endTime);
    	Object resultJson = null;
    	try {
        	//isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS04.getReportJson(queryBean);
            if(queryBean.isExport()){
            	ExcelExportUtil result = (ExcelExportUtil) resultJson;
            	result.write(request,response, "物料分批次到货统计.xlsx");
        	}
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
    	 return null;
    }

}
