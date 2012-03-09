package com.heke.framework.security.service.imp;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.heke.framework.common.web.PageQuery;
import com.heke.framework.security.entity.Dept;
import com.heke.framework.security.entity.Menu;
import com.heke.framework.security.query.DeptQuery;
import com.heke.framework.security.service.DeptService;
import com.heke.framework.security.service.MenuService;

//表示该测试用例是运用junit4进行测试，也可以换成其他测试框架
//(SpringJUnit4ClassRunner.class)
//该路径的设置时相当于该单元测试所在的路径，也可以用classpath进行设置，该设置还有一个inheritLocations的属性，默认为true,表示子类可以继承该设置。
@ContextConfiguration(locations = "/applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class DeptServiceImpTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Resource
	private DeptService deptService;

	@Resource
	private MenuService menuService;
	
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
	@Rollback(false)
	public void testOperation() {
		this.simpleJdbcTemplate.update("delete from fw_core_user_authorize");
		this.simpleJdbcTemplate.update("delete from fw_core_role_authorize");
		this.simpleJdbcTemplate.update("delete from fw_core_user");
		this.simpleJdbcTemplate.update("delete from fw_core_role");
		this.simpleJdbcTemplate.update("UPDATE fw_core_dept SET parent_id = NULL");
		this.simpleJdbcTemplate.update("delete from fw_core_dept");
	}

	@Test
	public void testAddDept() {
		Dept d = new Dept();
		d.setDeptName("测试部门kk");
		deptService.addDept(d);
		
		Dept dd = new Dept();
		dd.setDeptName("测试部门kkoo");
		dd.setParent(d);
		deptService.addDept(dd);
		
		Dept ddd = deptService.getDeptById(dd.getDeptId());
		Assert.assertEquals("测试部门kkoo", ddd.getDeptName());
		Assert.assertEquals("测试部门kk", ddd.getParent().getDeptName());
	}

	@Test
	public void testUpdateDept() {
		Dept d = new Dept();
		d.setDeptName("aaa");
		deptService.addDept(d);
		
		Dept dd = deptService.getDeptById(d.getDeptId());
		dd.setDeptName("bbb");
		deptService.updateDept(dd);
		
		Dept ddd = deptService.getDeptById(dd.getDeptId());
		
		Assert.assertEquals("bbb", ddd.getDeptName());
	}

	@Test
	public void testDelDept() {
		Dept d = new Dept();
		d.setDeptName("rt");
		deptService.addDept(d);
		
		deptService.delDept(d);
		
		Dept dd = deptService.getDeptById(d.getDeptId());
		
		Assert.assertNull(dd);
	}

	@Test
	public void testGetDeptById() {
		Dept d = new Dept();
		d.setDeptName("ke");
		deptService.addDept(d);
		
		Dept dd = deptService.getDeptById(d.getDeptId());
		
		Assert.assertEquals("ke", dd.getDeptName());
	}

	@Test
	//@Rollback(false)
	public void testFindPagedDept() {
		for (int i=0; i<30; i++) {
			Dept d = new Dept();
			d.setDeptName("dept_"+i);
			d.setCustomId("dd"+i);
			deptService.addDept(d);
		}
		
		Dept d = new Dept();
		d.setDeptName("开发部-Java");
		deptService.addDept(d);
		
		DeptQuery deptQuery = new DeptQuery();
		PageQuery pq = deptService.findPagedDept(deptQuery);
		Assert.assertEquals(20, pq.getDatas().size());
		
		DeptQuery deptQuery2 = new DeptQuery();
		deptQuery2.setDeptName("开发部");
		PageQuery pq2 = deptService.findPagedDept(deptQuery2);
		Assert.assertEquals(1, pq2.getDatas().size());
		Dept dd = (Dept)pq2.getDatas().get(0);
		Assert.assertEquals("开发部-Java", dd.getDeptName());
	}
	
	@Test
	public void testFindPagedDept2() {
		DeptQuery deptQuery = new DeptQuery();
		//deptQuery.setRows(5);
		PageQuery pq = deptService.findPagedDept(deptQuery);
		//Assert.assertEquals(31, pq.getTotal());
//		pq = deptService.findPagedDept(deptQuery);
//		Assert.assertEquals(31, pq.getTotal());
//		pq = deptService.findPagedDept(deptQuery);
//		Assert.assertEquals(31, pq.getTotal());
//		pq = deptService.findPagedDept(deptQuery);
//		Assert.assertEquals(31, pq.getTotal());
//		pq = deptService.findPagedDept(deptQuery);
//		Assert.assertEquals(31, pq.getTotal());
	}
	
	@Test
	public void testFindPagedDept3() {
		DeptQuery deptQuery = new DeptQuery();
		deptQuery.setParentId(1);
		PageQuery pq = deptService.findPagedDept(deptQuery);
		Assert.assertEquals(2, pq.getTotal());
	}
	
	@Test
	public void testQueryDeptItems() {
		List depts = deptService.queryDeptItems(0);
		Dept d = (Dept)depts.get(0);
		Assert.assertEquals("dept_0", d.getDeptName());
		
		depts = deptService.queryDeptItems(1);
		Assert.assertEquals(30, depts.size());
	}
	
	@Test
	public void testDg() {
		Menu p = menuService.getMenuById(1);
		
		
		dg(p);
	}
	

	private void dg(Menu m) {
		if ("0".equals(m.getLeaf())) {//树枝
			List<Menu> ms = menuService.queryMenuItems(m.getMenuId());
			for (int i=0; i<ms.size(); i++) {
				Menu mm = ms.get(i);
				dg(mm);
			}
		} else {
			System.out.println(m.getMenuName());
		}
	}
}
