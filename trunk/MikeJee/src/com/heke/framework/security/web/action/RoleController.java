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
import com.heke.framework.security.entity.Role;
import com.heke.framework.security.entity.User;
import com.heke.framework.security.query.RoleQuery;
import com.heke.framework.security.service.DeptService;
import com.heke.framework.security.service.RoleService;

/**
 * 角色控制器
 * 
 * @author Mike
 *
 */
@Controller
@RequestMapping(value = "/security/role")
public class RoleController {
	
	private Logger log = Logger.getLogger(RoleController.class);
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private DeptService deptService;
	
	@RequestMapping(value = "/manageRole")
	public String manageRole() {
		return "frame/security/manageRole";
	}
	
	@RequestMapping(value = "/queryRoles")
	@ResponseBody
	public RoleQuery queryDepts(RoleQuery query,HttpServletRequest request, HttpServletResponse response) {
		
		if (query.getDeptId() == 0) {
			//获取登录用户
			User user = (User)request.getSession().getAttribute("user");
			query.setDeptId(user.getDept().getDeptId());
		}
		
		RoleQuery roleQuery = (RoleQuery)roleService.findPagedRole(query);
		
		return roleQuery;
	}
	
	@RequestMapping(value = "/toAddRole")
	public String toAddRole(int deptId,ModelMap modelMap) {
		log.info(deptId);
		//查询父节点
		Dept dept = deptService.getDeptById(deptId);
		modelMap.addAttribute("dept",dept);
		
		return "frame/security/addRole";
	}
	
	@RequestMapping(value = "/addRole")
	public String addRole(@ModelAttribute("role") Role role, ModelMap modelMap) {	
		//log.info(dept.getDeptName());
		//log.info(dept.getParent().getDeptId());
		
		roleService.addRole(role);
		
		return "redirect:roleSuccess.do?roleId="+role.getRoleId();
	}
	
	@RequestMapping(value = "/roleSuccess", method = RequestMethod.GET)
	public String roleSuccess(@RequestParam("roleId") int roleId, ModelMap modelMap) {
		modelMap.addAttribute("roleId", roleId);
		return "frame/security/roleSuccess";
	}
	
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/getChildrenDeptByPid")
//	@ResponseBody
//	public List getChildrenDeptByPid(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		
//		//获取登录用户
//		User user = (User)request.getSession().getAttribute("user");
//		
//		List list = new ArrayList();
//		int _pid = 0;
//		String pid = request.getParameter("id");
//		
//		if (pid == null || "".equals(pid)) {//初始化，查询出人员所在部门展示
//			_pid = user.getDept().getDeptId();
//			Tree tree = new Tree();
//			Dept d = user.getDept();
//			Tree t = new Tree();
//			t.setId(d.getDeptId());
//			t.setText(d.getDeptName());
//			if ("0".equals(d.getLeaf())) {//树枝节点
//				t.setState("closed");
//			} else {//叶子节点
//				t.setState("");
//			}
//			list.add(t);
//		} else {//展示子节点
//			_pid = Integer.parseInt(pid);
//			List children = deptService.queryDeptItems(_pid);
//			
//			for (Object o : children) {
//				Dept d = (Dept)o;
//				Tree t = new Tree();
//				t.setId(d.getDeptId());
//				t.setText(d.getDeptName());
//				if ("0".equals(d.getLeaf())) {//树枝节点
//					t.setState("closed");
//				} else {//叶子节点
//					t.setState("");
//				}
//				list.add(t);
//			}
//		}
//		
//		//spring会自动转为为json格式数据
//		return list;
//	}
	
	@RequestMapping(value = "/toUpdateRole")
	public String toUpdateRole(int roleId, ModelMap modelMap) {
		
		Role role = roleService.getRoleById(roleId);
		modelMap.addAttribute("role", role);
		
		return "frame/security/updateRole";
	}
	
	@RequestMapping(value = "/updateRole")
	public String updateRole(@ModelAttribute("role") Role role, ModelMap modelMap) {
		
		roleService.updateRole(role);
		
		return "redirect:roleSuccess.do?roleId="+role.getRoleId();
	}
	
	@RequestMapping(value = "/delRole")
	@ResponseBody
	public String delRole(int roleId) {	
		
		String flag = "success";
		Role r = new Role();
		r.setRoleId(roleId);
		try {
			roleService.delRole(r);
		} catch (Exception e) {
			e.printStackTrace();
			flag = "error";
		}
		
		return flag;
	}
	
	/**
	 * to角色授权页面
	 * @return
	 */
	@RequestMapping(value = "/toRoleAuthorize")
	public String toRoleAuthorize(int roleId, ModelMap modelMap) {
		
		modelMap.addAttribute("roleId", roleId);
		
		return "frame/security/roleAuthorize";
	}
	
	/**
	 * 查询菜单：角色授权
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getMenusForGrant")
	@ResponseBody
	public List<Tree> getMenusForGrant(int roleId,HttpServletRequest request, HttpServletResponse response) {
		//获取登录用户
		User user = (User)request.getSession().getAttribute("user");
		
		Tree rootTree = roleService.getMenusForGrant(roleId,user.getUserId());
		List<Tree> treeList = new ArrayList<Tree>(); 
		treeList.add(rootTree);
		return treeList;
	}
	
	@RequestMapping(value = "/saveSelectedMenuForRole")
	@ResponseBody
	public String saveSelectedMenuForRole(int roleId, String menuIds) {
		
		log.info("role:"+roleId);
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
			roleService.saveSelectedMenu(roleId, ids);
		} catch (Exception e) {
			e.printStackTrace();
			flag = "error";
		}
		
		return flag;
	}
}
