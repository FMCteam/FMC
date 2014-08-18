package nju.software.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "description", catalog = "fmc")
public class Description implements java.io.Serializable {

	// Fields

	private Integer orderId;
	private String sampleClothesCustomerDescription;
	private String sampleClothesDesignDescription;
	private String productClothesCustomerDescription;
	private String productClothesDesignDescription;

	// Constructors

	/** default constructor */
	public Description() {
	}

	/** minimal constructor */
	public Description(Integer orderId) {
		this.orderId = orderId;
	}

	/** full constructor */
	public Description(Integer orderId,
			String sampleClothesCustomerDescription,
			String sampleClothesDesignDescription,
			String productClothesCustomerDescription,
			String productClothesDesignDescription) {
		this.orderId = orderId;
		this.sampleClothesCustomerDescription = sampleClothesCustomerDescription;
		this.sampleClothesDesignDescription = sampleClothesDesignDescription;
		this.productClothesCustomerDescription = productClothesCustomerDescription;
		this.productClothesDesignDescription = productClothesDesignDescription;
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

	@Column(name = "sample_clothes_customer_description", length = 65535)
	public String getSampleClothesCustomerDescription() {
		return this.sampleClothesCustomerDescription;
	}

	public void setSampleClothesCustomerDescription(
			String sampleClothesCustomerDescription) {
		this.sampleClothesCustomerDescription = sampleClothesCustomerDescription;
	}

	@Column(name = "sample_clothes_design_description", length = 65535)
	public String getSampleClothesDesignDescription() {
		return this.sampleClothesDesignDescription;
	}

	public void setSampleClothesDesignDescription(
			String sampleClothesDesignDescription) {
		this.sampleClothesDesignDescription = sampleClothesDesignDescription;
	}

	@Column(name = "product_clothes_customer_description", length = 65535)
	public String getProductClothesCustomerDescription() {
		return this.productClothesCustomerDescription;
	}

	public void setProductClothesCustomerDescription(
			String productClothesCustomerDescription) {
		this.productClothesCustomerDescription = productClothesCustomerDescription;
	}

	@Column(name = "product_clothes_design_description", length = 65535)
	public String getProductClothesDesignDescription() {
		return this.productClothesDesignDescription;
	}

	public void setProductClothesDesignDescription(
			String productClothesDesignDescription) {
		this.productClothesDesignDescription = productClothesDesignDescription;
	}

}