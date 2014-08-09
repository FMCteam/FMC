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
 * @author luxiangfan 
 * 质检记录
 */
@Entity
@Table(name = "check_record", catalog = "fmc")
public class CheckRecord {
	// Fields
	private Integer recordId;
	private Integer orderId;// 外键
	private Integer qualifiedAmount;//合格数量
	private Integer repairAmount;// 回修数量（即每次质检不合格数量的总和）
	private Timestamp repairTime;// 回修时间
	private String repairSide;// 回修加工方
	private Integer invalidAmount;// 报废数量
	

	// Constructors
	public CheckRecord() {
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
	
	@Column(name = "qualified_amount")
	public Integer getQualifiedAmount() {
		return qualifiedAmount;
	}

	public void setQualifiedAmount(Integer qualifiedAmount) {
		this.qualifiedAmount = qualifiedAmount;
	}
	
	@Column(name = "repair_amount")
	public Integer getRepairAmount() {
		return repairAmount;
	}

	public void setRepairAmount(Integer repairAmount) {
		this.repairAmount = repairAmount;
	}

	@Column(name = "repair_time", length = 19)
	public Timestamp getRepairTime() {
		return repairTime;
	}

	public void setRepairTime(Timestamp repairTime) {
		this.repairTime = repairTime;
	}

	@Column(name = "repair_side")
	public String getRepairSide() {
		return repairSide;
	}

	public void setRepairSide(String repairSide) {
		this.repairSide = repairSide;
	}

	@Column(name = "invalid_amount")
	public Integer getInvalidAmount() {
		return invalidAmount;
	}

	public void setInvalidAmount(Integer invalidAmount) {
		this.invalidAmount = invalidAmount;
	}

}
