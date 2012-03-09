package com.heke.framework.security.web.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heke.framework.common.tree.Tree;
import com.heke.framework.security.entity.Menu;
import com.heke.framework.security.entity.User;
import com.heke.framework.security.service.MenuService;
import com.heke.framework.security.service.UserService;

@Controller
@RequestMapping(value = "/index")
public class IndexController {
	
	@Resource
	private MenuService menuService;
	
	@Resource
	private UserService userService;

	/**
	 * 首页初始化
	 * @return
	 */
	@RequestMapping(value = "/indexInit")
	public String indexInit() {
		//TODO
		
		return "frame/index";
	}
	
	@RequestMapping(value = "/main")
	public String main() {
		//TODO
		
		return "frame/main";
	}

	/**
	 * 查询已经授权给用户的二级菜单
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getGranted2LevelMenus")
	@ResponseBody
	public List<Menu> getGranted2LevelMenus(HttpServletRequest request, HttpServletResponse response) {
		
		//获取登录用户
		User user = (User)request.getSession().getAttribute("user");
		int userId = 0;
		if (user != null) {
			userId = user.getUserId();
		}
		
		List<Menu> granted2LevelMenus = userService.get2LevelMenusByUserId(userId);
		
		return granted2LevelMenus;
	}
	
	/**
	 * 根据菜单id、人员id查询，已经授权给人员的子菜单
	 * @param pMenuId
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getGrantedMenu")
	@ResponseBody
	public List<Tree> getGrantedMenu(HttpServletRequest request, HttpServletResponse response,int pMenuId) {
		//获取登录用户
		User user = (User)request.getSession().getAttribute("user");
		int userId = 0;
		if (user != null) {
			userId = user.getUserId();
		}
		
		List<Tree> treeList = userService.getGrantedMenu(pMenuId, userId);
		return treeList;
	}
}
