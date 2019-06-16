package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:用户实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbSystemUser extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  用户id
	 */
	private java.lang.Integer userId;
	/**
	 *  医院id
	 */
	private java.lang.Integer hospitalId;
	/**
	 *  机构id
	 */
	private java.lang.Integer orgId;
	/**
	 *  用户账号
	 */
	private java.lang.String userAccount;
	/**
	 *  用户密码
	 */
	private java.lang.String userPassword;
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
	//columns END

	//setter and getter START
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public java.lang.Integer getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(java.lang.Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	public void setOrgId(java.lang.Integer value) {
		this.orgId = value;
	}
	
	public java.lang.Integer getOrgId() {
		return this.orgId;
	}
	public void setUserAccount(java.lang.String value) {
		this.userAccount = value;
	}
	
	public java.lang.String getUserAccount() {
		return this.userAccount;
	}
	public void setUserPassword(java.lang.String value) {
		this.userPassword = value;
	}
	
	public java.lang.String getUserPassword() {
		return this.userPassword;
	}
	public void setUserName(java.lang.String value) {
		this.userName = value;
	}
	
	public java.lang.String getUserName() {
		return this.userName;
	}
	public void setUserCode(java.lang.String value) {
		this.userCode = value;
	}
	
	public java.lang.String getUserCode() {
		return this.userCode;
	}
	public void setUserForm(java.lang.String value) {
		this.userForm = value;
	}
	
	public java.lang.String getUserForm() {
		return this.userForm;
	}
	//setter and getter END
}
