package com.insight.wisehealth.vte.loginpojo;

import java.util.List;

public class LoginModelPojo {
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
	 * 子模块
	 */
	private List<LoginModelPojo> loginModelPojoList;

	public java.lang.Integer getModelId() {
		return modelId;
	}

	public void setModelId(java.lang.Integer modelId) {
		this.modelId = modelId;
	}

	public java.lang.String getModelCode() {
		return modelCode;
	}

	public void setModelCode(java.lang.String modelCode) {
		this.modelCode = modelCode;
	}

	public java.lang.String getModelName() {
		return modelName;
	}

	public void setModelName(java.lang.String modelName) {
		this.modelName = modelName;
	}

	public List<LoginModelPojo> getLoginModelPojoList() {
		return loginModelPojoList;
	}

	public void setLoginModelPojoList(List<LoginModelPojo> loginModelPojoList) {
		this.loginModelPojoList = loginModelPojoList;
	}
	

}
