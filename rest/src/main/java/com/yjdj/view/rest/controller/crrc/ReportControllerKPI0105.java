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
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by lichengli on 2016年11月28日.
 */

@Controller
@RequestMapping("/crrc/kpi0105")
public class ReportControllerKPI0105 {
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerKPI0105.class);
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
	@Autowired
	private IReportServiceKPI0105 ireportservicekpi0105;
	
   @Autowired
    private GenericService genericService;
	   
    private static final String kpi0105 = "/crrc/kpi0105";

    
    
    @RequestMapping(method = RequestMethod.GET)
    public String reportFS00() {
        return kpi0105;
    }
    
    
    /**
     *  营业收入金额（当年累计） 和   净利润金额（当年累计）
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getYearAll", method = RequestMethod.POST)
    @ResponseBody
    public String getYearAll(@RequestBody QueryBean queryBean){
        try {
            String resultJson = ireportservicekpi0105.getYearAll(queryBean);
            return resultJson;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     *  营业收入金额（当年累计） 和   净利润金额（当年累计）
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getYearAllBukrs", method = RequestMethod.POST)
    @ResponseBody
    public String getYearAllBukrs(@RequestBody QueryBean queryBean){
        try {
            String resultJson = ireportservicekpi0105.getYearAllBukrs(queryBean);
            return resultJson;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     *  应收账款，月度统计
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getDueTo", method = RequestMethod.POST)
    @ResponseBody
    public String getDueTo(@RequestBody QueryBean queryBean){
            String resultJson = ireportservicekpi0105.getDueTo(queryBean);
            return resultJson;
    }
    
    /**
     *  指定月份下， 按照公司类别 分组，一级下钻
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getFirstType", method = RequestMethod.POST)
    @ResponseBody
    public String getFirstBukrsType(@RequestBody QueryBean queryBean){
            String resultJson = ireportservicekpi0105.getFirstType(queryBean);
            return resultJson;
    }
    
    
    /**
     *  指定月份下，指定公司类别，二级下钻
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getSecondBukrsType", method = RequestMethod.POST)
    @ResponseBody
    public String getSecondBukrsType(@RequestBody QueryBean queryBean){
            String resultJson = ireportservicekpi0105.getSecondBukrsType(queryBean);
            return resultJson;
    }
    
    
    
    
    /**
     *  指定月份下， 指定客户类型客户类别，二级下钻
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getSecondCustType", method = RequestMethod.POST)
    @ResponseBody
    public String getSecondCustType(@RequestBody QueryBean queryBean){
            String resultJson = ireportservicekpi0105.getSecondCustType(queryBean);
            return resultJson;
    }
    
    
    
    
    
}
