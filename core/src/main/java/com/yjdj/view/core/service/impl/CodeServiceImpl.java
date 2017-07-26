package com.yjdj.view.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.yjdj.view.core.cache.IValueGetter;
//import com.yjdj.view.core.cache.RedisCacheClient;
import com.yjdj.view.core.entity.mybeans.CodeInfo;
import com.yjdj.view.core.entity.mybeans.CodeType;
import com.yjdj.view.core.mapper.CodeInfoMapper;
import com.yjdj.view.core.mapper.CodeTypeMapper;
import com.yjdj.view.core.service.CodeService;

@Service
@SuppressWarnings("all")
public class CodeServiceImpl implements CodeService {
	private static final Logger logger = LoggerFactory.getLogger(CodeServiceImpl.class);
	private static final String CACHE_KEY_GET_ALL_CODES = "nebula_view_getAllCodes";
	private static final String CACHE_KEY_ALL_CODE_TYPE = "nebula_view_getAllCodeType";

	@Autowired
	private CodeInfoMapper codeInfoMapper;
	@Autowired
	private CodeTypeMapper codeTypeMapper;
	//@Autowired
	//private RedisCacheClient redisCacheClient;

	@Override
	public List<CodeType>  getAllCodeType(HashMap paramMap) {
//		return (List<CodeType>) redisCacheClient.get(CACHE_KEY_ALL_CODE_TYPE, new IValueGetter() {
//			@Override
//			public Object getValue(String key) {
//				logger.info("从数据库获取数据");
//				return codeTypeMapper.getAllCodeType();
//			}
//		});
	         return  codeTypeMapper.getAllCodeType(paramMap);
	}

	
	@Override
	public List<CodeInfo> search(String codeTypeId, com.yjdj.view.core.web.util.dwz.Page page) {
		List<CodeInfo> list = codeInfoMapper.getAllCode4Page(codeTypeId, (page.getPageNum() - 1) * page.getNumPerPage(),
		        page.getNumPerPage());
		int total = codeInfoMapper.getAllCodeInfoCountByType(codeTypeId);
		page.setTotalCount(total);
		return list;
	}
	
	@Override
	public List<CodeInfo> getCodeinfoById(String parentTypeId,String parentId) {
		
		return codeInfoMapper.getAllCodeInfoByType(parentTypeId,parentId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, CodeInfo> getAllCodesMapFromMemory(final String codeTypeId) {
//		return (Map<String, CodeInfo>) redisCacheClient.get("nebula_view_getAllCodesMapFromMemory" + codeTypeId,
//		        new IValueGetter() {
//			        @Override
//			        public Object getValue(String key) {
				        List<CodeInfo> codeInfos = getAllCodes(codeTypeId);
				        Map<String, CodeInfo> map = new HashMap<String, CodeInfo>();

				        for (CodeInfo codeInfo : codeInfos) {
					        map.put(codeInfo.getCode(), codeInfo);
				        }
				        return map;
//			        }
//		        });
	}

	@Override
	public CodeInfo getCodeInfoByCode(String codeType, String code) {
		Map<String, CodeInfo> codes = this.getAllCodesMapFromMemory(codeType);
		return codes.get(code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodeInfo> getAllCodes(final String codeTypeId) {
//		return (List<CodeInfo>) redisCacheClient.get(CACHE_KEY_GET_ALL_CODES + codeTypeId, new IValueGetter() {
//			@Override
//			public Object getValue(String key) {
				if ("00020".equals(codeTypeId)) { // 需要按照reserve1字段排序，此方法不够优雅，以后还有同类需求时再重构吧
					return codeInfoMapper.getAllCodeInfoByTypeOrderByReserve1(codeTypeId);
				} else {
					return codeInfoMapper.getAllCodeInfoByType(codeTypeId,null);
				}
//			}
//		});
	}

	@Override
	public CodeType getCodeType(String codeTypeId) {
		return codeTypeMapper.getCodeTypeById(codeTypeId);
	}

	//做逻辑删除
	@Override
	public void deleteCodeType(String codeTypeId) {
		codeTypeMapper.delete(codeTypeId);
		codeInfoMapper.deleteAllCodes(codeTypeId);
		// 清空缓存
		//redisCacheClient.delete(CACHE_KEY_ALL_CODE_TYPE);
		//redisCacheClient.delete(CACHE_KEY_GET_ALL_CODES + codeTypeId);
	}

	@Override
	public void deleteCodeById(String codeTypeId,String id) {
		CodeInfo info = codeInfoMapper.getByTypeIdAndId(codeTypeId,id);
		if (info == null) {
			return;
		}    
		codeInfoMapper.deleteByidAndcode(codeTypeId,id);
		// 清空缓存
		//redisCacheClient.delete(CACHE_KEY_GET_ALL_CODES + info.getCodeTypeId());

	}

	@Override
	public CodeInfo getCodeById(String id) {
		if (!StringUtils.isEmpty(id)) {
			return codeInfoMapper.getById(Long.parseLong(id));
		}
		return null;
	}

	@Override
	public void modifyCodeInfo(CodeInfo codeInfo, String userId) {
		if (!StringUtils.isEmpty(userId)) {
			codeInfo.setLastUpdateUser(userId);
		}
		codeInfo.setLastUpdateTime(new Date());
		codeInfoMapper.update(codeInfo);
		// 清空缓存
		//redisCacheClient.delete(CACHE_KEY_GET_ALL_CODES + codeInfo.getCodeTypeId());
	}

	@Override
	public void newCodeInfo(CodeInfo codeInfo, String userId) {
		if (!StringUtils.isEmpty(userId)) {
			codeInfo.setLastUpdateUser(userId);
		}
		// isDeleted
		codeInfo.setIsDeleted("0");
		codeInfo.setLastUpdateTime(new Date());
		codeInfoMapper.save(codeInfo);
		// 清空缓存
		//redisCacheClient.delete(CACHE_KEY_GET_ALL_CODES + codeInfo.getCodeTypeId());
	}

	@Override
	public int getMaxCodeTypeId() {
		//查询出maxID 包括逻辑删除
		HashMap paramMap = new HashMap();
		paramMap.put("indentify",1);
		List<CodeType> codeTypeList = codeTypeMapper.getAllCodeType(paramMap);
		int maxId = 0;
		if (codeTypeList != null) {
			for (CodeType cti : codeTypeList) {
				String ctiId = cti.getId();
				if (Integer.parseInt(ctiId) > maxId) {
					maxId = Integer.parseInt(ctiId);
				}
			}
		}
		return maxId;
	}
	
	

	@Override
	public int getMaxCodeId(String codeTypeId) {
		Map codesByTypeid = codeInfoMapper.getCodesByTypeid(codeTypeId);
	   return   codesByTypeid==null?0:Integer.valueOf(codesByTypeid.get("maxCodeId")+"");
	}

	@Override
	public void saveCodeType(CodeType ct) {
		codeTypeMapper.save(ct);
		// 清空缓存
		//redisCacheClient.delete(CACHE_KEY_ALL_CODE_TYPE);
	}

	@Override
	public void updateCodeType(CodeType ct) {
		if("".equals(ct.getParentId())){
			ct.setParentId(null);
		}
		codeTypeMapper.update(ct);
		// 清空缓存
		//redisCacheClient.delete(CACHE_KEY_ALL_CODE_TYPE);
	}

	@Override
	public void saveCodeInfo(CodeInfo codeInfo) {
		codeInfo.setLastUpdateTime(new Date());
		codeInfoMapper.save(codeInfo);
		// 清空缓存
		//redisCacheClient.delete(CACHE_KEY_GET_ALL_CODES + codeInfo.getCodeTypeId());
	}

	@Override
	public void updateCodeInfo(CodeInfo codeInfo) {
		codeInfo.setLastUpdateTime(new Date());
		codeInfoMapper.update(codeInfo);
		// 清空缓存
		//redisCacheClient.delete(CACHE_KEY_GET_ALL_CODES + codeInfo.getCodeTypeId());
	}

	@Override
	public void deleteAllCodeInfo(String codeTypeId) {
		codeInfoMapper.deleteAllCodes(codeTypeId);
		// 清空缓存
		//redisCacheClient.delete(CACHE_KEY_GET_ALL_CODES + codeTypeId);
	}




}
