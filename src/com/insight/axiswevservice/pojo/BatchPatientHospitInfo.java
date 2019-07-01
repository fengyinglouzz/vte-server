package com.insight.axiswevservice.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BatchPatientHospitInfo  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
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
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date patientInHospital;
	/**
	 *  患者数据出院时间（时间）
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
	public java.lang.String getPatientSex() {
		return patientSex;
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
	public java.util.Date getPatientInHospital() {
		return patientInHospital;
	}
	public void setPatientInHospital(java.util.Date patientInHospital) {
		this.patientInHospital = patientInHospital;
	}
	public java.util.Date getPatientOutHospital() {
		return patientOutHospital;
	}
	public void setPatientOutHospital(java.util.Date patientOutHospital) {
		this.patientOutHospital = patientOutHospital;
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
	public java.lang.String getPatientNativePlace() {
		return patientNativePlace;
	}
	public void setPatientNativePlace(java.lang.String patientNativePlace) {
		this.patientNativePlace = patientNativePlace;
	}
	public java.lang.String getPatientJob() {
		return patientJob;
	}
	public void setPatientJob(java.lang.String patientJob) {
		this.patientJob = patientJob;
	}
	public java.lang.String getPatientMarital() {
		return patientMarital;
	}
	public void setPatientMarital(java.lang.String patientMarital) {
		this.patientMarital = patientMarital;
	}
	public java.lang.String getPatientPhoneNumber() {
		return patientPhoneNumber;
	}
	public void setPatientPhoneNumber(java.lang.String patientPhoneNumber) {
		this.patientPhoneNumber = patientPhoneNumber;
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
	
	
	
}
