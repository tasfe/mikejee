package com.heke.framework.security.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heke.framework.common.util.SysConstants;
import com.heke.framework.common.web.PageQuery;
import com.heke.framework.security.dao.MenuDao;
import com.heke.framework.security.entity.Dept;
import com.heke.framework.security.entity.Menu;
import com.heke.framework.security.query.MenuQuery;
import com.heke.framework.security.service.MenuService;

@Service
@Transactional
public class MenuServiceImp implements MenuService {
	
	@Resource
	private MenuDao menuDao;

	@Override
	public void addMenu(Menu menu) {
		menu.setLeaf(SysConstants.IS_LEAF);
		menuDao.save(menu);
		
		Menu parent = menuDao.getById(menu.getParent().getMenuId());
		if (SysConstants.IS_LEAF.equals(parent.getLeaf())) {//如果父节点是叶子节点，则更新为树枝节点
			updateLeaf(parent.getMenuId(), SysConstants.IS_NOT_LEAF);
		}
	}

	@Override
	public void updateMenu(Menu menu) {

		// perDept对象处于持久状态（persistent）
		Menu perMenu = menuDao.getById(menu.getMenuId());
		perMenu.setMenuName(menu.getMenuName());
		perMenu.setRemark(menu.getRemark());
		perMenu.setSortNo(menu.getSortNo());
		perMenu.setRequest(menu.getRequest());
		//TODO
		
		Menu oldParent = perMenu.getParent();
		Menu newParent = menu.getParent();
		
		//判断是否需要更新父节点
		boolean isUpdateParent = false;
		if (newParent != null && oldParent.getMenuId() != newParent.getMenuId()) {
			newParent = menuDao.getById(newParent.getMenuId());
			perMenu.setParent(newParent);
			isUpdateParent = true;
		}
	
		menuDao.save(perMenu);
		
		//更新leaf
		if (isUpdateParent) {
			//1
			if (!hasChildren(oldParent.getMenuId())) {
				updateLeaf(oldParent.getMenuId(), SysConstants.IS_LEAF);
			}
			//2
			if (newParent.getLeaf().equals(SysConstants.IS_LEAF)) {
				updateLeaf(newParent.getMenuId(), SysConstants.IS_NOT_LEAF);
			}
		}
	}
	
	@Override
	public void delMenu(Menu menu) {
		
		//TODO
		
		if (hasChildren(menu.getMenuId())) {//有子节点，不允许删除
			throw new RuntimeException("该菜单有子节点，不允许删除!");
		}
		
		Menu m = menuDao.getById(menu.getMenuId());
		Menu parent = m.getParent();
		
		menuDao.removeById(menu.getMenuId());
	
		//update leaf
		if (!hasChildren(parent.getMenuId())) {
			updateLeaf(parent.getMenuId(), SysConstants.IS_LEAF);
		}

	}

	@Override
	public PageQuery findPagedMenu(MenuQuery menuQuery) {
		// 创建标准查询器
		Criteria criteria = menuDao.createCriteria();

		// criteria.createAlias("users", "user",Criteria.LEFT_JOIN);

		//排序
		criteria.addOrder(Order.asc("sortNo")).addOrder(Order.asc("menuId")).addOrder(Order.asc("menuName"));

		// 如果菜单名称或UID不为空
		if (StringUtils.isNotBlank(menuQuery.getMenuName())) {
			criteria.add(Restrictions.like("menuName", menuQuery.getMenuName(), MatchMode.START)
					.ignoreCase());
		}
		
		//部门id
//		if(deptQuery.getDeptId() > 0){
//			criteria.add(Restrictions.eq("deptId", deptQuery.getDeptId()));
//		}
		
		//父节点 or 
		if (menuQuery.getParentId() > 0) {
			criteria.add(Restrictions.or(
							Restrictions.eq("parent.menuId", menuQuery.getParentId()),
							Restrictions.eq("menuId", menuQuery.getParentId())
							)
			);
		}
		
		//业务对照码customId
//		if (StringUtils.isNotBlank(deptQuery.getCustomId())) {
//			criteria.add(Restrictions.eq("customId", deptQuery.getCustomId()));
//		}

		return menuDao.pagedByCriteria(criteria, menuQuery);
	}

	@Override
	public Menu getMenuById(int menuId) {
		return menuDao.getById(menuId);
	}

	@Override
	public boolean hasChildren(int menuId) {
		String hql = "from Menu m where m.parent.menuId = ?";
		int size = menuDao.find(hql, menuId).size();
		if (size > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List queryMenuItems(int menuId) {
		List children = null;
		if (menuId > 0) {
			String hql = "from Menu m where m.parent.menuId = ? order by m.sortNo";
			children = menuDao.find(hql, menuId);
		} else {
			String hql = "from Menu m where m.parent.menuId is null order by m.sortNo";
			children = menuDao.find(hql);
		}
		
		return children;
	}

	@Override
	public void updateLeaf(int menuId, String leaf) {
		String hql = "update Menu m set m.leaf = ? where m.menuId = ?";
		menuDao.createQuery(hql, leaf, menuId).executeUpdate();
	}

	@Override
	public List<Menu> queryAllMenu() {
		String hql = "from Menu m order by m.sortNo";
		List<Menu> allMenu = menuDao.find(hql);
		return allMenu;
	}
}
