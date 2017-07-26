package com.yjdj.view.rest.controller.crrc;

import com.yjdj.view.core.service.IReportServiceFS25;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by wangkai on 2016/11/9.
 */
@Controller
@RequestMapping("/crrc/reportFS25")
public class ReportControllerFS25 {
    @Autowired
    private IReportServiceFS25 iReportServiceFS25;

    /**
     * 获取fs09结果集
     * @param aufnr
     * @return
     */
    @RequestMapping(value = "/result", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestParam(value = "aufnr", required = false, defaultValue = "") String aufnr){
        try {
            String resultJson = iReportServiceFS25.getListFs25(aufnr);
            return resultJson;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "{}";
    }
}
