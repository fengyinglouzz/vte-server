package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;











import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;
import com.insight.wisehealth.vte.pojo.BleedingQualityRiskAssessmentPojo;
import com.insight.wisehealth.vte.pojo.PatientQualityViewKpiPojo;
import com.insight.wisehealth.vte.pojo.PatientQualityViewKpiRightPojo;
import com.insight.wisehealth.vte.pojo.PrevalenceAssessmentPojo;
import com.insight.wisehealth.vte.pojo.PreventionForMiddleHighRiskPatientPojo;
import com.insight.wisehealth.vte.pojo.PreventiveRatePatientPojo;
import com.insight.wisehealth.vte.pojo.QualityRiskAssessmentPojo;

/**
 * 
 * 描述:住院信息服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface VtePatientHospitInfoService {
	
	/**
	 * 添加住院信息
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbVtePatientHospitInfo saveVtePatientHospitInfo(Map map) throws Exception ;
	
	
	/**
	 * 删除住院信息
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delVtePatientHospitInfo(Map map) throws Exception ;
	
	/**
	 * 查询住院信息(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List queryVtePatientHospitInfoList(Map map) throws Exception;
	
	/**
	 * 查询住院信息(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countVtePatientHospitInfoList(Map map) throws Exception;
	
	/**
	 * 查询全部住院信息(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllVtePatientHospitInfoNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbVtePatientHospitInfo queryVtePatientHospitInfoInfo(Map map) throws Exception;

	/**
    * 质控视图KPI检测
    */
	public PatientQualityViewKpiPojo queryPatientQualityViewKpi(Map map);

	/**
    * 质控视图中高危患者预防-中高危患者数
    */
	public PreventionForMiddleHighRiskPatientPojo queryPreventionForMiddleHighRiskPatients(Map map);

   /**
    * 质控视图中高危患者预防-预防措施率
    */
	public PreventiveRatePatientPojo queryPreventiveRatePatients(Map map);

	/**
    * 质控视图VTE风险评估质量
    */
	public QualityRiskAssessmentPojo queryVteQualityRiskAssessment(Map map);

	/**
    * 质控视图出血风险评估质量
    */
	public BleedingQualityRiskAssessmentPojo queryBleedingQualityRiskAssessment(Map map);

	/**
    * 质控视图患病情况
    */
	public PrevalenceAssessmentPojo queryPrevalenceAssessment(Map map);

	/**
    * 质控视图KPI检测
    */
	public PatientQualityViewKpiRightPojo queryPatientQualityViewKpiRight(Map map);

}
