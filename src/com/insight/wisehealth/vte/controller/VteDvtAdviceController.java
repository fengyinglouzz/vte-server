
package com.insight.wisehealth.vte.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insight.core.util.JsonUtil;
import com.insight.core.util.R;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.persistence.TbVteDvtAdvice;
import com.insight.wisehealth.vte.service.VteDvtAdviceService;

/**
 * 
 * 描述:DVT诊断流程建议Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class VteDvtAdviceController  {
	@Autowired
	private VteDvtAdviceService vteDvtAdviceService;
	
	@RequestMapping("/vteDvtAdvice/queryList")
   public List queryList(@RequestParam("offset")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		try {
			list = vteDvtAdviceService.queryVteDvtAdviceList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
   
   @RequestMapping("/vteDvtAdvice/queryCount")
   public R queryCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    int count = 0;
		try {
			count = vteDvtAdviceService.countVteDvtAdviceList(map);
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
   @RequestMapping("/vteDvtAdvice/saveVteDvtAdviceInfo")
   public R saveVteDvtAdviceInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbVteDvtAdvice saveVteDvtAdvice;
		try {
			saveVteDvtAdvice = vteDvtAdviceService.saveVteDvtAdvice(map);
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
   @RequestMapping("/vteDvtAdvice/delVteDvtAdviceInfo")
   public R delVteDvtAdviceInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	   
		try {
			  vteDvtAdviceService.delVteDvtAdvice(map);
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
   @RequestMapping("/vteDvtAdvice/queryVteDvtAdviceInfo")
   public TbVteDvtAdvice queryVteDvtAdviceInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbVteDvtAdvice queryVteDvtAdviceInfo=null;
		try {
			   queryVteDvtAdviceInfo = vteDvtAdviceService.queryVteDvtAdviceInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  queryVteDvtAdviceInfo;
    }

}