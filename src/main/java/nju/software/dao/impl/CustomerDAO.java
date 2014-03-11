package nju.software.dao.impl;

import java.sql.Timestamp;
import java.util.*;

import nju.software.dao.ICustomerDAO;
import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Customer entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see nju.software.dataobject.Customer
 * @author MyEclipse Persistence Tools
 */
public class CustomerDAO extends HibernateDaoSupport implements ICustomerDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CustomerDAO.class);
	// property constants
	public static final String COMPANY_ID = "companyId";
	public static final String COMPANY_NAME = "companyName";
	public static final String CUSTOMER_NAME = "customerName";
	public static final String PROVINCE = "province";
	public static final String CITY = "city";
	public static final String WEBSITE_URL = "websiteUrl";
	public static final String WEBSITE_TYPE = "websiteType";
	public static final String COMPANY_ADDRESS = "companyAddress";
	public static final String COMPANY_FAX = "companyFax";
	public static final String COMPANY_PHONE = "companyPhone";
	public static final String BUY_CONTACT = "buyContact";
	public static final String CONTACT_PHONE1 = "contactPhone1";
	public static final String CONTACT_PHONE2 = "contactPhone2";
	public static final String QQ = "qq";
	public static final String EMAIL = "email";
	public static final String CUSTOMER_PHONE = "customerPhone";
	public static final String BOSS_NAME = "bossName";
	public static final String BOSS_PHONE = "bossPhone";
	public static final String REGISTER_EMPLOYEE_ID = "registerEmployeeId";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#save(nju.software.dataobject.Customer)
	 */
	public void save(Customer transientInstance) {
		log.debug("saving Customer instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#delete(nju.software.dataobject.Customer)
	 */
	public void delete(Customer persistentInstance) {
		log.debug("deleting Customer instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findById(java.lang.Integer)
	 */
	public Customer findById(java.lang.Integer id) {
		log.debug("getting Customer instance with id: " + id);
		try {
			Customer instance = (Customer) getHibernateTemplate().get(
					"nju.software.dataobject.Customer", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByExample(nju.software.dataobject.Customer)
	 */
	public List<Customer> findByExample(Customer instance) {
		log.debug("finding Customer instance by example");
		try {
			List<Customer> results = (List<Customer>) getHibernateTemplate()
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
	 * @see nju.software.dao.impl.ICustomerDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Customer instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Customer as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByCompanyId(java.lang.Object)
	 */
	public List<Customer> findByCompanyId(Object companyId) {
		return findByProperty(COMPANY_ID, companyId);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByCompanyName(java.lang.Object)
	 */
	public List<Customer> findByCompanyName(Object companyName) {
		return findByProperty(COMPANY_NAME, companyName);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByCustomerName(java.lang.Object)
	 */
	public List<Customer> findByCustomerName(Object customerName) {
		return findByProperty(CUSTOMER_NAME, customerName);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByProvince(java.lang.Object)
	 */
	public List<Customer> findByProvince(Object province) {
		return findByProperty(PROVINCE, province);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByCity(java.lang.Object)
	 */
	public List<Customer> findByCity(Object city) {
		return findByProperty(CITY, city);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByWebsiteUrl(java.lang.Object)
	 */
	public List<Customer> findByWebsiteUrl(Object websiteUrl) {
		return findByProperty(WEBSITE_URL, websiteUrl);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByWebsiteType(java.lang.Object)
	 */
	public List<Customer> findByWebsiteType(Object websiteType) {
		return findByProperty(WEBSITE_TYPE, websiteType);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByCompanyAddress(java.lang.Object)
	 */
	public List<Customer> findByCompanyAddress(Object companyAddress) {
		return findByProperty(COMPANY_ADDRESS, companyAddress);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByCompanyFax(java.lang.Object)
	 */
	public List<Customer> findByCompanyFax(Object companyFax) {
		return findByProperty(COMPANY_FAX, companyFax);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByCompanyPhone(java.lang.Object)
	 */
	public List<Customer> findByCompanyPhone(Object companyPhone) {
		return findByProperty(COMPANY_PHONE, companyPhone);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByBuyContact(java.lang.Object)
	 */
	public List<Customer> findByBuyContact(Object buyContact) {
		return findByProperty(BUY_CONTACT, buyContact);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByContactPhone1(java.lang.Object)
	 */
	public List<Customer> findByContactPhone1(Object contactPhone1) {
		return findByProperty(CONTACT_PHONE1, contactPhone1);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByContactPhone2(java.lang.Object)
	 */
	public List<Customer> findByContactPhone2(Object contactPhone2) {
		return findByProperty(CONTACT_PHONE2, contactPhone2);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByQq(java.lang.Object)
	 */
	public List<Customer> findByQq(Object qq) {
		return findByProperty(QQ, qq);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByEmail(java.lang.Object)
	 */
	public List<Customer> findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByCustomerPhone(java.lang.Object)
	 */
	public List<Customer> findByCustomerPhone(Object customerPhone) {
		return findByProperty(CUSTOMER_PHONE, customerPhone);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByBossName(java.lang.Object)
	 */
	public List<Customer> findByBossName(Object bossName) {
		return findByProperty(BOSS_NAME, bossName);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByBossPhone(java.lang.Object)
	 */
	public List<Customer> findByBossPhone(Object bossPhone) {
		return findByProperty(BOSS_PHONE, bossPhone);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findByRegisterEmployeeId(java.lang.Object)
	 */
	public List<Customer> findByRegisterEmployeeId(Object registerEmployeeId) {
		return findByProperty(REGISTER_EMPLOYEE_ID, registerEmployeeId);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Customer instances");
		try {
			String queryString = "from Customer";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#merge(nju.software.dataobject.Customer)
	 */
	public Customer merge(Customer detachedInstance) {
		log.debug("merging Customer instance");
		try {
			Customer result = (Customer) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#attachDirty(nju.software.dataobject.Customer)
	 */
	public void attachDirty(Customer instance) {
		log.debug("attaching dirty Customer instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.ICustomerDAO#attachClean(nju.software.dataobject.Customer)
	 */
	public void attachClean(Customer instance) {
		log.debug("attaching clean Customer instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ICustomerDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ICustomerDAO) ctx.getBean("CustomerDAO");
	}
	/*
	@Override
	public int countByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		log.debug("count Account instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Account as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value).size();
		} catch (RuntimeException re) {
			log.error("count by property name failed", re);
			throw re;
		}
	}
	*/

	/**
	 * Calculate paging params
	 * 
	 * @param params
	 *			page: the requested page limit: how many rows we want to show
	 * 			sidx: index row sord: the direction 
	 *          ======above from page============ 
	 *          count: total records
	 * @return 
	 * 			page: the requested page 
	 * 			limit: how many rows we want to show
	 *          sidx: index row 
	 *          sord: the direction 
	 *          count: total records
	 *          total_pages 
	 *          start: tell the hql the position to begin
	 */
	public Map<String, Object> calculateLimits(
			Map<String, Object> params) {
		int page = (Integer) params.get("page");
		int number_per_page = (Integer) params.get("number_per_page");
		int count = (Integer) params.get("count");
		int total_pages = 0;
		int start = 0;

		if (count > 0)
			total_pages = (int) Math.ceil((double) count / number_per_page);
		if (page > total_pages)
			page = total_pages;

		start = number_per_page * (page - 1);

		params.put("total_pages", total_pages);
		params.put("start", start);
		return params;
	}
	public List<Object> findByPropertyCustomerPage(
			String propertyName,String propertyValue,Map<String, Object> params) {
		// TODO Auto-generated method stub
		//int count = countByProperty(propertyName, value);
		int count=countByProperty(propertyName,propertyValue);
		params.put("page_number", count);

		
		int limit = (Integer) params.get("limit");
		int start = (Integer) params.get("start");
		String sidx = (String) params.get("sidx");
		String sord = (String) params.get("sord");

		List<Object> result = new ArrayList<Object>();
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
			//criteria.add(Restrictions.eq(propertyName,value));
			if (sord != null)
				if (sord.equals("asc"))
					criteria.addOrder(Order.asc(sidx));
				else
					criteria.addOrder(Order.desc(sidx));
			
			List<Customer> studentList = getHibernateTemplate().findByCriteria(criteria,start, limit);
			result.add(studentList);
			result.add(params);
		} catch (RuntimeException re) {
			log.error("find by property name limit failed", re);
			throw re;
		}
		return result;
		
	}

	public int countByProperty(String propertyName,String propertyValue) {
		// TODO Auto-generated method stub
		log.debug("finding all Customer instances by property");
		try {
			String queryString = "from Customer as model where model."+propertyName+"=?";
			return getHibernateTemplate().find(queryString,propertyValue).size();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public int countAll(){
		log.debug("finding all Customer instances by property");
		try {
			String queryString = "from Customer";
			return getHibernateTemplate().find(queryString).size();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
		
		
	}
	@Override
	public List<Object> listCustomer(Map<String, Object> params) {
		
		// TODO Auto-generated method stub
				//int count = countByProperty(propertyName, value);
				int count=countAll();
				

				int page = (Integer) params.get("page");
				int number_per_page = (Integer) params.get("number_per_page");
				
				int total_pages = 0;
				int start = 0;

				if (count > 0)
					total_pages = (int) Math.ceil((double) count / number_per_page);
				if (page > total_pages)
					page = total_pages;

				params.put("page_number", total_pages);
				start = number_per_page * (page - 1);
				List<Object> result = new ArrayList<Object>();
				try {
					DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
					//criteria.add(Restrictions.eq(propertyName,value));
				
					criteria.addOrder(Order.asc("customerName"));
					List<Customer> studentList = getHibernateTemplate().findByCriteria(criteria,start, number_per_page);
					result.add(studentList);
					result.add(params);
				} catch (RuntimeException re) {
					log.error("find by property name limit failed", re);
					throw re;
				}
				return result;
		// TODO Auto-generated method stub
		
	}

	

	
}