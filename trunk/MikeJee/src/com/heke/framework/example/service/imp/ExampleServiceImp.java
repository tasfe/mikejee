package com.heke.framework.example.service.imp;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heke.framework.common.web.PageQuery;
import com.heke.framework.example.dao.ExampleDao;
import com.heke.framework.example.entity.Example;
import com.heke.framework.example.query.ExampleQuery;
import com.heke.framework.example.service.ExampleService;

@Service
@Transactional
public class ExampleServiceImp implements ExampleService {
	
	@Resource
	private ExampleDao exampleDao;

	@Override
	public void addExample(Example example) {
		exampleDao.save(example);
	}

	@Override
	public PageQuery findPagedExample(ExampleQuery exampleQuery) {
		// 创建标准查询器
		Criteria criteria = exampleDao.createCriteria();

		// criteria.createAlias("users", "user",Criteria.LEFT_JOIN);

		//排序
		criteria.addOrder(Order.asc("id"));

//		// 如果部门名称或UID不为空
//		if (StringUtils.isNotBlank(deptQuery.getDeptName())) {
//			criteria.add(Restrictions.like("deptName", deptQuery.getDeptName(), MatchMode.START)
//					.ignoreCase());
//		}
//		
//		//部门id
//		if(deptQuery.getDeptId() > 0){
//			criteria.add(Restrictions.eq("deptId", deptQuery.getDeptId()));
//		}
//		
//		//父节点
//		if (deptQuery.getParentId() > 0) {
//			criteria.add(Restrictions.eq("parent.deptId", deptQuery.getParentId()));
//		}

		return exampleDao.pagedByCriteria(criteria, exampleQuery);
	}
	
}
