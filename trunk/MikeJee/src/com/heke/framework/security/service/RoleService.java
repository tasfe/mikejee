package com.heke.framework.security.service;

import java.util.List;

import com.heke.framework.common.tree.Tree;
import com.heke.framework.common.web.PageQuery;
import com.heke.framework.security.entity.Menu;
import com.heke.framework.security.entity.Role;
import com.heke.framework.security.query.RoleQuery;

/**
 * 角色业务逻辑接口
 * 
 * @author Mike
 *
 */
public interface RoleService {

	void addRole(Role role);
	
	void updateRole(Role role);
	
	void delRole(Role role);
	
	Role getRoleById(int roleId);
	
	/**
	 * 分页查询
	 * @param RoleQuery
	 * @return
	 */
	PageQuery findPagedRole(RoleQuery roleQuery);
	
	/**
	 * 保存角色人员关联信息
	 * @param roleId
	 * @param userIds
	 */
	void saveSelectedUser(int roleId,int[] userIds);
	
	/**
	 * 保存角色菜单关联信息
	 * @param roleId
	 * @param menuIds
	 */
	void saveSelectedMenu(int roleId,int[] menuIds);
	
	/**
	 * 查询某角色已授权菜单
	 * @param roleId
	 * @return
	 */
	List<Menu> getGrantedMenusByRoleId(int roleId);
	
	/**
	 * 查询菜单树:角色授权
	 * @param roleId
	 * @return Tree包含层级父子节点.
	 */
	Tree getMenusForGrant(int roleId, int operateUserId);
	
	/**
	 * 查询某部门下的角色
	 * @param deptId
	 * @return
	 */
	List<Role> getRoleListByDeptId(int deptId);
}
