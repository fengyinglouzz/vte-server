package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:VTE风险分度实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbVteRiskScore extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  
	 */
	private java.lang.Integer riskScoreId;
	/**
	 *  VTE风险分度名称（文）
	 */
	private java.lang.String riskScoreName;
	/**
	 *  VTE风险分度编码（文）
	 */
	private java.lang.String riskScoreCode;
	/**
	 *  VTE风险分度评分文本显示（文）
	 */
	private java.lang.String riskScoreShowText;
	/**
	 *  VTE风险分度范围最小值（数）
	 */
	private java.lang.Integer riskScoreMinValue;
	/**
	 *  VTE风险分度范围最大值（数）
	 */
	private java.lang.Integer riskScoreMaxValue;
	/**
	 *  VTE发生率（数）
	 */
	private java.math.BigDecimal riskScoreRate;
	//columns END

	//setter and getter START
	public void setRiskScoreId(java.lang.Integer value) {
		this.riskScoreId = value;
	}
	
	public java.lang.Integer getRiskScoreId() {
		return this.riskScoreId;
	}
	public void setRiskScoreName(java.lang.String value) {
		this.riskScoreName = value;
	}
	
	public java.lang.String getRiskScoreName() {
		return this.riskScoreName;
	}
	public void setRiskScoreCode(java.lang.String value) {
		this.riskScoreCode = value;
	}
	
	public java.lang.String getRiskScoreCode() {
		return this.riskScoreCode;
	}
	public void setRiskScoreShowText(java.lang.String value) {
		this.riskScoreShowText = value;
	}
	
	public java.lang.String getRiskScoreShowText() {
		return this.riskScoreShowText;
	}
	public void setRiskScoreMinValue(java.lang.Integer value) {
		this.riskScoreMinValue = value;
	}
	
	public java.lang.Integer getRiskScoreMinValue() {
		return this.riskScoreMinValue;
	}
	public void setRiskScoreMaxValue(java.lang.Integer value) {
		this.riskScoreMaxValue = value;
	}
	
	public java.lang.Integer getRiskScoreMaxValue() {
		return this.riskScoreMaxValue;
	}
	public void setRiskScoreRate(java.math.BigDecimal value) {
		this.riskScoreRate = value;
	}
	
	public java.math.BigDecimal getRiskScoreRate() {
		return this.riskScoreRate;
	}
	//setter and getter END
}
