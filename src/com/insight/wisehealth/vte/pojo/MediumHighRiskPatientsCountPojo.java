package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MediumHighRiskPatientsCountPojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.lang.Integer count;
	private java.lang.String patientDepartment;
	public java.lang.Integer getCount() {
		return count;
	}
	public void setCount(java.lang.Integer count) {
		this.count = count;
	}
	public java.lang.String getPatientDepartment() {
		return patientDepartment;
	}
	public void setPatientDepartment(java.lang.String patientDepartment) {
		this.patientDepartment = patientDepartment;
	}
	
}
