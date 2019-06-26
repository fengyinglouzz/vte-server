package com.insight.axiswevservice.service;

import java.util.List;
import java.util.Map;



import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.pojo.BatchPrintStatisticsDataPojo;
import com.insight.wisehealth.vte.pojo.MediumHighRiskPatientsAnalysisResultsPojo;
import com.insight.wisehealth.vte.pojo.OneLruAssessmentResultPojo;
@Service
public interface VtePatientHospitInfoAnalysisResultsService {
	/**
	 * 患者评估信息输出
	 * @param map
	 * @return 
	 * @return
	 * @throws Exception
	 */
	public List<MediumHighRiskPatientsAnalysisResultsPojo> batchPrintCheck(Map map);
	
	/**
    * 质控分析输出
	 * @return 
    */
	public List<BatchPrintStatisticsDataPojo> batchPrintStatisticsData(Map map) ;

	/**
	 * 患者最近评估结果
	 * @param map
	 * @return
	 */
	public OneLruAssessmentResultPojo batchPrintSingle(Map map);
	
}