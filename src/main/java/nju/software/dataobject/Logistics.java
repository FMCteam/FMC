package nju.software.dataobject;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Logistics entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "logistics", catalog = "fmc")
public class Logistics implements java.io.Serializable {

	// Fields

	private Integer orderId;
	private Timestamp inPostSampleClothesTime;
	private String inPostSampleClothesType;
	private String inPostSampleClothesNumber;
	private String sampleClothesType;
	private String sampleClothesAddress;
	private String sampleClothesName;
	private String sampleClothesPhone;
	private Timestamp sampleClothesTime;
	private String sampleClothesRemark;
	private String sampleClothesNumber;
	private String productClothesType;
	private String productClothesAddress;
	private String productClothesPrice;
	private String productClothesNumber;
	private String productClothesName;
	private String productClothesPhone;
	private Timestamp productClothesTime;
	private String productClothesRemark;

	// Constructors

	/** default constructor */
	public Logistics() {
	}

	/** minimal constructor */
	public Logistics(Integer orderId) {
		this.orderId = orderId;
	}

	/** full constructor */
	public Logistics(Integer orderId, Timestamp inPostSampleClothesTime,
			String inPostSampleClothesType, String inPostSampleClothesNumber,
			String sampleClothesType, String sampleClothesAddress,
			String sampleClothesName, String sampleClothesPhone,
			Timestamp sampleClothesTime, String sampleClothesRemark,
			String productClothesType, String productClothesAddress,
			String productClothesPrice, String productClothesNumber,
			String productClothesName, String productClothesPhone,
			Timestamp productClothesTime, String productClothesRemark) {
		this.orderId = orderId;
		this.inPostSampleClothesTime = inPostSampleClothesTime;
		this.inPostSampleClothesType = inPostSampleClothesType;
		this.inPostSampleClothesNumber = inPostSampleClothesNumber;
		this.sampleClothesType = sampleClothesType;
		this.sampleClothesAddress = sampleClothesAddress;
		this.sampleClothesName = sampleClothesName;
		this.sampleClothesPhone = sampleClothesPhone;
		this.sampleClothesTime = sampleClothesTime;
		this.sampleClothesRemark = sampleClothesRemark;
		this.productClothesType = productClothesType;
		this.productClothesAddress = productClothesAddress;
		this.productClothesPrice = productClothesPrice;
		this.productClothesNumber = productClothesNumber;
		this.productClothesName = productClothesName;
		this.productClothesPhone = productClothesPhone;
		this.productClothesTime = productClothesTime;
		this.productClothesRemark = productClothesRemark;
	}

	// Property accessors
	@Id
	@Column(name = "order_id", unique = true, nullable = false)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "in_post_sample_clothes_time", length = 19)
	public Timestamp getInPostSampleClothesTime() {
		return this.inPostSampleClothesTime;
	}

	public void setInPostSampleClothesTime(Timestamp inPostSampleClothesTime) {
		this.inPostSampleClothesTime = inPostSampleClothesTime;
	}

	@Column(name = "in_post_sample_clothes_type", length = 250)
	public String getInPostSampleClothesType() {
		return this.inPostSampleClothesType;
	}

	public void setInPostSampleClothesType(String inPostSampleClothesType) {
		this.inPostSampleClothesType = inPostSampleClothesType;
	}

	@Column(name = "in_post_sample_clothes_number", length = 250)
	public String getInPostSampleClothesNumber() {
		return this.inPostSampleClothesNumber;
	}

	public void setInPostSampleClothesNumber(String inPostSampleClothesNumber) {
		this.inPostSampleClothesNumber = inPostSampleClothesNumber;
	}

	@Column(name = "sample_clothes_type", length = 250)
	public String getSampleClothesType() {
		return this.sampleClothesType;
	}

	public void setSampleClothesType(String sampleClothesType) {
		this.sampleClothesType = sampleClothesType;
	}

	@Column(name = "sample_clothes_address", length = 250)
	public String getSampleClothesAddress() {
		return this.sampleClothesAddress;
	}

	public void setSampleClothesAddress(String sampleClothesAddress) {
		this.sampleClothesAddress = sampleClothesAddress;
	}

	@Column(name = "sample_clothes_name", length = 250)
	public String getSampleClothesName() {
		return this.sampleClothesName;
	}

	public void setSampleClothesName(String sampleClothesName) {
		this.sampleClothesName = sampleClothesName;
	}

	@Column(name = "sample_clothes_phone", length = 250)
	public String getSampleClothesPhone() {
		return this.sampleClothesPhone;
	}

	public void setSampleClothesPhone(String sampleClothesPhone) {
		this.sampleClothesPhone = sampleClothesPhone;
	}

	@Column(name = "sample_clothes_time", length = 19)
	public Timestamp getSampleClothesTime() {
		return this.sampleClothesTime;
	}

	public void setSampleClothesTime(Timestamp sampleClothesTime) {
		this.sampleClothesTime = sampleClothesTime;
	}

	@Column(name = "sample_clothes_remark", length = 500)
	public String getSampleClothesRemark() {
		return this.sampleClothesRemark;
	}

	public void setSampleClothesRemark(String sampleClothesRemark) {
		this.sampleClothesRemark = sampleClothesRemark;
	}

	@Column(name = "product_clothes_type", length = 250)
	public String getProductClothesType() {
		return this.productClothesType;
	}

	public void setProductClothesType(String productClothesType) {
		this.productClothesType = productClothesType;
	}

	@Column(name = "product_clothes_address", length = 250)
	public String getProductClothesAddress() {
		return this.productClothesAddress;
	}

	public void setProductClothesAddress(String productClothesAddress) {
		this.productClothesAddress = productClothesAddress;
	}

	@Column(name = "product_clothes_price", length = 250)
	public String getProductClothesPrice() {
		return this.productClothesPrice;
	}

	public void setProductClothesPrice(String productClothesPrice) {
		this.productClothesPrice = productClothesPrice;
	}

	@Column(name = "product_clothes_number", length = 250)
	public String getProductClothesNumber() {
		return this.productClothesNumber;
	}

	public void setProductClothesNumber(String productClothesNumber) {
		this.productClothesNumber = productClothesNumber;
	}

	@Column(name = "product_clothes_name", length = 250)
	public String getProductClothesName() {
		return this.productClothesName;
	}

	public void setProductClothesName(String productClothesName) {
		this.productClothesName = productClothesName;
	}

	@Column(name = "product_clothes_phone", length = 250)
	public String getProductClothesPhone() {
		return this.productClothesPhone;
	}

	public void setProductClothesPhone(String productClothesPhone) {
		this.productClothesPhone = productClothesPhone;
	}

	@Column(name = "product_clothes_time", length = 19)
	public Timestamp getProductClothesTime() {
		return this.productClothesTime;
	}

	public void setProductClothesTime(Timestamp productClothesTime) {
		this.productClothesTime = productClothesTime;
	}

	@Column(name = "product_clothes_remark", length = 500)
	public String getProductClothesRemark() {
		return this.productClothesRemark;
	}

	public void setProductClothesRemark(String productClothesRemark) {
		this.productClothesRemark = productClothesRemark;
	}
	
	@Column(name = "sample_clothes_number", length=250)
	public String getSampleClothesNumber() {
		return sampleClothesNumber;
	}

	public void setSampleClothesNumber(String sampleClothesNumber) {
		this.sampleClothesNumber = sampleClothesNumber;
	}

}