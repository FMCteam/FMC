package nju.software.dao.impl;

import java.util.List;
import nju.software.dao.ICheckDetailDAO;
import nju.software.dataobject.CheckDetail;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author luxiangfan
 * 
 */
public class CheckDetailDAO extends HibernateDaoSupport implements ICheckDetailDAO {
	private static final Logger log = LoggerFactory.getLogger(DeliveryRecordDAO.class);
	public static final String RECORD_ID = "recordId";

	@Override
	public void save(CheckDetail transientInstance) {
		log.debug("saving CheckDetail instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(CheckDetail persistentInstance) {
		log.debug("deleting CheckDetail instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public CheckDetail findById(Integer id) {
		log.debug("getting CheckDetail instance with id: " + id);
		try {

			CheckDetail instance = (CheckDetail) getHibernateTemplate().get(
					"nju.software.dataobject.CheckDetail", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<CheckDetail> findByExample(CheckDetail instance) {
		log.debug("finding CheckDetail instance by example");
		try {
			List<CheckDetail> results = getHibernateTemplate()
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
		log.debug("finding CheckDetail instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CheckDetail as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	@Override
	public List<CheckDetail> findByRecordId(Object recordId) {
		return findByProperty(RECORD_ID, recordId);
	}

	@Override
	public List findAll() {
		log.debug("finding all CheckDetail instances");
		try {
			String queryString = "from CheckDetail";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public CheckDetail merge(CheckDetail detachedInstance) {
		log.debug("merging Craft instance");
		try {
			CheckDetail result = getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(CheckDetail instance) {
		log.debug("attaching dirty CheckDetail instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(CheckDetail instance) {
		log.debug("attaching clean CheckDetail instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ICheckDetailDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ICheckDetailDAO) ctx.getBean("CheckDetailDAO");
	}
}
