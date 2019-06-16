package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;

public class VteAssessmentTypePojo  implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 *  患者住院号（系统自动生成）
	 */
	private java.lang.String assessmentTypeCode;
	/**
	 *  患者数据病案号（文）
	 */
	private java.lang.String assessmentTypeExplain;
	/**
	 *  患者数据年龄（数）
	 */
	private java.lang.Integer assessmentTypeValue;
	/**
	 *  横条样式
	 */
	private java.lang.String assessmentCls;
	
	
	public java.lang.String getAssessmentTypeCode() {
		return assessmentTypeCode;
	}
	public void setAssessmentTypeCode(java.lang.String assessmentTypeCode) {
		this.assessmentTypeCode = assessmentTypeCode;
	}
	public java.lang.String getAssessmentTypeExplain() {
		return assessmentTypeExplain;
	}
	public void setAssessmentTypeExplain(java.lang.String assessmentTypeExplain) {
		this.assessmentTypeExplain = assessmentTypeExplain;
	}
	public java.lang.Integer getAssessmentTypeValue() {
		return assessmentTypeValue;
	}
	public void setAssessmentTypeValue(java.lang.Integer assessmentTypeValue) {
		this.assessmentTypeValue = assessmentTypeValue;
	}
	public java.lang.String getAssessmentCls() {
		return assessmentCls;
	}
	public void setAssessmentCls(java.lang.String assessmentCls) {
		this.assessmentCls = assessmentCls;
	}
	
	
}
