package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS48;
import com.yjdj.view.core.service.impl.ReportServiceImpFS48;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by liwuhua on 2017年03月30日.
 */

@Controller
@RequestMapping("/crrc/reportFS48")
public class ReportControllerFS48 {
	
	@Autowired
	private IReportServiceFS48 ireportservicefs48;
	private final Logger logger=Logger.getLogger(ReportServiceImpFS48.class);
	
	private String tableName = "THM_FS48";
	private static final String FS48 = "crrc/reportPageFS48";
	
	
	@Autowired
	private GenericService genericService;
	
    @RequestMapping(method = RequestMethod.GET)
    public String reportFS48() {
        return FS48;
    }
	
    
    
    /**
     * 获取 所有站点
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getStations", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getStations( ){
            String resultJson;
			try {
				return resultJson = ireportservicefs48.getStations();
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			} catch (IOException e) {
				logger.error("IO 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("IO 数据封装异常").setCallbackType("").toString();
			}
    }
    
    
    /**
     * 获取fs48结果集
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean){
            String resultJson;
			try {
				return resultJson = ireportservicefs48.selecProductAttachOrSparepart(queryBean);
				
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			} catch (IOException e) {
				logger.error("IO 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("IO 数据封装异常").setCallbackType("").toString();
			}
    }
	
}
