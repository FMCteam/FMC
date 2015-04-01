package nju.software.dao.impl;

import java.util.ArrayList;
import java.util.List;

import nju.software.dao.IPackageDetailDAO;
import nju.software.dataobject.PackageDetail;
import nju.software.dataobject.Package;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * PackageDetail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see nju.software.dataobject.PackageDetail
 * @author MyEclipse Persistence Tools
 */
public class PackageDetailDAO extends HibernateDaoSupport implements IPackageDetailDAO {
	private static final Logger log = LoggerFactory
			.getLogger(PackageDetailDAO.class);
	// property constants
	public static final String PACKAGE_ID = "packageId";
	public static final String CLOTHES_STYLE_NAME = "clothesStyleName";
	public static final String CLOTHES_STYLE_COLOR = "clothesStyleColor";
	public static final String CLOTHES_AMOUNT = "clothesAmount";

	@Override
	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDetailDAO#save(nju.software.dataobject.PackageDetail)
	 */
	@Override
	public void save(PackageDetail transientInstance) {
		log.debug("saving PackageDetail instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDetailDAO#delete(nju.software.dataobject.PackageDetail)
	 */
	@Override
	public void delete(PackageDetail persistentInstance) {
		log.debug("deleting PackageDetail instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDetailDAO#findById(java.lang.Integer)
	 */
	@Override
	public PackageDetail findById(java.lang.Integer id) {
		log.debug("getting PackageDetail instance with id: " + id);
		try {
			PackageDetail instance = (PackageDetail) getHibernateTemplate()
					.get("nju.software.dataobject.PackageDetail", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDetailDAO#findByExample(nju.software.dataobject.PackageDetail)
	 */
	@Override
	public List<PackageDetail> findByExample(PackageDetail instance) {
		log.debug("finding PackageDetail instance by example");
		try {
			List<PackageDetail> results = getHibernateTemplate()
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
	 * @see nju.software.dao.impl.IPackageDetailDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding PackageDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PackageDetail as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDetailDAO#findByPackageId(java.lang.Object)
	 */
	@Override
	public List<PackageDetail> findByPackageId(Integer packageId) {
		return findByProperty(PACKAGE_ID, packageId);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDetailDAO#findByClothesStyleName(java.lang.Object)
	 */
	@Override
	public List<PackageDetail> findByClothesStyleName(Object clothesStyleName) {
		return findByProperty(CLOTHES_STYLE_NAME, clothesStyleName);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDetailDAO#findByClothesStyleColor(java.lang.Object)
	 */
	@Override
	public List<PackageDetail> findByClothesStyleColor(Object clothesStyleColor) {
		return findByProperty(CLOTHES_STYLE_COLOR, clothesStyleColor);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDetailDAO#findByClothesAmount(java.lang.Object)
	 */
	@Override
	public List<PackageDetail> findByClothesAmount(Object clothesAmount) {
		return findByProperty(CLOTHES_AMOUNT, clothesAmount);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDetailDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all PackageDetail instances");
		try {
			String queryString = "from PackageDetail";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDetailDAO#merge(nju.software.dataobject.PackageDetail)
	 */
	@Override
	public PackageDetail merge(PackageDetail detachedInstance) {
		log.debug("merging PackageDetail instance");
		try {
			PackageDetail result = getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDetailDAO#attachDirty(nju.software.dataobject.PackageDetail)
	 */
	@Override
	public void attachDirty(PackageDetail instance) {
		log.debug("attaching dirty PackageDetail instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IPackageDetailDAO#attachClean(nju.software.dataobject.PackageDetail)
	 */
	@Override
	public void attachClean(PackageDetail instance) {
		log.debug("attaching clean PackageDetail instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IPackageDetailDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IPackageDetailDAO) ctx.getBean("PackageDetailDAO");
	}

	@Override
	public List<List<PackageDetail>> findByPackageList(List<Package> packageList) {
		// TODO Auto-generated method stub
		List<List<PackageDetail>> dList = new ArrayList<List<PackageDetail>>();
		for(int i=0;i<packageList.size();i++) {
			Package pk = packageList.get(i);
			List<PackageDetail> pList = this.findByPackageId(pk.getPackageId());
			dList.add(pList);
		}
		return dList;
	}
}