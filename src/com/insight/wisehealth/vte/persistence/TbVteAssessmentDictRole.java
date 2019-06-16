package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:角色-评分数据字典关联表实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbVteAssessmentDictRole extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  
	 */
	private java.lang.Integer assessmentDictRoleId;
	/**
	 *  角色ID
	 */
	private java.lang.Integer roleId;
	/**
	 *  
	 */
	private java.lang.Integer assessmentDictId;
	//columns END

	//setter and getter START
	public void setAssessmentDictRoleId(java.lang.Integer value) {
		this.assessmentDictRoleId = value;
	}
	
	public java.lang.Integer getAssessmentDictRoleId() {
		return this.assessmentDictRoleId;
	}
	public void setRoleId(java.lang.Integer value) {
		this.roleId = value;
	}
	
	public java.lang.Integer getRoleId() {
		return this.roleId;
	}
	public void setAssessmentDictId(java.lang.Integer value) {
		this.assessmentDictId = value;
	}
	
	public java.lang.Integer getAssessmentDictId() {
		return this.assessmentDictId;
	}
	//setter and getter END
}
