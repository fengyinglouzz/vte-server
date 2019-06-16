
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
import com.insight.wisehealth.vte.persistence.TbSystemOrg;
import com.insight.wisehealth.vte.pojo.VteHospitalPojo;
import com.insight.wisehealth.vte.service.SystemOrgService;

/**
 * 
 * 描述:机构Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class SystemOrgController  {
	@Autowired
	private SystemOrgService systemOrgService;
	
	@RequestMapping("/systemOrg/queryList")
   public List queryList(@RequestParam("offset")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		try {
			list = systemOrgService.querySystemOrgList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
   
   @RequestMapping("/systemOrg/queryCount")
   public R queryCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    int count = 0;
		try {
			count = systemOrgService.countSystemOrgList(map);
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
   @RequestMapping("/systemOrg/saveSystemOrgInfo")
   public R saveSystemOrgInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbSystemOrg saveSystemOrg;
		try {
			saveSystemOrg = systemOrgService.saveSystemOrg(map);
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
   @RequestMapping("/systemOrg/delSystemOrgInfo")
   public R delSystemOrgInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	   
		try {
			  systemOrgService.delSystemOrg(map);
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
   @RequestMapping("/systemOrg/querySystemOrgInfo")
   public TbSystemOrg querySystemOrgInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbSystemOrg querySystemOrgInfo=null;
		try {
			   querySystemOrgInfo = systemOrgService.querySystemOrgInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  querySystemOrgInfo;
    }
   
   /**
	 * @param jsonString
	 * @return
	 */
  @RequestMapping("/systemOrg/queryVteHospitalPojoList")
  public List<VteHospitalPojo> queryVteHospitalPojoList(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    List<VteHospitalPojo> vteHospitalPojoList=null;
		try {
			vteHospitalPojoList = systemOrgService.queryVteHospitalPojoList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return  vteHospitalPojoList;
   }
  /**
	 * @param jsonString
	 * @return
	 */
 @RequestMapping("/systemOrg/querySystemAllDepartmentList")
 public List<TbSystemOrg> querySystemAllDepartmentList(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    List<TbSystemOrg> vteDepartmentList=null;
		try {
			vteDepartmentList = systemOrgService.queryAllSystemDepartment(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return  vteDepartmentList;
  }
}