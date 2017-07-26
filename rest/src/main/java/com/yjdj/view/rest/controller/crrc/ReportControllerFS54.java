package com.yjdj.view.rest.controller.crrc;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yjdj.view.core.service.IReportServiceFS54;
import com.yjdj.view.core.util.ExcelExportUtil54;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by yangzhijie on 17/3/30.
 */
@Controller
@RequestMapping("/crrc/reportFS54")
public class ReportControllerFS54 extends CrrcGenericController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS01.class);
    @Autowired
    private IReportServiceFS54 iReportServiceFS54;
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    private static final String FS54 = "crrc/reportPageFS54";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS15() {
        return FS54;
    }
    
    
    /**
     * 产品型号
     */
    @RequestMapping(value = "/cpxh", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getCpxh(String id) {
    	
    		List<String> result = null;
			try {
				result = iReportServiceFS54.getCpxh(id);
			} catch (DataAccessException | InvalidFormatException
					| InstantiationException | IllegalAccessException
					| IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("FS54 Cpxh+++++++++++"+result);
            return result;
        
    }
    
    /**
     * 配件名称
     */
    @RequestMapping(value = "/aname", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getAname(String id) {
    	
    		List<String> result = null;
			try {
				result = iReportServiceFS54.getAname(id);
			} catch (DataAccessException | InvalidFormatException
					| InstantiationException | IllegalAccessException
					| IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("FS54 Aname+++++++++++"+result);
            return result;
        
    }
    
    /**
     * 服务站
     */
    @RequestMapping(value = "/fwz", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getFwz(String id, String name) {
    	
    		List<Map<String,String>> result = null;
    		ObjectMapper mapper = new ObjectMapper();
    		String json = null;
			try {
				result = iReportServiceFS54.selectFwz(id,name);
				json = mapper.writeValueAsString(result);
			} catch (DataAccessException | InvalidFormatException
					| InstantiationException | IllegalAccessException
					| IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("FS54 FWZ+++++++++++"+result);
            return json;
        
    }
    
    /**
     * 服务站
     */
    @RequestMapping(value = "/fwd", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getFwd(String id, String name) {
    	
    	List<Map<String,String>> result = null;
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
			try {
				result = iReportServiceFS54.selectFwd(id,name);
				json = mapper.writeValueAsString(result);
			} catch (DataAccessException | InvalidFormatException
					| InstantiationException | IllegalAccessException
					| IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("FS54 FWD+++++++++++"+result);
            return json;
        
    }
    
    /**
     * 车型
     */
    @RequestMapping(value = "/cx", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getCx(String id, String name) {
    	
    	List<Map<String,String>> result = null;
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
			try {
				result = iReportServiceFS54.selectCx(id,name);
				json = mapper.writeValueAsString(result);
			} catch (DataAccessException | InvalidFormatException
					| InstantiationException | IllegalAccessException
					| IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("FS54 Cx+++++++++++"+result);
            return json;
        
    }

    /**
     * 根据查询条件返回表单json
     *
     * @param queryBean
     * @return
     */

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean) {
        try {
            Object resultJson = iReportServiceFS54.getReportJson(queryBean);
            return (String) resultJson;
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
     * 导出数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        QueryBean queryBean = new QueryBean();
        
		String cpxh = request.getParameter("cpxh");
        String aname = request.getParameter("aname");
        String matnrValue = request.getParameter("matnrValue");//物料多值
    	String matnrInterval = request.getParameter("matnrInterval");//物料区间    	
        String trainmodel = request.getParameter("trainmodel");
        String fwz = request.getParameter("fwz");
        String fwd = request.getParameter("fwd");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        String yearMonth = request.getParameter("dateYearMonth");
        
        if("".equals(cpxh)){
    		queryBean.setCpxh(null);
    	}else{
    		queryBean.setCpxh(Lists.newArrayList(cpxh.split(",")));
    	}
        if("".equals(aname)){
    		queryBean.setAname(null);
    	}else{
    		queryBean.setAname(Lists.newArrayList(aname.split(",")));
    	}
        if("".equals(matnrValue)){
    		queryBean.setMatnrValue(null);
    	}else{
    		queryBean.setMatnrValue(Lists.newArrayList(matnrValue.split(",")));
    	}
    	if ("".equals(matnrInterval)) {
            queryBean.setMatnrInterval(null);
        } else {
            queryBean.setMatnrInterval(Lists.newArrayList(matnrInterval.split(",")));
        }
    	if("".equals(trainmodel)){
    		queryBean.setTrainmodel(null);
    	}else{
    		queryBean.setTrainmodel(Lists.newArrayList(trainmodel.split(",")));
    	}
        if("".equals(fwz)){
    		queryBean.setFwz(null);
    	}else{
    		queryBean.setFwz(Lists.newArrayList(fwz.split(",")));
    	}
        if("".equals(fwd)){
    		queryBean.setFwd(null);
    	}else{
    		queryBean.setFwd(Lists.newArrayList(fwd.split(",")));
    	}
        queryBean.setExport(isExport);
        if("".equals(yearMonth)){
    		queryBean.setDateYearMonth(null);
    	}else{
    		queryBean.setDateYearMonth(yearMonth);
    	}        

        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS54.getReportJson(queryBean);
            if (resultJson != null && queryBean.isExport()) {
                ExcelExportUtil54 result = (ExcelExportUtil54) resultJson;
                result.write(request, response, "备品配件统计表.xlsx");
            }
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
            resultJson = iReportServiceFS54.importData(filePath);
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
