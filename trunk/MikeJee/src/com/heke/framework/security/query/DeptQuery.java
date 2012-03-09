package com.heke.framework.security.query;

import com.heke.framework.common.web.PageQuery;

/**
 * 部门查询器
 * 
 * @author Mike
 *
 */
public class DeptQuery extends PageQuery {
	
	private int deptId;
	
	private String deptName;
	
	private String customId;
	
	private int parentId;

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
}
