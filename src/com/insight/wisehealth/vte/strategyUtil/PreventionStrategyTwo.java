package com.insight.wisehealth.vte.strategyUtil;

import java.util.HashMap;
import java.util.Map;

import com.insight.core.config.SpringMvcContext;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.dao.TbVteAssessmentDao;
import com.insight.wisehealth.vte.persistence.TbVteAssessment;
import com.insight.wisehealth.vte.persistencepojo.TbVteAssessmentPojo;
import com.insight.wisehealth.vte.pojo.VteAssessmentStrategyPojo;

public class PreventionStrategyTwo  implements PreventionStrategy{

	
	@Override
	public VteAssessmentStrategyPojo AssessmentOfPreventionStrategies(Integer patientHospitId) {
		
		TbVteAssessmentDao vteAssessmentMapper = SpringMvcContext.getBean(TbVteAssessmentDao.class); 
		
		VteAssessmentStrategyPojo assessmentStrategyPojo = new VteAssessmentStrategyPojo();
		String assessmentStrategyName = "尚无结果，需完善评估或关注预防禁忌项";
		String assessmentStrategyType = "7";
		Map searchMap = new HashMap();
		searchMap.put("patientHospitId", patientHospitId);
		searchMap.put("pageSort", " createDt desc ");
		
		searchMap.put("assessmentType", "1");
		TbVteAssessmentPojo vteAssessment =	(TbVteAssessmentPojo)vteAssessmentMapper.queryVteAssessmentInfo(searchMap);
		searchMap.put("assessmentType", "2");
		TbVteAssessment bleedingAssessment=	(TbVteAssessmentPojo)vteAssessmentMapper.queryVteAssessmentInfo(searchMap);
		
		
		if(vteAssessment!=null ){
			if(vteAssessment.getAssessmentItem().equals("1")){ //Caprini评估
				if(vteAssessment.getAssessmentScore()<=2){
					assessmentStrategyName = "尽早活动、避免脱水";
					assessmentStrategyType = "1";
				}else if(vteAssessment.getAssessmentScore()<=4){
					if(bleedingAssessment==null||StringUtil.isEmpty(bleedingAssessment.getAssessmentResult())
							||bleedingAssessment.getAssessmentResult().equals("5")){
						assessmentStrategyName = "抗凝药物预防或机械预防";
						assessmentStrategyType = "2";
					}else{
						assessmentStrategyName = "机械预防";
						assessmentStrategyType = "4";
					}
				}else if(vteAssessment.getAssessmentScore()>=5){
					if(bleedingAssessment==null||StringUtil.isEmpty(bleedingAssessment.getAssessmentResult())
							||bleedingAssessment.getAssessmentResult().equals("5")){
						assessmentStrategyName = "抗凝药物预防+机械预防";
						assessmentStrategyType = "6";
					}else{
						assessmentStrategyName = "机械预防，直至出血风险消失可启用药物预防";
						assessmentStrategyType = "5";
					}
				}
			}else if(vteAssessment.getAssessmentItem().equals("2")){ //Padua评估
				if(vteAssessment.getAssessmentScore()<=4){
					assessmentStrategyName = "尽早活动、避免脱水";
					assessmentStrategyType = "1";
				}else if(vteAssessment.getAssessmentScore()>=4){
					if(bleedingAssessment==null||StringUtil.isEmpty(bleedingAssessment.getAssessmentResult())
							||bleedingAssessment.getAssessmentResult().equals("5")){
						assessmentStrategyName = "抗凝药物预防";
						assessmentStrategyType = "3";
					}else{
						assessmentStrategyName = "机械预防";
						assessmentStrategyType = "4";
					}
				}
			}
		}
		
		assessmentStrategyPojo.setAssessmentStrategyName(assessmentStrategyName);
		assessmentStrategyPojo.setAssessmentStrategyType(assessmentStrategyType);
		return assessmentStrategyPojo;
	}

}
