package com.heke.framework.security.web.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heke.framework.security.entity.User;
import com.heke.framework.security.service.UserService;

/**
 * 登录控制器
 * 
 * @author Mike
 *
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
	@Resource
	private UserService userService;

	/**
	 * 进入登录页面
	 * @return
	 */
	@RequestMapping(value = "/loginInit")
	public String loginInit() {
		
		//TODO
		
		return "frame/login";
	}
	
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping(value = "/doLogin")
	public String doLogin(HttpServletRequest request, HttpServletResponse response) {
		
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		
		User user = userService.getUserByAccount(account);
		
		//用户不存在
		if (user == null) {
			
			//TODO
			
			request.setAttribute("message", "账户不存在!");
			
			return "frame/login";
		}
		
		//密码不正确
		if (!user.getPassword().equals(password)) {
			
			//TODO
			
			request.setAttribute("message", "密码有误!");
			return "frame/login";
		}
		
		//往session中保存人员
		request.getSession().setAttribute("user", user);
		
		return "redirect:/index/indexInit.do";
	}
}
