/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, ketayao.com
 * Filename:		com.yjdj.view.core.entity
.RolePermission.java
 * Class:			RolePermission
 * Date:			2013-4-16
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.yjdj.view.core.entity;

import com.google.common.base.Objects;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  2.0.0
 * @since   2013-4-16 下午1:47:51 
 */
@Entity
@Table(name="security_role_permission")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.yjdj.view.core.entity.RolePermission")
public class RolePermission implements Idable<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="roleId")
	private Role role;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="permissionId")
	private Permission permission;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**  
	 * 返回 role 的值   
	 * @return role  
	 */
	public Role getRole() {
		return role;
	}

	/**  
	 * 设置 role 的值  
	 * @param role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**  
	 * 返回 permission 的值   
	 * @return permission  
	 */
	public Permission getPermission() {
		return permission;
	}

	/**  
	 * 设置 permission 的值  
	 * @param permission
	 */
	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	/**   
	 * @param obj
	 * @return  
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj instanceof RolePermission) {
			RolePermission that = (RolePermission) obj;
            return Objects.equal(id, that.getId());
        }

        return false;
	}

	/**
	 * @return
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
