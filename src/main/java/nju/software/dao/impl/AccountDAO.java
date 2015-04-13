package nju.software.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;

import nju.software.dao.IAccountDAO;
import nju.software.dataobject.Account;
import nju.software.dataobject.AccountRole;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Account entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see nju.software.dataobject.Account
 * @author MyEclipse Persistence Tools
 */
public class AccountDAO extends HibernateDaoSupport implements IAccountDAO {
	private static final Logger log = LoggerFactory.getLogger(AccountDAO.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String USER_TYPE = "userType";
	public static final String USER_PASSWORD = "userPassword";
	public static final String USER_NAME = "userName";
	public static final String USER_ROLE = "userRole";
	public static final String ROLE_MARKETSTAFF="marketStaff";
	@Override
	protected void initDao() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IAccountDAO#save(nju.software.dataobject.Account)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IAccoutDAO#save(nju.software.dataobject.Account)
	 */
	@Override
	public void save(Account transientInstance) {
		log.debug("saving Account instance");
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
	 * nju.software.dao.impl.IAccountDAO#delete(nju.software.dataobject.Account)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IAccoutDAO#delete(nju.software.dataobject.Account)
	 */
	@Override
	public void delete(Account persistentInstance) {
		log.debug("deleting Account instance");
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
	 * @see nju.software.dao.impl.IAccountDAO#findById(java.lang.Integer)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IAccoutDAO#findById(java.lang.Integer)
	 */
	@Override
	public Account findById(java.lang.Integer id) {
		log.debug("getting Account instance with id: " + id);
		try {
			Account instance = (Account) getHibernateTemplate().get(
					"nju.software.dataobject.Account", id);
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
	 * nju.software.dao.impl.IAccountDAO#findByExample(nju.software.dataobject
	 * .Account)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IAccoutDAO#findByExample(nju.software.dataobject
	 * .Account)
	 */
	@Override
	public List<Account> findByExample(Account instance) {
		log.debug("finding Account instance by example");
		try {
			List<Account> results = getHibernateTemplate()
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
	 * @see nju.software.dao.impl.IAccountDAO#findByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IAccoutDAO#findByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Account instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Account as model where model."
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
	 * @see nju.software.dao.impl.IAccountDAO#findByUserId(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IAccoutDAO#findByUserId(java.lang.Object)
	 */
	@Override
	public List<Account> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IAccountDAO#findByUserType(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IAccoutDAO#findByUserType(java.lang.Object)
	 */
	@Override
	public List<Account> findByUserType(Object userType) {
		return findByProperty(USER_TYPE, userType);
	}
	
	public List<Account> findByUserRole(Object userRole){
		return findByProperty(USER_ROLE, userRole);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IAccountDAO#findByUserPassword(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IAccoutDAO#findByUserPassword(java.lang.Object)
	 */
	@Override
	public List<Account> findByUserPassword(Object userPassword) {
		return findByProperty(USER_PASSWORD, userPassword);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IAccountDAO#findByUserName(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IAccoutDAO#findByUserName(java.lang.Object)
	 */
	@Override
	public List<Account> findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IAccountDAO#findAll()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nju.software.dao.impl.IAccoutDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Account instances");
		try {
			String queryString = "from Account";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IAccountDAO#merge(nju.software.dataobject.Account)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IAccoutDAO#merge(nju.software.dataobject.Account)
	 */
	@Override
	public Account merge(Account detachedInstance) {
		log.debug("merging Account instance");
		try {
			Account result = getHibernateTemplate().merge(
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
	 * nju.software.dao.impl.IAccountDAO#attachDirty(nju.software.dataobject
	 * .Account)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IAccoutDAO#attachDirty(nju.software.dataobject.
	 * Account)
	 */
	@Override
	public void attachDirty(Account instance) {
		log.debug("attaching dirty Account instance");
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
	 * nju.software.dao.impl.IAccountDAO#attachClean(nju.software.dataobject
	 * .Account)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nju.software.dao.impl.IAccoutDAO#attachClean(nju.software.dataobject.
	 * Account)
	 */
	@Override
	public void attachClean(Account instance) {
		log.debug("attaching clean Account instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IAccountDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IAccountDAO) ctx.getBean("AccountDAO");
	}

	@Override
	public List<Account> findByUserIdAndUserName(String role, Integer userId) {
		// TODO Auto-generated method stub
		log.debug("attaching clean Account instance");
		try {

			System.out.println(role);
			System.out.println(userId);
			String query = "from Account as model where model.userType=? and model.userId=?";

			return getHibernateTemplate().find(query, new Object[] { role, userId });
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
		
	}
    @Override
	public boolean isEmptytOfTable(){
    	
    	
    	final String num="select count(*) from account a";
		Object obj = null;
		try {
			obj = this.getHibernateTemplate().execute(new HibernateCallback() {
				@Override
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return session.createSQLQuery(num).uniqueResult();
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
    	
		BigInteger big =new BigInteger(String.valueOf(0));
    	//System.out.println("=============obj:"+obj);
    	if((obj.equals(big))){
    		System.out.println("in");
    		return true ;
    	}
    	
    	
    	return false ;   	
    }
   
	@Override
	public List findPermissionBYName(String username){
    	List list = null;
    	
		final String hql="select distinct p.MYID from permission p,account_role ar,account a,role_permission rp where p.PID is null and p.PERMISSION_ID=rp.permission_id and rp.role_id=ar.role_id and ar.account_id=a.account_id and ar.status='A'and a.user_name='"+username+"'";
                
		list =  this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(hql);
								
				
				return query.list();
			}
		});
    	//System.out.println("============list:"+list.toString());
    	return list ;
    	
    }

	@Override
	public boolean addAccountRole(String roleName, int accountId) {
		// TODO Auto-generated method stub
		Date date =new Date();
		final String hql="select r.roleId from Role r where r.name='"+roleName+"'";
        int roleId =  (int) this.getHibernateTemplate().execute(new HibernateCallback<Object>(){

			
			@Override
			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				Query q = arg0.createQuery(hql);
				return q.uniqueResult();
			}
			
		});
        System.out.println("============================roleId:"+roleId);
        AccountRole accountRole=  new AccountRole();
		accountRole.setRoleId(roleId);
		accountRole.setAccountId(accountId);
		accountRole.setStatus("A");
		accountRole.setCreated(date);
		accountRole.setLastmod(date);
		this.getHibernateTemplate().saveOrUpdate(accountRole);
	
		return false;
	}
    
    
	
	public void deleteAccountRole(AccountRole persistentInstance) {
		log.debug("deleting Account instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public List<AccountRole> findAccountRoleById(int accountId) {
		// TODO Auto-generated method stub
		String query = "from AccountRole as model where model.accountId=?";

		return getHibernateTemplate().find(query, accountId );
		//return null;
	}
    
	public void SaveOrUpDate(Account account){
		this.getHibernateTemplate().saveOrUpdate(account);
	}

	@Override
	public List findByPropertyinDESC(String propertyName, Object value) {

		
			log.debug("finding Account instance with property: " + propertyName
					+ ", value: " + value);
			try {
				String queryString = "from Employee as model where model."
						+ propertyName + "= ? order by model.user_name desc";
				return getHibernateTemplate().find(queryString, value);
			} catch (RuntimeException re) {
				log.error("find by property name failed", re);
				throw re;
			}
		
	}

	@Override
	public List getAllManagerStaff() {
		return findByPropertyinDESC(USER_ROLE,ROLE_MARKETSTAFF);
	}
	
    
    
    
}