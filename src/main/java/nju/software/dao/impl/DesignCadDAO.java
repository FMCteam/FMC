package nju.software.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import nju.software.dao.IDesignCadDAO;
import nju.software.dataobject.DesignCad;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * DesignCad entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see nju.software.dataobject.DesignCad
 * @author MyEclipse Persistence Tools
 */
public class DesignCadDAO extends HibernateDaoSupport implements IDesignCadDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DesignCadDAO.class);
	// property constants
	public static final String ORDER_ID = "orderId";
	public static final String CAD_URL = "cadUrl";
	public static final String CAD_VERSION = "cadVersion";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDesignCadDAO#save(nju.software.dataobject.DesignCad)
	 */
	public void save(DesignCad transientInstance) {
		log.debug("saving DesignCad instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDesignCadDAO#delete(nju.software.dataobject.DesignCad)
	 */
	public void delete(DesignCad persistentInstance) {
		log.debug("deleting DesignCad instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDesignCadDAO#findById(java.lang.Integer)
	 */
	public DesignCad findById(java.lang.Integer id) {
		log.debug("getting DesignCad instance with id: " + id);
		try {
			DesignCad instance = (DesignCad) getHibernateTemplate().get(
					"nju.software.dataobject.DesignCad", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDesignCadDAO#findByExample(nju.software.dataobject.DesignCad)
	 */
	public List<DesignCad> findByExample(DesignCad instance) {
		log.debug("finding DesignCad instance by example");
		try {
			List<DesignCad> results = (List<DesignCad>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDesignCadDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding DesignCad instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from DesignCad as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDesignCadDAO#findByOrderId(java.lang.Object)
	 */
	public List<DesignCad> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDesignCadDAO#findByCadUrl(java.lang.Object)
	 */
	public List<DesignCad> findByCadUrl(Object cadUrl) {
		return findByProperty(CAD_URL, cadUrl);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDesignCadDAO#findByCadVersion(java.lang.Object)
	 */
	public List<DesignCad> findByCadVersion(Object cadVersion) {
		return findByProperty(CAD_VERSION, cadVersion);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDesignCadDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all DesignCad instances");
		try {
			String queryString = "from DesignCad";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDesignCadDAO#merge(nju.software.dataobject.DesignCad)
	 */
	public DesignCad merge(DesignCad detachedInstance) {
		log.debug("merging DesignCad instance");
		try {
			DesignCad result = (DesignCad) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDesignCadDAO#attachDirty(nju.software.dataobject.DesignCad)
	 */
	public void attachDirty(DesignCad instance) {
		log.debug("attaching dirty DesignCad instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDesignCadDAO#attachClean(nju.software.dataobject.DesignCad)
	 */
	public void attachClean(DesignCad instance) {
		log.debug("attaching clean DesignCad instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IDesignCadDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IDesignCadDAO) ctx.getBean("DesignCadDAO");
	}
}