package com.yjdj.view.rest.controller.crrc;

import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.IReportServiceFS08;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by wangkai on 16/10/26.
 */
@Controller
@RequestMapping("/crrc/reportFS08")
public class ReportControllerFS08 {
    @Autowired
    private IReportServiceFS08 iReportServiceFS08;

    private static final String FS08 = "crrc/reportPageFS08";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS08() {
        return FS08;
    }

    /**
     * 获取fs08结果集
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean){
//        System.err.println("---------------"+queryBean.toString());
//        System.err.println(queryBean.isShow());
        try {
            String resultJson = iReportServiceFS08.getListFs08(queryBean);
            return resultJson;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
