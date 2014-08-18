package nju.software.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FabricCost entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fabric_cost", catalog = "newfmc")
public class FabricCost implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer orderId;
	private String fabricName;
	private Float tearPerMeter;
	private Float price;
	private Float costPerMeter;
    private Float tearPerMeterSampleAmountProduct;
    private Float tearPerMeterAskAmountProduct;
  	// Constructors

	/** default constructor */
	public FabricCost() {
	}

	/** full constructor */
	public FabricCost(Integer orderId, String fabricName, Float tearPerMeter,
			Float price, Float costPerMeter) {
		this.orderId = orderId;
		this.fabricName = fabricName;
		this.tearPerMeter = tearPerMeter;
		this.price = price;
		this.costPerMeter = costPerMeter;
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

	@Column(name = "tear_per_meter", nullable = false, precision = 12, scale = 0)
	public Float getTearPerMeter() {
		return this.tearPerMeter;
	}

	public void setTearPerMeter(Float tearPerMeter) {
		this.tearPerMeter = tearPerMeter;
	}

	@Column(name = "price", nullable = false, precision = 12, scale = 0)
	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Column(name = "cost_per_meter", nullable = false, precision = 12, scale = 0)
	public Float getCostPerMeter() {
		return this.costPerMeter;
	}

	public void setCostPerMeter(Float costPerMeter) {
		this.costPerMeter = costPerMeter;
	}



	public Float getTearPerMeterSampleAmountProduct() {
		return tearPerMeterSampleAmountProduct;
	}

	public void setTearPerMeterSampleAmountProduct(
			Float tearPerMeterSampleAmountProduct) {
		this.tearPerMeterSampleAmountProduct = tearPerMeterSampleAmountProduct;
	}

	public Float getTearPerMeterAskAmountProduct() {
		return tearPerMeterAskAmountProduct;
	}

	public void setTearPerMeterAskAmountProduct(
			Float tearPerMeterAskAmountProduct) {
		this.tearPerMeterAskAmountProduct = tearPerMeterAskAmountProduct;
	}

}