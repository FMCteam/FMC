package nju.software.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;


















import nju.software.dao.IOrderDAO;
import nju.software.dataobject.Customer;
import nju.software.dataobject.Order;
import nju.software.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Order
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see nju.software.dataobject.Order
 * @author MyEclipse Persistence Tools
 */
public class OrderDAO extends HibernateDaoSupport implements IOrderDAO {
	private static final Logger log = LoggerFactory.getLogger(OrderDAO.class);
	// property constants
	public static final String CUSTOMER_ID = "customerId";
	public static final String EMPLOYEE_ID = "employeeId";
	public static final String ORDER_STATE = "orderState";
	public static final String CUSTOMER_NAME = "customerName";
	public static final String CUSTOMER_COMPANY = "customerCompany";
	public static final String CUSTOMER_COMPANY_FAX = "customerCompanyFax";
	public static final String CUSTOMER_PHONE1 = "customerPhone1";
	public static final String CUSTOMER_PHONE2 = "customerPhone2";
	public static final String CUSTOMER_COMPANY_ADDRESS = "customerCompanyAddress";
	public static final String STYLE_NAME = "styleName";
	public static final String FABRIC_TYPE = "fabricType";
	public static final String STYLE_SEX = "styleSex";
	public static final String STYLE_SEASON = "styleSeason";
	public static final String SPECIAL_PROCESS = "specialProcess";
	public static final String OTHER_REQUIREMENTS = "otherRequirements";
	public static final String SAMPLE_CLOTHES_PICTURE = "sampleClothesPicture";
	public static final String REFERENCE_PICTURE = "referencePicture";
	public static final String ASK_AMOUNT = "askAmount";
	public static final String ASK_PRODUCE_PERIOD = "askProducePeriod";
	public static final String ASK_CODE_NUMBER = "askCodeNumber";
	public static final String HAS_POSTED_SAMPLE_CLOTHES = "hasPostedSampleClothes";
	public static final String IS_NEED_SAMPLE_CLOTHES = "isNeedSampleClothes";
	public static final String ORDER_SOURCE = "orderSource";

	protected void initDao() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#save(nju.software.dataobject.Order)
	 */
	public void save(Order transientInstance) {
		log.debug("saving Order instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#delete(nju.software.dataobject.Order)
	 */
	public void delete(Order persistentInstance) {
		log.debug("deleting Order instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findById(java.lang.Integer)
	 */
	public Order findById(java.lang.Integer id) {
		log.debug("getting Order instance with id: " + id);
		try {
			Order instance = (Order) getHibernateTemplate().get(
					"nju.software.dataobject.Order", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findByExample(nju.software.dataobject
	 * .Order)
	 */
	public List<Order> findByExample(Order instance) {
		log.debug("finding Order instance by example");
		try {
			List<Order> results = (List<Order>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Order instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Order as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findByCustomerId(java.lang.Object)
	 */
	public List<Order> findByCustomerId(Object customerId) {
		return findByProperty(CUSTOMER_ID, customerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findByEmployeeId(java.lang.Object)
	 */
	public List<Order> findByEmployeeId(Object employeeId) {
		return findByProperty(EMPLOYEE_ID, employeeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findByOrderState(java.lang.Object)
	 */
	public List<Order> findByOrderState(Object orderState) {
		return findByProperty(ORDER_STATE, orderState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findByCustomerName(java.lang.Object)
	 */
	public List<Order> findByCustomerName(Object customerName) {
		return findByProperty(CUSTOMER_NAME, customerName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findByCustomerCompany(java.lang.Object)
	 */
	public List<Order> findByCustomerCompany(Object customerCompany) {
		return findByProperty(CUSTOMER_COMPANY, customerCompany);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findByCustomerCompanyFax(java.lang.Object
	 * )
	 */
	public List<Order> findByCustomerCompanyFax(Object customerCompanyFax) {
		return findByProperty(CUSTOMER_COMPANY_FAX, customerCompanyFax);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findByCustomerPhone1(java.lang.Object)
	 */
	public List<Order> findByCustomerPhone1(Object customerPhone1) {
		return findByProperty(CUSTOMER_PHONE1, customerPhone1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findByCustomerPhone2(java.lang.Object)
	 */
	public List<Order> findByCustomerPhone2(Object customerPhone2) {
		return findByProperty(CUSTOMER_PHONE2, customerPhone2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findByCustomerCompanyAddress(java.lang
	 * .Object)
	 */
	public List<Order> findByCustomerCompanyAddress(
			Object customerCompanyAddress) {
		return findByProperty(CUSTOMER_COMPANY_ADDRESS, customerCompanyAddress);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findByStyleName(java.lang.Object)
	 */
	public List<Order> findByStyleName(Object styleName) {
		return findByProperty(STYLE_NAME, styleName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findByFabricType(java.lang.Object)
	 */
	public List<Order> findByFabricType(Object fabricType) {
		return findByProperty(FABRIC_TYPE, fabricType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findByStyleSex(java.lang.Object)
	 */
	public List<Order> findByStyleSex(Object styleSex) {
		return findByProperty(STYLE_SEX, styleSex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findByStyleSeason(java.lang.Object)
	 */
	public List<Order> findByStyleSeason(Object styleSeason) {
		return findByProperty(STYLE_SEASON, styleSeason);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findBySpecialProcess(java.lang.Object)
	 */
	public List<Order> findBySpecialProcess(Object specialProcess) {
		return findByProperty(SPECIAL_PROCESS, specialProcess);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findByOtherRequirements(java.lang.Object)
	 */
	public List<Order> findByOtherRequirements(Object otherRequirements) {
		return findByProperty(OTHER_REQUIREMENTS, otherRequirements);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findBySampleClothesPicture(java.lang.
	 * Object)
	 */
	public List<Order> findBySampleClothesPicture(Object sampleClothesPicture) {
		return findByProperty(SAMPLE_CLOTHES_PICTURE, sampleClothesPicture);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findByReferencePicture(java.lang.Object)
	 */
	public List<Order> findByReferencePicture(Object referencePicture) {
		return findByProperty(REFERENCE_PICTURE, referencePicture);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findByAskAmount(java.lang.Object)
	 */
	public List<Order> findByAskAmount(Object askAmount) {
		return findByProperty(ASK_AMOUNT, askAmount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findByAskProducePeriod(java.lang.Object)
	 */
	public List<Order> findByAskProducePeriod(Object askProducePeriod) {
		return findByProperty(ASK_PRODUCE_PERIOD, askProducePeriod);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findByAskCodeNumber(java.lang.Object)
	 */
	public List<Order> findByAskCodeNumber(Object askCodeNumber) {
		return findByProperty(ASK_CODE_NUMBER, askCodeNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findByHasPostedSampleClothes(java.lang
	 * .Object)
	 */
	public List<Order> findByHasPostedSampleClothes(
			Object hasPostedSampleClothes) {
		return findByProperty(HAS_POSTED_SAMPLE_CLOTHES, hasPostedSampleClothes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#findByIsNeedSampleClothes(java.lang.Object
	 * )
	 */
	public List<Order> findByIsNeedSampleClothes(Object isNeedSampleClothes) {
		return findByProperty(IS_NEED_SAMPLE_CLOTHES, isNeedSampleClothes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findByOrderSource(java.lang.Object)
	 */
	public List<Order> findByOrderSource(Object orderSource) {
		return findByProperty(ORDER_SOURCE, orderSource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Order instances");
		try {
			String queryString = "from Order";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IOrderDAO#merge(nju.software.dataobject.Order)
	 */
	public Order merge(Order detachedInstance) {
		log.debug("merging Order instance");
		try {
			Order result = (Order) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#attachDirty(nju.software.dataobject.Order
	 * )
	 */
	public void attachDirty(Order instance) {
		log.debug("attaching dirty Order instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IOrderDAO#attachClean(nju.software.dataobject.Order
	 * )
	 */
	public void attachClean(Order instance) {
		log.debug("attaching clean Order instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IOrderDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IOrderDAO) ctx.getBean("OrderDAO");
	}
	public List<Order> findResultsByCustomerId(int customerId) {
		try {
			String query = "from Order as model where model.customerId ="+customerId;
			
			List<Order> c= getHibernateTemplate().find(query);
			

			return c;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	public List<Order> findSampleOrderAndPage(final int off_set,
			final int length) {
		// TODO Auto-generated method stub
		log.debug("find the order that need sample and that need confirm, while pageing");
		try {

			List<Order> orderList=getHibernateTemplate().executeFind(
					new HibernateCallback<List<Order>>() {
						@Override
						public List<Order> doInHibernate(Session arg0)
								throws HibernateException, SQLException {
							// TODO Auto-generated method stub
							Query query =  arg0
									.createQuery("from Order as model where model.isNeedSampleClothes=1 and model.hasPostedSampleClothes=0");
							// query.setFirstResult(off_set);
						//	 query.setMaxResults(length);
							List<Order> list =  query
									.list();
							System.out.println(list.size());
							return list;
						}

					});
			return orderList;
			
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	
	
	
	public int coutSampleOrder() {
		// TODO Auto-generated method stub
		log.debug("find the order that need sample and that need confirm, while pageing the total count");
		try {
			String query = "from Order as model where model.isNeedSampleClothes=1 and model.hasPostedSampleClothes=0";
			
			List<Order> c= getHibernateTemplate().find(query);
			

			return c.size();
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}

	}

	@Override
	public List getOrderList(Integer page) {
		// TODO Auto-generated method stub
		//Integer number_per_page=2;
		DetachedCriteria criteria = DetachedCriteria.forClass(Order.class);
		//criteria.add(Restrictions.eq(propertyName,value));
		int start=number_per_page*(page-1);
		criteria.addOrder(org.hibernate.criterion.Order.asc("orderId"));
		List<Order> orderList = getHibernateTemplate().findByCriteria(criteria,start, number_per_page);
//		List<Order> orderList1 = getHibernateTemplate().findByCriteria(criteria);

		return orderList;
	}

	public List<Order> getOrders() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Order.class);
		List<Order> orderList = getHibernateTemplate().findByCriteria(criteria);

		return orderList;
	}
	
	public List<Order> getOrdersDoing() {
		List<Order> orderList = findByOrderState("A");
		return orderList;
	}

	public List<Order> getOrdersDone() {
		// TODO Auto-generated method stub
//		try {
//			String query = "from Order as model where model.orderState = "+"Done";
//			List<Order> c= getHibernateTemplate().find(query);
//			return c;
//		} catch (RuntimeException re) {
//			log.error("attach failed", re);
//			throw re;
//		}
		List<Order> orderList = findByOrderState("Done");
		return orderList;
 	}
	
	@Override
	public Integer getPageNumber() {
		// TODO Auto-generated method stub
		String queryString = "select count(*) from Order";
		long count = (Long) getHibernateTemplate().find(queryString).get(0);
		//Integer number_per_page=10;
		Integer pages=(int) Math.ceil((double)count/number_per_page);
		//System.out.println(count+" "+pages);
		return pages;
	}
	
	private Integer number_per_page=10;
	
	@Override
	public List<Order> getSearchOrderList(String ordernumber,
			String customername, String stylename, String startdate,String enddate,
			Integer[] employeeIds) {
      
		Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Order.class);
  		if(!StringUtils.isEmpty(ordernumber))
 			criteria.add(Restrictions.eq("orderId",Integer.parseInt(ordernumber) ));
 	 	if(!StringUtils.isEmpty(customername))
			criteria.add(Restrictions.like("customerName", "%" + customername + "%"));
 		if(!StringUtils.isEmpty(stylename))
			criteria.add(Restrictions.like("styleName", "%" + stylename + "%"));
 		if (!StringUtils.isEmpty(startdate)&&!StringUtils.isEmpty(enddate))
		{
			String strformat = "yyyy-MM-dd HH:mm:ss";
			String strformat2 = "yyyy-MM-dd";
	        DateUtil du = new DateUtil();
	        Date begindate1 = null;
	        Date enddate1 = null;
			if(startdate.length()==10&&enddate.length()==10){
				startdate = startdate+" 00:00:00";
				enddate = enddate+" 23:59:59";
	        begindate1 = du.parse(startdate, strformat);
	        enddate1 = du.parse(enddate, strformat);
	        System.out.println("bengin date is:"+begindate1+"end date is :"+enddate1);
			}
			criteria.add(Restrictions.between("orderTime",begindate1,enddate1));
		}
		if(employeeIds!=null&&employeeIds.length !=0)
		{
			criteria.add(Restrictions.in("employeeId",employeeIds));
		}
	    List<Order> orderList = criteria.list();		
		return orderList;
	}

	public List<Order> getSearchOrderDoingList(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds) {
	      
			Session session = getHibernateTemplate().getSessionFactory().openSession();
	        Criteria criteria = session.createCriteria(Order.class);
	        criteria.add(Restrictions.eq("orderState", "A"));
//	 		if (ordernumber != null)
	 		if(!StringUtils.isEmpty(ordernumber))
	 			criteria.add(Restrictions.eq("orderId",Integer.parseInt(ordernumber) ));
//			if (customername != null)
		 	if(!StringUtils.isEmpty(customername))
				criteria.add(Restrictions.like("customerName", "%" + customername + "%"));
//		    if (stylename != null)
			if(!StringUtils.isEmpty(stylename))

				criteria.add(Restrictions.like("styleName", "%" + stylename + "%"));
//			if (startdate != null&& enddate!=null)
			if (!StringUtils.isEmpty(startdate)&&!StringUtils.isEmpty(enddate))
			{
				String strformat = "yyyy-MM-dd HH:mm:ss";
				String strformat2 = "yyyy-MM-dd";
		        DateUtil du = new DateUtil();
		        Date begindate1 = null;
		        Date enddate1 = null;
				if(startdate.length()==10&&enddate.length()==10){
					startdate = startdate+" 00:00:00";
					enddate = enddate+" 23:59:59";
		        begindate1 = du.parse(startdate, strformat);
		        enddate1 = du.parse(enddate, strformat);
		        System.out.println("bengin date is:"+begindate1+"end date is :"+enddate1);
				}
				criteria.add(Restrictions.between("orderTime",begindate1,enddate1));
			}
			if(employeeIds!=null&&employeeIds.length !=0)
			{
				criteria.add(Restrictions.in("employeeId",employeeIds));
			}
		    List<Order> orderList = criteria.list();		
			return orderList;
		}

	public List<Order> getSearchOrderDoneList(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds) {
	      
			Session session = getHibernateTemplate().getSessionFactory().openSession();
	        Criteria criteria = session.createCriteria(Order.class);
	        criteria.add(Restrictions.eq("orderState", "Done"));
//	 		if (ordernumber != null)
	 		if(!StringUtils.isEmpty(ordernumber))
	 			criteria.add(Restrictions.eq("orderId",Integer.parseInt(ordernumber) ));
//			if (customername != null)
		 	if(!StringUtils.isEmpty(customername))
				criteria.add(Restrictions.like("customerName", "%" + customername + "%"));
//		    if (stylename != null)
			if(!StringUtils.isEmpty(stylename))

				criteria.add(Restrictions.like("styleName", "%" + stylename + "%"));
//			if (startdate != null&& enddate!=null)
			if (!StringUtils.isEmpty(startdate)&&!StringUtils.isEmpty(enddate))
			{
				String strformat = "yyyy-MM-dd HH:mm:ss";
				String strformat2 = "yyyy-MM-dd";
		        DateUtil du = new DateUtil();
		        Date begindate1 = null;
		        Date enddate1 = null;
				if(startdate.length()==10&&enddate.length()==10){
					startdate = startdate+" 00:00:00";
					enddate = enddate+" 23:59:59";
		        begindate1 = du.parse(startdate, strformat);
		        enddate1 = du.parse(enddate, strformat);
		        System.out.println("bengin date is:"+begindate1+"end date is :"+enddate1);
				}
				criteria.add(Restrictions.between("orderTime",begindate1,enddate1));
			}
			if(employeeIds!=null&&employeeIds.length !=0)
			{
				criteria.add(Restrictions.in("employeeId",employeeIds));
			}
		    List<Order> orderList = criteria.list();		
			return orderList;
		}

	public List<Order> findOrdersDoingByCustomer(Order orderExample) {
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Order.class);
        criteria.add(Restrictions.eq("orderState", "A"));
        criteria.add(Restrictions.eq("customerId", orderExample.getCustomerId()));
	    List<Order> customerOrdersDoingList = criteria.list();		

		return customerOrdersDoingList;
	}

	public List<Order> findOrdersDoingByEmployee(Order orderExample) {

		Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Order.class);
        criteria.add(Restrictions.eq("orderState", "A"));
        criteria.add(Restrictions.eq("employeeId", orderExample.getEmployeeId()));
	    List<Order> employeeOrdersDoingList = criteria.list();		

		return employeeOrdersDoingList;
		
	}

	public List<Order> findOrdersDoneByCustomer(Order orderExample) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Order.class);
        criteria.add(Restrictions.eq("orderState", "Done"));
        criteria.add(Restrictions.eq("customerId", orderExample.getCustomerId()));
	    List<Order> customerOrdersDoneList = criteria.list();		

		return customerOrdersDoneList;
	}

	public List<Order> findOrdersDoneByEmployee(Order orderExample) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Order.class);
        criteria.add(Restrictions.eq("orderState", "Done"));
        criteria.add(Restrictions.eq("employeeId", orderExample.getEmployeeId()));
	    List<Order> employeeOrdersDoneList = criteria.list();		

		return employeeOrdersDoneList;
	}





}