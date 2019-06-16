package com.insight.core.common;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 实体基类
 * @author 王明
 *
 */
public class BaseEntity {

	/**
	 *  是否有效，逻辑删除（0：否，1：是）数据字典
	 */
	private java.lang.Integer isEnable = Constants.IS_ENABLE_TRUE;
	/**
	 *  创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createDt;
	/**
	 *  创建人
	 */
	private java.lang.String createBy;
	/**
	 *  更新时间（最后一次）
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateDt;
	/**
	 *  更新人（最后一次）
	 */
	private java.lang.String updateBy;
	
	public java.lang.Integer getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(java.lang.Integer isEnable) {
		this.isEnable = isEnable;
	}
	public java.util.Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(java.util.Date createDt) {
		this.createDt = createDt;
	}
	public java.lang.String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}
	public java.util.Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(java.util.Date updateDt) {
		this.updateDt = updateDt;
	}
	public java.lang.String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}
}
