package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.IFabricCostDAO;
import nju.software.dataobject.FabricCost;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * FabricCost entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see nju.software.dataobject.FabricCost
 * @author MyEclipse Persistence Tools
 */
public class FabricCostDAO extends HibernateDaoSupport implements IFabricCostDAO {
	private static final Logger log = LoggerFactory
			.getLogger(FabricCostDAO.class);
	// property constants
	public static final String ORDER_ID = "orderId";
	public static final String FABRIC_NAME = "fabricName";
	public static final String TEAR_PER_METER = "tearPerMeter";
	public static final String PRICE = "price";
	public static final String COST_PER_METER = "costPerMeter";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#save(nju.software.dataobject.FabricCost)
	 */
	public void save(FabricCost transientInstance) {
		log.debug("saving FabricCost instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#delete(nju.software.dataobject.FabricCost)
	 */
	public void delete(FabricCost persistentInstance) {
		log.debug("deleting FabricCost instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#findById(java.lang.Integer)
	 */
	public FabricCost findById(java.lang.Integer id) {
		log.debug("getting FabricCost instance with id: " + id);
		try {
			FabricCost instance = (FabricCost) getHibernateTemplate().get(
					"nju.software.dataobject.FabricCost", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#findByExample(nju.software.dataobject.FabricCost)
	 */
	public List<FabricCost> findByExample(FabricCost instance) {
		log.debug("finding FabricCost instance by example");
		try {
			List<FabricCost> results = (List<FabricCost>) getHibernateTemplate()
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
	 * @see nju.software.dao.impl.IFabricCostDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding FabricCost instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FabricCost as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#findByOrderId(java.lang.Object)
	 */
	public List<FabricCost> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#findByFabricName(java.lang.Object)
	 */
	public List<FabricCost> findByFabricName(Object fabricName) {
		return findByProperty(FABRIC_NAME, fabricName);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#findByTearPerMeter(java.lang.Object)
	 */
	public List<FabricCost> findByTearPerMeter(Object tearPerMeter) {
		return findByProperty(TEAR_PER_METER, tearPerMeter);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#findByPrice(java.lang.Object)
	 */
	public List<FabricCost> findByPrice(Object price) {
		return findByProperty(PRICE, price);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#findByCostPerMeter(java.lang.Object)
	 */
	public List<FabricCost> findByCostPerMeter(Object costPerMeter) {
		return findByProperty(COST_PER_METER, costPerMeter);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all FabricCost instances");
		try {
			String queryString = "from FabricCost";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#merge(nju.software.dataobject.FabricCost)
	 */
	public FabricCost merge(FabricCost detachedInstance) {
		log.debug("merging FabricCost instance");
		try {
			FabricCost result = (FabricCost) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#attachDirty(nju.software.dataobject.FabricCost)
	 */
	public void attachDirty(FabricCost instance) {
		log.debug("attaching dirty FabricCost instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricCostDAO#attachClean(nju.software.dataobject.FabricCost)
	 */
	public void attachClean(FabricCost instance) {
		log.debug("attaching clean FabricCost instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IFabricCostDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IFabricCostDAO) ctx.getBean("FabricCostDAO");
	}
}