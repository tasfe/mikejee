package com.heke.framework.security.query;

import com.heke.framework.common.web.PageQuery;

/**
 * 菜单查询器
 * 
 * @author Mike
 *
 */
public class MenuQuery extends PageQuery {
	
	private String menuName;
	
	private int parentId;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
}
