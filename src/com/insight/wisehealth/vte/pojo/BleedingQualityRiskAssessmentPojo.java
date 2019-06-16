package com.insight.wisehealth.vte.pojo;

import java.io.Serializable;


public class BleedingQualityRiskAssessmentPojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.lang.String department;
	private java.lang.String bleedingSum;
	private java.lang.String oneDayBleedingRate;
	private java.lang.String recentlyBleedingRate;
	public java.lang.String getDepartment() {
		return department;
	}
	public void setDepartment(java.lang.String department) {
		this.department = department;
	}
	public java.lang.String getBleedingSum() {
		return bleedingSum;
	}
	public void setBleedingSum(java.lang.String bleedingSum) {
		this.bleedingSum = bleedingSum;
	}
	public java.lang.String getOneDayBleedingRate() {
		return oneDayBleedingRate;
	}
	public void setOneDayBleedingRate(java.lang.String oneDayBleedingRate) {
		this.oneDayBleedingRate = oneDayBleedingRate;
	}
	public java.lang.String getRecentlyBleedingRate() {
		return recentlyBleedingRate;
	}
	public void setRecentlyBleedingRate(java.lang.String recentlyBleedingRate) {
		this.recentlyBleedingRate = recentlyBleedingRate;
	}
	
}
