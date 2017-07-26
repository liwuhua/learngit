package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS39;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * Created by wangkai on 17/02/10.
 */
@Controller
@RequestMapping("/crrc/reportFS39")
public class ReportControllerFS39 {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS39.class);

    @Autowired
    private GenericService genericService;

    @Autowired
    private IReportServiceFS39 iReportServiceFS39;

    private static final String FS39 = "crrc/reportPageFS39";

    private String tableName = "THM_FS39";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS01() {
        return FS39;
    }
    /**
     * 获取工厂权限字段tplant
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tplant", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getTplant(HttpServletRequest request) {
        List<String> list = (List<String>) request.getSession().getAttribute("FS39-" + CommonConstant.PLANT);
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


    /**
     * 产成品
     *
     * @param groes
     * @return
     */
    @RequestMapping(value = "/groes", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getGroes(
            @RequestParam(value = "id", required = false, defaultValue = "") String groes) {
        try {
            return (String) iReportServiceFS39.getGroes(groes);
        }catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (NullPointerException e){
            return "{}";
        }
    }

    /**
     * 工序名称
     *
     * @param ltxa1
     * @return
     */
    @RequestMapping(value = "/ltxa1", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getLtxa1(
            @RequestParam(value = "id", required = false, defaultValue = "") String ltxa1) {
        try {
            return (String) iReportServiceFS39.getLtxa1(ltxa1);
        }catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (NullPointerException e){
            return "{}";
        }
    }

    /**
     * 销售订单号
     *
     * @param kdauf
     * @return
     */
    @RequestMapping(value = "/kdauf", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getKdauf(
            @RequestParam(value = "id", required = false, defaultValue = "") String kdauf) {
        try {
            return (String) iReportServiceFS39.getKdauf(kdauf);
        }catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (NullPointerException e){
            return "{}";
        }
    }

    /**
     * 销售订单行号
     *
     * @param kdpos
     * @return
     */
    @RequestMapping(value = "/kdpos", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getKdpos(
            @RequestParam(value = "id", required = false, defaultValue = "") String kdpos) {
        try {
            return (String) iReportServiceFS39.getKdpos(kdpos);
        }catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (NullPointerException e){
            return "{}";
        }
    }

    /**
     * 工作中心
     *
     * @param arbpl
     * @return
     */
    @RequestMapping(value = "/arbpl", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getArbpl(
            @RequestParam(value = "id", required = false, defaultValue = "") String arbpl,
            @RequestParam(value = "name", required = false, defaultValue = "") String ktext) {
        try {
            return (String) iReportServiceFS39.getArbpl(arbpl,ktext);
        }catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (NullPointerException e){
            return "{}";
        }
    }

    /**
     * 需求单位
     *
     * @param xqdw
     * @return
     */
    @RequestMapping(value = "/xqdw", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getXqdw(
            @RequestParam(value = "id", required = false, defaultValue = "") String xqdw) {
        try {
            return (String) iReportServiceFS39.getXqdw(xqdw);
        }catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (NullPointerException e){
            return "{}";
        }
    }

    /**
     * 生产订单类型
     *
     * @param auart
     * @return
     */
    @RequestMapping(value = "/auart", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getAuart(
            @RequestParam(value = "id", required = false, defaultValue = "") String auart) {
        try {
            return (String) iReportServiceFS39.getAuart(auart);
        }catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (NullPointerException e){
            return "{}";
        }
    }

    /**
     * 获取fs39结果集
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean){
        String resultJson;
        try {
            resultJson = (String)iReportServiceFS39.getDataFS39(queryBean);
            if(StringUtils.isNotBlank(resultJson) && !"null".equals(resultJson)){
                return resultJson;
            }else {
                return "{}";
            }
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (ParseException e) {
            logger.error("数据异常" + e.getMessage());
            return AjaxObject.newError("Json 数据异常").setCallbackType("").toString();
        }
    }

}
