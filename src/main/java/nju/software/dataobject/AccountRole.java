package nju.software.dataobject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * EUserRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "account_role", schema = "fmc")
public class AccountRole implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer accountId;
	private Integer roleId;
	private String status;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public AccountRole() {
	}

	/** full constructor */
	public AccountRole(Integer accountId, Integer roleId, String status, Date created,
			Date lastmod, Integer creater, Integer modifyer) {
		this.accountId = accountId;
		this.roleId = roleId;
		this.status = status;
		this.created = created;
		this.lastmod = lastmod;
		this.creater = creater;
		this.modifyer = modifyer;
	}

	// Property accessors
//	@SequenceGenerator(name = "EUserRole_generator", allocationSize = 1, sequenceName = "USERROLEID_SEQ")
	@Id
	@GeneratedValue(strategy = IDENTITY)
//    @GeneratedValue(strategy = SEQUENCE, generator = "EUserRole_generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 11, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "account_id", precision = 11, scale = 0)
	public Integer getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@Column(name = "role_id", precision = 11, scale = 0)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "status", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created", length = 7)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "lastmod", length = 7)
	public Date getLastmod() {
		return this.lastmod;
	}

	public void setLastmod(Date lastmod) {
		this.lastmod = lastmod;
	}

	@Column(name = "creater", precision = 11, scale = 0)
	public Integer getCreater() {
		return this.creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	@Column(name = "modifyer", precision = 11, scale = 0)
	public Integer getModifyer() {
		return this.modifyer;
	}

	public void setModifyer(Integer modifyer) {
		this.modifyer = modifyer;
	}

}