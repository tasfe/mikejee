package com.heke.framework.security.service.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heke.framework.common.tree.Tree;
import com.heke.framework.common.tree.TreeUtil;
import com.heke.framework.common.web.PageQuery;
import com.heke.framework.security.dao.RoleDao;
import com.heke.framework.security.entity.Menu;
import com.heke.framework.security.entity.Role;
import com.heke.framework.security.query.RoleQuery;
import com.heke.framework.security.service.MenuService;
import com.heke.framework.security.service.RoleService;
import com.heke.framework.security.service.UserService;

@Service
@Transactional
public class RoleServiceImp implements RoleService {

	@Resource
	private RoleDao roleDao;
	
	@Resource
	private MenuService menuService;
	
	@Resource
	private UserService userService;
	
	@Override
	public void addRole(Role role) {
		roleDao.save(role);
	}

	@Override
	public void delRole(Role role) {
		roleDao.remove(role);
	}

	@Override
	public PageQuery findPagedRole(RoleQuery roleQuery) {

		// 创建标准查询器
		Criteria criteria = roleDao.createCriteria();
		
		//排序
		criteria.addOrder(Order.asc("roleId"));

		// 如果角色名称或UID不为空
		if (StringUtils.isNotBlank(roleQuery.getRoleName())) {
			criteria.add(Restrictions.like("roleName", roleQuery.getRoleName(), MatchMode.START)
					.ignoreCase());
		}
		
		//所属部门 (deptId:1 ==> 根部门)
		if(roleQuery.getDeptId() > 0 && roleQuery.getDeptId() != 1){
			criteria.add(Restrictions.eq("dept.deptId", roleQuery.getDeptId()));
		}
		
		return roleDao.pagedByCriteria(criteria, roleQuery);
	}

	@Override
	public Role getRoleById(int roleId) {
		return roleDao.getById(roleId);
	}

	@Override
	public void updateRole(Role role) {
		Role r = roleDao.getById(role.getRoleId());
		r.setDept(role.getDept());
		r.setLocked(role.getLocked());
		r.setRemark(role.getRemark());
		r.setRoleName(role.getRoleName());
		r.setRoleType(role.getRoleType());
		
		roleDao.save(r);
	}

	@Override
	public List<Role> getRoleListByDeptId(int deptId) {
		String hql = "from Role r where r.dept.deptId = ?";
		return roleDao.find(hql, deptId);
	}

	@Override
	public void saveSelectedMenu(int roleId, int[] menuIds) {
		Role role = roleDao.getById(roleId);
		//删除
		//Set<Menu> menus = role.getMenus();
		//for (Iterator ite = menus.iterator(); ite.hasNext();) {
		//	role.getMenus().remove(ite.next());
		//}
		role.setMenus(new HashSet<Menu>(0));
		roleDao.save(role);
	
		if (menuIds.length != 0) {
			for (int i=0; i<menuIds.length; i++) {
				Menu m = menuService.getMenuById(menuIds[i]);
				role.getMenus().add(m);
			}
			roleDao.save(role);
		}
	}

	@Override
	public void saveSelectedUser(int roleId, int[] userIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Menu> getGrantedMenusByRoleId(int roleId) {
		String hql = "select r.menus from Role r where r.roleId = ?";
		List<Menu> menuList = roleDao.find(hql, roleId);
		
		return menuList;
	}

	/*
	 * 查询菜单树：角色授权
	 * (non-Javadoc)
	 * @see com.heke.framework.security.service.RoleService#getMenusForGrant(int)
	 */
	@Override
	public Tree getMenusForGrant(int roleId, int operateUserId) {
		
		Tree rootTree = null;
		List<Tree> allTree = new ArrayList<Tree>();
		//查询所有的菜单
		//List<Menu> allMenu = menuService.queryAllMenu();
		//查询用户所拥有的菜单
		List<Menu> allMenu = userService.getGrantedAllMenuByUserId(operateUserId);
		//查询已授权给角色的菜单
		List<Menu> grantedMenuForRole = getGrantedMenusByRoleId(roleId);

		for (int i=0; i<allMenu.size(); i++) {
			Menu m = allMenu.get(i);
			Tree t = new Tree();
			t.setId(m.getMenuId());
			t.setText(m.getMenuName());
			if (m.getParent() != null) {
				t.setPid(m.getParent().getMenuId());
			}
			if (grantedMenuForRole.contains(m)) {
				t.setChecked(true);
			}
			allTree.add(t);
		}
		
		//-----------关键点-----------
		//组装树
		for (int i=0; i<allTree.size(); i++) {
			Tree t = allTree.get(i);
			if (t.getPid() != 0) {
				Tree parent = TreeUtil.getTree(t.getPid(), allTree);
				parent.getChildren().add(t);
			} else {
				rootTree = t;
			}
		}
		
		return rootTree;
	}
	
}
