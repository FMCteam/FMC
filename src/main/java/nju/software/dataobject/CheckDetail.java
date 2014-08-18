/**
 * 
 */
package nju.software.dataobject;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author luxiangfan 
 * 质检详情
 */
@Entity
@Table(name = "check_detail", catalog = "fmc")
public class CheckDetail {
	// Fields
	private Integer detailId;// 自增主键
	private Integer recordId;// 外键
	private String color;
	private Integer xs;
	private Integer s;
	private Integer m;
	private Integer l;
	private Integer xl;
	private Integer xxl;
	private Integer checkAmount;// 质检总数
	private String checkResult;// 质检结果，QUALIFIED或UNQUALIFIED

	// Constructors
	public CheckDetail() {
	}

	public CheckDetail(Integer detailId, Integer recordId, String color,
			Integer xs, Integer s, Integer m, Integer l, Integer xl,
			Integer xxl, Integer checkAmount, String checkResult) {
		super();
		this.detailId = detailId;
		this.recordId = recordId;
		this.color = color;
		this.xs = xs;
		this.s = s;
		this.m = m;
		this.l = l;
		this.xl = xl;
		this.xxl = xxl;
		this.checkAmount = checkAmount;
		this.checkResult = checkResult;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "detail_id", unique = true, nullable = false)
	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	@Column(name = "record_id", nullable = false)
	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	@Column(name = "color", length = 45)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name = "XS")
	public Integer getXs() {
		return xs;
	}

	public void setXs(Integer xs) {
		this.xs = xs;
	}

	@Column(name = "S")
	public Integer getS() {
		return s;
	}

	public void setS(Integer s) {
		this.s = s;
	}

	@Column(name = "M")
	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	@Column(name = "L")
	public Integer getL() {
		return l;
	}

	public void setL(Integer l) {
		this.l = l;
	}

	@Column(name = "XL")
	public Integer getXl() {
		return xl;
	}

	public void setXl(Integer xl) {
		this.xl = xl;
	}

	@Column(name = "XXL")
	public Integer getXxl() {
		return xxl;
	}

	public void setXxl(Integer xxl) {
		this.xxl = xxl;
	}

	@Column(name = "check_amount")
	public Integer getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(Integer checkAmount) {
		this.checkAmount = checkAmount;
	}

	@Column(name = "check_result")
	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

}
