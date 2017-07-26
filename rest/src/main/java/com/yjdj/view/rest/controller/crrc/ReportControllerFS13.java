package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS13;
import com.yjdj.view.core.service.IReportServiceFS15;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
 * Created by wangkai on 17/02/10.
 */
@Controller
@RequestMapping("/crrc/reportFS13")
public class ReportControllerFS13 {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS13.class);

    @Autowired
    private GenericService genericService;

    @Autowired
    private IReportServiceFS13 iReportServiceFS13;

    private static final String FS13 = "crrc/reportPageFS13";

    private String tableName = "THM_STOCK_06";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS01() {
        return FS13;
    }
    /**
     * 获取权限字段tplant
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tplant", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getTplant(HttpServletRequest request) {
        List<String> list = (List<String>) request.getSession().getAttribute("FS13-" + CommonConstant.PLANT);
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                String json = genericService.getTplantJson(list);
                return json;
            } catch (JsonProcessingException e) {
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
     * 供应商编码
     *
     * @param id
     * @param name
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/tvendor", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTvendorParam(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                                      @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                      @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        try {
            return genericService.getAllTvendor(tableName, id, name,start,limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 管库员
     *
     * @param lgpbe
     * @return
     */
    @RequestMapping(value = "/lgpbe", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getVbeln(
            @RequestParam(value = "id", required = false, defaultValue = "") String lgpbe) {
        try {
            return (String) iReportServiceFS13.getLgpbe(lgpbe);
        }catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (NullPointerException e){
            return "{}";
        }
    }

    /**
     * 获取fs13结果集
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean){
        String resultJson;
        try {
            resultJson = (String)iReportServiceFS13.getData(queryBean);
            if(StringUtils.isNotBlank(resultJson) && !"null".equals(resultJson)){
                return resultJson;
            }else {
                return "{}";
            }
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        }
    }

}
