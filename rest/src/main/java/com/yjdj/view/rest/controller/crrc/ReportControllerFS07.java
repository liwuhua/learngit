package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS07;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
* Created by zhaomenglu on 2016/12/12.
*/

@Controller
@RequestMapping("/crrc/reportFS07")
public class ReportControllerFS07 {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS07.class);
    @Autowired
    private IReportServiceFS07 iReportServiceFS07;

    @Autowired
    private GenericService genericService;

    private static final String FS07 = "crrc/reportPageFS07";
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    private String tableName = "THM_FS07_05";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS07() {
        return FS07;
    }

    /**
     * 公司代码  权限字段
     * @return
     */
    @RequestMapping(value = "/tcompcode", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTcompCode(HttpServletRequest request){
        @SuppressWarnings("unchecked")
        List<String> codeList = (List<String>) request.getSession().getAttribute("FS07-"+ CommonConstant.COMP_CODE);
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
     * 客户多值区间
     * @param id
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/tcustomer", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTcustomerParam(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                                        @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        try {
            return genericService.getAllTcustomer(tableName, id, name);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 根据查询条件返回表单json
     * @param queryBean
     * @return
     * @throws IOException
     */
    /*
     * pas:应收账款统计分析表
     *
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean,HttpServletRequest request) {
        //根据权限取公司代码
        List<String> compCodeList = (List<String>) request.getSession().getAttribute("FS07-"+ CommonConstant.COMP_CODE);
        if(CollectionUtils.isEmpty(compCodeList)){
            compCodeList=new ArrayList<>();
        }

        Object resultJson = null;
        try {
            resultJson = iReportServiceFS07.selectDataTrue(queryBean,compCodeList);
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
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultJson.toString();
    }
//download！
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //根据权限取公司代码
        List<String> compCodeList = (List<String>) request.getSession().getAttribute("FS07-"+ CommonConstant.COMP_CODE);
        if(CollectionUtils.isEmpty(compCodeList)){
            compCodeList=new ArrayList<>();
        }

        QueryBean queryBean = new QueryBean();
        String compcodeValue = request.getParameter("compCodeValue");//公司代码多值
        String kunnrValue = request.getParameter("kunnrValue");//客户多值
        String dateYearMonthStart = request.getParameter("dateYearMonthStart");
        String dateYearMonthEnd = request.getParameter("dateYearMonthEnd");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if (StringUtils.isBlank(compcodeValue)) {
            queryBean.setCompCodeValue(null);
        } else {
            queryBean.setCompCodeValue(Lists.newArrayList(compcodeValue.split(",")));
        }
        if (StringUtils.isBlank(kunnrValue)) {
            queryBean.setKunnrValue(null);
        } else {
            queryBean.setKunnrValue(Lists.newArrayList(kunnrValue.split(",")));
        }
        queryBean.setExport(isExport);
        queryBean.setDateYearMonthStart(dateYearMonthStart);
        queryBean.setDateYearMonthEnd(dateYearMonthEnd);
        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS07.selectDataTrue(queryBean,compCodeList);
            if (resultJson != null && queryBean.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "应收账款统计分析.xlsx");
            }
        }
        catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        } catch (ParseException e) {
            e.printStackTrace();
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
        List<String> compCodeList = (List<String>) request.getSession().getAttribute("FS07-"+ CommonConstant.COMP_CODE);

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
            resultJson = iReportServiceFS07.importData(filePath,compCodeList);
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

