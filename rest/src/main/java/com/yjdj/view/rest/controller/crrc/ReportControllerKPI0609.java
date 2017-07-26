package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.IReportServiceKPI0609;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * KPI-采购仓储管理目标 controller
 * Created by liwuhua on 20161226
 */
@Controller
@RequestMapping("/crrc/kpi0609")
public class ReportControllerKPI0609 {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerKPI0609.class);
	
	@Autowired
	private IReportServiceKPI0609 iReportServiceKPI0609;
	
	
	/**
	 * KPI-销售收入指标及完成情况
	 * @return json
	 */
    @RequestMapping(value = "/incomeAndComple", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
    public String incomeAndComple(@RequestParam(value="yearmonth",required=true)String yearmonth) {
    	String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI0609.incomeAndComple(yearmonth);
            logger.info("resultJson+++++++++++" + resultJson);
            return resultJson;
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } 
       
    }
    
    
    /**
	 * KPI-销售收入实际下钻  下钻1层  
	 * @return json
	 */
    @RequestMapping(value = "/saleIncomeFiLayer", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String saleIncomeFiLayer(@RequestParam(value="yearmonth",required=true)String yearmonth) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI0609.saleIncomeFiLayer(yearmonth);
            logger.info("resultJson+++++++++++" + resultJson);
            return resultJson;
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } 
       
    }
    
    
    /**
   	 * KPI-点选某市场板块下钻至客户	
   	 * @return json
   	 */
    @RequestMapping(value = "/getcustoByKdgrp", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getcustoByKdgrp( 
    		@RequestParam(value="yearmonth",required=true)String yearmonth,
 		@RequestParam(value="kdgrp",required=true)String kdgrp,
                @RequestParam(value="flag",required=true)String flag) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI0609.getcustoByKdgrp(yearmonth,kdgrp,flag);
            logger.info("resultJson+++++++++++" + resultJson);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } 

        return resultJson;
    }
    
    

    /**
  	 * KPI-年度合同签订金额下钻 
  	 * @return json
  	 */
    @RequestMapping(value = "/anSignedLayer", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String anSignedLayer(@RequestParam(value="yearmonth",required=true)String yearmonth) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI0609.anSignedLayer(yearmonth);
            logger.info("resultJson+++++++++++" + resultJson);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } 

             return resultJson;
    }
    
    
    /**
  	 * KPI-饼图的数据
  	 * @return json
  	 */  
    @RequestMapping(value = "/getRatePie", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String anSiggetRatePienedLayer(@RequestParam(value="yearmonth",required=true)String yearmonth) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI0609.getRatePie(yearmonth);
            logger.info("resultJson+++++++++++" + resultJson);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } 

             return resultJson;
    }
    
    
    
    /**
	  	 * KPI-饼图钻取获得业务部门金额及比率 
	  	 * param: 年+市场板块
	  	 * @return json
	  	 */ 
    /*@RequestMapping(value = "/getBussiApart", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getBussiApart(@RequestParam(value="year",required=true)String year,
    		                    @RequestParam(value="znum",required=true)String znum) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI0609.getBussiApart(year,znum);
            logger.info("resultJson+++++++++++" + resultJson);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } 

             return resultJson;
    }*/
    
    
    
    /**
  	 * KPI-饼图饼图钻取通过业务部门获得客户金额和占比 
  	 * @return json
  	 */ 
/*    @RequestMapping(value = "/getCustByApart", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getCustByApart(@RequestParam(value="year",required=true)String year,
						    	 @RequestParam(value="znum",required=true)String znum,
						    	 @RequestParam(value="vkbur",required=true)String vkbur) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI0609.getCustByApart(year,znum,vkbur);
            logger.info("resultJson+++++++++++" + resultJson);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } 

             return resultJson;
    }*/
   
}
