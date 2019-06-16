package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;
import java.util.List;

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
public class VteAssessmentDictTreePojo  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  风险评估项目（字典）
	 */
	private java.lang.String assessmentItem;
	
	private java.lang.String assessmentItemExplain;
	
    private List<VteAssessmentDictPojo> vteAssessmentDictPojoList;
	//setter and getter START
	
	public void setAssessmentItem(java.lang.String value) {
		this.assessmentItem = value;
	}
	
	public java.lang.String getAssessmentItem() {
		return this.assessmentItem;
	}

	public List<VteAssessmentDictPojo> getVteAssessmentDictPojoList() {
		return vteAssessmentDictPojoList;
	}

	public void setVteAssessmentDictPojoList(
			List<VteAssessmentDictPojo> vteAssessmentDictPojoList) {
		this.vteAssessmentDictPojoList = vteAssessmentDictPojoList;
	}

	public java.lang.String getAssessmentItemExplain() {
		return assessmentItemExplain;
	}

	public void setAssessmentItemExplain(java.lang.String assessmentItemExplain) {
		this.assessmentItemExplain = assessmentItemExplain;
	}
	
	
	
	//setter and getter END
}
