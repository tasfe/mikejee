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
import com.heke.framework.security.query.DeptQuery;
import com.heke.framework.security.service.DeptService;

/**
 * 部门控制器
 * 
 * @author Mike
 *
 */
@Controller
@RequestMapping(value = "/security/dept")
public class DeptController {
	
	private Logger log = Logger.getLogger(DeptController.class);
	
	@Resource
	private DeptService deptService;
	
	@RequestMapping(value = "/manageDept")
	public String manageDept() {
		return "frame/security/manageDept";
	}
	
	@RequestMapping(value = "/queryDepts")
	@ResponseBody
	public DeptQuery queryDepts(DeptQuery query,HttpServletRequest request, HttpServletResponse response) {
		
		if (query.getParentId() == 0) {
			//获取登录用户
			User user = (User)request.getSession().getAttribute("user");
			query.setParentId(user.getDept().getDeptId());
		}
		
		DeptQuery deptQuery = (DeptQuery)deptService.findPagedDept(query);
		
		return deptQuery;
	}
	
	@RequestMapping(value = "/toAddDept")
	public String toAddDept(int pid,ModelMap modelMap) {
		log.info(pid);
		//查询父节点
		Dept parent = deptService.getDeptById(pid);
		modelMap.addAttribute("parent",parent);
		
		return "frame/security/addDept";
	}
	
	@RequestMapping(value = "/addDept")
	public String addDept(@ModelAttribute("dept") Dept dept, ModelMap modelMap) {	
		log.info(dept.getDeptName());
		log.info(dept.getParent().getDeptId());
		
		deptService.addDept(dept);
		
		return "redirect:deptSuccess.do?deptId="+dept.getDeptId();
	}
	
	@RequestMapping(value = "/toUpdateDept")
	public String toUpdateDept(int deptId, ModelMap modelMap) {
		
		Dept dept = deptService.getDeptById(deptId);
		modelMap.addAttribute("dept", dept);
		
		return "frame/security/updateDept";
	}
	
	@RequestMapping(value = "/updateDept")
	public String updateDept(@ModelAttribute("dept") Dept dept, ModelMap modelMap) {
		
		deptService.updateDept(dept);
		
		return "redirect:deptSuccess.do?deptId="+dept.getDeptId();
	}
	
	@RequestMapping(value = "/delDept")
	@ResponseBody
	public String delDept(int deptId) {	
		
		log.info(deptId);
		
		String flag = "success";
		Dept d = new Dept();
		d.setDeptId(deptId);
		try {
			deptService.delDept(d);
		} catch (Exception e) {
			e.printStackTrace();
			flag = "error";
		}
		
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getChildrenByPid")
	@ResponseBody
	public List getChildrenByPid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//获取登录用户
		User user = (User)request.getSession().getAttribute("user");
		
		List list = new ArrayList();
		int _pid = 0;
		String pid = request.getParameter("id");
		
		if (pid == null || "".equals(pid)) {//初始化，查询出人员所在部门展示
			_pid = user.getDept().getDeptId();
			Tree tree = new Tree();
			Dept d = user.getDept();
			Tree t = new Tree();
			t.setId(d.getDeptId());
			t.setText(d.getDeptName());
			if ("0".equals(d.getLeaf())) {//树枝节点
				t.setState("closed");
			} else {//叶子节点
				t.setState("");
			}
			list.add(t);
		} else {//展示子节点
			_pid = Integer.parseInt(pid);
			List children = deptService.queryDeptItems(_pid);
			
			for (Object o : children) {
				Dept d = (Dept)o;
				Tree t = new Tree();
				t.setId(d.getDeptId());
				t.setText(d.getDeptName());
				if ("0".equals(d.getLeaf())) {//树枝节点
					t.setState("closed");
				} else {//叶子节点
					t.setState("");
				}
				list.add(t);
			}
		}
		
		//spring会自动转为为json格式数据
		return list;
	}
	
	@RequestMapping(value = "/deptSuccess", method = RequestMethod.GET)
	public String deptSuccess(@RequestParam("deptId") int deptId, ModelMap modelMap) {
		modelMap.addAttribute("deptId", deptId);
		return "frame/security/deptSuccess";
	}
}
