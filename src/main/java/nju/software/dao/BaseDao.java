package nju.software.dao;

import java.io.Serializable;
import java.util.List;

import nju.software.dataobject.Pagination;

import org.hibernate.Criteria;




public interface BaseDao<T> {
	   public void update(T t);
	   public void add(T t);
	   public void deleteById(T t);
	   public abstract boolean deleteByHql(String hql);
	   public List<T> getTotalPage(Criteria c);
	   public T findById(Class<T> clazz,Serializable id);
	   public abstract List<T> findByParams(String hql, Object... params);
	   public List<T> PaginationSelect(Criteria c,Pagination page);
	   public List<Object> PaginationSelect(final String sql, Pagination page,final String currentPage);
	   public List<Object> PaginationList(String sql,Pagination page,String currentPage);
	   public List<Object> conditionQuery(String sql);
	   public abstract void saveOrupdate(T t);
	   public List<T> findByParams(String hql);
	   public List findCutPage(final String hql, final Integer begin, final Integer end);
	   public void del(String sql);
	   public T findByEntity(String hql);
	   public List<T> queryList(String sql);
	   public boolean addData(T whd);
	   public List findByHql(String hql, Pagination page,Integer pageNow);
	   public List<T> queryById(String hql);
	   public void updhql(String hql);
	  // public List<Object> PaginationSelect(final String sql,final Pagination p,String currentPage);
}
