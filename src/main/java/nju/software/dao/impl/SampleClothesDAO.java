package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.ISampleClothesDAO;
import nju.software.dataobject.SampleClothes;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for SampleClothes entities.
 * Transaction control of the save(), update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how to configure it for
 * the desired type of transaction control.
 * 
 * @see nju.software.dataobject.SampleClothes
 * @author MyEclipse Persistence Tools
 */
public class SampleClothesDAO extends HibernateDaoSupport implements ISampleClothesDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SampleClothesDAO.class);
	// property constants
	public static final String ORDER_ID = "orderId";
	public static final String COLOR = "color";
	public static final String STYLE = "style";
	public static final String AMOUNT = "amount";
	public static final String PRICE = "price";

	protected void initDao() {
		// do nothing
	}

	/* (非 Javadoc) 
	* <p>Title: save</p> 
	* <p>Description: </p> 
	* @param transientInstance 
	* @see nju.software.dataobject.ISampleClothesDAO#save(nju.software.dataobject.SampleClothes) 
	*/
	
	public void save(SampleClothes transientInstance) {
		log.debug("saving SampleClothes instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (非 Javadoc) 
	* <p>Title: delete</p> 
	* <p>Description: </p> 
	* @param persistentInstance 
	* @see nju.software.dataobject.ISampleClothesDAO#delete(nju.software.dataobject.SampleClothes) 
	*/
	
	public void delete(SampleClothes persistentInstance) {
		log.debug("deleting SampleClothes instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (非 Javadoc) 
	* <p>Title: findById</p> 
	* <p>Description: </p> 
	* @param id
	* @return 
	* @see nju.software.dataobject.ISampleClothesDAO#findById(java.lang.Integer) 
	*/
	
	public SampleClothes findById(java.lang.Integer id) {
		log.debug("getting SampleClothes instance with id: " + id);
		try {
			SampleClothes instance = (SampleClothes) getHibernateTemplate()
					.get("nju.software.dataobject.SampleClothes", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (非 Javadoc) 
	* <p>Title: findByExample</p> 
	* <p>Description: </p> 
	* @param instance
	* @return 
	* @see nju.software.dataobject.ISampleClothesDAO#findByExample(nju.software.dataobject.SampleClothes) 
	*/
	
	public List<SampleClothes> findByExample(SampleClothes instance) {
		log.debug("finding SampleClothes instance by example");
		try {
			List<SampleClothes> results = (List<SampleClothes>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/* (非 Javadoc) 
	* <p>Title: findByProperty</p> 
	* <p>Description: </p> 
	* @param propertyName
	* @param value
	* @return 
	* @see nju.software.dataobject.ISampleClothesDAO#findByProperty(java.lang.String, java.lang.Object) 
	*/
	
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding SampleClothes instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SampleClothes as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (非 Javadoc) 
	* <p>Title: findByOrderId</p> 
	* <p>Description: </p> 
	* @param orderId
	* @return 
	* @see nju.software.dataobject.ISampleClothesDAO#findByOrderId(java.lang.Object) 
	*/
	
	public List<SampleClothes> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	/* (非 Javadoc) 
	* <p>Title: findByColor</p> 
	* <p>Description: </p> 
	* @param color
	* @return 
	* @see nju.software.dataobject.ISampleClothesDAO#findByColor(java.lang.Object) 
	*/
	
	public List<SampleClothes> findByColor(Object color) {
		return findByProperty(COLOR, color);
	}

	/* (非 Javadoc) 
	* <p>Title: findByStyle</p> 
	* <p>Description: </p> 
	* @param style
	* @return 
	* @see nju.software.dataobject.ISampleClothesDAO#findByStyle(java.lang.Object) 
	*/
	
	public List<SampleClothes> findByStyle(Object style) {
		return findByProperty(STYLE, style);
	}

	/* (非 Javadoc) 
	* <p>Title: findByAmount</p> 
	* <p>Description: </p> 
	* @param amount
	* @return 
	* @see nju.software.dataobject.ISampleClothesDAO#findByAmount(java.lang.Object) 
	*/
	
	public List<SampleClothes> findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	/* (非 Javadoc) 
	* <p>Title: findByPrice</p> 
	* <p>Description: </p> 
	* @param price
	* @return 
	* @see nju.software.dataobject.ISampleClothesDAO#findByPrice(java.lang.Object) 
	*/
	
	public List<SampleClothes> findByPrice(Object price) {
		return findByProperty(PRICE, price);
	}

	/* (非 Javadoc) 
	* <p>Title: findAll</p> 
	* <p>Description: </p> 
	* @return 
	* @see nju.software.dataobject.ISampleClothesDAO#findAll() 
	*/
	
	public List findAll() {
		log.debug("finding all SampleClothes instances");
		try {
			String queryString = "from SampleClothes";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (非 Javadoc) 
	* <p>Title: merge</p> 
	* <p>Description: </p> 
	* @param detachedInstance
	* @return 
	* @see nju.software.dataobject.ISampleClothesDAO#merge(nju.software.dataobject.SampleClothes) 
	*/
	
	public SampleClothes merge(SampleClothes detachedInstance) {
		log.debug("merging SampleClothes instance");
		try {
			SampleClothes result = (SampleClothes) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (非 Javadoc) 
	* <p>Title: attachDirty</p> 
	* <p>Description: </p> 
	* @param instance 
	* @see nju.software.dataobject.ISampleClothesDAO#attachDirty(nju.software.dataobject.SampleClothes) 
	*/
	
	public void attachDirty(SampleClothes instance) {
		log.debug("attaching dirty SampleClothes instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (非 Javadoc) 
	* <p>Title: attachClean</p> 
	* <p>Description: </p> 
	* @param instance 
	* @see nju.software.dataobject.ISampleClothesDAO#attachClean(nju.software.dataobject.SampleClothes) 
	*/
	
	public void attachClean(SampleClothes instance) {
		log.debug("attaching clean SampleClothes instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ISampleClothesDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ISampleClothesDAO) ctx.getBean("SampleClothesDAO");
	}
}