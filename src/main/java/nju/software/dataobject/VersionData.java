package nju.software.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VersionData entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "version_data", catalog = "fmc")
public class VersionData implements java.io.Serializable {

	// Fields

	private Integer vid;
	private Integer orderId;
	private String size;
	private String centerBackLength;
	private String bust;
	private String waistline;
	private String shoulder;
	private String buttock;
	private String hem;
	private String trousers;
	private String skirt;
	private String sleeves;

	// Constructors

	/** default constructor */
	public VersionData() {
	}

	/** minimal constructor */
	public VersionData(Integer orderId) {
		this.orderId = orderId;
	}

	/** full constructor */
	public VersionData(Integer orderId, String size, String centerBackLength,
			String bust, String waistline, String shoulder, String buttock,
			String hem, String trousers, String skirt, String sleeves) {
		this.orderId = orderId;
		this.size = size;
		this.centerBackLength = centerBackLength;
		this.bust = bust;
		this.waistline = waistline;
		this.shoulder = shoulder;
		this.buttock = buttock;
		this.hem = hem;
		this.trousers = trousers;
		this.skirt = skirt;
		this.sleeves = sleeves;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "vid", unique = true, nullable = false)
	public Integer getVid() {
		return this.vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	@Column(name = "order_id", nullable = false)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "size", length = 250)
	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Column(name = " center_back_length", length = 250)
	public String getCenterBackLength() {
		return this.centerBackLength;
	}

	public void setCenterBackLength(String centerBackLength) {
		this.centerBackLength = centerBackLength;
	}

	@Column(name = "bust", length = 250)
	public String getBust() {
		return this.bust;
	}

	public void setBust(String bust) {
		this.bust = bust;
	}

	@Column(name = "waistline", length = 250)
	public String getWaistline() {
		return this.waistline;
	}

	public void setWaistline(String waistline) {
		this.waistline = waistline;
	}

	@Column(name = "shoulder", length = 250)
	public String getShoulder() {
		return this.shoulder;
	}

	public void setShoulder(String shoulder) {
		this.shoulder = shoulder;
	}

	@Column(name = "buttock", length = 250)
	public String getButtock() {
		return this.buttock;
	}

	public void setButtock(String buttock) {
		this.buttock = buttock;
	}

	@Column(name = "hem", length = 250)
	public String getHem() {
		return this.hem;
	}

	public void setHem(String hem) {
		this.hem = hem;
	}

	@Column(name = "trousers", length = 250)
	public String getTrousers() {
		return this.trousers;
	}

	public void setTrousers(String trousers) {
		this.trousers = trousers;
	}

	@Column(name = "skirt", length = 250)
	public String getSkirt() {
		return this.skirt;
	}

	public void setSkirt(String skirt) {
		this.skirt = skirt;
	}

	@Column(name = "sleeves", length = 250)
	public String getSleeves() {
		return this.sleeves;
	}

	public void setSleeves(String sleeves) {
		this.sleeves = sleeves;
	}

}