/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.yjdj.view.core.web.SecurityConstants.java
 * Class:			SecurityConstants
 * Date:			2012-8-9
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.yjdj.view.core.web;

import java.util.HashMap;
import java.util.Map;


/** 
 * 	
 * @author 	zhonglizhi
 */

public class MwdConstants {
	public enum TimeUnit{
		HOUR("小时"),
		DAY("天"),
		WEEK("周"),
		MONTH("月"),
		YEAR("年");
		private final String name;
        private TimeUnit(final String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return this.name;
        }
	}
	
	public static Map <String,String> DimensionNameMap = new HashMap<String,String>();
	
	static{
		DimensionNameMap.put("isp_id","网络运营商");
		DimensionNameMap.put("flow_type","流量来源类型");
		DimensionNameMap.put("flow_channel","流量来源渠道");
		DimensionNameMap.put("flow_is_paid","流量来源是否付费");
		DimensionNameMap.put("flow_host","流量来源主机");
		DimensionNameMap.put("flow_search_engine","流量来源搜索引擎");
		DimensionNameMap.put("flow_page_num","流量来源搜索页数");
		DimensionNameMap.put("flow_search_key","流量来源搜索关键词");
		DimensionNameMap.put("os_name","操作系统名称");
		DimensionNameMap.put("os_ver","操作系统版本");
		DimensionNameMap.put("os_is_mobile","是否移动设备");
		DimensionNameMap.put("browser_name","浏览器");
		DimensionNameMap.put("browser_ver","浏览器版本");
		DimensionNameMap.put("device_type","设备类型");
		DimensionNameMap.put("device_name","设备名称");
		DimensionNameMap.put("device_is_mobile","是否移动设备");
	}
	/**
	 * 维度具体名称在字典表中的类型
	 */
	public static Map <String,String> DimensionTypeMap = new HashMap<String,String>();
	
	static{
		DimensionTypeMap.put("isp_id","1");
		DimensionTypeMap.put("flow_type","2");
		DimensionTypeMap.put("flow_is_paid","3");
		DimensionTypeMap.put("os_is_mobile","4");
		DimensionTypeMap.put("device_type","5");
		DimensionTypeMap.put("device_is_mobile","6");
	}
	
	public enum TimeFormat{
		HOUR("yyyy-MM-dd HH"),
		DAY("yyyy-MM-dd"),
		WEEK("yyyy-MM-dd"),
		MONTH("yyyy-MM"),
		YEAR("yyyy-MM");
		
		private final String format;
        private TimeFormat(final String format) {
            this.format = format;
        }
        @Override
        public String toString() {
            return this.format;
        }
	}
}
