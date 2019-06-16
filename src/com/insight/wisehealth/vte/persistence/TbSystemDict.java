package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:字典实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbSystemDict extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  字典ID
	 */
	private java.lang.Integer dictId;
	/**
	 *  组织机构ID
	 */
	private java.lang.Integer orgId;
	/**
	 *  字典名称
	 */
	private java.lang.String dictName;
	/**
	 *  字典编码
	 */
	private java.lang.String dictCode;
	/**
	 *  字典别名
	 */
	private java.lang.String dictAlias;
	/**
	 *  字典默认值
	 */
	private java.lang.String dictDefaultValue;
	/**
	 *  字典排序
	 */
	private java.lang.Integer dictSeq;
	/**
	 *  字典备注
	 */
	private java.lang.String dictMemo;
	/**
	 *  字典是否多选（0是单选, 1是多选）（字典）
	 */
	private java.lang.String dictMultiselect;
	/**
	 *  国际化编码（参照各国语言缩写）
	 */
	private java.lang.String dictInternational;
	/**
	 *  
	 */
	private java.lang.Integer dictVersion;
	//columns END

	//setter and getter START
	public void setDictId(java.lang.Integer value) {
		this.dictId = value;
	}
	
	public java.lang.Integer getDictId() {
		return this.dictId;
	}
	public void setOrgId(java.lang.Integer value) {
		this.orgId = value;
	}
	
	public java.lang.Integer getOrgId() {
		return this.orgId;
	}
	public void setDictName(java.lang.String value) {
		this.dictName = value;
	}
	
	public java.lang.String getDictName() {
		return this.dictName;
	}
	public void setDictCode(java.lang.String value) {
		this.dictCode = value;
	}
	
	public java.lang.String getDictCode() {
		return this.dictCode;
	}
	public void setDictAlias(java.lang.String value) {
		this.dictAlias = value;
	}
	
	public java.lang.String getDictAlias() {
		return this.dictAlias;
	}
	public void setDictDefaultValue(java.lang.String value) {
		this.dictDefaultValue = value;
	}
	
	public java.lang.String getDictDefaultValue() {
		return this.dictDefaultValue;
	}
	public void setDictSeq(java.lang.Integer value) {
		this.dictSeq = value;
	}
	
	public java.lang.Integer getDictSeq() {
		return this.dictSeq;
	}
	public void setDictMemo(java.lang.String value) {
		this.dictMemo = value;
	}
	
	public java.lang.String getDictMemo() {
		return this.dictMemo;
	}
	public void setDictMultiselect(java.lang.String value) {
		this.dictMultiselect = value;
	}
	
	public java.lang.String getDictMultiselect() {
		return this.dictMultiselect;
	}
	public void setDictInternational(java.lang.String value) {
		this.dictInternational = value;
	}
	
	public java.lang.String getDictInternational() {
		return this.dictInternational;
	}
	public void setDictVersion(java.lang.Integer value) {
		this.dictVersion = value;
	}
	
	public java.lang.Integer getDictVersion() {
		return this.dictVersion;
	}
	//setter and getter END
}
