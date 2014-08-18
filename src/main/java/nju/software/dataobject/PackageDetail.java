package nju.software.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PackageDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "package_detail", catalog = "newfmc")
public class PackageDetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer packageId;
	private String clothesStyleName;
	private String clothesStyleColor;
	private Integer clothesAmount;

	// Constructors

	/** default constructor */
	public PackageDetail() {
	}

	/** full constructor */
	public PackageDetail(Integer packageId, String clothesStyleName,
			String clothesStyleColor, Integer clothesAmount) {
		this.packageId = packageId;
		this.clothesStyleName = clothesStyleName;
		this.clothesStyleColor = clothesStyleColor;
		this.clothesAmount = clothesAmount;
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

	@Column(name = "package_id", nullable = false)
	public Integer getPackageId() {
		return this.packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	@Column(name = "clothes_style_name", nullable = false, length = 250)
	public String getClothesStyleName() {
		return this.clothesStyleName;
	}

	public void setClothesStyleName(String clothesStyleName) {
		this.clothesStyleName = clothesStyleName;
	}

	@Column(name = "clothes_style_color", nullable = false, length = 250)
	public String getClothesStyleColor() {
		return this.clothesStyleColor;
	}

	public void setClothesStyleColor(String clothesStyleColor) {
		this.clothesStyleColor = clothesStyleColor;
	}

	@Column(name = "clothes_amount", nullable = false)
	public Integer getClothesAmount() {
		return this.clothesAmount;
	}

	public void setClothesAmount(Integer clothesAmount) {
		this.clothesAmount = clothesAmount;
	}

}