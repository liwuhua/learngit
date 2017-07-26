package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;
import java.text.ParseException;

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
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.IReportServiceFS23;
import com.yjdj.view.core.service.impl.ReportServiceImpFS23;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by liwuhua on 2316年10月21日.
 */

@Controller
@RequestMapping("/crrc/reportFS23")
public class ReportControllerFS23 {
	
	@Autowired
	private IReportServiceFS23 ireportservicefs23;
	private final Logger log=Logger.getLogger(ReportServiceImpFS23.class);
	
	
    /**
     * 获取fs23结果集
     * @param queryBean
     * @return  
     */
    @RequestMapping(value = "/result", method = {RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody  
    public String getReport(@RequestParam (value="AUFNR",required=true) String AUFNR){
            String resultJson;
			try {
				resultJson = ireportservicefs23.getListFs23(AUFNR);
				return resultJson;
			} catch (DataAccessException e) {
				log.error("数据查询异常"+e.getMessage());
				return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				log.error("Json数据封装异常"+e.getMessage());
				return AjaxObject.newError("Json数据封装异常").setCallbackType("").toString();
			}
    }
	
}
