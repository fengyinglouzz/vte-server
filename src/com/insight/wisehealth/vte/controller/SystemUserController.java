
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
import com.insight.wisehealth.vte.persistence.TbSystemUser;
import com.insight.wisehealth.vte.service.SystemUserService;

/**
 * 
 * 描述:用户Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class SystemUserController  {
	@Autowired
	private SystemUserService systemUserService;
	
	@RequestMapping("/systemUser/queryList")
   public List queryList(@RequestParam("offset")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		try {
			list = systemUserService.querySystemUserList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
   
   @RequestMapping("/systemUser/queryCount")
   public R queryCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    int count=0;
		try {
			
			count = systemUserService.countSystemUserList(map);
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
   @RequestMapping("/systemUser/saveSystemUserInfo")
   public R saveSystemUserInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    Object userId= map.get("userId");
	    TbSystemUser saveSystemUser;
		try {
			if(userId==null || StringUtil.isEmpty(userId.toString())){
				String userCode=(String) map.get("userCode");
				String userAccount=(String) map.get("userAccount");
				Map repeatMap=new HashMap();
				repeatMap.put("userCode", userCode); 
				TbSystemUser querySystemUserInfo = systemUserService.querySystemUserInfo(repeatMap);
				if(querySystemUserInfo!=null){
					return R.error("用户编码已存在");
				}
				Map repeatMap_a=new HashMap();
				repeatMap_a.put("userAccount", userAccount);
				TbSystemUser querySystemUserInfo_a = systemUserService.querySystemUserInfo(repeatMap_a);
				if(querySystemUserInfo_a!=null){
					return R.error("用户账号已存在");
				}
			}
			saveSystemUser = systemUserService.saveSystemUser(map);
			
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
   @RequestMapping("/systemUser/delSystemUserInfo")
   public R delSystemUserInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	   
		try {
			  systemUserService.delSystemUser(map);
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
   @RequestMapping("/systemUser/querySystemUserInfo")
   public TbSystemUser querySystemUserInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbSystemUser querySystemUserInfo=null;
		try {
			   querySystemUserInfo = systemUserService.querySystemUserInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  querySystemUserInfo;
    }

}