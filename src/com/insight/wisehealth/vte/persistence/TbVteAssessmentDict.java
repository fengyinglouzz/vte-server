package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:评分数据项字典实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbVteAssessmentDict extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  
	 */
	private java.lang.Integer assessmentDictId;
	/**
	 *  风险评估类型（字典）
	 */
	private java.lang.String assessmentType;
	/**
	 *  风险评估项目（字典）
	 */
	private java.lang.String assessmentItem;
	/**
	 *  评估字典编码（根据工程师自己定义）
	 */
	private java.lang.String assessmentDictCode;
	/**
	 *  
	 */
	private java.lang.String assessmentDictName;
	/**
	 *  
	 */
	private java.lang.Integer assessmentDictScore;
	/**
	 *  互斥条件 ｉｄ用逗号隔开（暂时无用预留）
	 */
	private java.lang.String assessmentDictMutex;
	//columns END

	//setter and getter START
	public void setAssessmentDictId(java.lang.Integer value) {
		this.assessmentDictId = value;
	}
	
	public java.lang.Integer getAssessmentDictId() {
		return this.assessmentDictId;
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
	public void setAssessmentDictCode(java.lang.String value) {
		this.assessmentDictCode = value;
	}
	
	public java.lang.String getAssessmentDictCode() {
		return this.assessmentDictCode;
	}
	public void setAssessmentDictName(java.lang.String value) {
		this.assessmentDictName = value;
	}
	
	public java.lang.String getAssessmentDictName() {
		return this.assessmentDictName;
	}
	public void setAssessmentDictScore(java.lang.Integer value) {
		this.assessmentDictScore = value;
	}
	
	public java.lang.Integer getAssessmentDictScore() {
		return this.assessmentDictScore;
	}
	public void setAssessmentDictMutex(java.lang.String value) {
		this.assessmentDictMutex = value;
	}
	
	public java.lang.String getAssessmentDictMutex() {
		return this.assessmentDictMutex;
	}
	//setter and getter END
}
