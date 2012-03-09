package com.heke.framework.security.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.heke.framework.security.dao.UserDao;
import com.heke.framework.security.entity.Dept;
import com.heke.framework.security.entity.Menu;
import com.heke.framework.security.entity.Role;
import com.heke.framework.security.entity.User;
import com.heke.framework.security.query.UserQuery;
import com.heke.framework.security.service.DeptService;
import com.heke.framework.security.service.MenuService;
import com.heke.framework.security.service.RoleService;
import com.heke.framework.security.service.UserService;

@Service
@Transactional
public class UserServiceImp implements UserService {

	@Resource
	private UserDao userDao;
	
	@Resource
	private DeptService deptService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private MenuService menuService;
	
	@Override
	public void addUser(User user) {
		userDao.save(user);
	}

	@Override
	public void delUser(User user) {
		userDao.remove(user);
	}

	@Override
	public PageQuery findPagedUser(UserQuery userQuery) {
		// 创建标准查询器
		Criteria criteria = userDao.createCriteria();

		// criteria.createAlias("users", "user",Criteria.LEFT_JOIN);

		//排序
		criteria.addOrder(Order.asc("userId")).addOrder(Order.asc("userName"));

		// 如果用户名称或UID不为空
		if (StringUtils.isNotBlank(userQuery.getUserName())) {
			criteria.add(Restrictions.like("userName", userQuery.getUserName(), MatchMode.START)
					.ignoreCase());
		}

		//所属部门(deptId:1 ==> 根部门)
		if(userQuery.getDeptId() > 0){
			criteria.add(Restrictions.eq("dept.deptId", userQuery.getDeptId()));
		}
		
		return userDao.pagedByCriteria(criteria, userQuery);
	}

	@Override
	public User getUserByAccount(String account) {
		String hql = "from User u where u.account = ?";
		List<User> users = userDao.find(hql, account);
		if (users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	@Override
	public User getUserById(int userId) {
		return userDao.getById(userId);
	}

	@Override
	public void updateUser(User user) {
		
		User u = userDao.getById(user.getUserId());
			
		u.setAccount(user.getAccount());
		u.setDept(user.getDept());
		u.setEmail(user.getEmail());
		u.setEnabled(user.getEnabled());
		u.setLocked(user.getLocked());
		u.setPassword(user.getPassword());
		u.setPhone(user.getPhone());
		u.setRemark(user.getRemark());
		u.setSex(user.getSex());
		u.setUserName(user.getUserName());
		u.setUserType(user.getUserType());
		
		userDao.save(u);
	}

	@Override
	public boolean accountIsExist(String account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Role> getGrantedRolesByUserId(int userId) {
		String hql = "select u.roles from User u where u.userId = ?";
		return userDao.find(hql, userId);
	}

	@Override
	public void saveSelectedMenu(int userId, int[] menuIds) {
		User user = userDao.getById(userId);
		user.setMenus(new HashSet<Menu>(0));
		userDao.save(user);
		
		if (menuIds.length != 0) {
			for (int i=0; i<menuIds.length; i++) {
				Menu m = menuService.getMenuById(menuIds[i]);
				user.getMenus().add(m);
			}
			userDao.save(user);
		}
		
	}

	@Override
	public void saveSelectedRole(int userId, int[] roleIds) {
		User user = userDao.getById(userId);
		user.setRoles(new HashSet<Role>(0));
		userDao.save(user);
	
		if (roleIds.length != 0) {
			for (int i=0; i<roleIds.length; i++) {
				Role r = roleService.getRoleById(roleIds[i]);
				user.getRoles().add(r);
			}
			userDao.save(user);
		}
	}

	@Override
	public Tree getRolesForGrant(int userId) {
		Tree needTree = null;
		List<Tree> allTree = new ArrayList<Tree>();
		
		//授权人员
		User user = userDao.getById(userId);
		//已经授权给人员的角色
		List<Role> grantedRoleList = getGrantedRolesByUserId(userId);
		
		//人员所在部门
		Dept userInDept = user.getDept();
		
		//allDept ==> allTree
		List<Dept> allDept = deptService.queryAllDept();
		for (int i=0; i<allDept.size(); i++) {
			Dept d = allDept.get(i);
			Tree t = new Tree();
			t.setId(d.getDeptId());
			t.setText(d.getDeptName());
			if (d.getParent() != null) {
				t.setPid(d.getParent().getDeptId());
			}
			t.getAttributes().setNodeType("dept");
			allTree.add(t);
		}
		
		//组装部门树
		for (int i=0; i<allTree.size(); i++) {
			Tree t = allTree.get(i);
			if (t.getPid() != 0) {
				Tree parent = TreeUtil.getTree(t.getPid(), allTree);
				parent.getChildren().add(t);
			}
			if (t.getId() == userInDept.getDeptId()) {
				needTree = t;
			}
		}
		
		//往部门树添加角色节点
		for (int i=0; i<allTree.size(); i++) {
			Tree deptTree = allTree.get(i);
			List<Role> roleList = roleService.getRoleListByDeptId(deptTree.getId());
			for (int j=0; j<roleList.size(); j++) {
				Role r = roleList.get(j);
				Tree roleTree = new Tree();
				roleTree.setId(r.getRoleId());
				roleTree.setText(r.getRoleName()+"[角色]");
				roleTree.getAttributes().setNodeType("role");
				//已授权角色
				if (grantedRoleList.contains(r)) {
					roleTree.setChecked(true);
				}
				//部门树节点添加角色节点
				deptTree.getChildren().add(roleTree);
			}
		} 
		
		return needTree;
	}
	
	@Override
	public List<Menu> getGrantedMenuByUserId(int userId) {
		String hql = "select u.menus from User u where u.userId = ?";
		return userDao.find(hql, userId);
	}

	/*
	 * 查询菜单树：给人员授权
	 * (non-Javadoc)
	 * @see com.heke.framework.security.service.UserService#getMenusForGrant(int)
	 */
	@Override
	public Tree getMenusForGrant(int userId, int operateUserId) {
		Tree rootTree = null;
		List<Tree> allTree = new ArrayList<Tree>();
		List<Menu> allMenu = null;
		
		//查询操作人拥有菜单
		if (operateUserId == 1) {//超级管理员(super)
			allMenu = menuService.queryAllMenu();
		} else {
			allMenu = getGrantedAllMenuByUserId(operateUserId);
		}
		
		//查询已授权给人员的菜单
		List<Menu> grantedMenuForUser = getGrantedMenuByUserId(userId);

		for (int i=0; i<allMenu.size(); i++) {
			Menu m = allMenu.get(i);
			Tree t = new Tree();
			t.setId(m.getMenuId());
			t.setText(m.getMenuName());
			if (m.getParent() != null) {
				t.setPid(m.getParent().getMenuId());
			}
			if (grantedMenuForUser.contains(m)) {
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

	/*
	 * (non-Javadoc)
	 * @see com.heke.framework.security.service.UserService#getRolesByUserId(int)
	 */
	@Override
	public List<Role> getRolesByUserId(int userId) {
		String hql = "select u.roles from User u where u.userId = ?";
		return userDao.find(hql, userId);
	}

	@Override
	public List<Menu> getGrantedAllMenuByUserId(int userId) {
		Set<Menu> userOfMenuSet = new HashSet<Menu>(0);
		List<Menu> grantedMenus = getGrantedMenuByUserId(userId);
		List<Role> grantedRoles = getRolesByUserId(userId);
		
		//grantedMenus ==> userOfMenuSet
		for (int i=0; i<grantedMenus.size(); i++) {
			Menu m = grantedMenus.get(i);
			userOfMenuSet.add(m);
		}
		
		//把人员拥有角色的菜单添加到userOfMenuSet
		for (int i=0; i<grantedRoles.size(); i++) {
			Role r = grantedRoles.get(i);
			List<Menu> roleOfMenus = roleService.getGrantedMenusByRoleId(r.getRoleId());
			for (int j=0; j<roleOfMenus.size(); j++) {
				Menu m = roleOfMenus.get(j);
				userOfMenuSet.add(m);
			}
		}
		
		List<Menu> userOfMenuList = new ArrayList<Menu>();
		//set ==> list
		for (Iterator<Menu> ite = userOfMenuSet.iterator(); ite.hasNext(); ) {
			Menu m = ite.next();
			userOfMenuList.add(m);
		}
		
		return userOfMenuList;
	}

	/*
	 * 查询授权给用户的二级菜单
	 * (non-Javadoc)
	 * @see com.heke.framework.security.service.UserService#get2LevelMenusByUserId(int)
	 */
	@Override
	public List<Menu> get2LevelMenusByUserId(int userId) {
		List<Menu> userOfMenuList = getGrantedAllMenuByUserId(userId);
		List<Menu> user2LevelOfMenuList = new ArrayList<Menu>();
		
		for (Iterator<Menu> ite = userOfMenuList.iterator(); ite.hasNext(); ) {
			Menu m = ite.next();
			if (m.getParent() != null && m.getParent().getMenuId() == 1) {
				user2LevelOfMenuList.add(m);
			}
		}
		
		//排序
		Collections.sort(user2LevelOfMenuList);
		
		return user2LevelOfMenuList;
	}

	/*
	 * 根据父菜单id、人员id查询已经授权给人员的子菜单
	 * (non-Javadoc)
	 * @see com.heke.framework.security.service.UserService#getGrantedMenu(int, int)
	 */
	@Override
	public List<Tree> getGrantedMenu(int pMenuId, int userId) {
		List<Menu> grantedAllMenuList = getGrantedAllMenuByUserId(userId);
		List<Tree> grantedAllTree = new ArrayList<Tree>();
		List<Tree> needTreeList = new ArrayList<Tree>(); 
		
		//grantedAllMenuList  => grantedAllTree
		for (int i=0; i<grantedAllMenuList.size(); i++) {
			Menu m = grantedAllMenuList.get(i);
			Tree t = new Tree();
			t.setId(m.getMenuId());
			t.setText(m.getMenuName());
			t.setSortNo(m.getSortNo());
			if (m.getParent() != null) {
				t.setPid(m.getParent().getMenuId());
			}
			t.getAttributes().setUrl(m.getRequest());
			grantedAllTree.add(t);
		}
		
		for (int i=0; i<grantedAllTree.size(); i++) {
			Tree t = grantedAllTree.get(i);
			if (t.getPid() != 0) {
				//拿到父节点
				Tree parent = TreeUtil.getTree(t.getPid(), grantedAllTree);
				parent.addChild(t);
			}
			if (t.getPid() != 0 && t.getPid() == pMenuId) {
				needTreeList.add(t);
			}
		}
		
		Collections.sort(needTreeList);
		
		return needTreeList;
	}

}
