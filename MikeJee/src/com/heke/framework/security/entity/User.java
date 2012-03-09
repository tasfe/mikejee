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
 * 用户
 * 
 * @author Mike
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "roles", "menus"})
@Entity
@Table(name = "fw_core_user")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class User implements Serializable {
	
	private int userId;
	
	private String userName;
	
	private String account;
	
	private String password;
	
	/**
	 * 性别(0:未知;1:男;2:女)
	 */
	private String sex;
	
	private String phone;
	
	private String email;
	
	private String locked;
	
	/**
	 * 人员类型(1:经办员;2:管理员;3:系统内置人员;4;项目网站注册用户)
	 */
	private String userType;
	
	private String enabled;
	
	private String remark;
	
	private Dept dept;
	
	private Set<Role> roles = new HashSet<Role>(0);
	
	private Set<Menu> menus = new HashSet<Menu>(0);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "user_name", nullable = false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "account")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "locked")
	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	@Column(name = "user_type")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name = "enabled")
	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne
	@JoinColumn(name = "dept_id", nullable = false)
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	//关联查询
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "fw_core_user_authorize", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	//关联查询
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "fw_core_user_menu_map", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "menu_id", nullable = false, updatable = false) })
	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	
	@Override
	public boolean equals(Object obj) {
		User u = (User)obj;
		return u.getUserId() == this.getUserId();
	}
	
	@Override
	public int hashCode() {
		return 42;
	}
}
