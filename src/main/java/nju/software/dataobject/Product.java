package nju.software.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Product entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "product", catalog = "fmc")
public class Product implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7404850952767634542L;
	private Integer id;
	private Integer orderId;
	private String style;
	private String color;
	private Integer askAmount;
	private Integer produceAmount;
	private Integer qualifiedAmount;
	
	//款式类型。
	public static enum StyleType {
		XS,
		S,
		M,
		L,
		XL,
		XLL
	}
	// Constructors

	/** default constructor */
	public Product() {
	}

	/** full constructor */
	public Product(Integer orderId, String style, String color,
			Integer askAmount, Integer produceAmount, Integer qualifiedAmount) {
		this.orderId = orderId;
		this.style = style;
		this.color = color;
		this.askAmount = askAmount;
		this.produceAmount = produceAmount;
		this.qualifiedAmount = qualifiedAmount;
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

	@Column(name = "style", nullable = false, length = 250)
	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@Column(name = "color", nullable = false, length = 250)
	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name = "ask_amount", nullable = false)
	public Integer getAskAmount() {
		return this.askAmount;
	}

	public void setAskAmount(Integer askAmount) {
		this.askAmount = askAmount;
	}

	@Column(name = "produce_amount", nullable = false)
	public Integer getProduceAmount() {
		return this.produceAmount;
	}

	public void setProduceAmount(Integer produceAmount) {
		this.produceAmount = produceAmount;
	}

	@Column(name = "qualified_amount", nullable = false)
	public Integer getQualifiedAmount() {
		return this.qualifiedAmount;
	}

	public void setQualifiedAmount(Integer qualifiedAmount) {
		this.qualifiedAmount = qualifiedAmount;
	}

}