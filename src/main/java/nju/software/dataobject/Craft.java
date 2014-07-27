package nju.software.dataobject;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

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
 	private double stampDutyMoney =  0;//印花费
 	private double washHangDyeMoney =  0;//水洗吊染费
 	private double laserMoney =  0;//激光费
 	private double embroideryMoney =  0;//刺绣费
 	private double crumpleMoney =  0;//压皱费
 	private double openVersionMoney =  0;//开版费
 	private String craftFileUrl;//工艺文件位置链接
	// Constructors

	/** default constructor */
	public Craft() {
	}

	/** full constructor */
	public Craft(Integer orderId, Short needCraft, double stampDutyMoney, double washHangDyeMoney,
			double laserMoney, double embroideryMoney, double crumpleMoney, double openVersionMoney,String craftFileUrl) {		
		this.orderId = orderId;
		this.needCraft = needCraft;
		this.stampDutyMoney = stampDutyMoney;
		this.washHangDyeMoney = washHangDyeMoney;
		this.laserMoney = laserMoney;
		this.embroideryMoney = embroideryMoney;
		this.crumpleMoney = crumpleMoney;
		this.openVersionMoney = openVersionMoney;
		this.craftFileUrl = craftFileUrl;
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
	public Short isNeedCraft() {
		return needCraft;
	}
	
	public void setNeedCraft(Short needCraft) {
		this.needCraft = needCraft;
	}
//	@Column(name = "logistics_state", columnDefinition="int default 0")
	@Column(name = "stamp_duty_money", nullable = true, precision = 22, scale = 0)	
	public double getStampDutyMoney() {
		return stampDutyMoney;
	}
	
	public void setStampDutyMoney(double stampDutyMoney) {
		this.stampDutyMoney = stampDutyMoney;
	}
	
	@Column(name = "wash_hang_dye_money", nullable = true, precision = 22, scale = 0)	
	public double getWashHangDyeMoney() {
		return washHangDyeMoney;
	}
	
	public void setWashHangDyeMoney(double washHangDyeMoney) {
		this.washHangDyeMoney = washHangDyeMoney;
	}
	
	@Column(name = "laser_money", nullable = true, precision = 22, scale = 0)		
	public double getLaserMoney() {
		return laserMoney;
	}
	
	public void setLaserMoney(double laserMoney) {
		this.laserMoney = laserMoney;
	}
	
	@Column(name = "embroidery_money", nullable = true, precision = 22, scale = 0)			
	public double getEmbroideryMoney() {
		return embroideryMoney;
	}
	
	public void setEmbroideryMoney(double embroideryMoney) {
		this.embroideryMoney = embroideryMoney;
	}
	
	@Column(name = "crumple_money", nullable = true, precision = 22, scale = 0)				
	public double getCrumpleMoney() {
		return crumpleMoney;
	}
	
	public void setCrumpleMoney(double crumpleMoney) {
		this.crumpleMoney = crumpleMoney;
	}

	@Column(name = "open_version_money", nullable = true, precision = 22, scale = 0)					
	public double getOpenVersionMoney() {
		return openVersionMoney;
	}
	
	public void setOpenVersionMoney(double openVersionMoney) {
		this.openVersionMoney = openVersionMoney;
	}
	@Column(name = "craft_file_url", nullable = true, length = 250)

	public String getCraftFileUrl() {
		return craftFileUrl;
	}
	public void setCraftFileUrl(String craftFileUrl) {
		this.craftFileUrl = craftFileUrl;
	}

}
