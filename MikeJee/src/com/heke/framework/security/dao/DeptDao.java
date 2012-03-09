package com.heke.framework.security.dao;

import org.springframework.stereotype.Repository;

import com.heke.framework.common.orm.HibernateEntityDao;
import com.heke.framework.security.entity.Dept;

/**
 * 部门数据访问类
 * 
 * @author Mike
 *
 */
@Repository
public class DeptDao extends HibernateEntityDao<Dept> {

}
