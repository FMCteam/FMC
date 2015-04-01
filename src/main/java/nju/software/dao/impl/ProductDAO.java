package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.IProductDAO;
import nju.software.dataobject.Product;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Product entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see nju.software.dataobject.Product
 * @author MyEclipse Persistence Tools
 */
public class ProductDAO extends HibernateDaoSupport implements IProductDAO {
	private static final Logger log = LoggerFactory.getLogger(ProductDAO.class);
	// property constants
	public static final String ORDER_ID = "orderId";
	public static final String STYLE = "style";
	public static final String COLOR = "color";
	public static final String ASK_AMOUNT = "askAmount";
	public static final String PRODUCE_AMOUNT = "produceAmount";
	public static final String QUALIFIED_AMOUNT = "qualifiedAmount";

	@Override
	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#save(nju.software.dataobject.Product)
	 */
	@Override
	public void save(Product transientInstance) {
		log.debug("saving Product instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#delete(nju.software.dataobject.Product)
	 */
	@Override
	public void delete(Product persistentInstance) {
		log.debug("deleting Product instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#findById(java.lang.Integer)
	 */
	@Override
	public Product findById(java.lang.Integer id) {
		log.debug("getting Product instance with id: " + id);
		try {
			Product instance = (Product) getHibernateTemplate().get(
					"nju.software.dataobject.Product", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#findByExample(nju.software.dataobject.Product)
	 */
	@Override
	public List<Product> findByExample(Product instance) {
		log.debug("finding Product instance by example");
		try {
			List<Product> results = getHibernateTemplate()
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
	 * @see nju.software.dao.impl.IProductDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Product instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Product as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#findByOrderId(java.lang.Object)
	 */
	@Override
	public List<Product> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#findByStyle(java.lang.Object)
	 */
	@Override
	public List<Product> findByStyle(Object style) {
		return findByProperty(STYLE, style);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#findByColor(java.lang.Object)
	 */
	@Override
	public List<Product> findByColor(Object color) {
		return findByProperty(COLOR, color);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#findByAskAmount(java.lang.Object)
	 */
	@Override
	public List<Product> findByAskAmount(Object askAmount) {
		return findByProperty(ASK_AMOUNT, askAmount);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#findByProduceAmount(java.lang.Object)
	 */
	@Override
	public List<Product> findByProduceAmount(Object produceAmount) {
		return findByProperty(PRODUCE_AMOUNT, produceAmount);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#findByQualifiedAmount(java.lang.Object)
	 */
	@Override
	public List<Product> findByQualifiedAmount(Object qualifiedAmount) {
		return findByProperty(QUALIFIED_AMOUNT, qualifiedAmount);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Product instances");
		try {
			String queryString = "from Product";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#merge(nju.software.dataobject.Product)
	 */
	@Override
	public Product merge(Product detachedInstance) {
		log.debug("merging Product instance");
		try {
			Product result = getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#attachDirty(nju.software.dataobject.Product)
	 */
	@Override
	public void attachDirty(Product instance) {
		log.debug("attaching dirty Product instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IProductDAO#attachClean(nju.software.dataobject.Product)
	 */
	@Override
	public void attachClean(Product instance) {
		log.debug("attaching clean Product instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IProductDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IProductDAO) ctx.getBean("ProductDAO");
	}

}