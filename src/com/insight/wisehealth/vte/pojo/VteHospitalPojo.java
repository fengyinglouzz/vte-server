package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;

public class VteHospitalPojo implements Serializable{
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
	
	private java.util.List<VteDepartmentPojo> vteDepartmentList;
	
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
	public java.util.List<VteDepartmentPojo> getVteDepartmentList() {
		return vteDepartmentList;
	}
	public void setVteDepartmentList(
			java.util.List<VteDepartmentPojo> vteDepartmentList) {
		this.vteDepartmentList = vteDepartmentList;
	}
	
	

}
