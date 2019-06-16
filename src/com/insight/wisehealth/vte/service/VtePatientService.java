package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;











import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbVteAssessmentDict;
import com.insight.wisehealth.vte.persistence.TbVteDoctorAdvice;
import com.insight.wisehealth.vte.persistence.TbVtePatient;
import com.insight.wisehealth.vte.persistencepojo.TbVteDoctorAdvicePojo;
import com.insight.wisehealth.vte.pojo.MediumHighRiskPatients;
import com.insight.wisehealth.vte.pojo.MediumHighRiskPatientsCountPojo;

/**
 * 
 * 描述:患者数据表服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface VtePatientService {
	
	/**
	 * 添加患者数据表
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbVtePatient saveVtePatient(Map map) throws Exception ;
	
	
	/**
	 * 删除患者数据表
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delVtePatient(Map map) throws Exception ;
	
	/**
	 * 查询患者数据表(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List queryVtePatientList(Map map) throws Exception;
	
	/**
	 * 查询患者数据表(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countVtePatientList(Map map) throws Exception;
	
	/**
	 * 查询全部患者数据表(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllVtePatientNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbVtePatient queryVtePatientInfo(Map map) throws Exception;

	/**
	 * 查询中高危检测患者列表(分页)
	 * @param map
	 * @return list
	 * @throws Exception
	 */
	public List<MediumHighRiskPatients> queryMediumHighRiskPatientsList(Map map);

	/**
	 * 查询中高危检测患者数
	 * @param map 
	 * @return int
	 * @throws Exception
	 */
	public int queryMediumHighRiskPatientsCount(Map map);

	/**
	 * 查询各科室中高危检测患者数
	 * @param map 
	 * @return list
	 * @throws Exception
	 */
	public List<MediumHighRiskPatientsCountPojo> queryMediumHighRiskPatientsDeptCount(Map map);

	/**
	 * 查询最新出血风险评估
	 * @param Integer
	 * @return list
	 * @throws Exception
	 */
	public List<TbVteAssessmentDict> queryPatientVteAssessmentDictList(String assessmentId);

	 /**
	  * 查询最新最新医嘱处理
	 * @param Integer
	  * @return TbVteDoctorAdvice
	  */
	public TbVteDoctorAdvicePojo queryPatientvteDoctorAdvice(Integer doctorAdviceId);

}
