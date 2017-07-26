package com.yjdj.view.rest.controller.crrc;

import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.IReportServiceFS00;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by wangkai on 16/10/19.
 */
@Controller
@RequestMapping("/crrc/reportFS00")
public class ReportControllerFS00 {
    @Autowired
    private IReportServiceFS00 iReportServiceFS00;

    private static final String FS00 = "crrc/reportPageFS00";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS00() {
        return FS00;
    }

//    /**
//     * 根据查询条件返回表单json
//     * @param reportJson
//     * @return
//     */
//    @RequestMapping(value = "/result", method = RequestMethod.POST)
//    @ResponseBody
//    public String getReport(@RequestParam(value = "reportJson", required = true, defaultValue = "") String reportJson){
//        try {
//            String resultJson = iReportServiceFS00.getReportJson(reportJson);
//            return resultJson;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    /**
     * 根据查询条件返回表单json
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean){
        System.err.println("---------------"+queryBean.toString());
        try {
            String resultJson = iReportServiceFS00.getReportJson(queryBean);
            return resultJson;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
