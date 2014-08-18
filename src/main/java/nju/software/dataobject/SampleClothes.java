package nju.software.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SampleClothes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sample_clothes", catalog = "newfmc")
public class SampleClothes implements java.io.Serializable {

	// Fields

	private Integer sid;
	private Integer orderId;
	private String color;
	private String style;
	private String amount;
	private Float price;

	// Constructors

	/** default constructor */
	public SampleClothes() {
	}

	/** minimal constructor */
	public SampleClothes(Integer sid, Integer orderId) {
		this.sid = sid;
		this.orderId = orderId;
	}

	/** full constructor */
	public SampleClothes(Integer sid, Integer orderId, String color,
			String style, String amount, Float price) {
		this.sid = sid;
		this.orderId = orderId;
		this.color = color;
		this.style = style;
		this.amount = amount;
		this.price = price;
	}

	// Property accessors
	@Id
	@Column(name = "sid", unique = true, nullable = false)
	public Integer getSid() {
		return this.sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	@Column(name = "order_id", nullable = false)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "color", length = 250)
	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name = "style", length = 250)
	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@Column(name = "amount", length = 250)
	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Column(name = "price", precision = 12, scale = 0)
	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

}