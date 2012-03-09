package com.heke.framework.security.service;

import java.util.List;

import com.heke.framework.common.tree.Tree;
import com.heke.framework.common.web.PageQuery;
import com.heke.framework.security.entity.Menu;
import com.heke.framework.security.entity.Role;
import com.heke.framework.security.entity.User;
import com.heke.framework.security.query.UserQuery;

/**
 * 用户业务逻辑接口
 * 
 * @author Mike
 *
 */
public interface UserService {

	void addUser(User user);
	
	void updateUser(User user);
	
	void delUser(User user);
	
	User getUserById(int userId);
	
	/**
	 * 根据账号查找人员
	 * @param account
	 * @return
	 */
	User getUserByAccount(String account);
	
	/**
	 * 分页查询
	 * @param MenuQuery
	 * @return
	 */
	PageQuery findPagedUser(UserQuery userQuery);
	
	/**
	 * 账号是否存在
	 * @param account
	 * @return true:存在；false：不存在
	 */
	boolean accountIsExist(String account);
	
	/**
	 * 保存人员角色关联信息
	 * @param userId
	 * @param roleIds
	 */
	void saveSelectedRole(int userId,int[] roleIds);
	
	/**
	 * 保存人员菜单关联信息
	 * @param userId
	 * @param menuIds
	 */
	void saveSelectedMenu(int userId,int[] menuIds);
	
	/**
	 * 查询角色：人员授权
	 * @param userId
	 * @return
	 */
	Tree getRolesForGrant(int userId);
	
	/**
	 * 查询某人员已授权的角色
	 * @param userId
	 * @return
	 */
	List<Role> getGrantedRolesByUserId(int userId);
	
	/**
	 * 根据用户id查询角色
	 * @param userId
	 * @return
	 */
	List<Role> getRolesByUserId(int userId);
	
	/**
	 * 查询某人员已授权的菜单
	 * @param userId
	 * @return
	 */
	List<Menu> getGrantedMenuByUserId(int userId);
	
	/**
	 * 查询菜单树:人员授权
	 * @param roleId
	 * @return Tree包含层级父子节点.
	 */
	Tree getMenusForGrant(int userId, int operateUserId);
	
	/**
	 * 查询授权给用户的二级菜单
	 * @param userId
	 * @return
	 */
	List<Menu> get2LevelMenusByUserId(int userId);
	
	/**
	 * 根据父菜单id、人员id查询已经授权给人员的子菜单
	 * @param pMenuId
	 * @param userId
	 * @return
	 */
	List<Tree> getGrantedMenu(int pMenuId,int userId);
	
	/**
	 * 查询授权给用户的所有菜单
	 * @param userId
	 * @return
	 */
	List<Menu> getGrantedAllMenuByUserId(int userId);
}
