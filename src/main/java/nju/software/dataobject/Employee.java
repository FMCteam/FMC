package nju.software.dataobject;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Employee entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "employee", catalog = "newfmc")
public class Employee implements java.io.Serializable {

	// Fields
	private String jobPhone;
	private String email;
	private String qq;
	private Integer employeeId;
	private String employeeName;
	private Short sex;
	private Short age;
	private String department;
	private Timestamp entryTime;
	private Integer directLeader;
	private String employeeLevel;
	private String phone1;
	private String phone2;
	private String employeeState;
	private Timestamp leaveTime;
	private String exCompany;
	private String exBusiness;
	private String exJob;
	private String eduBackground;
	private String eduSchool;
	private String eduField;
	private String hometown;
	private String address;
	private String chinaId;

	// Constructors

	/** default constructor */
	public Employee() {
	}

	/** minimal constructor */
	public Employee(String employeeName, Short sex, Short age,
			String department, Timestamp entryTime, Integer directLeader,
			String employeeLevel, String employeeState, String chinaId) {
		this.employeeName = employeeName;
		this.sex = sex;
		this.age = age;
		this.department = department;
		this.entryTime = entryTime;
		this.directLeader = directLeader;
		this.employeeLevel = employeeLevel;
		this.employeeState = employeeState;
		this.chinaId = chinaId;
	}

	/** full constructor */
	public Employee(String employeeName, Short sex, Short age,
			String department, Timestamp entryTime, Integer directLeader,
			String employeeLevel, String phone1, String phone2,
			String employeeState, Timestamp leaveTime, String exCompany,
			String exBusiness, String exJob, String eduBackground,
			String eduSchool, String eduField, String hometown, String address,
			String chinaId) {
		this.employeeName = employeeName;
		this.sex = sex;
		this.age = age;
		this.department = department;
		this.entryTime = entryTime;
		this.directLeader = directLeader;
		this.employeeLevel = employeeLevel;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.employeeState = employeeState;
		this.leaveTime = leaveTime;
		this.exCompany = exCompany;
		this.exBusiness = exBusiness;
		this.exJob = exJob;
		this.eduBackground = eduBackground;
		this.eduSchool = eduSchool;
		this.eduField = eduField;
		this.hometown = hometown;
		this.address = address;
		this.chinaId = chinaId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "employee_id", unique = true, nullable = false)
	public Integer getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "employee_name", nullable = false, length = 250)
	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Column(name = "sex", nullable = false)
	public Short getSex() {
		return this.sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	@Column(name = "age", nullable = false)
	public Short getAge() {
		return this.age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	@Column(name = "department", nullable = false, length = 250)
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "entry_time", nullable = false, length = 19)
	public Timestamp getEntryTime() {
		return this.entryTime;
	}

	public void setEntryTime(Timestamp entryTime) {
		this.entryTime = entryTime;
	}

	@Column(name = "direct_leader", nullable = false)
	public Integer getDirectLeader() {
		return this.directLeader;
	}

	public void setDirectLeader(Integer directLeader) {
		this.directLeader = directLeader;
	}

	@Column(name = "employee_level", nullable = false, length = 250)
	public String getEmployeeLevel() {
		return this.employeeLevel;
	}

	public void setEmployeeLevel(String employeeLevel) {
		this.employeeLevel = employeeLevel;
	}

	@Column(name = "phone_1", length = 250)
	public String getPhone1() {
		return this.phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	@Column(name = "phone_2", length = 250)
	public String getPhone2() {
		return this.phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	@Column(name = "employee_state", nullable = false, length = 250)
	public String getEmployeeState() {
		return this.employeeState;
	}

	public void setEmployeeState(String employeeState) {
		this.employeeState = employeeState;
	}

	@Column(name = "leave_time", length = 19)
	public Timestamp getLeaveTime() {
		return this.leaveTime;
	}

	public void setLeaveTime(Timestamp leaveTime) {
		this.leaveTime = leaveTime;
	}

	@Column(name = "ex_company", length = 250)
	public String getExCompany() {
		return this.exCompany;
	}

	public void setExCompany(String exCompany) {
		this.exCompany = exCompany;
	}

	@Column(name = "ex_business", length = 250)
	public String getExBusiness() {
		return this.exBusiness;
	}

	public void setExBusiness(String exBusiness) {
		this.exBusiness = exBusiness;
	}

	@Column(name = "ex_job", length = 250)
	public String getExJob() {
		return this.exJob;
	}

	public void setExJob(String exJob) {
		this.exJob = exJob;
	}

	@Column(name = "edu_background", length = 250)
	public String getEduBackground() {
		return this.eduBackground;
	}

	public void setEduBackground(String eduBackground) {
		this.eduBackground = eduBackground;
	}

	@Column(name = "edu_school", length = 250)
	public String getEduSchool() {
		return this.eduSchool;
	}

	public void setEduSchool(String eduSchool) {
		this.eduSchool = eduSchool;
	}

	@Column(name = "edu_field", length = 250)
	public String getEduField() {
		return this.eduField;
	}

	public void setEduField(String eduField) {
		this.eduField = eduField;
	}

	@Column(name = "hometown", length = 250)
	public String getHometown() {
		return this.hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	@Column(name = "address", length = 250)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "china_id", nullable = false, length = 250)
	public String getChinaId() {
		return this.chinaId;
	}

	public void setChinaId(String chinaId) {
		this.chinaId = chinaId;
	}

	public String getJobPhone() {
		return jobPhone;
	}

	public void setJobPhone(String jobPhone) {
		this.jobPhone = jobPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

}