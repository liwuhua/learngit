package com.yjdj.view.rest.controller.crrc;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.service.IReportServiceFS52;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yjdj.view.core.web.util.dwz.AjaxObject;


/**
 * Created by yangzhijie on 17/01/17.
 */
@Controller
@RequestMapping("/crrc/reportFS52")
public class ReportControllerFS52{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS01.class);
	@Autowired
	private IReportServiceFS52 iReportServiceFS52;

    private static final String FS52 = "crrc/reportPageFS52";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS52() {
        return FS52;
    }



    /**
     * 返回表单json
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public String getReport(){
        try {
            Object resultJson = iReportServiceFS52.getReportJson();
            logger.info("FS52 resultJson+++++++++++"+resultJson);
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
