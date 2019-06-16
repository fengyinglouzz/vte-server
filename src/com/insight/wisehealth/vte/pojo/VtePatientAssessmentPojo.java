package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;

public class VtePatientAssessmentPojo  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	
	
	private java.lang.Integer patientHospitId;
	/**
	 *  患者住院号（系统自动生成）
	 */
	private java.lang.String patientCode;
	/**
	 *  患者姓名
	 */
	private java.lang.String patientName;
	/**
	 *  患者数据病案号（文）
	 */
	private java.lang.String patientNumber;
	/**
	 *  患者数据年龄（数）
	 */
	private java.lang.Integer patientAge;
	/**
	 *  患者数据性别
	 */
	private java.lang.String patientSex;
	/**
	 *  患者数据科室（文）
	 */
	private java.lang.String patientDepartment;
	/**
	 *  患者数据病区（文）
	 */
	private java.lang.String patientArea;
	/**
	 *  患者数据病床（文）
	 */
	private java.lang.String patientBed;
	/**
	 *  患者数据责任医生（文）
	 */
	private java.lang.String patientDoctor;
	/**
	 *  YTE风险评估等级（字典）
	 */
	private java.lang.String assessmentResult;
	/**
	 *  YTE风险评估等级（翻译）
	 */
	private java.lang.String assessmentResultExplain;
	/**
	 * 评估项完整度（文）
	 */
	private java.lang.String assessmentCompletionDegree;
	
	private java.util.List<VteAssessmentTypePojo> assessmentTypeList;
	//columns END

	public java.lang.Integer getPatientHospitId() {
		return patientHospitId;
	}

	public void setPatientHospitId(java.lang.Integer patientHospitId) {
		this.patientHospitId = patientHospitId;
	}

	public java.lang.String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(java.lang.String patientCode) {
		this.patientCode = patientCode;
	}

	public java.lang.String getPatientName() {
		return patientName;
	}

	public void setPatientName(java.lang.String patientName) {
		this.patientName = patientName;
	}

	public java.lang.String getPatientNumber() {
		return patientNumber;
	}

	public void setPatientNumber(java.lang.String patientNumber) {
		this.patientNumber = patientNumber;
	}

	public java.lang.Integer getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(java.lang.Integer patientAge) {
		this.patientAge = patientAge;
	}

	public java.lang.String getPatientSex() {
		return patientSex;
	}

	public void setPatientSex(java.lang.String patientSex) {
		this.patientSex = patientSex;
	}

	public java.lang.String getPatientDepartment() {
		return patientDepartment;
	}

	public void setPatientDepartment(java.lang.String patientDepartment) {
		this.patientDepartment = patientDepartment;
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

	public java.lang.String getPatientDoctor() {
		return patientDoctor;
	}

	public void setPatientDoctor(java.lang.String patientDoctor) {
		this.patientDoctor = patientDoctor;
	}

	public java.lang.String getAssessmentResult() {
		return assessmentResult;
	}

	public void setAssessmentResult(java.lang.String assessmentResult) {
		this.assessmentResult = assessmentResult;
	}

	public java.lang.String getAssessmentResultExplain() {
		return assessmentResultExplain;
	}

	public void setAssessmentResultExplain(java.lang.String assessmentResultExplain) {
		this.assessmentResultExplain = assessmentResultExplain;
	}

	public java.lang.String getAssessmentCompletionDegree() {
		return assessmentCompletionDegree;
	}

	public void setAssessmentCompletionDegree(
			java.lang.String assessmentCompletionDegree) {
		this.assessmentCompletionDegree = assessmentCompletionDegree;
	}

	public java.util.List<VteAssessmentTypePojo> getAssessmentTypeList() {
		return assessmentTypeList;
	}

	public void setAssessmentTypeList(
			java.util.List<VteAssessmentTypePojo> assessmentTypeList) {
		this.assessmentTypeList = assessmentTypeList;
	}
	
	//setter and getter START
	
}
