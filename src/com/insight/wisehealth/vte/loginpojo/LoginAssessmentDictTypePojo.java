package com.insight.wisehealth.vte.loginpojo;

import java.util.List;

public class LoginAssessmentDictTypePojo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  风险评估项目（字典）
	 */
	private java.lang.String assessmentItem;
	
	private java.lang.String assessmentItemExplain;
	
    private List<LoginAssessmentDictPojo> loginAssessmentDictPojoList;

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

	public List<LoginAssessmentDictPojo> getLoginAssessmentDictPojoList() {
		return loginAssessmentDictPojoList;
	}

	public void setLoginAssessmentDictPojoList(
			List<LoginAssessmentDictPojo> loginAssessmentDictPojoList) {
		this.loginAssessmentDictPojoList = loginAssessmentDictPojoList;
	}
    
    
	
	
}
