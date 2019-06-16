package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class VtePatientPojo implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  
	 */
	private java.lang.Integer patientId;
	/**
	 *  患者住院号
	 */
	private java.lang.String patientCode;
	/**
	 *  患者数据姓名
	 */
	private java.lang.String patientName;
	/**
	 *  患者数据性别（文）
	 */
	private java.lang.String patientSex;
	/**
	 *  患者数据民族（文）
	 */
	private java.lang.String patientNation;
	/**
	 *  患者数据身份证号（文）
	 */
	private java.lang.String patientIdCode;

	/**
	 *  
	 */
	private java.lang.Integer patientHospitId;
	
	/**
	 *  患者数据入院时间（时间）
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private java.util.Date patientInHospital;
	/**
	 *  患者数据出院时间（时间）
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private java.util.Date patientOutHospital;
	/**
	 *  患者数据病案号（文）
	 */
	private java.lang.String patientNumber;
	/**
	 *  患者数据年龄（数）
	 */
	private java.lang.Integer patientAge;
	/**
	 *  患者数据籍贯（文）
	 */
	private java.lang.String patientNativePlace;
	/**
	 *  患者数据职业（文）
	 */
	private java.lang.String patientJob;
	/**
	 *  患者数据婚姻状况（文）
	 */
	private java.lang.String patientMarital;
	/**
	 *  患者数据手机号（文）
	 */
	private java.lang.String patientPhoneNumber;
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
	 *  患者数据是否完成VTE风险评估（文）
	 */
	private java.lang.String patientIsRisk;
	/**
	 *  患者数据是否完成VTE风险评估（文）
	 */
	private java.lang.String patientIsRiskExplain;
	/**
	 *  患者数据Caprini风险等级（字典）
	 */
	private java.lang.String patientCapriniGrade;
	/**
	 *  患者数据Caprini风险等级（字典）
	 */
	private java.lang.String patientCapriniGradeExplain;
	/**
	 *  患者数据Padua风险等级（字典）
	 */
	private java.lang.String patientPaduaGrade;
	/**
	 *  患者数据Padua风险等级（字典）
	 */
	private java.lang.String patientPaduaGradeExplain;
	/**
	 *  患者数据综合评分结果（字典）
	 */
	private java.lang.String patientSynGrade;
	/**
	 *  最后一次评估人
	 */
	private java.lang.String patientLastRiskUser;
	/**
	 *  患者数据最近一次评估时间（时间 ）
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private java.util.Date patientLastRiskDate;
	/**
	 *  患者数据数据来源（文）
	 */
	private java.lang.String patientFrom;

	
	//setter and getter START
	public void setPatientId(java.lang.Integer value) {
		this.patientId = value;
	}
	
	public java.lang.Integer getPatientId() {
		return this.patientId;
	}
	public void setPatientCode(java.lang.String value) {
		this.patientCode = value;
	}
	
	public java.lang.String getPatientCode() {
		return this.patientCode;
	}
	public void setPatientName(java.lang.String value) {
		this.patientName = value;
	}
	
	public java.lang.String getPatientName() {
		return this.patientName;
	}
	public void setPatientSex(java.lang.String value) {
		this.patientSex = value;
	}
	
	public java.lang.String getPatientSex() {
		return this.patientSex;
	}
	public void setPatientNation(java.lang.String value) {
		this.patientNation = value;
	}
	
	public java.lang.String getPatientNation() {
		return this.patientNation;
	}
	public void setPatientIdCode(java.lang.String value) {
		this.patientIdCode = value;
	}
	
	public java.lang.String getPatientIdCode() {
		return this.patientIdCode;
	}
	
	public void setPatientHospitId(java.lang.Integer value) {
		this.patientHospitId = value;
	}
	
	public java.lang.Integer getPatientHospitId() {
		return this.patientHospitId;
	}
	public void setPatientInHospital(java.util.Date value) {
		this.patientInHospital = value;
	}
	
	public java.util.Date getPatientInHospital() {
		return this.patientInHospital;
	}
	public void setPatientOutHospital(java.util.Date value) {
		this.patientOutHospital = value;
	}
	
	public java.util.Date getPatientOutHospital() {
		return this.patientOutHospital;
	}
	public void setPatientNumber(java.lang.String value) {
		this.patientNumber = value;
	}
	
	public java.lang.String getPatientNumber() {
		return this.patientNumber;
	}
	public void setPatientAge(java.lang.Integer value) {
		this.patientAge = value;
	}
	
	public java.lang.Integer getPatientAge() {
		return this.patientAge;
	}
	public void setPatientNativePlace(java.lang.String value) {
		this.patientNativePlace = value;
	}
	
	public java.lang.String getPatientNativePlace() {
		return this.patientNativePlace;
	}
	public void setPatientJob(java.lang.String value) {
		this.patientJob = value;
	}
	
	public java.lang.String getPatientJob() {
		return this.patientJob;
	}
	public void setPatientMarital(java.lang.String value) {
		this.patientMarital = value;
	}
	
	public java.lang.String getPatientMarital() {
		return this.patientMarital;
	}
	public void setPatientPhoneNumber(java.lang.String value) {
		this.patientPhoneNumber = value;
	}
	
	public java.lang.String getPatientPhoneNumber() {
		return this.patientPhoneNumber;
	}
	public void setPatientDepartment(java.lang.String value) {
		this.patientDepartment = value;
	}
	
	public java.lang.String getPatientDepartment() {
		return this.patientDepartment;
	}
	public void setPatientArea(java.lang.String value) {
		this.patientArea = value;
	}
	
	public java.lang.String getPatientArea() {
		return this.patientArea;
	}
	public void setPatientBed(java.lang.String value) {
		this.patientBed = value;
	}
	
	public java.lang.String getPatientBed() {
		return this.patientBed;
	}
	public void setPatientDoctor(java.lang.String value) {
		this.patientDoctor = value;
	}
	
	public java.lang.String getPatientDoctor() {
		return this.patientDoctor;
	}
	public void setPatientIsRisk(java.lang.String value) {
		this.patientIsRisk = value;
	}
	
	public java.lang.String getPatientIsRisk() {
		return this.patientIsRisk;
	}
	public void setPatientCapriniGrade(java.lang.String value) {
		this.patientCapriniGrade = value;
	}
	
	public java.lang.String getPatientCapriniGrade() {
		return this.patientCapriniGrade;
	}
	public void setPatientPaduaGrade(java.lang.String value) {
		this.patientPaduaGrade = value;
	}
	
	public java.lang.String getPatientPaduaGrade() {
		return this.patientPaduaGrade;
	}
	public void setPatientSynGrade(java.lang.String value) {
		this.patientSynGrade = value;
	}
	
	public java.lang.String getPatientSynGrade() {
		return this.patientSynGrade;
	}
	public void setPatientLastRiskUser(java.lang.String value) {
		this.patientLastRiskUser = value;
	}
	
	public java.lang.String getPatientLastRiskUser() {
		return this.patientLastRiskUser;
	}
	public void setPatientLastRiskDate(java.util.Date value) {
		this.patientLastRiskDate = value;
	}
	
	public java.util.Date getPatientLastRiskDate() {
		return this.patientLastRiskDate;
	}
	public void setPatientFrom(java.lang.String value) {
		this.patientFrom = value;
	}
	
	public java.lang.String getPatientFrom() {
		return this.patientFrom;
	}

	public java.lang.String getPatientIsRiskExplain() {
		return patientIsRiskExplain;
	}

	public void setPatientIsRiskExplain(java.lang.String patientIsRiskExplain) {
		this.patientIsRiskExplain = patientIsRiskExplain;
	}

	public java.lang.String getPatientCapriniGradeExplain() {
		return patientCapriniGradeExplain;
	}

	public void setPatientCapriniGradeExplain(
			java.lang.String patientCapriniGradeExplain) {
		this.patientCapriniGradeExplain = patientCapriniGradeExplain;
	}

	public java.lang.String getPatientPaduaGradeExplain() {
		return patientPaduaGradeExplain;
	}

	public void setPatientPaduaGradeExplain(
			java.lang.String patientPaduaGradeExplain) {
		this.patientPaduaGradeExplain = patientPaduaGradeExplain;
	}

}
