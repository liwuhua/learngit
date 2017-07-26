package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS05;
import com.yjdj.view.core.service.IReportServiceFS59;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelExportUtil05;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chengxuan on 2016/11/1.
 */
@Controller
@RequestMapping("/crrc/reportFS59")
public class ReportControllerFS59 {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS59.class);
    @Autowired
    private IReportServiceFS59 iReportServiceFS59;
    @Autowired
    private GenericService genericService;
    private static final String FS59 = "crrc/reportPageFS59";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS05() {
        return FS59;
    }

    /**
     * 根据查询条件返回表单json
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean,HttpServletRequest request){
        //根据权限取公司代码
        List<String> compCodeList = (List<String>) request.getSession().getAttribute("FS59-"+ CommonConstant.COMP_CODE);
        if(CollectionUtils.isEmpty(compCodeList)){
            compCodeList=new ArrayList<>();
        }
        try {
            Object resultJson = iReportServiceFS59.getFs59(queryBean,compCodeList);
            return (String)resultJson;
        } catch (Exception e) {
        	logger.error("数据返回IO异常!\n"+ e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //根据权限取公司代码
        List<String> compCodeList = (List<String>) request.getSession().getAttribute("FS59-"+ CommonConstant.COMP_CODE);
        if(CollectionUtils.isEmpty(compCodeList)){
            compCodeList=new ArrayList<>();
        }

        QueryBean queryBean = new QueryBean();

        String compCodeValue = request.getParameter("compCodeValue");
        String dateYearMonthStart = request.getParameter("dateYearMonthStart");
        String dateYearMonthEnd = request.getParameter("dateYearMonthEnd");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if ("".equals(compCodeValue)) {
            queryBean.setCompCodeValue(null);
        } else {
            queryBean.setCompCodeValue(Lists.newArrayList(compCodeValue.split(",")));
        }

        queryBean.setExport(isExport);
        queryBean.setDateYearMonthStart(dateYearMonthStart);
        queryBean.setDateYearMonthEnd(dateYearMonthEnd);

        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS59.getFs59(queryBean,compCodeList);
            if (resultJson != null && queryBean.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "投入产出表.xlsx");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 公司代码  权限字段 
     * @return
     */																					  
    @RequestMapping(value = "/tcompcode", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTcompCode(HttpServletRequest request){
    	 @SuppressWarnings("unchecked")
		List<String> codeList = (List<String>) request.getSession().getAttribute("FS59-"+ CommonConstant.COMP_CODE);
       if (CollectionUtils.isNotEmpty(codeList)) {
           try { 
        	//查询相应的公司代码 描述
           	return genericService.getListTcompCodeJson(codeList);
           }catch (JsonProcessingException e) {   
        	   logger.error("Json 数据封装异常" + e.getMessage());
               return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
           } catch (DataAccessException e) {
        	   logger.error("数据查询异常" + e.getMessage());
               return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
           }
       }
       return AjaxObject.newError("没有分配权限").setCallbackType("").toString();

    }
}
