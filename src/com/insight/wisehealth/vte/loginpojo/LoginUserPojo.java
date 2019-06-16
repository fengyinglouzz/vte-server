package com.insight.wisehealth.vte.loginpojo;

import java.util.Date;
import java.util.List;

public class LoginUserPojo {
	
	private String sessionId;
	private String ip;
	private String macName;
	private String userAgent;//浏览器版本信息
	
	/**
	 *  用户id
	 */
	private java.lang.Integer userId;

	/**
	 *  用户账号
	 */
	private java.lang.String userAccount;

	/**
	 *  用户姓名
	 */
	private java.lang.String userName;
	
	/**
	 *  用户编码
	 */
	private java.lang.String userCode;
	
	/**
	 *  1：系统录入 2：医院接口传入
	 */
	private java.lang.String userForm;
	
	private LoginHospitalPojo loginHospitalPojo;
	
	private LoginRolePojo loginRolePojo;
	
	private LoginDepartmentPojo loginDepartmentPojo;
	
	private List<LoginModelPojo> loginModelPojoList;
	
	private List<LoginAssessmentDictTypePojo> loginAssessmentDictPojoList;
	
	private List<String> allowUrlList;
	
	private Date loginTime;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMacName() {
		return macName;
	}

	public void setMacName(String macName) {
		this.macName = macName;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public java.lang.Integer getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	public java.lang.String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(java.lang.String userAccount) {
		this.userAccount = userAccount;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.String getUserCode() {
		return userCode;
	}

	public void setUserCode(java.lang.String userCode) {
		this.userCode = userCode;
	}

	public java.lang.String getUserForm() {
		return userForm;
	}

	public void setUserForm(java.lang.String userForm) {
		this.userForm = userForm;
	}

	public LoginHospitalPojo getLoginHospitalPojo() {
		return loginHospitalPojo;
	}

	public void setLoginHospitalPojo(LoginHospitalPojo loginHospitalPojo) {
		this.loginHospitalPojo = loginHospitalPojo;
	}

	public LoginRolePojo getLoginRolePojo() {
		return loginRolePojo;
	}

	public void setLoginRolePojo(LoginRolePojo loginRolePojo) {
		this.loginRolePojo = loginRolePojo;
	}

	public LoginDepartmentPojo getLoginDepartmentPojo() {
		return loginDepartmentPojo;
	}

	public void setLoginDepartmentPojo(LoginDepartmentPojo loginDepartmentPojo) {
		this.loginDepartmentPojo = loginDepartmentPojo;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public List<LoginModelPojo> getLoginModelPojoList() {
		return loginModelPojoList;
	}

	public void setLoginModelPojoList(List<LoginModelPojo> loginModelPojoList) {
		this.loginModelPojoList = loginModelPojoList;
	}

	public List<LoginAssessmentDictTypePojo> getLoginAssessmentDictPojoList() {
		return loginAssessmentDictPojoList;
	}

	public void setLoginAssessmentDictPojoList(
			List<LoginAssessmentDictTypePojo> loginAssessmentDictPojoList) {
		this.loginAssessmentDictPojoList = loginAssessmentDictPojoList;
	}

	public List<String> getAllowUrlList() {
		return allowUrlList;
	}

	public void setAllowUrlList(List<String> allowUrlList) {
		this.allowUrlList = allowUrlList;
	}
	
	
}
