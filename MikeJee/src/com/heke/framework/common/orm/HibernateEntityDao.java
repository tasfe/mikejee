package com.heke.framework.common.orm;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.heke.framework.common.web.PageFinder;
import com.heke.framework.common.web.PageQuery;

/**
 * 负责为单个Entity对象提供CRUD操作的Hibernate DAO基类.
 * <p/>
 * 子类只要在类定义时指定所管理Entity的Class, 即拥有对单个Entity对象的CRUD操作.
 * 
 * @author 龚磊
 * @see HibernateDaoSupport
 * @see GenericsUtils
 */

@SuppressWarnings("unchecked")
public abstract class HibernateEntityDao<T> extends HibernateDaoSupport {

    /**
     * DAO�?�?��理的Entity类型.
     */
    protected Class<T> entityClass;

    /**
     * 本类�?�?��现的sessionFacotry，以区分父类的sessionFacotry
     */
    private SessionFactory mySessionFacotry;

    /**
     * 在构造函数中将泛型T.class赋给entityClass.
     */
    public HibernateEntityDao() {
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());
    }

    /**
     * 取得entityClass.JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果�?
     */
    protected Class<T> getEntityClass() {
        return entityClass;
    }

    @Resource
    public void setMySessionFacotry(SessionFactory sessionFacotry) {
        this.mySessionFacotry = sessionFacotry;
    }

    /**
     * 使用注释之后的PO对象�?�?要将父类HibernateDaoSupport的构造函数重�?�?
     */
    @PostConstruct
    public void injectSessionFactory() {
        super.setSessionFactory(mySessionFacotry);
    }

    /**
     * 保存对象.
     */
    public T save(T o) {
        // // 根据对象的注解判断此对象是否�?�?��建立索引
        // if (o.getClass().getAnnotation(Indexed.class) != null) {
        // // 使用封装全文搜索引擎后的session进行数据库操�?�?
        // FullTextSession fullTextSession = getFullTextSession();
        // fullTextSession.saveOrUpdate(o);
        // } else {
        getHibernateTemplate().saveOrUpdate(o);
        // }
        return o;
    }

    /**
     * 根据ID获取对象. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 如果对象不存在，抛出异常.
     */
    public T getById(Serializable id) {
        return (T) getHibernateTemplate().get(entityClass, id);
    }

    /**
     * 获取全部对象
     */
    public List<T> getAll() {
        getHibernateTemplate().setCacheQueries(true);
        return getHibernateTemplate().loadAll(entityClass);
    }

    /**
     * 获取全部对象,带排序字段与升降序参�?�?
     * 
     * @param orderBy
     * @param isAsc
     * @return
     */
    public List<T> getAll(String orderBy, boolean isAsc) {
        if (isAsc)
            return getHibernateTemplate().findByCriteria(
                    DetachedCriteria.forClass(entityClass).addOrder(Order.asc(orderBy)));
        else
            return getHibernateTemplate().findByCriteria(
                    DetachedCriteria.forClass(entityClass).addOrder(Order.desc(orderBy)));
    }

    public Query createNamedQuery(String hql, String name, List<Integer> value) {
        Query query = getSession().getNamedQuery(hql);
        query.setParameterList(name, value);
        return query;
    }

    /**
     * 删除对象.
     */
    public T remove(T o) {
        // // 根据对象的注解判断此对象是否�?�?��建立索引
        // if (o.getClass().getAnnotation(Indexed.class) != null) {
        // // 使用封装全文搜索引擎后的session进行数据库操�?�?
        // FullTextSession fullTextSession = getFullTextSession();
        // fullTextSession.delete(o);
        // } else {
        getHibernateTemplate().delete(o);
        // }
        return o;
    }

    /**
     * 根据ID移除对象.
     */
    public T removeById(Serializable id) {
        return remove(getById(id));
    }

    public void flush() {
        getHibernateTemplate().flush();
    }

    public void clear() {
        getHibernateTemplate().clear();
    }

    /**
     * 取得Entity的Criteria. 可变的Restrictions条件列表,�?�?
     * {@link #createQuery(String,Object...)}
     * 
     * @param criterions
     * @return
     */
    public Criteria createCriteria(Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 根据属�?名和属�?值查询对�?�?
     * 
     * @return 符合条件的对象列�?�?
     * @see HibernateGenericDao#findBy(Class,String,Object)
     */
    public List<T> findBy(String propertyName, Object value) {
        return createCriteria(Restrictions.eq(propertyName, value)).setResultTransformer(
                Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    /**
     * 根据属�?名和属�?值查询对�?�?带排序参�?�?
     * 
     * @return 符合条件的对象列�?�?
     */
    public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
        return createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, value)).setResultTransformer(
                Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    /**
     * 根据属�?名和属�?值查询对�?�?带排序参�?�?
     * 
     * @param propertyName
     * @param value
     * @param orderBy
     * @param isAsc
     * @param limit
     * @return
     */
    public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc, int limit) {
        return createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, value))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setMaxResults(limit).list();
    }

    /**
     * 不带属�?查询对象,带排序参数和�?�?��返回�?�?
     * 
     * @return 符合条件的对象列�?�?
     */
    public List<T> findBy(String orderBy, boolean isAsc, int limit) {
        return createCriteria(orderBy, isAsc).setMaxResults(limit).setCacheable(true).list();
    }

    /**
     * 根据属�?名和属�?值查询单个对�?�?
     * 
     * @return 符合条件的唯�?�?���?�?or null
     */
    public T findUniqueBy(String propertyName, Object value) {
        return (T) createCriteria(Restrictions.eq(propertyName, value)).uniqueResult();
    }

    /**
     * 判断对象某些属�?的�?在数据库中唯�?�?
     * 
     * @param uniquePropertyNames
     *            在POJO里不能重复的属�?列表,以�?号分�?�?�?�?name,loginid,password"
     * @see HibernateGenericDao#isUnique(Class,Object,String)
     */
    public boolean isUnique(Object entity, String uniquePropertyNames) {
        Criteria criteria = createCriteria().setProjection(Projections.rowCount());
        String[] nameList = uniquePropertyNames.split(",");
        try {
            // 循环加入唯一�?�?
            for (String name : nameList) {
                criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
            }
            // 以下代码为了如果是update的情�?�?排除entity自身.
            String idName = getIdName();
            // 取得entity的主键�?
            Serializable id = getId(entity);

            // 如果id!=null,说明对象已存�?�?该操作为update,加入排除自身的判�?�?
            if (id != null)
                criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
        } catch (Exception e) {
            ReflectionUtils.handleReflectionException(e);
        }
        return (Integer) criteria.uniqueResult() == 0;
    }

    /**
     * 消除�?�?Hibernate Session 的关�?�?
     * 
     * @param entity
     */
    public void evict(Object entity) {
        getHibernateTemplate().evict(entity);
    }

    public void evict(String collection, Serializable id) {
        getSessionFactory().evictCollection(collection, id);
    }

    /**
     * 取得Entity的Criteria对象，带排序字段与升降序字段.
     * 
     * @param orderBy
     * @param isAsc
     * @param criterions
     * @return
     */
    public Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions) {
        Criteria criteria = createCriteria(criterions);
        if (isAsc)
            criteria.addOrder(Order.asc(orderBy));
        else
            criteria.addOrder(Order.desc(orderBy));

        return criteria;
    }

    /**
     * 根据hql查询,直接使用HibernateTemplate的find函数,不推荐使�?�?
     * 
     * @param values
     *            可变参数,见{@link #createQuery(String,Object...)}
     */
    public List find(String hql, Object... values) {
        return getHibernateTemplate().find(hql, values);
    }

    /**
     * 根据外置命名查询
     * 
     * @param queryName
     * @param values
     *            参数值列�?�?
     * @return
     */
    public List findByNameQuery(String queryName, Object... values) {
        return findByNameQuery(true, queryName, values);
    }

    public List findByNameQuery(boolean isCache, String queryName, Object... values) {
        getHibernateTemplate().setCacheQueries(isCache);
        return getHibernateTemplate().findByNamedQuery(queryName, values);
    }

    public void setCacheQueries(boolean isCache) {
        getHibernateTemplate().setCacheQueries(isCache);
    }

    /**
     * 根据外置命名查询
     * 
     * @param queryName
     * @param limit
     *            记录�?�?��返回�?�?
     * @param values
     * @return
     */
    public List findByNameQuery(int limit, String queryName, Object... values) {
        return findByNameQuery(limit, true, queryName, values);
    }

    public List findByNameQuery(int limit, boolean isCache, String queryName, Object... values) {
        Query queryObject = getSession().getNamedQuery(queryName).setMaxResults(limit).setCacheable(isCache);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        return queryObject.list();
    }

    /**
     * 创建Query对象.
     * 对于�?�?��first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设�?�?
     * 留意可以连续设置,如下�?�?
     * 
     * <pre>
     * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
     * </pre>
     * 
     * 调用方式如下�?�?
     * 
     * <pre>
     *        dao.createQuery(hql)
     *        dao.createQuery(hql,arg0);
     *        dao.createQuery(hql,arg0,arg1);
     *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
     * </pre>
     * 
     * @param values
     *            可变参数.
     */
    public Query createQuery(String hql, Object... values) {
        Query query = getSession().createQuery(hql);
        if (null != values && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 按游离hibernate标准查询器进行分页查�?�?
     * 
     * @param pagination
     * @return
     */
    public PageQuery pagedByDetachedCriteria(DetachedCriteria detachedCriteria, PageQuery query) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(this.getSession());
        return pagedByCriteria(criteria, query);
    }

    /**
     * 按hibernate标准查询器进行分页查�?
     * 
     * @param pagination
     * @return
     */
    public PageQuery pagedByCriteria(Criteria criteria, PageQuery query) {
        Long totalRows = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null);
        if (totalRows.intValue() < 1) {
            query.setDatas(new ArrayList<T>());
            return query;
        } else {
            query.setTotal(totalRows.intValue());
            List<T> list = criteria.setFirstResult((query.getPage() - 1) * query.getRows())
                    .setMaxResults(query.getRows()).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();
            query.setDatas(list);
            return query;
        }
    }

    /**
     * 适合多对多情�?的查询分页器，可去掉重复记录，同时查询器不能存在排序条件
     * 
     * @param criteria
     * @param distinctPropertyName
     * @param query
     * @return
     */
    public PageQuery pagedByCriteria(Criteria criteria, String distinctPropertyName, PageQuery query) {
        Long totalRows = (Long) criteria.setProjection(Projections.countDistinct(distinctPropertyName))
                .uniqueResult();
        criteria.setProjection(null);
        if (totalRows.intValue() < 1) {
            query.setDatas(new ArrayList<T>());
            return query;
        } else {
            query.setTotal(totalRows.intValue());
            criteria.setProjection(Projections.distinct(Projections.property(distinctPropertyName)));
            List<T> idlist = criteria.setFirstResult((query.getPage() - 1) * query.getRows())
                    .setMaxResults(query.getRows()).list();
            criteria = createCriteria(Restrictions.in(distinctPropertyName, idlist));
            query.setDatas(criteria.list());
            return query;
        }
    }

    /**
     * 分页查询
     * 
     * @param criteria
     * @param offset
     * @param pageSize
     * @return
     */
    public List<T> findByPage(Criteria criteria, int offset, int pageSize) {

        return criteria.setFirstResult(offset).setMaxResults(pageSize)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    /**
     * 使用搜索引擎进行分页查询
     * 
     * @param criteria
     * @param pageNo
     * @param pageSize
     * @return
     */
    // public PageFinder<T> pagedBySearcher(org.apache.lucene.search.Query
    // luceneQuery, int pageNo, int pageSize) {
    // FullTextSession s = getFullTextSession();
    // FullTextQuery query = s.createFullTextQuery(luceneQuery, entityClass);
    // int totalRows = query.getResultSize();
    // if (totalRows < 1) {
    // return new PageFinder(pageNo, pageSize, totalRows);
    // } else {
    // PageFinder finder = new PageFinder(pageNo, pageSize, totalRows);
    // query.setMaxResults(pageSize);
    // query.setFirstResult(finder.getStartOfPage());
    // List<T> list = query.list();
    // finder.setData(list);
    // return finder;
    // }
    // }

    /**
     * 按HQL方式进行分页查询
     * 
     * @param toPage
     *            跳转页号
     * @param pageSize
     *            每页数量
     * @param hql
     *            查询语句
     * @param values
     *            参数
     * @return
     */
    public PageFinder pagedByHQL(String hql, int toPage, int pageSize, Object... values) {
        String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
        List countlist = getHibernateTemplate().find(countQueryString, values);
        Long totalCount = (Long) countlist.get(0);
        if (totalCount.intValue() < 1) {
            return new PageFinder(toPage, pageSize, totalCount.intValue());
        } else {
            PageFinder finder = new PageFinder(toPage, pageSize, totalCount.intValue());
            Query query = createQuery(hql, values);
            List list = query.setFirstResult(finder.getStartOfPage()).setMaxResults(finder.getPageSize())
                    .list();
            finder.setData(list);
            return finder;
        }

    }

    /**
     * 分页查询函数，根据entityClass和查询条件参数创建默认的<code>Criteria</code>.
     * 
     * @param pageNo
     *            页号,�?�?�?�?��.
     * @return 含�?记录数和当前页数据的Page对象.
     */
    public PageQuery pagedFinder(PageQuery query, Criterion... criterions) {
        Criteria criteria = createCriteria(criterions);
        return pagedByCriteria(criteria, query);
    }

    /**
     * 分页查询函数，根据entityClass和查询条件参�?�?排序参数创建默认�?�?code>Criteria</code>.
     * 
     * @param pageNo
     *            页号,�?�?�?�?��.
     * @return 含�?记录数和当前页数据的Page对象.
     */
    public PageQuery pagedQuery(PageQuery query, String orderBy, boolean isAsc, Criterion... criterions) {
        Criteria criteria = createCriteria(orderBy, isAsc, criterions);
        return pagedByCriteria(criteria, query);
    }

    /**
     * 取得对象的主键�?,辅助函数.
     */
    public Serializable getId(Object entity) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        return (Serializable) PropertyUtils.getProperty(entity, getIdName());
    }

    /**
     * 取得对象的主键名,辅助函数.
     */
    public String getIdName() {
        ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
        Assert.notNull(meta, "Class " + entityClass + " not define in hibernate session factory.");
        String idName = meta.getIdentifierPropertyName();
        Assert.hasText(idName, entityClass.getSimpleName() + " has no identifier property define.");
        return idName;
    }

    /**
     * 去除hql的select 子句，未考虑union的情�?�?用于pagedQuery.
     * 
     * @param hql
     * @return
     */
    protected final static String removeSelect(String hql) {
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
        return hql.substring(beginPos);
    }

    /**
     * 去除hql的orderby 子句，用于pagedQuery.
     * 
     * @param hql
     * @return
     */
    protected final static String removeOrders(String hql) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获得全文搜索引擎的查询会�?�?
     * 
     * @return
     */
//    public FullTextSession getFullTextSession() {
//        return Search.getFullTextSession(getSession());
//    }
//
//    public void index() {
//        // getFullTextSession().createIndexer(entityClass).batchSizeToLoadObjects(25).cacheMode(CacheMode.NORMAL)
//        // .threadsToLoadObjects(5).threadsForSubsequentFetching(20).startAndWait();
//        FullTextSession fullTextSession = getFullTextSession();
//        fullTextSession.setFlushMode(FlushMode.MANUAL);
//        fullTextSession.setCacheMode(CacheMode.IGNORE);
//        // Transaction transaction = fullTextSession.beginTransaction();
//        // Scrollable results will avoid loading too many objects in memory
//        ScrollableResults results = fullTextSession.createCriteria(entityClass).setFetchSize(500)
//                .scroll(ScrollMode.FORWARD_ONLY);
//        int index = 0;
//        while (results.next()) {
//            index++;
//            fullTextSession.index(results.get(0)); // index each element
//            if (index % 10 == 0) {
//                fullTextSession.flushToIndexes(); // apply changes to indexes
//                fullTextSession.clear(); // free memory since the queue is
//                                         // processed
//            }
//        }
//        // transaction.commit();
//    }

    /***
     * 获得总记录条�?�?
     */
    public int getRowCount(Criteria criteria) {
        Integer totalRows = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
        return totalRows;
    }
}
