package com.heke.framework.security.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 菜单
 * 
 * @author Mike
 *
 */
//TODO
@JsonAutoDetect
//TODO
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "children"})
@Entity
@Table(name = "fw_core_menu")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class Menu implements Serializable, Comparable<Menu> {

	private int menuId;
	
	private String menuName;
	
	/**
	 * 节点图标CSS类名
	 */
	private String iconCls;
	
	/**
	 * 展开状态(1:展开;0:收缩)
	 */
	private String expanded;
	
	/**
	 * 请求地址
	 */
	private String request;
	
	/**
	 * 叶子节点(0:树枝节点;1:叶子节点)
	 */
	private String leaf;
	
	private int sortNo;
	
	private String remark;
	
	/**
	 * 节点图标
	 */
	private String icon;
	
	/**
	 * 菜单类型(1:系统菜单;0:业务菜单)
	 */
	private String menuType;
	
	private Menu parent;
	
	private Set<Menu> children = new HashSet<Menu>(0);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id", nullable = false, unique = true)
	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	@Column(name = "menu_name", nullable = false)
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "icon_cls")
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	@Column(name = "expanded")
	public String getExpanded() {
		return expanded;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}

	@Column(name = "request")
	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	@Column(name = "leaf")
	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	@Column(name = "sort_no")
	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "menu_type")
	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	public Set<Menu> getChildren() {
		return children;
	}

	public void setChildren(Set<Menu> children) {
		this.children = children;
	}
	
	@Override
	public boolean equals(Object obj) {
		Menu m = (Menu)obj;
		
		return this.getMenuId() == m.getMenuId();
	}
	
	public int hashCode(){  
		return 42;  
	}

	@Override
	public int compareTo(Menu o) {
		return this.sortNo - o.sortNo;
	}  
}
