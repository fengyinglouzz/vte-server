
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
import com.insight.wisehealth.vte.persistence.TbSystemRole;
import com.insight.wisehealth.vte.service.SystemRoleService;

/**
 * 
 * 描述:角色Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class SystemRoleController  {
	@Autowired
	private SystemRoleService systemRoleService;
	
	@RequestMapping("/systemRole/queryList")
   public List queryList(@RequestParam("offset")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		try {
			list = systemRoleService.querySystemRoleList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
   
   @RequestMapping("/systemRole/queryCount")
   public R queryCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    int count = 0;
		try {
			count = systemRoleService.countSystemRoleList(map);
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
   @RequestMapping("/systemRole/saveSystemRoleInfo")
   public R saveSystemRoleInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbSystemRole saveSystemRole;
		try {
			saveSystemRole = systemRoleService.saveSystemRole(map);
		} catch (Exception e) {
			return  R.error(e.getMessage());
		}
	    return  R.ok();
    }
   
   /**
	 * @param jsonString
	 * @return
	 */
   @RequestMapping("/systemRole/delSystemRoleInfo")
   public R delSystemRoleInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	   
		try {
			  systemRoleService.delSystemRole(map);
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
   @RequestMapping("/systemRole/querySystemRoleInfo")
   public TbSystemRole querySystemRoleInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbSystemRole querySystemRoleInfo=null;
		try {
			   querySystemRoleInfo = systemRoleService.querySystemRoleInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  querySystemRoleInfo;
    }
   /**
	 * @param jsonString
	 * @return
	 */
  @RequestMapping("/systemRole/queryRolePojoList")
  public List queryRolePojoList(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    List querySystemRoleList=null;
		try {
			querySystemRoleList = systemRoleService.queryAllSystemRoleNp(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  querySystemRoleList;
   }
}