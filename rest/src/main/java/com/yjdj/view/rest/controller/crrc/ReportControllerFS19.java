package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS19;
import com.yjdj.view.core.service.impl.ReportServiceImpFS19;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
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
 * Created by zhuxuan on 2016/11/1.
 */
@Controller
@RequestMapping("/crrc/reportFS19")
public class ReportControllerFS19{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS19.class);
    @Autowired
    private IReportServiceFS19 iReportServiceFS19;
    @Autowired
    private GenericService genericService;
    private final Logger log = Logger.getLogger(ReportServiceImpFS19.class);
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    private static final String FS19 = "crrc/reportPageFS19";
    private static final String EXCELFILENAME = "货币情况明细表.xlsx";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS19() {
        return FS19;
    }

    /**
     * 权限控制字段 获取所有可选择的公司代码和名称
     * @return
     */
    @RequestMapping(value = "/tcompcode", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTcompCode(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<String> codeList = (List<String>) request.getSession().getAttribute("FS19-" + CommonConstant.COMP_CODE);
        if (CollectionUtils.isNotEmpty(codeList)) {
            try {
                //查询相应的公司代码 描述
                return genericService.getListTcompCodeJson_Oversea(codeList);
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
     * 根据查询条件返回表单json
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean, HttpServletRequest request){
        //根据权限取公司代码
        List<String> compCodeCheck = (List<String>) request.getSession().getAttribute("FS19-"+ CommonConstant.COMP_CODE);
        if(compCodeCheck == null){
            compCodeCheck = new ArrayList<>();
        }else {
            if(compCodeCheck.contains("5026"))compCodeCheck.remove(compCodeCheck.indexOf("5026"));
            if(compCodeCheck.contains("5500"))compCodeCheck.remove(compCodeCheck.indexOf("5500"));
        }
        Object resultJson = null;
        try {
            resultJson = iReportServiceFS19.getReportJson(queryBean, compCodeCheck);
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

    //报表导出功能
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //根据权限取公司代码
        List<String> compCodeCheck = (List<String>) request.getSession().getAttribute("FS19-"+ CommonConstant.COMP_CODE);
        if(compCodeCheck == null){
            compCodeCheck = new ArrayList<>();
        }
        QueryBean queryBean = new QueryBean();
        String compCodeValue = request.getParameter("compCodeValue");
        String dateYearMonth = request.getParameter("dateYearMonth");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if ("".equals(compCodeValue)) {
            List<String> NULL = Lists.newArrayList();
            queryBean.setCompCodeValue(NULL);
        } else {
            queryBean.setCompCodeValue(Lists.newArrayList(compCodeValue.split(",")));
        }
        queryBean.setDateYearMonth(dateYearMonth);
        queryBean.setExport(isExport);
        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS19.getReportJson(queryBean,compCodeCheck);
            if (resultJson != null && queryBean.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, EXCELFILENAME);
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
        List<String> compCodeCheck = (List<String>) request.getSession().getAttribute("FS19-"+ CommonConstant.COMP_CODE);
        if(compCodeCheck==null){
            compCodeCheck=new ArrayList<>();
        }
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
            resultJson = iReportServiceFS19.importData(filePath,compCodeCheck);
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

        return (String) resultJson;
    }
}
