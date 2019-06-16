package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:DVT诊断流程建议实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbVteDvtAdvice extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  
	 */
	private java.lang.Integer dvtAdviceId;
	/**
	 *  
	 */
	private java.lang.Integer patientHospitId;
	/**
	 *  患者数据编码（系统自动生成）
	 */
	private java.lang.String patientCode;
	/**
	 *  DVT诊断流程建议所在环节（文）
	 */
	private java.lang.String dvtAdviceNodeName;
	/**
	 *  DVT诊断流程建议是否历史（字典）
	 */
	private java.lang.String dvtAdviceIshistory;
	//columns END

	//setter and getter START
	public void setDvtAdviceId(java.lang.Integer value) {
		this.dvtAdviceId = value;
	}
	
	public java.lang.Integer getDvtAdviceId() {
		return this.dvtAdviceId;
	}
	public void setPatientHospitId(java.lang.Integer value) {
		this.patientHospitId = value;
	}
	
	public java.lang.Integer getPatientHospitId() {
		return this.patientHospitId;
	}
	public void setPatientCode(java.lang.String value) {
		this.patientCode = value;
	}
	
	public java.lang.String getPatientCode() {
		return this.patientCode;
	}
	public void setDvtAdviceNodeName(java.lang.String value) {
		this.dvtAdviceNodeName = value;
	}
	
	public java.lang.String getDvtAdviceNodeName() {
		return this.dvtAdviceNodeName;
	}
	public void setDvtAdviceIshistory(java.lang.String value) {
		this.dvtAdviceIshistory = value;
	}
	
	public java.lang.String getDvtAdviceIshistory() {
		return this.dvtAdviceIshistory;
	}
	//setter and getter END
}
