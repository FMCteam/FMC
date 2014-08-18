package nju.software.dataobject;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Customer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "customer", catalog = "fmc")
public class Customer implements java.io.Serializable {

	// Fields

	private Integer customerId;
	private String companyId;
	private String companyName;
	private String customerName;
	private String province;
	private String city;
	private String websiteUrl;
	private String websiteType;
	private String companyAddress;
	private String companyFax;
	private String companyPhone;
	private String buyContact;
	private String contactPhone1;
	private String contactPhone2;
	private String qq;
	private String email;
	private String customerPhone;
	private String bossName;
	private String bossPhone;
	private Timestamp registerDate;
	private Integer registerEmployeeId;

	// Constructors

	/** default constructor */
	public Customer() {
	}

	/** minimal constructor */
	public Customer(String companyId, String companyName, String customerName,
			String province, Timestamp registerDate, Integer registerEmployeeId) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.customerName = customerName;
		this.province = province;
		this.registerDate = registerDate;
		this.registerEmployeeId = registerEmployeeId;
	}

	/** full constructor */
	public Customer(String companyId, String companyName, String customerName,
			String province, String city, String websiteUrl,
			String websiteType, String companyAddress, String companyFax,
			String companyPhone, String buyContact, String contactPhone1,
			String contactPhone2, String qq, String email,
			String customerPhone, String bossName, String bossPhone,
			Timestamp registerDate, Integer registerEmployeeId) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.customerName = customerName;
		this.province = province;
		this.city = city;
		this.websiteUrl = websiteUrl;
		this.websiteType = websiteType;
		this.companyAddress = companyAddress;
		this.companyFax = companyFax;
		this.companyPhone = companyPhone;
		this.buyContact = buyContact;
		this.contactPhone1 = contactPhone1;
		this.contactPhone2 = contactPhone2;
		this.qq = qq;
		this.email = email;
		this.customerPhone = customerPhone;
		this.bossName = bossName;
		this.bossPhone = bossPhone;
		this.registerDate = registerDate;
		this.registerEmployeeId = registerEmployeeId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "customer_id", unique = true, nullable = false)
	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "company_id", nullable = false, length = 250)
	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Column(name = "company_name", nullable = false, length = 250)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "customer_name", nullable = false, length = 250)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "province", nullable = false, length = 250)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city", length = 250)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "website_url", length = 250)
	public String getWebsiteUrl() {
		return this.websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	@Column(name = "website_type", length = 250)
	public String getWebsiteType() {
		return this.websiteType;
	}

	public void setWebsiteType(String websiteType) {
		this.websiteType = websiteType;
	}

	@Column(name = "company_address", length = 250)
	public String getCompanyAddress() {
		return this.companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	@Column(name = "company_fax", length = 250)
	public String getCompanyFax() {
		return this.companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	@Column(name = "company_phone", length = 250)
	public String getCompanyPhone() {
		return this.companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	@Column(name = "buy_contact", length = 250)
	public String getBuyContact() {
		return this.buyContact;
	}

	public void setBuyContact(String buyContact) {
		this.buyContact = buyContact;
	}

	@Column(name = "contact_phone_1", length = 250)
	public String getContactPhone1() {
		return this.contactPhone1;
	}

	public void setContactPhone1(String contactPhone1) {
		this.contactPhone1 = contactPhone1;
	}

	@Column(name = "contact_phone_2", length = 250)
	public String getContactPhone2() {
		return this.contactPhone2;
	}

	public void setContactPhone2(String contactPhone2) {
		this.contactPhone2 = contactPhone2;
	}

	@Column(name = "qq", length = 250)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "email", length = 250)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "customer_phone", length = 250)
	public String getCustomerPhone() {
		return this.customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	@Column(name = "boss_name", length = 250)
	public String getBossName() {
		return this.bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	@Column(name = "boss_phone", length = 250)
	public String getBossPhone() {
		return this.bossPhone;
	}

	public void setBossPhone(String bossPhone) {
		this.bossPhone = bossPhone;
	}

	@Column(name = "register_date", nullable = false, length = 19)
	public Timestamp getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}

	@Column(name = "register_employee_id", nullable = false)
	public Integer getRegisterEmployeeId() {
		return this.registerEmployeeId;
	}

	public void setRegisterEmployeeId(Integer registerEmployeeId) {
		this.registerEmployeeId = registerEmployeeId;
	}

}