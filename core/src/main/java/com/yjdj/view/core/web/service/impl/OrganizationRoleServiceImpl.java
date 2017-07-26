/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, ketayao.com
 * Filename:		com.yjdj.view.core.web.service.impl.OrganizationRoleServiceImpl.java
 * Class:			OrganizationRoleServiceImpl
 * Date:			2013-4-15
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.yjdj.view.core.web.service.impl;

import com.yjdj.view.core.web.dao.OrganizationRoleDAO;
import com.yjdj.view.core.entity.OrganizationRole;
import com.yjdj.view.core.web.service.OrganizationRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  2.0.0
 * @since   2013-4-15 下午4:16:04 
 */
@Service
@Transactional
public class OrganizationRoleServiceImpl implements OrganizationRoleService {
	
	private OrganizationRoleDAO organizationRoleDAO;
	
	/**
	 * 
	 * 构造函数
	 * @param organizationRoleDAO
	 */
	@Autowired
	public OrganizationRoleServiceImpl(OrganizationRoleDAO organizationRoleDAO) {
		this.organizationRoleDAO = organizationRoleDAO;
	}
	
	/**   
	 * @param id
	 * @return  
	 * @see com.yjdj.view.core.web.service.OrganizationRoleService#get(Long)
	 */
	@Override
	public OrganizationRole get(Long id) {
		return organizationRoleDAO.findOne(id);
	}

	/**
	 * @param organizationId
	 * @return
	 * @see com.yjdj.view.core.web.service.OrganizationRoleService#find(Long)
	 */
	@Override
	public List<OrganizationRole> find(Long organizationId) {
		return organizationRoleDAO.findByOrganizationId(organizationId);
	}

	/**
	 * @param organizationRole
	 * @see com.yjdj.view.core.web.service.OrganizationRoleService#save(com.yjdj.view.core.entity.OrganizationRole)
	 */
	@Override
	public void save(OrganizationRole organizationRole) {
		organizationRoleDAO.save(organizationRole);
	}

	/**
	 * @param organizationRoleId
	 * @see com.yjdj.view.core.web.service.OrganizationRoleService#delete(Long)
	 */
	@Override
	public void delete(Long organizationRoleId) {
		organizationRoleDAO.delete(organizationRoleId);
	}

}
