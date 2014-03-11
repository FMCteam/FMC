package nju.software.dataobject;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Package entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "package", catalog = "fmc")
public class Package implements java.io.Serializable {

	// Fields

	private Integer packageId;
	private Integer orderId;
	private Timestamp packageTime;

	// Constructors

	/** default constructor */
	public Package() {
	}

	/** minimal constructor */
	public Package(Integer orderId) {
		this.orderId = orderId;
	}

	/** full constructor */
	public Package(Integer orderId, Timestamp packageTime) {
		this.orderId = orderId;
		this.packageTime = packageTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "package_id", unique = true, nullable = false)
	public Integer getPackageId() {
		return this.packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	@Column(name = "order_id", nullable = false)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "package_time", length = 19)
	public Timestamp getPackageTime() {
		return this.packageTime;
	}

	public void setPackageTime(Timestamp packageTime) {
		this.packageTime = packageTime;
	}

}