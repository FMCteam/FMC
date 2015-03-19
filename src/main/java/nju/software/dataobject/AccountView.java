package nju.software.dataobject;

import java.sql.Timestamp;

public class AccountView {
	private String accountId;
    private String userId;
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
    private Timestamp resetLinkFailTime;
     
	public AccountView() {
		
	}
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getBigAvatar() {
		return bigAvatar;
	}
	public void setBigAvatar(String bigAvatar) {
		this.bigAvatar = bigAvatar;
	}
	public String getSmallAvatar() {
		return smallAvatar;
	}
	public void setSmallAvatar(String smallAvatar) {
		this.smallAvatar = smallAvatar;
	}
	public String getPasswordCookieValue() {
		return passwordCookieValue;
	}
	public void setPasswordCookieValue(String passwordCookieValue) {
		this.passwordCookieValue = passwordCookieValue;
	}
	public Timestamp getPasswordCookieTime() {
		return passwordCookieTime;
	}
	public void setPasswordCookieTime(Timestamp passwordCookieTime) {
		this.passwordCookieTime = passwordCookieTime;
	}
	public String getValidataCode() {
		return validataCode;
	}
	public void setValidataCode(String validataCode) {
		this.validataCode = validataCode;
	}
	public Timestamp getResetLinkFailTime() {
		return resetLinkFailTime;
	}
	public void setResetLinkFailTime(Timestamp resetLinkFailTime) {
		this.resetLinkFailTime = resetLinkFailTime;
	}
    
	
	
}
