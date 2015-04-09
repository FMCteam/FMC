package nju.software.model;

import nju.software.dataobject.Employee;
import nju.software.dataobject.MarketstaffAlter;

public class StaffAlterInfo {
	private MarketstaffAlter Alter ;
	private Employee  employee ;
	private Employee  employeeNext ;
	
	public StaffAlterInfo( MarketstaffAlter marketStaffAlter,Employee  employee,Employee  employeeNext){
		this.Alter=marketStaffAlter;
		this.employee=employee;
		this.employeeNext=employeeNext;
	}
	
	
	public MarketstaffAlter getAlter() {
		return Alter;
	}
	public void setAlter(MarketstaffAlter marketStaffAlter) {
		this.Alter = marketStaffAlter;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Employee getEmployeeNext() {
		return employeeNext;
	}
	public void setEmployeeNext(Employee employeeNext) {
		this.employeeNext = employeeNext;
	}
	
	
	
	

}
