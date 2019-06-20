
package com.insight.wisehealth.vte.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insight.core.util.JsonUtil;
import com.insight.core.util.R;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.persistence.TbSystemUser;
import com.insight.wisehealth.vte.persistence.TbVteDepartment;
import com.insight.wisehealth.vte.service.VteDepartmentService;

/**
 * 
 * 描述:患者科室（用于方便日后统计使用）Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class VteDepartmentController  {
	@Autowired
	private VteDepartmentService vteDepartmentService;
	
	@RequestMapping("/vteDepartment/queryList")
   public List queryList(@RequestParam("offset")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		try {
			list = vteDepartmentService.queryVteDepartmentList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
   
   @RequestMapping("/vteDepartment/queryCount")
   public R queryCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    int count = 0;
		try {
			count = vteDepartmentService.countVteDepartmentList(map);
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
   @RequestMapping("/vteDepartment/saveVteDepartmentInfo")
   public R saveVteDepartmentInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    Object departmentId= map.get("departmentId");
	    TbVteDepartment saveVteDepartment;
		try {
			if(departmentId==null || StringUtil.isEmpty(departmentId.toString())){
				String departmentCode=(String) map.get("departmentCode");
				if(!StringUtils.isEmpty(departmentCode)){
					Map repeatMap=new HashMap();
					repeatMap.put("departmentCode", departmentCode); 
//					 TbVteDepartment queryVteDepartmentInfo = vteDepartmentService.queryVteDepartmentInfo(repeatMap);
//					if(queryVteDepartmentInfo!=null){
//						return R.error("科室编码已存在");
//					}
				}
			}
			saveVteDepartment = vteDepartmentService.saveVteDepartment(map);
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
   @RequestMapping("/vteDepartment/delVteDepartmentInfo")
   public R delVteDepartmentInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	   
		try {
			  vteDepartmentService.delVteDepartment(map);
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
   @RequestMapping("/vteDepartment/queryVteDepartmentInfo")
   public TbVteDepartment queryVteDepartmentInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbVteDepartment queryVteDepartmentInfo=null;
		try {
			   queryVteDepartmentInfo = vteDepartmentService.queryVteDepartmentInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  queryVteDepartmentInfo;
    }

}