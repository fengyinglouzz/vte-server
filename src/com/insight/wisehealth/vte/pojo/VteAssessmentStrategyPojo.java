package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;

public class VteAssessmentStrategyPojo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 *  策略类型（用于前台是否弹窗及弹窗内容）
	 */
	private java.lang.String assessmentStrategyType;
	/**
	 *  策略名称
	 */
	private java.lang.String assessmentStrategyName;
	
	
	public java.lang.String getAssessmentStrategyType() {
		return assessmentStrategyType;
	}
	public void setAssessmentStrategyType(java.lang.String assessmentStrategyType) {
		this.assessmentStrategyType = assessmentStrategyType;
	}
	public java.lang.String getAssessmentStrategyName() {
		return assessmentStrategyName;
	}
	public void setAssessmentStrategyName(java.lang.String assessmentStrategyName) {
		this.assessmentStrategyName = assessmentStrategyName;
	}

	
}
