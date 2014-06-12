package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.IAccessoryCostDAO;
import nju.software.dataobject.AccessoryCost;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * AccessoryCost entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see nju.software.dataobject.AccessoryCost
 * @author MyEclipse Persistence Tools
 */
public class AccessoryCostDAO extends HibernateDaoSupport implements IAccessoryCostDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AccessoryCostDAO.class);
	// property constants
	public static final String ORDER_ID = "orderId";
	public static final String ACCESSORY_NAME = "accessoryName";
	public static final String TEAR_PER_PIECE = "tearPerPiece";
	public static final String PRICE = "price";
	public static final String COST_PER_PIECE = "costPerPiece";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#save(nju.software.dataobject.AccessoryCost)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#save(nju.software.dataobject.AccessoryCost)
	 */
	public void save(AccessoryCost transientInstance) {
		log.debug("saving AccessoryCost instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#delete(nju.software.dataobject.AccessoryCost)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#delete(nju.software.dataobject.AccessoryCost)
	 */
	public void delete(AccessoryCost persistentInstance) {
		log.debug("deleting AccessoryCost instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findById(java.lang.Integer)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findById(java.lang.Integer)
	 */
	public AccessoryCost findById(java.lang.Integer id) {
		log.debug("getting AccessoryCost instance with id: " + id);
		try {
			AccessoryCost instance = (AccessoryCost) getHibernateTemplate()
					.get("nju.software.dataobject.AccessoryCost", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByExample(nju.software.dataobject.AccessoryCost)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByExample(nju.software.dataobject.AccessoryCost)
	 */
	public List<AccessoryCost> findByExample(AccessoryCost instance) {
		log.debug("finding AccessoryCost instance by example");
		try {
			List<AccessoryCost> results = (List<AccessoryCost>) getHibernateTemplate()
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
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding AccessoryCost instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from AccessoryCost as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByOrderId(java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByOrderId(java.lang.Object)
	 */
	public List<AccessoryCost> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByAccessoryName(java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByAccessoryName(java.lang.Object)
	 */
	public List<AccessoryCost> findByAccessoryName(Object accessoryName) {
		return findByProperty(ACCESSORY_NAME, accessoryName);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByTearPerPiece(java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByTearPerPiece(java.lang.Object)
	 */
	public List<AccessoryCost> findByTearPerPiece(Object tearPerPiece) {
		return findByProperty(TEAR_PER_PIECE, tearPerPiece);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByPrice(java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByPrice(java.lang.Object)
	 */
	public List<AccessoryCost> findByPrice(Object price) {
		return findByProperty(PRICE, price);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByCostPerPiece(java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findByCostPerPiece(java.lang.Object)
	 */
	public List<AccessoryCost> findByCostPerPiece(Object costPerPiece) {
		return findByProperty(COST_PER_PIECE, costPerPiece);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findAll()
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all AccessoryCost instances");
		try {
			String queryString = "from AccessoryCost";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#merge(nju.software.dataobject.AccessoryCost)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#merge(nju.software.dataobject.AccessoryCost)
	 */
	public AccessoryCost merge(AccessoryCost detachedInstance) {
		log.debug("merging AccessoryCost instance");
		try {
			AccessoryCost result = (AccessoryCost) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#attachDirty(nju.software.dataobject.AccessoryCost)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#attachDirty(nju.software.dataobject.AccessoryCost)
	 */
	public void attachDirty(AccessoryCost instance) {
		log.debug("attaching dirty AccessoryCost instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#attachClean(nju.software.dataobject.AccessoryCost)
	 */
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IAccessoryCostDAO#attachClean(nju.software.dataobject.AccessoryCost)
	 */
	public void attachClean(AccessoryCost instance) {
		log.debug("attaching clean AccessoryCost instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IAccessoryCostDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IAccessoryCostDAO) ctx.getBean("AccessoryCostDAO");
	}

	@Override
	public void deleteByProperty(String propertyName, Object orderId) {
		// TODO Auto-generated method stub
		log.debug("deleting AccessoryCost instance with property: " + propertyName
				+ ", value: " + orderId);
		try {
			String queryString = "delete from AccessoryCost as model where model."
					+ propertyName + "= ?";
			 getHibernateTemplate().bulkUpdate(queryString, orderId);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
		
	}
}