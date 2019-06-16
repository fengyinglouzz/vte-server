package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:患者数据表实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbVtePatient extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  
	 */
	private java.lang.Integer patientId;
	/**
	 *  患者住院号
	 */
	private java.lang.String patientCode;
	/**
	 *  患者数据姓名
	 */
	private java.lang.String patientName;
	/**
	 *  患者数据性别（文）
	 */
	private java.lang.String patientSex;
	/**
	 *  患者数据民族（文）
	 */
	private java.lang.String patientNation;
	/**
	 *  患者数据身份证号（文）
	 */
	private java.lang.String patientIdCode;
	//columns END

	//setter and getter START
	public void setPatientId(java.lang.Integer value) {
		this.patientId = value;
	}
	
	public java.lang.Integer getPatientId() {
		return this.patientId;
	}
	public void setPatientCode(java.lang.String value) {
		this.patientCode = value;
	}
	
	public java.lang.String getPatientCode() {
		return this.patientCode;
	}
	public void setPatientName(java.lang.String value) {
		this.patientName = value;
	}
	
	public java.lang.String getPatientName() {
		return this.patientName;
	}
	public void setPatientSex(java.lang.String value) {
		this.patientSex = value;
	}
	
	public java.lang.String getPatientSex() {
		return this.patientSex;
	}
	public void setPatientNation(java.lang.String value) {
		this.patientNation = value;
	}
	
	public java.lang.String getPatientNation() {
		return this.patientNation;
	}
	public void setPatientIdCode(java.lang.String value) {
		this.patientIdCode = value;
	}
	
	public java.lang.String getPatientIdCode() {
		return this.patientIdCode;
	}
	//setter and getter END
}
