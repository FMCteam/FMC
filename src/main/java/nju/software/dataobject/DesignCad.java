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
	private String cadFabric; //面料
	private String cadPackage; //装箱
	private String cadVersionData; //版型
	private String cadBox; //包装
	private String cadTech; //工艺
	private String cadOther; //其他
	private String cadSide;//制版人姓名
	private Timestamp completeTime;//制版完成时间

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

	@Column(name = "cad_url", nullable = true, length = 250)
	public String getCadUrl() {
		return this.cadUrl;
	}

	public void setCadUrl(String cadUrl) {
		this.cadUrl = cadUrl;
	}

	@Column(name = "cad_version", nullable = true)
	public Short getCadVersion() {
		return this.cadVersion;
	}

	public void setCadVersion(Short cadVersion) {
		this.cadVersion = cadVersion;
	}

	@Column(name = "upload_time", nullable = true, length = 19)
	public Timestamp getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Column(name = "cad_fabric")
	public String getCadFabric() {
		return cadFabric;
	}

	public void setCadFabric(String cadFabric) {
		this.cadFabric = cadFabric;
	}

	@Column(name = "cad_package")
	public String getCadPackage() {
		return cadPackage;
	}

	public void setCadPackage(String cadPackage) {
		this.cadPackage = cadPackage;
	}

	@Column(name = "cad_version_data")
	public String getCadVersionData() {
		return cadVersionData;
	}

	public void setCadVersionData(String cadVersionData) {
		this.cadVersionData = cadVersionData;
	}

	@Column(name = "cad_box")
	public String getCadBox() {
		return cadBox;
	}

	public void setCadBox(String cadBox) {
		this.cadBox = cadBox;
	}

	@Column(name = "cad_tech")
	public String getCadTech() {
		return cadTech;
	}

	public void setCadTech(String cadTech) {
		this.cadTech = cadTech;
	}

	@Column(name = "cad_other")
	public String getCadOther() {
		return cadOther;
	}

	public void setCadOther(String cadOther) {
		this.cadOther = cadOther;
	}
	@Column(name = "cad_side")
	public String getCadSide() {
		return cadSide;
	}

	public void setCadSide(String cadSide) {
		this.cadSide = cadSide;
	}

	@Column(name = "complete_time", nullable = true, length = 19)
	public Timestamp getCompleteTime() {
		return this.completeTime;
	}

	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
	}
}