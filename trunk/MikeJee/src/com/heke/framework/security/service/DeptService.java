package com.heke.framework.security.service;

import java.util.List;

import com.heke.framework.common.web.PageQuery;
import com.heke.framework.security.entity.Dept;
import com.heke.framework.security.query.DeptQuery;

/**
 * 部门业务逻辑接口
 * 
 * @author Mike
 *
 */
public interface DeptService {
	
	void addDept(Dept dept);
	
	void updateDept(Dept dept);
	
	void delDept(Dept dept);
	
	Dept getDeptById(int deptId);
	
	/**
	 * 根据父节点id查询子节点
	 * @param pDeptId
	 * @return
	 */
	List queryDeptItems(int pDeptId);
	
	/**
	 * 分页查询
	 * @param deptQuery
	 * @return
	 */
	PageQuery findPagedDept(DeptQuery deptQuery);
	
	/**
	 * 更新是否叶子节点
	 * @param deptId
	 * @param leaf
	 */
	void updateLeaf(int deptId,String leaf);
	
	/**
	 * 判断是否有子节点
	 * @param deptId
	 * @return
	 */
	boolean hasChildren(int deptId);
	
	/**
	 * 查询所有的部门
	 * @return
	 */
	List<Dept> queryAllDept();
	
}
