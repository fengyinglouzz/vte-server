
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
import com.insight.wisehealth.vte.common.SessionUser;
import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;
import com.insight.wisehealth.vte.persistencepojo.TbVteDoctorAdvicePojo;
import com.insight.wisehealth.vte.pojo.BleedingQualityRiskAssessmentPojo;
import com.insight.wisehealth.vte.pojo.PatientQualityViewKpiPojo;
import com.insight.wisehealth.vte.pojo.PatientQualityViewKpiRightPojo;
import com.insight.wisehealth.vte.pojo.PrevalenceAssessmentPojo;
import com.insight.wisehealth.vte.pojo.PreventionForMiddleHighRiskPatientPojo;
import com.insight.wisehealth.vte.pojo.PreventiveRatePatientPojo;
import com.insight.wisehealth.vte.pojo.QualityRiskAssessmentPojo;
import com.insight.wisehealth.vte.service.VtePatientHospitInfoService;

/**
 * 
 * 描述:住院信息Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class VtePatientHospitInfoController  {
	@Autowired
	private VtePatientHospitInfoService vtePatientHospitInfoService;
	
	@RequestMapping("/vtePatientHospitInfo/queryList")
   public List queryList(@RequestParam("offset")  Integer  start,@RequestParam("limit") Integer  limit ,@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
    	Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		try {
			list = vtePatientHospitInfoService.queryVtePatientHospitInfoList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return list;
    }
   
   @RequestMapping("/vtePatientHospitInfo/queryCount")
   public R queryCount(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    int count = 0;
		try {
			count = vtePatientHospitInfoService.countVtePatientHospitInfoList(map);
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
   @RequestMapping("/vtePatientHospitInfo/saveVtePatientHospitInfoInfo")
   public R saveVtePatientHospitInfoInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbVtePatientHospitInfo saveVtePatientHospitInfo;
		try {
			saveVtePatientHospitInfo = vtePatientHospitInfoService.saveVtePatientHospitInfo(map);
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
   @RequestMapping("/vtePatientHospitInfo/delVtePatientHospitInfoInfo")
   public R delVtePatientHospitInfoInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	   
		try {
			  vtePatientHospitInfoService.delVtePatientHospitInfo(map);
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
   @RequestMapping("/vtePatientHospitInfo/queryVtePatientHospitInfoInfo")
   public TbVtePatientHospitInfo queryVtePatientHospitInfoInfo(@RequestParam(value="jsonString",required=false) String  jsonString) {
	    if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	    Map map = JsonUtil.getMapFromJson(jsonString);
	    TbVtePatientHospitInfo queryVtePatientHospitInfoInfo=null;
		try {
			   queryVtePatientHospitInfoInfo = vtePatientHospitInfoService.queryVtePatientHospitInfoInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return  queryVtePatientHospitInfoInfo;
    }

   
   /**
    * 质控视图KPI检测
    */
   @RequestMapping("/qualityView/queryPatientQualityViewKpi")
   public PatientQualityViewKpiPojo queryPatientQualityViewKpi(@RequestParam(value="jsonString",required=false) String  jsonString) {
	   if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	   }
	   Map map = JsonUtil.getMapFromJson(jsonString);
	   PatientQualityViewKpiPojo patientQualityViewKpi=null;
	   try {
		   patientQualityViewKpi = vtePatientHospitInfoService.queryPatientQualityViewKpi(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patientQualityViewKpi;
   }
   
   /**
    * 质控视图KPI检测右侧单独科室数据
    */
   @RequestMapping("/qualityView/queryPatientQualityViewKpiRight")
   public PatientQualityViewKpiRightPojo queryPatientQualityViewKpiRight(@RequestParam(value="jsonString",required=false) String  jsonString) {
	   if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	   Map map = JsonUtil.getMapFromJson(jsonString);
	   PatientQualityViewKpiRightPojo patientQualityViewKpiRight=null;
	   try {
		   patientQualityViewKpiRight = vtePatientHospitInfoService.queryPatientQualityViewKpiRight(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patientQualityViewKpiRight;
   }
   
   /**
    * 质控视图中高危患者预防-中高危患者数
    */
   @RequestMapping("/qualityView/queryPreventionForMiddleHighRiskPatients")
   public PreventionForMiddleHighRiskPatientPojo queryPreventionForMiddleHighRiskPatients(@RequestParam(value="jsonString",required=false) String  jsonString) {
	   if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	   Map map = JsonUtil.getMapFromJson(jsonString);
	   PreventionForMiddleHighRiskPatientPojo preventionPatient=null;
	   try {
		   preventionPatient = vtePatientHospitInfoService.queryPreventionForMiddleHighRiskPatients(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return preventionPatient;
   }
   
   /**
    * 质控视图中高危患者预防-预防措施率
    */
   @RequestMapping("/qualityView/queryPreventiveRatePatients")
   public PreventiveRatePatientPojo queryPreventiveRatePatients(@RequestParam(value="jsonString",required=false) String  jsonString) {
	   if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	   Map map = JsonUtil.getMapFromJson(jsonString);
	   PreventiveRatePatientPojo preventiveRatePatient=null;
	   try {
		   preventiveRatePatient = vtePatientHospitInfoService.queryPreventiveRatePatients(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return preventiveRatePatient;
   }
   
   /**
    * 质控视图VTE风险评估质量
    */
   @RequestMapping("/qualityView/queryVteQualityRiskAssessment")
   public QualityRiskAssessmentPojo queryVteQualityRiskAssessment(@RequestParam(value="jsonString",required=false) String  jsonString) {
	   if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	   Map map = JsonUtil.getMapFromJson(jsonString);
	   QualityRiskAssessmentPojo qualityRiskAssessment=null;
	   try {
		   qualityRiskAssessment = vtePatientHospitInfoService.queryVteQualityRiskAssessment(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qualityRiskAssessment;
   }
   
   /**
    * 质控视图出血风险评估质量
    */
   @RequestMapping("/qualityView/queryBleedingQualityRiskAssessment")
   public BleedingQualityRiskAssessmentPojo queryBleedingQualityRiskAssessment(@RequestParam(value="jsonString",required=false) String  jsonString) {
	   if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	   Map map = JsonUtil.getMapFromJson(jsonString);
	   BleedingQualityRiskAssessmentPojo bleedingQualityRiskAssessment=null;
	   try {
		   bleedingQualityRiskAssessment = vtePatientHospitInfoService.queryBleedingQualityRiskAssessment(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bleedingQualityRiskAssessment;
   }
   
   /**
    * 质控视图患病情况
    */
   @RequestMapping("/qualityView/queryPrevalenceAssessment")
   public PrevalenceAssessmentPojo queryPrevalenceAssessment(@RequestParam(value="jsonString",required=false) String  jsonString) {
	   if(StringUtil.isEmpty(jsonString)){
	    	jsonString = "{}";
	    }
	   Map map = JsonUtil.getMapFromJson(jsonString);
	   PrevalenceAssessmentPojo prevalenceAssessment=null;
	   try {
		   prevalenceAssessment = vtePatientHospitInfoService.queryPrevalenceAssessment(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prevalenceAssessment;
   }
}