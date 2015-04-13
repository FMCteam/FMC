package nju.software.dao.impl;

import java.util.ArrayList;
import java.util.List;

import nju.software.dao.IEmployeeDAO;
import nju.software.dataobject.Employee;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Employee entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see nju.software.dataobject.Employee
 * @author MyEclipse Persistence Tools
 */
public class EmployeeDAO extends HibernateDaoSupport implements IEmployeeDAO {
	private static final Logger log = LoggerFactory
			.getLogger(EmployeeDAO.class);
	// property constants
	public static final String EMPLOYEE_NAME = "employeeName";
	public static final String SEX = "sex";
	public static final String AGE = "age";
	public static final String DEPARTMENT = "department";
	public static final String DIRECT_LEADER = "directLeader";
	public static final String EMPLOYEE_LEVEL = "employeeLevel";
	public static final String PHONE1 = "phone1";
	public static final String PHONE2 = "phone2";
	public static final String EMPLOYEE_STATE = "employeeState";
	public static final String EX_COMPANY = "exCompany";
	public static final String EX_BUSINESS = "exBusiness";
	public static final String EX_JOB = "exJob";
	public static final String EDU_BACKGROUND = "eduBackground";
	public static final String EDU_SCHOOL = "eduSchool";
	public static final String EDU_FIELD = "eduField";
	public static final String HOMETOWN = "hometown";
	public static final String ADDRESS = "address";
	public static final String CHINA_ID = "chinaId";

	@Override
	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#save(nju.software.dataobject.Employee)
	 */
	@Override
	public void save(Employee transientInstance) {
		log.debug("saving Employee instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findById(java.lang.Integer)
	 */
	@Override
	public Employee findById(java.lang.Integer id) {
		log.debug("getting Employee instance with id: " + id);
		try {
			Employee instance = (Employee) getHibernateTemplate().get(
					"nju.software.dataobject.Employee", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByExample(nju.software.dataobject.Employee)
	 */
	@Override
	public List<Employee> findByExample(Employee instance) {
		log.debug("finding Employee instance by example");
		try {
			List<Employee> results = getHibernateTemplate()
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
	 * @see nju.software.dao.impl.IEmployeeDAO#delete(nju.software.dataobject.Employee)
	 */
	@Override
	public void delete(Employee persistentInstance) {
		log.debug("deleting Employee instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Employee instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Employee as model where model."
					+ propertyName + " = ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/*
	 * hcj,根据市场部和部门专员姓名模糊查询
	 */
	public List findEmployees(String propertyName, Object value) {
		log.debug("finding Employee instance with property: " + propertyName
				+ ", value: " + value);
		List list =new ArrayList<Employee>();
		try {
			String queryString = "from Employee as model where model.department = '市场部' and model."
					+ propertyName + " like ?";
			list =getHibernateTemplate().find(queryString, "%"+(String)value+"%");
		} catch (RuntimeException re) {
			
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByEmployeeName(java.lang.Object)
	 */
	@Override
	public List<Employee> findByEmployeeName(Object employeeName) {
		return findEmployees(EMPLOYEE_NAME, employeeName);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findBySex(java.lang.Object)
	 */
	@Override
	public List<Employee> findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByAge(java.lang.Object)
	 */
	@Override
	public List<Employee> findByAge(Object age) {
		return findByProperty(AGE, age);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByDepartment(java.lang.Object)
	 */
	@Override
	public List<Employee> findByDepartment(Object department) {
		return findByProperty(DEPARTMENT, department);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByDirectLeader(java.lang.Object)
	 */
	@Override
	public List<Employee> findByDirectLeader(Object directLeader) {
		return findByProperty(DIRECT_LEADER, directLeader);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByEmployeeLevel(java.lang.Object)
	 */
	@Override
	public List<Employee> findByEmployeeLevel(Object employeeLevel) {
		return findByProperty(EMPLOYEE_LEVEL, employeeLevel);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByPhone1(java.lang.Object)
	 */
	@Override
	public List<Employee> findByPhone1(Object phone1) {
		return findByProperty(PHONE1, phone1);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByPhone2(java.lang.Object)
	 */
	@Override
	public List<Employee> findByPhone2(Object phone2) {
		return findByProperty(PHONE2, phone2);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByEmployeeState(java.lang.Object)
	 */
	@Override
	public List<Employee> findByEmployeeState(Object employeeState) {
		return findByProperty(EMPLOYEE_STATE, employeeState);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByExCompany(java.lang.Object)
	 */
	@Override
	public List<Employee> findByExCompany(Object exCompany) {
		return findByProperty(EX_COMPANY, exCompany);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByExBusiness(java.lang.Object)
	 */
	@Override
	public List<Employee> findByExBusiness(Object exBusiness) {
		return findByProperty(EX_BUSINESS, exBusiness);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByExJob(java.lang.Object)
	 */
	@Override
	public List<Employee> findByExJob(Object exJob) {
		return findByProperty(EX_JOB, exJob);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByEduBackground(java.lang.Object)
	 */
	@Override
	public List<Employee> findByEduBackground(Object eduBackground) {
		return findByProperty(EDU_BACKGROUND, eduBackground);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByEduSchool(java.lang.Object)
	 */
	@Override
	public List<Employee> findByEduSchool(Object eduSchool) {
		return findByProperty(EDU_SCHOOL, eduSchool);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByEduField(java.lang.Object)
	 */
	@Override
	public List<Employee> findByEduField(Object eduField) {
		return findByProperty(EDU_FIELD, eduField);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByHometown(java.lang.Object)
	 */
	@Override
	public List<Employee> findByHometown(Object hometown) {
		return findByProperty(HOMETOWN, hometown);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByAddress(java.lang.Object)
	 */
	@Override
	public List<Employee> findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findByChinaId(java.lang.Object)
	 */
	@Override
	public List<Employee> findByChinaId(Object chinaId) {
		return findByProperty(CHINA_ID, chinaId);
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#findAll()
	 */
	@Override
	public List<Employee> findAll() {
		log.debug("finding all Employee instances");
		try {
			String queryString = "from Employee";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#merge(nju.software.dataobject.Employee)
	 */
	@Override
	public Employee merge(Employee detachedInstance) {
		log.debug("merging Employee instance");
		try {
			Employee result = getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#attachDirty(nju.software.dataobject.Employee)
	 */
	@Override
	public void attachDirty(Employee instance) {
		log.debug("attaching dirty Employee instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see nju.software.dao.impl.IEmployeeDAO#attachClean(nju.software.dataobject.Employee)
	 */
	@Override
	public void attachClean(Employee instance) {
		log.debug("attaching clean Employee instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IEmployeeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IEmployeeDAO) ctx.getBean("EmployeeDAO");
	}

	@Override
	public List<Employee> findByPage(int page, int numberPerPage) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
		int start=numberPerPage*(page-1);
		criteria.addOrder(org.hibernate.criterion.Order.desc("employeeId"));
		List<Employee> list = getHibernateTemplate().findByCriteria(criteria,start, numberPerPage);
		return list;
		
//		int first = (page-1)*numberPerPage;
//		List<Employee> list = null;
//		Session session = null;
//		try {
//			session = this.getSession();
//			Query q = session.createQuery("from Employee as model order by employee_id desc");
//			q.setFirstResult(first);
//			q.setMaxResults(numberPerPage);
//			list = q.list();
//			session.flush();
//			return list;
//		} catch (DataAccessResourceFailureException e) {
//			e.printStackTrace();
//		} catch (HibernateException e) {
//			e.printStackTrace();
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
//		getHibernateTemplate().executeFind(new HibernateCallback<Employee>() {
//			public Employee doInHibernate(Session session)
//					throws HibernateException, SQLException {
//				Query query = session.createQuery("from employee");
//				query.setFirstResult(first);
//				query.setMaxResults(numPerPage);
//				List list = query.list();
//				return (Employee) list;
//			}
//			
//		})
//		return list;
	}

	/**
	 * return the count of records by certain property
	 */
	@Override
	public int count(String propertyName, Object value) {
		// TODO Auto-generated method stub
		log.debug("counting Employee instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "select count(*) from Employee as model where model."
					+ propertyName + "= ?";
			
			long count = (Long)getHibernateTemplate().find(queryString, value).get(0);
//			System.out.println(count);
			return (int)count;
		} catch (RuntimeException re) {
			log.error("count by property name failed", re);
			re.printStackTrace();
			throw re;
		}
	}

	/**
	 * return the count of records
	 */
	@Override
	public int count() {
		// TODO Auto-generated method stub
		log.debug("counting Employee instance");
		try {
			String queryString = "select count(*) from Employee";
			long count = (Long) getHibernateTemplate().find(queryString).get(0);
			return (int)count;
		} catch (RuntimeException re) {
			log.error("count by property name failed", re);
			throw re;
		}
	}
	
	
	@Override
	public List findByPropertyinDESC(String propertyName, Object value) {
		log.debug("finding Employee instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Employee as model where model."
					+ propertyName + "= ? order by model.employee_name desc";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
//	public List getAll

}