package nju.software.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import nju.software.dao.ILogisticsDAO;
import nju.software.dataobject.Logistics;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Logistics entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see nju.software.dataobject.Logistics
 * @author MyEclipse Persistence Tools
 */
public class LogisticsDAO extends HibernateDaoSupport implements ILogisticsDAO {
	private static final Logger log = LoggerFactory
			.getLogger(LogisticsDAO.class);
	// property constants
	public static final String IN_POST_SAMPLE_CLOTHES_TYPE = "inPostSampleClothesType";
	public static final String IN_POST_SAMPLE_CLOTHES_NUMBER = "inPostSampleClothesNumber";
	public static final String SAMPLE_CLOTHES_TYPE = "sampleClothesType";
	public static final String SAMPLE_CLOTHES_ADDRESS = "sampleClothesAddress";
	public static final String SAMPLE_CLOTHES_NAME = "sampleClothesName";
	public static final String SAMPLE_CLOTHES_PHONE = "sampleClothesPhone";
	public static final String SAMPLE_CLOTHES_REMARK = "sampleClothesRemark";
	public static final String PRODUCT_CLOTHES_TYPE = "productClothesType";
	public static final String PRODUCT_CLOTHES_ADDRESS = "productClothesAddress";
	public static final String PRODUCT_CLOTHES_PRICE = "productClothesPrice";
	public static final String PRODUCT_CLOTHES_NUMBER = "productClothesNumber";
	public static final String PRODUCT_CLOTHES_NAME = "productClothesName";
	public static final String PRODUCT_CLOTHES_PHONE = "productClothesPhone";
	public static final String PRODUCT_CLOTHES_REMARK = "productClothesRemark";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#save(nju.software.dataobject.Logistics)
	 */
	public void save(Logistics transientInstance) {
		log.debug("saving Logistics instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#delete(nju.software.dataobject.Logistics)
	 */
	public void delete(Logistics persistentInstance) {
		log.debug("deleting Logistics instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findById(java.lang.Integer)
	 */
	public Logistics findById(java.lang.Integer id) {
		log.debug("getting Logistics instance with id: " + id);
		try {
			Logistics instance = (Logistics) getHibernateTemplate().get(
					"nju.software.dataobject.Logistics", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findByExample(nju.software.dataobject.Logistics)
	 */
	public List<Logistics> findByExample(Logistics instance) {
		log.debug("finding Logistics instance by example");
		try {
			List<Logistics> results = (List<Logistics>) getHibernateTemplate()
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
	 * @see nju.software.dao.impl.ILogisticsDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Logistics instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Logistics as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findByInPostSampleClothesType(java.lang.Object)
	 */
	public List<Logistics> findByInPostSampleClothesType(
			Object inPostSampleClothesType) {
		return findByProperty(IN_POST_SAMPLE_CLOTHES_TYPE,
				inPostSampleClothesType);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findByInPostSampleClothesNumber(java.lang.Object)
	 */
	public List<Logistics> findByInPostSampleClothesNumber(
			Object inPostSampleClothesNumber) {
		return findByProperty(IN_POST_SAMPLE_CLOTHES_NUMBER,
				inPostSampleClothesNumber);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findBySampleClothesType(java.lang.Object)
	 */
	public List<Logistics> findBySampleClothesType(Object sampleClothesType) {
		return findByProperty(SAMPLE_CLOTHES_TYPE, sampleClothesType);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findBySampleClothesAddress(java.lang.Object)
	 */
	public List<Logistics> findBySampleClothesAddress(
			Object sampleClothesAddress) {
		return findByProperty(SAMPLE_CLOTHES_ADDRESS, sampleClothesAddress);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findBySampleClothesName(java.lang.Object)
	 */
	public List<Logistics> findBySampleClothesName(Object sampleClothesName) {
		return findByProperty(SAMPLE_CLOTHES_NAME, sampleClothesName);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findBySampleClothesPhone(java.lang.Object)
	 */
	public List<Logistics> findBySampleClothesPhone(Object sampleClothesPhone) {
		return findByProperty(SAMPLE_CLOTHES_PHONE, sampleClothesPhone);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findBySampleClothesRemark(java.lang.Object)
	 */
	public List<Logistics> findBySampleClothesRemark(Object sampleClothesRemark) {
		return findByProperty(SAMPLE_CLOTHES_REMARK, sampleClothesRemark);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findByProductClothesType(java.lang.Object)
	 */
	public List<Logistics> findByProductClothesType(Object productClothesType) {
		return findByProperty(PRODUCT_CLOTHES_TYPE, productClothesType);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findByProductClothesAddress(java.lang.Object)
	 */
	public List<Logistics> findByProductClothesAddress(
			Object productClothesAddress) {
		return findByProperty(PRODUCT_CLOTHES_ADDRESS, productClothesAddress);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findByProductClothesPrice(java.lang.Object)
	 */
	public List<Logistics> findByProductClothesPrice(Object productClothesPrice) {
		return findByProperty(PRODUCT_CLOTHES_PRICE, productClothesPrice);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findByProductClothesNumber(java.lang.Object)
	 */
	public List<Logistics> findByProductClothesNumber(
			Object productClothesNumber) {
		return findByProperty(PRODUCT_CLOTHES_NUMBER, productClothesNumber);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findByProductClothesName(java.lang.Object)
	 */
	public List<Logistics> findByProductClothesName(Object productClothesName) {
		return findByProperty(PRODUCT_CLOTHES_NAME, productClothesName);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findByProductClothesPhone(java.lang.Object)
	 */
	public List<Logistics> findByProductClothesPhone(Object productClothesPhone) {
		return findByProperty(PRODUCT_CLOTHES_PHONE, productClothesPhone);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findByProductClothesRemark(java.lang.Object)
	 */
	public List<Logistics> findByProductClothesRemark(
			Object productClothesRemark) {
		return findByProperty(PRODUCT_CLOTHES_REMARK, productClothesRemark);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Logistics instances");
		try {
			String queryString = "from Logistics";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#merge(nju.software.dataobject.Logistics)
	 */
	public Logistics merge(Logistics detachedInstance) {
		log.debug("merging Logistics instance");
		try {
			Logistics result = (Logistics) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#attachDirty(nju.software.dataobject.Logistics)
	 */
	public void attachDirty(Logistics instance) {
		log.debug("attaching dirty Logistics instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ILogisticsDAO#attachClean(nju.software.dataobject.Logistics)
	 */
	public void attachClean(Logistics instance) {
		log.debug("attaching clean Logistics instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ILogisticsDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ILogisticsDAO) ctx.getBean("LogisticsDAO");
	}

	@Override
	public void deleteByProperty(String propertyName, Object orderId) {
		// TODO Auto-generated method stub
		
	}
}