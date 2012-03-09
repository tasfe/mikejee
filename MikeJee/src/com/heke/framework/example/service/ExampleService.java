package com.heke.framework.example.service;

import com.heke.framework.common.web.PageQuery;
import com.heke.framework.example.entity.Example;
import com.heke.framework.example.query.ExampleQuery;

public interface ExampleService {
	
	void addExample(Example example);
	
	/**
	 * 分页查询
	 * @param deptQuery
	 * @return
	 */
	PageQuery findPagedExample(ExampleQuery exampleQuery);
}
