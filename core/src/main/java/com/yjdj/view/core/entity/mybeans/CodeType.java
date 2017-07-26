package com.yjdj.view.core.entity.mybeans;

import java.io.Serializable;

import com.yjdj.view.core.entity.MybatisBase;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CodeType extends MybatisBase implements Serializable {

	private static final long serialVersionUID = -5945747845441513220L;
	/**
	 * ID 唯一，不能重复，使用5位定长的序列构成，不足5位前面补0
	 */
	private String id;

	/**
	 * 英文名
	 */
	private String ename;

	/**
	 * 中文名
	 */
	private String name;

	/**
	 * 每级Code长度
	 */
//	private int codeLength;
	private String codeLength;

	/**
	 * 描述
	 */
	private String description;
	
	/**
	 *  是否为逻辑删除
	 */
	private String isDeleted;
	
	/**
	 *  parentId 父级id
	 */
	private String parentId;
	
	@Override
	public String toString() {
		return "CodeType [id=" + id + ", ename=" + ename + ", name=" + name + ", codeLength=" + codeLength
				+ ", description=" + description + ", isDeleted=" + isDeleted + ", parentId=" + parentId + "]";
	}
}
