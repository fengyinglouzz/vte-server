package com.insight.axiswevservice.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class VteBatchPatientPojo implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
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

	
	//setter and getter START
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

}
