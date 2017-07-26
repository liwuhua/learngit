package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS29;
import com.yjdj.view.core.service.IThmVbap01Service;
import com.yjdj.view.core.service.impl.ReportServiceImpFS29;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by liwuhua on 年月日.
 */

@Controller
@RequestMapping("/crrc/reportFS29")
public class ReportControllerFS29 {
	
	@Autowired
	private IReportServiceFS29 ireportservicefs29;
	private final Logger logger=Logger.getLogger(ReportServiceImpFS29.class);
	
	@Autowired
	private GenericService genericService;
	
	@Autowired
	private IThmVbap01Service iThmVbap01Service;
	
	private String tableName = "THM_MDTBX_02";
	private static final String FS29 = "crrc/reportPageFS29";
	
    @RequestMapping(method = RequestMethod.GET)
    public String reportFS29() {
        return FS29;
    }
    
	
	/**
     * 获取fs29 权限字段 工厂
     * @return
     */
    @RequestMapping(value = "/factory", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListPcompCode(HttpServletRequest request){
    	   List<String> list = (List<String>) request.getSession().getAttribute("FS29-" + CommonConstant.PLANT);
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
     * 获取fs29 销售订单
     *
     * @param vbeln
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/vbeln", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getVbeln(
            @RequestParam(value = "id", required = false, defaultValue = "") String vbeln,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "15") int limit) {
        try {  
            return iThmVbap01Service.getVbeln(tableName, vbeln, start, limit);
        }catch (JsonProcessingException e) {
        	logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
        	logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
    
    /**
     * 获取fs29 销售订单行号
     *
     * @param vbeln
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/linenum", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getLinenum(
            @RequestParam(value = "id", required = false, defaultValue = "") String linenum,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        try {
            return iThmVbap01Service.getLinenum(tableName, linenum.trim(), start, limit);
        }catch (JsonProcessingException e) {
        	logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
        	logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
    
    
    /**
     * 获取fs29 生产订单
     *      
     * @param aufnr
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/aufnr", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getAufnr(
            @RequestParam(value = "id", required = false, defaultValue = "") String aufnr,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam (value = "limit", required = false, defaultValue = "15") int limit) {
        try {  
            return iThmVbap01Service.getAufnr(tableName, aufnr, start, limit);
        }catch (JsonProcessingException e) {
        	logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
        	logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
    
    /**
     * 获取fs29 生产订单物料编码
     *
     * @param id
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/matnr", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getMatnr(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                           @RequestParam(value = "name", required = false, defaultValue = "") String name,
                           @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                           @RequestParam (value = "limit", required = false, defaultValue = "15") int limit               
    		) {
        try {  
            return genericService.getTmaterial(tableName, id, name,start,limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
    /**
     * 获取fs29   组件物料编码
     *
     * @param id
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/copomatnum", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getCopomatnum(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                               @RequestParam(value = "name", required = false, defaultValue = "") String name,
                               @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                               @RequestParam (value = "limit", required = false, defaultValue = "15") int limit ) {
        try { 
            return genericService.getCopomatnum(tableName, id, name,start,limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
 
    
    
    
    /**
     * 获取fs29 生产订单类型 
     * <p>Title: getListOrderType</p>
     * <p>Description: </p>
     * @param ordTypeCode
     * @param txtmd
     * @return
     */
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
     * 获取fs29 产品型号
     *
     * @param vbeln
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/productmodel", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProductmodel(
            @RequestParam(value = "id", required = false, defaultValue = "") String productmodel,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        try {
            return iThmVbap01Service.getProductmodel(tableName, productmodel.trim(), start, limit);
        }catch (JsonProcessingException e) {
        	logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
        	logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
    
    /**
     * 获取fs29 工作中心
     *      
     * @param zhwcx
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/zhwcx", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getZhwcx(@RequestParam(value = "id", required = false, defaultValue = "") String zhwcx,
                           @RequestParam(value = "name", required = false, defaultValue = "") String txtmd) {
        try {                          
            return genericService.getAllZhwcx(tableName, zhwcx,txtmd );
        }catch (JsonProcessingException e) {
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
            @RequestParam(value = "limit", required = false, defaultValue = "15") int limit) {
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
     * 组件采购类型  COMPPURTYPE  需手动在mysql中维护   
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
     * 查询
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean,HttpServletRequest request){
            String resultJson;
			try {
				List<String> plantList = (List<String>) request.getSession().getAttribute("FS29-" + CommonConstant.PLANT);
				resultJson = (String)ireportservicefs29.getListFs29(queryBean,plantList);
				return resultJson;
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			}catch (IOException e) {
				logger.error("IO 操作异常" + e.getMessage());
	            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
	        } 
        }
    
    
    
    /**
     * 导出
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile_04(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	QueryBean querybean = new QueryBean();
        String startTimeThree=request.getParameter("startTimeThree");
        String endTimeThree=request.getParameter("endTimeThree");
        String startTime=request.getParameter("startTime");  // DELIDATE = 查询条件中的报告日期
		String ylzd = request.getParameter("ylzd");
		
		//list类型的
        String matlGroupValue = request.getParameter("matlGroupValue");
        String vkorgValue = request.getParameter("vkorgValue");
        String werksValue=request.getParameter("werksValue");
        String kunnrValue=request.getParameter("kunnrValue");
        String vbelnValue = request.getParameter("vbelnValue");
        String dwerkValue = request.getParameter("dwerkValue");
        String matnrValue = request.getParameter("matnrValue");
        String vendorValue = request.getParameter("vendorValue");
        String purGroupValue = request.getParameter("purGroupValue");
        String oneValue = request.getParameter("oneValue");
        String twoValue = request.getParameter("twoValue");
        String dauatValue = request.getParameter("dauatValue");
		String comppurtypeValue = request.getParameter("comppurtypeValue");
		
		Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        Boolean isExclu1 = Boolean.valueOf(request.getParameter("isExclu1"));
        Boolean isExclu2 = Boolean.valueOf(request.getParameter("isExclu2"));
        Boolean isShow = Boolean.valueOf(request.getParameter("isShow"));
      
        querybean.setStartTime(startTimeThree);
        querybean.setEndTimeThree(endTimeThree);
        querybean.setStartTime(startTime);
        querybean.setYlzd(ylzd);
        querybean.setStartitem(null);
        querybean.setPageitem(null);
        
        
        
        
		if ("".equals(matlGroupValue)) {
            querybean.setMatlGroupValue(null);
        } else {
            querybean.setMatlGroupValue(Lists.newArrayList(matlGroupValue.split(",")));
        }
		
		
		if ("".equals(vkorgValue)) {
            querybean.setVkorgValue(null);
        } else {
            querybean.setVkorgValue(Lists.newArrayList(vkorgValue.split(",")));
        }
        
        
	   if ("".equals(werksValue)) {
            querybean.setWerksValue(null);
        } else {
            querybean.setWerksValue(Lists.newArrayList(werksValue.split(",")));
        }
	   
      if ("".equals(kunnrValue)) {
	   querybean.setKunnrValue(null);
	   } else {
	   querybean.setKunnrValue(Lists.newArrayList(kunnrValue.split(",")));
	   }

	   if ("".equals(vbelnValue)) {
	   querybean.setVbelnValue(null);
	   } else {
	   querybean.setVbelnValue(Lists.newArrayList(vbelnValue.split(",")));
	   }

	   if ("".equals(dwerkValue)) {
	   querybean.setDwerkValue(null);
	   } else {
	   querybean.setDwerkValue(Lists.newArrayList(dwerkValue.split(",")));
	   }

	   if ("".equals(matnrValue)) {
	   querybean.setMatnrValue(null);
	   } else {
	   querybean.setMatnrValue(Lists.newArrayList(matnrValue.split(",")));
	   }
	   if ("".equals(vendorValue)) {
	   querybean.setVendorValue(null);
	   } else {
	   querybean.setVendorValue(Lists.newArrayList(vendorValue.split(",")));
	   }

	   if ("".equals(purGroupValue)) {
	   querybean.setPurGroupValue(null);
	   } else {
	   querybean.setPurGroupValue(Lists.newArrayList(purGroupValue.split(",")));
	   }

	   if ("".equals(oneValue)) {
	   querybean.setOneValue(null);
	   } else {
	   querybean.setOneValue(Lists.newArrayList(oneValue.split(",")));
	   }

	   if ("".equals(twoValue)) {
	   querybean.setTwoValue(null);
	   } else {
	   querybean.setTwoValue(Lists.newArrayList(twoValue.split(",")));
	   }

	   if ("".equals(dauatValue)) {
	   querybean.setDauatValue(null);
	   } else {
	   querybean.setDauatValue(Lists.newArrayList(dauatValue.split(",")));
	   }

	   if ("".equals(comppurtypeValue)) {
	   querybean.setComppurtypeValue(null);
	   } else {
	   querybean.setComppurtypeValue(Lists.newArrayList(comppurtypeValue.split(",")));
	   }

		
        querybean.setExport(isExport);
        querybean.setExclu1(isExclu1);
        querybean.setExclu2(isExclu2);
        querybean.setShow(isShow);
        
        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
        	@SuppressWarnings("unchecked")
			List<String> plantList = (List<String>) request.getSession().getAttribute("FS29-" + CommonConstant.PLANT);
			resultJson = ireportservicefs29.getListFs29(querybean,plantList);
            if (resultJson != null && querybean.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "生产订单缺料统计分析表.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }
    
    
	
}
