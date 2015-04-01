package nju.software.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nju.software.dao.BaseDao;
import nju.software.dataobject.Pagination;
import nju.software.util.AppException;
import nju.software.util.CommonUtil;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T>{

	@Override
	public List<T> PaginationSelect(final Criteria c,final Pagination page) {
		// TODO Auto-generated method stub
		return
		this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				c.setFirstResult((page.getPage()-1) * page.getPageSize());
				c.setMaxResults(page.getPageSize());
				return c.list();
			}
		});
	}
	@Override
	public List<Object>  PaginationSelect(final String sql,Pagination page,final String currentPage) {
		// TODO Auto-generated method stub
		
		final String num="select count(*) from (" + sql + ") t";
		Object obj = null;
		try {
			obj = this.getHibernateTemplate().execute(new HibernateCallback() {
				@Override
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return session.createSQLQuery(num).uniqueResult();
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		page = CommonUtil.getPagination(page, new Integer(obj.toString()));
		page.setCount(new Integer(obj.toString()));
		try {
			page.setPage(new Integer(currentPage));
		} catch (Exception e1) {
			
			page.setPage(1);
		}
		List<Object> list = new ArrayList<Object>();
		final int endNum = (page.getPage() - 1) * page.getPageSize() + page.getPageSize();
		final int beginNum = (page.getPage() - 1) * page.getPageSize();
		
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				query.setFirstResult(beginNum);
				query.setMaxResults(endNum);				
				return query.list();
			}
		});
	}
	
	@Override
	public T findByEntity(final String hql){
		T t = null;
		try{
		t = (T)this.getHibernateTemplate().execute(new HibernateCallback(){

				@Override
				public Object doInHibernate(Session arg0)
						throws HibernateException, SQLException {
					return arg0.createQuery(hql).uniqueResult();
				}
				
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		return t;
	}	
	
	@Override
	public void add(T t) {
		
		this.getHibernateTemplate().save(t);
	}

	@Override
	public void deleteById(T t) {
		
		this.getHibernateTemplate().delete(t);
	}


	@Override
	public T findById(Class<T> clazz, Serializable id) {
		
		return this.getHibernateTemplate().get(clazz, id);
	}

	@Override
	public List<T> findByParams(String hql,Object...params){
		List<T> list = null;
		try {
			list = getHibernateTemplate().find(hql, params);
		} catch (RuntimeException re) {
			throw re;
		}
		return list;
	}
	@Override
	public List<T> findByParams(String hql){
		List<T> list = null;
		try {
			list = getHibernateTemplate().find(hql);
		} catch (RuntimeException re) {
			throw re;
		}
		return list;
	}
	
	@Override
	public List<T> queryById(final String hql){
		List<T> list = new ArrayList<T>();
		try{
			list=this.getHibernateTemplate().executeFind(new HibernateCallback(){

				
				@Override
				public Object doInHibernate(Session arg0)
						throws HibernateException, SQLException {
					return arg0.createQuery(hql).list();
				}
				
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	
		
	}
	@Override
	public boolean deleteByHql(String hql){
		try {
			return getHibernateTemplate().bulkUpdate(hql, null)>0?true:false;
		}catch (RuntimeException re) {
			throw re;
		}
	}
	@Override
	public List<T> getTotalPage(final Criteria c) {
		
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session){
			
				return c.list();
			}
		});
	
	}

//	public List<Object> PaginationSelect(final String sql, Pagination page ,final String currentPage) {
//		
//		List<Object> list=new ArrayList<Object>();
//		try {
//			list = this.getHibernateTemplate().executeFind(
//					new HibernateCallback() {
//						public Object doInHibernate(Session session)
//								throws HibernateException, SQLException {
//							return session
//									.createSQLQuery(sql).list();
//							
//						}
//					});
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//		}
//		 page = CommonUtil.getPagination(list.size());
//		try {
//			page.setPage(new Integer(currentPage));
//		} catch (Exception e1) {
//			
//			page.setPage(1);
//		}
//		return CommonUtil.getPaginationList(list, page);
//	
//	}
	@Override
	public List<Object> PaginationList(String sql, Pagination page, final String currentPage) {
		
		
		final String num="select count(*) from (" + sql + ") as total ";
		Object obj = null;
		try {
			obj = this.getHibernateTemplate().execute(new HibernateCallback() {
				@Override
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return session.createSQLQuery(num).uniqueResult();
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		page = CommonUtil.getPagination(page, new Integer(obj.toString()));
		page.setCount(new Integer(obj.toString()));
		try {
			page.setPage(new Integer(currentPage));
		} catch (Exception e1) {
			
			page.setPage(1);
		}
		List<Object> list = new ArrayList<Object>();
		int endNum = (page.getPage() - 1) * page.getPageSize() + page.getPageSize();
		int beginNum = (page.getPage() - 1) * page.getPageSize();
		final String paginationSql = " select pa.*" + " from (" + sql + ") pa limit "
				+ beginNum +","+ endNum;
		try {
			list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
				@Override
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return session.createSQLQuery(paginationSql).list();
				}
			});
		} catch (DataAccessException e1) {
			e1.printStackTrace();
		}
		
		
		return list;

	}
	
	@Override
	public List<Object> conditionQuery(final String sql) {
		
		List<Object> list=new ArrayList<Object>();
		try {
			list = this.getHibernateTemplate().executeFind(
					new HibernateCallback() {
						@Override
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							return session.createSQLQuery(sql).list();
						}
					});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void saveOrupdate(T t) {
		this.getHibernateTemplate().saveOrUpdate(t);
	}
	
	@Override
	public List findByHql(final String hql,Pagination page,Integer pageNow){
		List alllist = new ArrayList();
		alllist = this.getHibernateTemplate().executeFind(new HibernateCallback(){

			
			@Override
			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				return arg0.createQuery(hql).list();
			}
			
		});
		page.setCount(alllist.size());
		final Integer pageSize=page.getPageSize();
		final Integer begin = (pageNow-1)*pageSize;
		final Integer end = pageSize;
		List ptList = new ArrayList();
		try {
			ptList = this.getHibernateTemplate().executeFind(
					new HibernateCallback() {
						@Override
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							return session
									.createQuery(hql).setFirstResult(begin).setMaxResults(end).list();
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(
					"error");
		}
		return ptList;
		

		
	}
	@Override
	public List findCutPage(final String hql, final Integer begin, final Integer end){
		List ptList = new ArrayList();
		try {
			ptList = this.getHibernateTemplate().executeFind(
					new HibernateCallback() {
						@Override
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							return session
									.createQuery(hql).setFirstResult(begin).setMaxResults(end).list();
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(
					"error");
		}
		return ptList;
		
	}

	@Override
	public void del(final String  sql) {
		this.getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.createSQLQuery(sql).executeUpdate();
				return null;
			}});
		
	}

	@Override
	public List<T> queryList(final String hql){
		List<T>  list = new ArrayList<T>();
		try{
			list = this.getHibernateTemplate().executeFind(new HibernateCallback(){

				
				@Override
				public Object doInHibernate(Session arg0)
						throws HibernateException, SQLException {
					return arg0.createQuery(hql).list();
				}
				
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public boolean addData(T whd){
		try{
			Integer i = (Integer)this.getHibernateTemplate().save(whd);
			return i>0 ? true : false;
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("com.gene.daoImpl.BaseDaoImpl.addSupRtnLine.error");
		}
	}
	
	@Override
	public void updhql(final String hql){
		this.getHibernateTemplate().execute(new HibernateCallback(){

			
			@Override
			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				Query q = arg0.createQuery(hql);
				return q.executeUpdate();
			}
			
		});
	}
	
	@Override
    public List<Object> findByhql2(final String hql){
        List<Object> list=new ArrayList<Object>();
		try {
			list = this.getHibernateTemplate().executeFind(
					new HibernateCallback() {
						@Override
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							return session.createQuery(hql).list();
						}
					});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return list;
        
    }
}
