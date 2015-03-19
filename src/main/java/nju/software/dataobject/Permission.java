package nju.software.dataobject;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "permission", catalog = "fmc")
public class Permission implements Serializable {

	// Fields

	private Integer permissionId;
	private Integer pid;//子权限对应父权限的permissionid
	private String name;
	private String pname;//子权限对应父权限的name
	private Integer sort;//同等级模组排序
	private String myid;//由cps移植过来，现表示任务的样式
	private String type;//是否可以有子节点
	private String isused;//是否启用
	private String state;
	private String url;
	private String iconcls;
	private String status;
	private String description;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public Permission() {
	}

	/** full constructor */
	public Permission(Integer pid, String name, String pname, Integer sort,
			String myid, String type, String isused, String state, String url,
			String iconcls, String status, String description, Date created,
			Date lastmod, Integer creater, Integer modifyer) {
		this.pid = pid;
		this.name = name;
		this.pname = pname;
		this.sort = sort;
		this.myid = myid;//由cps移植过来，现表示任务的样式
		this.type = type;
		this.isused = isused;
		this.state = state;
		this.url = url;
		this.iconcls = iconcls;
		this.status = status;
		this.description = description;
		this.created = created;
		this.lastmod = lastmod;
		this.creater = creater;
		this.modifyer = modifyer;
	}

	// Property accessors
//	@SequenceGenerator(name = "EPermission_generator",  allocationSize = 1,sequenceName = "PERMISSIONID_SEQ")
	@Id
	@GeneratedValue(strategy = IDENTITY)
//    @GeneratedValue(strategy = SEQUENCE, generator = "EPermission_generator")
	@Column(name = "PERMISSION_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public Integer getPermissionId() {
		return this.permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	@Column(name = "PID", precision = 11, scale = 0)
	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PNAME", length = 100)
	public String getPname() {
		return this.pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	@Column(name = "SORT", precision = 11, scale = 0)
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "MYID", length = 55)
	public String getMyid() {
		return this.myid;
	}

	public void setMyid(String myid) {
		this.myid = myid;
	}

	@Column(name = "TYPE")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "ISUSED")
	public String getIsused() {
		return this.isused;
	}

	public void setIsused(String isused) {
		this.isused = isused;
	}

	@Column(name = "STATE", length = 20)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "URL", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "ICONCLS", length = 100)
	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED", length = 7)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LASTMOD", length = 7)
	public Date getLastmod() {
		return this.lastmod;
	}

	public void setLastmod(Date lastmod) {
		this.lastmod = lastmod;
	}

	@Column(name = "CREATER", precision = 11, scale = 0)
	public Integer getCreater() {
		return this.creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	@Column(name = "MODIFYER", precision = 11, scale = 0)
	public Integer getModifyer() {
		return this.modifyer;
	}

	public void setModifyer(Integer modifyer) {
		this.modifyer = modifyer;
	}
}