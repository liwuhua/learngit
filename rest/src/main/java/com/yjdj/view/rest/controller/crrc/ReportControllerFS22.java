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
import com.yjdj.view.core.service.IReportServiceFS12;
import com.yjdj.view.core.service.IReportServiceFS22;
import com.yjdj.view.core.util.ExcelExportUtil22;
import com.yjdj.view.core.util.ExcelExportUtil24;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by lichengli on 2016年11月28日.
 */

@Controller
@RequestMapping("/crrc/reportFS22")
public class ReportControllerFS22 {
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS24.class);
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
	@Autowired
	private IReportServiceFS22 ireportservicefs22;
	
   @Autowired
    private GenericService genericService;
	   
    private static final String FS22 = "crrc/reportPageFS22";

    
    
    @RequestMapping(method = RequestMethod.GET)
    public String reportFS00() {
        return FS22;
    }
    
    /**
     * 公司代码  权限字段 
     * @return
     */																					  
    @RequestMapping(value = "/tcompcode", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTcompCode(HttpServletRequest request){
    	 @SuppressWarnings("unchecked")
		List<String> codeList = (List<String>) request.getSession().getAttribute("FS22-"+ CommonConstant.COMP_CODE);
       if (CollectionUtils.isNotEmpty(codeList)) {
           try { 
        	//查询相应的公司代码 描述
           	return genericService.getListTcompCodeJson(codeList);
           }catch (JsonProcessingException e) {   
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
     * 内部订单模糊查询
     * @param aufnr 模糊查询时输入的订单号
     * @return
     */
    @RequestMapping(value = "/innerOrder", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getInnerOrder(@RequestParam(value = "id", required = false, defaultValue = "") String aufnr){
    
        return ireportservicefs22.getInnerOrder(aufnr);
       

    }
    
    
    
    /**
     * 获取fs22 一级明细结果集
     * 传入的参数有
     * 分页的页数offset
     * 每页显示的数量rowCount
     * 公司代码 compCodeValue 多值
     * 查询区间 ，dateYearMonthStart,dateYearMonthEnd
     * 订单号，orderID 多值
     * 订单类型 dauatValue 单选， 默认Z002
     *
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result01", method = RequestMethod.POST)
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean){
        try {
            String resultJson = ireportservicefs22.getListFs22(queryBean);
            return resultJson;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 获取fs22 二级明细结果集,查询条件的 QueryBean 和一级类型一致，
     * 先查一级类型，一级类型查完后，根据一级类型的数据，逐条查询对应订单下的二级明细
     * @return
     */
    
    @RequestMapping(value = "/reportDetail", method = RequestMethod.POST)
    @ResponseBody
    public String getReportDetail(@RequestBody QueryBean queryBean){
        try {
            String resultJson = ireportservicefs22.getListFsSecondDetail22(queryBean);
            return resultJson;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //导出功能
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryBean queryBean = new QueryBean();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String compValues = request.getParameter("compValues");
        String exportflag = request.getParameter("exportflag");
        String orderID = request.getParameter("orderID"); 
        
        
       if (compValues==null||"".equals(compValues)) {
            queryBean.setCompCodeValue(null);;
        } else {
            queryBean.setCompCodeValue(Lists.newArrayList(compValues.split(",")));
        }
       
       if (orderID==null||"".equals(orderID)) {
           queryBean.setOrderID(null);;
       } else {
           queryBean.setOrderID(Lists.newArrayList(orderID.split(",")));
       }
        queryBean.setDateYearMonthStart(startTime);
        queryBean.setDateYearMonthEnd(endTime);
        
        try {
                ExcelExportUtil22 result = ireportservicefs22.exportExcel(queryBean,Integer.valueOf(exportflag));
                result.write(request, response, "研发费用明细表.xlsx");
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }
    
    /**
     * 导入数据 Upload single file using Spring Controller
     * @param request
     * @param response
     * @param resultJson
     * @return 
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "text/plain")
    public @ResponseBody
    String upload(MultipartHttpServletRequest request, HttpServletResponse response, Object resultJson) {
        Iterator<String> itr = request.getFileNames();
        String filePath = null;
        MultipartFile mpf = null;
        List<String> compCodeList = (List<String>) request.getSession().getAttribute("FS22-"+ CommonConstant.COMP_CODE);
        
        if(compCodeList==null||compCodeList.size()==0){
        	return "ERRORCOMPANEY, 查询有误,没有该公司的查询权限";
        }
        
        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());
            logger.info(mpf.getOriginalFilename() + " uploaded! " + files.size());
            // 获得在tomcat中项目的路径， 需要在web.xml配置ft.webapp
            String webRootPath = System.getProperty("ft.webapp");
            File dir = new File(webRootPath + File.separator + "uploadFiles");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File serverFile = new File(dir.getAbsolutePath() + File.separator + mpf.getOriginalFilename());
            if (serverFile.exists()) {
                serverFile.delete();
                serverFile = new File(dir.getAbsolutePath() + File.separator + mpf.getOriginalFilename());
            }
            try {
                fileMeta = new FileMeta();
                fileMeta.setBytes(mpf.getBytes());
                FileCopyUtils.copy(mpf.getBytes(), serverFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            filePath = serverFile.toString();
            filePath = filePath.replace("\\", "//");
        }
        try {
            logger.info("filePath++++++++++++" + filePath);
            resultJson = ireportservicefs22.importData(filePath,compCodeList);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("写入数据库异常" + e.getMessage());
            return AjaxObject.newError("写入数据库异常").setCallbackType("").toString();
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        } catch (InvalidFormatException e) {
            // TODO Auto-generated catch block
            logger.error("格式错误异常" + e.getMessage());
            return AjaxObject.newError("格式错误异常").setCallbackType("").toString();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block 
            logger.error("实例化异常" + e.getMessage());
            return AjaxObject.newError("实例化异常").setCallbackType("").toString();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            logger.error("反射异常" + e.getMessage());
            return AjaxObject.newError("反射异常").setCallbackType("").toString();
        }
        return (String) resultJson;
    }
	
    
    @RequestMapping(value = "/resultSum01", method = RequestMethod.POST)
    @ResponseBody
    public String getReportSum1(@RequestBody QueryBean queryBean){
            String resultJson = ireportservicefs22.returnSum(queryBean, 1);
            return resultJson;
    }
    
    @RequestMapping(value = "/resultSum02", method = RequestMethod.POST)
    @ResponseBody
    public String getReportSum2(@RequestBody QueryBean queryBean){
    	String resultJson = ireportservicefs22.returnSum(queryBean,2);
        return resultJson;
    }
}
