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
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heke.framework.security.entity.Dept;
import com.heke.framework.security.entity.Menu;
import com.heke.framework.security.query.MenuQuery;
import com.heke.framework.security.service.MenuService;

/**
 * 菜单控制器
 * 
 * @author Mike
 *
 */
@Controller
@RequestMapping(value = "/security/menu")
public class MenuController {
	
	private Logger log = Logger.getLogger(MenuController.class);

	@Resource
	private MenuService menuService;
	
	@RequestMapping(value = "/manageMenu")
	public String manageMenu() {
		return "frame/security/manageMenu";
	}
	
	@RequestMapping(value = "/queryMenus")
	@ResponseBody
	public MenuQuery queryDepts(MenuQuery query) {
		
		//父节点为根节点时，查询所有
		if (query.getParentId() == 1) {
			query.setParentId(0);
		}
		
		MenuQuery menuQuery = (MenuQuery)menuService.findPagedMenu(query);
		
		return menuQuery;
	}
	
	@RequestMapping(value = "/toAddMenu")
	public String toAddMenu(int pid,ModelMap modelMap) {
		log.info(pid);
		//查询父节点
		Menu parent = menuService.getMenuById(pid);
		modelMap.addAttribute("parent",parent);
		
		return "frame/security/addMenu";
	}
	
	@RequestMapping(value = "/addMenu")
	public String addMenu(@ModelAttribute("menu") Menu menu, ModelMap modelMap) {	
		
		menuService.addMenu(menu);
		
		return "redirect:menuSuccess.do?menuId="+menu.getMenuId();
	}
	
	@RequestMapping(value = "/toUpdateMenu")
	public String toUpdateMenu(int menuId, ModelMap modelMap) {
		
		Menu menu = menuService.getMenuById(menuId);
		modelMap.addAttribute("menu", menu);
		
		return "frame/security/updateMenu";
	}
	
	@RequestMapping(value = "/updateMenu")
	public String updateDept(@ModelAttribute("menu") Menu menu, ModelMap modelMap) {
		
		menuService.updateMenu(menu);
		
		return "redirect:menuSuccess.do?menuId="+menu.getMenuId();
	}
	
	@RequestMapping(value = "/delMenu")
	@ResponseBody
	public String delMenu(int menuId) {	
		
		log.info(menuId);
		
		String flag = "success";
		Menu m = new Menu();
		m.setMenuId(menuId);
		try {
			menuService.delMenu(m);
		} catch (Exception e) {
			e.printStackTrace();
			flag = "error";
		}
		
		return flag;
	}
	
	@RequestMapping(value = "/getChildrenByPid")
	@ResponseBody
	public List getChildrenByPid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String pid = request.getParameter("id");
		int _pid = 0;
		
		if (pid != null && !"".equals(pid)) {
			_pid = Integer.parseInt(pid);
		}
		
		List children = menuService.queryMenuItems(_pid);
		
		List list = new ArrayList();
		for (Object o : children) {
			Map map = new HashMap();
			Menu m = (Menu)o;
			map.put("id", m.getMenuId());
			map.put("text", m.getMenuName());
			if ("0".equals(m.getLeaf())) {//树枝节点
				map.put("state", "closed");
			} else {//叶子节点
				map.put("state", "");
			}
			list.add(map);
		}
		
		return list;
	}
	
	@RequestMapping(value = "/menuSuccess", method = RequestMethod.GET)
	public String menuSuccess(@RequestParam("menuId") int menuId, ModelMap modelMap) {
		modelMap.addAttribute("menuId", menuId);
		return "frame/security/menuSuccess";
	}
}
