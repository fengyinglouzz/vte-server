
package com.insight.wisehealth.vte.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insight.core.util.JsonUtil;
import com.insight.core.util.R;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.persistence.TbVteRiskScore;
import com.insight.wisehealth.vte.service.VteRiskScoreService;

/**
 * 
 * 描述:VTE风险分度Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class VteRiskScoreController  {
	@Autowired
	private VteRiskScoreService vteRiskScoreService;
	
	@RequestMapping("/vteRiskScore/queryList")
   public List queryList(@RequestParam("offset")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		try {
			list = vteRiskScoreService.queryVteRiskScoreList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
   
   @RequestMapping("/vteRiskScore/queryCount")
   public R queryCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    int count = 0;
		try {
			count = vteRiskScoreService.countVteRiskScoreList(map);
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
   @RequestMapping("/vteRiskScore/saveVteRiskScoreInfo")
   public R saveVteRiskScoreInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbVteRiskScore saveVteRiskScore;
		try {
			saveVteRiskScore = vteRiskScoreService.saveVteRiskScore(map);
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
   @RequestMapping("/vteRiskScore/delVteRiskScoreInfo")
   public R delVteRiskScoreInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	   
		try {
			  vteRiskScoreService.delVteRiskScore(map);
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
   @RequestMapping("/vteRiskScore/queryVteRiskScoreInfo")
   public TbVteRiskScore queryVteRiskScoreInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbVteRiskScore queryVteRiskScoreInfo=null;
		try {
			   queryVteRiskScoreInfo = vteRiskScoreService.queryVteRiskScoreInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  queryVteRiskScoreInfo;
    }
   /**
  	 * @param jsonString
  	 * @return
  	 */
     @RequestMapping(value = "/vteAssessmentAdviceSave/queryVteRiskScoreInfoList", method = RequestMethod.POST)
     public List queryVteRiskScoreInfoList(@RequestParam(value="modelCode",required=false) String  modelCode) {
    	 
    	Map map = new HashMap();
   	    map.put("modelCode", modelCode);
 		List list = new ArrayList();
 		try {
 			list = vteRiskScoreService.queryVteRiskScoreInfoList(map);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	    return list;
      }

}