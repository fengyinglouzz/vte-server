
package com.insight.wisehealth.vte.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insight.core.util.JsonUtil;
import com.insight.core.util.R;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.common.ConstantsDict;
import com.insight.wisehealth.vte.common.SessionUser;
import com.insight.wisehealth.vte.loginpojo.LoginUserPojo;
import com.insight.wisehealth.vte.persistence.TbSystemRole;
import com.insight.wisehealth.vte.persistence.TbSystemUser;
import com.insight.wisehealth.vte.persistence.TbVteAssessmentDict;
import com.insight.wisehealth.vte.persistence.TbVteDoctorAdvice;
import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;
import com.insight.wisehealth.vte.pojo.MediumHighRiskPatients;
import com.insight.wisehealth.vte.pojo.MediumHighRiskPatientsCountPojo;
import com.insight.wisehealth.vte.persistence.TbVtePatient;
import com.insight.wisehealth.vte.persistencepojo.TbVteDoctorAdvicePojo;
import com.insight.wisehealth.vte.service.VtePatientHospitInfoService;
import com.insight.wisehealth.vte.service.VtePatientService;

/**
 * 
 * 描述:患者数据表Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class VtePatientController  {
	@Autowired
	private VtePatientService vtePatientService;
	@Autowired
	private VtePatientHospitInfoService vtePatientHospitInfoService;
	
	@RequestMapping("/vtePatient/queryList")
   public List queryList(@RequestParam("offset")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		
		
		try {
			//查询登录用户信息
			LoginUserPojo loginuser =  SessionUser.getCurrentUser();
			String rolePower = loginuser.getLoginRolePojo().getRolePower();
			if(!StringUtil.isEmpty(rolePower)&&rolePower.equals(ConstantsDict.DATA_POWER_CODE)){
				String departmentName = loginuser.getLoginDepartmentPojo().getDepartmentName();
				map.put("departmentName", departmentName);
			}
			list = vtePatientService.queryVtePatientList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
   
   @RequestMapping("/vtePatient/queryCount")
   public R queryCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    int count = 0;
		try {
			//查询登录用户信息
			LoginUserPojo loginuser =  SessionUser.getCurrentUser();
			String rolePower = loginuser.getLoginRolePojo().getRolePower();
			if(!StringUtil.isEmpty(rolePower)&&rolePower.equals(ConstantsDict.DATA_POWER_CODE)){
				String departmentName = loginuser.getLoginDepartmentPojo().getDepartmentName();
				map.put("departmentName", departmentName);
			}
			count = vtePatientService.countVtePatientList(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return R.ok().put("count", count);
    }
   
   /**
    * 查询中高危检测患者列表
    */
   @RequestMapping("/mediumHighRiskPatient/queryMediumHighRiskPatientsList")
   public List<MediumHighRiskPatients> queryMediumHighRiskPatientsList(@RequestParam("start")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	   LoginUserPojo loginUser=SessionUser.getCurrentUser();
	   if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
   		Map map = JsonUtil.getMapFromJson(jsonString);
   		if(!loginUser.getLoginRolePojo().getRolePower().equals("1")){
   			map.put("departmentCode", loginUser.getLoginDepartmentPojo().getDepartmentCode());
   		}
		map.put("start", start);
		map.put("limit", limit);
		List<MediumHighRiskPatients> list=null;
		try {
			list = vtePatientService.queryMediumHighRiskPatientsList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
   }
   /**
    * 查询中高危检测患者数
    */
   @RequestMapping("/mediumHighRiskPatient/queryMediumHighRiskPatientsCount")
   public R queryMediumHighRiskPatientsCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	   LoginUserPojo loginUser=SessionUser.getCurrentUser();
	   if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	   }
	   Map map = JsonUtil.getMapFromJson(jsonString); 
	   if(!loginUser.getLoginRolePojo().getRolePower().equals("1")){
 			map.put("departmentCode", loginUser.getLoginDepartmentPojo().getDepartmentCode());
	   }
	   int count = 0;
		try {
			count = vtePatientService.queryMediumHighRiskPatientsCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.ok().put("count", count);
   }
   
   /**
    * 查询各科室中高危检测患者数
    */
   @RequestMapping("/mediumHighRiskPatient/queryMediumHighRiskPatientsDeptCount")
   public List queryMediumHighRiskPatientsDeptCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	   LoginUserPojo loginUser=SessionUser.getCurrentUser();
	   if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	   }
	   Map map = JsonUtil.getMapFromJson(jsonString); 
	   if(!loginUser.getLoginRolePojo().getRolePower().equals("1")){
 			map.put("departmentCode", loginUser.getLoginDepartmentPojo().getDepartmentCode());
	   }
	   List<MediumHighRiskPatientsCountPojo> list = new ArrayList();
		try {
			list = vtePatientService.queryMediumHighRiskPatientsDeptCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
   }
   
   /**
    * 查询最新出血风险评估和最新预防禁忌评估
    */
   @RequestMapping("/mediumHighRiskPatient/queryPatientVteAssessmentDictList")
   public List queryPatientVteAssessmentDictList(@RequestParam("assessmentId")  String  assessmentId) {
	   List<TbVteAssessmentDict> list = new ArrayList();
		try {
			list = vtePatientService.queryPatientVteAssessmentDictList(assessmentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
   }
   
   /**
    * 查询最新最新医嘱处理
    */
   @RequestMapping("/mediumHighRiskPatient/queryPatientvteDoctorAdvice")
   public TbVteDoctorAdvicePojo queryPatientvteDoctorAdvice(@RequestParam("doctorAdviceId")  Integer  doctorAdviceId) {
	   TbVteDoctorAdvicePojo tbVteDoctorAdvice = null;
	   try {
			tbVteDoctorAdvice = vtePatientService.queryPatientvteDoctorAdvice(doctorAdviceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbVteDoctorAdvice;
   }
   /**
	 * @param jsonString
	 * @return
	 */
   @RequestMapping("/vtePatient/addPatient")
   public R addPatient(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    String patientNumber=(String) map.get("patientNumber");
	    Map repeatMap=new HashMap();
	    repeatMap.put("patientNumber", patientNumber); 
	    TbVtePatient saveVtePatient;
		try {
			TbVtePatientHospitInfo queryVtePatientHospitInfoInfo = vtePatientHospitInfoService.queryVtePatientHospitInfoInfo(repeatMap);
			if(queryVtePatientHospitInfoInfo!=null){
				return R.error("病案号已存在");
			}else{
				saveVtePatient = vtePatientService.saveVtePatient(map);
			}
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
   @RequestMapping("/vtePatient/delVtePatientInfo")
   public R delVtePatientInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	   
		try {
			  vtePatientService.delVtePatient(map);
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
   @RequestMapping("/vtePatient/queryVtePatientInfo")
   public TbVtePatient queryVtePatientInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbVtePatient queryVtePatientInfo=null;
		try {
			   queryVtePatientInfo = vtePatientService.queryVtePatientInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  queryVtePatientInfo;
    }

}