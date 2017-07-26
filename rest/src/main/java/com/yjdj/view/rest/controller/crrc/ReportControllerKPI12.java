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
import com.yjdj.view.core.service.IReportServiceKPI12;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * KPI-生产管理目标 controller
 * Created by  on 20161226
 */
@Controller
@RequestMapping("/crrc/kpi12")
public class ReportControllerKPI12 {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerKPI12.class);
	
	@Autowired
	private IReportServiceKPI12 iReportServiceKPI12;
	
	/**
	 * 产品正点交付率，产品完成总数，产品未完成总数
	 * @return
	 */
    @RequestMapping(value = "/resultRateAndCount", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProOTDeliverRateAndProCompCount(HttpServletRequest request,
            @RequestParam(value = "yearMonth", required = false, defaultValue = "") String yearMonth) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI12.getProOTDeliverRateAndProCompCount(yearMonth);
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
	 * 产品正点交付钻取
	 * @return
	 */
    @RequestMapping(value = "/resultProOTDeliverGoDown", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProOTDeliverGoDown(HttpServletRequest request,
            @RequestParam(value = "yearMonth", required = false, defaultValue = "") String yearMonth) {
        String resultJson = null;
        //String yearMonth, String bukrs
        try {
            resultJson = (String) iReportServiceKPI12.getProOTDeliverGoDown(yearMonth);
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
	 * 按公司产品正点交付钻取
	 * @return
	 */
    @RequestMapping(value = "/resultProOTDeliverByBukrsGoDown", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProOTDeliverByBukrsGoDown(HttpServletRequest request,
            @RequestParam(value = "yearMonth", required = false, defaultValue = "") String yearMonth,
            @RequestParam(value = "bukrs", required = false, defaultValue = "") String bukrs) {
        String resultJson = null;
        //String yearMonth, String bukrs
        try {
            resultJson = (String) iReportServiceKPI12.getProOTDeliverByBukrsGoDown(yearMonth,bukrs);
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
	 * 产品累计未完成总数钻取
	 * @return
	 */
    @RequestMapping(value = "/resultProUnCompSumGoDown", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProUnCompSumGoDown(HttpServletRequest request,
            @RequestParam(value = "yearMonth", required = false, defaultValue = "") String yearMonth) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI12.getProUnCompSumGoDown(yearMonth);
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
	 * 按公司产品累计未完成总数钻取
	 * @return
	 */
    @RequestMapping(value = "/resultProUnCompSumByBukrsGoDown", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProUnCompSumByBukrsGoDown(HttpServletRequest request,
            @RequestParam(value = "yearMonth", required = false, defaultValue = "") String yearMonth,
            @RequestParam(value = "bukrs", required = false, defaultValue = "") String bukrs) {
        String resultJson = null;
        try {
            resultJson = (String) iReportServiceKPI12.getProUnCompSumByBukrsGoDown(yearMonth,bukrs);
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
   	 * 产品完成总数钻取
   	 * @return
   	 */
       @RequestMapping(value = "/resultProCompSumGoDown", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
       @ResponseBody
       public String getProCompSumGoDown(HttpServletRequest request,
               @RequestParam(value = "yearMonth", required = false, defaultValue = "") String yearMonth) {
           String resultJson = null;
           try {
               resultJson = (String) iReportServiceKPI12.getProCompSumGoDown(yearMonth);
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
   	 * 按公司产品完成总数钻取
   	 * @return
   	 */
       @RequestMapping(value = "/resultProCompSumByBukrsGoDown", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
       @ResponseBody
       public String getProCompSumByBukrsGoDown(HttpServletRequest request,
               @RequestParam(value = "yearMonth", required = false, defaultValue = "") String yearMonth,
               @RequestParam(value = "bukrs", required = false, defaultValue = "") String bukrs) {
           String resultJson = null;
           try {
               resultJson = (String) iReportServiceKPI12.getProCompSumByBukrsGoDown(yearMonth,bukrs);
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
