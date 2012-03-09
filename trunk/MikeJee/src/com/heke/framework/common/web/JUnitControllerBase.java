/**
 * Created on 2012-1-5 上午12:03:32 <br>
 */
package com.heke.framework.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

/**
 * Controller测试基类.<br>
 * 使用方式参考com.rhyton.synframe.examples.web.DeptControllerTest.<br>
 * 注意！！！<br>
 * 1、建议写Controller单元测试，目的是减少启动web容器做页面测试的次数，节省时间，也为以后代码维护带来好处.<br>
 * 2、其它注意事项请参看：com.rhyton.synframe.examples.service.DeptManageServiceImplTest.<br>
 * 
 * @author lzh <br>
 */
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/springMVC-servlet.xml" })
public class JUnitControllerBase extends AbstractTransactionalJUnit4SpringContextTests {
	private HandlerMapping handlerMapping;
	private HandlerAdapter handlerAdapter;
	protected MockHttpServletRequest request = new MockHttpServletRequest();
	protected MockHttpServletResponse response = new MockHttpServletResponse();
	
	/**
	 * 插入测试数据时的主键，我们的序列一般是用1开始的，-999999999一般不会导致主键冲突，<br>
	 * 这里指定用一个值是为了防止序列值不断的增大，因为以后持续集成每天都会自动去跑这些测试
	 */
	protected final long id = -999999999;

	/**
	 * 执行request对象请求的action
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView excuteAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MockServletContext msc = new MockServletContext();
		msc.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, applicationContext);

		handlerMapping = (HandlerMapping) applicationContext.getBean(DefaultAnnotationHandlerMapping.class);
		handlerAdapter = (HandlerAdapter) applicationContext.getBean(applicationContext
				.getBeanNamesForType(AnnotationMethodHandlerAdapter.class)[0]);

		request.setAttribute(HandlerMapping.class.getName() + ".introspectTypeLevelMapping", true);
		HandlerExecutionChain chain = handlerMapping.getHandler(request);
		final ModelAndView model = handlerAdapter.handle(request, response, chain.getHandler());
		return model;
	}
}