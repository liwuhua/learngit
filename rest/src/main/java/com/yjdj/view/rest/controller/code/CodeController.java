package com.yjdj.view.rest.controller.code;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yjdj.view.core.entity.User;
import com.yjdj.view.core.entity.mybeans.CodeInfo;
import com.yjdj.view.core.entity.mybeans.CodeType;
import com.yjdj.view.core.service.CodeService;
import com.yjdj.view.core.util.StringUtil;
import com.yjdj.view.core.web.SecurityConstants;
import com.yjdj.view.core.web.exception.ExistedException;
import com.yjdj.view.core.web.exception.ServiceException;
import com.yjdj.view.core.web.util.dwz.AjaxObject;
import com.yjdj.view.core.web.util.dwz.Page;

@Controller
@RequestMapping("/management/code")
@SuppressWarnings("all")
public class CodeController {
	@Autowired
	private CodeService codeService;

	private static final String LIST = "management/code/list";
	private static final String CREATE = "management/code/create";
	private static final String UPDATE = "management/code/update";
	private static final String TYPE_CREATE = "management/code/codeTypeCreate";
	private static final String TYPE_UPDATE = "management/code/codeTypeUpdate";
	

	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Page page, ServletRequest request, Map<String, Object> map, String codeTypeId) {
		//CodeInfo表中的信息
		List<CodeInfo> list = codeService.search(codeTypeId, page);
		map.put("page", page);
		map.put("codeInfoEntities", list);
		map.put("codeTypeId", codeTypeId);
		if (!StringUtil.isEmpty(codeTypeId)) {
			map.put("isAutoLoad", "yes");
		}
		//查询出所有的CodeType数据 不包括逻辑删除的
		HashMap paramMap = new HashMap();
		paramMap.put("indentify",0);
		List<CodeType> codeTypes = codeService.getAllCodeType(paramMap);
		for (CodeType codeType : codeTypes) {
//			String id = codeType.getId();
			codeType.setName(codeType.getId() + " " + codeType.getName());
		}
		map.put("codeTypes", codeTypes);  //所有的codeType  parent从这里面取
		return LIST; 
	}

	@RequestMapping(value = "/codeTypeCreate", method = RequestMethod.GET)
	public String preCodeTypeCreate(Map<String, Object> map) {
		//查出所有的类型的id和名称 供选择父级(不包括逻辑删除的)
		HashMap paramMap = new HashMap();
		paramMap.put("indentify",0);
		List<CodeType> codeTypes = codeService.getAllCodeType(paramMap);
		 CodeType codeType = new CodeType();
		 codeType.setId(null);
		 codeType.setName("");
		 codeTypes.add(0, codeType);
		map.put("codeTypes",codeTypes);
		return TYPE_CREATE;
	}
 
	@RequestMapping(value = "/createCodeType", method = RequestMethod.POST)
	@ResponseBody
	public String createCodeType(@Valid CodeType codeType, HttpServletRequest request) {
		try {
			
			int maxId = codeService.getMaxCodeTypeId();
//			第一个整型参数输出5位宽度，不足补0 codetype
			String id = String.format("%1$05d", maxId + 1);
			codeType.setId(id);
			codeService.saveCodeType(codeType);
		} catch (ExistedException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("").toString();
		}
		return AjaxObject.newOk("添加编码类型成功！").toString();
	}


	@RequestMapping(value = "/codeTypeUpdate/{codeTypeId}", method = RequestMethod.GET)
	public String preCodeTypeUpdate(@PathVariable String codeTypeId, Map<String, Object> map) {
		//查出所有的类型的id和名称(不包括本级和已逻辑删除的类型  父级保存在第一位置)
		List<CodeType> codeTypes=new ArrayList<CodeType>();
		CodeType codeType = codeService.getCodeType(codeTypeId); //单个主类型的信息
		String parentId= codeType.getParentId();
		
		//查询出其他的主类型的type 不包括本级和父级的(如果没有其他 的类型  那么不添加到codeTypes中)
		 HashMap paramMap = new HashMap();
		 paramMap.put("indentify",2);
		 paramMap.put("codeTypeId",codeTypeId); //本级的id
		 paramMap.put("parentId",parentId);     //父级id
		 List<CodeType> codeTypesOther = codeService.getAllCodeType(paramMap); // 不包括逻辑删除的及本级和父级 
		   //通过parentid查找得到相关父级信息  没有查询到数值 那么codeTypeParent为null
			CodeType codeTypeParent = codeService.getCodeType(parentId); //父级 通过判断父级进行逻辑操作
			if(codeTypeParent==null ){
				 CodeType parecodeType = new CodeType();
				 parecodeType.setId(null);
				 parecodeType.setName("");
				 codeTypes.add(0, parecodeType);
				//其他类型不为空
				if(CollectionUtils.isNotEmpty(codeTypesOther) ){
					codeTypes.addAll(codeTypesOther);
				}
			
			}else{
				codeTypes.add(0, codeTypeParent);
				//父类不为空的时候  有其他类型那么添加  最后添加一个空类型
				if(CollectionUtils.isNotEmpty(codeTypesOther) ){
					codeTypes.addAll(codeTypesOther);
					 CodeType parecodeType = new CodeType();
					 parecodeType.setId(null);
					 parecodeType.setName("");
					 codeTypes.add(codeTypes.size(), parecodeType);
					
				}else{
					 CodeType parecodeType = new CodeType();
					 parecodeType.setId(null);
					 parecodeType.setName("");
					 codeTypes.add(codeTypes.size(), parecodeType);
				}
				
			}
		 
			map.put("codeTypes",codeTypes); //所有的父级
			map.put("codeType", codeType);
			return TYPE_UPDATE;
	}

	@RequestMapping(value = "/updateCodeType", method = RequestMethod.POST)
	@ResponseBody
	public String updateCodeType(@Valid CodeType codeType, HttpServletRequest request) {
		try {
			codeService.updateCodeType(codeType);
		} catch (ExistedException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("").toString();
		}
		return AjaxObject.newOk("修改编码类型成功！").toString();
	}

    //逻辑删除
	@RequestMapping(value = "/deleteCodeType/{id}", method = { RequestMethod.POST })
	@ResponseBody
	public String deleteCodeType(@PathVariable String id) {
		try {
			codeService.deleteAllCodeInfo(id);
			codeService.deleteCodeType(id);
		} catch (ServiceException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("").toString();
		}
		return AjaxObject.newOk("删除编码类型成功！").setCallbackType("").toString();
	}

	//codeTypeId  编码类型id CREATE页面中使用
	@RequestMapping(value = "/createCode/{codeTypeId}/{parentTypeId}", method = RequestMethod.GET)
	public String preCreateCode(@PathVariable String codeTypeId, @PathVariable String parentTypeId, Map<String, Object> map) {
		//找到父类下面的所有id codeTypeId
		List<CodeInfo> codeinfos = codeService.getCodeinfoById(parentTypeId,null);
		map.put("codeinfos",codeinfos);
		return CREATE;
	}

	@RequestMapping(value = "/createCode", method = RequestMethod.POST)
	@ResponseBody
	public String createCode(@Valid CodeInfo codeInfo, HttpServletRequest request) {
         
		User user = (User) request.getSession().getAttribute(SecurityConstants.LOGIN_USER);
		int maxcodeId = codeService.getMaxCodeId(codeInfo.getCodeTypeId());
 		//第一个整型参数输出5位宽度，不足补0
		String codeId = String.format("%1$05d", maxcodeId + 1); //code codetype下面的最大的id
		codeInfo.setStage("1");
		codeInfo.setIsDeleted("0");
		codeInfo.setCode4(codeId);
		codeInfo.setLastUpdateUser(user.getUsername());
		try {
			codeService.saveCodeInfo(codeInfo);
		} catch (ExistedException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("").toString();
		}
		return AjaxObject.newOk("添加编码成功！").toString();
	}

	//编辑通过传递   codeTypeId id 获取编辑预留信息  跳转到更新页面
	@RequestMapping(value = "/updateCode/{codeTypeId}/{id}/{parentTypeId}", method = RequestMethod.GET)
	public String preCodeUpdate( @PathVariable String codeTypeId,@PathVariable String id,
			@PathVariable String parentTypeId,Map<String, Object> map) {
	    
		//查出父级   放在第一位  extend为父级  查出父级信息 放在第一位   然后查询出其他类型
		CodeInfo codeInfo = codeService.getCodeById(id);
		if(codeInfo!=null){
			String parentId = codeInfo.getExtend();
			List<CodeInfo> codeinfos = codeService.getCodeinfoById(parentTypeId,parentId);
			map.put("codeinfos",codeinfos);
			map.put("codeInfo", codeInfo);
		}
		return UPDATE;
	
	}

	//codeInfo 的信息传递编辑后的信息  保存编辑后的内容
	@RequestMapping(value = "/updateCode", method = RequestMethod.POST)
	@ResponseBody
	public String updateCode(@Valid CodeInfo codeInfo, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(SecurityConstants.LOGIN_USER);
		codeInfo.setStage("1");
		codeInfo.setLastUpdateUser(user.getUsername());
		codeService.updateCodeInfo(codeInfo);
		try {
			codeService.updateCodeInfo(codeInfo);
		} catch (ExistedException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("").toString();
		}
		return AjaxObject.newOk("修改编码成功！").toString();
	}
   
	//逻辑删除
	@RequestMapping(value = "/deleteCode/{codeTypeId}", method = { RequestMethod.POST })
	@ResponseBody 
	public String delete(  
			@PathVariable  String codeTypeId, String[] ids) {
		for (String id : ids) {
			try {  
				codeService.deleteCodeById(codeTypeId,id);
			} catch (ServiceException e) {
				return AjaxObject.newError(e.getMessage()).setCallbackType("").toString();
			}
		}
		return AjaxObject.newOk("删除编码成功！").setCallbackType("").toString();
	}
	
	

	@RequestMapping(value = "/searchColumnsType/{reserve1}", method = { RequestMethod.GET })
	@ResponseBody
	public List<CodeInfo> searchColumnsType(@PathVariable String reserve1) {
		List<CodeInfo> codeInfos = codeService.getAllCodes("00001");
		for (CodeInfo codeInfo : codeInfos) {
			if (!codeInfo.getReserve1().equals(reserve1)) {
				continue;
			}
			codeInfo.setName(codeInfo.getName());
		}
		return codeInfos;
	}

	@RequestMapping(value = "/searchColumnsRevert2/{code}", method = { RequestMethod.GET })
	@ResponseBody
	public CodeInfo searchColumnsRevert2(@PathVariable  String code){
		return codeService.getCodeInfoByCode("00001",code);
	}

}
