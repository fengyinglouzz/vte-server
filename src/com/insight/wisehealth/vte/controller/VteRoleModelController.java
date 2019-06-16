
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
import com.insight.wisehealth.vte.persistence.TbVteRoleModel;
import com.insight.wisehealth.vte.service.VteRoleModelService;

/**
 * 
 * 描述:角色模块权限Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class VteRoleModelController  {
	@Autowired
	private VteRoleModelService vteRoleModelService;
	
	@RequestMapping("/vteRoleModel/queryList")
   public List queryList(@RequestParam("offset")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		try {
			list = vteRoleModelService.queryVteRoleModelList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
   
   @RequestMapping("/vteRoleModel/queryCount")
   public R queryCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    int count = 0;
		try {
			count = vteRoleModelService.countVteRoleModelList(map);
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
   @RequestMapping("/vteRoleModel/saveVteRoleModelInfo")
   public R saveVteRoleModelInfo(@RequestParam(value="jsonString") String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
		try {
			vteRoleModelService.saveVteModelRoleMost(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return  R.ok();
    }
   
   /**
	 * @param jsonString
	 * @return
	 */
   @RequestMapping("/vteRoleModel/delVteRoleModelInfo")
   public R delVteRoleModelInfo(@RequestParam(value="jsonString") String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	   
		try {
			  vteRoleModelService.delVteRoleModel(map);
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
   @RequestMapping("/vteRoleModel/queryVteRoleModelInfo")
   public TbVteRoleModel queryVteRoleModelInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbVteRoleModel queryVteRoleModelInfo=null;
		try {
			   queryVteRoleModelInfo = vteRoleModelService.queryVteRoleModelInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  queryVteRoleModelInfo;
    }
   
   @RequestMapping("/vteRoleModel/queryVteModelByRoleId")
   public List<Integer> queryVteModelByRoleId(@RequestParam(value="roleId") Integer  roleId) {
	    Map map =new HashMap();
	    map.put("roleId", roleId);
	    List<Integer>  vteModelList = null;
		try {
			vteModelList = vteRoleModelService.queryVteModelByRoleId(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return  vteModelList;
    }

}