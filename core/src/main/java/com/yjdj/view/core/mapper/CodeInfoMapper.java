package com.yjdj.view.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yjdj.view.core.entity.mybeans.CodeInfo;

/**
 * 
 * 
 * @author wangjunfeng
 * @date 2016年2月24日
 */
@MyBatisRepository
@SuppressWarnings("all")
public interface CodeInfoMapper extends BaseMapper  {
	/**
	 * 保存元数据
	 *
	 * @param codeInfo 元数据
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	long save(CodeInfo codeInfo);

	/**
	 * 全量更新元数据
	 *
	 * @param codeInfo 元数据
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	void update(CodeInfo codeInfo);

	/**
	 * 直接删除元数据
	 *
	 * @param id 元数据Id
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	void delete(@Param("id") String id);

	/**
	 * 主键查询元数据
	 *
	 * @param id id
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	CodeInfo getById(Long id);
	
	
	
	/**
	 * 主键查询元数据
	 *
	 * @param id codeTypeId 
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	CodeInfo getByTypeIdAndId(@Param("codeTypeId")String codeTypeId , @Param("id")String id);
	
	void deleteByidAndcode(@Param("codeTypeId")String codeTypeId , @Param("id")String id);

	
	/**
	 * 取得所有的元数据类型
	 *
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	List<CodeInfo> getAllTypeCode();
	
	/**
	 * 通过codetype取得最大的codeid
	 *
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	Map getCodesByTypeid(String codeTypeId);
	
	/**
	 * 根据code类型，code1，code2，code3，code4，level来获取元数据
	 *
	 * @param codeTypeId 元数据类型
	 * @param code1 code1
	 * @param code2 code2
	 * @param code3 code3
	 * @param code4 code4
	 * @param level 等级
	 * @return
	 * @author wangjunfeng
	 * @date 2013-12-17
	 */
	CodeInfo getCodeInfoByCodeTypeAndCode1234AndLevel(@Param("codeTypeId") String codeTypeId,
	        @Param("code1") String code1, @Param("code2") String code2, @Param("code3") String code3,
	        @Param("code4") String code4, @Param("level") String level);

	/**
	 * 获取某种类型的某级code下的所有直接子节点
	 *
	 * @param codeTypeId 元数据编码类型id
	 * @param code1 一级分类码
	 * @param code2 二级分类码
	 * @param code3 三级分类码
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	List<CodeInfo> getCodeInfoByCodeTypeAndCode(@Param("codeTypeId") String codeTypeId, @Param("code1") String code1,
	        @Param("code2") String code2, @Param("code3") String code3);

	/**
	 * 取得某一类下的code1，code2，code3的对应确定code信息
	 *
	 * @param codeTypeId codeTypeId
	 * @param code1 一级编码
	 * @param code2 二级编码
	 * @param code3 三级编码
	 * @return
	 * @author wangjunfeng
	 * @date 2013-5-31
	 */
	List<CodeInfo> getCodeInfosByCode1Code2Code3(@Param("codeTypeId") String codeTypeId, @Param("code1") String code1,
	        @Param("code2") String code2, @Param("code3") String code3);

	/**
	 * 获取某种类型的所有元数据编码，按照code排序
	 *
	 * @param codeTypeId 元数据编码类型id
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */                  
	List<CodeInfo> getAllCodeInfoByType(@Param("codeTypeId")String codeTypeId,@Param("codeId")String codeId);

	/**
	 * 取得某种类型的所有元数据编码，按照reserve1字段排序
	 *
	 * @param codeTypeId
	 * @return
	 * @author wangjunfeng
	 * @date 2014年8月13日
	 */
	List<CodeInfo> getAllCodeInfoByTypeOrderByReserve1(String codeTypeId);

	/**
	 * 获取某种类型的元数据记录数（包括已经删除的）
	 *
	 * @param codeTypeId 元数据类型id
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	int getAllCodeInfoCountByType(String codeTypeId);

	/**
	 * 分页获取某种类型的所有元数据（包括已经删除的）
	 *
	 * @param codeTypeId 元数据编码类型id
	 * @param start 起始记录号
	 * @param fetchSize 每页大小
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	List<CodeInfo> getAllCode4Page(@Param("codeTypeId") String codeTypeId, @Param("start") long start,
	        @Param("fetchSize") int fetchSize);

	/**
	 * @param codeTypeId
	 * @author wangjunfeng
	 * @date 2014-1-13
	 */
	void deleteAllCodes(String codeTypeId);
}
