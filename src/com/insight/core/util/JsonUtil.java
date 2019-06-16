package com.insight.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

/**
 * 
 * 
 * 描述:JSON 转换处理
 * 
 * Copyright (c) 2013 by . Frank
 * 
 * @author Frank
 * @version 1.0
 */
public class JsonUtil {


	/**
	 * 从一个JSON 对象字符格式中得到一个java对象，形如： {"id" : idValue, "name" : nameValue,
	 * "aBean" : {"aBeanId" : aBeanIdValue, ...}}
	 * 
	 * @author Frank
	 * @param jsonString json字符串
	 * @param clazz 类
	 * @return   T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getDTO(String jsonString, Class<T> clazz) {
		JSONObject jsonObject = null;
		JsonConfig jsonConfig = JsonConfigFactory.create(clazz);
		
		try {
		
			//System.out.println("=======jsonString========="+jsonString);
			JSONUtils.getMorpherRegistry().deregisterMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd'T'HH:mm:ss.SSSZ","yyyy-MM-dd"}));
			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd'T'HH:mm:ss.SSSZ","yyyy-MM-dd"}));
			
			jsonObject = JSONObject.fromObject(jsonString, jsonConfig);
			
			//System.out.println("=======jsonObject========="+jsonObject.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T)JSONObject.toBean(jsonObject, jsonConfig);
	}
	
	/**
	 * 字符串转化成指定的类
	 * @author Frank
	 * @param jsonString json串
	 * @param clazz 类
	 * @param ignore  不转换的字段
	 * @return Object
	 */
	public static Object getDTO(String jsonString, Class clazz,String[] ignore) {
		JSONObject jsonObject = null;
		JsonConfig jsonConfig = JsonConfigFactory.create(clazz, ignore);
		try {
			jsonObject = JSONObject.fromObject(jsonString, jsonConfig);
			
			/*for(int i  = 0 ; i < ignore.length ; i++){
				if(StringUtils.isEmpty((String) jsonObject.get(ignore[i]))){
					
					System.out.println("=======JSON转换忽略字段========="+ignore[i]);
					jsonObject.remove(ignore[i]);
				}
				
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toBean(jsonObject, jsonConfig);
	}
	
	
	

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象，其中beansList是一类的集合，形如： {"id" : idValue, "name" :
	 * nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}, beansList:[{}, {},
	 * ...]}
	 * 
	 * 如果class中没有对应的field,不会抛出异常
	 * @author Frank 
	 * @param jsonString json字符串
	 * @param clazz 类名
	 * @param map
	 *            集合属性的类型 (key : 集合属性名, value : 集合属性类型class) eg: ("beansList" :
	 *            Bean.class)
	 * @return Object
	 */
	public static Object getDTO(String jsonString, Class clazz, Map map) {
		JSONObject jsonObject = null;
		JsonConfig jsonConfig = JsonConfigFactory.create(clazz, map);
		try {
			jsonObject = JSONObject.fromObject(jsonString, jsonConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toBean(jsonObject, jsonConfig);
	}

	/**
	 * 从一个JSON数组得到一个java对象数组，形如： [{"id" : idValue, "name" : nameValue}, {"id" :
	 * idValue, "name" : nameValue}, ...]
	 * 
	 * @author Frank 
	 * @param jsonString  json字符串
	 * @param clazz 类
	 * @return Object
	 */
	public static Object[] getDTOArray(String jsonString, Class clazz) {
		JsonConfig jsonConfig = JsonConfigFactory.create(clazz);
		JSONArray array = JSONArray.fromObject(jsonString, jsonConfig);
		Object[] obj = new Object[array.size()];
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			obj[i] = JSONObject.toBean(jsonObject, jsonConfig);
		}
		return obj;
	}

	/**
	 * 从一个JSON数组得到一个java对象数组，形如： [{"id" : idValue, "name" : nameValue}, {"id" :
	 * idValue, "name" : nameValue}, ...]
	 * 
	 * @author Frank
	 * @param jsonString json字符串
	 * @param clazz 类
	 * @param map map对象
	 * @return Obejct[]
	 */
	public static Object[] getDTOArray(String jsonString, Class clazz, Map map) {
		JsonConfig jsonConfig = JsonConfigFactory.create(clazz, map);
		JSONArray array = JSONArray.fromObject(jsonString, jsonConfig);
		Object[] obj = new Object[array.size()];
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			obj[i] = JSONObject.toBean(jsonObject, jsonConfig);
		}
		return obj;
	}

	/**
	 * 从一个JSON数组得到一个java对象集合
	 * @author Frank 
	 * @param jsonString json字符串
	 * @param clazz 类
	 * @return List
	 */
	public static List getDTOList(String jsonString, Class clazz) {
		JsonConfig jsonConfig = JsonConfigFactory.create(clazz);
		JSONArray array = JSONArray.fromObject(jsonString, jsonConfig);
		List list = new ArrayList();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			JSONObject jsonObject = (JSONObject) iter.next();
			list.add(JSONObject.toBean(jsonObject, jsonConfig));
		}
		return list;
	}

	/**
	 * 从一个JSON数组得到一个java对象集合，其中对象中包含有集合属性
	 * @author Frank 
	 * @param jsonString json字符串
	 * @param clazz 类
	 * @param map
	 *            集合属性的类型
	 * @return List
	 */
	public static List getDTOList(String jsonString, Class clazz, Map map) {
		JsonConfig jsonConfig = JsonConfigFactory.create(clazz, map);
		JSONArray array = JSONArray.fromObject(jsonString, jsonConfig);
		List list = new ArrayList();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			JSONObject jsonObject = (JSONObject) iter.next();
			list.add(JSONObject.toBean(jsonObject, jsonConfig));
		}
		return list;
	}

	/**
	 * 从json HASH表达式中获取一个map，该map支持嵌套功能 形如：{"id" : "johncon", "name" : "小强"}
	 * 注意commons
	 * -collections版本，必须包含org.apache.commons.collections.map.MultiKeyMap
	 * @author Frank 
	 * @param jsonString json字符串
	 * @return Map
	 */
	public static Map getMapFromJson(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Map map = new HashMap();
		if(jsonObject!=null&&!jsonObject.isEmpty()){
			for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				map.put(key, jsonObject.get(key));
			}
		}
		return map;
	}
	
	/**
	 * 从json HASH表达式中获取一个map，该map支持嵌套功能 
	 * 
	 * 形如：
	 *	{
	 *	  ultrasonicUpdateGroupObj=[
	 *	    
	 *	  ],
	 *	  ultrasonicUpdateObj={
	 *	    "ultrasonicLiverInternalUnitDisplay": "0",
	 *	    "ultrasonicLiverInternalTumor": [
	 *	      "0",
	 *	      "1",
	 *	      "4",
	 *	      "3",
	 *	      "2"
	 *	    ],
	 *	    "patientUniqueId": "WHS201607040000403P000001",
	 *	    "ultrasonicId": "119"
	 *	  },
	 *	  patientUniqueId=WHS201607040000403P000001
	 *	}
	 * 其中对 JSONArray类型 ["1","2","3"] 转字符串 "1,2,3"  为了支持ibatis存储映射多选类型如下：
	 * 
	 * @author Frank 
	 * @param jsonString  json字符串
	 * @return Map
	 */
	public static Map getStringMapFromJson(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Map map = new HashMap();
		if(jsonObject!=null&&!jsonObject.isEmpty()){
			for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				Object value = (Object)jsonObject.get(key);
				if( value instanceof JSONArray){
					
					JSONArray v = (JSONArray)value;
					
					StringBuilder sb = new StringBuilder();
					for(int i  =0 ; i <v.size(); i++){
						sb.append(v.get(i));
						if(i!=v.size()-1)
						sb.append(",");
					}
					map.put(key,sb.toString());
				}else{
					map.put(key, value);
				}
			}
		}
		return map;
	}


	/**
	 * 从json数组中得到相应java数组 json形如：["123", "456"]
	 * @author Frank 
	 * @param jsonString json字符串
	 * @return Object[]
	 */
	public static Object[] getObjectArrayFromJson(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();
	}

	/**
	 * 把数据对象转换成json字符串 DTO对象形如：{"id" : idValue, "name" : nameValue, ...}
	 * 数组对象形如：[{}, {}, {}, ...] map对象形如：{key1 : {"id" : idValue, "name" :
	 * nameValue, ...}, key2 : {}, ...}
	 * 
	 * @param object 对象
	 * @return String 
	 */
	public static String getJSONString(Object object) {
		String jsonString = null;
		JsonConfig jsonConfig = setDataFormat2JSON();
		if (object != null) {
			if (object instanceof Collection || object instanceof Object[]) {
				jsonString = JSONArray.fromObject(object, jsonConfig)
						.toString();
			} else {
				jsonString = JSONObject.fromObject(object, jsonConfig)
						.toString();
			}
		}
		
		//System.out.println("~~~普通JSON~~~~"+jsonString);
		return jsonString == null ? "{}" : jsonString;
	}

	/**
	 * Java转JSON
	 * 
	 * 处理3种日期类型
	 * 
	 * 1.java.sql.Date 
	 * 2.java.sql.Timestamp
	 * 3.java.util.Date
	 * @author Frank 
	 * @return JsonConfig
	 */
	private static JsonConfig setDataFormat2JSON() {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class,
				new JsonDateValueProcessor(JsonDateValueProcessor.DEFAULT_DATE_FORMAT));
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
				new JsonDateValueProcessor(JsonDateValueProcessor.FULL_DATE_FORMAT));
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor(JsonDateValueProcessor.FULL_DATE_FORMAT));
		
		
		
		return jsonConfig;
	}

	/**
	 * JSON转Java
	 * 
	 * 支持两种格式
	 * 
	 * 1.yyyy-MM-dd 
	 * 2.yyyy-MM-dd HH:mm:ss
	 * @author Frank 
	 * @return void
	 */
	private static void registerDataFormat2JAVA() {
		
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));  
	}

	// ////////////////////////////old 为了兼容暂时//////////////////////////////////
	/**
	 * 
	 * 转换分页格式----一般用于grid显示
	 * 
	 * { totalCount:xxx, root:[{}, {}, {}, ...]
	 * 
	 * }
	 * 
	 * @author Frank 
	 * @param totalCount 数据条数
	 * @param msgs 数据集合
	 * @return String
	 */

	public static String list2JsonStringPage(long totalCount, List msgs) {
		StringBuilder sb = new StringBuilder();
		sb.append("{").append("\"totalCount\":").append("\"")
				.append(totalCount).append("\",");
		sb.append("\"root\":");
		JSONArray jsonArray = JSONArray.fromObject(msgs,
				setDataFormat2JSON());

		sb.append(jsonArray.toString());

		sb.append("}");
		
		
		System.out.println("~~~分页JSON~~~~"+sb);
		return sb.toString();
	}
	/**
	 * 转换分页格式----一般用于grid显示 
	 * 
	 * @param result
	 * 			必须包含两个元素:count, list
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String list2JsonStringPage(Map<String, Object> result) {
		List list = (List) result.get("list");
		int count = (Integer) result.get("count");
		return list2JsonStringPage(count, list);
	}
	
	
	/**
	 * 带结果状态的list封装  ---一般用于需要返回状态标示的请求
	 * @author Frank
	 * @param succ 是否成功
	 * @param count 条数
	 * @param msgs 数据集合
	 * @return String
	 */
	public static String list2JsonStringPage(boolean succ, int count, List msgs)
	  {
	    StringBuilder sb = new StringBuilder();
	    sb.append("{").append("\"success\":").append("\"")
	      .append(succ).append("\",");
	    sb.append("\"totalCount\":").append("\"").append(count).append("\",");
	    sb.append("\"root\":");
	    JSONArray jsonArray = JSONArray.fromObject(msgs, 
	      setDataFormat2JSON());

	    sb.append(jsonArray.toString());

	    sb.append("}");
	    
	    System.out.println("~~~jsonString~~~~"+sb);
	    
	    return sb.toString();
	  }
	/**
	 * 带结果状态的list封装  ---一般用于需要返回状态标示的请求,并且有弹出提示信息
	 * @param succ 是否成功
	 * @param count 条数
	 * @param msgs 数据数组
	 * @param info 提醒信息
	 * @return String
	 */
	public static String list2JsonStringPage(boolean succ, int count, List msgs, String info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{").append("\"success\":").append("\"").append(succ).append("\",");
		sb.append("\"info\":").append("\"").append(info).append("\",");
		sb.append("\"totalCount\":").append("\"").append(count).append("\",");
		sb.append("\"root\":");
		JSONArray jsonArray = JSONArray.fromObject(msgs, 
				setDataFormat2JSON());
		
		sb.append(jsonArray.toString());
		
		sb.append("}");
		
		System.out.println("~~~分页JSON~~~~"+sb);
		return sb.toString();
	}


	/**
	 * 
	 * 转换集合 为JSON字符串
	 * @author Frank 
	 * @param models 数据集合
	 * @return String
	 */
	public static String list2JsonString(List models) {
		return getJSONString(models);
	}
	
	
	
	/**
	 * 转换JSON树
	 * @author Frank 
	 * @param models 数据集合
	 * @return String
	 */
	public static String list2JsonTreeString(List models){
		
		String jsonString = getJSONString(models);
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("{")
		.append("\"success\":")
		.append("\"")
		.append(true)
		.append("\",")
		
		
		.append("\"root\":{")
		.append("\"text\":\"全部\"")
		
		.append("},")
		
		.append("\"children\":")
		.append(jsonString)
		.append("}");
		
		;
		System.out.println("~~~树JSON~~~~"+sb);
		return jsonString;
	}

	/**
	 * 设置转化json字符串的格式类型
	 * @author Frank
	 * @return JsonConfig
	 */
	public static JsonConfig setUtilDataFormat2JSON() {
		// 日期值处理器
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		return jsonConfig;
	}
	
	
	public static void main(String[] args) {
		
//		CallOrderPojo o = new CallOrderPojo();
//		
//		o.setCallinDate(new Date());
//		
//		String json = JsonUtil.getJSONString(o);
//		
//		System.out.println(json);
//		
//		CallOrderPojo o1 = (CallOrderPojo) JsonUtil.getDTO(
//				(json), CallOrderPojo.class);
		
		
	//	String jsonString = "{'orderCustname':'aa','orderTel1':'56568','orderTel2':'','orderTel3':'','orderCustcode':'K2014120500002','orderCustname1':'aa','orderTel4':'11111','orderDate':'2014-12-15 14:55:42','orderProvince':'\u6cb3\u5317\u7701','orderCity':'\u5510\u5c71\u5e02','orderArea':'\u8def\u5317\u533a','orderStreet':'\u5927\u91cc\u8857\u9053','orderAddress':'\u6cb3\u5317\u7701\u5510\u5c71\u5e02\u8def\u5317\u533a\u5927\u91cc\u8857\u9053','orderZip':'063000','orderCommunity':'','orderPaymode':'\u90ae\u653f\u6c47\u6b3e','orderSelfcheck':'\u901a\u8fc7','orderExpress':'\u4e2d\u901a\u5feb\u9012','orderChecklevel':'\u52a0\u6025','orderMedia':'','orderAskid':'admin','indentReason':'','useDays':'','orderMemo':'','orderBcyc':'0','orderBcfq':'0','orderBcjf':'100','orderYcye':'0','orderFqye':'0','orderJfye':'0','orderTotal':'105'}";
		
	//	TbBizOrder order = JsonUtil.getDTO(jsonString, TbBizOrder.class);
		
	//	System.out.println(order);
		
		
		
	}
	
	
	

}
