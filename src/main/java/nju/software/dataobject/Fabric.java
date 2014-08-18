package nju.software.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Fabric entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fabric", catalog = "newfmc")
public class Fabric implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer orderId;
	private String fabricName;
	private String fabricAmount;

	// Constructors

	/** default constructor */
	public Fabric() {
	}

	/** full constructor */
	public Fabric(Integer orderId, String fabricName, String fabricAmount) {
		this.orderId = orderId;
		this.fabricName = fabricName;
		this.fabricAmount = fabricAmount;
	}

	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "order_id", nullable = false)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "fabric_name", nullable = false, length = 250)
	public String getFabricName() {
		return this.fabricName;
	}

	public void setFabricName(String fabricName) {
		this.fabricName = fabricName;
	}

	@Column(name = "fabric_amount", nullable = false, length = 250)
	public String getFabricAmount() {
		return this.fabricAmount;
	}

	public void setFabricAmount(String fabricAmount) {
		this.fabricAmount = fabricAmount;
	}

}