package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.service.IReportServiceFS46;
import com.yjdj.view.core.service.IReportServiceFS47;
import com.yjdj.view.core.service.impl.ReportServiceImpFS56;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhuxuan on 2017/3/29.
 */
@Controller
@RequestMapping("/crrc/reportFS47")
public class ReportControllerFS47 extends BaseController{
    @Autowired
    private IReportServiceFS47 iReportServiceFS47;
    private final Logger logger = Logger.getLogger(ReportServiceImpFS56.class);
    private static final String FS47 = "crrc/reportPageFS47";
    private static final String EXCELFILENAME = "配属表.xlsx";
    FileMeta fileMeta = null;
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS47() {
        return FS47;
    }

    /**
     * 导出模板
     * @param request
     * @param response
     * @return
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/downloadTemplate", method = RequestMethod.POST)
    public String downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryPojo queryPojo = new QueryPojo();
        String train_type = request.getParameter("train_type");
        queryPojo.setTrain_type(train_type);
        Object resultObj = null;
        int action = 0;//action = 0 为导出模板
        try {
            resultObj = iReportServiceFS47.getReportJson(queryPojo,action);
            if (resultObj != null) {
                ExcelExportUtil result = (ExcelExportUtil) resultObj;
                result.write(request, response, EXCELFILENAME);
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }

    /**
     * 导出数据
     * @param request
     * @param response
     * @return
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryPojo queryPojo = new QueryPojo();
        queryPojo.setTrain_type(request.getParameter("train_type"));
        queryPojo.setRepair_level(request.getParameter("repair_level").isEmpty() ? null : Lists.newArrayList(request.getParameter("repair_level").toString().split(",")));
        queryPojo.setTrain_num(request.getParameter("train_num").isEmpty() ? null : Lists.newArrayList(request.getParameter("train_num").toString().split(",")));
        queryPojo.setManufacturer(request.getParameter("manufacturer").isEmpty() ? null : Lists.newArrayList(request.getParameter("manufacturer").toString().split(",")));
        queryPojo.setBureau_subjection(request.getParameter("bureau_subjection").isEmpty() ? null : Lists.newArrayList(request.getParameter("bureau_subjection").toString().split(",")));
        queryPojo.setPlace_subjection(request.getParameter("place_subjection").isEmpty() ? null : Lists.newArrayList(request.getParameter("place_subjection").toString().split(",")));
        queryPojo.setMotor_serial_num(request.getParameter("motor_serial_num").isEmpty() ? null : Lists.newArrayList(request.getParameter("motor_serial_num").toString().split(",")));
        int action = 0;
        try {
            ExcelExportUtil result = (ExcelExportUtil)iReportServiceFS47.getReportJson(queryPojo,action);
            result.write(request, response, EXCELFILENAME);
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }

    /**
     * 根据查询条件返回表单json
     * @param queryPojo
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryPojo queryPojo){
        int action = 1;
        try {
            String resultJson = (String)iReportServiceFS47.getReportJson(queryPojo,action);
            return resultJson;
        } catch (IOException e) {
            logger.error("数据返回IO异常!\n"+ e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

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
            resultJson = iReportServiceFS47.importData(filePath);
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

    /* 查询条件 */

    /**
     * 获取所有可选的车型
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/train_type", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getAllTrainModel(String txt) throws JsonProcessingException{
        List<String> resultJson = null;
        try {
            resultJson = iReportServiceFS47.getListTrainModel(txt);
        } catch (org.springframework.dao.DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultJson;
    }

    @RequestMapping(value = "/train_num", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getAllTrainNum(String txt) throws JsonProcessingException{
        List<String> resultJson = null;
        try {
            resultJson = iReportServiceFS47.getAllTrainNum(txt);
        } catch (org.springframework.dao.DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultJson;
    }

    @RequestMapping(value = "/manufacturer", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getAllManufacturer(String txt) throws JsonProcessingException{
        List<String> resultJson = null;
        try {
            resultJson = iReportServiceFS47.getAllManufacturer(txt);
        } catch (org.springframework.dao.DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultJson;
    }

    @RequestMapping(value = "/bureau_subjection", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getAllBureau_subjection(String txt) throws JsonProcessingException{
        List<String> resultJson = null;
        try {
            resultJson = iReportServiceFS47.getAllBureau_subjection(txt);
        } catch (org.springframework.dao.DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultJson;
    }

    @RequestMapping(value = "/place_subjection", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getAllPlace_subjection(String txt) throws JsonProcessingException{
        List<String> resultJson = null;
        try {
            resultJson = iReportServiceFS47.getAllPlace_subjection(txt);
        } catch (org.springframework.dao.DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultJson;
    }

    @RequestMapping(value = "/repair_level", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getAllRepair_level(String txt) throws JsonProcessingException{
        List<String> resultJson = null;
        try {
            resultJson = iReportServiceFS47.getAllRepair_level(txt);
        } catch (org.springframework.dao.DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultJson;
    }

    @RequestMapping(value = "/motor_serial_num", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getAllMotor_serial_num(String txt) throws JsonProcessingException{
        List<String> resultJson = null;
        try {
            resultJson = iReportServiceFS47.getAllMotor_serial_num(txt);
        } catch (org.springframework.dao.DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultJson;
    }
}
