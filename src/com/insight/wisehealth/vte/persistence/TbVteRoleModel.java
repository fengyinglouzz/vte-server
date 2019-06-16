package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:角色模块权限实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbVteRoleModel extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  
	 */
	private java.lang.Integer roleModelId;
	/**
	 *  模块id
	 */
	private java.lang.Integer modelId;
	/**
	 *  角色ID
	 */
	private java.lang.Integer roleId;
	//columns END

	//setter and getter START
	public void setRoleModelId(java.lang.Integer value) {
		this.roleModelId = value;
	}
	
	public java.lang.Integer getRoleModelId() {
		return this.roleModelId;
	}
	public void setModelId(java.lang.Integer value) {
		this.modelId = value;
	}
	
	public java.lang.Integer getModelId() {
		return this.modelId;
	}
	public void setRoleId(java.lang.Integer value) {
		this.roleId = value;
	}
	
	public java.lang.Integer getRoleId() {
		return this.roleId;
	}
	//setter and getter END
}
