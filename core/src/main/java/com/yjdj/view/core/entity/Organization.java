/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.yjdj.view.core.entity
.Organization.java
 * Class:			Organization
 * Date:			2012-8-27
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
import java.util.List;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-8-27 下午3:25:15 
 */
@Entity
@Table(name = "security_organization")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.yjdj.view.core.entity.Organization")
public class Organization implements Idable<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Length(min=1, max=64)
	@Column(nullable=false, length=64)
	private String name;
	
	@Length(max=255)
	@Column(length=255)
	private String description;

	@ManyToOne
	@JoinColumn(name="parentId")
	private Organization parent;
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="parent")
	private List<Organization> children = Lists.newArrayList();
	
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="organization")
	private List<User> users = Lists.newArrayList();

	@OneToMany(mappedBy="organization", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	@OrderBy("priority ASC")
	private List<OrganizationRole> organizationRoles = Lists.newArrayList();

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
	 * 返回 parent 的值
	 * @return parent
	 */
	public Organization getParent() {
		return parent;
	}

	/**
	 * 设置 parent 的值
	 * @param parent
	 */
	public void setParent(Organization parent) {
		this.parent = parent;
	}

	/**
	 * 返回 children 的值
	 * @return children
	 */
	public List<Organization> getChildren() {
		return children;
	}

	/**
	 * 设置 children 的值
	 * @param children
	 */
	public void setChildren(List<Organization> children) {
		this.children = children;
	}

	/**
	 * 返回 users 的值
	 * @return users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * 设置 users 的值
	 * @param users
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<OrganizationRole> getOrganizationRoles() {
		return organizationRoles;
	}

	public void setOrganizationRoles(List<OrganizationRole> organizationRoles) {
		this.organizationRoles = organizationRoles;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(id)
				.addValue(name)
				.addValue(parent == null ? null:parent.getName())
				//.addValue(children.size())
				.toString();
	}
}
