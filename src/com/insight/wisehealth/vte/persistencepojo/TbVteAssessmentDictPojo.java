package com.insight.wisehealth.vte.persistencepojo;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
import com.insight.wisehealth.vte.persistence.TbVteAssessmentDict;
/**
 * 
 * 描述:评分数据项字典实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbVteAssessmentDictPojo extends TbVteAssessmentDict implements Serializable{
	//columns START
	/**
	 *  风险评估类型（字典）
	 */
	private java.lang.String assessmentTypeExplain;
	/**
	 *  风险评估项目（字典）
	 */
	private java.lang.String assessmentItemExplain;
	//columns END

	//setter and getter START
	
	public java.lang.String getAssessmentItemExplain() {
		return assessmentItemExplain;
	}
	public java.lang.String getAssessmentTypeExplain() {
		return assessmentTypeExplain;
	}
	public void setAssessmentTypeExplain(java.lang.String assessmentTypeExplain) {
		this.assessmentTypeExplain = assessmentTypeExplain;
	}
	public void setAssessmentItemExplain(java.lang.String assessmentItemExplain) {
		this.assessmentItemExplain = assessmentItemExplain;
	}
	
	
	//setter and getter END
}
