package com.heke.framework.security.dao;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.heke.framework.security.entity.Dept;


//表示该测试用例是运用junit4进行测试，也可以换成其他测试框架
@RunWith(SpringJUnit4ClassRunner.class)
//该路径的设置时相当于该单元测试所在的路径，也可以用classpath进行设置，该设置还有一个inheritLocations的属性，默认为true,表示子类可以继承该设置。
@ContextConfiguration(locations = "/applicationContext.xml")
//@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class DeptDaoTest {
	
	@Resource
	DeptDao deptDao;

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
	public void get() {
		Dept d = deptDao.getById(6);
		Assert.assertEquals("测试部", d.getDeptName());
		Assert.assertEquals("根节点", d.getParent().getDeptName());
	}
	
	@Test
	public void insert() {
		Dept parent = new Dept();
		//根节点
		parent.setDeptId(5);
		Dept d = new Dept();
		d.setDeptName("运维部");
		d.setParent(parent);
		deptDao.save(d);
		
		Assert.assertTrue(d.getDeptId() != 0);
	}
	
	@Test
	public void update() {
		Dept d = new Dept();
		d.setDeptName("销售部");
		deptDao.save(d);
		
		Dept dd = deptDao.getById(d.getDeptId());
		dd.setDeptName("销售部aa");
		Dept parent = deptDao.getById(5);
		dd.setParent(parent);
		deptDao.save(dd);
		
		Dept ddd = deptDao.getById(d.getDeptId());
		
		Assert.assertEquals("销售部aa", ddd.getDeptName());
		Assert.assertEquals("根节点", ddd.getParent().getDeptName());
	}
	
	@Test
	public void delete() {
		Dept d = new Dept();
		d.setDeptName("aaa");
		deptDao.save(d);
		
		int id = d.getDeptId();
		
		deptDao.removeById(id);
		
		Dept dd = deptDao.getById(id);
		Assert.assertNull(dd);
	}
}
