package com.yjdj.view.rest.controller.crrc;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS22;
import com.yjdj.view.core.service.IReportServiceKPI0105;
import com.yjdj.view.core.service.IReportServiceKPI1516;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by lichengli on 2016年11月28日.
 */

@Controller
@RequestMapping("/crrc/kpi1516")
public class ReportControllerKPI1516 {
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerKPI1516.class);
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
	@Autowired
	private IReportServiceKPI1516 ireportservicekpi1516;
	
   @Autowired
    private GenericService genericService;
	   
    private static final String kpi1516 = "/crrc/kpi1516";

    
    
    @RequestMapping(method = RequestMethod.GET)
    public String reportFS00() {
        return kpi1516;
    }
    

    
    
    /**
     *  订单在产金额,所得月份在产资金周转天数
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/ziChanAndzhouZhuan", method = RequestMethod.POST)
    @ResponseBody
    public String getMTMandOIPM(@RequestParam(value = "time", required = false, defaultValue = "") String time){
            String resultJson = ireportservicekpi1516.getMTMandOIPM(time);
            return resultJson;
    }
    
    
    /**
     *  
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/ziChanAndChejian", method = RequestMethod.POST)
    @ResponseBody
    public String getFirstDetail(@RequestParam(value = "time", required = false, defaultValue = "") String time){
            String resultJson = ireportservicekpi1516.getFirstDetail(time);
            return resultJson;
    }
    
    /**
     *  原材料车间仓评估类下钻
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/cheJianCangValClass", method = RequestMethod.POST)
    @ResponseBody
    public String cheJianCangValClass(@RequestParam(value = "time", required = false, defaultValue = "") String time){
            String resultJson = ireportservicekpi1516.getVClassShopDetail(time);
            return resultJson;
    }
    
    
    /**
     *  订单类别二级下钻
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/orderTypeSecondDetail", method = RequestMethod.POST)
    @ResponseBody
    public String orderTypeSecondDetail(@RequestParam(value = "time", required = false, defaultValue = "") String time){
            String resultJson = ireportservicekpi1516.getSecondWorkShopDetail(time);
            return resultJson;
    }
    
    

    
    
    
    
}
