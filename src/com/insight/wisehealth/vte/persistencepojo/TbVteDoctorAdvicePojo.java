package com.insight.wisehealth.vte.persistencepojo;

import java.io.Serializable;

import com.insight.wisehealth.vte.persistence.TbVteDoctorAdvice;
/**
 * 
 * 描述:医嘱处理实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbVteDoctorAdvicePojo extends TbVteDoctorAdvice implements Serializable{
	//columns START
	/**
	 *  医嘱处理诊断结果（字典）
	 */
	private java.lang.String doctorAdviceResultExplain;
	/**
	 *  风险处理措施（字典）
	 */
	private java.lang.String doctorAdviceRiskExplain;
	//columns END
	

	//setter and getter START
	public java.lang.String getDoctorAdviceResultExplain() {
		return doctorAdviceResultExplain;
	}
	public void setDoctorAdviceResultExplain(
			java.lang.String doctorAdviceResultExplain) {
		this.doctorAdviceResultExplain = doctorAdviceResultExplain;
	}
	public java.lang.String getDoctorAdviceRiskExplain() {
		return doctorAdviceRiskExplain;
	}
	public void setDoctorAdviceRiskExplain(java.lang.String doctorAdviceRiskExplain) {
		this.doctorAdviceRiskExplain = doctorAdviceRiskExplain;
	}
	//setter and getter END
}
