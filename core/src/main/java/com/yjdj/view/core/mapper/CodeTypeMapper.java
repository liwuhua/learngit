package com.yjdj.view.core.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yjdj.view.core.entity.mybeans.CodeType;

/**
 * 
 * 
 * @author wangjunfeng
 * @date 2016年2月24日
 */
@MyBatisRepository
public interface CodeTypeMapper extends BaseMapper {
	/**
	 * 保存元数据类型
	 *
	 * @param codeType 元数据类型
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	void save(CodeType codeType);

	/**
	 * 全量更新元数据类型
	 *
	 * @param codeType 元数据类型
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	void update(CodeType codeType);

	/**
	 * 直接删除元数据类型
	 *
	 * @param id 元数据类型Id
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	void delete(String id);

	/**
	 * 根据id取得元数据类型
	 *
	 * @param id 元数据类型id
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-22
	 */
	CodeType getCodeTypeById(String id);

	/**
	 * 取得所有的元数据类型
	 *
	 * @return
	 * @author wangjunfeng
	 * @date 2012-11-19
	 */
	List<CodeType> getAllCodeType(HashMap map);
}
