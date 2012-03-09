/**
 * 
 */
package com.heke.framework.common.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author gonglei
 * 
 */
public class SystemContextLoaderListener extends ContextLoaderListener {
	/**
	 * spring上下文环境
	 */
	private static ApplicationContext applicationContext;

	/**
	 * servlet上下文环境
	 */
	private static ServletContext servletContext;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		servletContext = event.getServletContext();
		applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

		//启动spring 
		
	}

	/**
	 * @return the applicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @return the servletContext
	 */
	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
}
