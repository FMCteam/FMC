package nju.software.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Quote entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "quote", catalog = "fmc")
public class Quote implements java.io.Serializable {

	// Fields

	private Integer orderId;
	private Float designCost = (float) 0; // 设计费用
	private Float cutCost = (float) 0; // 裁剪费用
	private Float manageCost = (float) 0; // 管理费用
	private Float swingCost = (float) 0; // 缝制费用
	private Float ironingCost = (float) 0; // 整烫费用
	private Float nailCost = (float) 0; // 锁订费用
	private Float packageCost = (float) 0; // 包装费用
	private Float otherCost = (float) 0; // 其他费用
	private Float fabricCost = (float) 0; // 面料费用
	private Float accessoryCost = (float) 0; // 辅料费用
	private Float singleCost = (float) 0; // 单件成本

	@Column(name = "fabric_cost", nullable = false, precision = 12, scale = 0)
	public Float getFabricCost() {
		return fabricCost;
	}

	public void setFabricCost(Float fabricCost) {
		this.fabricCost = fabricCost;
	}

	@Column(name = "accessory_cost", nullable = false, precision = 12, scale = 0)
	public Float getAccessoryCost() {
		return accessoryCost;
	}

	public void setAccessoryCost(Float accessoryCost) {
		this.accessoryCost = accessoryCost;
	}

	@Column(name = "single_cost", nullable = false, precision = 12, scale = 0)
	public Float getSingleCost() {
		return singleCost;
	}

	public void setSingleCost(Float singleCost) {
		this.singleCost = singleCost;
	}

	private Float profitPerPiece = (float) 0; // 单件利润
	private Float innerPrice = (float) 0; // 生产报价
	private Float outerPrice = (float) 0; // 外部报价

	// Constructors

	/** default constructor */
	public Quote() {
	}

	/** full constructor */
	public Quote(Integer orderId, Float designCost, Float cutCost,
			Float manageCost, Float swingCost, Float ironingCost,
			Float nailCost, Float packageCost, Float otherCost,
			Float profitPerPiece, Float innerPrice, Float outerPrice) {
		this.orderId = orderId;
		this.designCost = designCost;
		this.cutCost = cutCost;
		this.manageCost = manageCost;
		this.swingCost = swingCost;
		this.ironingCost = ironingCost;
		this.nailCost = nailCost;
		this.packageCost = packageCost;
		this.otherCost = otherCost;
		this.profitPerPiece = profitPerPiece;
		this.innerPrice = innerPrice;
		this.outerPrice = outerPrice;
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

	@Column(name = "design_cost", nullable = false, precision = 12, scale = 0)
	public Float getDesignCost() {
		return this.designCost;
	}

	public void setDesignCost(Float designCost) {
		this.designCost = designCost;
	}

	@Column(name = "cut_cost", nullable = false, precision = 12, scale = 0)
	public Float getCutCost() {
		return this.cutCost;
	}

	public void setCutCost(Float cutCost) {
		this.cutCost = cutCost;
	}

	@Column(name = "manage_cost", nullable = false, precision = 12, scale = 0)
	public Float getManageCost() {
		return this.manageCost;
	}

	public void setManageCost(Float manageCost) {
		this.manageCost = manageCost;
	}

	@Column(name = "swing_cost", nullable = false, precision = 12, scale = 0)
	public Float getSwingCost() {
		return this.swingCost;
	}

	public void setSwingCost(Float swingCost) {
		this.swingCost = swingCost;
	}

	@Column(name = "ironing_cost", nullable = false, precision = 12, scale = 0)
	public Float getIroningCost() {
		return this.ironingCost;
	}

	public void setIroningCost(Float ironingCost) {
		this.ironingCost = ironingCost;
	}

	@Column(name = "nail_cost", nullable = false, precision = 12, scale = 0)
	public Float getNailCost() {
		return this.nailCost;
	}

	public void setNailCost(Float nailCost) {
		this.nailCost = nailCost;
	}

	@Column(name = "package_cost", nullable = false, precision = 12, scale = 0)
	public Float getPackageCost() {
		return this.packageCost;
	}

	public void setPackageCost(Float packageCost) {
		this.packageCost = packageCost;
	}

	@Column(name = "other_cost", nullable = false, precision = 12, scale = 0)
	public Float getOtherCost() {
		return this.otherCost;
	}

	public void setOtherCost(Float otherCost) {
		this.otherCost = otherCost;
	}

	@Column(name = "profit_per_piece", nullable = false, precision = 12, scale = 0)
	public Float getProfitPerPiece() {
		return this.profitPerPiece;
	}

	public void setProfitPerPiece(Float profitPerPiece) {
		this.profitPerPiece = profitPerPiece;
	}

	@Column(name = "inner_price", nullable = false, precision = 12, scale = 0)
	public Float getInnerPrice() {
		return this.innerPrice;
	}

	public void setInnerPrice(Float innerPrice) {
		this.innerPrice = innerPrice;
	}

	@Column(name = "outer_price", nullable = false, precision = 12, scale = 0)
	public Float getOuterPrice() {
		return this.outerPrice;
	}

	public void setOuterPrice(Float outerPrice) {
		this.outerPrice = outerPrice;
	}

}