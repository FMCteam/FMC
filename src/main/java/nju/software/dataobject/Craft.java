package nju.software.dataobject;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Craft entity. @author whh
 */
@Entity
@Table(name = "craft", catalog = "fmc")
public class Craft implements Serializable {
	// Fields
    private Integer craftId;
 	private Integer orderId;
 
 	private Short needCraft;//0 not need craft 1 need craft
 	private float stampDutyMoney =  0;//印花费
 	private float washHangDyeMoney =  0;//水洗吊染费
 	private float laserMoney =  0;//激光费
 	private float embroideryMoney =  0;//刺绣费
 	private float crumpleMoney =  0;//压皱费
 	private float openVersionMoney =  0;//开版费
 	private String craftFileUrl;//工艺文件位置链接
 	private String orderSampleStatus;//订单工艺状态
 	private String craftLeader;//工艺负责人
 	private Timestamp completeTime;//工艺完成时间
 	private String crafsManName;//大货工艺负责人
 	private Timestamp crafsProduceDate;//大货工艺完成时间
	// Constructors

	/** default constructor */
	public Craft() {
	}

	/** full constructor */
	public Craft(Integer orderId, Short needCraft, float stampDutyMoney, float washHangDyeMoney,
			float laserMoney, float embroideryMoney, float crumpleMoney, float openVersionMoney,String craftFileUrl,String orderSampleStatus,String crafsManName,Timestamp crafsProduceDate ) {		
		this.orderId = orderId;
		this.needCraft = needCraft;
		this.stampDutyMoney = stampDutyMoney;
		this.washHangDyeMoney = washHangDyeMoney;
		this.laserMoney = laserMoney;
		this.embroideryMoney = embroideryMoney;
		this.crumpleMoney = crumpleMoney;
		this.openVersionMoney = openVersionMoney;
		this.craftFileUrl = craftFileUrl;
		this.orderSampleStatus=orderSampleStatus;
		this.crafsProduceDate=crafsProduceDate;
		this.crafsManName=crafsManName;
	} 	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "craft_id", unique = true, nullable = false) 	
	public Integer getCraftId() {
		return craftId;
	}

	public void setCraftId(Integer craftId) {
		this.craftId = craftId;
	}
	@Column(name = "order_id", nullable = false)	
	public Integer getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "need_craft", nullable = false)	
	public Short getNeedCraft() {
		return needCraft;
	}
	
	public void setNeedCraft(Short needCraft) {
		this.needCraft = needCraft;
	}
	
//	@Column(name = "logistics_state", columnDefinition="int default 0")
	@Column(name = "stamp_duty_money", nullable = true, precision = 22, scale = 0)	
	public float getStampDutyMoney() {
		return stampDutyMoney;
	}
	
	public void setStampDutyMoney(float stampDutyMoney) {
		this.stampDutyMoney = stampDutyMoney;
	}
	
	@Column(name = "wash_hang_dye_money", nullable = true, precision = 22, scale = 0)	
	public float getWashHangDyeMoney() {
		return washHangDyeMoney;
	}
	
	public void setWashHangDyeMoney(float washHangDyeMoney) {
		this.washHangDyeMoney = washHangDyeMoney;
	}
	
	@Column(name = "laser_money", nullable = true, precision = 22, scale = 0)		
	public float getLaserMoney() {
		return laserMoney;
	}
	
	public void setLaserMoney(float laserMoney) {
		this.laserMoney = laserMoney;
	}
	
	@Column(name = "embroidery_money", nullable = true, precision = 22, scale = 0)			
	public float getEmbroideryMoney() {
		return embroideryMoney;
	}
	
	public void setEmbroideryMoney(float embroideryMoney) {
		this.embroideryMoney = embroideryMoney;
	}
	
	@Column(name = "crumple_money", nullable = true, precision = 22, scale = 0)				
	public float getCrumpleMoney() {
		return crumpleMoney;
	}
	
	public void setCrumpleMoney(float crumpleMoney) {
		this.crumpleMoney = crumpleMoney;
	}

	@Column(name = "open_version_money", nullable = true, precision = 22, scale = 0)					
	public float getOpenVersionMoney() {
		return openVersionMoney;
	}
	
	public void setOpenVersionMoney(float openVersionMoney) {
		this.openVersionMoney = openVersionMoney;
	}

	@Column(name = "craft_file_url", nullable = true, length = 250)
	public String getCraftFileUrl() {
		return craftFileUrl;
	}
	public void setCraftFileUrl(String craftFileUrl) {
		this.craftFileUrl = craftFileUrl;
	}
	@Column(name = "order_sample_status", nullable = true, length = 250)
	public String getOrderSampleStatus() {
		return orderSampleStatus;
	}

	public void setOrderSampleStatus(String orderSampleStatus) {
		this.orderSampleStatus = orderSampleStatus;
	}
	

	@Column(name = "craft_leader")
	public String getCraftLeader() {
		return craftLeader;
	}

	public void setCraftLeader(String craftLeader) {
		this.craftLeader = craftLeader;
	}

	@Column(name = "complete_time", length = 19)
	public Timestamp getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
	}
	@Column(name = "crafts_manName")
	public String getCrafsManName() {
		return crafsManName;
	}

	public void setCrafsManName(String crafsManName) {
		this.crafsManName = crafsManName;
	}
	@Column(name = "crafts_productDate", length = 19)
	public Timestamp getCrafsProduceDate() {
		return crafsProduceDate;
	}

	public void setCrafsProduceDate(Timestamp crafsProduceDate) {
		this.crafsProduceDate = crafsProduceDate;
	}
	
	
}
