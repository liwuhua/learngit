package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS44;
import com.yjdj.view.core.service.impl.ReportServiceImpFS44;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by liwuhua on 年月日.
 */

@Controller
@RequestMapping("/crrc/reportFS44")
public class ReportControllerFS44 {
	
	@Autowired
	private IReportServiceFS44 ireportservicefs44;
	private final Logger logger=Logger.getLogger(ReportServiceImpFS44.class);
	
	private String tableName = "THM_FS44";
	private static final String FS44 = "crrc/reportPageFS44";
	
	
	@Autowired
	private GenericService genericService;
	
    @RequestMapping(method = RequestMethod.GET)
    public String reportFS44() {
        return FS44;
    }
	
    
	/**
     * 获取fs44  权限字段 工厂
     * @return
     */
    @RequestMapping(value = "/factory", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListPcompCode(HttpServletRequest request){
    	List<String> plantList = (List<String>) request.getSession().getAttribute("FS44-" + CommonConstant.PLANT);
           if (CollectionUtils.isNotEmpty(plantList)) {
               try {
                   String json = genericService.getTplantJson(plantList);
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
     * 获取fs44 工作中心
     *      
     * @param zhwcx
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/zhwcx", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getZhwcx(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                           @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        try {
            return genericService.getAllZhwcx(tableName, id,name );
        }catch (JsonProcessingException e) {
        	logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
        	logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
	
    /**
     * 获取fs44   组件采购类型  COMPPURTYPE  需手动在mysql中维护   
     *
     * @param id
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/comppurtype", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getCompPurtype(HttpServletRequest request,
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        try {
                return genericService.getAllCompPurtype(tableName, id, name);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
	/**
	 * 采购组
	 *
	 * @param id
	 * @param name
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/tpurgroup", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String getTpurGroup(HttpServletRequest request,
							   @RequestParam(value = "id", required = false, defaultValue = "") String id,
							   @RequestParam(value = "name", required = false, defaultValue = "") String name,
                               @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                               @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
		try {
			return genericService.getAllTpurGroup(tableName, id, name,start,limit);
		} catch (JsonProcessingException e) {
			logger.error("Json 数据封装异常" + e.getMessage());
			return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
		} catch (DataAccessException e) {
			logger.error("数据查询异常" + e.getMessage());
			return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
		}
	}
    
    
	 
    /**
     * 供应商编码
     *
     * @param id
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/tvendor", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTvendorParam(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                                      @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                      @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        try {
            return genericService.getAllTvendor(tableName, id, name,start,limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
    
    
    /**
     * 配送方式  ZPSFS  需手动在mysql中维护   
     *
     * @param id
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/zpsfs", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getZpsfs(HttpServletRequest request,
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        try {
                return genericService.getAllZpsfs(tableName, id, name);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
    
	
    /**
     * 获取fs44结果集
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean,HttpServletRequest request){
    	List<String> plantList = (List<String>) request.getSession().getAttribute("FS44-" + CommonConstant.PLANT);
            String resultJson;
			try {
				resultJson = ireportservicefs44.getListFs44(queryBean,plantList);
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
