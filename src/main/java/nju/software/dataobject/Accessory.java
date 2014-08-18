package nju.software.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Accessory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "accessory", catalog = "fmc")
public class Accessory implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer orderId;
	private String accessoryName;
	private String accessoryQuery;

	// Constructors

	/** default constructor */
	public Accessory() {
	}

	/** full constructor */
	public Accessory(Integer orderId, String accessoryName,
			String accessoryQuery) {
		this.orderId = orderId;
		this.accessoryName = accessoryName;
		this.accessoryQuery = accessoryQuery;
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

	@Column(name = "accessory_query", nullable = false, length = 250)
	public String getAccessoryQuery() {
		return this.accessoryQuery;
	}

	public void setAccessoryQuery(String accessoryQuery) {
		this.accessoryQuery = accessoryQuery;
	}

}