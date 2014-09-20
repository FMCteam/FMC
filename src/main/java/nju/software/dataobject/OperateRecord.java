package nju.software.dataobject;

import static javax.persistence.GenerationType.IDENTITY;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OperateRecord entity. @author weiheng
 * 毛衣操作任务
 */

@Entity
@Table(name = "operate_record", catalog = "fmc")
public class OperateRecord implements java.io.Serializable {

	// Fields

	private Integer operateId;
	private Integer orderId;//外键
	private String taskName;//环节名称
	private Timestamp operateTime;//完成时间
	private String operatePerson;//负责人
	private String operateRemark;//消息备注

	// Constructors

	/** default constructor */
	public OperateRecord() {
	}

	/** minimal constructor */
	public OperateRecord(Integer operateId, Integer orderId) {
		this.operateId = operateId;
		this.orderId = orderId;
	}

	/** full constructor */
	public OperateRecord(Integer operateId, Integer orderId, String taskName,
			Timestamp operateTime, String operatePerson, String operateRemark) {
		this.operateId = operateId;
		this.orderId = orderId;
		this.taskName = taskName;
		this.operateTime = operateTime;
		this.operatePerson = operatePerson;
		this.operateRemark = operateRemark;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "operate_id", unique = true, nullable = false)
	public Integer getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Integer operateId) {
		this.operateId = operateId;
	}
	
	@Column(name = "order_id", nullable = false)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "task_name")
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(name = "operate_time",length = 19)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "operate_person")
	public String getOperatePerson() {
		return this.operatePerson;
	}

	public void setOperatePerson(String operatePerson) {
		this.operatePerson = operatePerson;
	}
	
	@Column(name = "operate_remark")
	public String getOperateRemark() {
		return this.operateRemark;
	}

	public void setOperateRemark(String operateRemark) {
		this.operateRemark = operateRemark;
	}

}