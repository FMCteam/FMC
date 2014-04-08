package nju.software.dataobject;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Order entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "order", catalog = "fmc")
public class Order implements java.io.Serializable {

	// Fields

	private Integer orderId;
	private Integer customerId;
	private Integer employeeId;
	private String orderState;
	private Timestamp orderTime;
	private String customerName;
	private String customerCompany;
	private String customerCompanyFax;
	private String customerPhone1;
	private String customerPhone2;
	private String customerCompanyAddress;
	private String styleName;
	private String fabricType;
	private String styleSex;
	private String styleSeason;
	private String specialProcess;
	private String otherRequirements;
	private String sampleClothesPicture;
	private String referencePicture;
	private Integer askAmount;
	private String askProducePeriod;
	private Timestamp askDeliverDate;
	private String askCodeNumber;
	private Short hasPostedSampleClothes;//0 no sample 1 recieved the sample 2 not recieved the sample
	private Short isNeedSampleClothes;//0 not need sample 1 need sample
	private String orderSource;
	private String payAccountInfo;
	private double discount;
	private double totalMoney;
	private double sampleMoney;
	
	
	@Column(name = "sampleMoney", nullable = true, precision = 22, scale = 0)
	public double getSampleMoney() {
		return sampleMoney;
	}

	public void setSampleMoney(double sampleMoney) {
		this.sampleMoney = sampleMoney;
	}

	@Column(name = "discount", nullable = false, precision = 22, scale = 0)
	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@Column(name = "total_money", nullable = false, precision = 22, scale = 0)
	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	//特殊工艺：水洗，激光，压皱。多选，并且不限于这三个预定的工艺。
	//采用使用“|”符号分隔。比如"shuixi|jiguang|其它",表明使用了水洗和激光的预定工艺，以及添加的“其它”工艺。
	public static enum SpecialProcessType {
		SHUIXI,
		JIGUANG,
		YAZHOU
	}
	//款式季节
	public static enum StyleSeasonType {
		CHUNXIA, //春夏
		QIUDONG  //秋冬
	}
	//其它要求：有主标，有吊牌，可水洗
	public static enum OtherRequirementsType {
		ZHUBIAO,
		DIAOPAI,
		SHUIXI
	}
	//面料类型：梭织/针织/编织/梭针混合/针编混合/梭编混合
	public static enum FabricType {
		SUOZHI,
		ZHENZHI,
		BIANZHI,
		SUOZHENHUNHE,
		ZHENBIANHUNHE,
		SUOBIANHUNHE
	}
	// Constructors

	/** default constructor */
	public Order() {
	}

	/** minimal constructor */
	public Order(Integer customerId, Integer employeeId, String orderState,
			Timestamp orderTime, String customerName,
			Short hasPostedSampleClothes, Short isNeedSampleClothes) {
		this.customerId = customerId;
		this.employeeId = employeeId;
		this.orderState = orderState;
		this.orderTime = orderTime;
		this.customerName = customerName;
		this.hasPostedSampleClothes = hasPostedSampleClothes;
		this.isNeedSampleClothes = isNeedSampleClothes;
	}

	/** full constructor */
	public Order(Integer customerId, Integer employeeId, String orderState,
			Timestamp orderTime, String customerName, String customerCompany,
			String customerCompanyFax, String customerPhone1,
			String customerPhone2, String customerCompanyAddress,
			String styleName, String fabricType, String styleSex,
			String styleSeason, String specialProcess,
			String otherRequirements, String sampleClothesPicture,
			String referencePicture, Integer askAmount,
			String askProducePeriod, Timestamp askDeliverDate,
			String askCodeNumber, Short hasPostedSampleClothes,
			Short isNeedSampleClothes, String orderSource) {
		this.customerId = customerId;
		this.employeeId = employeeId;
		this.orderState = orderState;
		this.orderTime = orderTime;
		this.customerName = customerName;
		this.customerCompany = customerCompany;
		this.customerCompanyFax = customerCompanyFax;
		this.customerPhone1 = customerPhone1;
		this.customerPhone2 = customerPhone2;
		this.customerCompanyAddress = customerCompanyAddress;
		this.styleName = styleName;
		this.fabricType = fabricType;
		this.styleSex = styleSex;
		this.styleSeason = styleSeason;
		this.specialProcess = specialProcess;
		this.otherRequirements = otherRequirements;
		this.sampleClothesPicture = sampleClothesPicture;
		this.referencePicture = referencePicture;
		this.askAmount = askAmount;
		this.askProducePeriod = askProducePeriod;
		this.askDeliverDate = askDeliverDate;
		this.askCodeNumber = askCodeNumber;
		this.hasPostedSampleClothes = hasPostedSampleClothes;
		this.isNeedSampleClothes = isNeedSampleClothes;
		this.orderSource = orderSource;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "order_id", unique = true, nullable = false)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "customer_id", nullable = false)
	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "employee_id", nullable = false)
	public Integer getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "order_state", nullable = false, length = 250)
	public String getOrderState() {
		return this.orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	@Column(name = "order_time", nullable = false, length = 19)
	public Timestamp getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	@Column(name = "customer_name", nullable = false, length = 250)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "customer_company", length = 250)
	public String getCustomerCompany() {
		return this.customerCompany;
	}

	public void setCustomerCompany(String customerCompany) {
		this.customerCompany = customerCompany;
	}

	@Column(name = "customer_company_fax", length = 250)
	public String getCustomerCompanyFax() {
		return this.customerCompanyFax;
	}

	public void setCustomerCompanyFax(String customerCompanyFax) {
		this.customerCompanyFax = customerCompanyFax;
	}

	@Column(name = "customer_phone_1", length = 250)
	public String getCustomerPhone1() {
		return this.customerPhone1;
	}

	public void setCustomerPhone1(String customerPhone1) {
		this.customerPhone1 = customerPhone1;
	}

	@Column(name = "customer_phone_2", length = 250)
	public String getCustomerPhone2() {
		return this.customerPhone2;
	}

	public void setCustomerPhone2(String customerPhone2) {
		this.customerPhone2 = customerPhone2;
	}

	@Column(name = "customer_company_address", length = 250)
	public String getCustomerCompanyAddress() {
		return this.customerCompanyAddress;
	}

	public void setCustomerCompanyAddress(String customerCompanyAddress) {
		this.customerCompanyAddress = customerCompanyAddress;
	}

	@Column(name = "style_name", length = 250)
	public String getStyleName() {
		return this.styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	@Column(name = "fabric_type", length = 250)
	public String getFabricType() {
		return this.fabricType;
	}

	public void setFabricType(String fabricType) {
		this.fabricType = fabricType;
	}

	@Column(name = "style_sex", length = 250)
	public String getStyleSex() {
		return this.styleSex;
	}

	public void setStyleSex(String styleSex) {
		this.styleSex = styleSex;
	}

	@Column(name = "style_season", length = 250)
	public String getStyleSeason() {
		return this.styleSeason;
	}

	public void setStyleSeason(String styleSeason) {
		this.styleSeason = styleSeason;
	}

	@Column(name = "special_process", length = 250)
	public String getSpecialProcess() {
		return this.specialProcess;
	}

	public void setSpecialProcess(String specialProcess) {
		this.specialProcess = specialProcess;
	}

	@Column(name = "other_requirements", length = 250)
	public String getOtherRequirements() {
		return this.otherRequirements;
	}

	public void setOtherRequirements(String otherRequirements) {
		this.otherRequirements = otherRequirements;
	}

	@Column(name = "sample_clothes_picture", length = 250)
	public String getSampleClothesPicture() {
		return this.sampleClothesPicture;
	}

	public void setSampleClothesPicture(String sampleClothesPicture) {
		this.sampleClothesPicture = sampleClothesPicture;
	}

	@Column(name = "reference_picture", length = 250)
	public String getReferencePicture() {
		return this.referencePicture;
	}

	public void setReferencePicture(String referencePicture) {
		this.referencePicture = referencePicture;
	}

	@Column(name = "ask_amount")
	public Integer getAskAmount() {
		return this.askAmount;
	}

	public void setAskAmount(Integer askAmount) {
		this.askAmount = askAmount;
	}

	@Column(name = "ask_produce_period", length = 250)
	public String getAskProducePeriod() {
		return this.askProducePeriod;
	}

	public void setAskProducePeriod(String askProducePeriod) {
		this.askProducePeriod = askProducePeriod;
	}

	@Column(name = "ask_deliver_date", length = 19)
	public Timestamp getAskDeliverDate() {
		return this.askDeliverDate;
	}

	public void setAskDeliverDate(Timestamp askDeliverDate) {
		this.askDeliverDate = askDeliverDate;
	}

	@Column(name = "ask_code_number", length = 250)
	public String getAskCodeNumber() {
		return this.askCodeNumber;
	}

	public void setAskCodeNumber(String askCodeNumber) {
		this.askCodeNumber = askCodeNumber;
	}

	@Column(name = "has_posted_sample_clothes", nullable = false)
	public Short getHasPostedSampleClothes() {
		return this.hasPostedSampleClothes;
	}

	public void setHasPostedSampleClothes(Short hasPostedSampleClothes) {
		this.hasPostedSampleClothes = hasPostedSampleClothes;
	}

	@Column(name = "is_need_sample_clothes", nullable = false)
	public Short getIsNeedSampleClothes() {
		return this.isNeedSampleClothes;
	}

	public void setIsNeedSampleClothes(Short isNeedSampleClothes) {
		this.isNeedSampleClothes = isNeedSampleClothes;
	}

	@Column(name = "order_source", length = 250)
	public String getOrderSource() {
		return this.orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	@Column(name = "pay_account_info", length = 250)
	public String getPayAccountInfo() {
		return payAccountInfo;
	}

	public void setPayAccountInfo(String payAccountInfo) {
		this.payAccountInfo = payAccountInfo;
	}

}