/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, ketayao.com
 * Filename:		com.yjdj.view.core.web.service.impl.RolePermissionImpl.java
 * Class:			RolePermissionImpl
 * Date:			2013-4-16
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.yjdj.view.core.web.service.impl;

import com.yjdj.view.core.web.dao.PermissionDAO;
import com.yjdj.view.core.entity.Permission;
import com.yjdj.view.core.web.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  2.0.0
 * @since   2013-4-16 下午2:12:41 
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	private PermissionDAO permissionDAO;

	/**   
	 * @param permission  
	 * @see com.yjdj.view.core.web.service.PermissionService#save(com.yjdj.view.core.entity.Permission)
	 */
	@Override
	public void save(Permission permission) {
		permissionDAO.save(permission);
	}

	/**   
	 * @param id
	 * @return  
	 * @see com.yjdj.view.core.web.service.PermissionService#get(Long)
	 */
	@Override
	public Permission get(Long id) {
		return permissionDAO.findOne(id);
	}

	/**
	 * @param permission
	 * @see com.yjdj.view.core.web.service.PermissionService#update(com.yjdj.view.core.entity.Permission)
	 */
	@Override
	public void update(Permission permission) {
		permissionDAO.save(permission);
	}

	/**
	 * @param id
	 * @see com.yjdj.view.core.web.service.PermissionService#delete(Long)
	 */
	@Override
	public void delete(Long id) {
		permissionDAO.delete(id);
	}
}
