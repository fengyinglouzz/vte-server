package com.insight.wisehealth.vte.persistence;

import java.io.Serializable;

import com.insight.core.common.BaseEntity;
/**
 * 
 * 描述:字典数据实体
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
public class TbSystemDictData extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/**
	 *  字典数据ID
	 */
	private java.lang.Integer dictDataId;
	/**
	 *  字典ID
	 */
	private java.lang.Integer dictId;
	/**
	 *  字典数据名称
	 */
	private java.lang.String dictDataName;
	/**
	 *  字典数据值
	 */
	private java.lang.String dictDataValue;
	/**
	 *  字典数据备注
	 */
	private java.lang.String dictDataMemo;
	/**
	 *  字典数据排序
	 */
	private java.lang.Integer dictDataSeq;
	//columns END

	//setter and getter START
	public void setDictDataId(java.lang.Integer value) {
		this.dictDataId = value;
	}
	
	public java.lang.Integer getDictDataId() {
		return this.dictDataId;
	}
	public void setDictId(java.lang.Integer value) {
		this.dictId = value;
	}
	
	public java.lang.Integer getDictId() {
		return this.dictId;
	}
	public void setDictDataName(java.lang.String value) {
		this.dictDataName = value;
	}
	
	public java.lang.String getDictDataName() {
		return this.dictDataName;
	}
	public void setDictDataValue(java.lang.String value) {
		this.dictDataValue = value;
	}
	
	public java.lang.String getDictDataValue() {
		return this.dictDataValue;
	}
	public void setDictDataMemo(java.lang.String value) {
		this.dictDataMemo = value;
	}
	
	public java.lang.String getDictDataMemo() {
		return this.dictDataMemo;
	}
	public void setDictDataSeq(java.lang.Integer value) {
		this.dictDataSeq = value;
	}
	
	public java.lang.Integer getDictDataSeq() {
		return this.dictDataSeq;
	}
	//setter and getter END
}
