package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;

public class VteRiskScoreCodePojo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private java.lang.String assessmentResult;
	
	private java.lang.String assessmentResultExplain;
	
	private java.lang.Integer assessmentScore;

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

	public java.lang.Integer getAssessmentScore() {
		return assessmentScore;
	}

	public void setAssessmentScore(java.lang.Integer assessmentScore) {
		this.assessmentScore = assessmentScore;
	}
	
	
}
