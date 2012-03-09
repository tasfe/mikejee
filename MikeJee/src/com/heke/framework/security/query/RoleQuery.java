package com.heke.framework.security.query;

import com.heke.framework.common.web.PageQuery;

/**
 * 角色查询器
 * 
 * @author Mike
 *
 */
public class RoleQuery extends PageQuery {
	
	private String roleName;
	
	private int deptId;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
}
