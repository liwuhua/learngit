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
import com.yjdj.view.core.service.IReportServiceKPI10;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * KPI-采购仓储管理目标 controller
 * Created by liwuhua on 2016年11月18日.
 */
@Controller
@RequestMapping("/crrc/kpi10")
public class ReportControllerKPI10 {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerKPI10.class);
	
	@Autowired
	private IReportServiceKPI10 iReportServiceKPI10;
	
	
	/**
	 * KPI-当月正点交付率 
	 * @return
	 */
    @RequestMapping(value = "/getPunDeliRate", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
    public String getPunDeliRate(@RequestParam(value="dateYearMonth",required=true)String dateYearMonth) {
    	String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI10.punDeliRate(dateYearMonth);
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
	 * KPI-正点率第一层下钻
	 * @return
	 */
    @RequestMapping(value = "/getFirstLayer", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getFirstLayer(@RequestParam(value="dateYearMonth",required=true)String dateYearMonth) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI10.firstLayer(dateYearMonth);
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
	 * KPI-正点率第二层下钻
	 * @return
	 */
    @RequestMapping(value = "/getSecondLayer", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getSecondLayer( 
    		@RequestParam(value="vbtypcode",required=true)String vbtypcode, 
 		    @RequestParam(value="dateYearMonth",required=true)String dateYearMonth
 		    ) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI10.secondLayer(vbtypcode,dateYearMonth);
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
	 * KPI-正点率第三层下钻 
	 * @return
	 */
    @RequestMapping(value = "/getThirdLayer", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getThirdLayer(  
    		   @RequestParam(value="vbtypcode",required=true)String vbtypcode,
    		   @RequestParam(value="matnr",required=true)String matnr,
    		   @RequestParam(value="dateYearMonth",required=true)String dateYearMonth
     		   ) {
        String resultJson = null;  
        try {
            resultJson = (String) iReportServiceKPI10.thirdLayer(vbtypcode,matnr,dateYearMonth);
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
   
}
