package com.heke.framework.security.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.heke.framework.common.web.PageQuery;
import com.heke.framework.security.dao.DeptDao;
import com.heke.framework.security.entity.Dept;
import com.heke.framework.security.query.DeptQuery;
import com.heke.framework.security.service.DeptService;

/**
 * 
 * @author Mike
 *
 */
@Service
//注意！即使是单个增删改查也必须启动事务，事务结束后spring框架会自动为我们关闭session
@Transactional
public class DeptServiceImp implements DeptService {

	@Resource
	private DeptDao deptDao;
	
	@Override
	public void addDept(Dept dept) {
		dept.setLeaf(Dept.IS_LEAF);
		deptDao.save(dept);
		
		Dept parent = deptDao.getById(dept.getParent().getDeptId());
		if (Dept.IS_LEAF.equals(parent.getLeaf())) {//如果父节点是叶子节点，则更新为树枝节点
			updateLeaf(parent.getDeptId(), Dept.IS_NOT_LEAF);
		}
	}

	@Override
	public void updateDept(Dept dept) {
		// perDept对象处于持久状态（persistent）
		Dept perDept = deptDao.getById(dept.getDeptId());
		perDept.setCustomId(dept.getCustomId());
		perDept.setDeptName(dept.getDeptName());
		perDept.setRemark(dept.getRemark());
		perDept.setSortNo(dept.getSortNo());
		
		Dept oldParent = perDept.getParent();
		Dept newParent = dept.getParent();
		
		//判断是否需要更新父节点
		boolean isUpdateParent = false;
		if (newParent != null && oldParent.getDeptId() != newParent.getDeptId()) {
			newParent = deptDao.getById(newParent.getDeptId());
			perDept.setParent(newParent);
			isUpdateParent = true;
		}
	
		deptDao.save(perDept);
		
		//更新leaf
		if (isUpdateParent) {
			//1
			if (!hasChildren(oldParent.getDeptId())) {
				updateLeaf(oldParent.getDeptId(), Dept.IS_LEAF);
			}
			//2
			if (newParent.getLeaf().equals(Dept.IS_LEAF)) {
				updateLeaf(newParent.getDeptId(), Dept.IS_NOT_LEAF);
			}
		}
		
		
	}

	@Override
	public void delDept(Dept dept) {
		
		if (hasChildren(dept.getDeptId())) {//有子节点，不允许删除
			throw new RuntimeException("该部门有子节点，不允许删除!");
		}
		
		Dept d = deptDao.getById(dept.getDeptId());
		Dept parent = d.getParent();
		
		deptDao.removeById(dept.getDeptId());
	
		//update leaf
		if (!hasChildren(parent.getDeptId())) {
			updateLeaf(parent.getDeptId(), Dept.IS_LEAF);
		}
	}

	@Override
	public Dept getDeptById(int deptId) {
		return deptDao.getById(deptId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PageQuery findPagedDept(DeptQuery deptQuery) {
		// 创建标准查询器
		Criteria criteria = deptDao.createCriteria();

		// criteria.createAlias("users", "user",Criteria.LEFT_JOIN);

		//排序
		criteria.addOrder(Order.asc("sortNo")).addOrder(Order.asc("deptId")).addOrder(Order.asc("deptName"));

		// 如果部门名称或UID不为空
		if (StringUtils.isNotBlank(deptQuery.getDeptName())) {
			criteria.add(Restrictions.like("deptName", deptQuery.getDeptName(), MatchMode.START)
					.ignoreCase());
		}
		
		//部门id
		if(deptQuery.getDeptId() > 0){
			criteria.add(Restrictions.eq("deptId", deptQuery.getDeptId()));
		}
		
		//父节点 or 
		if (deptQuery.getParentId() > 0) {
			criteria.add(Restrictions.or(
							Restrictions.eq("parent.deptId", deptQuery.getParentId()),
							Restrictions.eq("deptId", deptQuery.getParentId())
							)
			);
		}
		
		//业务对照码customId
		if (StringUtils.isNotBlank(deptQuery.getCustomId())) {
			criteria.add(Restrictions.eq("customId", deptQuery.getCustomId()));
		}

		return deptDao.pagedByCriteria(criteria, deptQuery);
	}

	@Override
	public List queryDeptItems(int pDeptId) {
		List childDepts = null;
		if (pDeptId > 0) {
			String hql = "from Dept d where d.parent.deptId = ? order by d.sortNo";
			childDepts = deptDao.find(hql, pDeptId);
		} else {
			String hql = "from Dept d where d.parent.deptId is null order by d.sortNo";
			childDepts = deptDao.find(hql);
		}
		
		return childDepts;
	}

	@Override
	public void updateLeaf(int deptId, String leaf) {
		String hql = "update Dept d set d.leaf = ? where d.deptId = ?";
		deptDao.createQuery(hql, leaf,deptId).executeUpdate();
	}

	@Override
	public boolean hasChildren(int deptId) {
		String hql = "from Dept d where d.parent.deptId = ?";
		int size = deptDao.find(hql, deptId).size();
		if (size > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Dept> queryAllDept() {
		
		String hql = "from Dept d";
		List<Dept> allDept = deptDao.find(hql);
		
		return allDept;
	}
}
