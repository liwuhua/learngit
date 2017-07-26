package com.yjdj.view.core.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

	/**
	 * <p>Discription:[导出字段名]</p>
	 * <p>默认调用当前字段的“get”方法，如指定导出字段为对象，请填写“对象名.对象属性”，例：“area.name”、“office.name”</p>
	 * @return 
	 */
	String value() default "";
	
	/**
	 * <p>Discription:[导出字段标题]</p>
	 * <p>需要添加批注请用“**”分隔，标题**批注，仅对导出模板有效</p>
	 * @return
	 */
	String title() default "";
	
	/**
	 * <p>Discription:[字段类型]</p>
	 * <p>0：导出导入；1：仅导出；2：仅导入</p>
	 * Created on 2015-03-09
	 * @return
	 * @author:周志军
	 */
	int type() default 0;

	/**
	 * <p>Discription:[导出字段对齐方式]</p>
	 * <p>0：自动；1：靠左；2：居中；3：靠右</p>
	 * <p>备注：Integer/Long类型设置居右对齐（align=3）</p>
	 * @return
	 */
	int align() default 0;
	
	/**
	 * <p>Discription:[导出字段字段排序（升序）]</p>
	 * @return
	 */
	int sort() default 0;

	/**
	 * <p>Discription:[如果是字典类型，请设置字典的type值]</p>
	 * @return
	 */
	String dictType() default "";
	
	/**
	 * <p>Discription:[反射类型]</p>
	 * @return
	 */
	Class<?> fieldType() default Class.class;
	
	/**
	 * <p>Discription:[字段归属组]</p>
	 * <p>根据分组导出导入</p>
	 * @return
	 */
	int[] groups() default {};
}
