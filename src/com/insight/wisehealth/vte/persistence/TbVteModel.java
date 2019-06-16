package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:模块表实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbVteModel extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  
	 */
	private java.lang.Integer modelId;
	/**
	 *  
	 */
	private java.lang.String modelCode;
	/**
	 *  
	 */
	private java.lang.String modelName;
	/**
	 *  允许访问地址
	 */
	private java.lang.String modelAllowUrl;
	//columns END

	//setter and getter START
	public void setModelId(java.lang.Integer value) {
		this.modelId = value;
	}
	
	public java.lang.Integer getModelId() {
		return this.modelId;
	}
	public void setModelCode(java.lang.String value) {
		this.modelCode = value;
	}
	
	public java.lang.String getModelCode() {
		return this.modelCode;
	}
	public void setModelName(java.lang.String value) {
		this.modelName = value;
	}
	
	public java.lang.String getModelName() {
		return this.modelName;
	}
	public void setModelAllowUrl(java.lang.String value) {
		this.modelAllowUrl = value;
	}
	
	public java.lang.String getModelAllowUrl() {
		return this.modelAllowUrl;
	}
	//setter and getter END
}
