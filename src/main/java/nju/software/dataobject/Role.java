package nju.software.dataobject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ERole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role", schema = "fmc")
public class Role implements java.io.Serializable {

	// Fields

	private Integer roleId;
	private String name;
	private String description;
	private String status;
	private Date created;
	private Date lastmod;
	private String sort;
	private String creater;
	private String modifyer;

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** full constructor */
	public Role(String name, String description, String status, Date created,
			Date lastmod, String sort, String creater, String modifyer) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.created = created;
		this.lastmod = lastmod;
		this.sort = sort;
		this.creater = creater;
		this.modifyer = modifyer;
	}

	// Property accessors
//	@SequenceGenerator(name = "ERole_generator", allocationSize = 1, sequenceName = "ROLE_SEQ")
	@Id
	@GeneratedValue(strategy = IDENTITY)
//    @GeneratedValue(strategy = SEQUENCE, generator = "ERole_generator")
	@Column(name = "role_id", unique = true, nullable = false, precision = 11, scale = 0)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "name", length = 55)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "status")
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

	@Column(name = "sort", precision = 11, scale = 0)
	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Column(name = "creater", precision = 11, scale = 0)
	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Column(name = "modifyer", precision = 11, scale = 0)
	public String getModifyer() {
		return this.modifyer;
	}

	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}

}