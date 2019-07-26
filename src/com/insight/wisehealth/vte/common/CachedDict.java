package com.insight.wisehealth.vte.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.Cacheable;
import javax.servlet.ServletContext;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.insight.core.config.SpringMvcContext;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.service.SystemDictService;

/**
 * 
 * 描述:字典缓存
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王明
 * @version 1.0.0
 */
@Component 
public class CachedDict {
	//字典中的一些值 程序中需要加入判断
	public final static String PATIENT_GENDER_FEMALE = "1";// 字典 PATIENT_GENDER 性别中的女 的真实值

	private static final Logger logger = LoggerFactory.getLogger(CachedDict.class);
	
	public static List<Map> getDictDataList(Map map)  {
		try{
			Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
			SystemDictService systemDictService = SpringMvcContext.getBean(SystemDictService.class); 
			Integer orgId = (Integer) map.get("orgId");
			String dictInternational = (String) map.get("dictInternational");
			String dictCode = (String) map.get("dictCode");
			String dictCodeName = StringUtil.tableNameToLowerFirst(dictCode);
			String key = new StringBuffer(orgId.toString()).append(dictInternational).toString();
			Element  element = ehCache.get(key);
			Map dictMap;
			if(element==null){
				Map searchMap = new HashMap();
				searchMap.put("orgId", orgId);
				searchMap.put("dictInternational", dictInternational);
				dictMap = systemDictService.queryLocalDictData(searchMap);
			    element=new Element(orgId+dictInternational, dictMap); 
			    ehCache.put(element);
			}else{
				dictMap = (Map) element.getObjectValue();
			}
			return (List)dictMap.get(dictCodeName);
		}catch (Exception e){
			logger.error("字典获取出错", e);
		} 
		return null;
	}
	
	public static Map getDictData(Map map)  {
		try{
			Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
			SystemDictService systemDictService = SpringMvcContext.getBean(SystemDictService.class); 
			Integer orgId = (Integer) map.get("orgId");
			String dictInternational = (String) map.get("dictInternational");
			String key = new StringBuffer(orgId.toString()).append(dictInternational).toString();
			Element  element = ehCache.get(key);
			Map dictMap;
			if(element==null){
				Map searchMap = new HashMap();
				searchMap.put("orgId", orgId);
				searchMap.put("dictInternational", dictInternational);
				dictMap = systemDictService.queryLocalDictData(searchMap);
			    element=new Element(orgId+dictInternational, dictMap); 
			    ehCache.put(element);
			}else{
				dictMap = (Map) element.getObjectValue();
			}
			return dictMap;
		}catch (Exception e){
			logger.error("字典获取出错", e);
		} 
		return null;
	}

	/**
	 * 字典数据值转化成字典数据名称
	 * @param orgId 机构ID
	 * @param dictInternational 字典国际化
	 * @param dataList 存放Map的list集合
	 * @param dictCodeFieldMap 字典替换Map：key为dictCode,value为list中Map的key名，页面Model中应有该属性名+Explain
	 * @throws Exception 
	 */
	public static void dictDataValue2DictDataName(Integer orgId, String dictInternational,
			List<Map> dataList, Map<String, String> dictCodeFieldMap) {
		orgId=1;//标准字典org
		try{
			Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
			SystemDictService systemDictService = SpringMvcContext.getBean(SystemDictService.class); 
			if (null != dataList && !dataList.isEmpty()) {
				String key = new StringBuffer(orgId.toString()).append(dictInternational).toString();
				Element  element = ehCache.get(key);
				Map dictMap;
				if(element==null){
					Map searchMap = new HashMap();
					searchMap.put("orgId", orgId);
					searchMap.put("dictInternational", dictInternational);
					dictMap = systemDictService.queryLocalDictData(searchMap);
				    element=new Element(orgId+dictInternational, dictMap); 
				    ehCache.put(element);
				}else{
					dictMap = (Map) element.getObjectValue();
				}
				for (int i = 0; i < dataList.size(); i++) {
					Map tempMap = (Map) dataList.get(i);
					Set<String> dictCodeSet = dictCodeFieldMap.keySet();
					for (String dictCode : dictCodeSet) {
						String dictCodeName = StringUtil.tableNameToLowerFirst(dictCode);
						String field = dictCodeFieldMap.get(dictCode);
						List<Map> dictDataList = (List)dictMap.get(dictCodeName);
						if (dictDataList != null) {
							if(dictDataList.size()==1){//如果当前字典只有一组
								if(dictCodeName.endsWith("UnitDisplay")||dictCodeName.endsWith("Unit")){//并且当前字典为单位字典
									Map<String, String> map = dictDataList.get(0);
									String dictDataName = map.get("dictDataName");
									String fieldExplain = field+"Explain";
									tempMap.put(fieldExplain, dictDataName);
									continue;
								}
							}
							for (Map<String, String> map : dictDataList) {
								String dictDataValue = (String) tempMap.get(field);
								String value = map.get("dictDataValue");
								if(null != dictDataValue) {
									if (dictDataValue.equals(value)) {
										String dictDataName = map.get("dictDataName");
										String fieldExplain = field+"Explain";
										tempMap.put(fieldExplain, dictDataName);
										dataList.set(i, tempMap);
										break;
									} else {//多选字典
										String[] array = dictDataValue.split(",");
										for(String str : array) {
											StringBuffer newDictDataNames = new StringBuffer();
											if (value.equals(str)) {
												String dictDataName = map.get("dictDataName");
												String fieldExplain = field+"Explain";
												if(tempMap.containsKey(fieldExplain)) {
													String dictDataNames = (String) tempMap.get(fieldExplain);
													newDictDataNames.append(dictDataNames)
														.append("；").append(dictDataName).toString();
												} else {
													newDictDataNames.append(dictDataName);
												}
												tempMap.put(fieldExplain, newDictDataNames.toString());
												dataList.set(i, tempMap);
												break;
											}
										}
									}
								} else {
									logger.debug("dictDataValue is null.");
								}
								
							}
						} else {
							logger.debug("未找到该{}字典的数据项", dictCode);
						}
					}
	
				}
			}
		}catch (Exception e){
			logger.error("字典翻译出错", e);
		} 
	}
	
	
	/**
	 * 字典数据值转化成字典数据名称
	 * @param orgId 机构ID
	 * @param dictInternational 字典国际化
	 * @param dataMap 存放数据 Map集合
	 * @param dictCodeFieldMap 字典替换Map：key为dictCode,value为list中Map的key名，页面Model中应有该属性名+Explain
	 * @throws Exception 
	 */
	public static void dictDataValueToDictDataName(Map dictMap,
			Map tempMap, Map<String, String> dictCodeFieldMap)  {
		//orgId=1;//标准字典org
		try{
		    if(tempMap != null && !tempMap.isEmpty()){
				Set<String> dictCodeSet = dictCodeFieldMap.keySet();
				for (String dictCode : dictCodeSet) {
					String dictCodeName = StringUtil.tableNameToLowerFirst(dictCode);
					String field = dictCodeFieldMap.get(dictCode);
					List<Map> dictDataList =(List)dictMap.get(dictCodeName);
					if (dictDataList != null) {
						if(dictDataList.size()==1){//如果当前字典只有一组
							if(dictCodeName.endsWith("UnitDisplay")||dictCodeName.endsWith("Unit")){//并且当前字典为单位字典
								Map<String, String> map = dictDataList.get(0);
								String dictDataName = map.get("dictDataName");
								String fieldExplain = field+"Explain";
								tempMap.put(fieldExplain, dictDataName);
								continue;
							}
						}
						for (Map<String, String> map : dictDataList) {
							String dictDataValue = String.valueOf(tempMap.get(field));
							String value = map.get("dictDataValue");
							if(null != dictDataValue) {
								if (dictDataValue.equals(value)) {
									String dictDataName = map.get("dictDataName");
									String fieldExplain = field+"Explain";
									tempMap.put(fieldExplain, dictDataName);
									break;
								} else {//多选字典
									String[] array = dictDataValue.split(",");
									for(String str : array) {
										StringBuffer newDictDataNames = new StringBuffer();
										if (value.equals(str)) {
											String dictDataName = map.get("dictDataName");
											String fieldExplain = field+"Explain";
											if(tempMap.containsKey(fieldExplain)) {
												String dictDataNames = (String) tempMap.get(fieldExplain);
												newDictDataNames.append(dictDataNames)
													.append("；").append(dictDataName).toString();
											} else {
												newDictDataNames.append(dictDataName);
											}
											tempMap.put(fieldExplain, newDictDataNames.toString());
											break;
										}
									}
								}
							} else {
								logger.debug("dictDataValue is null.");
							}
							
						}
					} else {
						logger.debug("未找到该{}字典的数据项", dictCode);
					}
				}
		    }	
		}catch (Exception e){
			logger.error("字典翻译出错", e);
		} 
	}
	
	
	public static Map finddictMap(Integer orgId,String dictInternational) throws Exception{
		Map dictMap;
		Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
		SystemDictService systemDictService = SpringMvcContext.getBean(SystemDictService.class); 
		String key = new StringBuffer(orgId.toString()).append(dictInternational).toString();
		Element  element = ehCache.get(key);
		if(element==null){
			Map searchMap = new HashMap();
			searchMap.put("orgId", orgId);
			searchMap.put("dictInternational", dictInternational);
			dictMap = systemDictService.queryLocalDictData(searchMap);
		    element=new Element(orgId+dictInternational, dictMap); 
		    ehCache.put(element);
		}else{
			dictMap = (Map) element.getObjectValue();
		}
		return dictMap;
	}
	
	/**
	 * 字典数据值转化成字典数据名称性别去掉Explain
	 * @param orgId 机构ID
	 * @param dictInternational 字典国际化
	 * @param dataMap 存放数据 Map集合
	 * @param dictCodeFieldMap 字典替换Map：key为dictCode,value为list中Map的key名，页面Model中应有该属性名+Explain
	 * @throws Exception 
	 */
	public static void dictDataValueToDictDataNameStr(Integer orgId, String dictInternational,
			Map tempMap, Map<String, String> dictCodeFieldMap)  {
		Integer orgHospitalId = orgId;
		try{
		    if(tempMap!=null&&!tempMap.isEmpty()){
		    	Map dictMap;
				Set<String> dictCodeSet = dictCodeFieldMap.keySet();
				for (String dictCode : dictCodeSet) {
					String field = dictCodeFieldMap.get(dictCode);
					if(!tempMap.containsKey(field)){//需要翻译的字段中没有这个属性 不在进行翻译
						continue;
					}
					String dictCodeName = StringUtil.tableNameToLowerFirst(dictCode);
					if(dictCodeName.endsWith("UnitDisplay")||dictCodeName.endsWith("Unit")){//并且当前字典为单位字典 单位字典使用医院字典进行翻译，其余字典使用标准字典进行翻译
						//TODO 等单位字典全部统一之后 也使用标准字典进行翻译
						orgId = orgHospitalId;
					}else{
						orgId=1;//标准字典org
					}
					dictMap = finddictMap(orgId, dictInternational);
					List<Map> dictDataList =(List)dictMap.get(dictCodeName);
					if (dictDataList != null) {
						if(dictDataList.size()==1){//如果当前字典只有一组
							if(dictCodeName.endsWith("UnitDisplay")||dictCodeName.endsWith("Unit")){//并且当前字典为单位字典
								Map<String, String> map = dictDataList.get(0);
								String dictDataName = map.get("dictDataName");
								String fieldExplain = field;
								tempMap.put(fieldExplain, dictDataName);
								continue;
							}
						}
						for (Map<String, String> map : dictDataList) {
							String dictDataValue = String.valueOf(tempMap.get(field));
							String value = map.get("dictDataValue");
							if(null != dictDataValue) {
								if(dictDataValue.indexOf(",")>0){//是否为多选
									//多选字典
									StringBuffer tempdictDataValue = new StringBuffer();
									tempdictDataValue.append(",").append( dictDataValue).append(",");
									StringBuffer tempvalue =  new StringBuffer();
									tempdictDataValue.append(",").append( value).append(",");
									if(tempdictDataValue.indexOf(tempvalue.toString())>=0){
										StringBuffer newDictDataNames = new StringBuffer();
										String dictDataName = map.get("dictDataName");
										//String fieldExplain = field;
										String fieldExplain = field+"Explain";
										if(tempMap.containsKey(fieldExplain)) {
											String dictDataNames = (String) tempMap.get(fieldExplain);
											newDictDataNames.append(dictDataNames)
												.append("；").append(dictDataName).toString();
										} else {
											newDictDataNames.append(dictDataName);
										}
										tempMap.put(fieldExplain, newDictDataNames.toString());
									}
								}else{
									if (dictDataValue.equals(value)) {
										String dictDataName = map.get("dictDataName");
										String fieldExplain = field;
										tempMap.put(fieldExplain, dictDataName);
										break;
									} 
								}
							} else {
								//logger.debug("dictDataValue is null.");
							}
							
						}
						Object object = tempMap.get(field+"Explain");
						if(object!=null){
							tempMap.put(field, object);
						}
					} else {
						//logger.debug("未找到该{}字典的数据项", dictCode);
					}
				}
		    }	
		}catch (Exception e){
			//logger.error("字典翻译出错", e);
		} 
	}
	
	
	//简单字典翻译
	public static String dictDataValueToDictDataName(Integer orgId, String dictInternational,String dictCode,String dictDataValue)  {
		orgId=1;//标准字典org
		try{
			Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
			SystemDictService systemDictService = SpringMvcContext.getBean(SystemDictService.class); 
			if(!StringUtil.isEmpty(dictDataValue)){
				String key = new StringBuffer(orgId.toString()).append(dictInternational).toString();
				Element  element = ehCache.get(key);
				Map dictMap;
				if(element==null){
					Map searchMap = new HashMap();
					searchMap.put("orgId", orgId);
					searchMap.put("dictInternational", dictInternational);
					dictMap = systemDictService.queryLocalDictData(searchMap);
				    element=new Element(key, dictMap); 
				    ehCache.put(element);
				}else{
					dictMap = (Map) element.getObjectValue();
				}
				String dictCodeName = StringUtil.tableNameToLowerFirst(dictCode);
				List<Map> dictDataList =(List)dictMap.get(dictCodeName);
				if(dictDataList == null){//如果本机构字典中不存在，查看系统字典中是否存在
					key = new StringBuffer("1").append(dictInternational).toString();
					element = ehCache.get(key);
					if(element==null){
						Map searchMap = new HashMap();
						searchMap.put("orgId", 1);
						searchMap.put("dictInternational", dictInternational);
						dictMap = systemDictService.queryLocalDictData(searchMap);
					    element=new Element(key, dictMap); 
					    ehCache.put(element);
					}else{
						dictMap = (Map) element.getObjectValue();
					}
					dictDataList =(List)dictMap.get(dictCodeName);
				}
				if (dictDataList != null) {
					StringBuffer newDictDataNames = new StringBuffer();
					for (Map<String, String> map : dictDataList) {
						String value = map.get("dictDataValue");
						if(null != dictDataValue) {
							if (dictDataValue.equals(value)) {
								newDictDataNames.append(map.get("dictDataName"));
							} else if((","+dictDataValue+",").indexOf(","+value+",")>=0){//多选字典
								newDictDataNames.append("；");
								newDictDataNames.append(map.get("dictDataName"));
							}
						} else {
							logger.debug("dictDataValue is null.");
						}
					}
					return newDictDataNames.toString().replaceFirst("；","");
				} else {
					logger.debug("未找到该{}字典的数据项", dictCodeName);
				}
			}	
		}catch (Exception e){
			logger.error("字典翻译出错", e);
		} 
		return null;
	}
	
	
	//字典值转化
	public static String dictDataNameToDictDataValue(Integer orgId, String dictInternational,String dictCode,String dictDataName)  {
			try{
				Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
				SystemDictService systemDictService = SpringMvcContext.getBean(SystemDictService.class); 
				if(!StringUtil.isEmpty(dictDataName)){
					dictDataName = dictDataName.trim();
					//WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
					String key = new StringBuffer(orgId.toString()).append(dictInternational).toString();
					Element  element = ehCache.get(key);
					Map dictMap;
					if(element==null){
						Map searchMap = new HashMap();
						searchMap.put("orgId", orgId);
						searchMap.put("dictInternational", dictInternational);
						dictMap = systemDictService.queryLocalDictData(searchMap);
					    element=new Element(key, dictMap); 
					    ehCache.put(element);
					}else{
						dictMap = (Map) element.getObjectValue();
					}
					String dictCodeName = StringUtil.tableNameToLowerFirst(dictCode);
					List<Map> dictDataList =(List)dictMap.get(dictCodeName);
					if (dictDataList != null) {
						StringBuffer newDictDataValues = new StringBuffer();
						for (Map<String, String> map : dictDataList) {
							String name = map.get("dictDataName");
							if (dictDataName.equals(name)) {
								newDictDataValues.append(map.get("dictDataValue"));
							} else if((","+dictDataName+",").indexOf(","+name+",")>=0){//多选字典
								newDictDataValues.append("；");
								newDictDataValues.append(map.get("dictDataValue"));
							}
						}
						return newDictDataValues.toString().replaceFirst("；","");
					}else {
						logger.debug("未找到该{}字典的数据项", dictCodeName);
					}
				}	
			}catch (Exception e){
				logger.error("字典翻译出错", e);
			} 
			return null;
		}
	
	//根据机构id和国际化编码  加载字典到缓存
	/*public static void loadDictDataByDictCode(List dictList) throws SQLException {
			ServletContext servletContext = ServletActionContext.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			Cache ehCache =  (Cache) webApplicationContext.getBean("ehCache");
			//
			Map searchMap = new HashMap();
			StringBuffer orgidSb = new StringBuffer();
			StringBuffer dictInternationalSb = new StringBuffer();
			Map<String,Object> orgMap =  new HashMap();
			Map<String,Object> dictInternationalMap =  new HashMap();
			//清除字典
			if(dictList!=null&&dictList.size()>0){
				for(int i=0;i<dictList.size();i++){
					Map dictdataMap = (Map)dictList.get(i);
					if(dictdataMap.get("orgId")==null){//设置默认值
						dictdataMap.put("orgId",1);
					}
					Integer orgId = Integer.parseInt(dictdataMap.get("orgId").toString()) ;
					if(dictdataMap.get("dictInternational")==null){//设置默认值
						dictdataMap.put("dictInternational","zh_CN");
					}
					String dictInternational = (String) dictdataMap.get("dictInternational");//国际化
					if(!orgMap.containsKey(orgId.toString())){
						orgMap.put(orgId.toString(), dictdataMap);
					}
					if(!dictInternationalMap.containsKey(dictInternational)){
						dictInternationalMap.put(dictInternational, dictdataMap);
					}
				}
			}
			StringBuffer orgIds = new StringBuffer();
			StringBuffer dictInternationals = new StringBuffer();
			 for (String key : orgMap.keySet()) {
				 orgIds.append(",'").append(key).append("'");
			 }
			 searchMap.put("orgIds",orgIds.substring(1));
			 for (String key : dictInternationalMap.keySet()) {
				 dictInternationals.append(",'").append(key).append("'");
			 }
			 searchMap.put("dictInternationals",dictInternationals.substring(1));
			 SystemDictService systemDictService =  (SystemDictService) webApplicationContext.getBean("systemDictService");
			 systemDictService.queryDictDataForEhcache(ehCache,searchMap);
			 
		}
	*/
	public static String gainDictMapKey(Integer orgId, String dictInternational,
			String dictCode){
				String key = new StringBuffer(String.valueOf(orgId))
				.append(dictInternational).toString();
				 return key;
	}
	
	public static void dictDataValue2DictDataNameForObj(Integer orgId, String dictInternational,
			List<Object> dataList, Map<String, String> dictCodeFieldMap) {
		orgId=1;//标准字典org
		try{
			Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
			SystemDictService systemDictService = SpringMvcContext.getBean(SystemDictService.class); 
			if (null != dataList && !dataList.isEmpty()) {
				String key = new StringBuffer(orgId.toString()).append(dictInternational).toString();
				Element  element = ehCache.get(key);
				Map dictMap;
				if(element==null){
					Map searchMap = new HashMap();
					searchMap.put("orgId", orgId);
					searchMap.put("dictInternational", dictInternational);
					dictMap = systemDictService.queryLocalDictData(searchMap);
				    element=new Element(orgId+dictInternational, dictMap); 
				    ehCache.put(element);
				}else{
					dictMap = (Map) element.getObjectValue();
				}
				if(dictMap ==null){
		    		logger.error( "当前机构或语言没有字典项","");
		    		return;
		    	}
				for (int i = 0; i < dataList.size(); i++) {
					Object object =  dataList.get(i);
					
					Class<?> classType = object.getClass();
					
					Field fields[] = classType.getDeclaredFields();
					
					Field field = null;
					String suffixMethodName;
					Method getMethod, setMethod;
					Object objValue = null;
					Set<String> dictCodeSet = dictCodeFieldMap.keySet();//需要翻译的字段
					
					for (int j = 0; j < fields.length; j++) {
						field = fields[j];
						String pName=field.getName();//属性名字
						if(Modifier.isStatic(field.getModifiers())){
							continue;
						}
						suffixMethodName = pName.substring(0, 1).toUpperCase() + (pName.length()>1?(pName.substring(1)):"");
							
						setMethod = classType.getMethod("set" + suffixMethodName, new Class[] { field.getType() });
						
						
						for (String dictCode : dictCodeSet) {
							String dictCodeName = StringUtil.tableNameToLowerFirst(dictCode);
							String codeName=dictCodeName.substring(0,dictCodeName.indexOf("Explain"));
							String field_d = dictCodeFieldMap.get(dictCode);//相当于属性
							getMethod = classType.getMethod("get" +  codeName.substring(0, 1).toUpperCase() + (codeName.length()>1?(codeName.substring(1)):""), new Class[] {});
							objValue = getMethod.invoke(object);
							if(dictCodeName.equals(pName)){
								List<Map> dictDataList = (List)dictMap.get(codeName);
								/*if(dictDataList.size()==1){//如果当前字典只有一组
									if(dictCodeName.endsWith("UnitDisplay")||dictCodeName.endsWith("Unit")){//并且当前字典为单位字典
										Map<String, String> map = dictDataList.get(0);
										String dictDataName = map.get("dictDataName");
										setMethod.invoke(object, new Object[] { dictDataName });
										continue;
									}
								}*/
								StringBuffer newDictDataNames = new StringBuffer();
								boolean flag=false;
								for (Map<String, String> map : dictDataList) {
									String value = map.get("dictDataValue");
									if(null != objValue) {
										if(!objValue.toString().contains(",")){//单选
											if (objValue.equals(value)) {
												String dictDataName = map.get("dictDataName");
												setMethod.invoke(object, new Object[] { dictDataName });
												dataList.set(i, object);
												break;
											}
											
										}else{//多选字典
											String[] array = objValue.toString().split(",");
											flag=true;
											for(String str : array) {
												if (value.equals(str)) {
													String dictDataName = map.get("dictDataName");
												    newDictDataNames.append('；').append(dictDataName);
												}
											}
										}
										
									} else {
										logger.debug("dictDataValue is null.");
									}
									
								}
								if(flag){//多选的时候才执行
									if(newDictDataNames==null){
										setMethod.invoke(object, new Object[] { "" });
									}else{
										String explainValue=newDictDataNames.toString().substring(1, newDictDataNames.length());
										setMethod.invoke(object, new Object[] { explainValue });
									}
									dataList.set(i, object);
								}
							
						}
					}
					}
				}
			}
		}catch (Exception e){
			logger.error("字典翻译出错", e);
		} 
	}
	
	/**将实体类对向 转换成 带pojo对象
	 * @param orgId
	 * @param dictInternational
	 * @param object 实体类
	 * @param dictCodeFieldMap 需要翻译的字段
	 * @param pojoObject pojo类
	 * 
	 */
	public static void dictDataValueToDictDataNameTranObject(Integer orgId, String dictInternational,
			Object object, Map<String, String> dictCodeFieldMap,Object pojoObject)  {
		Integer orgHospitalId = orgId;
		Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
		SystemDictService systemDictService = SpringMvcContext.getBean(SystemDictService.class);
		try{
		    if(object!=null){
		    	String key = new StringBuffer(orgId.toString()).append(dictInternational).toString();
				Element  element = ehCache.get(key);
		    	Map dictMap;
		    	if(element==null){
					Map searchMap = new HashMap();
					searchMap.put("orgId", orgId);
					searchMap.put("dictInternational", dictInternational);
					dictMap = systemDictService.queryLocalDictData(searchMap);
				    element=new Element(orgId+dictInternational, dictMap); 
				    ehCache.put(element);
				}else{
					dictMap = (Map) element.getObjectValue();
				}
		    	if(dictMap ==null){
		    		logger.error( "当前机构或语言没有字典项","");
		    		return;
		    	}
				Class<?> classType = object.getClass();
				Class<?> classPojoType = pojoObject.getClass();
				String className=classType.getName();
				String classPojoName=classPojoType.getName();
				String typeName = classType.getTypeName();
				String typePojoName = classPojoType.getTypeName();
				if(/*typeName.contains("com.insight.wisehealth.vte.persistence")
						&&typePojoName.contains("com.insight.wisehealth.vte.persistencepojo")
						&&*/classPojoName.contains(className)){
					Field fields[] = classType.getDeclaredFields();
					Field pojoFields[] = classPojoType.getDeclaredFields();
					
					Field field = null;
					String suffixMethodName;
					Method getMethod, setMethod,setMethodExplain;
					Object objValue = null;
					Set<String> dictCodeSet = dictCodeFieldMap.keySet();//需要翻译的字段
					
					for (int j = 0; j < fields.length; j++) {
						field = fields[j];
						String pName=field.getName();//属性名字
						if(pName.equals("serialVersionUID")){
							continue;
						}
						suffixMethodName = pName.substring(0, 1).toUpperCase() + (pName.length()>1?(pName.substring(1)):"");
							
						for (String dictCode : dictCodeSet) {
							String dictCodeName = StringUtil.tableNameToLowerFirst(dictCode);
							String codeName=dictCodeName.substring(0,dictCodeName.indexOf("Explain"));
							String field_d = dictCodeFieldMap.get(dictCode);//相当于属性
							getMethod = classType.getMethod("get" +  suffixMethodName.substring(0, 1).toUpperCase() + (suffixMethodName.length()>1?(suffixMethodName.substring(1)):""), new Class[] {});
							objValue = getMethod.invoke(object);
							/*if(dictDataList.size()==1){//如果当前字典只有一组
								if(dictCodeName.endsWith("UnitDisplay")||dictCodeName.endsWith("Unit")){//并且当前字典为单位字典
									Map<String, String> map = dictDataList.get(0);
									String dictDataName = map.get("dictDataName");
									setMethod.invoke(object, new Object[] { dictDataName });
									continue;
								}
							}*/
							setMethod = classPojoType.getMethod("set" + suffixMethodName, new Class[] { field.getType() });
							setMethod.invoke(pojoObject,new Object[] { objValue });
							if(pName.equals(codeName)){//含有需要翻译的字段
								setMethodExplain = classPojoType.getMethod("set" + suffixMethodName+"Explain", new Class[] { field.getType() });
								StringBuffer newDictDataNames = new StringBuffer();
								List<Map> dictDataList = (List)dictMap.get(codeName);
								boolean flag=false;
								for (Map<String, String> map : dictDataList) {
									String value = map.get("dictDataValue");
									if(null != objValue) {
										if(!objValue.toString().contains(",")){//单选
											if (objValue.equals(value)) {
												String dictDataName = map.get("dictDataName");
												setMethodExplain.invoke(pojoObject, new Object[] { dictDataName });
												break;
											}
											
										}else{//多选字典
											String[] array = objValue.toString().split(",");
											flag=true;
											for(String str : array) {
												if (value.equals(str)) {
													String dictDataName = map.get("dictDataName");
												    newDictDataNames.append('；').append(dictDataName);
												}
											}
										}
										
									} else {
										logger.debug("dictDataValue is null.");
									}
									
								}
								if(flag){//多选的时候才执行
									if(newDictDataNames==null){
										setMethodExplain.invoke(pojoObject, new Object[] { "" });
									}else{
										String explainValue=newDictDataNames.toString().substring(1, newDictDataNames.length());
										setMethodExplain.invoke(pojoObject, new Object[] { explainValue });
									}
								}
							}
							
					 }
					}					
				}else{
					logger.error( "参数Object对象传入应为com.insight.wisehealth.vte.persistence或com.insight.wisehealth.vte.persistence","");
				}

				
			}
		    	
		}catch (Exception e){
			logger.error("字典翻译出错", e);
		} 
		
	}
	

}
