package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class VteAssessmentAndAdvicePojo  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 *  评估ID
	 */
	private java.lang.Integer assessmentId;
	/**
	 *  评估或者医嘱   ‘assessment’为评估，‘advice’为医嘱
	 */
	private java.lang.String modelName;
	/**
	 *  阶段
	 */
	private java.lang.String assessmentStage;
	/**
	 *  阶段(翻译)
	 */
	private java.lang.String assessmentStageExplain;
	/**
	 *  类型
	 */
	private java.lang.String assessmentType;
	/**
	 *  类型(翻译)
	 */
	private java.lang.String assessmentTypeExplain;
	/**
	 *  项目
	 */
	private java.lang.String assessmentItem;
	/**
	 *  项目(翻译)
	 */
	private java.lang.String assessmentItemExplain;
	/**
	 *  评估结果
	 */
	private java.lang.String assessmentResult;
	/**
	 *  评估结果(翻译)
	 */
	private java.lang.String assessmentResultExplain;
	/**
	 *  医嘱风险措施
	 */
	private java.lang.String doctorAdviceRisk;
	/**
	 *  医嘱风险措施(翻译)
	 */
	private java.lang.String doctorAdviceRiskExplain;
	/**
	 *  医嘱结果
	 */
	private java.lang.String doctorAdviceResult;
	/**
	 *  医嘱结果(翻译)
	 */
	private java.lang.String doctorAdviceResultExplain;
	/**
	 *  页面显示结果
	 */
	private java.lang.String resultExplain;
	/**
	 *  得分
	 */
	private java.lang.Integer assessmentScore;
	/**
	 *  创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private java.util.Date createDt;
	
	
	
	
	public java.lang.Integer getAssessmentId() {
		return assessmentId;
	}
	public void setAssessmentId(java.lang.Integer assessmentId) {
		this.assessmentId = assessmentId;
	}
	public java.lang.String getModelName() {
		return modelName;
	}
	public void setModelName(java.lang.String modelName) {
		this.modelName = modelName;
	}
	public java.lang.String getAssessmentStage() {
		return assessmentStage;
	}
	public void setAssessmentStage(java.lang.String assessmentStage) {
		this.assessmentStage = assessmentStage;
	}
	public java.lang.String getAssessmentStageExplain() {
		return assessmentStageExplain;
	}
	public void setAssessmentStageExplain(java.lang.String assessmentStageExplain) {
		this.assessmentStageExplain = assessmentStageExplain;
	}
	public java.lang.String getAssessmentType() {
		return assessmentType;
	}
	public void setAssessmentType(java.lang.String assessmentType) {
		this.assessmentType = assessmentType;
	}
	public java.lang.String getAssessmentTypeExplain() {
		return assessmentTypeExplain;
	}
	public void setAssessmentTypeExplain(java.lang.String assessmentTypeExplain) {
		this.assessmentTypeExplain = assessmentTypeExplain;
	}
	public java.lang.String getAssessmentItem() {
		return assessmentItem;
	}
	public void setAssessmentItem(java.lang.String assessmentItem) {
		this.assessmentItem = assessmentItem;
	}
	public java.lang.String getAssessmentItemExplain() {
		return assessmentItemExplain;
	}
	public void setAssessmentItemExplain(java.lang.String assessmentItemExplain) {
		this.assessmentItemExplain = assessmentItemExplain;
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
	
	public java.lang.String getDoctorAdviceRisk() {
		return doctorAdviceRisk;
	}
	public void setDoctorAdviceRisk(java.lang.String doctorAdviceRisk) {
		this.doctorAdviceRisk = doctorAdviceRisk;
	}
	public java.lang.String getDoctorAdviceRiskExplain() {
		return doctorAdviceRiskExplain;
	}
	public void setDoctorAdviceRiskExplain(java.lang.String doctorAdviceRiskExplain) {
		this.doctorAdviceRiskExplain = doctorAdviceRiskExplain;
	}
	public java.lang.String getDoctorAdviceResult() {
		return doctorAdviceResult;
	}
	public void setDoctorAdviceResult(java.lang.String doctorAdviceResult) {
		this.doctorAdviceResult = doctorAdviceResult;
	}
	public java.lang.String getDoctorAdviceResultExplain() {
		return doctorAdviceResultExplain;
	}
	public void setDoctorAdviceResultExplain(
			java.lang.String doctorAdviceResultExplain) {
		this.doctorAdviceResultExplain = doctorAdviceResultExplain;
	}
	public java.lang.String getResultExplain() {
		return resultExplain;
	}
	public void setResultExplain(java.lang.String resultExplain) {
		this.resultExplain = resultExplain;
	}
	public java.lang.Integer getAssessmentScore() {
		return assessmentScore;
	}
	public void setAssessmentScore(java.lang.Integer assessmentScore) {
		this.assessmentScore = assessmentScore;
	}
	public java.util.Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(java.util.Date createDt) {
		this.createDt = createDt;
	}
	@Override
	public String toString() {
		return "VteAssessmentAndAdvicePojo [assessmentId=" + assessmentId
				+ ", modelName=" + modelName + ", assessmentStage="
				+ assessmentStage + ", assessmentStageExplain="
				+ assessmentStageExplain + ", assessmentType=" + assessmentType
				+ ", assessmentTypeExplain=" + assessmentTypeExplain
				+ ", assessmentItem=" + assessmentItem
				+ ", assessmentItemExplain=" + assessmentItemExplain
				+ ", assessmentResult=" + assessmentResult
				+ ", assessmentResultExplain=" + assessmentResultExplain
				+ ", doctorAdviceResult=" + doctorAdviceResult
				+ ", doctorAdviceResultExplain=" + doctorAdviceResultExplain
				+ ", resultExplain=" + resultExplain + ", assessmentScore="
				+ assessmentScore + ", createDt=" + createDt + "]";
	}

	
	
	
	
	
}
