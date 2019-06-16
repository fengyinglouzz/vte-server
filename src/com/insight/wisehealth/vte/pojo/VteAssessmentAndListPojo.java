package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;
import java.util.List;

public class VteAssessmentAndListPojo implements Serializable {
	private static final long serialVersionUID = 1L;
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
	 *  医嘱结果
	 */
	private java.lang.String doctorAdviceResult;
	/**
	 *  医嘱结果(翻译)
	 */
	private java.lang.String doctorAdviceResultExplain;
	/**
	 * 风险处理措施
	 */
	private java.lang.String doctorAdviceRisk;
	/**
	 * 风险处理措施(翻译)
	 */
	private java.lang.String doctorAdviceRiskExplain;
	/**
	 * 医嘱处理医嘱
	 */
	private java.lang.String doctorAdviceCent;
	
	/**
	 *  得分
	 */
	private java.lang.Integer assessmentScore;
	/**
	 *  选中数据
	 */
	private java.lang.String assessmentSelectData;
	/**
	 * 根据得分不同分开选中数据
	 */
	private java.util.Map<String,List<String>> 	selectData;
	
	
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
	
	public java.lang.String getDoctorAdviceCent() {
		return doctorAdviceCent;
	}
	public void setDoctorAdviceCent(java.lang.String doctorAdviceCent) {
		this.doctorAdviceCent = doctorAdviceCent;
	}
	public java.lang.Integer getAssessmentScore() {
		return assessmentScore;
	}
	public void setAssessmentScore(java.lang.Integer assessmentScore) {
		this.assessmentScore = assessmentScore;
	}
	public java.lang.String getAssessmentSelectData() {
		return assessmentSelectData;
	}
	public void setAssessmentSelectData(java.lang.String assessmentSelectData) {
		this.assessmentSelectData = assessmentSelectData;
	}
	public java.util.Map<String, List<String>> getSelectData() {
		return selectData;
	}
	public void setSelectData(java.util.Map<String, List<String>> selectData) {
		this.selectData = selectData;
	}
	
}
