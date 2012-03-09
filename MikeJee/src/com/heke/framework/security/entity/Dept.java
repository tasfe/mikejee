package com.heke.framework.security.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 部门
 * 
 * @author Mike
 *
 */
//TODO
@JsonAutoDetect
//TODO
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "children"})
@Entity
@Table(name = "fw_core_dept")
//在hibernate映射文件对数据库表的描述中，加入dynamic-insert= "true "和   dynamic-update= "true "   语句，这时hibernate在进行插入（更新）操作时，只会为那些值不为空的字段赋值，而值为空的字段就会使用数据库表中定义的默认值了。 
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class Dept implements java.io.Serializable {

	public static final String IS_LEAF = "1";
	
	public static final String IS_NOT_LEAF = "0";
	
	private int deptId;
	
	private String deptName;
	
	/**
	 * 自定义部门编号
	 */
	private String customId;
	
	private int sortNo;
	
	/**
	 * 叶子节点(0:树枝节点;1:叶子节点)
	 */
	private String leaf;
	
	private String remark;
	
	/**
	 * 启用状态(1:启用;0:停用)
	 */
	private String enabled;
	
	private Dept parent;
	
	private Set<Dept> children = new HashSet<Dept>(0);

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "dept_id", unique = true, nullable = false)
	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	@Column(name = "dept_name", length = 100, nullable = false)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "custom_id")
	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	@Column(name = "sort_no")
	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	@Column(name = "leaf", nullable = false)
	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "enabled")
	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	public Dept getParent() {
		return parent;
	}

	public void setParent(Dept parent) {
		this.parent = parent;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	public Set<Dept> getChildren() {
		return children;
	}

	public void setChildren(Set<Dept> children) {
		this.children = children;
	}
	
	@Override
	public boolean equals(Object obj) {
		Dept d = (Dept)obj;
		return d.getDeptId() == this.getDeptId();
	}
	
	@Override
	public int hashCode() {
		return 42;
	}
	
}
