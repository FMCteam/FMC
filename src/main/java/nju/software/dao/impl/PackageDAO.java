package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.IPackageDAO;
import nju.software.dataobject.Package;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Package entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see nju.software.dataobject.Package
 * @author MyEclipse Persistence Tools
 */
public class PackageDAO extends HibernateDaoSupport implements IPackageDAO {
	private static final Logger log = LoggerFactory.getLogger(PackageDAO.class);
	// property constants
	public static final String ORDER_ID = "orderId";

	@Override
	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDAO#save(nju.software.dataobject.Package)
	 */
	@Override
	public void save(Package transientInstance) {
		log.debug("saving Package instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDAO#delete(nju.software.dataobject.Package)
	 */
	@Override
	public void delete(Package persistentInstance) {
		log.debug("deleting Package instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDAO#findById(java.lang.Integer)
	 */
	@Override
	public Package findById(java.lang.Integer id) {
		log.debug("getting Package instance with id: " + id);
		try {
			Package instance = (Package) getHibernateTemplate().get(
					"nju.software.dataobject.Package", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDAO#findByExample(nju.software.dataobject.Package)
	 */
	@Override
	public List<Package> findByExample(Package instance) {
		log.debug("finding Package instance by example");
		try {
			List<Package> results = getHibernateTemplate()
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
	 * @see nju.software.dao.impl.IPackageDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Package instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Package as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDAO#findByOrderId(java.lang.Object)
	 */
	@Override
	public List<Package> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Package instances");
		try {
			String queryString = "from Package";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDAO#merge(nju.software.dataobject.Package)
	 */
	@Override
	public Package merge(Package detachedInstance) {
		log.debug("merging Package instance");
		try {
			Package result = getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDAO#attachDirty(nju.software.dataobject.Package)
	 */
	@Override
	public void attachDirty(Package instance) {
		log.debug("attaching dirty Package instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDAO#attachClean(nju.software.dataobject.Package)
	 */
	@Override
	public void attachClean(Package instance) {
		log.debug("attaching clean Package instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IPackageDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IPackageDAO) ctx.getBean("PackageDAO");
	}
}