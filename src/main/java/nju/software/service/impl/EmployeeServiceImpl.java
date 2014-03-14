package nju.software.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nju.software.dao.impl.EmployeeDAO;
import nju.software.dataobject.Employee;
import nju.software.service.EmployeeService;


@Service("employeeServiceImpl")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDAO employeeDAO;
	
	public Employee getEmployeeById(int employeeId) {
		// TODO Auto-generated method stub
		try {
			Employee employee = employeeDAO.findById(employeeId);
			return employee;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int addEmployee(Employee employee) {
		// TODO Auto-generated method stub
		try {
			employeeDAO.save(employee);
			return employee.getEmployeeId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public boolean deleteEmployee(int employeeId) {
		// TODO Auto-generated method stub
		try {
			employeeDAO.delete(getEmployeeById(employeeId));
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		try {
			employeeDAO.attachDirty(employee);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public List<Employee> getAllEmployee() {
		// TODO Auto-generated method stub
		try {
			List<Employee> list = employeeDAO.findAll();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Employee> getEmployeeByPage(int page, int numberPerPage) {
		// TODO Auto-generated method stub
		try {
			List<Employee> list = employeeDAO.findByPage(page, numberPerPage);
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int getcount() {
		// TODO Auto-generated method stub
		try {
			int count = employeeDAO.count();
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

}
