package com.insight.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 
 * 
 * 描述:JSON转换辅助类(处理java.util.Date 和 java.sql.Date 和 java.sql.Timestamp)
 * 
 * Copyright © 2015 Frank All rights reserved
 * 
 * @author Frank
 * @version 1.0
 */
public class JsonDateValueProcessor implements JsonValueProcessor {

	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	public static final DateFormat YMD_DATE_FORMATER = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
	
	private DateFormat dateFormat;

	public JsonDateValueProcessor() {
		this(FULL_DATE_FORMAT);
	}

	/**
	 * 构造方法
	 * @author Frank
	 * @param datePattern 日期格式化
	 */
	public JsonDateValueProcessor(String datePattern) {
		try {
			dateFormat = new SimpleDateFormat(datePattern);
		} catch (Exception ex) {
			dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		}
	}

	/**
	 * 
	 * @author Frank
	 * @param value 对象值
	 * @param jsonConfig jsonConfig对象
	 * @return Object 
	 */
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value, jsonConfig);
	}

	/**
	 * 根据对象类型设置日期格式化的类型
	 * 对象的类型包括（java.sql.Date,java.sql.Timestamp,java.util.Date）
	 * @author Frank
	 * @param key key键
	 * @param value  对象值
	 * @param jsonConfig  JsonConfig对象
	 * @return Object
	 */
	public Object processObjectValue(String key, Object value,JsonConfig jsonConfig) {
		return process(value, jsonConfig);
	}

	/**
	 * 
	 * 根据对象类型设置日期格式化的类型
	 * 对象的类型包括（java.sql.Date,java.sql.Timestamp,java.util.Date）
	 * @author Frank
	 * @param value  对象值
	 * @param jsonConfig  JsonConfig对象
	 * @return Object
	 */
	private Object process(Object value, JsonConfig jsonConfig) {

		String jsonString = "";

		if (value instanceof java.sql.Date) {
			jsonString = dateFormat.format((new Date(((java.sql.Date) value)
					.getTime())));
		} else if (value instanceof java.sql.Timestamp) {
			jsonString = dateFormat.format((new Date(
					((java.sql.Timestamp) value).getTime())));
		} else if (value instanceof Date) {
			jsonString = dateFormat.format((Date) value);
		}

		return jsonString;

	}

}