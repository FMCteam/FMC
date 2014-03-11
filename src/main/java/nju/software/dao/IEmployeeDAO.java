package nju.software.dao;

import java.util.List;

import nju.software.dataobject.Employee;

public interface IEmployeeDAO {

	public abstract void save(Employee transientInstance);

	public abstract void delete(Employee persistentInstance);

	public abstract Employee findById(java.lang.Integer id);

	public abstract List<Employee> findByExample(Employee instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Employee> findByEmployeeName(Object employeeName);

	public abstract List<Employee> findBySex(Object sex);

	public abstract List<Employee> findByAge(Object age);

	public abstract List<Employee> findByDepartment(Object department);

	public abstract List<Employee> findByDirectLeader(Object directLeader);

	public abstract List<Employee> findByEmployeeLevel(Object employeeLevel);

	public abstract List<Employee> findByPhone1(Object phone1);

	public abstract List<Employee> findByPhone2(Object phone2);

	public abstract List<Employee> findByEmployeeState(Object employeeState);

	public abstract List<Employee> findByExCompany(Object exCompany);

	public abstract List<Employee> findByExBusiness(Object exBusiness);

	public abstract List<Employee> findByExJob(Object exJob);

	public abstract List<Employee> findByEduBackground(Object eduBackground);

	public abstract List<Employee> findByEduSchool(Object eduSchool);

	public abstract List<Employee> findByEduField(Object eduField);

	public abstract List<Employee> findByHometown(Object hometown);

	public abstract List<Employee> findByAddress(Object address);

	public abstract List<Employee> findByChinaId(Object chinaId);

	public abstract List findAll();
	
	public abstract List<Employee> findByPage(int page, int numberPerPage);
	
	public abstract int count(String propertyName, Object value);
	
	public abstract int count();

	public abstract Employee merge(Employee detachedInstance);

	public abstract void attachDirty(Employee instance);

	public abstract void attachClean(Employee instance);
}