/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.mwd.web.entity.Role.java
 * Class:			Role
 * Date:			2012-6-7
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.yjdj.view.core.entity;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-6-7 上午11:07:45 
 */
@Entity
@Table(name="security_role")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.yjdj.view.core.entity.Role")
public class Role implements Idable<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false, length=32)
	private String name;
	
	@Length(max=255)
	@Column(length=255)
	private String description;
	
	@OneToMany(mappedBy="role", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	@OrderBy("priority ASC")
	private List<UserRole> userRoles = new ArrayList<UserRole>(0);

	@OneToMany(mappedBy="role", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	@OrderBy("priority ASC")
	private List<OrganizationRole> organizationRoles = Lists.newArrayList();

	//@OneToMany(mappedBy="role", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	@OneToMany(mappedBy="role", cascade={CascadeType.ALL}, orphanRemoval=true)
	private List<RolePermission> rolePermissions = Lists.newArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 返回 name 的值
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 name 的值
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 description 的值
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置 description 的值
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 返回 userRoles 的值
	 * @return userRoles
	 */
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	/**
	 * 设置 userRoles 的值
	 * @param userRoles
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	/**
	 * 返回 organizationRoles 的值
	 * @return organizationRoles
	 */
	public List<OrganizationRole> getOrganizationRoles() {
		return organizationRoles;
	}

	/**
	 * 设置 organizationRoles 的值
	 * @param organizationRoles
	 */
	public void setOrganizationRoles(List<OrganizationRole> organizationRoles) {
		this.organizationRoles = organizationRoles;
	}

	/**
	 * 返回 rolePermissions 的值
	 * @return rolePermissions
	 */
	public List<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	/**  
	 * 设置 rolePermissions 的值  
	 * @param rolePermissions
	 */
	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}
	
	/**   
	 * @param
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

		if (obj instanceof Role) {
			Role that = (Role) obj;
            return Objects.equal(id, that.getId())
                    && Objects.equal(name, that.getName());
        }

        return false;
	}

	/**
	 * @return
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(id, name);
	}
}
