package com.yjdj.view.rest.controller.crrc;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS05;
import com.yjdj.view.core.util.ExcelExportUtil05;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by zhangwenguo on 2016/11/1.
 */
@Controller
@RequestMapping("/crrc/reportFS05")
public class ReportControllerFS05{

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS05.class);
    @Autowired
    private IReportServiceFS05 iReportServiceFS05;
    @Autowired
    private GenericService genericService;
    private static final String FS05 = "crrc/reportPageFS05";
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    
    @RequestMapping(method = RequestMethod.GET)
    public String reportFS05() {
        return FS05;
    }

    /**
     * 根据查询条件返回表单json
     * @param queryBean
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/result", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean, HttpServletRequest request){
    	List<String> compCodeCheck = (List<String>) request.getSession().getAttribute("FS05-"+ CommonConstant.COMP_CODE);
        try {
            Object resultJson = iReportServiceFS05.getReportJson(queryBean, compCodeCheck);
            return (String)resultJson;
        } catch (IOException e) {
        	logger.error("数据返回IO异常!\n"+ e.getMessage());
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
    	 List<String> codeList = (List<String>) request.getSession().getAttribute("FS05-"+ CommonConstant.COMP_CODE);
       if (CollectionUtils.isNotEmpty(codeList)) {
           try { 
        	//查询相应的公司代码 描述
           	return genericService.getListTcompCodeJson_Oversea(codeList);
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
    
    /**
     * 导出数据
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request,HttpServletResponse response) throws IOException{
    	List<String> compCodeCheck = (List<String>) request.getSession().getAttribute("FS05-"+ CommonConstant.COMP_CODE);
    	QueryBean queryBean = new QueryBean();
    	String compCodeValue = request.getParameter("compCodeValue");
    	String startTime = request.getParameter("startTime");
    	String endTime = request.getParameter("endTime");
    	Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
    	if("".equals(compCodeValue)){
    		queryBean.setCompCodeValue(null);
    	}else{
    		queryBean.setCompCodeValue(Lists.newArrayList(compCodeValue.split(",")));
    	}
    	
    	queryBean.setExport(isExport);
    	queryBean.setStartTime(startTime);
    	queryBean.setEndTime(endTime);
    	Object resultJson = null;
    	try {
        	//isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS05.getReportJson(queryBean, compCodeCheck);
            if(resultJson != null && queryBean.isExport()){
            	ExcelExportUtil05 result = (ExcelExportUtil05) resultJson;
            	result.write(request,response, "应上交应弥补款项报表.xlsx");
        	}
        } catch (IOException e) {
        	logger.error("导出失败!" +e.getMessage());
        	throw e;
        }
    	 return null;
    }
    
    /**
     * 导入数据 Upload single file using Spring Controller
     * @param request
     * @param response
     * @param resultJson
     * @return 
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "text/plain")
    public @ResponseBody
    String upload(MultipartHttpServletRequest request, HttpServletResponse response, Object resultJson) {
    	List<String> compCodeCheck = (List<String>) request.getSession().getAttribute("FS05-"+ CommonConstant.COMP_CODE);
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
			resultJson = iReportServiceFS05.importData(filePath,compCodeCheck);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("写入数据库异常" + e.getMessage());
            return AjaxObject.newError("写入数据库异常").setCallbackType("").toString();
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
        return (String) resultJson;
    }
    
}
