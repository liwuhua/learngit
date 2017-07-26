package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS01;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import org.springframework.dao.DataAccessException;

/**
 * Created by zhangwenguo on 16/10/20.
 */
@Controller
@RequestMapping("/crrc/reportFS01")
public class ReportControllerFS01{

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS01.class);
    @Autowired
    private IReportServiceFS01 iReportServiceFS01;

    @Autowired
    private GenericService genericService;

    private static final String FS01 = "crrc/reportPageFS01";
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    private String tableName = "THM_MSEG_03";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS01() {
        return FS01;
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
        List<String> list = (List<String>) request.getSession().getAttribute("FS01-" + CommonConstant.PLANT);
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

    @RequestMapping(value = "/tpurgroup", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTpurGroupParam(HttpServletRequest request,
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        try {
            List<String> list = (List<String>) request.getSession().getAttribute("FS01-" + CommonConstant.PUR_GROUP);
            if (CollectionUtils.isNotEmpty(list)) {
                return genericService.getAllTpurGroup(tableName, id, name,list);
            }
            return AjaxObject.newError("没有分配权限").setCallbackType("").toString();
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
     *
     * @param queryBean
     * @return
     * @throws IOException
     */
    /*
     * pas:采购入库金额统计简称(Purchase amount statistics) 
     * Purchase amount statistics
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean) {
        Object resultJson = null;
        try {
            resultJson = iReportServiceFS01.getReportJson(queryBean);
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

    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryBean queryBean = new QueryBean();
        String plantValue = request.getParameter("plantValue");
        String matnrValue = request.getParameter("matnrValue");
        String matnrInterval = request.getParameter("matnrInterval");
        String vendorValue = request.getParameter("vendorValue");
        String purGroupValue = request.getParameter("purGroupValue");
        Boolean isShow = Boolean.valueOf(request.getParameter("isShow"));
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if ("".equals(plantValue)) {
            queryBean.setPlantValue(null);
        } else {
            queryBean.setPlantValue(Lists.newArrayList(plantValue.split(",")));
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
        if ("".equals(vendorValue)) {
            queryBean.setVendorValue(null);
        } else {
            queryBean.setVendorValue(Lists.newArrayList(vendorValue.split(",")));
        }
        if ("".equals(purGroupValue)) {
            queryBean.setPurGroupValue(null);
        } else {
            queryBean.setPurGroupValue(Lists.newArrayList(purGroupValue.split(",")));
        }

        queryBean.setExport(isExport);
        queryBean.setStartTime(startTime);
        queryBean.setEndTime(endTime);
        queryBean.setShow(isShow);
        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS01.getReportJson(queryBean);
            if (resultJson != null && queryBean.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "采购入库金额统计.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }

}
