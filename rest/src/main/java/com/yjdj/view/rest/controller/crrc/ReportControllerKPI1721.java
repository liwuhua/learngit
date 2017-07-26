package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;

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
import com.yjdj.view.core.service.IReportServiceKPI1721;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * KPI-采购仓储管理目标 controller
 * Created by zhangwenguo on 20161226
 */
@Controller
@RequestMapping("/crrc/kpi1721")
public class ReportControllerKPI1721 {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerKPI1721.class);
	
	@Autowired
	private IReportServiceKPI1721 iReportServiceKPI1721;
	
	/**
	 * KPI-采购到货正点率
	 * @return
	 */
    @RequestMapping(value = "/resultRate", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getOrdersSumRate(HttpServletRequest request,
            @RequestParam(value = "time", required = false, defaultValue = "") String time) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI1721.getOrdersSumRate(time);
            logger.info("resultJson+++++++++++" + resultJson);
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

        return resultJson;
    }
    /**
	 * KPI-采购到货正点率
	 * @return
	 */
    @RequestMapping(value = "/resultEkgrpByRate", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getEkgrpByRate(HttpServletRequest request,
            @RequestParam(value = "label", required = false, defaultValue = "") String label,
            @RequestParam(value = "time", required = false, defaultValue = "") String time) {
        String resultJson = null;
        
        try {
            resultJson = (String) iReportServiceKPI1721.getEkgrpByRate(label,time);
            logger.info("resultJson+++++++++++" + resultJson);
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

        return resultJson;
    }
    
    /**
	 * KPI-采购到货正点率
	 * @return
	 */
    @RequestMapping(value = "/resultLifnrRateAndEkgrp", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getRate(HttpServletRequest request,
            @RequestParam(value = "time", required = false, defaultValue = "") String time) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI1721.getLifnrRateAndEkgrpRate(time);
            logger.info("resultJson+++++++++++" + resultJson);
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

        return resultJson;
    }
    /**
	 * 原材料总金额 和 周转天数
	 * @return
	 */
    @RequestMapping(value = "/resultMaterial", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getMaterial(HttpServletRequest request,
            @RequestParam(value = "time", required = false, defaultValue = "") String time) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI1721.getMaterial(time);
            logger.info("resultJson+++++++++++" + resultJson);
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

        return resultJson;
    }
    
    /**
	 * 产成品总金额 和 周转天数
	 * @return
	 */
    @RequestMapping(value = "/resultProduct", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProduct(HttpServletRequest request,
            @RequestParam(value = "time", required = false, defaultValue = "") String time) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI1721.getProduct(time);
            logger.info("resultJson+++++++++++" + resultJson);
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

        return resultJson;
    }
    
    /**
	 * 原材料评估类下钻
	 * @return
	 */
    @RequestMapping(value = "/resultMaterialGoDown", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getMaterialGoDown(HttpServletRequest request,
            @RequestParam(value = "time", required = false, defaultValue = "") String time,
            @RequestParam(value = "flag", required = false, defaultValue = "") String flag) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI1721.getMaterialGoDown(time,flag);
            logger.info("resultJson+++++++++++" + resultJson);
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

        return resultJson;
    }
    
    /**
	 * 产成品无动态下钻
	 * @return
	 */
    @RequestMapping(value = "/resultProductDynamicGoDown", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProductDynamicGoDown(HttpServletRequest request,
            @RequestParam(value = "time", required = false, defaultValue = "") String time) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI1721.getProductDynamicGoDown(time);
            logger.info("resultJson+++++++++++" + resultJson);
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

        return resultJson;
    }
    
    
}
