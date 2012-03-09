package com.heke.framework.security.query;

import com.heke.framework.common.web.PageQuery;

/**
 * 用户查询器
 * 
 * @author Mike
 *
 */
public class UserQuery extends PageQuery {
	
	private String userName;
	
	private int deptId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
}
