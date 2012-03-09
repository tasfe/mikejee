package com.heke.framework.example.dao;

import org.springframework.stereotype.Repository;

import com.heke.framework.common.orm.HibernateEntityDao;
import com.heke.framework.example.entity.Example;

@Repository
public class ExampleDao extends HibernateEntityDao<Example> {

}
