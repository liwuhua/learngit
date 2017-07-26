/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.yjdj.view.core.controller.ModuleController.java
 * Class:			ModuleController
 * Date:			2012-8-6
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.yjdj.view.rest.controller.security;

import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.Module;
import com.yjdj.view.core.entity.Permission;
import com.yjdj.view.core.web.exception.ExistedException;
import com.yjdj.view.core.web.exception.ServiceException;
import com.yjdj.view.core.web.log.Log;
import com.yjdj.view.core.web.log.LogMessageObject;
import com.yjdj.view.core.web.log.impl.LogUitl;
import com.yjdj.view.core.web.service.ModuleService;
import com.yjdj.view.core.web.service.PermissionService;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import com.yjdj.view.core.web.util.dwz.Page;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-8-6 上午10:08:48 
 */
@Controller
@RequestMapping("/management/security/module")
public class ModuleController {
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private PermissionService permissionService;
	
	private static final String CREATE = "management/security/module/create";
	private static final String UPDATE = "management/security/module/update";
	private static final String LIST = "management/security/module/list";
	private static final String TREE = "management/security/module/tree";
	private static final String VIEW = "management/security/module/view";
	private static final String TREE_LIST = "management/security/module/tree_list";
	private static final String LOOKUP_PARENT = "management/security/module/lookup_parent";
	
	@RequiresPermissions("Module:save")
	@RequestMapping(value="/create/{parentModuleId}", method= RequestMethod.GET)
	public String preCreate(Map<String, Object> map, @PathVariable Long parentModuleId) {
		map.put("parentModuleId", parentModuleId);
		return CREATE;
	}
	
	@Log(message="添加了{0}模块�?")
	@RequiresPermissions("Module:save") 
	@RequestMapping(value="/create", method= RequestMethod.POST)
	public @ResponseBody
    String create(@Valid Module module) {
		Module parentModule = moduleService.get(module.getParent().getId());
		if (parentModule == null) {
			return AjaxObject.newError("添加模块失败：id=" + module.getParent().getId() + "的父模块不存在！").toString();
		}
		
		List<Permission> permissions = Lists.newArrayList();
		for (Permission permission : module.getPermissions()) {
			if (StringUtils.isNotBlank(permission.getShortName())) {
				permissions.add(permission);
			}
		}
		
		for (Permission permission : permissions) {
			permission.setModule(module);
		}
		module.setPermissions(permissions);
		
		try {
			moduleService.save(module);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加模块失败�?" + e.getMessage()).toString();
		}
		
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{module.getName()}));
		return AjaxObject.newOk("添加模块成功�?").toString();
	}
	
	@RequiresPermissions("Module:edit")
	@RequestMapping(value="/update/{id}", method= RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Module module = moduleService.get(id);
		Permission permissionCompCode = new Permission();
		Permission permissionPlant = new Permission();
		Permission permissionSalesorg = new Permission();
		Permission permissionSalesoff = new Permission();
		Permission permissionPurgroup = new Permission();
		Permission permissionFunction = new Permission();
		List<Permission> permision = module.getPermissions();
		List<Permission> compCodeList = new ArrayList<Permission>(); 
		List<Permission> plantList = new ArrayList<Permission>(); 
		List<Permission> salesorgList = new ArrayList<Permission>(); 
		List<Permission> salesoffList = new ArrayList<Permission>(); 
		List<Permission> purgroupfList = new ArrayList<Permission>();
		List<Permission> functionfList = new ArrayList<Permission>();
		Map<String,Object> hashMap = new HashMap<String, Object>();
		for(int i=0; i<permision.size(); i++){
			if("COMP_CODE".equals(permision.get(i).getDescription())){
				permissionCompCode.setDescription(permision.get(i).getDescription());
				permissionCompCode.setName(permision.get(i).getName());
				permissionCompCode.setShortName(permision.get(i).getShortName());
				compCodeList.add(permissionCompCode);
				permissionCompCode = new Permission(); 
			}else if("PLANT".equals(permision.get(i).getDescription())){
				permissionPlant.setDescription(permision.get(i).getDescription());
				permissionPlant.setName(permision.get(i).getName());
				permissionPlant.setShortName(permision.get(i).getShortName());
				plantList.add(permissionPlant);
				permissionPlant = new Permission(); 
			}else if("SALESORG".equals(permision.get(i).getDescription())){
				permissionSalesorg.setDescription(permision.get(i).getDescription());
				permissionSalesorg.setName(permision.get(i).getName());
				permissionSalesorg.setShortName(permision.get(i).getShortName());
				salesorgList.add(permissionSalesorg);
				permissionSalesorg = new Permission(); 
			}else if("SALES_OFF".equals(permision.get(i).getDescription())){
				permissionSalesoff.setDescription(permision.get(i).getDescription());
				permissionSalesoff.setName(permision.get(i).getName());
				permissionSalesoff.setShortName(permision.get(i).getShortName());
				salesoffList.add(permissionSalesoff);
				permissionSalesoff = new Permission(); 
			}else if("PUR_GROUP".equals(permision.get(i).getDescription())){
				permissionPurgroup.setDescription(permision.get(i).getDescription());
				permissionPurgroup.setName(permision.get(i).getName());
				permissionPurgroup.setShortName(permision.get(i).getShortName());
				purgroupfList.add(permissionPurgroup);
				permissionPurgroup = new Permission(); 
			}else if("FUNCTION".equals(permision.get(i).getDescription())){
				permissionFunction.setDescription(permision.get(i).getDescription());
				permissionFunction.setName(permision.get(i).getName());
				permissionFunction.setShortName(permision.get(i).getShortName());
				functionfList.add(permissionFunction);
				permissionFunction = new Permission(); 
			}
		}
		
		hashMap.put("COMP_CODE", compCodeList);
		hashMap.put("PLANT", plantList);
		hashMap.put("SALESORG", salesorgList);
		hashMap.put("SALES_OFF", salesoffList);
		hashMap.put("PUR_GROUP", purgroupfList);
		hashMap.put("FUNCTION", functionfList);
		
		map.put("module", module);
		map.put("permission", hashMap);
		return UPDATE;
	}
	
	@Log(message="修改了{0}模块的信息�?")
	@RequiresPermissions("Module:edit")
	@RequestMapping(value="/update", method= RequestMethod.POST)
	public @ResponseBody
    String update(@Valid Module module) {
		Module oldModule = moduleService.get(module.getId());
		oldModule.setName(module.getName());
		oldModule.setPriority(module.getPriority());
		oldModule.setDescription(module.getDescription());
		oldModule.setSn(module.getSn());
		oldModule.setUrl(module.getUrl());
		oldModule.setParent(module.getParent());
		
		// 模块自定义权限，删除过后新增报错，会有validate的验证错误�?hibernate不能copy到permission的�?，导致�?
		for (Permission permission : module.getPermissions()) {
			if (StringUtils.isNotBlank(permission.getShortName())) {// 已�?中的
				if (permission.getId() == null) {//新增
					permission.setModule(oldModule);
					oldModule.getPermissions().add(permission);
					permissionService.save(permission);
				} 
			} else {// 未�?中的
				if (permission.getId() != null) {//删除
					for (Permission oldPermission : oldModule.getPermissions()) {
						if (oldPermission.getId().equals(permission.getId())) {
							oldPermission.setModule(null);
							permission = oldPermission;
							break;
						}
					}
					permissionService.delete(permission.getId());
					oldModule.getPermissions().remove(permission);
				}
			}
		}
		
		moduleService.update(oldModule);
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{oldModule.getName()}));
		return AjaxObject.newOk("修改模块成功�?").toString();
	}
	
	@Log(message="删除了{0}模块�?")
	@RequiresPermissions("Module:delete")
	@RequestMapping(value="/delete/{id}", method= RequestMethod.POST)
	public @ResponseBody
    String delete(@PathVariable Long id) {
		Module module = moduleService.get(id);
		try {
			moduleService.delete(id);
		} catch (ServiceException e) {
			return AjaxObject.newError("删除模块失败�?" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{module.getName()}));
		return AjaxObject.newOk("删除模块成功�?").setCallbackType("").toString();
	}
	
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/tree_list", method={RequestMethod.GET, RequestMethod.POST})
	public String tree_list(Map<String, Object> map) {
		return TREE_LIST;
	}
	
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/tree", method= RequestMethod.GET)
	public String tree(Map<String, Object> map) {
		Module module = moduleService.getTree();
		
		map.put("module", module);
		return TREE;
	}
	
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/list/{parentModuleId}", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, @PathVariable Long parentModuleId, String keywords,
			Map<String, Object> map) {
		List<Module> modules = null;
		if (StringUtils.isNotBlank(keywords)) {
			modules = moduleService.find(parentModuleId, keywords, page);
		} else {
			modules = moduleService.find(parentModuleId, page);
		}
		
		map.put("page", page);
		map.put("modules", modules);
		map.put("keywords", keywords);
		map.put("parentModuleId", parentModuleId);

		return LIST;
	}
	
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET})
	public String view(@PathVariable Long id, Map<String, Object> map) {
		Module module = moduleService.get(id);
		
		map.put("module", module);
		return VIEW;
	}
	
	@RequiresPermissions(value={"Module:edit", "Module:save"}, logical=Logical.OR)
	@RequestMapping(value="/lookupParent/{id}", method={RequestMethod.GET})
	public String lookup(@PathVariable Long id, Map<String, Object> map) {
		Module module = moduleService.getTree();
		
		map.put("module", module);
		return LOOKUP_PARENT;
	}
}
