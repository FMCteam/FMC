package nju.software.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AccessoryCost entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "accessory_cost", catalog = "fmc")
public class AccessoryCost implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer orderId;
	private String accessoryName;
	private Float tearPerPiece;
	private Float price;
	private Float costPerPiece;

	// Constructors

	/** default constructor */
	public AccessoryCost() {
	}

	/** full constructor */
	public AccessoryCost(Integer orderId, String accessoryName,
			Float tearPerPiece, Float price, Float costPerPiece) {
		this.orderId = orderId;
		this.accessoryName = accessoryName;
		this.tearPerPiece = tearPerPiece;
		this.price = price;
		this.costPerPiece = costPerPiece;
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

	@Column(name = "accessory_name", nullable = false, length = 250)
	public String getAccessoryName() {
		return this.accessoryName;
	}

	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}

	@Column(name = "tear_per_piece", nullable = false, precision = 12, scale = 0)
	public Float getTearPerPiece() {
		return this.tearPerPiece;
	}

	public void setTearPerPiece(Float tearPerPiece) {
		this.tearPerPiece = tearPerPiece;
	}

	@Column(name = "price", nullable = false, precision = 12, scale = 0)
	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Column(name = "cost_per_piece", nullable = false, precision = 12, scale = 0)
	public Float getCostPerPiece() {
		return this.costPerPiece;
	}

	public void setCostPerPiece(Float costPerPiece) {
		this.costPerPiece = costPerPiece;
	}

}