package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;


public class PreventiveRatePatientPojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.lang.String department;
	private java.lang.String medicinePreventiveRate;
	private java.lang.String mechanicalPreventiveRate;
	public java.lang.String getDepartment() {
		return department;
	}
	public void setDepartment(java.lang.String department) {
		this.department = department;
	}
	public java.lang.String getMedicinePreventiveRate() {
		return medicinePreventiveRate;
	}
	public void setMedicinePreventiveRate(java.lang.String medicinePreventiveRate) {
		this.medicinePreventiveRate = medicinePreventiveRate;
	}
	public java.lang.String getMechanicalPreventiveRate() {
		return mechanicalPreventiveRate;
	}
	public void setMechanicalPreventiveRate(
			java.lang.String mechanicalPreventiveRate) {
		this.mechanicalPreventiveRate = mechanicalPreventiveRate;
	}
	
}
