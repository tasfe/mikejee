package com.heke.framework.security.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heke.framework.common.tree.Tree;
import com.heke.framework.security.entity.Dept;
import com.heke.framework.security.entity.User;
import com.heke.framework.security.query.UserQuery;
import com.heke.framework.security.service.DeptService;
import com.heke.framework.security.service.UserService;

/**
 * 人员控制器
 * 
 * @author Mike
 *
 */
@Controller
@RequestMapping(value = "/security/user")
public class UserController {
	
	private Logger log = Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	
	@Resource
	private DeptService deptService;
	
	@RequestMapping(value = "/manageUser")
	public String manageUser() {
		return "frame/security/manageUser";
	}
	
	@RequestMapping(value = "/queryUsers")
	@ResponseBody
	public UserQuery queryUsers(UserQuery query,HttpServletRequest request, HttpServletResponse response) {
		
		if (query.getDeptId() == 0) {
			//获取登录用户
			User user = (User)request.getSession().getAttribute("user");
			query.setDeptId(user.getDept().getDeptId());
		}
		
		UserQuery userQuery = (UserQuery)userService.findPagedUser(query);
		
		return userQuery;
	}
	
	@RequestMapping(value = "/toAddUser")
	public String toAddUser(int deptId,ModelMap modelMap) {
		log.info(deptId);
		//查询父节点
		Dept dept = deptService.getDeptById(deptId);
		modelMap.addAttribute("dept",dept);
		
		return "frame/security/addUser";
	}
	
	@RequestMapping(value = "/addUser")
	public String addUser(@ModelAttribute("user") User user, ModelMap modelMap) {	
		//log.info(dept.getDeptName());
		//log.info(dept.getParent().getDeptId());
		
		userService.addUser(user);
		
		return "redirect:userSuccess.do?userId="+user.getUserId();
	}
	
	@RequestMapping(value = "/userSuccess", method = RequestMethod.GET)
	public String userSuccess(@RequestParam("userId") int userId, ModelMap modelMap) {
		modelMap.addAttribute("userId", userId);
		return "frame/security/userSuccess";
	}
	
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/getChildrenDeptByPid")
//	@ResponseBody
//	public List getChildrenDeptByPid(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		
//		String pid = request.getParameter("id");
//		int _pid = 0;
//		
//		if (pid != null && !"".equals(pid)) {
//			_pid = Integer.parseInt(pid);
//		}
//		
//		List children = deptService.queryDeptItems(_pid);
//		
//		List list = new ArrayList();
//		for (Object o : children) {
//			Map map = new HashMap();
//			Dept d = (Dept)o;
//			map.put("id", d.getDeptId());
//			map.put("text", d.getDeptName());
//			if ("0".equals(d.getLeaf())) {//树枝节点
//				map.put("state", "closed");
//			} else {//叶子节点
//				map.put("state", "");
//			}
//			list.add(map);
//		}
//		
//		//spring会自动转为为json格式数据
//		return list;
//	}
	
	@RequestMapping(value = "/toUpdateUser")
	public String toUpdateUser(int userId, ModelMap modelMap) {
		
		User user = userService.getUserById(userId);
		modelMap.addAttribute("user", user);
		
		return "frame/security/updateUser";
	}
	
	@RequestMapping(value = "/updateUser")
	public String updateUser(@ModelAttribute("user") User user, ModelMap modelMap) {
		
		userService.updateUser(user);
		
		return "redirect:userSuccess.do?userId="+user.getUserId();
	}
	
	@RequestMapping(value = "/delUser")
	@ResponseBody
	public String delUser(int userId) {	
		
		String flag = "success";
		User u = new User();
		u.setUserId(userId);
		try {
			userService.delUser(u);
		} catch (Exception e) {
			e.printStackTrace();
			flag = "error";
		}
		
		return flag;
	}
	
	/**
	 * to人员授权页面
	 * @return
	 */
	@RequestMapping(value = "/toUserAuthorize")
	public String toUserAuthorize(int userId, ModelMap modelMap) {
		
		modelMap.addAttribute("userId", userId);
		
		return "frame/security/userAuthorize";
	}
	
	/**
	 * 查询角色：人员授权
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getRolesForGrant")
	@ResponseBody
	public List<Tree> getRolesForGrant(int userId) {
		Tree rootTree = userService.getRolesForGrant(userId);
		List<Tree> treeList = new ArrayList<Tree>(); 
		treeList.add(rootTree);
		return treeList;
	}
	
	/**
	 * 保存选中角色：关联人员
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "/saveSelectedRolesForUser")
	@ResponseBody
	public String saveSelectedRolesForUser(int userId, String roleIds) {
		
		log.info("userId:"+userId);
		log.info("roleIds:"+roleIds);
		
		//"2,4,6" ==> [2,4,6]
		int[] ids = {}; 
		if (roleIds != null && !"".equals(roleIds)) {
			String[] strIds = roleIds.split(",");
			ids = new int[strIds.length];
			for (int i=0; i<strIds.length; i++) {
				ids[i] = Integer.parseInt(strIds[i]);
			}
		}
		
		String flag = "success";
		try {
			userService.saveSelectedRole(userId, ids);
		} catch (Exception e) {
			e.printStackTrace();
			flag = "error";
		}
		
		return flag;
	}
	
	/**
	 * 查询菜单：人员授权
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getMenusForGrant")
	@ResponseBody
	public List<Tree> getMenusForGrant(int userId,HttpServletRequest request, HttpServletResponse response) {
		//获取登录用户
		User user = (User)request.getSession().getAttribute("user");
		//操作人id
		int operateUserId = user.getUserId();
		
		Tree rootTree = userService.getMenusForGrant(userId,operateUserId);
		List<Tree> treeList = new ArrayList<Tree>(); 
		treeList.add(rootTree);
		return treeList;
	}
	
	/**
	 * 保存菜单：关联人员
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	@RequestMapping(value = "/saveSelectedMenuForUser")
	@ResponseBody
	public String saveSelectedMenuForUser(int userId, String menuIds) {
		
		log.info("userId:"+userId);
		log.info("menuIds:"+menuIds);
		
		//"2,4,6" ==> [2,4,6]
		int[] ids = {}; 
		if (menuIds != null && !"".equals(menuIds)) {
			String[] strIds = menuIds.split(",");
			ids = new int[strIds.length];
			for (int i=0; i<strIds.length; i++) {
				ids[i] = Integer.parseInt(strIds[i]);
			}
		}
		
		String flag = "success";
		try {
			userService.saveSelectedMenu(userId, ids);
		} catch (Exception e) {
			e.printStackTrace();
			flag = "error";
		}
		
		return flag;
	}
}
