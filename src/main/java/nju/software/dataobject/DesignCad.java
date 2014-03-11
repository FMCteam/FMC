package nju.software.dataobject;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DesignCad entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "design_cad", catalog = "fmc")
public class DesignCad implements java.io.Serializable {

	// Fields

	private Integer cadId;
	private Integer orderId;
	private String cadUrl;
	private Short cadVersion;
	private Timestamp uploadTime;

	// Constructors

	/** default constructor */
	public DesignCad() {
	}

	/** full constructor */
	public DesignCad(Integer orderId, String cadUrl, Short cadVersion,
			Timestamp uploadTime) {
		this.orderId = orderId;
		this.cadUrl = cadUrl;
		this.cadVersion = cadVersion;
		this.uploadTime = uploadTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "cad_id", unique = true, nullable = false)
	public Integer getCadId() {
		return this.cadId;
	}

	public void setCadId(Integer cadId) {
		this.cadId = cadId;
	}

	@Column(name = "order_id", nullable = false)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "cad_url", nullable = false, length = 250)
	public String getCadUrl() {
		return this.cadUrl;
	}

	public void setCadUrl(String cadUrl) {
		this.cadUrl = cadUrl;
	}

	@Column(name = "cad_version", nullable = false)
	public Short getCadVersion() {
		return this.cadVersion;
	}

	public void setCadVersion(Short cadVersion) {
		this.cadVersion = cadVersion;
	}

	@Column(name = "upload_time", nullable = false, length = 19)
	public Timestamp getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

}