package com.insight.wisehealth.vte.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.insight.core.config.SpringMvcContext;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.dao.TbVteRiskScoreDao;
import com.insight.wisehealth.vte.persistence.TbVteAssessment;
import com.insight.wisehealth.vte.persistence.TbVteAssessmentDict;
import com.insight.wisehealth.vte.persistence.TbVteRiskScore;
import com.insight.wisehealth.vte.pojo.VteAssessmentAndListPojo;
import com.insight.wisehealth.vte.pojo.VteRiskScoreCodePojo;
import com.insight.wisehealth.vte.service.VteAssessmentDictService;

public class AssessmentDict {
	private static final Logger logger = LoggerFactory.getLogger(AssessmentDict.class);
	private static final String  ASSESSMENT_DICT_NAME = "assessment";
	private static final String  ASSESSMENT_TYPE_NAME = "assessment_type";
	
	public static Map getAssessmentDictList(String assessmentItem)  {
		try{
			Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
			VteAssessmentDictService assessmentDictService = SpringMvcContext.getBean(VteAssessmentDictService.class); 
			
			String key = new StringBuffer(ASSESSMENT_DICT_NAME).append(assessmentItem).toString();
			Element  element = ehCache.get(key);
			Map dictMap;
			if(element==null){
				Map searchMap = new HashMap();
				searchMap.put("assessmentItem", assessmentItem);
				dictMap = new HashMap();
				List queryVteAssessmentDictList = assessmentDictService.queryAllVteAssessmentDictNp(searchMap);
				for (Object object : queryVteAssessmentDictList) {
					TbVteAssessmentDict tbVteAssessmentDict = (TbVteAssessmentDict)object;
					Map innerMap = new HashMap();
					innerMap.put("assessmentDictName", tbVteAssessmentDict.getAssessmentDictName());
					Integer assessmentDictScore = tbVteAssessmentDict.getAssessmentDictScore();
					innerMap.put("assessmentDictScore", assessmentDictScore);
					String assessmentDictCode = tbVteAssessmentDict.getAssessmentDictCode();
					innerMap.put("assessmentDictCode", assessmentDictCode);
					dictMap.put(assessmentDictCode, innerMap);
				}
			    element=new Element(ASSESSMENT_DICT_NAME + assessmentItem, dictMap); 
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
	 * 加载到缓存中
	 */
	public static void setAssessmentDictListToCache()  {
		try{
			Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
			VteAssessmentDictService assessmentDictService = SpringMvcContext.getBean(VteAssessmentDictService.class); 
			
			Map searchMap = new HashMap();
			List assessmentDictList = assessmentDictService.queryAllVteAssessmentDictNp(null);
			for (Object object : assessmentDictList) {
				TbVteAssessmentDict tbVteAssessmentDict = (TbVteAssessmentDict)object;
				String assessmentItem = tbVteAssessmentDict.getAssessmentItem();
				String key = new StringBuffer(ASSESSMENT_DICT_NAME).append(assessmentItem).toString();
				
				String typeKey = new StringBuffer(ASSESSMENT_TYPE_NAME).append(assessmentItem).toString();
				
				Element  element = ehCache.get(key);
				Map innerMap = new HashMap();
				innerMap.put("assessmentDictName", tbVteAssessmentDict.getAssessmentDictName());
				Integer assessmentDictScore = tbVteAssessmentDict.getAssessmentDictScore();
				innerMap.put("assessmentDictScore", assessmentDictScore);
				String assessmentDictCode = tbVteAssessmentDict.getAssessmentDictCode();
				innerMap.put("assessmentDictCode", assessmentDictCode);
				if(element==null){
					Map dictMap = new HashMap();
					dictMap.put(assessmentDictCode, innerMap);
				    element=new Element(ASSESSMENT_DICT_NAME + assessmentItem, dictMap); 
				    ehCache.put(element);
				}else{
					Map dictMap = (Map)element.getObjectValue();
					dictMap.put(assessmentDictCode, innerMap);
				}
				
				Element  typeElement = ehCache.get(typeKey);
				if(typeElement==null){
					StringBuffer scoreStr = new StringBuffer( "" + assessmentDictScore);
					typeElement=new Element(ASSESSMENT_TYPE_NAME + assessmentItem,scoreStr); 
					 ehCache.put(typeElement);
				}else{
					StringBuffer scoreStr =(StringBuffer)typeElement.getObjectValue();
					if(scoreStr.indexOf( assessmentDictScore+"" ) == -1){
						if(scoreStr!=null && !StringUtil.isEmpty(scoreStr.toString())){
							scoreStr = scoreStr.append(";").append(assessmentDictScore);
						}else{
							scoreStr = scoreStr.append(assessmentDictScore);
						}
					}
				}
			}
		}catch (Exception e){
			logger.error("字典获取出错", e);
		} 
	}
	
	/**将实体类对向 转换成 带pojo对象
	 * @param assessmentItem
	 * @param object 实体类
	 * @param dictCodeFieldName 需要翻译的字段名称
	 * @param pojoObject pojo类
	 * 
	 */
	public static void dictDataValueToDictDataNameTranObject( String assessmentItem,Object object,String dictCodeFieldName,Object pojoObject)  {
		Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
		VteAssessmentDictService assessmentDictService = SpringMvcContext.getBean(VteAssessmentDictService.class); 
		try{
		    if(object!=null){
		    	
		    	Class<?> classType = object.getClass();
				Class<?> classPojoType = pojoObject.getClass();
				
				String suffixMethodName = dictCodeFieldName.substring(0, 1).toUpperCase() + (dictCodeFieldName.length()>1?(dictCodeFieldName.substring(1)): "");
				Method getMethod,setMethodExplain;
				Object objValue = null;
				getMethod = classType.getMethod("get" +  suffixMethodName, new Class[] {});
				setMethodExplain = classPojoType.getMethod("set" + suffixMethodName+"Explain", new Class[] { String.class });
				StringBuffer newDictDataNames = new StringBuffer();
				
				objValue = getMethod.invoke(object);
		    	String key = new StringBuffer(ASSESSMENT_DICT_NAME).append(assessmentItem).toString();
		    	
		    	Element  element = ehCache.get(key);
		    	Map dictMap;
		    	if(element==null){
					Map searchMap = new HashMap();
					searchMap.put("assessmentItem", assessmentItem);
					List assessmentDictList = assessmentDictService.queryAllVteAssessmentDictNp(null);
					dictMap = new HashMap();
					for (Object assessmentDictObject : assessmentDictList) {
						TbVteAssessmentDict tbVteAssessmentDict = (TbVteAssessmentDict)assessmentDictObject;
						Map innerMap = new HashMap();
						innerMap.put("assessmentDictName", tbVteAssessmentDict.getAssessmentDictName());
						innerMap.put("assessmentDictScore", tbVteAssessmentDict.getAssessmentDictScore());
						String assessmentDictCode = tbVteAssessmentDict.getAssessmentDictCode();
						innerMap.put("assessmentDictCode", assessmentDictCode);
						dictMap.put(assessmentDictCode, innerMap);
					}
				    element=new Element(key, dictMap); 
				    ehCache.put(element);
				}else{
					dictMap = (Map) element.getObjectValue();
				}
		    	
		    	if(dictMap == null){
		    		logger.error( "没有当前项目字典","");
					return ;
				}
		    	
		    	if(objValue!=null&&!objValue.equals("")){
		    		
		    		String selectValue = (String)objValue;
		    		String[] splitValue = selectValue.split(",");
		    		for (String string : splitValue) {
		    			Map innerMap = (Map)dictMap.get(string);
			    		if(innerMap!=null && !innerMap.isEmpty()){
			    			String assessmentDictName = (String)innerMap.get("assessmentDictName");
			    			 newDictDataNames.append('；').append(assessmentDictName);
			    		}else{
			    			logger.error( "当前机构或语言没有字典项","");
			    		}
					}
		    		
		    	}
		    	setMethodExplain.invoke(pojoObject, new Object[] { (suffixMethodName.length()>1?(suffixMethodName.substring(1)): "") });
			}
		    	
		}catch (Exception e){
			logger.error("字典翻译出错", e);
		} 
		
	}
	/**
	 * 将实体类对向 转换成 带pojo对象
	 * @param assessmentAndListPojo 特殊实体
	 * @param pojoObject pojo类
	 * 
	 */
	public static void dictDataValueTranObjectSpecial( VteAssessmentAndListPojo assessmentAndListPojo)  {
		Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
		VteAssessmentDictService assessmentDictService = SpringMvcContext.getBean(VteAssessmentDictService.class); 
		try{
		    if(assessmentAndListPojo!=null){
		    	
				String assessmentSelectData = assessmentAndListPojo.getAssessmentSelectData();
				String assessmentItem = assessmentAndListPojo.getAssessmentItem();
				
		    	String key = new StringBuffer(ASSESSMENT_DICT_NAME).append(assessmentItem).toString();
		    	Element  element = ehCache.get(key);
		    	Map dictMap;
		    	if(element==null){
					Map searchMap = new HashMap();
					searchMap.put("assessmentItem", assessmentItem);
					List assessmentDictList = assessmentDictService.queryAllVteAssessmentDictNp(searchMap);
					dictMap = new HashMap();
					for (Object assessmentDictObject : assessmentDictList) {
						TbVteAssessmentDict tbVteAssessmentDict = (TbVteAssessmentDict)assessmentDictObject;
						Map innerMap = new HashMap();
						innerMap.put("assessmentDictName", tbVteAssessmentDict.getAssessmentDictName());
						innerMap.put("assessmentDictScore", tbVteAssessmentDict.getAssessmentDictScore());
						String assessmentDictCode = tbVteAssessmentDict.getAssessmentDictCode();
						innerMap.put("assessmentDictCode", assessmentDictCode);
						dictMap.put(assessmentDictCode, innerMap);
					}
				    element=new Element(key, dictMap); 
				    ehCache.put(element);
				}else{
					dictMap = (Map) element.getObjectValue();
				}
		    	
		    	if(dictMap == null){
		    		logger.error( "没有当前项目字典","");
					return ;
				}
		    	
		    	StringBuffer scoreStr = new StringBuffer() ;
		    	String typeKey = new StringBuffer(ASSESSMENT_TYPE_NAME).append(assessmentItem).toString();
		    	Element  typeElement = ehCache.get(typeKey);
		    	if(typeElement==null){
		    		Map searchMap = new HashMap();
					searchMap.put("assessmentItem", assessmentItem);
					List assessmentDictList = assessmentDictService.queryAllVteAssessmentDictNp(searchMap);
		    		for (Object object : assessmentDictList) {
		    			TbVteAssessmentDict vteAssessmentDict = (TbVteAssessmentDict)object;
		    			Integer assessmentDictScore = vteAssessmentDict.getAssessmentDictScore();
		    			if(scoreStr.indexOf( assessmentDictScore+"" ) == -1){
		    				if(scoreStr!=null && !StringUtil.isEmpty(scoreStr.toString())){
								scoreStr = scoreStr.append(";").append(assessmentDictScore);
							}else{
								scoreStr = scoreStr.append(assessmentDictScore);
							}
		    			}
					}
		    		typeElement=new Element(ASSESSMENT_TYPE_NAME + assessmentItem,scoreStr); 
					 ehCache.put(typeElement);
		    	}else{
		    		scoreStr = (StringBuffer)typeElement.getObjectValue();
		    	}
		    	  
		    	String[] scoreList = scoreStr.toString().split(";");
		    	
		    	Map<String, List<String>> selectData = new HashMap<String, List<String>>();
		    	
		    	for (String scoreValue : scoreList) {
		    		
					List<String> innerList = new ArrayList<String>();
					if(assessmentSelectData!=null&&!assessmentSelectData.equals("")){
			    		String[] splitValue = assessmentSelectData.split(",");
			    		for (String string : splitValue) {
			    			Map innerMap = (Map)dictMap.get(string);
				    		if(innerMap!=null && !innerMap.isEmpty()){
				    			String assessmentDictScore = innerMap.get("assessmentDictScore").toString();
				    			if(assessmentDictScore.equals(scoreValue)){
				    				String assessmentDictName = (String)innerMap.get("assessmentDictName");
				    				innerList.add(assessmentDictName);
				    			}
				    			
				    		}else{
				    			logger.error( "当前机构或语言没有字典项~~~~~~","");
				    		}
						}
			    		
			    	}
					selectData.put(scoreValue, innerList);
				}
		    	assessmentAndListPojo.setSelectData(selectData);
			}
		    	
		}catch (Exception e){
			logger.error("字典翻译出错", e);
		} 
		
	}
	public static VteRiskScoreCodePojo calculateAssessmentResultAndScore(String assessmentItem,String assessmentSelectData) {
		
		if(assessmentSelectData==null){
			assessmentSelectData = "";
		}
		VteRiskScoreCodePojo scoreCodePojo = new VteRiskScoreCodePojo();
		TbVteRiskScoreDao vteRiskScoreMapper = SpringMvcContext.getBean(TbVteRiskScoreDao.class); 
		int score = 0;
		if(!assessmentSelectData.equals("") && assessmentSelectData.length()>0){
			String[] selectData = assessmentSelectData.split(",");
			Map assessmentDictList = getAssessmentDictList(assessmentItem);
			
			for (String string : selectData) {
				Object object = assessmentDictList.get(string);
				Map innerMap = (Map)object;
				Integer assessmentDictScore = 0;
				if(innerMap!=null){
					assessmentDictScore = (Integer)innerMap.get("assessmentDictScore");
				}
				score = score + assessmentDictScore;
			}
		}
		
		//设置分数
		scoreCodePojo.setAssessmentScore(score);
		if(assessmentItem.equals("4")){
			if(score>=3){
				scoreCodePojo.setAssessmentResult("4");
				scoreCodePojo.setAssessmentResultExplain("有");
			}else{
				scoreCodePojo.setAssessmentResult("5");
				scoreCodePojo.setAssessmentResultExplain("无");
			}
			
		}else if(assessmentItem.equals("3")||assessmentItem.equals("5")||assessmentItem.equals("6")){ //药物预防和机械预防
			if(score>0){
				scoreCodePojo.setAssessmentResult("4");
				scoreCodePojo.setAssessmentResultExplain("有");
			}else{
				scoreCodePojo.setAssessmentResult("5");
				scoreCodePojo.setAssessmentResultExplain("无");
			}
			
		}else{
			//设置结果
			Map map = new HashMap();
			map.put("riskScoreCode", "assessment_item_" + assessmentItem);
			List vteRiskScoreList = vteRiskScoreMapper.queryAllVteRiskScoreNP(map);
			
			String resultCode = "";
			for (Object object : vteRiskScoreList) {
				TbVteRiskScore vteRiskScore = (TbVteRiskScore)object;
				String riskScoreName = vteRiskScore.getRiskScoreName();
				Integer minValue = vteRiskScore.getRiskScoreMinValue();
				Integer maxValue = vteRiskScore.getRiskScoreMaxValue();
				if(minValue <= score){
					if(maxValue==null || maxValue>= score){
						resultCode = vteRiskScore.getRiskScoreId() + "" ;
						String assessmentResult = ConstantsDict.RISK_SCORE_CODE_MAP.get(resultCode);
						scoreCodePojo.setAssessmentResult(assessmentResult);
						scoreCodePojo.setAssessmentResultExplain(riskScoreName);
					}
				}
			}
		}
		
		
		return scoreCodePojo;
	}
}
