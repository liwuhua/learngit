/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.yjdj.view.core.web.log.LogAdapter.java
 * Class:			LogTemplateAdapter
 * Date:			2013-5-3
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          2.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.yjdj.view.core.web.log.impl;

import com.google.common.collect.Maps;
import com.yjdj.view.core.web.log.LogAPI;
import com.yjdj.view.core.web.log.LogLevel;

import java.util.Map;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  2.1.0
 * @since   2013-5-3 下午5:21:07 
 */

public class LogAdapter implements LogAPI {

	/**   
	 * @param message
	 * @param logLevel  
	 * @see com.yjdj.view.core.web.log.LogAPI#log(String, com.yjdj.view.core.web.log.LogLevel)
	 */
	@Override
	public void log(String message, LogLevel logLevel) {
		log(message, null, logLevel);
	}

	/**
	 * @param message
	 * @param objects
	 * @param logLevel
	 * @see com.yjdj.view.core.web.log.LogAPI#log(String, Object[], com.yjdj.view.core.web.log.LogLevel)
	 */
	@Override
	public void log(String message, Object[] objects, LogLevel logLevel) {
		
	}
	
	/**   
	 * @return  
	 * @see com.yjdj.view.core.web.log.LogAPI#getRootLogLevel()
	 */
	@Override
	public LogLevel getRootLogLevel() {
		return LogLevel.ERROR;
	}

	/**   
	 * @return  
	 * @see com.yjdj.view.core.web.log.LogAPI#getCustomLogLevel()
	 */
	@Override
	public Map<String, LogLevel> getCustomLogLevel() {
		return Maps.newHashMap();
	}
}
