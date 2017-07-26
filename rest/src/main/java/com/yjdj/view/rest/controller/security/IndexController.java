/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.yjdj.view.core.web.controller.IndexController.java
 * Class:			IndexController
 * Date:			2012-8-2
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:
 *
 * </pre>
 *
 */
package com.yjdj.view.rest.controller.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yjdj.view.core.entity.*;
import com.yjdj.view.core.mapper.MuserRoleMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.yjdj.view.core.constant.CommonConstant;
import com.yjdj.view.core.entity.mybeans.CarSalesCount;
import com.yjdj.view.core.service.ChartService;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.web.SecurityConstants;
import com.yjdj.view.core.web.exception.ServiceException;
import com.yjdj.view.core.web.log.Log;
import com.yjdj.view.core.web.log.LogMessageObject;
import com.yjdj.view.core.web.log.impl.LogUitl;
import com.yjdj.view.core.web.service.ModuleService;
import com.yjdj.view.core.web.service.UserService;
import com.yjdj.view.core.web.shiro.ShiroDbRealm;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version 1.1.0
 * @since 2012-8-2 下午5:45:57
 */
@Controller
@RequestMapping("/management/index")
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ChartService chartService;
    @Autowired
    private GenericService genericService;

    @Autowired
    private MuserRoleMapper muserRoleMapper;

    private static final String INDEX = "management/index/index";  //后台页面位置
    private static final String CAR = "management/index/car";
    private static final String TOURPICTURE = "management/index/tourPicture";
    private static final String TOURTOPIC = "management/index/tourTopic";
    private static final String UPDATE_PASSWORD = "management/index/updatePwd";
    private static final String UPDATE_BASE = "management/index/updateBase";

    @Log(message = "{0}登录了系统.")
    @RequiresUser
    @RequestMapping(value = "", method = RequestMethod.GET)

    public String index(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject.getPrincipal();
        // 加入ipAddress
        shiroUser.setIpAddress(request.getRemoteAddr());

        Module menuModule = getMenuModule(subject);

        // 这个是放入user还是shiroUser呢?
        request.getSession().setAttribute(SecurityConstants.LOGIN_USER, shiroUser.getUser());
        request.setAttribute("menuModule", menuModule);

        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getLoginName()}));
        return INDEX;
    }

    @Log(message = "{0}登录了系统.")
    @RequiresUser
    @RequestMapping(value = "indexPortal", method = RequestMethod.GET)
    public String indexPortal(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject.getPrincipal();

//        List<UserRole> userRoles = shiroUser.getUser().getUserRoles();
//        for(UserRole userRole : userRoles){
//            String roleId = userRole.getRole().getId().toString();
//            String roleName = userRole.getRole().getName();
//            System.out.println(roleId+"---------"+roleName);
//        }

        //根据用户id获取角色列表
        boolean isGljsc = false;
        List<MuserRole> roles = muserRoleMapper.getRoles(shiroUser.getUser().getId().toString());
        for(MuserRole role : roles){
            if(role.getId() == 96){
                isGljsc = true;
                break;
            }
        }

        // 加入ipAddress
        shiroUser.setIpAddress(request.getRemoteAddr());
        Module menuModule = getMenuModule(subject);
        List<Module> newportalMenuList = new ArrayList<Module>();
        //叶子菜单列表
        List<Module> leafMenuList = new ArrayList<Module>();
        Boolean isAdmin = false;
        String pagePath = "/crrc/index";
        String pagePathGlgsc = "/crrc/gljsc";
        StringBuilder bs = new StringBuilder();
        for (Module m : menuModule.getChildren()) {
            if (m.getId() == 2) {//管理模块
                isAdmin = true;
            } else if (!m.getSn().contains(CommonConstant.KPI)) {//业务模块
                newportalMenuList.add(m);
            }
        }

        leafList(newportalMenuList, leafMenuList);
        // 这个是放入user还是shiroUser呢?

        request.getSession().setAttribute(SecurityConstants.ORG_ID, shiroUser.getUser().getOrganization().getId());
        request.getSession().setAttribute(SecurityConstants.LOGIN_USER, shiroUser.getUser());
        request.getSession().setAttribute(SecurityConstants.MENU_ID_DISPLAY, newportalMenuList.size() == 0 ? "" : newportalMenuList.get(0).getId());
        request.getSession().setAttribute(SecurityConstants.MENU_MODULE, newportalMenuList);
        request.getSession().setAttribute(SecurityConstants.LEAF_MODULE, leafMenuList);
        request.getSession().setAttribute(SecurityConstants.IS_ADMIN, isAdmin);

        setFSPermission(request, shiroUser);

        request.setAttribute("groupId", newportalMenuList.size() == 0 ? "" : newportalMenuList.get(0).getId());
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getLoginName()}));
        if(request.getSession().getAttribute("isCodeManager")!=null){
            request.setAttribute("menuModule", menuModule);
            LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getLoginName()}));
            return INDEX;
        }
        if(isGljsc){
            return  pagePathGlgsc;
        }
        return pagePath;
    }

    private void setFSPermission(HttpServletRequest request, ShiroDbRealm.ShiroUser shiroUser) {
        try {
            List<Map<String, String>> list = genericService.getCurrentUserPermissons(shiroUser.getId());
            List<String> comp_codes = new ArrayList<String>();
            List<String> plants = new ArrayList<String>();
            List<String> salesorgs = new ArrayList<String>();
            List<String> sales_offs = new ArrayList<String>();
            List<String> pur_groups = new ArrayList<String>();
            if (CollectionUtils.isNotEmpty(list)) {
                String sn = list.get(0).get("sn");
                int cn = 0;
                for (Map<String, String> map : list) {
                    if("Code".equalsIgnoreCase(map.get("sn"))){
                        cn ++;
                    }
                    if (!sn.equals(map.get("sn"))) {

                        request.getSession().setAttribute(sn + "-" + CommonConstant.COMP_CODE, comp_codes);
                        request.getSession().setAttribute(sn + "-" + CommonConstant.PLANT, plants);
                        request.getSession().setAttribute(sn + "-" + CommonConstant.SALESORG, salesorgs);
                        request.getSession().setAttribute(sn + "-" + CommonConstant.SALES_OFF, sales_offs);
                        request.getSession().setAttribute(sn + "-" + CommonConstant.PUR_GROUP, pur_groups);

                        sn = map.get("sn");
                        
                        comp_codes = new ArrayList<String>();
                        plants = new ArrayList<String>();
                        salesorgs = new ArrayList<String>();
                        sales_offs = new ArrayList<String>();
                        pur_groups = new ArrayList<String>();
                        if (CommonConstant.COMP_CODE.equals(map.get("description"))) {
                            comp_codes.add(map.get("short_name"));
                        }
                        if (CommonConstant.PLANT.equals(map.get("description"))) {
                            plants.add(map.get("short_name"));
                        }
                        if (CommonConstant.SALESORG.equals(map.get("description"))) {
                            salesorgs.add(map.get("short_name"));
                        }
                        if (CommonConstant.SALES_OFF.equals(map.get("description"))) {
                            sales_offs.add(map.get("short_name"));
                        }
                        if (CommonConstant.PUR_GROUP.equals(map.get("description"))) {
                            pur_groups.add(map.get("short_name"));
                        }
                        if (CommonConstant.FUNCTION.equals(map.get("description"))) {
                            if (CommonConstant.FUNCTION_VIEW.equals(map.get("short_name"))) {
                                request.getSession().setAttribute(sn + "-" + CommonConstant.FUNCTION_VIEW, true);
                            }
                            if (CommonConstant.FUNCTION_IMPORT.equals(map.get("short_name"))) {
                                request.getSession().setAttribute(sn + "-" + CommonConstant.FUNCTION_IMPORT, true);
                            }
                            if (CommonConstant.FUNCTION_VIEW_EXPORT.equals(map.get("short_name"))) {
                                request.getSession().setAttribute(sn + "-" + CommonConstant.FUNCTION_VIEW_EXPORT, true);
                            }
                        }

                    } else {
                        if (CommonConstant.COMP_CODE.equals(map.get("description"))) {
                            comp_codes.add(map.get("short_name"));
                        }
                        if (CommonConstant.PLANT.equals(map.get("description"))) {
                            plants.add(map.get("short_name"));
                        }
                        if (CommonConstant.SALESORG.equals(map.get("description"))) {
                            salesorgs.add(map.get("short_name"));
                        }
                        if (CommonConstant.SALES_OFF.equals(map.get("description"))) {
                            sales_offs.add(map.get("short_name"));
                        }
                        if (CommonConstant.PUR_GROUP.equals(map.get("description"))) {
                            pur_groups.add(map.get("short_name"));
                        }
                        if (CommonConstant.FUNCTION.equals(map.get("description"))) {
                            if (CommonConstant.FUNCTION_VIEW.equals(map.get("short_name"))) {
                                request.getSession().setAttribute(sn + "-" + CommonConstant.FUNCTION_VIEW, true);
                            }
                            if (CommonConstant.FUNCTION_IMPORT.equals(map.get("short_name"))) {
                                request.getSession().setAttribute(sn + "-" + CommonConstant.FUNCTION_IMPORT, true);
                            }
                            if (CommonConstant.FUNCTION_VIEW_EXPORT.equals(map.get("short_name"))) {
                                request.getSession().setAttribute(sn + "-" + CommonConstant.FUNCTION_VIEW_EXPORT, true);
                            }
                        }
                    }
                }
                
                if(cn == list.size()){
                    request.getSession().setAttribute("isCodeManager", true);
                }
                if(cn >0 && cn < list.size()){
                    request.getSession().setAttribute("isUserWithCodeManage", true);
                }

                request.getSession().setAttribute(sn + "-" + CommonConstant.COMP_CODE, comp_codes);
                request.getSession().setAttribute(sn + "-" + CommonConstant.PLANT, plants);
                request.getSession().setAttribute(sn + "-" + CommonConstant.SALESORG, salesorgs);
                request.getSession().setAttribute(sn + "-" + CommonConstant.SALES_OFF, sales_offs);
                request.getSession().setAttribute(sn + "-" + CommonConstant.PUR_GROUP, pur_groups);

            }
        } catch (JsonProcessingException | DataAccessException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void leafList(List<Module> mlist, List<Module> leafs) {
        for (Module m : mlist) {
            if (!m.getUrl().contentEquals("#") && m.getUrl().contains("/crrc/")) {
                leafs.add(m);
            } else {
                leafList(m.getChildren(), leafs);
            }
        }
    }

    private Module getMenuModule(Subject subject) {
        Module rootModule = moduleService.getTree();

        check(rootModule, subject);
        return rootModule;
    }

    private void check(Module module, Subject subject) {
        List<Module> list1 = Lists.newArrayList();
        for (Module m1 : module.getChildren()) {
            // 只加入拥有view权限的Module
            if (subject.isPermitted(m1.getSn() + ":"
                    + Permission.PERMISSION_READ)
                    || subject.isPermitted(m1.getSn() + ":"
                            + CommonConstant.FUNCTION_VIEW_EXPORT)) {
                check(m1, subject);
                list1.add(m1);
            }
        }
        module.setChildren(list1);
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.GET)
    public String preUpdatePassword() {
        return UPDATE_PASSWORD;
    }

    @Log(message = "{0}获取图表数据.")
    @RequestMapping(value = "/chartdata", method = RequestMethod.POST)
    public @ResponseBody
    List<CarSalesCount> selecSalescount() {
        List<CarSalesCount> salesCounts = chartService.selecSalescount();
        return salesCounts;
    }

    @Log(message = "{0}跳转至car页面.")
    @RequestMapping(value = "/car", method = RequestMethod.GET)
    public String car() {
        return CAR;
    }

    @Log(message = "{0}跳转至tourPicture页面.")
    @RequestMapping(value = "/tourPicture", method = RequestMethod.GET)
    public String tourPicture() {
        return TOURPICTURE;
    }

    @Log(message = "{0}跳转至tourTopic页面.")
    @RequestMapping(value = "/tourTopic", method = RequestMethod.GET)
    public String tourTopic() {
        return TOURTOPIC;
    }

    @Log(message = "{0}修改了密码.")
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    //区别于上面的/updatePwd controller   登录时采用的是post方法  上面的get方法只是为了跳转到修改页  
    public @ResponseBody
    String updatePassword(HttpServletRequest request, String plainPassword,
            String newPassword, String rPassword) {
        User user = (User) request.getSession().getAttribute(SecurityConstants.LOGIN_USER);

        if (newPassword != null && newPassword.equals(rPassword)) {
            user.setPlainPassword(plainPassword);
            try {
                userService.updatePwd(user, newPassword);
            } catch (ServiceException e) {
                LogUitl.putArgs(LogMessageObject.newIgnore());//忽略日志
                return AjaxObject.newError(e.getMessage()).setCallbackType("").toString();
            }
            LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
            return AjaxObject.newOk("修改密码成功!").toString();
        }
        return AjaxObject.newError("修改密码失败!").setCallbackType("").toString();
    }

    @RequestMapping(value = "/updateBase", method = RequestMethod.GET)
    public String preUpdateBase() {
        return UPDATE_BASE;
    }

    @Log(message = "{0}修改了详细信息.")
    @RequestMapping(value = "/updateBase", method = RequestMethod.POST)
    public @ResponseBody
    String update(User user, HttpServletRequest request) {
        User loginUser = (User) request.getSession().getAttribute(SecurityConstants.LOGIN_USER);

        loginUser.setPhone(user.getPhone());
        loginUser.setEmail(user.getEmail());

        userService.update(loginUser);

        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
        return AjaxObject.newOk("修改详细信息成功!").toString();
    }
}
