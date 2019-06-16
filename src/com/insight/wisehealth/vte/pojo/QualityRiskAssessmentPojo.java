package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;


public class QualityRiskAssessmentPojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.lang.String department;
	private java.lang.String vteRiskAssessmentPatientSum;
	private java.lang.String oneDayVteRiskAssessmentRate;
	private java.lang.String middleHighRiskRate;
	public java.lang.String getDepartment() {
		return department;
	}
	public void setDepartment(java.lang.String department) {
		this.department = department;
	}
	public java.lang.String getVteRiskAssessmentPatientSum() {
		return vteRiskAssessmentPatientSum;
	}
	public void setVteRiskAssessmentPatientSum(
			java.lang.String vteRiskAssessmentPatientSum) {
		this.vteRiskAssessmentPatientSum = vteRiskAssessmentPatientSum;
	}
	public java.lang.String getOneDayVteRiskAssessmentRate() {
		return oneDayVteRiskAssessmentRate;
	}
	public void setOneDayVteRiskAssessmentRate(
			java.lang.String oneDayVteRiskAssessmentRate) {
		this.oneDayVteRiskAssessmentRate = oneDayVteRiskAssessmentRate;
	}
	public java.lang.String getMiddleHighRiskRate() {
		return middleHighRiskRate;
	}
	public void setMiddleHighRiskRate(java.lang.String middleHighRiskRate) {
		this.middleHighRiskRate = middleHighRiskRate;
	}
	
}
