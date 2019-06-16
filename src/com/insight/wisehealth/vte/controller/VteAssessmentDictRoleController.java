
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
import com.insight.wisehealth.vte.persistence.TbVteAssessmentDictRole;
import com.insight.wisehealth.vte.service.VteAssessmentDictRoleService;

/**
 * 
 * 描述:角色-评分数据字典关联表Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class VteAssessmentDictRoleController  {
	@Autowired
	private VteAssessmentDictRoleService vteAssessmentDictRoleService;
	
	@RequestMapping("/vteAssessmentDictRole/queryList")
   public List queryList(@RequestParam("offset")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		try {
			list = vteAssessmentDictRoleService.queryVteAssessmentDictRoleList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
   
   @RequestMapping("/vteAssessmentDictRole/queryCount")
   public R queryCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    int count = 0;
		try {
			count = vteAssessmentDictRoleService.countVteAssessmentDictRoleList(map);
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
   @RequestMapping("/vteAssessmentDictRole/saveVteAssessmentDictRoleInfo")
   public R saveVteAssessmentDictRoleInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
		try {
			vteAssessmentDictRoleService.saveVteAssessmentDictRoleMost(map);
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
   @RequestMapping("/vteAssessmentDictRole/delVteAssessmentDictRoleInfo")
   public R delVteAssessmentDictRoleInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	   
		try {
			  vteAssessmentDictRoleService.delVteAssessmentDictRole(map);
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
   @RequestMapping("/vteAssessmentDictRole/queryVteAssessmentDictRoleInfo")
   public TbVteAssessmentDictRole queryVteAssessmentDictRoleInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbVteAssessmentDictRole queryVteAssessmentDictRoleInfo=null;
		try {
			   queryVteAssessmentDictRoleInfo = vteAssessmentDictRoleService.queryVteAssessmentDictRoleInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  queryVteAssessmentDictRoleInfo;
    }
   
   @RequestMapping("/vteAssessmentDictRole/queryAssessmentDicByRoleId")
   public  List<Integer> queryAssessmentDicByRoleId(@RequestParam("roleId")  Integer  roleId) {
	    Map map =new HashMap();
	    map.put("roleId", roleId);
	    List<Integer>  vteModelList = null;
		try {
			vteModelList = vteAssessmentDictRoleService.queryAssessmentDicByRoleId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return vteModelList;
    }

}