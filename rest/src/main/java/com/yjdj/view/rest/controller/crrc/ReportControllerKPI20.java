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
import com.yjdj.view.core.service.IReportServiceKPI20;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * KPI-生产管理目标 controller
 * Created by  on 20162026
 */
@Controller
@RequestMapping("/crrc/kpi20")
public class ReportControllerKPI20 {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerKPI20.class);
	
	@Autowired
	private IReportServiceKPI20 iReportServiceKPI20;
	
	  
    @RequestMapping(value = "/ledgercheck", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getLedgercheck( @RequestParam(value = "yearMonth", required = false, defaultValue = "") 
                       String yearMonth) {
        String resultJson = null;
        try {
        	logger.info("resultJson+++++++++++" + resultJson);
        	resultJson = (String) iReportServiceKPI20.getLedgercheck(yearMonth);
            return resultJson;
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
    
   
    
}
