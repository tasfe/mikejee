package com.heke.framework.security.service.imp;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.heke.framework.security.entity.Menu;
import com.heke.framework.security.service.RoleService;

//表示该测试用例是运用junit4进行测试，也可以换成其他测试框架
//(SpringJUnit4ClassRunner.class)
//该路径的设置时相当于该单元测试所在的路径，也可以用classpath进行设置，该设置还有一个inheritLocations的属性，默认为true,表示子类可以继承该设置。
@ContextConfiguration(locations = "/applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class RoleServiceImpTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Resource
	private RoleService roleService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetGrantedMenusByRoleId() {
		List<Menu> menuList = roleService.getGrantedMenusByRoleId(111);
		Assert.assertEquals(0, menuList.size());
	}
}
