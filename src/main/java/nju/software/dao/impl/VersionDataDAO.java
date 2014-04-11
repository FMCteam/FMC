package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.IVersionDataDAO;
import nju.software.dataobject.VersionData;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for VersionData entities.
 * Transaction control of the save(), update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how to configure it for
 * the desired type of transaction control.
 * 
 * @see nju.software.dataobject.VersionData
 * @author MyEclipse Persistence Tools
 */
public class VersionDataDAO extends HibernateDaoSupport implements IVersionDataDAO {
	private static final Logger log = LoggerFactory
			.getLogger(VersionDataDAO.class);
	// property constants
	public static final String ORDER_ID = "orderId";
	public static final String SIZE = "size";
	public static final String CENTER_BACK_LENGTH = "centerBackLength";
	public static final String BUST = "bust";
	public static final String WAISTLINE = "waistline";
	public static final String SHOULDER = "shoulder";
	public static final String BUTTOCK = "buttock";
	public static final String HEM = "hem";
	public static final String TROUSERS = "trousers";
	public static final String SKIRT = "skirt";
	public static final String SLEEVES = "sleeves";

	protected void initDao() {
		// do nothing
	}

	/* (非 Javadoc) 
	* <p>Title: save</p> 
	* <p>Description: </p> 
	* @param transientInstance 
	* @see nju.software.dataobject.IVersionDataDAO#save(nju.software.dataobject.VersionData) 
	*/
	
	public void save(VersionData transientInstance) {
		log.debug("saving VersionData instance");
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
	* @see nju.software.dataobject.IVersionDataDAO#delete(nju.software.dataobject.VersionData) 
	*/
	
	public void delete(VersionData persistentInstance) {
		log.debug("deleting VersionData instance");
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
	* @see nju.software.dataobject.IVersionDataDAO#findById(java.lang.Integer) 
	*/
	
	public VersionData findById(java.lang.Integer id) {
		log.debug("getting VersionData instance with id: " + id);
		try {
			VersionData instance = (VersionData) getHibernateTemplate().get(
					"nju.software.dataobject.VersionData", id);
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
	* @see nju.software.dataobject.IVersionDataDAO#findByExample(nju.software.dataobject.VersionData) 
	*/
	
	public List<VersionData> findByExample(VersionData instance) {
		log.debug("finding VersionData instance by example");
		try {
			List<VersionData> results = (List<VersionData>) getHibernateTemplate()
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
	* @see nju.software.dataobject.IVersionDataDAO#findByProperty(java.lang.String, java.lang.Object) 
	*/
	
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding VersionData instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from VersionData as model where model."
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
	* @see nju.software.dataobject.IVersionDataDAO#findByOrderId(java.lang.Object) 
	*/
	
	public List<VersionData> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	/* (非 Javadoc) 
	* <p>Title: findBySize</p> 
	* <p>Description: </p> 
	* @param size
	* @return 
	* @see nju.software.dataobject.IVersionDataDAO#findBySize(java.lang.Object) 
	*/
	
	public List<VersionData> findBySize(Object size) {
		return findByProperty(SIZE, size);
	}

	/* (非 Javadoc) 
	* <p>Title: findByCenterBackLength</p> 
	* <p>Description: </p> 
	* @param centerBackLength
	* @return 
	* @see nju.software.dataobject.IVersionDataDAO#findByCenterBackLength(java.lang.Object) 
	*/
	
	public List<VersionData> findByCenterBackLength(Object centerBackLength) {
		return findByProperty(CENTER_BACK_LENGTH, centerBackLength);
	}

	/* (非 Javadoc) 
	* <p>Title: findByBust</p> 
	* <p>Description: </p> 
	* @param bust
	* @return 
	* @see nju.software.dataobject.IVersionDataDAO#findByBust(java.lang.Object) 
	*/
	
	public List<VersionData> findByBust(Object bust) {
		return findByProperty(BUST, bust);
	}

	/* (非 Javadoc) 
	* <p>Title: findByWaistline</p> 
	* <p>Description: </p> 
	* @param waistline
	* @return 
	* @see nju.software.dataobject.IVersionDataDAO#findByWaistline(java.lang.Object) 
	*/
	
	public List<VersionData> findByWaistline(Object waistline) {
		return findByProperty(WAISTLINE, waistline);
	}

	/* (非 Javadoc) 
	* <p>Title: findByShoulder</p> 
	* <p>Description: </p> 
	* @param shoulder
	* @return 
	* @see nju.software.dataobject.IVersionDataDAO#findByShoulder(java.lang.Object) 
	*/
	
	public List<VersionData> findByShoulder(Object shoulder) {
		return findByProperty(SHOULDER, shoulder);
	}

	/* (非 Javadoc) 
	* <p>Title: findByButtock</p> 
	* <p>Description: </p> 
	* @param buttock
	* @return 
	* @see nju.software.dataobject.IVersionDataDAO#findByButtock(java.lang.Object) 
	*/
	
	public List<VersionData> findByButtock(Object buttock) {
		return findByProperty(BUTTOCK, buttock);
	}

	/* (非 Javadoc) 
	* <p>Title: findByHem</p> 
	* <p>Description: </p> 
	* @param hem
	* @return 
	* @see nju.software.dataobject.IVersionDataDAO#findByHem(java.lang.Object) 
	*/
	
	public List<VersionData> findByHem(Object hem) {
		return findByProperty(HEM, hem);
	}

	/* (非 Javadoc) 
	* <p>Title: findByTrousers</p> 
	* <p>Description: </p> 
	* @param trousers
	* @return 
	* @see nju.software.dataobject.IVersionDataDAO#findByTrousers(java.lang.Object) 
	*/
	
	public List<VersionData> findByTrousers(Object trousers) {
		return findByProperty(TROUSERS, trousers);
	}

	/* (非 Javadoc) 
	* <p>Title: findBySkirt</p> 
	* <p>Description: </p> 
	* @param skirt
	* @return 
	* @see nju.software.dataobject.IVersionDataDAO#findBySkirt(java.lang.Object) 
	*/
	
	public List<VersionData> findBySkirt(Object skirt) {
		return findByProperty(SKIRT, skirt);
	}

	/* (非 Javadoc) 
	* <p>Title: findBySleeves</p> 
	* <p>Description: </p> 
	* @param sleeves
	* @return 
	* @see nju.software.dataobject.IVersionDataDAO#findBySleeves(java.lang.Object) 
	*/
	
	public List<VersionData> findBySleeves(Object sleeves) {
		return findByProperty(SLEEVES, sleeves);
	}

	/* (非 Javadoc) 
	* <p>Title: findAll</p> 
	* <p>Description: </p> 
	* @return 
	* @see nju.software.dataobject.IVersionDataDAO#findAll() 
	*/
	
	public List findAll() {
		log.debug("finding all VersionData instances");
		try {
			String queryString = "from VersionData";
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
	* @see nju.software.dataobject.IVersionDataDAO#merge(nju.software.dataobject.VersionData) 
	*/
	
	public VersionData merge(VersionData detachedInstance) {
		log.debug("merging VersionData instance");
		try {
			VersionData result = (VersionData) getHibernateTemplate().merge(
					detachedInstance);
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
	* @see nju.software.dataobject.IVersionDataDAO#attachDirty(nju.software.dataobject.VersionData) 
	*/
	
	public void attachDirty(VersionData instance) {
		log.debug("attaching dirty VersionData instance");
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
	* @see nju.software.dataobject.IVersionDataDAO#attachClean(nju.software.dataobject.VersionData) 
	*/
	
	public void attachClean(VersionData instance) {
		log.debug("attaching clean VersionData instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IVersionDataDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IVersionDataDAO) ctx.getBean("VersionDataDAO");
	}
	
	@Override
	public void deleteByProperty(String propertyName, Object orderId) {
		// TODO Auto-generated method stub
		log.debug("deleting VersionData instance with property: " + propertyName
				+ ", value: " + orderId);
		try {
			String queryString = "delete from VersionData as model where model."
					+ propertyName + "= ?";
			 getHibernateTemplate().bulkUpdate(queryString, orderId);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
}