package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.IDescriptionDAO;
import nju.software.dataobject.Description;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Description entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see nju.software.dataobject.Description
 * @author MyEclipse Persistence Tools
 */
public class DescriptionDAO extends HibernateDaoSupport implements IDescriptionDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DescriptionDAO.class);
	// property constants
	public static final String SAMPLE_CLOTHES_CUSTOMER_DESCRIPTION = "sampleClothesCustomerDescription";
	public static final String SAMPLE_CLOTHES_DESIGN_DESCRIPTION = "sampleClothesDesignDescription";
	public static final String PRODUCT_CLOTHES_CUSTOMER_DESCRIPTION = "productClothesCustomerDescription";
	public static final String PRODUCT_CLOTHES_DESIGN_DESCRIPTION = "productClothesDesignDescription";

	@Override
	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDescriptionDAO#save(nju.software.dataobject.Description)
	 */
	@Override
	public void save(Description transientInstance) {
		log.debug("saving Description instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDescriptionDAO#delete(nju.software.dataobject.Description)
	 */
	@Override
	public void delete(Description persistentInstance) {
		log.debug("deleting Description instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDescriptionDAO#findById(java.lang.Integer)
	 */
	@Override
	public Description findById(java.lang.Integer id) {
		log.debug("getting Description instance with id: " + id);
		try {
			Description instance = (Description) getHibernateTemplate().get(
					"nju.software.dataobject.Description", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDescriptionDAO#findByExample(nju.software.dataobject.Description)
	 */
	@Override
	public List<Description> findByExample(Description instance) {
		log.debug("finding Description instance by example");
		try {
			List<Description> results = getHibernateTemplate()
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
	 * @see nju.software.dao.impl.IDescriptionDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Description instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Description as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDescriptionDAO#findBySampleClothesCustomerDescription(java.lang.Object)
	 */
	@Override
	public List<Description> findBySampleClothesCustomerDescription(
			Object sampleClothesCustomerDescription) {
		return findByProperty(SAMPLE_CLOTHES_CUSTOMER_DESCRIPTION,
				sampleClothesCustomerDescription);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDescriptionDAO#findBySampleClothesDesignDescription(java.lang.Object)
	 */
	@Override
	public List<Description> findBySampleClothesDesignDescription(
			Object sampleClothesDesignDescription) {
		return findByProperty(SAMPLE_CLOTHES_DESIGN_DESCRIPTION,
				sampleClothesDesignDescription);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDescriptionDAO#findByProductClothesCustomerDescription(java.lang.Object)
	 */
	@Override
	public List<Description> findByProductClothesCustomerDescription(
			Object productClothesCustomerDescription) {
		return findByProperty(PRODUCT_CLOTHES_CUSTOMER_DESCRIPTION,
				productClothesCustomerDescription);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDescriptionDAO#findByProductClothesDesignDescription(java.lang.Object)
	 */
	@Override
	public List<Description> findByProductClothesDesignDescription(
			Object productClothesDesignDescription) {
		return findByProperty(PRODUCT_CLOTHES_DESIGN_DESCRIPTION,
				productClothesDesignDescription);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDescriptionDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Description instances");
		try {
			String queryString = "from Description";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDescriptionDAO#merge(nju.software.dataobject.Description)
	 */
	@Override
	public Description merge(Description detachedInstance) {
		log.debug("merging Description instance");
		try {
			Description result = getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDescriptionDAO#attachDirty(nju.software.dataobject.Description)
	 */
	@Override
	public void attachDirty(Description instance) {
		log.debug("attaching dirty Description instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IDescriptionDAO#attachClean(nju.software.dataobject.Description)
	 */
	@Override
	public void attachClean(Description instance) {
		log.debug("attaching clean Description instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IDescriptionDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IDescriptionDAO) ctx.getBean("DescriptionDAO");
	}
}