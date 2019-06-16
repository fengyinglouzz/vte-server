
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
import com.insight.wisehealth.vte.common.CachedDict;
import com.insight.wisehealth.vte.persistence.TbSystemDict;
import com.insight.wisehealth.vte.service.SystemDictService;

/**
 * 
 * 描述:字典Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class SystemDictController  {
	@Autowired
	private SystemDictService systemDictService;
	
	@RequestMapping("/systemDict/queryList")
   public List queryList(@RequestParam("offset")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		try {
			list = systemDictService.querySystemDictList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
   
   @RequestMapping("/systemDict/queryCount")
   public R queryCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    int count = 0;
		try {
			count = systemDictService.countSystemDictList(map);
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
   @RequestMapping("/systemDict/saveSystemDictInfo")
   public R saveSystemDictInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbSystemDict saveSystemDict;
		try {
			saveSystemDict = systemDictService.saveSystemDict(map);
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
   @RequestMapping("/systemDict/delSystemDictInfo")
   public R delSystemDictInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	   
		try {
			  systemDictService.delSystemDict(map);
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
   @RequestMapping("/systemDict/querySystemDictInfo")
   public TbSystemDict querySystemDictInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbSystemDict querySystemDictInfo=null;
		try {
			   querySystemDictInfo = systemDictService.querySystemDictInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  querySystemDictInfo;
    }
   
	@RequestMapping("/systemDict/queryAllDictList")
   public List queryAllDictList( @RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		List list = new ArrayList();
		try {
			list = systemDictService.queryAllSystemDictNp(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
	

   @RequestMapping("/systemDict/queryAllDict")
   public Map queryAllDict( @RequestParam(value="jsonString",required=false) String  jsonString) {
	    Map map = new HashMap();
		map.put("orgId",1);
		map.put("dictInternational","zh_CN");
		Map returnMap = CachedDict.getDictData(map);
	    return returnMap;
    }
}