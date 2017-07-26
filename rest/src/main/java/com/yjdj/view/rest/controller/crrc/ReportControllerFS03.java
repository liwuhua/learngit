package com.yjdj.view.rest.controller.crrc;

import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.IReportServiceFS03;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.StringUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by zhaomenglu  on 2016/12/14.
 */
@Controller
@RequestMapping("/crrc/reportFS03")
public class ReportControllerFS03 {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS03.class);
    @Autowired
    private IReportServiceFS03 iReportServiceFS03;

    @Autowired
    private GenericService genericService;
    private static final String FS03 = "crrc/reportPageFS03";
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    private String tableName = "THM_MSEG_03";
    private QueryBean queryBean;


    @RequestMapping(method = RequestMethod.GET)
    public String reportFS03() {
        return FS03;
    }

    /**
     * 工厂多值
     *
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/tplant", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListProducePlant(HttpServletRequest request) {
        List<String> list = (List<String>) request.getSession().getAttribute("FS03-" + CommonConstant.PLANT);
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
     * 物料多值多区间
     *
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
     * 采购组多值
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tpurgroup", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTpurGroup(HttpServletRequest request,
                                   @RequestParam(value = "id", required = false, defaultValue = "") String id,
                                   @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        List<String> list = (List<String>)request.getSession().getAttribute("FS03-"+ CommonConstant.PUR_GROUP);
        if(CollectionUtils.isNotEmpty(list)){
            try {
                String json = genericService.getAllTpurGroup(tableName, id, name,list);
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
     * 根据查询条件返回表单json
     *
     * @param queryBean
     * @return
     * @throws IOException
     */
    /*
     * pas:采购成本变动金额统计表
     *
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean) {
        this.queryBean = queryBean;
        Object resultJson = null;
        try {
            resultJson = iReportServiceFS03.selectDataTrue(queryBean);
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
    //download！
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryBean queryBean = new QueryBean();
        String plantValue = request.getParameter("plantValue");//工厂多值
        String matnrValue = request.getParameter("matnrValue");//物料编码多值
        String vendorValue = request.getParameter("vendorValue");//供应商多值
        String purgroupValue = request.getParameter("purgroupValue");//采购组多值
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if (StringUtil.isEmpty(plantValue)) {
            queryBean.setPlantValue(null);
        } else {
            queryBean.setPlantValue(Lists.newArrayList(plantValue.split(",")));
        }
        if (StringUtil.isEmpty(matnrValue)) {
            queryBean.setMatnrValue(null);
        } else {
            queryBean.setMatnrValue(Lists.newArrayList(matnrValue.split(",")));
        }
        if (StringUtil.isEmpty(vendorValue)) {
            queryBean.setVendorValue(null);
        } else {
            queryBean.setVendorValue(Lists.newArrayList(vendorValue.split(",")));
        }
        if (StringUtil.isEmpty(purgroupValue)) {
            queryBean.setPurGroupValue(null);
        } else {
            queryBean.setPurGroupValue(Lists.newArrayList(purgroupValue.split(",")));
        }
        queryBean.setExport(isExport);
        queryBean.setStartTime(startTime);
        queryBean.setEndTime(endTime);
        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS03.selectDataTrue(queryBean);
            if (resultJson != null && queryBean.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "采购成本变动金额统计表.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }

}
