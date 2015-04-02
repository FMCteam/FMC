package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.IMarketstaffAlterDAO;
import nju.software.dataobject.MarketstaffAlter;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class MarketstaffAlterDAO extends HibernateDaoSupport implements IMarketstaffAlterDAO{

	private static final Logger log = LoggerFactory.getLogger(MarketstaffAlterDAO.class);
	private static final String EMPLOYEE_ID = "employeeId";
	private static final String VERIFY_STATE = "verifyState";
	@Override
	public void save(MarketstaffAlter transientInstance) {
		log.debug("save MarketstaffAlter instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.debug("save failed", re);
			throw re;
		}
		
	}

	@Override
	public void delete(MarketstaffAlter persistentInstance) {
		log.debug("delete MarketstaffAlter instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete sucessful");
		} catch (RuntimeException re) {
			log.debug("delte failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(MarketstaffAlter instance) {
		log.debug("attaching dirty MarketstaffAlter instance");
		try{
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		}catch(RuntimeException re){
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(MarketstaffAlter instance) {
		log.debug("attaching clean MarketstaffAlter instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public MarketstaffAlter findById(Integer alterId) {
		log.debug("getting MarketstaffAlter instance with id: " + alterId);
		try {
			MarketstaffAlter instance = (MarketstaffAlter) getHibernateTemplate().get(
					"nju.software.dataobject.MarketstaffAlter", alterId);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<MarketstaffAlter> findByExample(MarketstaffAlter example) {
		log.debug("finding MarketstaffAlter instance by example");
		try {
			List<MarketstaffAlter> results = (List<MarketstaffAlter>) getHibernateTemplate()
					.findByExample(example);
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
		log.debug("finding MarketstaffAlter instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from MarketstaffAlter as model where model."
					+ propertyName + "= ? order by model.applyTime desc";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	@Override
	public List<MarketstaffAlter> findVerifyState(String verfiyState) {
		return findByProperty(VERIFY_STATE, verfiyState);
	}

	@Override
	public List<MarketstaffAlter> findByApplyerId(Integer applyId) {
		return findByProperty(EMPLOYEE_ID, applyId);
	}

}
