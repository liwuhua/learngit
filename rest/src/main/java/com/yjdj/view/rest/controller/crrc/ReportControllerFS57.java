package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS57;
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
 * Created by sunwan on 2017/3/2.
 */

@Controller
@RequestMapping("/crrc/reportFS57")
public class ReportControllerFS57 {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS57.class);
    @Autowired
    public IReportServiceFS57 iReportServiceFS57;
    @Autowired
    private GenericService genericService;

    private String tableName = "THM_FS57";

    private static final String FS57 = "crrc/reportPageFS57";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS57() {
        return FS57;
    }


    /**
     * 获取销售组织权限列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tsalesorg", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getTSalesOrg(HttpServletRequest request) {
        List<String> list = (List<String>) request.getSession().getAttribute("FS57-"+ CommonConstant.SALESORG);
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
     * 获取工厂权限列表
     * @param list
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
//    public String getTplantJson(List<String> list) throws JsonProcessingException,DataAccessException;




    /**
     * 销售组织多值
     * @param id
     * @param name
     * @return
     */
//    @RequestMapping(value = "/tsalesorgparam", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
//    @ResponseBody
//    public String getListTsalesOrgParam(@RequestParam(value = "id", required = false, defaultValue = "") String id,
//                                        @RequestParam(value = "name", required = false, defaultValue = "") String name) {
//        try {
//            return genericService.getAllTsalesOrg(tableName, id, name);
//        } catch (JsonProcessingException e) {
//            logger.error("Json 数据封装异常" + e.getMessage());
//            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
//        } catch (DataAccessException e) {
//            logger.error("数据查询异常" + e.getMessage());
//            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
//        }
//    }

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
        List<String> list = (List<String>) request.getSession().getAttribute("FS57-" + CommonConstant.PLANT);
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
     * 客户多值
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/tcustomer", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTcustomerParam(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                                        @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        try {
            return iReportServiceFS57.getAllTcustomer(tableName, id, name);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

//查询数据
    @RequestMapping(value = "/result", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean){
        Object resultJson = null;
        try {
            resultJson = iReportServiceFS57.selectData(queryBean);
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


    //导出功能
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryBean queryBean = new QueryBean();
        String vkorgValue = request.getParameter("vkorgValue");
        String werksValue = request.getParameter("werksValue");
        String kunnrValue = request.getParameter("kunnrValue");
        String dateYearMonth = request.getParameter("dateYearMonth");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if ("".equals(vkorgValue)) {
            queryBean.setVkorgValue(null);
        } else {
            queryBean.setVkorgValue(Lists.newArrayList(vkorgValue.split(",")));
        }
        if ("".equals(werksValue)) {
            queryBean.setWerksValue(null);
        } else {
            queryBean.setWerksValue(Lists.newArrayList(werksValue.split(",")));
        }
        if ("".equals(kunnrValue)) {
            queryBean.setKunnrValue(null);
        } else {
            queryBean.setKunnrValue(Lists.newArrayList(kunnrValue.split(",")));
        }
        queryBean.setDateYearMonth(dateYearMonth);
        queryBean.setExport(isExport);

        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS57.selectData(queryBean);
            if (resultJson != null && queryBean.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "检修统计表.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }


}
