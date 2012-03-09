package com.heke.framework.security.entity;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heke.framework.security.dao.DeptDao;


//表示该测试用例是运用junit4进行测试，也可以换成其他测试框架
@RunWith(SpringJUnit4ClassRunner.class)
//该路径的设置时相当于该单元测试所在的路径，也可以用classpath进行设置，该设置还有一个inheritLocations的属性，默认为true,表示子类可以继承该设置。
@ContextConfiguration(locations = "/applicationContext.xml")
//@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
//@Transactional
public class EntityTest {
	
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
	public void testDept() {
////		Configuration config = new Configuration();
////		SessionFactory sessionFactory = config.buildSessionFactory();
////		
////		System.out.println(sessionFactory);
//		
//		//System.out.println("aaa");
//		
//		SessionFactory sessionFactory = deptDao.getSessionFactory();
//		Session session = sessionFactory.openSession();
//		Transaction tr = session.beginTransaction();
//		
////		Dept dept = new Dept();
////		dept.setDeptName("测试部门1");
////		session.save(dept);
//		
////		Dept parent = (Dept)session.load(Dept.class, 0);
////		//System.out.println(dept.getDeptName());
////		
////		Dept dept = new Dept();
////		dept.setParent(parent);
////		session.save(dept);
//		
//		Example e = new Example();
//		e.setName("test");
//		session.save(e);
//		
//		tr.commit();
//		session.close();
		
		//System.out.println(deptDao);
		
		SessionFactory sessionFactory = deptDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
		/*
		//load
		Dept rootDept = (Dept)session.load(Dept.class, 5);
		
		//new
		Dept child = new Dept();
		child.setDeptName("测试部");
		child.setParent(rootDept);
		
		//save
		session.save(child);
		*/
		
		Dept parent = (Dept)session.get(Dept.class, 5);
		Dept d = new Dept();
		d.setDeptName("开发部");
		d.setParent(parent);
		
		session.save(d);
		
		tr.commit();
		session.close();
	}
	
	@Test
	public void testUser() {
		SessionFactory sessionFactory = deptDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
//		Dept d = new Dept();
//		d.setDeptId(7);
//		User u = new User();
//		u.setUserName("张三");
//		u.setAccount("zhangsan");
//		u.setPassword("123");
//		u.setDept(d);
//		
//		session.save(u);
		
		User u = (User)session.get(User.class, 1);
		
		tr.commit();
		session.close();
		
		System.out.println(u);
	}
	
	@Test
	public void testRole() {
		SessionFactory sessionFactory = deptDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
		//开发部
		Dept d = (Dept)session.get(Dept.class, 7);
		Role r = new Role();
		r.setRoleName("开发部角色1");
		r.setDept(d);
		
		session.save(r);
		tr.commit();
		session.close();
		
		System.out.println();
	}
	
	@Test
	public void testSecurity() {
		SessionFactory sessionFactory = deptDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
//		User u = (User)session.get(User.class, 1);
//		Role r = (Role)session.get(Role.class, 1);
//		u.getRoles().add(r);
//		
//		session.save(u);
		
//		Menu m1 = (Menu)session.get(Menu.class, 1);
//		Menu m2 = (Menu)session.get(Menu.class, 2);
//		Role r = (Role)session.get(Role.class, 1);
//		r.getMenus().add(m1);
//		r.getMenus().add(m2);
//		
//		session.saveOrUpdate(r);
		
		Menu m1 = (Menu)session.get(Menu.class, 1);
		User u = (User)session.get(User.class, 1);
		
		tr.commit();
		session.close();
		
		System.out.println();
	}
	
	@Test
	public void testMenu() {
		SessionFactory sessionFactory = deptDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
		Menu parent = (Menu)session.get(Menu.class, 1);
		Menu m = new Menu();
		m.setMenuName("测试菜单qq");
		m.setParent(parent);
		
		session.save(m);
		
		tr.commit();
		session.close();
		
		System.out.println();
	}
}
