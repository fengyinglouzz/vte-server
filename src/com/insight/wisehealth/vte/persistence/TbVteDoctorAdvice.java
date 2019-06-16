package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:医嘱处理实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbVteDoctorAdvice extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  
	 */
	private java.lang.Integer doctorAdviceId;
	/**
	 *  
	 */
	private java.lang.Integer patientHospitId;
	/**
	 *  患者数据编码（系统自动生成）
	 */
	private java.lang.String patientCode;
	/**
	 *  医嘱处理诊断结果（字典）
	 */
	private java.lang.String doctorAdviceResult;
	/**
	 *  风险处理措施（字典）
	 */
	private java.lang.String doctorAdviceRisk;
	/**
	 *  医嘱处理医嘱（文）
	 */
	private java.lang.String doctorAdviceCent;
	/**
	 *  来源（字典）0为录入，1为导入 
	 */
	private java.lang.String doctorAdviceFrom;
	/**
	 *  
	 */
	private java.lang.Integer doctorAdviceIsTemp;
	//columns END

	//setter and getter START
	public void setDoctorAdviceId(java.lang.Integer value) {
		this.doctorAdviceId = value;
	}
	
	public java.lang.Integer getDoctorAdviceId() {
		return this.doctorAdviceId;
	}
	public void setPatientHospitId(java.lang.Integer value) {
		this.patientHospitId = value;
	}
	
	public java.lang.Integer getPatientHospitId() {
		return this.patientHospitId;
	}
	public void setPatientCode(java.lang.String value) {
		this.patientCode = value;
	}
	
	public java.lang.String getPatientCode() {
		return this.patientCode;
	}
	public void setDoctorAdviceResult(java.lang.String value) {
		this.doctorAdviceResult = value;
	}
	
	public java.lang.String getDoctorAdviceResult() {
		return this.doctorAdviceResult;
	}
	public void setDoctorAdviceRisk(java.lang.String value) {
		this.doctorAdviceRisk = value;
	}
	
	public java.lang.String getDoctorAdviceRisk() {
		return this.doctorAdviceRisk;
	}
	public void setDoctorAdviceCent(java.lang.String value) {
		this.doctorAdviceCent = value;
	}
	
	public java.lang.String getDoctorAdviceCent() {
		return this.doctorAdviceCent;
	}
	public void setDoctorAdviceFrom(java.lang.String value) {
		this.doctorAdviceFrom = value;
	}
	
	public java.lang.String getDoctorAdviceFrom() {
		return this.doctorAdviceFrom;
	}
	public void setDoctorAdviceIsTemp(java.lang.Integer value) {
		this.doctorAdviceIsTemp = value;
	}
	
	public java.lang.Integer getDoctorAdviceIsTemp() {
		return this.doctorAdviceIsTemp;
	}
	//setter and getter END
}
