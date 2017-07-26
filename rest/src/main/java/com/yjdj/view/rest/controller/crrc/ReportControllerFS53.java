package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS53;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yjdj.view.core.web.util.dwz.AjaxObject;


/**
 * Created by yangzhijie on 17/02/16.
 */
@Controller
@RequestMapping("/crrc/reportFS53")
public class ReportControllerFS53{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS53.class);
	@Autowired
	private IReportServiceFS53 iReportServiceFS53;

    @Autowired
    private GenericService genericService;

    private static final String FS53 = "crrc/reportPageFS53";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS53() {
        return FS53;
    }


    /**
     * 故障类型
     */
    @RequestMapping(value = "/gzlx", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getGzlx(String id) {
    	
    		List<String> result = null;
			try {
				result = iReportServiceFS53.getGzlx(id);
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("FS53 Gzlx+++++++++++"+result);
            return result;
        
    }

    /**
     * 根据故障类型返回表单json
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public String getReport(@RequestBody QueryPojo queryPojo){
        try {
        	if(!queryPojo.getGzlx().isEmpty()){
        		Object resultJson = iReportServiceFS53.getReportJson(queryPojo);
                logger.info("FS53 resultJson+++++++++++"+resultJson);
                return (String)resultJson;
        	}else{
        		List<String> cpxhList = new ArrayList<String>();
        		cpxhList = getGzlx("");
        		queryPojo.setGzlx(cpxhList);
        		Object resultJson = iReportServiceFS53.getReportJson(queryPojo);
                logger.info("FS53 resultJson+++++++++++"+resultJson);
                return (String)resultJson;
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
    }
}
