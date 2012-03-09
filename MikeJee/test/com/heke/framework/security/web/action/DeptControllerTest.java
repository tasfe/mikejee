package com.heke.framework.security.web.action;

import org.junit.Test;

import com.heke.framework.common.web.JUnitControllerBase;

public class DeptControllerTest extends JUnitControllerBase {

	@Test
	public void testQueryDepts() throws Exception {
		this.request.setServletPath("/security/queryDeptsqq");
		//this.request.addParameter("deptName", "测试数据");
		this.excuteAction(this.request, this.response);
		//Assert.assertTrue(response.getContentAsString().contains("测试数据"));
		System.out.println(response.getContentAsString());
	}

}
