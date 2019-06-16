package com.insight.axiswevservice.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BatchSynAssessmentPojo extends BatchPatientHospitInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.String  customerPublicKeyCode;
	
	private java.lang.String  assessmentUser;
	
	private java.util.Date  assessmentTime;
	
	private java.lang.String  assessmentStage;
	
	private java.lang.String  assessmentType;
	
	private java.lang.String  assessmentItem;
	
	private java.lang.String[]  assessmentSelectData;

	
	
	

	public java.lang.String getCustomerPublicKeyCode() {
		return customerPublicKeyCode;
	}

	public void setCustomerPublicKeyCode(java.lang.String customerPublicKeyCode) {
		this.customerPublicKeyCode = customerPublicKeyCode;
	}

	public java.lang.String getAssessmentUser() {
		return assessmentUser;
	}

	public void setAssessmentUser(java.lang.String assessmentUser) {
		this.assessmentUser = assessmentUser;
	}

	public java.util.Date getAssessmentTime() {
		return assessmentTime;
	}

	public void setAssessmentTime(java.util.Date assessmentTime) {
		this.assessmentTime = assessmentTime;
	}

	public java.lang.String getAssessmentStage() {
		return assessmentStage;
	}

	public void setAssessmentStage(java.lang.String assessmentStage) {
		this.assessmentStage = assessmentStage;
	}

	public java.lang.String getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(java.lang.String assessmentType) {
		this.assessmentType = assessmentType;
	}
	
	public java.lang.String getAssessmentItem() {
		return assessmentItem;
	}

	public void setAssessmentItem(java.lang.String assessmentItem) {
		this.assessmentItem = assessmentItem;
	}

	public java.lang.String[] getAssessmentSelectData() {
		return assessmentSelectData;
	}

	public void setAssessmentSelectData(java.lang.String[] assessmentSelectData) {
		this.assessmentSelectData = assessmentSelectData;
	}
	
}
