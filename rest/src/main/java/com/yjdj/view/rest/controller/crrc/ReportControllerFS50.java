package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS50;
import com.yjdj.view.core.service.IThmVbap01Service;
import com.yjdj.view.core.service.impl.ReportServiceImpFS50;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelExportUtil2227;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sunwan on 2016/12/30.
 */

@Controller
@RequestMapping("/crrc/reportFS50")
public class ReportControllerFS50 {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS50.class);

    @Autowired
    private IReportServiceFS50 iReportServiceFS50;

    @Autowired
    private IThmVbap01Service iThmVbap01Service;

    @Autowired
    private GenericService genericService;
    
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    private String tableName = "THM_FS50";
    private static final String FS5001 = "crrc/reportPageFS5001";
    private static final String FS5002 = "crrc/reportPageFS5002";
    private static final String FS5003 = "crrc/reportPageFS5003";
    private static final String FS5004 = "crrc/reportPageFS5004";

    @RequestMapping(value = "/01", method = RequestMethod.GET)
    public String reportFS5001() {
        return FS5001;
    }

    @RequestMapping(value = "/02", method = RequestMethod.GET)
    public String reportFS5002() {
        return FS5002;
    }

    @RequestMapping(value = "/03", method = RequestMethod.GET)
    public String reportFS5003() {
        return FS5003;
    }
    
    @RequestMapping(value = "/04", method = RequestMethod.GET)
    public String reportFS5004() {
        return FS5004;
    }

    
    /**
     * 信息编号
     *
     * @param id
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/infocode", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getInfocode(  @RequestParam(value = "id", required = false, defaultValue = "") String name,
                                @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                @RequestParam(value = "limit", required = false, defaultValue = "15") Integer limit) {
        try {
        	return iReportServiceFS50.getInfocode(name,start,limit);
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
    
    /**
     * 产品编号
     *
     * @param id
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/produccode", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProducCode(  @RequestParam(value = "id", required = false, defaultValue = "") String name,
    							  @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                  @RequestParam(value = "limit", required = false, defaultValue = "15") Integer limit) {
        try {
        	return iReportServiceFS50.getProducCode(name,start,limit);
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
    
    
    /**
     * 机车编号
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/motorcode", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getMotorCode(  @RequestParam(value = "id", required = false, defaultValue = "") String name,
            					 @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                 @RequestParam(value = "limit", required = false, defaultValue = "15") Integer limit) {
        try {  
        	String str = URLDecoder.decode(name);
        	return iReportServiceFS50.getMotorCode(str,start,limit);
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
    
    
    /**
     * 报告编号
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/reportcode", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReportCode(  @RequestParam(value = "id", required = false, defaultValue = "") String name,
            					 @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                 @RequestParam(value = "limit", required = false, defaultValue = "15") Integer limit) {
        try {  
        	return iReportServiceFS50.getReportCode(name,start,limit);
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
    
    
    /**
     * 质量损失类别编号
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/losstypecode", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getLossTypeCode(  @RequestParam(value = "id", required = false, defaultValue = "") String name,
									@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
						            @RequestParam(value = "limit", required = false, defaultValue = "15") Integer limit) {
    	        try {
    	            return iReportServiceFS50.getLossTypeCode(name,start,limit);
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
    
    
    
    
    
    /**
     * 事故类别
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/accitype", method ={RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getAcciType(@RequestParam(value = "id", required = false, defaultValue = "") String name){
     
    	  try {
			return iReportServiceFS50.getAccitype(name);
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
    
    
    /**
     * 故障部位
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/defalocation", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getDefalocation(@RequestParam(value = "id", required = false, defaultValue = "") String name){
     
    	  try {
			return iReportServiceFS50.getDefalocation(name);
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
    
    
    /**
     * 产品寿命阶段
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/productlife", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProductLife(@RequestParam(value = "id", required = false, defaultValue = "") String name){
     
    	  try {
			return iReportServiceFS50.getProductLife(name);
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
    
    
    /**
     * 产品型号
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/productmodel", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProductModel(@RequestParam(value = "id", required = false, defaultValue = "") String name){
     
    	  try {
			return iReportServiceFS50.getProductModel(name);
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
    
    
    /**
     * 配属局段
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/attaburea", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getAttaburea(@RequestParam(value = "id", required = false, defaultValue = "") String name){
     
    	  try {
			return iReportServiceFS50.getAttaburea(name);
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
    
    
    /**
     * 严重度
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/severity", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getSeverity(@RequestParam(value = "id", required = false, defaultValue = "") String name){
     
    	  try {
			return iReportServiceFS50.getSeverity(name);
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
    
    
    /**
     * 供应商
     *
     * @param id
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/tvendor", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getTvendor(  @RequestParam(value = "id", required = false, defaultValue = "") String name,
                                @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                @RequestParam(value = "limit", required = false, defaultValue = "15") Integer limit) {
        try {
        	return iReportServiceFS50.getTvendor(name,start,limit);
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
    
    
    
    
    //通用
    @RequestMapping(value = "/result_01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getReport_01(@RequestBody QueryPojo queryPojo){
        Object resultJson = null;
        try {
            resultJson = iReportServiceFS50.selectData_01(queryPojo);
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
        return resultJson.toString();
    }
    
    
//国铁服务中心
    @RequestMapping(value = "/result_02", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getReport_02(@RequestBody QueryPojo queryPojo){
        Object resultJson = null;
        try {
            resultJson = iReportServiceFS50.selectData_02(queryPojo);
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
        return resultJson.toString();
    }
//质保部
    @RequestMapping(value = "/result_03", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getReport_03(@RequestBody QueryPojo queryPojo){
        Object resultJson = null;
        try {
            resultJson = iReportServiceFS50.selectData_03(queryPojo);
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
        return resultJson.toString();
    }

    //04表
    @RequestMapping(value = "/result_04", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getReport_04(@RequestBody QueryPojo queryPojo){
        Object resultJson = null;
        try {
            resultJson = iReportServiceFS50.selectData_04(queryPojo);
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
        return resultJson.toString();
    }

    //通用导出
    @RequestMapping(value = "/downloadFile_01", method = RequestMethod.POST)
    public String downloadFile_01(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryPojo queryPojo = new QueryPojo();
        String xxbh=request.getParameter("xxbh");
        String psjd=request.getParameter("psjd");
        String cpsmjd=request.getParameter("cpsmjd");
        String jxdd=request.getParameter("jxdd");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String cpxh=request.getParameter("cpxh");
        String cpbh=request.getParameter("cpbh");
        String jcbh = request.getParameter("jcbh");
        String gzbw = request.getParameter("gzbw");
        String sglb = request.getParameter("sglb");
        String yzd = request.getParameter("yzd");
        String lifnr = request.getParameter("lifnr");
        String startYearMonth = request.getParameter("startYearMonth");
        String endYearMonth = request.getParameter("endYearMonth");
        
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if ("".equals(xxbh)) {
            queryPojo.setXxbh(null);
        } else {
            queryPojo.setXxbh(Lists.newArrayList(xxbh.split(",")));
        }
        if ("".equals(psjd)) {
            queryPojo.setPsjd(null);
        } else {
            queryPojo.setPsjd(Lists.newArrayList(psjd.split(",")));
        }
        if ("".equals(cpsmjd)) {
            queryPojo.setCpsmjd(null);
        } else {
            queryPojo.setCpsmjd(Lists.newArrayList(cpsmjd.split(",")));
        }
        if ("".equals(jxdd)) {
            queryPojo.setJxdd(null);
        } else {
            queryPojo.setJxdd(Lists.newArrayList(jxdd.split(",")));
        }
        queryPojo.setStartTime(startTime);
        queryPojo.setEndTime(endTime);
        if ("".equals(cpxh)) {
            queryPojo.setCpxh(null);
        } else {
            queryPojo.setCpxh(Lists.newArrayList(cpxh.split(",")));
        }
        if ("".equals(cpbh)) {
            queryPojo.setCpbh(null);
        } else {
            queryPojo.setCpbh(Lists.newArrayList(cpbh.split(",")));
        }
        if ("".equals(jcbh)) {
            queryPojo.setJcbh(null);
        } else {
            queryPojo.setJcbh(Lists.newArrayList(jcbh.split(",")));
        }
        if ("".equals(gzbw)) {
            queryPojo.setGzbw(null);
        } else {
            queryPojo.setGzbw(Lists.newArrayList(gzbw.split(",")));
        }
        if ("".equals(sglb)) {
            queryPojo.setSglb(null);
        } else {
            queryPojo.setSglb(Lists.newArrayList(sglb.split(",")));
        }
        if ("".equals(yzd)) {
            queryPojo.setYzd(null);
        } else {
            queryPojo.setYzd(Lists.newArrayList(yzd.split(",")));
        }
        queryPojo.setStartYearMonth(startYearMonth);
        queryPojo.setEndYearMonth(endYearMonth);
        if ("".equals(lifnr)) {
            queryPojo.setLifnr(null);
        } else {
            queryPojo.setLifnr(Lists.newArrayList(lifnr.split(",")));
        }
        queryPojo.setExport(isExport);

        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS50.selectData_01(queryPojo);
            if (resultJson != null && queryPojo.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "厂外铁路产品质量信息表-通用.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }


    //国铁服务中心导出
    @RequestMapping(value = "/downloadFile_02", method = RequestMethod.POST)
    public String downloadFile_02(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryPojo queryPojo = new QueryPojo();
        String xxbh=request.getParameter("xxbh");
        String psjd=request.getParameter("psjd");
        String cpsmjd=request.getParameter("cpsmjd");
        String jxdd=request.getParameter("jxdd");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String cpxh=request.getParameter("cpxh");
        String cpbh=request.getParameter("cpbh");
        String jcbh = request.getParameter("jcbh");
        String gzbw = request.getParameter("gzbw");
        String sglb = request.getParameter("sglb");
        String yzd = request.getParameter("yzd");
        String lifnr = request.getParameter("lifnr");
        String lbbh = request.getParameter("lbbh");
        String bgbh = request.getParameter("bgbh");
        String startYearMonth = request.getParameter("startYearMonth");
        String endYearMonth = request.getParameter("endYearMonth");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if ("".equals(xxbh)) {
            queryPojo.setXxbh(null);
        } else {
            queryPojo.setXxbh(Lists.newArrayList(xxbh.split(",")));
        }
        if ("".equals(psjd)) {
            queryPojo.setPsjd(null);
        } else {
            queryPojo.setPsjd(Lists.newArrayList(psjd.split(",")));
        }
        if ("".equals(cpsmjd)) {
            queryPojo.setCpsmjd(null);
        } else {
            queryPojo.setCpsmjd(Lists.newArrayList(cpsmjd.split(",")));
        }
        if ("".equals(jxdd)) {
            queryPojo.setJxdd(null);
        } else {
            queryPojo.setJxdd(Lists.newArrayList(jxdd.split(",")));
        }
        queryPojo.setStartTime(startTime);
        queryPojo.setEndTime(endTime);
        queryPojo.setStartYearMonth(startYearMonth);
        queryPojo.setEndYearMonth(endYearMonth);
        if ("".equals(cpxh)) {
            queryPojo.setCpxh(null);
        } else {
            queryPojo.setCpxh(Lists.newArrayList(cpxh.split(",")));
        }
        if ("".equals(cpbh)) {
            queryPojo.setCpbh(null);
        } else {
            queryPojo.setCpbh(Lists.newArrayList(cpbh.split(",")));
        }
        if ("".equals(jcbh)) {
            queryPojo.setJcbh(null);
        } else {
            queryPojo.setJcbh(Lists.newArrayList(jcbh.split(",")));
        }
        if ("".equals(gzbw)) {
            queryPojo.setGzbw(null);
        } else {
            queryPojo.setGzbw(Lists.newArrayList(gzbw.split(",")));
        }
        if ("".equals(sglb)) {
            queryPojo.setSglb(null);
        } else {
            queryPojo.setSglb(Lists.newArrayList(sglb.split(",")));
        }
        if ("".equals(yzd)) {
            queryPojo.setYzd(null);
        } else {
            queryPojo.setYzd(Lists.newArrayList(yzd.split(",")));
        }
        if ("".equals(lifnr)) {
            queryPojo.setLifnr(null);
        } else {
            queryPojo.setLifnr(Lists.newArrayList(lifnr.split(",")));
        }
        if ("".equals(lbbh)) {
            queryPojo.setLbbh(null);
        } else {
            queryPojo.setLbbh(Lists.newArrayList(lbbh.split(",")));
        }
        if ("".equals(bgbh)) {
            queryPojo.setBgbh(null);
        } else {
            queryPojo.setBgbh(Lists.newArrayList(bgbh.split(",")));
        }
        queryPojo.setExport(isExport);

        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS50.selectData_02(queryPojo);
            if (resultJson != null && queryPojo.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "厂外铁路产品质量信息表-国铁服务中心.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }

    //质保部导出
    @RequestMapping(value = "/downloadFile_03", method = RequestMethod.POST)
    public String downloadFile_03(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryPojo queryPojo = new QueryPojo();
        String xxbh=request.getParameter("xxbh");
        String psjd=request.getParameter("psjd");
        String cpsmjd=request.getParameter("cpsmjd");
        String jxdd=request.getParameter("jxdd");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String cpxh=request.getParameter("cpxh");
        String cpbh=request.getParameter("cpbh");
        String jcbh = request.getParameter("jcbh");
        String gzbw = request.getParameter("gzbw");
        String sglb = request.getParameter("sglb");
        String yzd = request.getParameter("yzd");
        String lifnr = request.getParameter("lifnr");
        String lbbh = request.getParameter("lbbh");
        String startYearMonth = request.getParameter("startYearMonth");
        String endYearMonth = request.getParameter("endYearMonth");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if ("".equals(xxbh)) {
            queryPojo.setXxbh(null);
        } else {
            queryPojo.setXxbh(Lists.newArrayList(xxbh.split(",")));
        }
        if ("".equals(psjd)) {
            queryPojo.setPsjd(null);
        } else {
            queryPojo.setPsjd(Lists.newArrayList(psjd.split(",")));
        }
        if ("".equals(cpsmjd)) {
            queryPojo.setCpsmjd(null);
        } else {
            queryPojo.setCpsmjd(Lists.newArrayList(cpsmjd.split(",")));
        }
        if ("".equals(jxdd)) {
            queryPojo.setJxdd(null);
        } else {
            queryPojo.setJxdd(Lists.newArrayList(jxdd.split(",")));
        }
        queryPojo.setStartTime(startTime);
        queryPojo.setEndTime(endTime);
        queryPojo.setStartYearMonth(startYearMonth);
        queryPojo.setEndYearMonth(endYearMonth);
        if ("".equals(cpxh)) {
            queryPojo.setCpxh(null);
        } else {
            queryPojo.setCpxh(Lists.newArrayList(cpxh.split(",")));
        }
        if ("".equals(cpbh)) {
            queryPojo.setCpbh(null);
        } else {
            queryPojo.setCpbh(Lists.newArrayList(cpbh.split(",")));
        }
        if ("".equals(jcbh)) {
            queryPojo.setJcbh(null);
        } else {
            queryPojo.setJcbh(Lists.newArrayList(jcbh.split(",")));
        }
        if ("".equals(gzbw)) {
            queryPojo.setGzbw(null);
        } else {
            queryPojo.setGzbw(Lists.newArrayList(gzbw.split(",")));
        }
        if ("".equals(sglb)) {
            queryPojo.setSglb(null);
        } else {
            queryPojo.setSglb(Lists.newArrayList(sglb.split(",")));
        }
        if ("".equals(yzd)) {
            queryPojo.setYzd(null);
        } else {
            queryPojo.setYzd(Lists.newArrayList(yzd.split(",")));
        }
        if ("".equals(lifnr)) {
            queryPojo.setLifnr(null);
        } else {
            queryPojo.setLifnr(Lists.newArrayList(lifnr.split(",")));
        }
        if ("".equals(lbbh)) {
            queryPojo.setLbbh(null);
        } else {
            queryPojo.setLbbh(Lists.newArrayList(lbbh.split(",")));
        }
        queryPojo.setExport(isExport);

        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS50.selectData_03(queryPojo);
            if (resultJson != null && queryPojo.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "厂外铁路产品质量信息表-质保部.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }

    //04导出
    @RequestMapping(value = "/downloadFile_04", method = RequestMethod.POST)
    public String downloadFile_04(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryPojo queryPojo = new QueryPojo();
        String xxbh=request.getParameter("xxbh");
        String psjd=request.getParameter("psjd");
        String cpsmjd=request.getParameter("cpsmjd");
        String jxdd=request.getParameter("jxdd");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String cpxh=request.getParameter("cpxh");
        String cpbh=request.getParameter("cpbh");
        String jcbh = request.getParameter("jcbh");
        String gzbw = request.getParameter("gzbw");
        String sglb = request.getParameter("sglb");
        String yzd = request.getParameter("yzd");
        String lifnr = request.getParameter("lifnr");
        String lbbh = request.getParameter("lbbh");
        String bgbh = request.getParameter("bgbh");
        String startYearMonth = request.getParameter("startYearMonth");
        String endYearMonth = request.getParameter("endYearMonth");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if ("".equals(xxbh)) {
            queryPojo.setXxbh(null);
        } else {
            queryPojo.setXxbh(Lists.newArrayList(xxbh.split(",")));
        }
        if ("".equals(psjd)) {
            queryPojo.setPsjd(null);
        } else {
            queryPojo.setPsjd(Lists.newArrayList(psjd.split(",")));
        }
        if ("".equals(cpsmjd)) {
            queryPojo.setCpsmjd(null);
        } else {
            queryPojo.setCpsmjd(Lists.newArrayList(cpsmjd.split(",")));
        }
        if ("".equals(jxdd)) {
            queryPojo.setJxdd(null);
        } else {
            queryPojo.setJxdd(Lists.newArrayList(jxdd.split(",")));
        }
        queryPojo.setStartTime(startTime);
        queryPojo.setEndTime(endTime);
        queryPojo.setStartYearMonth(startYearMonth);
        queryPojo.setEndYearMonth(endYearMonth);
        if ("".equals(cpxh)) {
            queryPojo.setCpxh(null);
        } else {
            queryPojo.setCpxh(Lists.newArrayList(cpxh.split(",")));
        }
        if ("".equals(cpbh)) {
            queryPojo.setCpbh(null);
        } else {
            queryPojo.setCpbh(Lists.newArrayList(cpbh.split(",")));
        }
        if ("".equals(jcbh)) {
            queryPojo.setJcbh(null);
        } else {
            queryPojo.setJcbh(Lists.newArrayList(jcbh.split(",")));
        }
        if ("".equals(gzbw)) {
            queryPojo.setGzbw(null);
        } else {
            queryPojo.setGzbw(Lists.newArrayList(gzbw.split(",")));
        }
        if ("".equals(sglb)) {
            queryPojo.setSglb(null);
        } else {
            queryPojo.setSglb(Lists.newArrayList(sglb.split(",")));
        }
        if ("".equals(yzd)) {
            queryPojo.setYzd(null);
        } else {
            queryPojo.setYzd(Lists.newArrayList(yzd.split(",")));
        }
        if ("".equals(lifnr)) {
            queryPojo.setLifnr(null);
        } else {
            queryPojo.setLifnr(Lists.newArrayList(lifnr.split(",")));
        }
        if ("".equals(lbbh)) {
            queryPojo.setLbbh(null);
        } else {
            queryPojo.setLbbh(Lists.newArrayList(lbbh.split(",")));
        }
        if ("".equals(bgbh)) {
            queryPojo.setBgbh(null);
        } else {
            queryPojo.setBgbh(Lists.newArrayList(bgbh.split(",")));
        }
        queryPojo.setExport(isExport);

        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS50.selectData_04(queryPojo);
            if (resultJson != null && queryPojo.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "厂外铁路产品质量信息表-国铁服务中心总表.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }

    /**
     * 导入数据 Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "text/plain")
    @ResponseBody
    public  String upload(MultipartHttpServletRequest request, HttpServletResponse response, Object resultJson) {
        Iterator<String> itr = request.getFileNames();
        String filePath = null;
        MultipartFile mpf = null;
        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());
            logger.info(mpf.getOriginalFilename() + " uploaded! " + files.size());
            // 获得在tomcat中项目的路径， 需要在web.xml配置ft.webapp
            String webRootPath = System.getProperty("ft.webapp");
            File dir = new File(webRootPath + File.separator + "uploadFiles");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File serverFile = new File(dir.getAbsolutePath() + File.separator + mpf.getOriginalFilename());
            if (serverFile.exists()) {
                serverFile.delete();
                serverFile = new File(dir.getAbsolutePath() + File.separator + mpf.getOriginalFilename());
            }
            try {
                fileMeta = new FileMeta();
                fileMeta.setBytes(mpf.getBytes());
                FileCopyUtils.copy(mpf.getBytes(), serverFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            filePath = serverFile.toString();
            filePath = filePath.replace("\\", "//");
        }
        try {
            logger.info("filePath++++++++++++" + filePath);
            resultJson = iReportServiceFS50.importData(filePath);
            return (String) resultJson;
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据库写入异常" + e.getMessage());
            return AjaxObject.newError("数据库写入异常").setCallbackType("").toString();
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        } catch (InvalidFormatException e) {
            // TODO Auto-generated catch block
            logger.error("格式错误异常" + e.getMessage());
            return AjaxObject.newError("格式错误异常").setCallbackType("").toString();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            logger.error("实例化异常" + e.getMessage());
            return AjaxObject.newError("实例化异常").setCallbackType("").toString();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            logger.error("反射异常" + e.getMessage());
            return AjaxObject.newError("反射异常").setCallbackType("").toString();
        }

        
    }


    //模板导出
    @RequestMapping(value = "/downloadTemplate", method = RequestMethod.POST)
    public String downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object resultJson = null;
        try {
            resultJson = iReportServiceFS50.downloadTemplate();
            if (resultJson != null) {
            	ExcelExportUtil2227 result = (ExcelExportUtil2227) resultJson;
                result.write(request, response, "厂外铁路产品质量信息表-导入模板.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }
}
