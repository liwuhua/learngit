package com.yjdj.view.rest.controller.crrc;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
import com.yjdj.view.core.entity.mybeans.FileMeta;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.QueryPojo2;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceKPI2227;
import com.yjdj.view.core.service.IThmVbap01Service;
import com.yjdj.view.core.service.impl.ReportServiceImpFS18;
import com.yjdj.view.core.util.ExcelExportUtil2227;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by liwuhua on 2016年11月18日.
 */

@Controller
@RequestMapping("/crrc/reportKPI2227")
public class ReportControllerKPI2227 {
	
	@Autowired
	private IReportServiceKPI2227 ireportservicekpi2227;
	
	private final Logger logger = Logger.getLogger(ReportServiceImpFS18.class);
	
//    private static final String KPI2227 = "crrc/reportPageKPI2227";
    private static final String FS5005 = "crrc/reportPageFS5005";
    private final String tableName = "THM_VBAP_04";
    private final String  projectTable = "THM_KPI2227_04";
    private final String  categoryTable = "THM_KPI2227_05";
    private final String  reasonTable = "THM_KPI2227_06";
  
    @RequestMapping(method = RequestMethod.GET)
    public String reportFS5005() {
        return FS5005;
    }
    
    
    /**
     * 质量损失 +营业收入的截止时间
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getDeadline", method = RequestMethod.POST )
    @ResponseBody
    public String getDeadline( ){
            String resultJson;
			try {
				resultJson = ireportservicekpi2227.getDeadline( );
				return resultJson;
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			} catch (IOException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
				return AjaxObject.newError("IO操作异常").setCallbackType("").toString();
			}
        }
    
    
    /**
     * 质量损失类别编号
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/losstypecode", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getLossTypeCode(  @RequestParam(value = "id", required = false, defaultValue = "") String name,
									@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
						            @RequestParam(value = "limit", required = false, defaultValue = "15") Integer limit) {
    	        try {
    	            return ireportservicekpi2227.getLossTypeCode(name,start,limit);
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
    
    
    /**
     * 质量问题发生单位
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/occurplant", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getOccurPlant(  @RequestParam(value = "id", required = false, defaultValue = "") String name,
									@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
						            @RequestParam(value = "limit", required = false, defaultValue = "15") Integer limit) {
    	        try {
    	            return ireportservicekpi2227.getOccurPlant(name,start,limit);
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
    
    
    /**
     * 责任单位
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/dutyplant", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getDutyPlant(  @RequestParam(value = "id", required = false, defaultValue = "") String name,
									@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
						            @RequestParam(value = "limit", required = false, defaultValue = "15") Integer limit) {
    	        try {
    	            return ireportservicekpi2227.getDutyPlant(name,start,limit);
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
    
    
    
    /**
     * 获取KPI2227 质量损失趋势
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getPlantAccount", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getPlantAccount(@RequestBody QueryBean queryBean){
          
    	  try {
				return ireportservicekpi2227.getPlantAccount(queryBean);
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			}
        }
    
    /**
     * 获取KPI2227  质量问题分布
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getQualiDetail", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getQualiDetail(@RequestBody QueryBean queryBean){
            String resultJson;
			try {
				resultJson = ireportservicekpi2227.getQualiDetail(queryBean);
				return resultJson;
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			}
        }
    
    
    /**
     * 获取KPI2227  各质量损失类别下钻至质量损失原因 
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getCauseByType", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getCauseByType(@RequestBody QueryBean queryBean){
            String resultJson;
			try {
				resultJson = ireportservicekpi2227.getCauseByType(queryBean);
				return resultJson;
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			}
        }
    
    /**
     * 获取KPI2227  质量损失原因下钻至责任单位
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getDutyunitByCause", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getDutyunitByCause(@RequestBody QueryBean queryBean){
            String resultJson;
			try {
				resultJson = ireportservicekpi2227.getDutyunitByCause(queryBean);
				return resultJson;
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			}
        }
    
    
    
    /**
     * 获取KPI2227  获取所有的项目类型
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getProjecttypes", method = RequestMethod.POST )
    @ResponseBody
    public String getProjecttypes( ){
            String resultJson;
			try {
				resultJson = ireportservicekpi2227.getProjecttypes( );
				return resultJson;
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			}
       }
    
    
    /**
     * 获取KPI2227结果集 产品故障统计 :  按型号统计(数量) + 按故障类型统计(台次)
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getFaultCount", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getFaultCount(@RequestBody QueryBean queryBean){
            String resultJson;
			try {
				resultJson = ireportservicekpi2227.getFaultCount(queryBean);
				return resultJson;
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			}
        }
    
    
    /**
     * 获取KPI2227结果集   产品故障统计   对具体型号下钻1层  故障类型  故障数量 
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getLayerByModel", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getLayerByModel(@RequestBody QueryBean queryBean){
            String resultJson;
			try {
				resultJson = ireportservicekpi2227.getLayerByModel(queryBean);
				return resultJson;
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			}
        }
    
    
    /**
     * 获取KPI2227结果集   产品故障统计  故障类型点击下钻展示格式   产品型号	故障数量
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/getLayerByType", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getLayerByType(@RequestBody QueryBean queryBean){
            String resultJson;
			try {
				resultJson = ireportservicekpi2227.getLayerByType(queryBean);
				return resultJson;
			} catch (DataAccessException e) {
				logger.error("数据查询异常" + e.getMessage());
	            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
			} catch (JsonProcessingException e) {
				logger.error("Json 数据封装异常" + e.getMessage());
	            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
			}
        }
    
    /**
     * 查询条件   项目类型
     * @throws IOException 
     * @throws IllegalAccessException 
     * @throws InstantiationException       
     */
    @RequestMapping(value = "/getProjecttype", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProjecttype(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                                        @RequestParam(value = "name", required = false, defaultValue = "") String name) {
    	 String resultJson;
    	try {
    		resultJson = ireportservicekpi2227.getCode(projectTable, id, name,1);
			return resultJson;
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
    
    
    
    /**
     * 查询条件   质量损失类别
     * @throws IOException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @RequestMapping(value = "/getCategorytype", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getCategorycode(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                                        @RequestParam(value = "name", required = false, defaultValue = "") String name) {
    	 String resultJson;
    	try {
    		resultJson = ireportservicekpi2227.getCode(categoryTable, id, name,2);
			return resultJson;
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
    
    /**
     * 查询条件   质量损失原因
     * @throws IOException 
     * @throws IllegalAccessException 
     * @throws InstantiationException  
     */
    @RequestMapping(value = "/getReasontype", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReasontype(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                                        @RequestParam(value = "name", required = false, defaultValue = "") String name) {
    	 String resultJson;
    	try {
    		resultJson = ireportservicekpi2227.getCode(reasonTable, id, name,3);
			return resultJson;
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
    
    
    
    /**
     * 导入数据 Upload single file using Spring Controller
     * @throws IOException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @RequestMapping(value = "/uploadFile", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain")
    public @ResponseBody
    String upload(MultipartHttpServletRequest request, HttpServletResponse response, Object resultJson) throws InstantiationException, IllegalAccessException, IOException {
        Iterator<String> itr = request.getFileNames();
        String filePath = null;
        MultipartFile mpf = null;
        LinkedList<FileMeta> files = new LinkedList<FileMeta>();
        FileMeta fileMeta = null;

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
            resultJson = ireportservicekpi2227.importData(filePath);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (InvalidFormatException e) {
            // TODO Auto-generated catch block
            logger.error("格式错误异常" + e.getMessage());
            return AjaxObject.newError("格式错误异常").setCallbackType("").toString();
        } 
        
        if (resultJson != null || !"".equals(resultJson)) {
            return (String) resultJson;
        } else {
            return (String) resultJson;
        }
    }
    
    
    /**
     * 获取fs50-05结果集
     * @param queryBean
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryPojo2 querypojo) throws IOException {
        try {
            String resultJson = (String) ireportservicekpi2227.getResult(querypojo);
            return resultJson;
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
        	logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
    
    
    
    /**
     * 导出厂内查询结果表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public String downloadFile_01(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	QueryPojo2 queryPojo = new QueryPojo2();
        String lbbh=request.getParameter("lbbh");//质量损失类别编号
        String lbdm=request.getParameter("lbdm");//质量损失类别
        String yydm=request.getParameter("yydm");//质量损失原因
        String xmlx=request.getParameter("xmlx");//项目类型
        String fsdw=request.getParameter("fsdw");//质量问题发生单位
        String zrdw = request.getParameter("zrdw");//责任单位
        String startTime = request.getParameter("starttime");//发现时间
        String endTime = request.getParameter("endtime");///发现时间
        Boolean isExport = Boolean.valueOf(request.getParameter("isExport"));

//        String productcode=request.getParameter("productCode");
//        String jnw = request.getParameter("jnw");
//        String xzjx=request.getParameter("xzjx");


        
        if ("".equals(lbbh)||lbbh==null) {
            queryPojo.setLbbh(null);
        } else {
            queryPojo.setLbbh(Lists.newArrayList(lbbh.split(",")));
        }
        
        if ("".equals(lbdm)||lbdm==null) {
            queryPojo.setLbbh(null);
        } else {
            queryPojo.setLbdm(Lists.newArrayList(lbbh.split(",")));
        }
        if ("".equals(yydm)||yydm==null) {
            queryPojo.setYydm(null);
        } else {
            queryPojo.setYydm(Lists.newArrayList(yydm.split(",")));
        }
        if ("".equals(fsdw)||fsdw==null) {
            queryPojo.setFsdw(null);
        } else {
            queryPojo.setFsdw(Lists.newArrayList(fsdw.split(",")));
        }
        
//        if ("".equals(productcode)||productcode==null) {
//            queryPojo.setProductCode(null);
//        } else {
//            queryPojo.setProductCode(Lists.newArrayList(productcode.split(",")));
//        }
        
        queryPojo.setStartTime(startTime);
        queryPojo.setEndTime(endTime);
        queryPojo.setExport(isExport);

        
        Object resultJson = null;
        try {
            //isExport 为true则是导出数据，为false为查询数据
            resultJson = ireportservicekpi2227.getResult(queryPojo);
            if (resultJson != null && queryPojo.isExport()) {
            	ExcelExportUtil2227 result = (ExcelExportUtil2227) resultJson;
                result.write(request, response, "厂内产品质量信息表.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }

    
    
    //模板导出
    @RequestMapping(value = "/downloadTemplate", method = {RequestMethod.GET,RequestMethod.POST})
    public String downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object resultJson = null;
        try {
            resultJson = ireportservicekpi2227.downloadTemplate();
            if (resultJson != null) {
            	ExcelExportUtil2227 result = (ExcelExportUtil2227) resultJson;
                result.write(request, response, "厂内产品质量信息表-导入模板.xlsx");
            }
        } catch (IOException e) {
            logger.error("IO 操作异常" + e.getMessage());
            return AjaxObject.newError("IO 操作异常").setCallbackType("").toString();
        }
        return null;
    }
    
    
    
    
    
}
