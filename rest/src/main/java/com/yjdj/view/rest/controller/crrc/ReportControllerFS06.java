package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS06;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by sunwan on 2016/11/7.
 */
@Controller
@RequestMapping("/crrc/reportFS06")
public class ReportControllerFS06 {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS06.class);
    @Autowired
    private IReportServiceFS06 iReportServiceFS06;

    @Autowired
    private GenericService genericService;

    private String tableName = "THM_VBAP_02";

    private static final String FS06 = "crrc/reportPageFS06";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS06() {
        return FS06;
    }

    /**
     * 获取权限字段tsalesorg
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tsalesorg", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getTSalesOrg(HttpServletRequest request) {
        List<String> list = (List<String>) request.getSession().getAttribute("FS06-"+ CommonConstant.SALESORG);
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                String json = genericService.getSalesOrgJson(list);
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
     * 物料多值多区间
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/tmaterial", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTmaterialParam(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                                      @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        try {
            return genericService.getAllTmaterial(tableName, id, name);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 客户
     * @param id
     * @param name
     * @return
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
     * 销售组织
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/tsalesorgparam", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTsalesOrgParam(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                                        @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        try {
            return genericService.getAllTsalesOrg(tableName, id, name);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 销售部门
     * @param request
     * @return
     */
    @RequestMapping(value = "/tsalesoff", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTsalesOffParam(HttpServletRequest request) {
        List<String> list = (List<String>) request.getSession().getAttribute("FS06-"+ CommonConstant.SALES_OFF);
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                String json = genericService.getSalesOffJson(list);
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
     * 获取fs02 销售订单
     *
     * @param vbeln
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/vbeln", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getVbeln(
            @RequestParam(value = "id", required = false, defaultValue = "") String vbeln,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "15") int limit) {
        try {
            return genericService.getVbeln(tableName, vbeln, start, limit);
        }catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 销售合同类型
     * @return
     */
    @RequestMapping(value = "/salesDocType", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getAllAuart(@RequestParam(value = "id", required = false, defaultValue = "") String doc_type,
                              @RequestParam(value = "name", required = false, defaultValue = "") String txtsh) {
        try {
            return genericService.getListSalesDoc(doc_type,txtsh);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
//合同执行情况
    @RequestMapping(value = "/result", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
     @ResponseBody
     public String getReport(@RequestBody QueryBean queryBean){
        Object resultJson = null;
        try {
            resultJson = iReportServiceFS06.selectData_01(queryBean);
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
//弹窗、交货明细
    @RequestMapping(value = "/result_sub", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getReport_sub(@RequestParam(value = "vbeln", required = true) String vbeln,
                                @RequestParam(value = "posnr", required = true) String posnr,
                                @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                 @RequestParam(value = "limit", required = false, defaultValue = "20") int limit){
        String resultJson2 = null;
        try {
            resultJson2 = iReportServiceFS06.selectData_02(vbeln,posnr,start,limit);
            logger.info("resultJson+++++++++++" + resultJson2);
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
        return resultJson2;
    }

//导出功能
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryBean queryBean = new QueryBean();
        String vbelnValue = request.getParameter("vbelnValue");
        String vkorgValue = request.getParameter("vkorgValue");
        String matnrValue = request.getParameter("matnrValue");
        String matnrInterval = request.getParameter("matnrInterval");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String kunnrValue = request.getParameter("kunnrValue");
        String auartValue = request.getParameter("auartValue");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if ("".equals(vbelnValue)) {
            queryBean.setVbelnValue(null);
        } else {
            queryBean.setVbelnValue(Lists.newArrayList(vbelnValue.split(",")));
        }
        if ("".equals(vkorgValue)) {
            queryBean.setVkorgValue(null);
        } else {
            queryBean.setVkorgValue(Lists.newArrayList(vkorgValue.split(",")));
        }
        if ("".equals(matnrValue)) {
            queryBean.setMatnrValue(null);
        } else {
            queryBean.setMatnrValue(Lists.newArrayList(matnrValue.split(",")));
        }
        if ("".equals(matnrInterval)) {
            queryBean.setMatnrInterval(null);
        } else {
            queryBean.setMatnrInterval(Lists.newArrayList(matnrInterval.split(",")));
        }
        queryBean.setStartTime(startTime);
        queryBean.setEndTime(endTime);
        if ("".equals(kunnrValue)) {
            queryBean.setKunnrValue(null);
        } else {
            queryBean.setKunnrValue(Lists.newArrayList(kunnrValue.split(",")));
        }
        if ("".equals(auartValue)) {
            queryBean.setAuartValue(null);
        } else {
            queryBean.setAuartValue(Lists.newArrayList(auartValue.split(",")));
        }
        queryBean.setExport(isExport);

        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS06.selectData_01(queryBean);
            if (resultJson != null && queryBean.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "合同执行情况统计表.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }

}
