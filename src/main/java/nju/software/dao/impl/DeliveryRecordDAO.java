package nju.software.dao.impl;

import java.util.List;
import nju.software.dao.IDeliveryRecordDAO;
import nju.software.dataobject.DeliveryRecord;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 * @author luxiangfan
 *
 */
public class DeliveryRecordDAO extends HibernateDaoSupport implements IDeliveryRecordDAO{
	private static final Logger log = LoggerFactory.getLogger(DeliveryRecordDAO.class);
	public static final String ORDER_ID = "orderId";
	
	@Override
	public void save(DeliveryRecord transientInstance) {
		log.debug("saving DeliveryRecord instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(DeliveryRecord persistentInstance) {
		log.debug("deleting DeliveryRecord instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public DeliveryRecord findById(Integer id) {
		log.debug("getting DeliveryRecord instance with id: " + id);
		try {
 
			DeliveryRecord instance = (DeliveryRecord) getHibernateTemplate().get(
					"nju.software.dataobject.DeliveryRecord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<DeliveryRecord> findByExample(DeliveryRecord instance) {
		log.debug("finding DeliveryRecord instance by example");
		try {
			List<DeliveryRecord> results = (List<DeliveryRecord>) getHibernateTemplate()
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
		log.debug("finding DeliveryRecord instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from DeliveryRecord as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	@Override
	public List<DeliveryRecord> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	@Override
	public List findAll() {
		log.debug("finding all DeliveryRecord instances");
		try {
			String queryString = "from DeliveryRecord";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public DeliveryRecord merge(DeliveryRecord detachedInstance) {
		log.debug("merging Craft instance");
		try {
			DeliveryRecord result = (DeliveryRecord) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(DeliveryRecord instance) {
		log.debug("attaching dirty DeliveryRecord instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(DeliveryRecord instance) {
		log.debug("attaching clean DeliveryRecord instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IDeliveryRecordDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IDeliveryRecordDAO) ctx.getBean("DeliveryRecordDAO");
	}
}
