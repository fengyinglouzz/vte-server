package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:患者科室（用于方便日后统计使用）实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbVteDepartment extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  
	 */
	private java.lang.Integer departmentId;
	/**
	 *  科室编码（系统自动生成）
	 */
	private java.lang.String departmentCode;
	/**
	 *  科室名称（同步患者时。查看该表中不存在科室，那么将这个科室信息记录下来）
	 */
	private java.lang.String departmentName;
	/**
	 *  是否进行中高危检测
	 */
	private java.lang.Integer departmentMonitor;
	/**
	 *  是否进行质控视图
	 */
	private java.lang.Integer departmentQualitycontrol;
	/**
	 *  科室排序
	 */
	private java.lang.Integer departmentSort;
	//columns END

	//setter and getter START
	public void setDepartmentId(java.lang.Integer value) {
		this.departmentId = value;
	}
	
	public java.lang.Integer getDepartmentId() {
		return this.departmentId;
	}
	public void setDepartmentCode(java.lang.String value) {
		this.departmentCode = value;
	}
	
	public java.lang.String getDepartmentCode() {
		return this.departmentCode;
	}
	public void setDepartmentName(java.lang.String value) {
		this.departmentName = value;
	}
	
	public java.lang.String getDepartmentName() {
		return this.departmentName;
	}
	public void setDepartmentMonitor(java.lang.Integer value) {
		this.departmentMonitor = value;
	}
	
	public java.lang.Integer getDepartmentMonitor() {
		return this.departmentMonitor;
	}
	public void setDepartmentQualitycontrol(java.lang.Integer value) {
		this.departmentQualitycontrol = value;
	}
	
	public java.lang.Integer getDepartmentQualitycontrol() {
		return this.departmentQualitycontrol;
	}
	public void setDepartmentSort(java.lang.Integer value) {
		this.departmentSort = value;
	}
	
	public java.lang.Integer getDepartmentSort() {
		return this.departmentSort;
	}
	//setter and getter END
}
