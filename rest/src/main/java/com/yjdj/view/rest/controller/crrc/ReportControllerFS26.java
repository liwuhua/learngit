package com.yjdj.view.rest.controller.crrc;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
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
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS26;
import com.yjdj.view.core.util.ExcelExportUtil24;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by liuchengli on 2016年12月13日.
 */

@Controller
@RequestMapping("/crrc/reportFS26")
public class ReportControllerFS26 {
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS26.class);
	@Autowired
	private IReportServiceFS26 ireportservicefs26;
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
   @Autowired
    private GenericService genericService;
	   
    private static final String FS26 = "crrc/reportPageFS26";

    
    
    @RequestMapping(method = RequestMethod.GET)
    public String reportFS00() {
        return FS26;
    }
    
    
    /**
     * 公司代码  权限字段 
     * @return
     */																					  
    @RequestMapping(value = "/tcompcode", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTcompCode(HttpServletRequest request){
    	 @SuppressWarnings("unchecked")
		List<String> codeList = (List<String>) request.getSession().getAttribute("FS26-"+ CommonConstant.COMP_CODE);
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
    
    
    
    
    
    /**
     * 获取fs26 一级明细结果集
     * 传入的参数有
     * 公司代码 compCodeValue 多值
     * 查询区间 ，dateYearMonthStart,dateYearMonthEnd
     *
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result01", method = RequestMethod.POST)
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean){
            String resultJson = ireportservicefs26.returnFinalResult(queryBean, 1);
            return resultJson;
        
    }
    
    /**
     * 获取fs26
     * 
     * @return
     */
    
    @RequestMapping(value = "/report02", method = RequestMethod.POST)
    @ResponseBody
    public String getReportDetail(@RequestBody QueryBean queryBean){
            String resultJson = ireportservicefs26.returnFinalResult(queryBean, 2);
            return resultJson;
    }
    
  //导出功能
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryBean queryBean = new QueryBean();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String compValues = request.getParameter("compValues");
        String exportflag = request.getParameter("exportflag");
        
       if (compValues==null||"".equals(compValues)) {
            queryBean.setCompCodeValue(null);;
        } else {
            queryBean.setCompCodeValue(Lists.newArrayList(compValues.split(",")));
        }
        queryBean.setDateYearMonthStart(startTime);
        queryBean.setDateYearMonthEnd(endTime);
        
        try {
                ExcelExportUtil24 result = ireportservicefs26.exportExcel(queryBean,Integer.valueOf(exportflag));
                result.write(request, response, "销售费用统计分析表.xlsx");
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
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
        Iterator<String> itr = request.getFileNames();
        String filePath = null;
        MultipartFile mpf = null;
        List<String> compCodeList = (List<String>) request.getSession().getAttribute("FS24-"+ CommonConstant.COMP_CODE);
        if(compCodeList==null||compCodeList.size()==0){
        	return "ERRORCOMPANEY, 查询有误,没有该公司的查询权限";
        }
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
            resultJson = ireportservicefs26.importData(filePath,compCodeList);
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
