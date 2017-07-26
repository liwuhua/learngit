package com.yjdj.view.core.entity.mybeans;

import java.io.Serializable;
import java.util.Date;

import com.yjdj.view.core.entity.MybatisBase;
import com.yjdj.view.core.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CodeInfo extends MybatisBase implements Serializable {

	private static final long serialVersionUID = -6108263228015871712L;
	/**
	 * ID 主键序列，自动增长
	 */
	private String id;

	/**
	 * 元数据类型ID，唯一，不能重复，使用5位定长的序列构成，不足5位前面补0
	 */
	private String codeTypeId;

	/**
	 * 一级编码
	 */
	private String code1;

	/**
	 * 二级编码
	 */
	private String code2;

	/**
	 * 三级编码
	 */
	private String code3;

	/**
	 * 四级编码
	 */
	private String code4;

	/**
	 * 中文名字
	 */
	private String name;

	/**
	 * 英文名字
	 */
	private String ename;

	/**
	 * 等级
	 */
	private String level;

	/**
	 * 阶段
	 */
	private String stage;

	/**
	 * 保留字段1
	 */
	private String reserve1;

	/**
	 * 保留字段2
	 */
	private String reserve2;

	/**
	 * 保留字段3
	 */
	private String reserve3;

	/**
	 * 保留字段4
	 */
	private String reserve4;

	/**
	 * 保留字段5
	 */
	private String reserve5;

	/**
	 * 扩展字段
	 */
	private String extend;

	/**
	 * 描述
	 */
	private String descript;

	/**
	 * 是否已经删除
	 */
	private String isDeleted = "0";

	/**
	 * 最后更新者
	 */
	private String lastUpdateUser;

	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;

	/**
	 * 返回该元数据的Code
	 *
	 * @return
	 * @author wangjunfeng
	 * @date 2012-12-13
	 */
	public String getCode() {
		StringBuilder sb = new StringBuilder();
		sb.append(StringUtil.nullToStr(code1));
		sb.append(StringUtil.nullToStr(code2));
		sb.append(StringUtil.nullToStr(code3));
		sb.append(StringUtil.nullToStr(code4));

		return sb.toString();
	}

	@Override
	public String toString() {
		return "CodeInfo [id=" + id + ", codeTypeId=" + codeTypeId + ", code1=" + code1 + ", code2=" + code2
		        + ", code3=" + code3 + ", code4=" + code4 + ", name=" + name + ", ename=" + ename + ", level=" + level
		        + ", stage=" + stage + ", reserve1=" + reserve1 + ", reserve2=" + reserve2 + ", reserve3=" + reserve3
		        + ", reserve4=" + reserve4 + ", reserve5=" + reserve5 + ", extend=" + extend + ", descript=" + descript
		        + ", isDeleted=" + isDeleted + ", lastUpdateUser=" + lastUpdateUser + ", lastUpdateTime="
		        + lastUpdateTime + "]";
	}

}
