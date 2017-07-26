package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjdj.view.core.constant.CommonConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS02;
import com.yjdj.view.core.service.IThmVbap01Service;
import com.yjdj.view.core.service.impl.ReportServiceImpFS02;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by liwuhua on 2016年10月21日.
 */
@Controller
@RequestMapping("/crrc/reportFS02")
public class ReportControllerFS02 {

    @Autowired
    private IReportServiceFS02 ireportservicefs02;

    @Autowired
    private IThmVbap01Service iThmVbap01Service;

    @Autowired
    private GenericService genericService;

    private final Logger log = Logger.getLogger(ReportServiceImpFS02.class);
    private String tableName = "THM_VBAP_01";

    private static final String FS0201 = "crrc/reportPageFS0201";
    private static final String FS0202 = "crrc/reportPageFS0202";

    @RequestMapping(value = "/01", method = RequestMethod.GET)
    public String reportFS0201() {
        return FS0201;
    }

    @RequestMapping(value = "/02", method = RequestMethod.GET)
    public String reportFS0202() {
        return FS0202;
    }

    /**
     * 订单完成统计表获取权限字段salesOrg
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/01/salesOrg", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getTplant01(HttpServletRequest request) {
        List<String> list = (List<String>) request.getSession().getAttribute("FS0201-"+ CommonConstant.SALESORG);
        if (CollectionUtils.isNotEmpty(list)) {
            try {
            	return genericService.getSalesOrgJson(list);
            }catch (JsonProcessingException e) {
                log.error("Json 数据封装异常" + e.getMessage());
                return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
            } catch (DataAccessException e) {
                log.error("数据查询异常" + e.getMessage());
                return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
            }
        }
        return AjaxObject.newError("没有分配权限").setCallbackType("").toString();
    }

    /**
     * 订单需求统计表获取权限字段salesOrg
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/02/salesOrg", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getTplant02(HttpServletRequest request) {
        List<String> list = (List<String>) request.getSession().getAttribute("FS0202-"+CommonConstant.SALESORG);
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                String json = genericService.getSalesOrgJson(list);
                return json;
            }catch (JsonProcessingException e) {
                log.error("Json 数据封装异常" + e.getMessage());
                return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
            } catch (DataAccessException e) {
                log.error("数据查询异常" + e.getMessage());
                return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
            }
        }
        return AjaxObject.newError("没有分配权限").setCallbackType("").toString();
    }

    /**
     * 获取fs02 销售订单
     *
     * @param vbeln
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/vbeln", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getVbeln(
            @RequestParam(value = "id", required = false, defaultValue = "") String vbeln,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "15") int limit) {
        try {
            return iThmVbap01Service.getVbeln(tableName, vbeln, start, limit);
        }catch (JsonProcessingException e) {
            log.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            log.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 01模糊查询销售部门
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/01/tsalesoff",  method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTsalesOff01(HttpServletRequest request) {
        List<String> list = (List<String>) request.getSession().getAttribute("FS0201-"+CommonConstant.SALES_OFF);
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                String json = genericService.getSalesOffJson(list);
                return json;
            }catch (JsonProcessingException e) {
                log.error("Json 数据封装异常" + e.getMessage());
                return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
            } catch (DataAccessException e) {
                log.error("数据查询异常" + e.getMessage());
                return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
            }
        }
        return AjaxObject.newError("没有分配权限").setCallbackType("").toString();
    }

    /**
     * 02模糊查询销售部门
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/02/tsalesoff",  method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTsalesOff02(HttpServletRequest request) {
        List<String> list = (List<String>) request.getSession().getAttribute("FS0202-"+CommonConstant.SALES_OFF);
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                String json = genericService.getSalesOffJson(list);
                return json;
            }catch (JsonProcessingException e) {
                log.error("Json 数据封装异常" + e.getMessage());
                return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
            } catch (DataAccessException e) {
                log.error("数据查询异常" + e.getMessage());
                return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
            }
        }
        return AjaxObject.newError("没有分配权限").setCallbackType("").toString();
    }

    /**
     * 获取fs02表一结果集
     *
     * @param queryBean
     * @return     
     */																		
    @RequestMapping(value = "/01/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport1(@RequestBody QueryBean queryBean,HttpServletRequest request) {
        try {
        	List<String> salesorgList = (List<String>) request.getSession().getAttribute("FS0201-"+ CommonConstant.SALESORG);
        	String resultJson = (String) ireportservicefs02.getListFs02Tb1(queryBean,salesorgList);
            return resultJson;
        } catch (JsonProcessingException e) {
            log.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            log.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 获取fs02表二结果集
     *
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/02/result", method = RequestMethod.POST,produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean,HttpServletRequest request){
        try {
        	List<String> salesorgList = (List<String>) request.getSession().getAttribute("FS0202-"+ CommonConstant.SALESORG);
        	String resultJson = (String) ireportservicefs02.getListFs02Tb2(queryBean,salesorgList);
            return resultJson;
        }catch (JsonProcessingException e) {
            log.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            log.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 导出销售订单完成统计表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/01/downloadFile", method = RequestMethod.POST)
    public void downloadFile1(HttpServletRequest request, HttpServletResponse response) {

        QueryBean queryBean = new QueryBean();
        //获取权限内的销售组织
        List<String> salesorgList = (List<String>) request.getSession().getAttribute("FS0201-"+ CommonConstant.SALESORG);
       
        if(!("".equals(request.getParameter("vkorgValue")))){
        	queryBean.setVkorgValue(Lists.newArrayList(request.getParameter("vkorgValue").split(",")));
        }
        
        if(!("".equals(request.getParameter("vkburValue")))){
        	 queryBean.setVkburValue(Lists.newArrayList(request.getParameter("vkburValue").split(",")));
        }
        
        if(!("".equals(request.getParameter("vbelnValue")))){
        	 queryBean.setVbelnValue(Lists.newArrayList(request.getParameter("vbelnValue").split(",")));
        }
        
        if(!("".equals(request.getParameter("matnrValue")))){
        	queryBean.setMatnrValue(Lists.newArrayList(request.getParameter("matnrValue").split(",")));
        }
        if(!("".equals(request.getParameter("matnrInterval")))){
        	queryBean.setMatnrInterval(Lists.newArrayList(request.getParameter("matnrInterval").split(",")));
        }
       
        //单值
        queryBean.setStartTime(request.getParameter("startTime"));
        queryBean.setEndTime(request.getParameter("endTime"));
        queryBean.setProductType(request.getParameter("productType"));
        queryBean.setStartitem(Integer.parseInt(request.getParameter("startitem")));
        queryBean.setPageitem(Integer.parseInt(request.getParameter("pageitem")));
        queryBean.setExport(Boolean.valueOf(request.getParameter("isExport")));

        try {
            ExcelExportUtil ee = ireportservicefs02.getexportExcel1(queryBean,salesorgList);
            ee.write(request, response, "销售订单完成统计表.xlsx");
        }catch (JsonProcessingException e) {
            log.error("Json 数据封装异常" + e.getMessage());
        } catch (DataAccessException e) {
            log.error("数据查询异常" + e.getMessage());
        }catch (IOException e) {
            log.error("IO 操作异常" + e.getMessage());
        }
    }

    /**
     * 导出销售订单需求统计表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/02/downloadFile", method = RequestMethod.POST)
    public void downloadFile2(HttpServletRequest request, HttpServletResponse response) {
    	
    	List<String> salesorgList = (List<String>) request.getSession().getAttribute("FS0202-"+ CommonConstant.SALESORG);
    	//前端接收的参数  list<> 多值
        QueryBean queryBean = new QueryBean();
        String vkorgValue = request.getParameter("vkorgValue");
        String vkburValue = request.getParameter("vkburValue");
        String vbelnValue = request.getParameter("vbelnValue");
        String matnrValue = request.getParameter("matnrValue");
        String matnrInterval = request.getParameter("matnrInterval");
        
        if(!("".equals(request.getParameter("vkorgValue")))){
        	 queryBean.setVkorgValue(Lists.newArrayList(request.getParameter("vkorgValue").split(",")));
        }
        
       if(!("".equals(request.getParameter("vkburValue")))){
	        queryBean.setVkburValue(Lists.newArrayList(request.getParameter("vkburValue").split(",")));
        }
   
       if(!("".equals(request.getParameter("vbelnValue")))){
    	   queryBean.setVbelnValue(Lists.newArrayList(request.getParameter("vbelnValue").split(",")));
       }
   
       if(!("".equals(request.getParameter("matnrValue")))){
    	   queryBean.setMatnrValue(Lists.newArrayList(request.getParameter("matnrValue").split(",")));
       }
   
       if(!("".equals(request.getParameter("matnrInterval")))){
    	   queryBean.setMatnrInterval(Lists.newArrayList(request.getParameter("matnrInterval").split(",")));
       }
       
        //单值
        queryBean.setStartTimeTwo(request.getParameter("startTimeTwo"));
        queryBean.setEndTimeTwo(request.getParameter("endTimeTwo"));
        queryBean.setProductType(request.getParameter("productType"));
        queryBean.setExport(Boolean.valueOf(request.getParameter("isExport")));

        try {
            ExcelExportUtil ee = ireportservicefs02.getexportExcel2(queryBean,salesorgList);
            ee.write(request, response, "销售订单需求统计表.xlsx");
        }catch (JsonProcessingException e) {
            log.error("Json 数据封装异常" + e.getMessage());
        } catch (DataAccessException e) {
            log.error("数据查询异常" + e.getMessage());
        }catch (IOException e) {
            log.error("IO 操作异常" + e.getMessage());
        }
    }

}
