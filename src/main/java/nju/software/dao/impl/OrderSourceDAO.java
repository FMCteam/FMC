package nju.software.dao.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nju.software.dao.IOrderSourceDAO;
import nju.software.dataobject.OrderSource;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class OrderSourceDAO extends HibernateDaoSupport implements IOrderSourceDAO{
	private static final Logger log = LoggerFactory.getLogger(OrderSourceDAO.class);
	//property names
	private static final String ORDER_ID = "orderId";
	private static final String SOURCE_ID = "sourceId";
	private static final String SOURCE_NAME = "source";
	
	@Override
	public void save(OrderSource instance) {
		log.debug("saving OrderSource instance");
		try {
			getHibernateTemplate().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(OrderSource instance) {
		log.debug("deleting OrderSource instance");
		try {
			getHibernateTemplate().delete(instance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
		
	}

	@Override
	public OrderSource findById(Integer id) {
		log.debug("getting OrderSource instance with id: " + id);
		try {
			OrderSource instance = (OrderSource) getHibernateTemplate().get(
					"nju.software.dataobject.OrderSource", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
		
	}

	@Override
	public List<OrderSource> findByExample(OrderSource example) {
		log.debug("finding OrderSource instance by example");
		try {
			List<OrderSource> results = getHibernateTemplate()
					.findByExample(example);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List<OrderSource> findByOrderId(Integer orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	@Override
	public List<OrderSource> findBySource(String source) {
		return findByProperty(SOURCE_NAME, source);
	}

	@Override
	public List<OrderSource> findAll() {
		log.debug("finding all OrderSource instances");
		try {
			String queryString = "from OrderSource";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public OrderSource merge(OrderSource orderSource) {
		log.debug("merging OrderSource instance");
		try {
			OrderSource result = getHibernateTemplate().merge(
					orderSource);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
		
	}

	@Override
	public void attachDirty(OrderSource orderSource) {
		log.debug("attaching dirty OrderSource instance");
		try {
			getHibernateTemplate().saveOrUpdate(orderSource);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
		
	}

	@Override
	public void attachClean(OrderSource orderSource) {
		log.debug("attaching clean OrderSource instance");
		try {
			getHibernateTemplate().lock(orderSource, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
		
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding OrderSource instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from OrderSource as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

}
