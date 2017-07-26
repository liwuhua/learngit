/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.yjdj.view.core.web.service.impl.RoleServiceImpl.java
 * Class:			RoleServiceImpl
 * Date:			2012-8-7
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.yjdj.view.core.web.service.impl;

import com.yjdj.view.core.web.dao.RoleDAO;
import com.yjdj.view.core.entity.Role;
import com.yjdj.view.core.web.service.RoleService;
import com.yjdj.view.core.web.shiro.ShiroDbRealm;
import com.yjdj.view.core.web.util.dwz.Page;
import com.yjdj.view.core.web.util.dwz.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-8-7 下午5:04:52 
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired(required = false)
	private ShiroDbRealm shiroRealm;
	
	@Override
	public void save(Role role) {
		roleDAO.save(role);
	}

	@Override
	public Role get(Long id) {
		return roleDAO.findOne(id);
	}
	
	@Override
	public List<Role> findAll(Page page) {
		org.springframework.data.domain.Page<Role> springDataPage = roleDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/**   
	 * @param role  
	 * @see com.yjdj.view.core.web.service.RoleService#update(com.yjdj.view.core.entity.Role)
	 */
	public void update(Role role) {
		roleDAO.save(role);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

	/**   
	 * @param id  
	 * @see com.yjdj.view.core.web.service.RoleService#delete(Long)
	 */
	public void delete(Long id) {
		roleDAO.delete(id);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

	/**
	 * @param page
	 * @param name
	 * @return
	 * @see com.yjdj.view.core.web.service.RoleService#find(com.yjdj.view.core.web.util.dwz.Page, String)
	 */
	public List<Role> find(Page page, String name) {
		org.springframework.data.domain.Page<Role> springDataPage = 
				(org.springframework.data.domain.Page<Role>)roleDAO.findByNameContaining(name, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

}
