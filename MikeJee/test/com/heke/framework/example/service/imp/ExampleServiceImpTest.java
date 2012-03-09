package com.heke.framework.example.service.imp;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.heke.framework.example.entity.Example;
import com.heke.framework.example.service.ExampleService;

//表示该测试用例是运用junit4进行测试，也可以换成其他测试框架
//(SpringJUnit4ClassRunner.class)
//该路径的设置时相当于该单元测试所在的路径，也可以用classpath进行设置，该设置还有一个inheritLocations的属性，默认为true,表示子类可以继承该设置。
@ContextConfiguration(locations = "/applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class ExampleServiceImpTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Resource
	private ExampleService exampleService;
	
	@Test
	@Rollback(false)
	public void testAddExample() {
		for (int i=0; i<35; i++) {
			Example e = new Example();
			e.setName("example_"+i);
			e.setTheCode("code_"+i);
			e.setTheContent("content=="+i);
			exampleService.addExample(e);
		}
	}
}
