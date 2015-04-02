/**
 * 
 */
package nju.software.dao.impl;

import java.util.List;
import nju.software.dao.ICheckRecordDAO;
import nju.software.dataobject.CheckRecord;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author luxiangfan
 * 
 */
public class CheckRecordDAO extends HibernateDaoSupport implements ICheckRecordDAO {
	private static final Logger log = LoggerFactory.getLogger(DeliveryRecordDAO.class);
	public static final String ORDER_ID = "orderId";

	@Override
	public void save(CheckRecord transientInstance) {
		log.debug("saving CheckRecord instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(CheckRecord persistentInstance) {
		log.debug("deleting CheckRecord instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public CheckRecord findById(Integer id) {
		log.debug("getting CheckRecord instance with id: " + id);
		try {

			CheckRecord instance = (CheckRecord) getHibernateTemplate().get(
					"nju.software.dataobject.CheckRecord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<CheckRecord> findByExample(CheckRecord instance) {
		log.debug("finding CheckRecord instance by example");
		try {
			List<CheckRecord> results = getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CheckRecord instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CheckRecord as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	@Override
	public List<CheckRecord> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	@Override
	public List findAll() {
		log.debug("finding all CheckRecord instances");
		try {
			String queryString = "from CheckRecord";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public CheckRecord merge(CheckRecord detachedInstance) {
		log.debug("merging Craft instance");
		try {
			CheckRecord result = getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(CheckRecord instance) {
		log.debug("attaching dirty CheckRecord instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(CheckRecord instance) {
		log.debug("attaching clean CheckRecord instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ICheckRecordDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ICheckRecordDAO) ctx.getBean("CheckRecordDAO");
	}

}
