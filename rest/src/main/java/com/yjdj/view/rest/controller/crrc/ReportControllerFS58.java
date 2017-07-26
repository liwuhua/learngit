package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS58;
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
 * Created by sunwan on 2017/4/24.
 */

@Controller
@RequestMapping("/crrc/reportFS58")
public class ReportControllerFS58 {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS58.class);
    @Autowired
    public IReportServiceFS58 iReportServiceFS58;
    @Autowired
    private GenericService genericService;

    private static final String FS58 = "crrc/reportPageFS58";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS58() {
        return FS58;
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
        List<String> list = (List<String>) request.getSession().getAttribute("FS58-" + CommonConstant.PLANT);
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
     * 获取电机型号 /crrc/reportFS58/groes
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/groes", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getGroes(@RequestParam(value = "id", required = false, defaultValue = "") String id) {
    	
        try {
            String json = iReportServiceFS58.getGroesJson(id);
            return json;
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
    /**
     * 维修级别 /crrc/reportFS58/zlevel
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/zlevel", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getZlevel(@RequestParam(value = "id", required = false, defaultValue = "") String id) {
    	
        try {
            String json = iReportServiceFS58.getZlevelJson(id);
            return json;
		}catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 配件物料编码 /crrc/reportFS58/pjwlbm
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/pjwlbm", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getPjwlbm(@RequestParam(value = "id", required = false, defaultValue = "") String id,
    		@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
            @RequestParam(value = "limit", required = false, defaultValue = "15") Integer limit) {
    	
        try {
            String json = iReportServiceFS58.getPjwlbmJson(id, name, start, limit);
            return json;
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
            resultJson = iReportServiceFS58.selectData(queryBean);
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
        String werksValue = request.getParameter("werksValue");
        String groesValue = request.getParameter("groesValue");
        String zlevelValue = request.getParameter("zlevelValue");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String matnrValue = request.getParameter("matnrValue");
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
        if ("".equals(werksValue)) {
            queryBean.setWerksValue(null);
        } else {
            queryBean.setWerksValue(Lists.newArrayList(werksValue.split(",")));
        }
        if ("".equals(groesValue)) {
            queryBean.setGroesValue(null);
        } else {
            queryBean.setGroesValue(Lists.newArrayList(groesValue.split(",")));
        }
        if ("".equals(zlevelValue)) {
            queryBean.setZlevelValue(null);
        } else {
            queryBean.setZlevelValue(Lists.newArrayList(zlevelValue.split(",")));
        }
        queryBean.setStartTime(startTime);
        queryBean.setEndTime(endTime);
        if ("".equals(matnrValue)) {
            queryBean.setMatnrValue(null);
        } else {
            queryBean.setMatnrValue(Lists.newArrayList(matnrValue.split(",")));
        }
        queryBean.setExport(isExport);

        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = iReportServiceFS58.selectData(queryBean);
            if (resultJson != null && queryBean.isExport()) {
                ExcelExportUtil result = (ExcelExportUtil) resultJson;
                result.write(request, response, "检修电机（产品）偶换件偶换率统计表.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }
}
