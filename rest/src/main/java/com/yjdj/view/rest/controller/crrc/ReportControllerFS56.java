package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.IReportServiceFS56;
import com.yjdj.view.core.service.impl.ReportServiceImpFS56;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhuxuan on 2017/2/20.
 */
@Controller
@RequestMapping("/crrc/reportFS56")
public class ReportControllerFS56 extends BaseController{
    @Autowired
    private IReportServiceFS56 iReportServiceFS56;
    private final Logger logger = Logger.getLogger(ReportServiceImpFS56.class);
    private static final String FS56 = "crrc/reportPageFS56";
    private static final String EXCELFILENAME = "检修电机数据统计表.xlsx";
    FileMeta fileMeta = null;
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS56() {
        return FS56;
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
        QueryBean queryBean = new QueryBean();
        String motormodel = request.getParameter("motormodel");
        queryBean.setMotormodel(motormodel);
        Object resultJson = null;
        int action = 0;//action = 0 为导出模板
        try {
            resultJson = iReportServiceFS56.getReportJson(queryBean,action);
            if (resultJson != null) {
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
     * 根据查询条件返回表单json
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean){
        int action = 1;
        try {
            String resultJson = (String)iReportServiceFS56.getReportJson(queryBean,action);
            return resultJson;
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
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryBean queryBean = new QueryBean();
        String motormodel = request.getParameter("motormodel");
        queryBean.setMotormodel(motormodel);
        int action = 2;
        try {
            ExcelExportUtil result = (ExcelExportUtil)iReportServiceFS56.getReportJson(queryBean,action);
            result.write(request, response, EXCELFILENAME);
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }

    /**
     * 导入数据
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "text/plain" )
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
        int flag = 1;//0为上传模板，1为上传数据
        try {
            logger.info("filePath++++++++++++" + filePath);
            resultJson = iReportServiceFS56.importData(filePath,flag);
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

    /**
     * 导入模板
     */
    @RequestMapping(value = "/uploadTemplate", method = RequestMethod.POST, produces = "text/plain" )
    public @ResponseBody
    String uploadTemplate(MultipartHttpServletRequest request, HttpServletResponse response, Object resultJson) {
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
        int flag = 0;//0为上传模板，1为上传数据
        try {
            logger.info("filePath++++++++++++" + filePath);
            resultJson = iReportServiceFS56.importData(filePath,flag);//0为上传模板，1为上传数据
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

    /**
     * 用户查询条件，电机型号
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/motormodel", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> getAllMotorModel(String id) throws JsonProcessingException{
        List<String> resultJson = null;
        try {
            resultJson = iReportServiceFS56.getMotorModel(id);
        } catch (DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultJson;
    }
}
