package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.service.IReportServiceFS46;
import com.yjdj.view.core.service.impl.ReportServiceImpFS46;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
 * Created by zhuxuan on 2016/12/28.
 */
@Controller
@RequestMapping("/crrc/reportFS46")
public class ReportControllerFS46 extends BaseController {
    @Autowired
    private IReportServiceFS46 iReportServiceFS46;
    private final Logger logger = Logger.getLogger(ReportServiceImpFS46.class);
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    private static final String FS46 = "crrc/reportPageFS46";
    private static final String EXCELFILENAME = "车型检索配属情况表.xlsx";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS46() {
        return FS46;
    }

    /**
     * 获取所有可选的车型
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/trainmodel", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getAllTrainModel(String id) throws JsonProcessingException{
        List<String> resultJson = null;
        try {
            resultJson = iReportServiceFS46.getAllTrianModel(id);
        } catch (DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultJson;
    }
    /**
     * 根据查询条件返回表单json
     * @param queryPojo
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getReport(@RequestBody QueryPojo queryPojo){
        try {
            Object resultJson = iReportServiceFS46.getReportJson(queryPojo);
            return (String)resultJson;
        } catch (IOException e) {
            logger.error("数据返回IO异常!\n"+ e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出数据
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryPojo queryPojo = new QueryPojo();
        String trainmodel = request.getParameter("trainmodel");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if ("".equals(trainmodel)) {
            List<String> NULL = Lists.newArrayList();
            queryPojo.setTrainmodel(NULL);
        } else {
            queryPojo.setTrainmodel(Lists.newArrayList(trainmodel.split(",")));
        }
        queryPojo.setExport(isExport);
        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS46.getReportJson(queryPojo);
            if (resultJson != null && queryPojo.isExport()) {
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
  /*  @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "text/plain")
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
            resultJson = iReportServiceFS46.importData(filePath);
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
*/
}
