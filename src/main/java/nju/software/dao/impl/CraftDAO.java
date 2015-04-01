package nju.software.dao.impl;


import java.util.List;
import nju.software.dao.ICraftDAO;
import nju.software.dataobject.Craft;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Craft
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see nju.software.dataobject.Craft
 * @author MyEclipse Persistence Tools
 */
public class CraftDAO extends HibernateDaoSupport implements ICraftDAO{
	private static final Logger log = LoggerFactory.getLogger(CraftDAO.class);
	// property constants
	public static final String ORDER_ID = "orderId";
	public static final String NEED_CRAFT = "needCraft";
	public static final String STAMP_DUTY_MONEY = "stampDutyMoney";
	public static final String WASH_HANG_DYE_MONEY = "washHangDyeMoney";
	public static final String LASER_MONEY = "laserMoney";
	public static final String EMBROIDERY_MONEY = "embroideryMoney";
	public static final String CRUMPLE_MONEY = "crumpleMoney";
	public static final String OPEN_VERSION_MONEY = "openVersionMoney";
    public static final String CRAFT_FILE_URL = "craftFileUrl";
	@Override
	public void save(Craft transientInstance) {
		log.debug("saving Craft instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(Craft persistentInstance) {
		log.debug("deleting Craft instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public Craft findById(java.lang.Integer id) {
		log.debug("getting Craft instance with id: " + id);
		try {
 
			Craft instance = (Craft) getHibernateTemplate().get(
					"nju.software.dataobject.Craft", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List<Craft> findByExample(Craft instance) {
		log.debug("finding Craft instance by example");
		try {
			List<Craft> results = getHibernateTemplate()
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
		log.debug("finding Craft instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Craft as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	@Override
	public List<Craft> findByOrderId(Object orderId) {
		return findByProperty(ORDER_ID, orderId);
	}

	@Override
	public List<Craft> findByNeedCraft(Object needCraft) {
		return findByProperty(NEED_CRAFT, needCraft);
	}

	public List<Craft> findByStampDutyMoney(Object stampDutyMoney) {
		return findByProperty(STAMP_DUTY_MONEY, stampDutyMoney);
	}

	public List<Craft> findByWashHangDyeMoney(Object washHangDyeMoney) {
		return findByProperty(WASH_HANG_DYE_MONEY, washHangDyeMoney);
	}

	public List<Craft> findByLaserMoney(Object laserMoney) {
		return findByProperty(LASER_MONEY, laserMoney);
	}

	public List<Craft> findByEmbroideryMoney(Object embroideryMoney) {
		return findByProperty(EMBROIDERY_MONEY, embroideryMoney);
	}

	public List<Craft> findByCrumpleMoney(Object crumpleMoney) {
		return findByProperty(CRUMPLE_MONEY, crumpleMoney);
	}

	public List<Craft> findByOpenVersionMoney(Object openVersionMoney) {
		return findByProperty(OPEN_VERSION_MONEY, openVersionMoney);
	}

	@Override
	public List findAll() {
		log.debug("finding all Craft instances");
		try {
			String queryString = "from Craft";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public Craft merge(Craft detachedInstance) {
		log.debug("merging Craft instance");
		try {
			Craft result = getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(Craft instance) {
		log.debug("attaching dirty Craft instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(Craft instance) {
		log.debug("attaching clean Craft instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public static ICraftDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ICraftDAO) ctx.getBean("CraftDAO");
	}
}