
package com.insight.wisehealth.vte.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insight.core.util.JsonUtil;
import com.insight.core.util.R;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.persistence.TbVteAssessmentDict;
import com.insight.wisehealth.vte.pojo.VteAssessmentDictTreePojo;
import com.insight.wisehealth.vte.service.VteAssessmentDictService;

/**
 * 
 * 描述:评分数据项字典Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class VteAssessmentDictController  {
	@Autowired
	private VteAssessmentDictService vteAssessmentDictService;
	
	@RequestMapping("/vteAssessmentDict/queryList")
   public List queryList(@RequestParam("offset")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		try {
			list = vteAssessmentDictService.queryVteAssessmentDictList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
   
   @RequestMapping("/vteAssessmentDict/queryCount")
   public R queryCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    int count = 0;
		try {
			count = vteAssessmentDictService.countVteAssessmentDictList(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return R.ok().put("count", count);
    }
   

   /**
	 * @param jsonString
	 * @return
	 */
   @RequestMapping("/vteAssessmentDict/saveVteAssessmentDictInfo")
   public R saveVteAssessmentDictInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbVteAssessmentDict saveVteAssessmentDict;
		try {
			saveVteAssessmentDict = vteAssessmentDictService.saveVteAssessmentDict(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  R.ok();
    }
   
   /**
	 * @param jsonString
	 * @return
	 */
   @RequestMapping("/vteAssessmentDict/delVteAssessmentDictInfo")
   public R delVteAssessmentDictInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	   
		try {
			  vteAssessmentDictService.delVteAssessmentDict(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  R.ok();
    }
   /**
	 * @param jsonString
	 * @return
	 */
   @RequestMapping("/vteAssessmentDict/queryVteAssessmentDictInfo")
   public TbVteAssessmentDict queryVteAssessmentDictInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbVteAssessmentDict queryVteAssessmentDictInfo=null;
		try {
			   queryVteAssessmentDictInfo = vteAssessmentDictService.queryVteAssessmentDictInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  queryVteAssessmentDictInfo;
    }
   
   @RequestMapping("/vteAssessmentDict/queryVteAssessmentDictTree")
   public List<VteAssessmentDictTreePojo> queryVteAssessmentDictTree() {
	   List<VteAssessmentDictTreePojo> vteAssessmentDictTreePojoList=null;
	   Map searchMap = new HashMap();
		try {
			vteAssessmentDictTreePojoList = vteAssessmentDictService.queryVteAssessmentDictTreePojo(searchMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return  vteAssessmentDictTreePojoList;
    }

}