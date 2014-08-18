package nju.software.dataobject;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * Account entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="account"
    ,catalog="newfmc"
, uniqueConstraints = @UniqueConstraint(columnNames="user_name")
)

public class Account  implements java.io.Serializable {


    // Fields    

     private Integer accountId;
     private Integer userId;
     private String userType;
     private String userPassword;
     private String userName;
     private String userRole;
     private String nickName;
     private String bigAvatar;
     private String smallAvatar;
     private String passwordCookieValue;
     private Timestamp passwordCookieTime;
     private String validataCode;//找回密码验证密钥
     private Timestamp resetLinkFailTime;//重置密码链接失效时间


    /**
 	 * userType
 	 */
 	public static enum Type {
 		CUSTOMER,
 		EMPLOYEE,
 		ADMIN
 	}
 	
    // Constructors

    /** default constructor */
    public Account() {
    }

	/** minimal constructor */
    public Account(Integer userId, String userType, String userPassword, String userName, String userRole, String nickName) {
        this.userId = userId;
        this.userType = userType;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userRole = userRole;
        this.nickName = nickName;
    }
    
    /** full constructor */
    public Account(Integer userId, String userType, String userPassword, String userName, String userRole, String nickName, String bigAvatar, String smallAvatar, String samllAvatar, String passwordCookieValue, Timestamp passwordCookieTime) {
        this.userId = userId;
        this.userType = userType;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userRole = userRole;
        this.nickName = nickName;
        this.bigAvatar = bigAvatar;
        this.smallAvatar = smallAvatar;
        this.passwordCookieValue = passwordCookieValue;
        this.passwordCookieTime = passwordCookieTime;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="account_id", unique=true, nullable=false)

    public Integer getAccountId() {
        return this.accountId;
    }
    
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    
    @Column(name="user_id", nullable=false)

    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    @Column(name="user_type", nullable=false, length=250)

    public String getUserType() {
        return this.userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    @Column(name="user_password", nullable=false, length=250)

    public String getUserPassword() {
        return this.userPassword;
    }
    
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    
    @Column(name="user_name", unique=true, nullable=false, length=250)

    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Column(name="user_role", nullable=false, length=250)

    public String getUserRole() {
        return this.userRole;
    }
    
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    @Column(name="nick_name", nullable=false, length=250)

    public String getNickName() {
        return this.nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    @Column(name="big_avatar", length=250)

    public String getBigAvatar() {
        return this.bigAvatar;
    }
    
    public void setBigAvatar(String bigAvatar) {
        this.bigAvatar = bigAvatar;
    }
    
    @Column(name="small_avatar", length=250)

    public String getSmallAvatar() {
        return this.smallAvatar;
    }
    
    public void setSmallAvatar(String smallAvatar) {
        this.smallAvatar = smallAvatar;
    }
    

    @Column(name="password_cookie_value", length=250)
    public String getPasswordCookieValue() {
        return this.passwordCookieValue;
    }
    
    public void setPasswordCookieValue(String passwordCookieValue) {
        this.passwordCookieValue = passwordCookieValue;
    }
    
    @Column(name="password_cookie_time", length=19)
    public Timestamp getPasswordCookieTime() {
        return this.passwordCookieTime;
    }
    
    public void setPasswordCookieTime(Timestamp passwordCookieTime) {
        this.passwordCookieTime = passwordCookieTime;
    }

    @Column(name="validate_code", length=250)
	public String getValidataCode() {
		return validataCode;
	}

	public void setValidataCode(String validataCode) {
		this.validataCode = validataCode;
	}

	@Column(name="reset_link_fail_time")
	public Timestamp getResetLinkFailTime() {
		return resetLinkFailTime;
	}

	public void setResetLinkFailTime(Timestamp resetLinkFailTime) {
		this.resetLinkFailTime = resetLinkFailTime;
	}

}