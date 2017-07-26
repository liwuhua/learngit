package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.service.IReportServiceFS27;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by wangkai on 2016/11/9.
 */
@Controller
@RequestMapping("/crrc/reportFS27")
public class ReportControllerFS27 {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS27.class);

    @Autowired
    private IReportServiceFS27 iReportServiceFS27;

    /**
     * 获取fs09结果集
     * @param aufnr
     * @return
     */
    @RequestMapping(value = "/result", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestParam(value = "aufnr", required = false, defaultValue = "") String aufnr,
                            @RequestParam(value = "matnr", required = false, defaultValue = "") String matnr){
        try {
            String resultJson = iReportServiceFS27.getListFs27(aufnr,matnr);
            return resultJson;
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
        return "{}";
    }
}
