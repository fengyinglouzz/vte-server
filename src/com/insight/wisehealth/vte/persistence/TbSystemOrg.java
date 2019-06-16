package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:机构实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbSystemOrg extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  组织机构ID
	 */
	private java.lang.Integer orgId;
	/**
	 *  组织机构名称
	 */
	private java.lang.String orgName;
	/**
	 *  组织机构编码(代表层级关系)
	 */
	private java.lang.String orgCode;
	/**
	 *  组织机构联系人
	 */
	private java.lang.String orgContact;
	/**
	 *  '机构类型（manage：管理机构，hospital : 医院，department_n：科室(内)，department_w：科室(外)，department：科室，test：测试机构）
	 */
	private java.lang.String orgType;
	/**
	 *  组织机构电话
	 */
	private java.lang.String orgPhone;
	/**
	 *  组织机构邮箱
	 */
	private java.lang.String orgEmail;
	/**
	 *  组织机构地址
	 */
	private java.lang.String orgAddress;
	/**
	 *  组织机构是否是叶子（0：否，1：是）（字典）
	 */
	private java.lang.String orgLeaf;
	/**
	 *  组织机构字典版本号
	 */
	private java.lang.Integer orgVersion;
	//columns END

	//setter and getter START
	public void setOrgId(java.lang.Integer value) {
		this.orgId = value;
	}
	
	public java.lang.Integer getOrgId() {
		return this.orgId;
	}
	public void setOrgName(java.lang.String value) {
		this.orgName = value;
	}
	
	public java.lang.String getOrgName() {
		return this.orgName;
	}
	public void setOrgCode(java.lang.String value) {
		this.orgCode = value;
	}
	
	public java.lang.String getOrgCode() {
		return this.orgCode;
	}
	public void setOrgContact(java.lang.String value) {
		this.orgContact = value;
	}
	
	public java.lang.String getOrgContact() {
		return this.orgContact;
	}
	public void setOrgType(java.lang.String value) {
		this.orgType = value;
	}
	
	public java.lang.String getOrgType() {
		return this.orgType;
	}
	public void setOrgPhone(java.lang.String value) {
		this.orgPhone = value;
	}
	
	public java.lang.String getOrgPhone() {
		return this.orgPhone;
	}
	public void setOrgEmail(java.lang.String value) {
		this.orgEmail = value;
	}
	
	public java.lang.String getOrgEmail() {
		return this.orgEmail;
	}
	public void setOrgAddress(java.lang.String value) {
		this.orgAddress = value;
	}
	
	public java.lang.String getOrgAddress() {
		return this.orgAddress;
	}
	public void setOrgLeaf(java.lang.String value) {
		this.orgLeaf = value;
	}
	
	public java.lang.String getOrgLeaf() {
		return this.orgLeaf;
	}
	public void setOrgVersion(java.lang.Integer value) {
		this.orgVersion = value;
	}
	
	public java.lang.Integer getOrgVersion() {
		return this.orgVersion;
	}
	//setter and getter END
}
