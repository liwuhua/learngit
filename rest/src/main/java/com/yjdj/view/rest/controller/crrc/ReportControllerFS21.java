package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS21;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengxuan on 2016年10月21日.
 */

@Controller
@RequestMapping("/crrc/reportFS21")
public class ReportControllerFS21 {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS21.class);
	@Autowired
	private IReportServiceFS21 ireportservicefs21;
	@Autowired
	private GenericService genericService;

	private String tableName = "THM_FS21_02";
	private static final String FS21 = "crrc/reportPageFS21";

	@RequestMapping(method = RequestMethod.GET)
	public String reportFS01() {
		return FS21;
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
		List<String> list = (List<String>) request.getSession().getAttribute("FS21-" + CommonConstant.PLANT);
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

//	@RequestMapping(value = "/tpurgroup", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
//	@ResponseBody
//	public String getListTpurGroupParam(HttpServletRequest request,
//										@RequestParam(value = "id", required = false, defaultValue = "") String id,
//										@RequestParam(value = "name", required = false, defaultValue = "") String name) {
//		try {
////			List<String> list = (List<String>) request.getSession().getAttribute("FS21-" + CommonConstant.PUR_GROUP);
////			if (CollectionUtils.isNotEmpty(list)) {
////				return genericService.getAllTpurGroup(tableName, id, name,list);
////			}
////			return AjaxObject.newError("没有分配权限").setCallbackType("").toString();
//			return genericService.getAllTpurGroup(tableName, id, name);
//		} catch (JsonProcessingException e) {
//			logger.error("Json 数据封装异常" + e.getMessage());
//			return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
//		} catch (DataAccessException e) {
//			logger.error("数据查询异常" + e.getMessage());
//			return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
//		}
//	}


	/**
	 * 采购组
	 *
	 * @param id
	 * @param name
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/tpurgroup", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String getTpurGroup(HttpServletRequest request,
							   @RequestParam(value = "id", required = false, defaultValue = "") String id,
							   @RequestParam(value = "name", required = false, defaultValue = "") String name,
                               @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                               @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
		try {
			return genericService.getAllTpurGroup(tableName, id, name,start,limit);
		} catch (JsonProcessingException e) {
			logger.error("Json 数据封装异常" + e.getMessage());
			return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
		} catch (DataAccessException e) {
			logger.error("数据查询异常" + e.getMessage());
			return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
		}
	}

    	/**
	 * MRP控制者
	 * @param id
	 * @param name
     * @return
     */
	@RequestMapping(value = "/mrpctl", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String getListMrpctl(@RequestParam(value = "id", required = false, defaultValue = "") String id,
								   @RequestParam(value = "name", required = false, defaultValue = "") String name){
		try {
			return genericService.getListMrpctl(id,name);
		} catch (JsonProcessingException e) {
			logger.error("Json 数据封装异常" + e.getMessage());
			return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
		} catch (DataAccessException e) {
			logger.error("数据查询异常" + e.getMessage());
			return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
		}
	}


//	/**
//	 * 创建者
//	 * @param id
//	 * @param name
//     * @return
//     */
//	@RequestMapping(value = "/createuser", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
//	@ResponseBody
//	public String getListCreatUser(@RequestParam(value = "id", required = false, defaultValue = "") String id,
//								   @RequestParam(value = "name", required = false, defaultValue = "") String name){
//		try {
//			return genericService.getListPerson(id,name);
//		} catch (JsonProcessingException e) {
//			logger.error("Json 数据封装异常" + e.getMessage());
//			return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
//		} catch (DataAccessException e) {
//			logger.error("数据查询异常" + e.getMessage());
//			return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
//		}
//	}

	/**
	 * 更改者
	 * @param id
	 * @param name
     * @return
     */
//	@RequestMapping(value = "/updateuser", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
//	@ResponseBody
//	public String getListUpdateUser(@RequestParam(value = "id", required = false, defaultValue = "") String id,
//								   @RequestParam(value = "name", required = false, defaultValue = "") String name){
//		try {
//			return genericService.getListPerson(id,name);
//		} catch (JsonProcessingException e) {
//			logger.error("Json 数据封装异常" + e.getMessage());
//			return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
//		} catch (DataAccessException e) {
//			logger.error("数据查询异常" + e.getMessage());
//			return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
//		}
//	}



	/**
	 * 根据查询条件返回表单json
	 * @param queryBean
	 * @return
	 */
	@RequestMapping(value = "/result", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getReport(@RequestBody QueryBean queryBean){
		try {
			String resultJson = ireportservicefs21.getDataFs21(queryBean).toString();
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
		}
	}

	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
	public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//工厂权限
		List<String> list = (List<String>) request.getSession().getAttribute("FS21-" + CommonConstant.PLANT);
		if(CollectionUtils.isEmpty(list)){
			list = new ArrayList<String>();
		}
		QueryBean queryBean = new QueryBean();
		//工厂
		String plantValue = request.getParameter("plantValue");
		if (StringUtils.isBlank(plantValue)) {
			queryBean.setPlantValue(null);
		} else {
			queryBean.setPlantValue(Lists.newArrayList(plantValue.split(",")));
		}
		//物料编码
		String matnrValue = request.getParameter("matnrValue");
		if ("".equals(matnrValue)) {
			queryBean.setMatnrValue(null);
		} else {
			queryBean.setMatnrValue(Lists.newArrayList(matnrValue.split(",")));
		}
		String matnrInterval = request.getParameter("matnrInterval");
		if ("".equals(matnrInterval)) {
			queryBean.setMatnrInterval(null);
		} else {
			queryBean.setMatnrInterval(Lists.newArrayList(matnrInterval.split(",")));
		}
		//采购组
		String purGroupValue = request.getParameter("purGroupValue");
		if ("".equals(purGroupValue)) {
			queryBean.setPurGroupValue(null);
		} else {
			queryBean.setPurGroupValue(Lists.newArrayList(purGroupValue.split(",")));
		}
		//创建者
		String oneValue = request.getParameter("oneValue");
		if ("".equals(oneValue)) {
			queryBean.setOneValue(null);
		} else {
			queryBean.setOneValue(Lists.newArrayList(oneValue.split(",")));
		}
		//更改者
		String twoValue = request.getParameter("twoValue");
		if("".equals(twoValue)){
			queryBean.setTwoValue(null);
		}else{
			queryBean.setTwoValue(Lists.<String>newArrayList(twoValue.split(",")));
		}
		//创建日期
		String startTime = request.getParameter("startTime");
		if("".equals(startTime)){
			queryBean.setStartTime(null);
		}else{
			queryBean.setStartTime(startTime);
		}
		String endTime = request.getParameter("endTime");
		if("".equals(endTime)){
			queryBean.setEndTime(null);
		}else{
			queryBean.setEndTime(endTime);
		}
		//更改日期
		String startTimeTwo = request.getParameter("endTime");
		if("".equals(startTimeTwo)){
			queryBean.setStartTimeTwo(null);
		}else{
			queryBean.setStartTimeTwo(startTimeTwo);
		}
		String endTimeTwo = request.getParameter("endTimeTwo");
		if("".equals(endTimeTwo)){
			queryBean.setEndTimeTwo(null);
		}else{
			queryBean.setEndTimeTwo(endTimeTwo);
		}
		//下载
		Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));
		queryBean.setExport(isExport);

		queryBean.setStartitem(null);
		queryBean.setPageitem(null);

		Object resultJson = null;
		try {
			//isExport 为true则是导出数据，为false为查询数据
			resultJson = ireportservicefs21.getDataFs21(queryBean);
			if (resultJson != null && queryBean.isExport()) {
				ExcelExportUtil result = (ExcelExportUtil) resultJson;
				result.write(request, response, "排产异常物料联查统计分析表.xlsx");
			}
		} catch (IOException e) {
			logger.error("IO 操作异常" + e.getMessage());
			return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
		}

		return null;
	}
	
}
