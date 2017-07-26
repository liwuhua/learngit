package com.yjdj.view.rest.controller.crrc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import com.yjdj.view.core.service.IReportServiceFS12;
import com.yjdj.view.core.service.impl.ReportServiceImpFS12;
import com.yjdj.view.core.util.ExcelExportUtil12;
import com.yjdj.view.core.web.util.dwz.AjaxObject;


/**
 * Created by liwuhua on 2016年10月21日.
 */

@Controller
@RequestMapping("/crrc/reportFS12")
public class ReportControllerFS12 {
	
	@Autowired
	private IReportServiceFS12 ireportservicefs12;
	
   @Autowired
    private GenericService genericService;
    private static final String FS12 = "crrc/reportPageFS12";
    private final Logger log = Logger.getLogger(ReportServiceImpFS12.class);
    
    
    @RequestMapping(method = RequestMethod.GET)
    public String reportFS00() {
        return FS12;
    }
    
    /**
     * 公司代码  权限字段 
     * @return
     */																					  
    @RequestMapping(value = "/tcompcode", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTcompCode(HttpServletRequest request){
    	 @SuppressWarnings("unchecked")
		List<String> codeList = (List<String>) request.getSession().getAttribute("FS12-"+ CommonConstant.COMP_CODE);
       if (CollectionUtils.isNotEmpty(codeList)) {
           try { 
        	   //查询相应的公司代码 描述
           	return genericService.getListTcompCodeJson(codeList);
           }catch (JsonProcessingException e) {   
               log.error("Json 数据封装异常" + e.getMessage());
               return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
           } catch (DataAccessException e) {
               log.error("数据查询异常" + e.getMessage());
               return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
           }
       }
       return AjaxObject.newError("没有分配权限").setCallbackType("").toString();

    }
    
   
    /**
     * 获取fs12结果集
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean,HttpServletRequest request){
   	    //根据权限取公司代码
    	List<String> compCodeList = (List<String>) request.getSession().getAttribute("FS12-"+ CommonConstant.COMP_CODE);
    	if(compCodeList==null){
    		compCodeList=new ArrayList<>();
    	}
    	String resultJson;
			try {
				resultJson = (String)ireportservicefs12.getListFs12(queryBean,compCodeList);
				return resultJson;
			} catch (DataAccessException e) {
				log.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				log.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			}
    }
    
    
    /**
     * 导出期间费用明细表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public void downloadFile1(HttpServletRequest request, HttpServletResponse response) {
   	    //根据权限取公司代码
    	List<String> compCodeList = (List<String>) request.getSession().getAttribute("FS12-"+ CommonConstant.COMP_CODE);
    	if(compCodeList==null){
    		compCodeList = new ArrayList<>();
    	}
    	  
        //年月是必选    如不选公司代码则显示5*公司代码下数据
        QueryBean queryBean = new QueryBean();
        if(request.getParameter("compCodeValue")==""){
        	queryBean.setCompCodeValue(null);
        }else {
        	queryBean.setCompCodeValue(Lists.newArrayList(request.getParameter("compCodeValue").split(",")));
		}
        queryBean.setStartTime(request.getParameter("startTime"));
        queryBean.setEndTime(request.getParameter("endTime"));
        queryBean.setExport(Boolean.valueOf(request.getParameter("isExport")));

        try {
            ExcelExportUtil12 ee = ireportservicefs12.getexportExcel(queryBean,compCodeList);
            ee.write(request, response, "期间费用明细表.xlsx");

        }catch (JsonProcessingException e) {
            log.error("Json 数据封装异常" + e.getMessage());
        } catch (DataAccessException e) {
            log.error("数据查询异常" + e.getMessage());
        }catch (IOException e) {
            log.error("IO 操作异常" + e.getMessage());
        }
    }
    
    
    /**
     * 导入数据 Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "text/plain")
    public @ResponseBody
    String upload(MultipartHttpServletRequest request, HttpServletResponse response, Object resultJson) {
   	    //权限内的所有公司代码
    	List<String> compCodeList = (List<String>) request.getSession().getAttribute("FS12-"+ CommonConstant.COMP_CODE);
        if(compCodeList==null){
        	compCodeList=new ArrayList<>();
        }  
    	
        Iterator<String> itr = request.getFileNames();
        LinkedList<FileMeta> files = new LinkedList<FileMeta>();
        String filePath = null;
        MultipartFile mpf = null;
        FileMeta fileMeta = null;

        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());
            log.info(mpf.getOriginalFilename() + " uploaded! " + files.size());
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
            resultJson = ireportservicefs12.importData(filePath,compCodeList);
        } catch (JsonProcessingException e) {
            log.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            log.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (IOException e) {
            log.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        } catch (InvalidFormatException e) {
            // TODO Auto-generated catch block
            log.error("格式错误异常" + e.getMessage());
            return AjaxObject.newError("格式错误异常").setCallbackType("").toString();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block 
            log.error("实例化异常" + e.getMessage());
            return AjaxObject.newError("实例化异常").setCallbackType("").toString();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            log.error("反射异常" + e.getMessage());
            return AjaxObject.newError("反射异常").setCallbackType("").toString();
        }

        if (resultJson != null || !"".equals(resultJson)) {
            return (String) resultJson;
        } else {
            return (String) resultJson;
        }
    }  
	   
}
