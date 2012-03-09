package com.heke.framework.security.service;

import java.util.List;

import com.heke.framework.common.web.PageQuery;
import com.heke.framework.security.entity.Menu;
import com.heke.framework.security.query.MenuQuery;

/**
 * 菜单业务逻辑接口
 * 
 * @author Mike
 *
 */
public interface MenuService {
	
	void addMenu(Menu menu);
	
	void updateMenu(Menu menu);
	
	void delMenu(Menu Menu);
	
	Menu getMenuById(int menuId);
	
	/**
	 * 根据父节点id查询子节点
	 * @param pMenuId
	 * @return
	 */
	List queryMenuItems(int pMenuId);
	
	/**
	 * 分页查询
	 * @param MenuQuery
	 * @return
	 */
	PageQuery findPagedMenu(MenuQuery menuQuery);
	
	/**
	 * 更新是否叶子节点
	 * @param MenuId
	 * @param leaf
	 */
	void updateLeaf(int menuId,String leaf);
	
	/**
	 * 判断是否有子节点
	 * @param MenuId
	 * @return
	 */
	boolean hasChildren(int menuId);
	
	/**
	 * 查询所有菜单
	 * @return
	 */
	List<Menu> queryAllMenu();
	
}
