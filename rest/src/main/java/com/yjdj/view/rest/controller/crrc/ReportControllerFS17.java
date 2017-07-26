package com.yjdj.view.rest.controller.crrc;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import com.yjdj.view.core.service.IReportServiceFS17;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by yangzhijie on 16/11/21.
 */
@Controller
@RequestMapping("/crrc/reportFS17")
public class ReportControllerFS17 extends CrrcGenericController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS17.class);
    @Autowired
    private IReportServiceFS17 iReportServiceFS17;
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    private static final String FS17 = "crrc/reportPageFS17";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS17() {
        return FS17;
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
            Object resultJson = iReportServiceFS17.getReportJson(queryBean);
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
        String dateYearMonth = request.getParameter("dateYearMonth");
        String compCodeValue = request.getParameter("compCodeValue");
        queryBean.getDateYearMonth();
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        queryBean.setExport(isExport);
        queryBean.setDateYearMonth(dateYearMonth);
        queryBean.setCompCodeValue(Lists.newArrayList(compCodeValue.split(",")));

        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS17.getReportJson(queryBean);
            if (resultJson != null && queryBean.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "合并现金流量表.xlsx");
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
            resultJson = iReportServiceFS17.importData(filePath);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
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

        if (resultJson != null || !"".equals(resultJson)) {
            return (String) resultJson;
        } else {
            return (String) resultJson;
        }
    }
}
