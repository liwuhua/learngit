package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS51;
import com.yjdj.view.core.util.ExcelExportUtil51;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yjdj.view.core.web.util.dwz.AjaxObject;


/**
 * Created by yangzhijie on 17/01/04.
 */
@Controller
@RequestMapping("/crrc/reportFS51")
public class ReportControllerFS51{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS01.class);
	@Autowired
	private IReportServiceFS51 iReportServiceFS51;

    @Autowired
    private GenericService genericService;

    private static final String FS51 = "crrc/reportPageFS51";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS51() {
        return FS51;
    }


    /**
     * 产品型号
     */
    @RequestMapping(value = "/productModel", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getProductModel(String id) {
    	
    		List<String> result = null;
			try {
				result = iReportServiceFS51.getCpxh(id);
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("FS51 Cpxh+++++++++++"+result);
            return result;
        
    }

    /**
     * 根据产品型号返回表单json
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public String getReport(@RequestBody QueryPojo queryPojo){
        try {
            Object resultJson = iReportServiceFS51.getReportJson(queryPojo);
            logger.info("FS51 resultJson+++++++++++"+resultJson);
            return (String)resultJson;
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
    
    
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        

        QueryPojo queryPojo = new QueryPojo();

        String productModel = request.getParameter("productModel");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if ("".equals(productModel)) {
        	queryPojo.setProductModel(null);
        } else {
        	queryPojo.setProductModel(Lists.newArrayList(productModel.split(",")));
        }

        queryPojo.setExport(isExport);
        
        Object resultJson = null;
        
        try {
        	resultJson = iReportServiceFS51.getReportJson(queryPojo);
            if(queryPojo.isExport()){
            	ExcelExportUtil51 result = (ExcelExportUtil51) resultJson;
            	result.write(request,response, "电机型号检索故障信息表.xlsx");
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

        return null;
    }

}
