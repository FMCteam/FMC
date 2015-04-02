package nju.software.dataobject;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 专员变更表
 * @author margine
 *
 */
@Entity
@Table(name = "marketstaff_alter", catalog = "fmc")
public class MarketstaffAlter {

	/**自增主键*/
	private Integer alterId;
	/**流程实例ID*/
	private String processId;
	/**申请专员ID*/
	private Integer employeeId;
	/**更换后的专员ID*/
	private Integer nextEmployeeId;
	/**审批结束时订单的当前任务*/
	private String currentOrderTaskName;
	/**申请订单ID*/
	private Integer orderId;
	/**申请时间*/
	private Timestamp applyTime;
	/**审批结束时间*/
	private Timestamp endTime;
	/**审批状态
	 * Todo表示待审批， Agree表示同意更换，Disagree表示拒绝更换
	 * */
	private String verifyState;
	
	public static final String STATE_TODO = "Todo";
	public static final String STATE_AGREE = "Agree";
	public static final String STATE_DISAGREE = "Disagree";
	
	/**
	 * default constructor
	 */
	public MarketstaffAlter(){
		
	}
	/**
	 * minimal constructor
	 */
	public MarketstaffAlter(Integer employeeId, Integer orderId, Timestamp applyTime){
		this.orderId = orderId;
		this.employeeId = employeeId;
		this.applyTime = applyTime;
	}
	
	/**
	 *full constructor 
	 */
	public MarketstaffAlter(String processId,
			Integer employeeId, Integer nextEmployeeId, Integer orderId, String currentOrderTaskName, Timestamp applyTime,
			Timestamp endTime, String verifyState) {
		super();
		this.processId = processId;
		this.employeeId = employeeId;
		this.nextEmployeeId = nextEmployeeId;
		this.currentOrderTaskName = currentOrderTaskName;
		this.orderId = orderId;
		this.applyTime = applyTime;
		this.endTime = endTime;
		this.verifyState = verifyState;
	}
	
	@Id
	@GeneratedValue(strategy= IDENTITY)
	@Column(name="alter_id", unique = true, nullable= false)
	public Integer getAlterId() {
		return alterId;
	}
	
	
	
	@Column(name="employee_id", nullable = false)
	public Integer getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	
	@Column(name="order_id", nullable = false)
	public Integer getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	@Column(name="apply_time")
	public Timestamp getApplyTime() {
		return applyTime;
	}
	
	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}
	
	@Column(name="end_time")
	public Timestamp getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	
	@Column(name="verify_state")
	public String getVerifyState() {
		return verifyState;
	}
	
	public void setVerifyState(String verifyState) {
		this.verifyState = verifyState;
	}
	
	@Column(name="process_id", nullable=false)
	public String getProcessId() {
		return processId;
	}
	
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	@Column(name="next_employee_id")
	public Integer getNextEmployeeId() {
		return nextEmployeeId;
	}
	
	public void setNextEmployeeId(Integer nextEmployeeId) {
		this.nextEmployeeId = nextEmployeeId;
	}

	@Column(name="current_order_task")
	public String getCurrentTaskName() {
		return currentOrderTaskName;
	}
	
	public void setCurrentTaskName(String currentTaskName) {
		this.currentOrderTaskName = currentTaskName;
	}
	public void setAlterId(Integer alterId) {
		this.alterId = alterId;
	}
	

}
