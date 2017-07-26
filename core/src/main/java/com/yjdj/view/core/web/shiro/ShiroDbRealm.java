/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.yjdj.view.core.web.shiro.ShiroDbRealm.java
 * Class:			ShiroDbRealm
 * Date:			2012-8-2
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.yjdj.view.core.web.shiro;

import com.yjdj.view.core.entity.RolePermission;
import com.yjdj.view.core.entity.Permission;
import com.yjdj.view.core.entity.UserRole;
import com.yjdj.view.core.entity.OrganizationRole;
import com.yjdj.view.core.entity.User;
import com.yjdj.view.core.entity.Module;
import com.yjdj.view.core.entity.Role;
import com.google.common.collect.Sets;
import com.yjdj.view.core.util.Digests;
import com.yjdj.view.core.util.Encodes;
import com.yjdj.view.core.web.service.ModuleService;
import com.yjdj.view.core.web.service.OrganizationRoleService;
import com.yjdj.view.core.web.service.UserRoleService;
import com.yjdj.view.core.web.service.UserService;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a> Version 1.1.0
 * @since 2012-8-2 下午3:09:50
 */

public class ShiroDbRealm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);
	
	private static final int INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	private static final String ALGORITHM = "SHA-1";

	// 是否启用超级管理员
	protected boolean activeRoot = false;
	
	// 是否使用验证码
	protected boolean useCaptcha = false;
	
	protected UserService userService;
	
	protected UserRoleService userRoleService;
	
	protected OrganizationRoleService organizationRoleService;
	
	protected ModuleService moduleService;
	
	protected ImageCaptchaService imageCaptchaService; 
	
	/**
	 * 给ShiroDbRealm提供编码信息，用于密码密码比对
	 * 描述
	 */	
	public ShiroDbRealm() {
		super();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM);
		matcher.setHashIterations(INTERATIONS);

		setCredentialsMatcher(matcher);
	}
	
	/**
	 * 认证回调函数, 登录时调用.
	 */
	// TODO 对认证进行缓存处理
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		if (useCaptcha) {
			CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
			if(!token.getUsername().equals("crrcapp")){
				String parm = token.getCaptcha();
				try {
					if (!imageCaptchaService.validateResponseForID(SecurityUtils
							.getSubject().getSession().getId().toString(), parm.toLowerCase())) {//忽略大小写。
						throw new IncorrectCaptchaException("验证码错误！");
					}
				} catch (Exception e) {
					// session如果没有刷新，validateResponseForID会抛出com.octo.captcha.service.CaptchaServiceException的异常
					throw new IncorrectCaptchaException("验证码错误！");
				}
			}
		} 

		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
		User user = userService.get(token.getUsername());
		if (user != null) {
			if (user.getStatus().equals("disabled")) {
				throw new DisabledAccountException();
			}
			
			byte[] salt = Encodes.decodeHex(user.getSalt());
			
			ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUsername(), user);
			// 这里可以缓存认证
			return new SimpleAuthenticationInfo(shiroUser, user.getPassword(),
					ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
		
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Collection<?> collection = principals.fromRealm(getName());
		if (CollectionUtils.isEmpty(collection)) {
			return null;
		}
		ShiroUser shiroUser = (ShiroUser) collection.iterator().next();
		
		List<UserRole> userRoles = userRoleService.find(shiroUser.getId());
		List<OrganizationRole> organizationRoles = organizationRoleService
				.find(shiroUser.getUser().getOrganization().getId());
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(makePermissions(userRoles, organizationRoles, shiroUser));
		
		return info;
	}
	
	private Collection<String> makePermissions(List<UserRole> userRoles, List<OrganizationRole> organizationRoles, ShiroUser shiroUser) {
		// 是否启用超级管理员 
		if (activeRoot) {
			// id==1为超级管理员，构造所有权限 
			if (shiroUser.getId() == 1) {
				Collection<String> stringPermissions = Sets.newHashSet();
				
				List<Module> modules = moduleService.findAll();
				for (Module module : modules) {
					List<Permission> permissions = module.getPermissions();
					// 默认构造CRUD权限
					stringPermissions.add(module.getSn() + ":" + Permission.PERMISSION_CREATE);
					stringPermissions.add(module.getSn() + ":" + Permission.PERMISSION_READ);
					stringPermissions.add(module.getSn() + ":" + Permission.PERMISSION_UPDATE);
					stringPermissions.add(module.getSn() + ":" + Permission.PERMISSION_DELETE);
					
					for (Permission permission : permissions) {
						stringPermissions.add(module.getSn() + ":" + permission.getShortName());
					}
				}
				
				log.info("使用了超级管理员:" + shiroUser.getLoginName() + "登录了系统。At " + new Date());
				log.info(shiroUser.getLoginName() + "拥有的权限:" + stringPermissions);
				return stringPermissions;
			}
		}
		
		Set<Role> roles = Sets.newHashSet();
		for (UserRole userRole : userRoles) {
			roles.add(userRole.getRole());
		}
		
		for (OrganizationRole organizationRole : organizationRoles) {
			roles.add(organizationRole.getRole());
		}
		
		Collection<String> stringPermissions = Sets.newHashSet();
		for (Role role : roles) {
			List<RolePermission> rolePermissions = role.getRolePermissions();
			for (RolePermission rolePermission : rolePermissions) {
				Permission permission = rolePermission.getPermission();
				if (permission.getModule() != null) {//避免脏数据
					stringPermissions.add(permission.getModule().getSn() + ":" + permission.getShortName());
				}
			}
		}
		
		log.info(shiroUser.getLoginName() + "拥有的权限:" + stringPermissions);
		return stringPermissions;
	}
	
	public static class HashPassword {
		public String salt;
		public String password;
	}
	
	public static HashPassword encryptPassword(String plainPassword) {
		HashPassword result = new HashPassword();
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		result.salt = Encodes.encodeHex(salt);

		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, INTERATIONS);
		result.password = Encodes.encodeHex(hashPassword);
		return result;
	}
	
	/**
	 * 
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @param salt 盐值
	 * @return
	 */
	public static boolean validatePassword(String plainPassword, String password, String salt) {
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), INTERATIONS);
		String oldPassword = Encodes.encodeHex(hashPassword);
		return password.equals(oldPassword);
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	
	/**  
	 * 设置 isActiveRoot 的值  
	 * @param isActiveRoot
	 */
	public void setActiveRoot(boolean activeRoot) {
		this.activeRoot = activeRoot;
	}

	/**  
	 * 设置 useCaptcha 的值  
	 * @param useCaptcha
	 */
	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**  
	 * 设置 userRoleService 的值  
	 * @param userRoleService
	 */
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}
	
	/**  
	 * 设置 moduleService 的值  
	 * @param moduleService
	 */
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	/**  
	 * 设置 organizationRoleService 的值  
	 * @param organizationRoleService
	 */
	public void setOrganizationRoleService(
			OrganizationRoleService organizationRoleService) {
		this.organizationRoleService = organizationRoleService;
	}

	public void setImageCaptchaService(ImageCaptchaService imageCaptchaService) {
		this.imageCaptchaService = imageCaptchaService;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {

		private static final long serialVersionUID = -1748602382963711884L;
		private Long id;
		private String loginName;
		private String ipAddress;
		private User user;
		
		public ShiroUser() {
			
		}
		
		/**  
		 * 构造函数
		 * @param id
		 * @param loginName
		 * @param email
		 * @param createTime
		 * @param status
		 */
		public ShiroUser(Long id, String loginName, User user) {
			this.id = id;
			this.loginName = loginName;
			this.user = user;
		}

		/**  
		 * 返回 id 的值   
		 * @return id  
		 */
		public Long getId() {
			return id;
		}

		/**  
		 * 返回 loginName 的值   
		 * @return loginName  
		 */
		public String getLoginName() {
			return loginName;
		}
		
		public String getIpAddress() {
			return ipAddress;
		}

		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}

		/**  
		 * 返回 user 的值   
		 * @return user  
		 */
		public User getUser() {
			return user;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}
	}
}
