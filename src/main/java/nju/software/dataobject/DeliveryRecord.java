/**
 * 
 */
package nju.software.dataobject;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author luxiangfan 样衣和大货的发货记录表（有多次发货）
 */
@Entity
@Table(name = "delivery_record", catalog = "newfmc")
public class DeliveryRecord implements java.io.Serializable {
	// Fields
	private Integer recordId;
	private Integer orderId;
	private String sendType;// 发货类型，sample表示样衣，product表示大货
	private String recipientName;// 收件人姓名
	private String recipientAddr;// 收件人地址
	private String recipientPhone;// 收件人手机
	private String senderName;// 发货人姓名
	private String expressName;// 快递名称
	private String expressNumber;// 快递单号
	private String expressPrice;// 快递价格
	private Timestamp sendTime;// 发货时间
	private String remark;// 发货备注

	// Constructors
	public DeliveryRecord() {
	}

	public DeliveryRecord(Integer orderId) {
		this.orderId = orderId;
	}

	public DeliveryRecord(Integer recordId, Integer orderId, String sendType,
			String recipientName, String recipientAddr, String recipientPhone,
			String senderName, String expressName, String expressNumber,
			String expressPrice, Timestamp sendTime, String remark) {
		super();
		this.recordId = recordId;
		this.orderId = orderId;
		this.sendType = sendType;
		this.recipientName = recipientName;
		this.recipientAddr = recipientAddr;
		this.recipientPhone = recipientPhone;
		this.senderName = senderName;
		this.expressName = expressName;
		this.expressNumber = expressNumber;
		this.expressPrice = expressPrice;
		this.sendTime = sendTime;
		this.remark = remark;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "record_id", unique = true, nullable = false)
	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	@Column(name = "order_id", nullable = false)
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "send_type", nullable = false, length = 250)
	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	@Column(name = "recipient_name", nullable = false, length = 250)
	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	@Column(name = "recipient_addr", nullable = false, length = 250)
	public String getRecipientAddr() {
		return recipientAddr;
	}

	public void setRecipientAddr(String recipientAddr) {
		this.recipientAddr = recipientAddr;
	}

	@Column(name = "recipient_phone", nullable = false, length = 250)
	public String getRecipientPhone() {
		return recipientPhone;
	}

	public void setRecipientPhone(String recipientPhone) {
		this.recipientPhone = recipientPhone;
	}

	@Column(name = "sender_name", length = 250)
	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	@Column(name = "express_name", nullable = false, length = 250)
	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	@Column(name = "express_number", nullable = false, length = 250)
	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	@Column(name = "express_price", length = 250)
	public String getExpressPrice() {
		return expressPrice;
	}

	public void setExpressPrice(String expressPrice) {
		this.expressPrice = expressPrice;
	}

	@Column(name = "send_time", length = 19)
	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	@Column(name = "remark", length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
