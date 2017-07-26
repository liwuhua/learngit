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
import com.yjdj.view.core.service.IReportServiceFS18;
import com.yjdj.view.core.service.IThmVbap01Service;
import com.yjdj.view.core.service.impl.ReportServiceImpFS18;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by liwuhua on 2016年11月18日.
 */

@Controller
@RequestMapping("/crrc/reportFS18")
public class ReportControllerFS18 {
	
	@Autowired
	private IReportServiceFS18 ireportservicefs18;
	
	@Autowired
    private IThmVbap01Service iThmVbap01Service;
	
	@Autowired
	private GenericService genericService;
	
    private final Logger logger = Logger.getLogger(ReportServiceImpFS18.class);
	
    private static final String FS18 = "crrc/reportPageFS18";
    private final String tableName = "THM_VBAP_04_01";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS18() {
        return FS18;
    }
    
    /**
     * 获取fs02 销售订单
     * @param vbeln
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/vbeln", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getVbeln(@RequestParam(value = "id", required = false, defaultValue = "") String vbeln,
                           @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                           @RequestParam(value = "limit", required = false, defaultValue = "20") int limit){
        try {
            return iThmVbap01Service.getVbeln(tableName,vbeln,start,limit);
        } catch (JsonProcessingException e) {
        	logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        }
    }
    
    /**
     * 获取fs18 销售订单工厂
     * @return
     */
    @RequestMapping(value = "/saorderFactory", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTcompCode(HttpServletRequest request){
    	   List<String> list = (List<String>) request.getSession().getAttribute("FS18-"+ CommonConstant.PLANT);
           if (CollectionUtils.isNotEmpty(list)) {
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
     * 获取fs18 生产工厂
     * @return
     */
    @RequestMapping(value = "/proFactory", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListPcompCode(HttpServletRequest request){
    	   List<String> list = (List<String>) request.getSession().getAttribute("FS18-"+CommonConstant.PLANT);
           if (CollectionUtils.isNotEmpty(list)) {
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
    
      
    //订单类型
    @RequestMapping(value = "/produOrderType", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListOrderType(@RequestParam(value = "id", required = false, defaultValue = "") String ordTypeCode,
    		                       @RequestParam(value = "name", required = false, defaultValue = "") String txtmd){
    	try {
    		return genericService.getAllOrderType(tableName,ordTypeCode,txtmd);
    	  } catch (JsonProcessingException e) {
    		  logger.error("Json 数据封装异常" + e.getMessage());
              return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
          }
    	
    }
    
    
    /**
     * 获取fs18结果集
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean,HttpServletRequest request){
    	List<String> plantList = (List<String>) request.getSession().getAttribute("FS18-"+CommonConstant.PLANT);    
    	try {
				String resultJson = ireportservicefs18.getListFs18(queryBean,plantList);
				return resultJson;
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			}
    }
    
}
