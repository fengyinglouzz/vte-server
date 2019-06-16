package com.insight.wisehealth.vte.strategyUtil;

import java.util.HashMap;
import java.util.Map;


import com.insight.core.config.SpringMvcContext;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.dao.TbVteAssessmentDao;
import com.insight.wisehealth.vte.persistence.TbVteAssessment;
import com.insight.wisehealth.vte.persistencepojo.TbVteAssessmentPojo;
import com.insight.wisehealth.vte.pojo.VteAssessmentStrategyPojo;

public class PreventionStrategyOne implements PreventionStrategy{

	
	@Override
	public VteAssessmentStrategyPojo AssessmentOfPreventionStrategies(Integer patientHospitId) {
		
		TbVteAssessmentDao vteAssessmentMapper = SpringMvcContext.getBean(TbVteAssessmentDao.class); 
		
		Map searchMap = new HashMap();
		searchMap.put("patientHospitId", patientHospitId);
		searchMap.put("pageSort", " createDt desc ");
		
		searchMap.put("assessmentType", "1");
		TbVteAssessmentPojo vteAssessment =	(TbVteAssessmentPojo)vteAssessmentMapper.queryVteAssessmentInfo(searchMap);//VTE风险评估
		searchMap.put("assessmentType", "2");
		TbVteAssessment bleedingAssessment=	(TbVteAssessmentPojo)vteAssessmentMapper.queryVteAssessmentInfo(searchMap);//出血风险评估
		searchMap.put("assessmentType", "3");
		searchMap.put("assessmentItem", "5");
		TbVteAssessment drugPrevention=	(TbVteAssessmentPojo)vteAssessmentMapper.queryVteAssessmentInfo(searchMap);//药物预防风险评估
		searchMap.put("assessmentItem", "6");
		TbVteAssessment mechanicalPrecaution = (TbVteAssessmentPojo)vteAssessmentMapper.queryVteAssessmentInfo(searchMap);//机械预防风险评估
		
		
		VteAssessmentStrategyPojo assessmentStrategyPojo = new VteAssessmentStrategyPojo();
		String assessmentStrategyName = "尚无结果，需完善评估或关注预防禁忌项";
		String assessmentStrategyType = "7";
		if(vteAssessment!=null ){
			if(vteAssessment.getAssessmentItem().equals("1")){ //Caprini评估
				//Caprini评估得分为0-2分
				if(vteAssessment.getAssessmentScore()<=2){
					//XXX=尽早活动、避免脱水==Case1：由Caprini评估得分为0-2分得出，不考虑出血风险评估及预防禁忌评估结果；
					assessmentStrategyName = "尽早活动、避免脱水";
					assessmentStrategyType = "1";
				}else if(vteAssessment.getAssessmentScore()<=4){ //Caprini评估得分为3-4分
					
					if(bleedingAssessment==null||StringUtil.isEmpty(bleedingAssessment.getAssessmentResult())
							||bleedingAssessment.getAssessmentResult().equals("5")){  //外科出血风险评估结果为无或内科出血风险评估结果为无
						
						if(drugPrevention==null||StringUtil.isEmpty(drugPrevention.getAssessmentResult())
								||drugPrevention.getAssessmentResult().equals("5")){ //药物预防禁忌评估为无
							
							if(mechanicalPrecaution==null||StringUtil.isEmpty(mechanicalPrecaution.getAssessmentResult())
									||mechanicalPrecaution.getAssessmentResult().equals("5")){ //机械预防禁忌评估为无
								
								//XXX=抗凝药物预防或机械预防===由Caprini评估得分为3-4分&外科出血风险评估结果为无或内科出血风险评估结果为无&药物预防禁忌评估为无&机械预防禁忌评估为无的结果得出；
								assessmentStrategyName = "抗凝药物预防或机械预防";
								assessmentStrategyType = "2";
							}else{	//机械预防禁忌评估为有
								
								//XXX=抗凝药物预防===Case1：由Caprini评估得分为3-4分&外科出血风险评估结果为无或内科出血风险评估结果为无&药物预防禁忌评估为无&机械预防禁忌评估为有的结果得出；
								assessmentStrategyName = "抗凝药物预防";
								assessmentStrategyType = "3";
							}
						}else{// 药物预防禁忌评估为有
							if(mechanicalPrecaution==null||StringUtil.isEmpty(mechanicalPrecaution.getAssessmentResult())
									||mechanicalPrecaution.getAssessmentResult().equals("5")){//机械预防禁忌评估为无
								
								//4、XXX=机械预防===Case2：由Caprini评估得分为3-4分&外科出血风险评估结果为无或内科出血风险评估结果为无&药物预防禁忌结果为有&机械预防禁忌评估为无的结果得出；
								assessmentStrategyName = "机械预防";
								assessmentStrategyType = "4";
							}
						}
					}else{ //外科出血风险评估结果为有或内科出血风险评估结果为有
						
						if(mechanicalPrecaution==null||StringUtil.isEmpty(mechanicalPrecaution.getAssessmentResult())
								||mechanicalPrecaution.getAssessmentResult().equals("5")){ //机械预防禁忌评估为无
							
							//4、XXX=机械预防===Case1：由Caprini评估得分为3-4分&外科出血风险评估结果为有或内科出血风险评估结果为有&不考虑药物预防禁忌结果&机械预防禁忌评估为无的结果得出；
							assessmentStrategyName = "机械预防";
							assessmentStrategyType = "4";
						}
					}
				}else if(vteAssessment.getAssessmentScore()>=5){ //Caprini评估得分为≥5分
					
					if(bleedingAssessment==null||StringUtil.isEmpty(bleedingAssessment.getAssessmentResult())
							||bleedingAssessment.getAssessmentResult().equals("5")){//外科出血风险评估结果为无或内科出血风险评估结果为无
						
						if(drugPrevention==null||StringUtil.isEmpty(drugPrevention.getAssessmentResult())
								||drugPrevention.getAssessmentResult().equals("5")){ //药物预防禁忌评估为无
							
							if(mechanicalPrecaution==null||StringUtil.isEmpty(mechanicalPrecaution.getAssessmentResult())
									||mechanicalPrecaution.getAssessmentResult().equals("5")){ //机械预防禁忌评估为无
								
								//6、XXX=抗凝药物预防+机械预防====由Caprini评估得分为≥5分&外科出血风险评估结果为无或内科出血风险评估结果为无&药物预防禁忌结果为无&机械预防禁忌评估结果为无的结果得出；
								assessmentStrategyName = "抗凝药物预防+机械预防";
								assessmentStrategyType = "6";
							}else{ //机械预防禁忌评估为有
								//XXX=抗凝药物预防===Case2：由Caprini评估得分为≥5分&外科出血风险评估结果为无或内科出血风险评估结果为无&药物预防禁忌评估为无&机械预防禁忌评估为有的结果得出；
								assessmentStrategyName = "抗凝药物预防";
								assessmentStrategyType = "3";
							}
						}else{ //药物预防禁忌结果为有
							if(mechanicalPrecaution==null||StringUtil.isEmpty(mechanicalPrecaution.getAssessmentResult())
									||mechanicalPrecaution.getAssessmentResult().equals("5")){//机械预防禁忌评估为无的结果得出
								
								//4、XXX=机械预防===Case3：由Caprini评估得分为≥5分&外科出血风险评估结果为无或内科出血风险评估结果为无&药物预防禁忌结果为有&机械预防禁忌评估为无的结果得出；
								assessmentStrategyName = "机械预防";
								assessmentStrategyType = "4";
							}
						}
					}else{ //外科出血风险评估结果为有或内科出血风险评估结果为有
						if(mechanicalPrecaution==null||StringUtil.isEmpty(mechanicalPrecaution.getAssessmentResult())
								||mechanicalPrecaution.getAssessmentResult().equals("5")){//机械预防禁忌评估为无
							
							//5、XXX=机械预防，直至出血风险消失可启用药物预防===由Caprini评估得分为≥5分&外科出血风险评估结果为有或内科出血风险评估结果为有&不考虑药物预防禁忌结果&机械预防禁忌评估为无的结果得出；
							assessmentStrategyName = "机械预防，直至出血风险消失可启用药物预防";
							assessmentStrategyType = "5";
						}/*else{ //机械预防禁忌评估为有
							assessmentStrategyName = "机械预防";
							assessmentStrategyType = "4";
						}*/
					}
				}
				
			}else if(vteAssessment.getAssessmentItem().equals("2")){ //Padua评估
				
				if(vteAssessment.getAssessmentScore()<4){ //Padua评估得分为0＜4分
					
					//XXX=尽早活动、避免脱水==Case2：由Padua评估得分为0＜4分得出，不考虑出血风险评估及预防禁忌评估结果；
					assessmentStrategyName = "尽早活动、避免脱水";
					assessmentStrategyType = "1";
				}else if(vteAssessment.getAssessmentScore()>=4){ //Padua评估得分为≥4分
					
					if(bleedingAssessment==null||StringUtil.isEmpty(bleedingAssessment.getAssessmentResult())
							||bleedingAssessment.getAssessmentResult().equals("5")){ //内科出血风险评估结果为无
						
						if(drugPrevention==null||StringUtil.isEmpty(drugPrevention.getAssessmentResult())
								||drugPrevention.getAssessmentResult().equals("5")){ //药物预防禁忌评估为无
							
							//XXX=抗凝药物预防===Case3：由Padua评估得分为≥4分&内科出血风险评估结果为无&药物预防禁忌评估为无&不考虑机械预防禁忌评估的结果得出；
							assessmentStrategyName = "抗凝药物预防";
							assessmentStrategyType = "3";
						}else{ //药物预防禁忌结果为有
							if(mechanicalPrecaution==null||StringUtil.isEmpty(mechanicalPrecaution.getAssessmentResult())
									||mechanicalPrecaution.getAssessmentResult().equals("5")){
								//4、XXX=机械预防===Case5：由Padua评估得分为≥4分&内科出血风险评估表为无&药物预防禁忌结果为有&机械预防禁忌评估为无的结果得出；
								assessmentStrategyName = "机械预防";
								assessmentStrategyType = "4";
							}
						}
					}else{ //内科出血风险评估表为有
						if(mechanicalPrecaution==null||StringUtil.isEmpty(mechanicalPrecaution.getAssessmentResult())
								||mechanicalPrecaution.getAssessmentResult().equals("5")){
							//4、XXX=机械预防===Case4：由Padua评估得分为≥4分&内科出血风险评估表为有&不考虑药物预防禁忌结果&机械预防禁忌评估为无的结果得出；
							assessmentStrategyName = "机械预防";
							assessmentStrategyType = "4";
						}
					}
				}
			}
		}
		
		
		
		assessmentStrategyPojo.setAssessmentStrategyName(assessmentStrategyName);
		assessmentStrategyPojo.setAssessmentStrategyType(assessmentStrategyType);
		return assessmentStrategyPojo;
	}

}
