/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.yjdj.view.core.web.controller.LogEntityController.java
 * Class:			LogEntityController
 * Date:			2013-5-26
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          2.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.yjdj.view.rest.controller.security;

import com.yjdj.view.core.entity.LogEntity;
import com.yjdj.view.core.web.log.Log;
import com.yjdj.view.core.web.log.LogLevel;
import com.yjdj.view.core.web.log.LogMessageObject;
import com.yjdj.view.core.web.log.impl.LogUitl;
import com.yjdj.view.core.web.service.LogEntityService;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import com.yjdj.view.core.web.util.dwz.Page;
import com.yjdj.view.core.web.util.persistence.DynamicSpecifications;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  2.1.0
 * @since   2013-5-26 下午1:54:30 
 */
@Controller
@RequestMapping("/management/security/logEntity")
public class LogEntityController {
	@Autowired
	private LogEntityService logEntityService;
	
	private static final String LIST = "management/security/logEntity/list";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, 
				new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("LogEntity:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, ServletRequest request, Map<String, Object> map) {
		Specification<LogEntity> spec = DynamicSpecifications.bySearchFilter(request, LogEntity.class);
		
		List<LogEntity> logEntities = logEntityService.findByExample(spec, page); 
		
		map.put("page", page);
		map.put("logEntities", logEntities);
		map.put("logLevels", LogLevel.values());
		
		return LIST;
	}
	
	@Log(message="删除了{0}条日志.")
	@RequiresPermissions("LogEntity:delete")
	@RequestMapping(value="/delete", method= RequestMethod.POST)
	public @ResponseBody
    String deleteMany(Long[] ids) {
		AjaxObject ajaxObject = new AjaxObject("删除日志成功!");
		for (Long id : ids) {
			logEntityService.delete(id);
		}
		
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{ids.length}));
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
}
