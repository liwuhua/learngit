package com.yjdj.view.rest.controller.crrc;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.service.IReportServiceFS20;
import com.yjdj.view.core.service.impl.ReportServiceImpFS20;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by liwuhua on 2016年10月21日.
 */
@Controller
@RequestMapping("/crrc/reportFS20")
public class ReportControllerFS20 {

    @Autowired
    private IReportServiceFS20 ireportservicefs20;
    private final Logger log = Logger.getLogger(ReportServiceImpFS20.class);

    /**
     * 获取fs20结果集
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/result", method = {RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestParam(value = "matnr", required = true) String matnr,
            @RequestParam(value = "kdauf", required = true) String kdauf,
            @RequestParam(value = "kdpos", required = true) String kdpos,
            @RequestParam(value = "aufnr", required = true) String aufnr) {
        String resultJson;
        try { 
            resultJson = ireportservicefs20.getFs20(kdauf,kdpos,matnr,aufnr);
            return resultJson;
        } catch (DataAccessException e) {
            log.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (JsonProcessingException e) {
            log.error("Json数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json数据封装异常").setCallbackType("").toString();
        } catch (InterruptedException e) {
            log.error("特殊异常" + e.getMessage());
            return AjaxObject.newError("特殊异常").setCallbackType("").toString();
        }

    }

}
