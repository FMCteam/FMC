package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.IFabricDAO;
import nju.software.dataobject.Fabric;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fabric entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see nju.software.dataobject.Fabric
 * @author MyEclipse Persistence Tools
 */
public class FabricDAO extends HibernateDaoSupport implements IFabricDAO {
	private static final Logger log = LoggerFactory.getLogger(FabricDAO.class);
	// property constants
	public static final String ORDER_ID = "orderId";
	public static final String FABRIC_NAME = "fabricName";
	public static final String FABRIC_AMOUNT = "fabricAmount";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricDAO#save(nju.software.dataobject.Fabric)
	 */
	public void save(Fabric transientInstance) {
		log.debug("saving Fabric instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricDAO#delete(nju.software.dataobject.Fabric)
	 */
	public void delete(Fabric persistentInstance) {
		log.debug("deleting Fabric instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricDAO#findById(java.lang.Integer)
	 */
	public Fabric findById(java.lang.Integer id) {
		log.debug("getting Fabric instance with id: " + id);
		try {
			Fabric instance = (Fabric) getHibernateTemplate().get(
					"nju.software.dataobject.Fabric", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricDAO#findByExample(nju.software.dataobject.Fabric)
	 */
	public List<Fabric> findByExample(Fabric instance) {
		log.debug("finding Fabric instance by example");
		try {
			List<Fabric> results = (List<Fabric>) getHibernateTemplate()
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
	 * @see nju.software.dao.impl.IFabricDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fabric instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Fabric as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricDAO#findByOrderId(java.lang.Object)
	 */
	public List<Fabric> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricDAO#findByFabricName(java.lang.Object)
	 */
	public List<Fabric> findByFabricName(Object fabricName) {
		return findByProperty(FABRIC_NAME, fabricName);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricDAO#findByFabricAmount(java.lang.Object)
	 */
	public List<Fabric> findByFabricAmount(Object fabricAmount) {
		return findByProperty(FABRIC_AMOUNT, fabricAmount);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Fabric instances");
		try {
			String queryString = "from Fabric";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricDAO#merge(nju.software.dataobject.Fabric)
	 */
	public Fabric merge(Fabric detachedInstance) {
		log.debug("merging Fabric instance");
		try {
			Fabric result = (Fabric) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricDAO#attachDirty(nju.software.dataobject.Fabric)
	 */
	public void attachDirty(Fabric instance) {
		log.debug("attaching dirty Fabric instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IFabricDAO#attachClean(nju.software.dataobject.Fabric)
	 */
	public void attachClean(Fabric instance) {
		log.debug("attaching clean Fabric instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IFabricDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IFabricDAO) ctx.getBean("FabricDAO");
	}
}