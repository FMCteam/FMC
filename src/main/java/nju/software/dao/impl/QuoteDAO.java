package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.IQuoteDAO;
import nju.software.dataobject.Quote;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Quote
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see nju.software.dataobject.Quote
 * @author MyEclipse Persistence Tools
 */
public class QuoteDAO extends HibernateDaoSupport implements IQuoteDAO {
	private static final Logger log = LoggerFactory.getLogger(QuoteDAO.class);
	// property constants
	public static final String DESIGN_COST = "designCost";
	public static final String CUT_COST = "cutCost";
	public static final String MANAGE_COST = "manageCost";
	public static final String SWING_COST = "swingCost";
	public static final String IRONING_COST = "ironingCost";
	public static final String NAIL_COST = "nailCost";
	public static final String PACKAGE_COST = "packageCost";
	public static final String OTHER_COST = "otherCost";
	public static final String PROFIT_PER_PIECE = "profitPerPiece";
	public static final String INNER_PRICE = "innerPrice";
	public static final String OUTER_PRICE = "outerPrice";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#save(nju.software.dataobject.Quote)
	 */
	public void save(Quote transientInstance) {
		log.debug("saving Quote instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#delete(nju.software.dataobject.Quote)
	 */
	public void delete(Quote persistentInstance) {
		log.debug("deleting Quote instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findById(java.lang.Integer)
	 */
	public Quote findById(java.lang.Integer id) {
		log.debug("getting Quote instance with id: " + id);
		try {
			Quote instance = (Quote) getHibernateTemplate().get(
					"nju.software.dataobject.Quote", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findByExample(nju.software.dataobject.Quote)
	 */
	public List<Quote> findByExample(Quote instance) {
		log.debug("finding Quote instance by example");
		try {
			List<Quote> results = (List<Quote>) getHibernateTemplate()
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
	 * @see nju.software.dao.impl.IQuoteDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Quote instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Quote as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findByDesignCost(java.lang.Object)
	 */
	public List<Quote> findByDesignCost(Object designCost) {
		return findByProperty(DESIGN_COST, designCost);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findByCutCost(java.lang.Object)
	 */
	public List<Quote> findByCutCost(Object cutCost) {
		return findByProperty(CUT_COST, cutCost);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findByManageCost(java.lang.Object)
	 */
	public List<Quote> findByManageCost(Object manageCost) {
		return findByProperty(MANAGE_COST, manageCost);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findBySwingCost(java.lang.Object)
	 */
	public List<Quote> findBySwingCost(Object swingCost) {
		return findByProperty(SWING_COST, swingCost);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findByIroningCost(java.lang.Object)
	 */
	public List<Quote> findByIroningCost(Object ironingCost) {
		return findByProperty(IRONING_COST, ironingCost);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findByNailCost(java.lang.Object)
	 */
	public List<Quote> findByNailCost(Object nailCost) {
		return findByProperty(NAIL_COST, nailCost);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findByPackageCost(java.lang.Object)
	 */
	public List<Quote> findByPackageCost(Object packageCost) {
		return findByProperty(PACKAGE_COST, packageCost);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findByOtherCost(java.lang.Object)
	 */
	public List<Quote> findByOtherCost(Object otherCost) {
		return findByProperty(OTHER_COST, otherCost);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findByProfitPerPiece(java.lang.Object)
	 */
	public List<Quote> findByProfitPerPiece(Object profitPerPiece) {
		return findByProperty(PROFIT_PER_PIECE, profitPerPiece);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findByInnerPrice(java.lang.Object)
	 */
	public List<Quote> findByInnerPrice(Object innerPrice) {
		return findByProperty(INNER_PRICE, innerPrice);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findByOuterPrice(java.lang.Object)
	 */
	public List<Quote> findByOuterPrice(Object outerPrice) {
		return findByProperty(OUTER_PRICE, outerPrice);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Quote instances");
		try {
			String queryString = "from Quote";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#merge(nju.software.dataobject.Quote)
	 */
	public Quote merge(Quote detachedInstance) {
		log.debug("merging Quote instance");
		try {
			Quote result = (Quote) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#attachDirty(nju.software.dataobject.Quote)
	 */
	public void attachDirty(Quote instance) {
		log.debug("attaching dirty Quote instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IQuoteDAO#attachClean(nju.software.dataobject.Quote)
	 */
	public void attachClean(Quote instance) {
		log.debug("attaching clean Quote instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IQuoteDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IQuoteDAO) ctx.getBean("QuoteDAO");
	}
}