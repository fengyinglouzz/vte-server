package com.insight.core.util;

import java.util.Map;

import net.sf.json.JsonConfig;

/**
 * 将json字符串 转化成java对象的时候,通用的配置
 * 
 * @author Frank
 *
 */
public class JsonConfigFactory {
	/**
	 * json转换java对象的配置
	 * 
	 * 1.""字符 转换为 null value
	 * 2.json中的 field 大于等于 bean中的field
	 * @author Frank 
	 * @param clazz 转换对象类型
	 * @return JsonConfig
	 */
	public static JsonConfig create(Class clazz){
		JsonConfig jsonConfig = new JsonConfig();
		//jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor()); //日期
		
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class,new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,new JsonDateValueProcessor());
		
		jsonConfig.setRootClass( clazz );//转换对象类型
		jsonConfig.setHandleJettisonEmptyElement(true);// ""字符 转换为 null value
		jsonConfig.setPropertySetStrategy(JsonPropertySetStrategy.getInstance());//json中的 field >= bean中的field
		return jsonConfig;
	}
	/**
	 * json转换java对象的配置
	 * 
	 * 1.""字符 转换为 null value
	 * 2.json中的 field 大于等于 bean中的field
	 * 3.排除一些不转换的字段
	 * 
	 * @param clazz 转换对象类型
	 * @param excludes 不转换的字段
	 * @return JsonConfig
	 */
	public static JsonConfig create(Class clazz, String[] excludes){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass( clazz );//转换对象类型
		jsonConfig.setExcludes(excludes);//排除一些不转换的字段
		jsonConfig.setHandleJettisonEmptyElement(true);// ""字符 转换为 null value
		jsonConfig.setPropertySetStrategy(JsonPropertySetStrategy.getInstance());//json中的 field >= bean中的field
		return jsonConfig;
	}
	/**
	 * json转换java对象的配置
	 * 
	 * 1.""字符 转换为 null value
	 * 2.json中的 field 大于等于 bean中的field
	 * 3.排除一些不转换的字段
	 * 
	 * @param clazz 转换对象类型
	 * @param excludes 不转换的字段
	 * @param classMap 不转换的字段
	 * @return JsonConfig
	 */
	public static JsonConfig create(Class clazz, String[] excludes, Map classMap){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass( clazz );//转换对象类型
		jsonConfig.setClassMap(classMap);
		jsonConfig.setExcludes(excludes);
		jsonConfig.setHandleJettisonEmptyElement(true);// ""字符 转换为 null value
		jsonConfig.setPropertySetStrategy(JsonPropertySetStrategy.getInstance());//json中的 field >= bean中的field
		return jsonConfig;
	}
	/**
	 * json转换java对象的配置
	 * 
	 * 1.""字符 转换为 null value
	 * 2.json中的 field 大于等于 bean中的field
	 * 3.排除一些不转换的字段
	 * 
	 * @param clazz 转换对象类型
	 * @param classMap 不转换的字段
	 * @return JsonConfig
	 */
	public static JsonConfig create(Class clazz, Map classMap){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass( clazz );//转换对象类型
		jsonConfig.setClassMap(classMap);
		jsonConfig.setHandleJettisonEmptyElement(true);// ""字符 转换为 null value
		jsonConfig.setPropertySetStrategy(JsonPropertySetStrategy.getInstance());//json中的 field >= bean中的field
		return jsonConfig;
	}
	
}
