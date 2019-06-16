package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:角色实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbSystemRole extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  角色ID
	 */
	private java.lang.Integer roleId;
	/**
	 *  角色名称
	 */
	private java.lang.String roleName;
	/**
	 *  角色编码
	 */
	private java.lang.String roleCode;
	/**
	 *  角色数据权限（字典）
	 */
	private java.lang.String rolePower;
	//columns END

	//setter and getter START
	public void setRoleId(java.lang.Integer value) {
		this.roleId = value;
	}
	
	public java.lang.Integer getRoleId() {
		return this.roleId;
	}
	public void setRoleName(java.lang.String value) {
		this.roleName = value;
	}
	
	public java.lang.String getRoleName() {
		return this.roleName;
	}
	public void setRoleCode(java.lang.String value) {
		this.roleCode = value;
	}
	
	public java.lang.String getRoleCode() {
		return this.roleCode;
	}
	public void setRolePower(java.lang.String value) {
		this.rolePower = value;
	}
	
	public java.lang.String getRolePower() {
		return this.rolePower;
	}
	//setter and getter END
}
