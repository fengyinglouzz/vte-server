package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:风险评估实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbVteAssessment extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  
	 */
	private java.lang.Integer assessmentId;
	/**
	 *  
	 */
	private java.lang.Integer patientHospitId;
	/**
	 *  患者数据编码（系统自动生成）
	 */
	private java.lang.String patientCode;
	/**
	 *  风险评估阶段（字典）
	 */
	private java.lang.String assessmentStage;
	/**
	 *  风险评估类型（字典）
	 */
	private java.lang.String assessmentType;
	/**
	 *  风险评估项目（字典）
	 */
	private java.lang.String assessmentItem;
	/**
	 *  风险评估结果（字典）
	 */
	private java.lang.String assessmentResult;
	/**
	 *  风险评估分值（数）
	 */
	private java.lang.Integer assessmentScore;
	/**
	 *  
	 */
	private java.lang.String assessmentSelectData;
	/**
	 *  
	 */
	private java.lang.String assessmentFrom;
	/**
	 *  
	 */
	private java.lang.String assessmentStatus;
	/**
	 *  
	 */
	private java.lang.Integer assessmentIsTemp;
	//columns END

	//setter and getter START
	public void setAssessmentId(java.lang.Integer value) {
		this.assessmentId = value;
	}
	
	public java.lang.Integer getAssessmentId() {
		return this.assessmentId;
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
	public void setAssessmentStage(java.lang.String value) {
		this.assessmentStage = value;
	}
	
	public java.lang.String getAssessmentStage() {
		return this.assessmentStage;
	}
	public void setAssessmentType(java.lang.String value) {
		this.assessmentType = value;
	}
	
	public java.lang.String getAssessmentType() {
		return this.assessmentType;
	}
	public void setAssessmentItem(java.lang.String value) {
		this.assessmentItem = value;
	}
	
	public java.lang.String getAssessmentItem() {
		return this.assessmentItem;
	}
	public void setAssessmentResult(java.lang.String value) {
		this.assessmentResult = value;
	}
	
	public java.lang.String getAssessmentResult() {
		return this.assessmentResult;
	}
	public void setAssessmentScore(java.lang.Integer value) {
		this.assessmentScore = value;
	}
	
	public java.lang.Integer getAssessmentScore() {
		return this.assessmentScore;
	}
	public void setAssessmentSelectData(java.lang.String value) {
		this.assessmentSelectData = value;
	}
	
	public java.lang.String getAssessmentSelectData() {
		return this.assessmentSelectData;
	}
	public void setAssessmentFrom(java.lang.String value) {
		this.assessmentFrom = value;
	}
	
	public java.lang.String getAssessmentFrom() {
		return this.assessmentFrom;
	}
	public void setAssessmentStatus(java.lang.String value) {
		this.assessmentStatus = value;
	}
	
	public java.lang.String getAssessmentStatus() {
		return this.assessmentStatus;
	}
	public void setAssessmentIsTemp(java.lang.Integer value) {
		this.assessmentIsTemp = value;
	}
	
	public java.lang.Integer getAssessmentIsTemp() {
		return this.assessmentIsTemp;
	}
	//setter and getter END
}
