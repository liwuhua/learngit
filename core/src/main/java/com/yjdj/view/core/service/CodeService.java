package com.yjdj.view.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yjdj.view.core.entity.mybeans.CodeInfo;
import com.yjdj.view.core.entity.mybeans.CodeType;

/**
 * 
 * 
 * @author wangjunfeng
 * @date 2016年2月24日
 */
@SuppressWarnings("all")
public interface CodeService {
	/**
	 * 取得所有的元数据类型
	 *
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-22
	 */

	List<CodeType> getAllCodeType(HashMap paramMap);

	/**
	 * 分页查找指定类型的编码
	 *
	 * @param codeTypeId
	 * @param pageInfo
	 * @return
	 * @author wangjunfeng
	 * @date 2014年4月16日
	 */
	List<CodeInfo> search(String codeTypeId, com.yjdj.view.core.web.util.dwz.Page page);
	
	List<CodeInfo> getCodeinfoById(String parentTypeId,String parentId);

	Map<String, CodeInfo> getAllCodesMapFromMemory(final String codeTypeId);

	/**
	 * 取得某个编码分类下的某个编码的详细信息
	 *
	 * @param codeType
	 * @param code
	 * @return
	 * @author wangjunfeng
	 * @date 2014年5月27日
	 */
	CodeInfo getCodeInfoByCode(String codeType, String code);

	/**
	 * 取得编码分类下面的所有编码
	 *
	 * @param codeTypeId
	 * @return
	 * @author wangjunfeng
	 * @date 2014年4月15日
	 */
	List<CodeInfo> getAllCodes(final String codeTypeId);

	/**
	 * 获取编码类型
	 *
	 * @param codeTypeId
	 * @return
	 * @author wangjunfeng
	 * @date 2014年4月17日
	 */
	CodeType getCodeType(String codeTypeId);

	void deleteCodeType(String codeTypeId);

	/**
	 * 根据id删除指定的元数据
	 *
	 * @param id 元数据id
	 * @author wangjunfeng
	 * @date 2012-11-22
	 */
	void deleteCodeById(String codeTypeId,String id);

	/**
	 * 通过id获取元数据
	 *
	 * @param id id
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-23
	 */
	CodeInfo getCodeById(String id);

	/**
	 * 修改元数据
	 *
	 * @param codeInfo 元数据
	 * @param userId 操作用户
	 * @author wangjunfeng
	 * @date 2012-11-23
	 */
	void modifyCodeInfo(CodeInfo codeInfo, String userId);

	/**
	 * 保存新增的元数据
	 *
	 * @param codeInfo 元数据
	 * @param userId 操作用户ID
	 * @author wangjunfeng
	 * @date 2012-11-23
	 */
	void newCodeInfo(CodeInfo codeInfo, String userId);

	/**
	 * 获得最大的元数据类型id
	 *
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-25
	 */
	int getMaxCodeTypeId();
	
	int getMaxCodeId(String codeTypeId);

	void saveCodeType(CodeType ct);

	void updateCodeType(CodeType ct);

	void saveCodeInfo(CodeInfo codeInfo);

	void updateCodeInfo(CodeInfo codeInfo);

	/**
	 * 根据codeTypeId删除所有的元数据
	 *
	 * @param codeTypeId codeTypeId
	 * @author wangjunfeng
	 * @date 2014-1-3
	 */
	void deleteAllCodeInfo(String codeTypeId);
}
