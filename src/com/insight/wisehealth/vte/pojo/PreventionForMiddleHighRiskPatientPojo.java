package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;


public class PreventionForMiddleHighRiskPatientPojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.lang.String department;
	private java.lang.String capriniMiddleRisk;
	private java.lang.String capriniHighRisk;
	private java.lang.String paduaHighRisk;
	public java.lang.String getDepartment() {
		return department;
	}
	public void setDepartment(java.lang.String department) {
		this.department = department;
	}
	public java.lang.String getCapriniMiddleRisk() {
		return capriniMiddleRisk;
	}
	public void setCapriniMiddleRisk(java.lang.String capriniMiddleRisk) {
		this.capriniMiddleRisk = capriniMiddleRisk;
	}
	public java.lang.String getCapriniHighRisk() {
		return capriniHighRisk;
	}
	public void setCapriniHighRisk(java.lang.String capriniHighRisk) {
		this.capriniHighRisk = capriniHighRisk;
	}
	public java.lang.String getPaduaHighRisk() {
		return paduaHighRisk;
	}
	public void setPaduaHighRisk(java.lang.String paduaHighRisk) {
		this.paduaHighRisk = paduaHighRisk;
	}
	
	
}
