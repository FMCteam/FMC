package nju.software.dao.impl;
import java.util.List;
import nju.software.dao.IOperateRecordDAO;
import nju.software.dataobject.OperateRecord;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * OperateRecord entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see nju.software.dataobject.OperateRecord
 * @author MyEclipse Persistence Tools
 */
public class OperateRecordDAO extends HibernateDaoSupport implements IOperateRecordDAO {
	private static final Logger log = LoggerFactory
			.getLogger(OperateRecordDAO.class);
	// property constants
	public static final String ORDER_ID = "orderId";
	public static final String TASK_NAME = "taskName";
	public static final String OPERATE_PERSON = "operatePerson";
	public static final String OPERATE_REMARK = "operateRemark";

	public void save(OperateRecord transientInstance) {
		log.debug("saving OperateRecord instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(OperateRecord persistentInstance) {
		log.debug("deleting OperateRecord instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public OperateRecord findById(java.lang.Integer id) {
		log.debug("getting OperateRecord instance with id: " + id);
		try {
			OperateRecord instance = (OperateRecord) getHibernateTemplate().get(
					"nju.software.dataobject.OperateRecord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(OperateRecord instance) {
		log.debug("finding OperateRecord instance by example");
		try {
			List<OperateRecord> results =(List<OperateRecord>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding OperateRecord instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from OperateRecord as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<OperateRecord> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	public List<OperateRecord> findByTaskName(Object taskName) {
		return findByProperty(TASK_NAME, taskName);
	}

	public List<OperateRecord> findByOperatePerson(Object operatePerson) {
		return findByProperty(OPERATE_PERSON, operatePerson);
	}

	public List<OperateRecord> findByOperateRemark(Object operateRemark) {
		return findByProperty(OPERATE_REMARK, operateRemark);
	}

	public List findAll() {
		log.debug("finding all OperateRecord instances");
		try {
			String queryString = "from OperateRecord";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public OperateRecord merge(OperateRecord detachedInstance) {
		log.debug("merging OperateRecord instance");
		try {
			OperateRecord result = (OperateRecord) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(OperateRecord instance) {
		log.debug("attaching dirty OperateRecord instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(OperateRecord instance) {
		log.debug("attaching clean OperateRecord instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	

	public static IOperateRecordDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IOperateRecordDAO) ctx.getBean("OperateRecordDAO");
	}
}