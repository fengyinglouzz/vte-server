package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;
import java.util.List;

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
public class VteModelTreePojo  implements Serializable{
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
	
	
	private List<VteModelTreePojo> childrenList;
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
	public List<VteModelTreePojo> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<VteModelTreePojo> childrenList) {
		this.childrenList = childrenList;
	}

	
	//setter and getter END


}
