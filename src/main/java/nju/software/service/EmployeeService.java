package nju.software.service;

import java.util.List;

import nju.software.dataobject.Employee;

public interface EmployeeService {

	public Employee getEmployeeById(int employeeId);

	public int addEmployee(Employee employee);

	public boolean deleteEmployee(int employeeId);

	public boolean updateEmployee(Employee employee);

	public List<Employee> getAllEmployee();

	public List<Employee> getEmployeeByPage(int page, int numberPerPage);

	public int getcount();

	public List<Employee> getEmployeeByName(String employeename);

}
