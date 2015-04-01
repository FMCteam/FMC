package nju.software.dao.impl;

import java.util.List;

import nju.software.dao.IMoneyDAO;
import nju.software.dataobject.Money;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Money
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see nju.software.dataobject.Money
 * @author MyEclipse Persistence Tools
 */
public class MoneyDAO extends HibernateDaoSupport implements IMoneyDAO {
	private static final Logger log = LoggerFactory.getLogger(MoneyDAO.class);
	// property constants
	public static final String ORDER_ID = "orderId";
	public static final String MONEY_TYPE = "moneyType";
	public static final String MONEY_AMOUNT = "moneyAmount";
	public static final String MONEY_STATE = "moneyState";
	public static final String MONEY_NAME = "moneyName";
	public static final String MONEY_BANK = "moneyBank";
	public static final String MONEY_NUMBER = "moneyNumber";
	public static final String MONEY_REMARK = "moneyRemark";

	@Override
	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#save(nju.software.dataobject.Money)
	 */
	@Override
	public void save(Money transientInstance) {
		log.debug("saving Money instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#delete(nju.software.dataobject.Money)
	 */
	@Override
	public void delete(Money persistentInstance) {
		log.debug("deleting Money instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#findById(java.lang.Integer)
	 */
	@Override
	public Money findById(java.lang.Integer id) {
		log.debug("getting Money instance with id: " + id);
		try {
			Money instance = (Money) getHibernateTemplate().get(
					"nju.software.dataobject.Money", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#findByExample(nju.software.dataobject.Money)
	 */
	@Override
	public List<Money> findByExample(Money instance) {
		log.debug("finding Money instance by example");
		try {
			List<Money> results = getHibernateTemplate()
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
	 * @see nju.software.dao.impl.IMoneyDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Money instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Money as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#findByOrderId(java.lang.Object)
	 */
	@Override
	public List<Money> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#findByMoneyType(java.lang.Object)
	 */
	@Override
	public List<Money> findByMoneyType(Object moneyType) {
		return findByProperty(MONEY_TYPE, moneyType);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#findByMoneyAmount(java.lang.Object)
	 */
	@Override
	public List<Money> findByMoneyAmount(Object moneyAmount) {
		return findByProperty(MONEY_AMOUNT, moneyAmount);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#findByMoneyState(java.lang.Object)
	 */
	@Override
	public List<Money> findByMoneyState(Object moneyState) {
		return findByProperty(MONEY_STATE, moneyState);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#findByMoneyName(java.lang.Object)
	 */
	@Override
	public List<Money> findByMoneyName(Object moneyName) {
		return findByProperty(MONEY_NAME, moneyName);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#findByMoneyBank(java.lang.Object)
	 */
	@Override
	public List<Money> findByMoneyBank(Object moneyBank) {
		return findByProperty(MONEY_BANK, moneyBank);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#findByMoneyNumber(java.lang.Object)
	 */
	@Override
	public List<Money> findByMoneyNumber(Object moneyNumber) {
		return findByProperty(MONEY_NUMBER, moneyNumber);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#findByMoneyRemark(java.lang.Object)
	 */
	@Override
	public List<Money> findByMoneyRemark(Object moneyRemark) {
		return findByProperty(MONEY_REMARK, moneyRemark);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Money instances");
		try {
			String queryString = "from Money";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#merge(nju.software.dataobject.Money)
	 */
	@Override
	public Money merge(Money detachedInstance) {
		log.debug("merging Money instance");
		try {
			Money result = getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#attachDirty(nju.software.dataobject.Money)
	 */
	@Override
	public void attachDirty(Money instance) {
		log.debug("attaching dirty Money instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IMoneyDAO#attachClean(nju.software.dataobject.Money)
	 */
	@Override
	public void attachClean(Money instance) {
		log.debug("attaching clean Money instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IMoneyDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IMoneyDAO) ctx.getBean("MoneyDAO");
	}
}