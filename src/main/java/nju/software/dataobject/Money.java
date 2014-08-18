package nju.software.dataobject;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Money entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "money", catalog = "newfmc")
public class Money implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer orderId;
	private String moneyType;
	private Double moneyAmount;
	private String moneyState;
	private String moneyName;
	private String moneyBank;
	private String moneyNumber;
	private String moneyRemark;
	private Timestamp receiveTime;
	private String receiveAccount;
	

	// Constructors

	@Column(name = "money_receive_time", nullable = false, length = 19)
	public Timestamp getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Timestamp receiveTime) {
		this.receiveTime = receiveTime;
	}

	@Column(name = "money_receive_account", nullable = false, length = 250)
	public String getReceiveAccount() {
		return receiveAccount;
	}

	public void setReceiveAccount(String receiveAccount) {
		this.receiveAccount = receiveAccount;
	}

	/** default constructor */
	public Money() {
	}

	/** minimal constructor */
	public Money(Integer orderId, String moneyType, Double moneyAmount,
			String moneyState) {
		this.orderId = orderId;
		this.moneyType = moneyType;
		this.moneyAmount = moneyAmount;
		this.moneyState = moneyState;
	}

	/** full constructor */
	public Money(Integer orderId, String moneyType, Double moneyAmount,
			String moneyState, String moneyName, String moneyBank,
			String moneyNumber, String moneyRemark) {
		this.orderId = orderId;
		this.moneyType = moneyType;
		this.moneyAmount = moneyAmount;
		this.moneyState = moneyState;
		this.moneyName = moneyName;
		this.moneyBank = moneyBank;
		this.moneyNumber = moneyNumber;
		this.moneyRemark = moneyRemark;
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

	@Column(name = "money_type", nullable = false, length = 250)
	public String getMoneyType() {
		return this.moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	@Column(name = "money_amount", nullable = false, precision = 22, scale = 0)
	public Double getMoneyAmount() {
		return this.moneyAmount;
	}

	public void setMoneyAmount(Double moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	@Column(name = "money_state", nullable = false, length = 45)
	public String getMoneyState() {
		return this.moneyState;
	}

	public void setMoneyState(String moneyState) {
		this.moneyState = moneyState;
	}

	@Column(name = "money_name", length = 45)
	public String getMoneyName() {
		return this.moneyName;
	}

	public void setMoneyName(String moneyName) {
		this.moneyName = moneyName;
	}

	@Column(name = "money_bank", length = 250)
	public String getMoneyBank() {
		return this.moneyBank;
	}

	public void setMoneyBank(String moneyBank) {
		this.moneyBank = moneyBank;
	}

	@Column(name = "money_number", length = 250)
	public String getMoneyNumber() {
		return this.moneyNumber;
	}

	public void setMoneyNumber(String moneyNumber) {
		this.moneyNumber = moneyNumber;
	}

	@Column(name = "money_remark", length = 250)
	public String getMoneyRemark() {
		return this.moneyRemark;
	}

	public void setMoneyRemark(String moneyRemark) {
		this.moneyRemark = moneyRemark;
	}

}