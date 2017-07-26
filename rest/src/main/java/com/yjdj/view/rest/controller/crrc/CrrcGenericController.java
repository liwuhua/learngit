package com.yjdj.view.rest.controller.crrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangkai on 16/10/20.
 */
@Controller
@RequestMapping("/crrc")
public class CrrcGenericController {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CrrcGenericController.class);

    @Autowired
    private GenericService genericService;
    
    private static final String GLJSC = "/crrc/gljsc";
    private static final String PAGE_INDEX = "/crrc/index";
    @RequestMapping(value = "/gljsc", method = RequestMethod.GET)
    public String reportGLJSC() {
        return GLJSC;
    }
    
     @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String pageIndex() {
        return PAGE_INDEX;
    }

    /**
     * 模糊查询评估类
     *
     * @param val_class
     * @param txtmd
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/valclass", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListValclass(@RequestParam(value = "id", required = false, defaultValue = "") String val_class,
                               @RequestParam(value = "name", required = false, defaultValue = "") String txtmd,
                               @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                               @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        try {
            return genericService.getListValclass(val_class, txtmd, start, limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 模糊查询物料组
     *
     * @param matl_group
     * @param txtmd
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/matkl", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListMatkl(@RequestParam(value = "id", required = false, defaultValue = "") String matl_group,
                               @RequestParam(value = "name", required = false, defaultValue = "") String txtmd,
                               @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                               @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        try {
            return genericService.getListMatkl(matl_group, txtmd, start, limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 模糊查询库存地点
     *
     * @param lgort
     * @param txtmd
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/lgort", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListLgort(@RequestParam(value = "id", required = false, defaultValue = "") String lgort,
                                          @RequestParam(value = "name", required = false, defaultValue = "") String txtmd,
                                          @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                          @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        try {
            return genericService.getListLgort(lgort, txtmd, start, limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 获取销售合同类型
     * @param doc_type
     * @param txtsh
     * @return
     */
    @RequestMapping(value = "/salesdoc", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    @Deprecated
    public String getListSalesDoc(@RequestParam(value = "id", required = false, defaultValue = "") String doc_type,
                                   @RequestParam(value = "name", required = false, defaultValue = "") String txtsh) {
        try {
            return genericService.getListSalesDoc(doc_type, txtsh);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 获取采购合同类型
     * @param doc_type
     * @param txtsh
     * @return
     */
    @RequestMapping(value = "/purdoc", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    @Deprecated
    public String getListPurDoc(@RequestParam(value = "id", required = false, defaultValue = "") String doc_type,
                                   @RequestParam(value = "name", required = false, defaultValue = "") String txtsh) {
        try {
            return genericService.getListPurDoc(doc_type, txtsh);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 模糊查询公司代码
     *
     * @param comp_code
     * @param txtmd
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/tcompcode", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    @Deprecated
    public String getListTcompCode(@RequestParam(value = "id", required = false, defaultValue = "") String comp_code,
            @RequestParam(value = "name", required = false, defaultValue = "") String txtmd,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        try {
            return genericService.getListTcompCode(comp_code, txtmd, start, limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 模糊查询采购组
     *
     * @param pur_group
     * @param txtsh
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/tpurgroup", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTpurGroupParam(@RequestParam(value = "id", required = false, defaultValue = "") String pur_group,
            @RequestParam(value = "name", required = false, defaultValue = "") String txtsh,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        try {
            return genericService.getListTpurGroup(pur_group, txtsh, start, limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 模糊查询工厂列表
     *
     * @param plant
     * @param txtsh
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/tplant", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTplantParam(@RequestParam(value = "id", required = false, defaultValue = "") String plant,
            @RequestParam(value = "name", required = false, defaultValue = "") String txtsh,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        try {
            return genericService.getListTplant(plant, txtsh, start, limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 模糊查询供应商列表
     *
     * @param vendor
     * @param txtmd
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/tvendor", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTvendorParam(@RequestParam(value = "id", required = false, defaultValue = "") String vendor,
            @RequestParam(value = "name", required = false, defaultValue = "") String txtmd,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        try {
            return genericService.getListTvendor(vendor, txtmd, start, limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 模糊查询物料列表
     *
     * @param material
     * @param txtmd
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/tmaterial", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTmaterialParam(@RequestParam(value = "id", required = false, defaultValue = "") String material,
            @RequestParam(value = "name", required = false, defaultValue = "") String txtmd,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        try {
            return genericService.getListTmaterial(material, txtmd, start, limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 模糊查询供应商类型列表
     *
     * @param vendorType
     * @param txtmd
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/vendortype", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTvendorTypeParam(@RequestParam(value = "id", required = false, defaultValue = "") String vendorType,
                                        @RequestParam(value = "name", required = false, defaultValue = "") String txtmd,
                                        @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                        @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        try {
            return genericService.getListTvendorType(vendorType, txtmd, start, limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 模糊查询销售部门
     *
     * @param salesoff
     * @param txtsh
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/tsalesoff", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTsalesOffParam(@RequestParam(value = "id", required = false, defaultValue = "") String salesoff,
            @RequestParam(value = "name", required = false, defaultValue = "") String txtsh,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        try {
            return genericService.getListTsalesOff(salesoff, txtsh, start, limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }

    /**
     * 模糊查询销售组织
     *
     * @param salesorg
     * @param txtlg
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/tsalesorg", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getListTsalesOrg(@RequestParam(value = "id", required = false, defaultValue = "") String salesorg,
            @RequestParam(value = "name", required = false, defaultValue = "") String txtlg,
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        try {
            return genericService.getListTsalesOrg(salesorg, txtlg, start, limit);
        } catch (JsonProcessingException e) {
            logger.error("Json 数据封装异常" + e.getMessage());
            return AjaxObject.newError("Json 数据封装异常").setCallbackType("").toString();
        } catch (DataAccessException e) {
            logger.error("数据查询异常" + e.getMessage());
            return AjaxObject.newError("数据查询异常").setCallbackType("").toString();
        }
    }
}
