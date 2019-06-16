package com.insight.wisehealth.vte.loginpojo;

import java.io.Serializable;

public class LoginHospitalPojo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 *  组织机构ID
	 */
	private java.lang.Integer hospitalId;
	/**
	 *  组织机构名称
	 */
	private java.lang.String hospitalName;
	/**
	 *  组织机构编码(代表层级关系)
	 */
	private java.lang.String hospitalCode;
	
	
	public java.lang.Integer getHospitalId() {
		return hospitalId;
	}
	
	public void setHospitalId(java.lang.Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	public java.lang.String getHospitalName() {
		return hospitalName;
	}
	
	public void setHospitalName(java.lang.String hospitalName) {
		this.hospitalName = hospitalName;
	}
	
	public java.lang.String getHospitalCode() {
		return hospitalCode;
	}
	
	public void setHospitalCode(java.lang.String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	
	

}
