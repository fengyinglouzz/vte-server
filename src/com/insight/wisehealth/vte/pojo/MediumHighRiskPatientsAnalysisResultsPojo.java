package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MediumHighRiskPatientsAnalysisResultsPojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.lang.String departmentName;
	private java.lang.Integer assessmentNum;
	private List<MediumHighRiskPatientsSubsetPojo> list;
	public java.lang.String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(java.lang.String departmentName) {
		this.departmentName = departmentName;
	}
	public java.lang.Integer getAssessmentNum() {
		return assessmentNum;
	}
	public void setAssessmentNum(java.lang.Integer assessmentNum) {
		this.assessmentNum = assessmentNum;
	}
	public List<MediumHighRiskPatientsSubsetPojo> getList() {
		return list;
	}
	public void setList(List<MediumHighRiskPatientsSubsetPojo> list) {
		this.list = list;
	}

}
