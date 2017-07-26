package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS13;
import com.yjdj.view.core.service.IReportServiceFS14;
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
@RequestMapping("/crrc/reportFS14")
public class ReportControllerFS14 {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReportControllerFS14.class);

    @Autowired
    private GenericService genericService;

    @Autowired
    private IReportServiceFS14 iReportServiceFS14;

    private static final String FS14 = "crrc/reportPageFS14";

    private String tableName = "THM_STOCK_07";

    @RequestMapping(method = RequestMethod.GET)
    public String reportFS01() {
        return FS14;
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
        List<String> list = (List<String>) request.getSession().getAttribute("FS14-" + CommonConstant.PLANT);
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
     * 管库员(库存仓位)
     *
     * @param lgpbe
     * @return
     */
    @RequestMapping(value = "/lgpbe", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getVbeln(
            @RequestParam(value = "id", required = false, defaultValue = "") String lgpbe) {
        try {
            return (String) iReportServiceFS14.getLgpbe(lgpbe);
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
     * 库存状态
     *
     * @param sttyp
     * @return
     */
    @RequestMapping(value = "/sttyp", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getSttyp(
            @RequestParam(value = "id", required = false, defaultValue = "") String sttyp) {
        try {
            return (String) iReportServiceFS14.getSttyp(sttyp);
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
     * 特殊库存标识
     *
     * @param sobkz
     * @return
     */
    @RequestMapping(value = "/sobkz", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getSobkz(
            @RequestParam(value = "id", required = false, defaultValue = "") String sobkz) {
        try {
            return (String) iReportServiceFS14.getSobkz(sobkz);
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
     * 获取fs13结果集
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getReport(@RequestBody QueryBean queryBean){
        String resultJson;
        try {
            resultJson = (String)iReportServiceFS14.getData(queryBean);
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
            logger.error("日期解析异常" + e.getMessage());
            return AjaxObject.newError("日期解析异常").setCallbackType("").toString();
        }
    }

}
