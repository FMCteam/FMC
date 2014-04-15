package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.IAccessoryDAO;
import nju.software.dataobject.Accessory;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Accessory entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see nju.software.dataobject.Accessory
 * @author MyEclipse Persistence Tools
 */
public class AccessoryDAO extends HibernateDaoSupport implements IAccessoryDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AccessoryDAO.class);
	// property constants
	public static final String ORDER_ID = "orderId";
	public static final String ACCESSORY_NAME = "accessoryName";
	public static final String ACCESSORY_QUERY = "accessoryQuery";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryDAO#save(nju.software.dataobject.Accessory)
	 */
	public void save(Accessory transientInstance) {
		log.debug("saving Accessory instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryDAO#delete(nju.software.dataobject.Accessory)
	 */
	public void delete(Accessory persistentInstance) {
		log.debug("deleting Accessory instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryDAO#findById(java.lang.Integer)
	 */
	public Accessory findById(java.lang.Integer id) {
		log.debug("getting Accessory instance with id: " + id);
		try {
			Accessory instance = (Accessory) getHibernateTemplate().get(
					"nju.software.dataobject.Accessory", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryDAO#findByExample(nju.software.dataobject.Accessory)
	 */
	public List<Accessory> findByExample(Accessory instance) {
		log.debug("finding Accessory instance by example");
		try {
			List<Accessory> results = (List<Accessory>) getHibernateTemplate()
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
	 * @see nju.software.dao.impl.IAccessoryDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Accessory instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Accessory as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryDAO#findByOrderId(java.lang.Object)
	 */
	public List<Accessory> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryDAO#findByAccessoryName(java.lang.Object)
	 */
	public List<Accessory> findByAccessoryName(Object accessoryName) {
		return findByProperty(ACCESSORY_NAME, accessoryName);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryDAO#findByAccessoryQuery(java.lang.Object)
	 */
	public List<Accessory> findByAccessoryQuery(Object accessoryQuery) {
		return findByProperty(ACCESSORY_QUERY, accessoryQuery);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Accessory instances");
		try {
			String queryString = "from Accessory";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryDAO#merge(nju.software.dataobject.Accessory)
	 */
	public Accessory merge(Accessory detachedInstance) {
		log.debug("merging Accessory instance");
		try {
			Accessory result = (Accessory) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryDAO#attachDirty(nju.software.dataobject.Accessory)
	 */
	public void attachDirty(Accessory instance) {
		log.debug("attaching dirty Accessory instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryDAO#attachClean(nju.software.dataobject.Accessory)
	 */
	public void attachClean(Accessory instance) {
		log.debug("attaching clean Accessory instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IAccessoryDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IAccessoryDAO) ctx.getBean("AccessoryDAO");
	}

	@Override
	public void deleteByProperty(String propertyName, Object orderId) {
		// TODO Auto-generated method stub
		log.debug("deleting Accessory instance with property: " + propertyName
				+ ", value: " + orderId);
		try {
			String queryString = "delete from Accessory as model where model."
					+ propertyName + "= ?";
			 getHibernateTemplate().bulkUpdate(queryString, orderId);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}