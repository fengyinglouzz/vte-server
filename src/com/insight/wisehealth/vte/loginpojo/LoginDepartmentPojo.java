package com.insight.wisehealth.vte.loginpojo;

import java.io.Serializable;

public class LoginDepartmentPojo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 *  组织机构ID
	 */
	private java.lang.Integer departmentId;
	/**
	 *  组织机构名称
	 */
	private java.lang.String departmentName;
	/**
	 *  组织机构编码(代表层级关系)
	 */
	private java.lang.String departmentCode;
	
	
	public java.lang.Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(java.lang.Integer departmentId) {
		this.departmentId = departmentId;
	}
	public java.lang.String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(java.lang.String departmentName) {
		this.departmentName = departmentName;
	}
	public java.lang.String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(java.lang.String departmentCode) {
		this.departmentCode = departmentCode;
	}
	
	

}
