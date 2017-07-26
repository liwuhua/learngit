package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS37;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by zhangwenguo on 2017/03/28
 */
@Controller
@RequestMapping("/crrc/reportFS37")
public class ReportControllerFS37{

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS37.class);

    @Autowired
    private IReportServiceFS37 iReportServiceFS37;
    @Autowired
    private GenericService genericService;
    private static final String FS37 = "crrc/reportPageFS37";
    
    @RequestMapping(method = RequestMethod.GET)
    public String reportFS37() {
        return FS37;
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
        List<String> list = (List<String>) request.getSession().getAttribute("FS37-" + CommonConstant.PLANT);
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
     * 根据查询条件返回表单json
     * @param queryBean
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean){
    	String matnr = null;
    	String werks = null;
    	if(queryBean.getMatnrValue() != null){
    		matnr = queryBean.getMatnrValue().get(0);
    	}
    	if(queryBean.getPlantValue() != null){
    		werks = queryBean.getPlantValue().get(0);
    	}
        try {
            Object resultJson = iReportServiceFS37.getReportJson(matnr,werks);
            logger.info("resultJson+++++++++++" + resultJson);
            return (String)resultJson;
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
    }
    
}