package com.insight.axiswevservice.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BatchSynDoctorAdvicePojo extends BatchPatientHospitInfo implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private java.lang.String  customerPublicKeyCode;
	
	
	
	private java.lang.String  assessmentUser;
	
	private java.util.Date  assessmentTime;
	
	private java.lang.String  doctorAdviceResult;
	
	private java.lang.String  doctorAdviceRisk;
	
	private java.lang.String  doctorAdviceCent;

	
	
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

	public java.lang.String getDoctorAdviceResult() {
		return doctorAdviceResult;
	}

	public void setDoctorAdviceResult(java.lang.String doctorAdviceResult) {
		this.doctorAdviceResult = doctorAdviceResult;
	}

	public java.lang.String getDoctorAdviceRisk() {
		return doctorAdviceRisk;
	}

	public void setDoctorAdviceRisk(java.lang.String doctorAdviceRisk) {
		this.doctorAdviceRisk = doctorAdviceRisk;
	}

	public java.lang.String getDoctorAdviceCent() {
		return doctorAdviceCent;
	}

	public void setDoctorAdviceCent(java.lang.String doctorAdviceCent) {
		this.doctorAdviceCent = doctorAdviceCent;
	}
	
	
}
