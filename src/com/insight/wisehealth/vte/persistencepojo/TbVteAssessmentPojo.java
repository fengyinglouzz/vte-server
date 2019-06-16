package com.insight.wisehealth.vte.persistencepojo;

import java.io.Serializable;

import com.insight.wisehealth.vte.persistence.TbVteAssessment;

public class TbVteAssessmentPojo extends TbVteAssessment  implements Serializable{
	
	/**
	 *  风险评估阶段（字典）
	 */
	private java.lang.String assessmentStageExplain;
	/**
	 *  风险评估类型（字典）
	 */
	private java.lang.String assessmentTypeExplain;
	/**
	 *  风险评估项目（字典）
	 */
	private java.lang.String assessmentItemExplain;
	/**
	 *  风险评估结果（字典）
	 */
	private java.lang.String assessmentResultExplain;
	/**
	 *  
	 */
	private java.lang.String assessmentSelectDataExplain;
	public java.lang.String getAssessmentStageExplain() {
		return assessmentStageExplain;
	}
	public void setAssessmentStageExplain(java.lang.String assessmentStageExplain) {
		this.assessmentStageExplain = assessmentStageExplain;
	}
	public java.lang.String getAssessmentTypeExplain() {
		return assessmentTypeExplain;
	}
	public void setAssessmentTypeExplain(java.lang.String assessmentTypeExplain) {
		this.assessmentTypeExplain = assessmentTypeExplain;
	}
	public java.lang.String getAssessmentItemExplain() {
		return assessmentItemExplain;
	}
	public void setAssessmentItemExplain(java.lang.String assessmentItemExplain) {
		this.assessmentItemExplain = assessmentItemExplain;
	}
	public java.lang.String getAssessmentResultExplain() {
		return assessmentResultExplain;
	}
	public void setAssessmentResultExplain(java.lang.String assessmentResultExplain) {
		this.assessmentResultExplain = assessmentResultExplain;
	}
	public java.lang.String getAssessmentSelectDataExplain() {
		return assessmentSelectDataExplain;
	}
	public void setAssessmentSelectDataExplain(
			java.lang.String assessmentSelectDataExplain) {
		this.assessmentSelectDataExplain = assessmentSelectDataExplain;
	}
	
	
}
