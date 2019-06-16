package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:用户角色实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbSystemUserRole extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  
	 */
	private java.lang.Integer userRoleId;
	/**
	 *  角色ID
	 */
	private java.lang.Integer roleId;
	/**
	 *  
	 */
	private java.lang.Integer userId;
	//columns END

	//setter and getter START
	public void setUserRoleId(java.lang.Integer value) {
		this.userRoleId = value;
	}
	
	public java.lang.Integer getUserRoleId() {
		return this.userRoleId;
	}
	public void setRoleId(java.lang.Integer value) {
		this.roleId = value;
	}
	
	public java.lang.Integer getRoleId() {
		return this.roleId;
	}
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	//setter and getter END
}
