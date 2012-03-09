package com.heke.framework.security.dao;

import org.springframework.stereotype.Repository;

import com.heke.framework.common.orm.HibernateEntityDao;
import com.heke.framework.security.entity.Role;

/**
 * 角色数据访问类
 * 
 * @author Mike
 *
 */
@Repository
public class RoleDao extends HibernateEntityDao<Role> {

}
