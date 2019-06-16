package com.insight.wisehealth.vte.strategyUtil;

import org.springframework.beans.factory.annotation.Autowired;

import com.insight.wisehealth.vte.dao.TbVteAssessmentDao;
import com.insight.wisehealth.vte.pojo.VteAssessmentStrategyPojo;

public interface PreventionStrategy {
/**
 * 
 
 * @param patientHospitId 患者ID
 * @return
 */
	
	
	public VteAssessmentStrategyPojo AssessmentOfPreventionStrategies(Integer patientHospitId);
}
