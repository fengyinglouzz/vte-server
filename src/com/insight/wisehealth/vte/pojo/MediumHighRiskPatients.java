package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MediumHighRiskPatients implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.lang.Integer patientId;
	private java.lang.String patientCode;
	private java.lang.String patientName;
	private java.lang.String patientAge;
	private java.lang.String patientSex;
	private java.lang.String patientNation;
	private java.lang.String patientIdCode;
	private java.lang.String patientHospitId;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") 
	private java.util.Date patientLastRiskDate;
	private java.lang.String patientNumber;
	private java.lang.String patientArea;
	private java.lang.String patientBed;
	private java.lang.String patientLastRiskTypeExplain;
	private java.lang.String assessmentResultExplain;
	private java.lang.Integer assessmentTypeId;
	private java.lang.String assessmentItemId;
	private java.lang.Integer doctorAdviceId;
	
	
	public java.lang.Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(java.lang.Integer patientId) {
		this.patientId = patientId;
	}
	public java.lang.String getPatientCode() {
		return patientCode;
	}
	public void setPatientCode(java.lang.String patientCode) {
		this.patientCode = patientCode;
	}
	public java.lang.String getPatientSex() {
		return patientSex;
	}
	public java.lang.String getPatientName() {
		return patientName;
	}
	public void setPatientName(java.lang.String patientName) {
		this.patientName = patientName;
	}
	public void setPatientSex(java.lang.String patientSex) {
		this.patientSex = patientSex;
	}
	public java.lang.String getPatientNation() {
		return patientNation;
	}
	public void setPatientNation(java.lang.String patientNation) {
		this.patientNation = patientNation;
	}
	public java.lang.String getPatientIdCode() {
		return patientIdCode;
	}
	public void setPatientIdCode(java.lang.String patientIdCode) {
		this.patientIdCode = patientIdCode;
	}
	public java.lang.String getPatientHospitId() {
		return patientHospitId;
	}
	public void setPatientHospitId(java.lang.String patientHospitId) {
		this.patientHospitId = patientHospitId;
	}
	public java.util.Date getPatientLastRiskDate() {
		return patientLastRiskDate;
	}
	public void setPatientLastRiskDate(java.util.Date patientLastRiskDate) {
		this.patientLastRiskDate = patientLastRiskDate;
	}
	public java.lang.String getPatientNumber() {
		return patientNumber;
	}
	public void setPatientNumber(java.lang.String patientNumber) {
		this.patientNumber = patientNumber;
	}
	public java.lang.String getPatientArea() {
		return patientArea;
	}
	public void setPatientArea(java.lang.String patientArea) {
		this.patientArea = patientArea;
	}
	public java.lang.String getPatientBed() {
		return patientBed;
	}
	public void setPatientBed(java.lang.String patientBed) {
		this.patientBed = patientBed;
	}
	public java.lang.String getPatientLastRiskTypeExplain() {
		return patientLastRiskTypeExplain;
	}
	public void setPatientLastRiskTypeExplain(
			java.lang.String patientLastRiskTypeExplain) {
		this.patientLastRiskTypeExplain = patientLastRiskTypeExplain;
	}
	
	public java.lang.String getAssessmentResultExplain() {
		return assessmentResultExplain;
	}
	public void setAssessmentResultExplain(java.lang.String assessmentResultExplain) {
		this.assessmentResultExplain = assessmentResultExplain;
	}
	public java.lang.Integer getAssessmentTypeId() {
		return assessmentTypeId;
	}
	public void setAssessmentTypeId(java.lang.Integer assessmentTypeId) {
		this.assessmentTypeId = assessmentTypeId;
	}
	public java.lang.String getAssessmentItemId() {
		return assessmentItemId;
	}
	public void setAssessmentItemId(java.lang.String assessmentItemId) {
		this.assessmentItemId = assessmentItemId;
	}
	public java.lang.Integer getDoctorAdviceId() {
		return doctorAdviceId;
	}
	public void setDoctorAdviceId(java.lang.Integer doctorAdviceId) {
		this.doctorAdviceId = doctorAdviceId;
	}
	public java.lang.String getPatientAge() {
		return patientAge;
	}
	public void setPatientAge(java.lang.String patientAge) {
		this.patientAge = patientAge;
	}

}
