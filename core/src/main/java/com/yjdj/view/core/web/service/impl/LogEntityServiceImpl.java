/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.yjdj.view.core.web.service.impl.LogEntityServiceImpl.java
 * Class:			LogEntityServiceImpl
 * Date:			2013-5-3
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          2.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.yjdj.view.core.web.service.impl;

import com.yjdj.view.core.web.dao.LogEntityDAO;
import com.yjdj.view.core.entity.LogEntity;
import com.yjdj.view.core.web.log.LogLevel;
import com.yjdj.view.core.web.service.LogEntityService;
import com.yjdj.view.core.web.util.dwz.Page;
import com.yjdj.view.core.web.util.dwz.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  2.1.0
 * @since   2013-5-3 下午5:08:05 
 */
@Service
@Transactional
public class LogEntityServiceImpl implements LogEntityService {
	
	@Autowired
	private LogEntityDAO logEntityDAO;


	@Override
	public void save(LogEntity logEntity) {
		logEntityDAO.save(logEntity);
	}

	/**   
	 * @param id
	 * @return  
	 * @see com.yjdj.view.core.web.service.LogEntityService#get(Long)
	 */
	@Override
	public LogEntity get(Long id) {
		return logEntityDAO.findOne(id);
	}


	@Override
	public void update(LogEntity logEntity) {
		logEntityDAO.save(logEntity);
	}

	/**
	 * @param id
	 * @see com.yjdj.view.core.web.service.LogEntityService#delete(Long)
	 */
	@Override
	public void delete(Long id) {
		logEntityDAO.delete(id);
	}


	@Override
	public List<LogEntity> findByLogLevel(LogLevel logLevel, Page page) {
		org.springframework.data.domain.Page<LogEntity> springDataPage = 
				logEntityDAO.findByLogLevel(logLevel, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/**   
	 * @return  
	 * @see com.yjdj.view.core.web.service.LogEntityService#findAll()
	 */
	@Override
	public List<LogEntity> findAll() {
		return logEntityDAO.findAll();
	}


	@Override
	public List<LogEntity> findByExample(
			Specification<LogEntity> specification, Page page) {
		org.springframework.data.domain.Page<LogEntity> springDataPage = 
				logEntityDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
