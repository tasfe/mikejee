package com.heke.framework.security.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 角色
 * 
 * @author Mike
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "users", "menus"})
@Entity
@Table(name = "fw_core_role")
public class Role implements Serializable {
	
	private int roleId;
	
	private String roleName;
	
	/**
	 * 角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)
	 */
	private String roleType;
	
	private String locked;
	
	private String remark;
	
	private Dept dept;
	
	private Set<User> users = new HashSet<User>(0);
	
	private Set<Menu> menus = new HashSet<Menu>(0);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id", unique = true, nullable = false)
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_name", nullable = false)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "role_type")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Column(name = "locked")
	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne
	@JoinColumn(name = "dept_id")
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "fw_core_role_authorize", joinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "menu_id", nullable = false, updatable = false) })
	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	
	@Override
	public boolean equals(Object obj) {
		Role r = (Role)obj;
		return r.getRoleId() == this.getRoleId();
	}
	
	@Override
	public int hashCode() {
		return 42;
	}
}
