package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.IProduceDAO;
import nju.software.dataobject.Produce;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Produce entities.
 * Transaction control of the save(), update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how to configure it for
 * the desired type of transaction control.
 * 
 * @see nju.software.dataobject.Produce
 * @author MyEclipse Persistence Tools
 */
public class ProduceDAO extends HibernateDaoSupport implements IProduceDAO{
	private static final Logger log = LoggerFactory.getLogger(ProduceDAO.class);
	// property constants
	public static final String OID = "oid";
	public static final String COLOR = "color";
	public static final String XS = "xs";
	public static final String S = "s";
	public static final String M = "m";
	public static final String L = "l";
	public static final String XL = "xl";
	public static final String XXL = "xxl";
	public static final String TYPE = "type";

	protected void initDao() {
		// do nothing
	}

	public void save(Produce transientInstance) {
		log.debug("saving Produce instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Produce persistentInstance) {
		log.debug("deleting Produce instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Produce findById(java.lang.Integer id) {
		log.debug("getting Produce instance with id: " + id);
		try {
			Produce instance = (Produce) getHibernateTemplate().get(
					"nju.software.dataobject.Produce", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Produce> findByExample(Produce instance) {
		log.debug("finding Produce instance by example");
		try {
			List<Produce> results = (List<Produce>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Produce instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Produce as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Produce> findByOid(Object oid) {
		return findByProperty(OID, oid);
	}

	public List<Produce> findByColor(Object color) {
		return findByProperty(COLOR, color);
	}

	public List<Produce> findByXs(Object xs) {
		return findByProperty(XS, xs);
	}

	public List<Produce> findByS(Object s) {
		return findByProperty(S, s);
	}

	public List<Produce> findByM(Object m) {
		return findByProperty(M, m);
	}

	public List<Produce> findByL(Object l) {
		return findByProperty(L, l);
	}

	public List<Produce> findByXl(Object xl) {
		return findByProperty(XL, xl);
	}

	public List<Produce> findByXxl(Object xxl) {
		return findByProperty(XXL, xxl);
	}

	public List<Produce> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findAll() {
		log.debug("finding all Produce instances");
		try {
			String queryString = "from Produce";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Produce merge(Produce detachedInstance) {
		log.debug("merging Produce instance");
		try {
			Produce result = (Produce) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Produce instance) {
		log.debug("attaching dirty Produce instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Produce instance) {
		log.debug("attaching clean Produce instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProduceDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ProduceDAO) ctx.getBean("ProduceDAO");
	}

	@Override
	public List<Produce> findByOrderId(Object orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteByProperty(String propertyName, Object orderId) {
		// TODO Auto-generated method stub
		log.debug("deleting Produce instance with property: " + propertyName
				+ ", value: " + orderId);
		try {
			String queryString = "delete from Produce as model where model."
					+ propertyName + "= ?";
			 getHibernateTemplate().bulkUpdate(queryString, orderId);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}