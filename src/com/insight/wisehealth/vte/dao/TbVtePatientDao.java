package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbVtePatient;
import com.insight.wisehealth.vte.pojo.VtePatientPojo;


/**
 * 
 * 描述:患者数据表Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbVtePatientDao {
	List selectMapByPrimaryKey(Map map);
	
	TbVtePatient selectByPrimaryKey(TbVtePatient vtePatient);
	
	int deleteByPrimaryKey(TbVtePatient vtePatient);

	int insert(TbVtePatient vtePatient);
	
	int insertSelective(TbVtePatient vtePatient);
	
	int updateByPrimaryKeySelective(TbVtePatient vtePatient);
	
	int updateByPrimaryKey(TbVtePatient vtePatient);
	
	int updateByFormMap(Map map);

	List queryAllVtePatient(Map map);
	
	int countAllVtePatient(Map map);
	
	List queryAllVtePatientNP(Map map);
	
	TbVtePatient queryVtePatientInfo(Map map);

	List queryMediumHighRiskPatientsList(Map map);

	int queryLatestBleedingRiskVteAssessment(Map mapA);

	int queryLatestBleedingRiskVteDoctorAdvice(Map mapA);

	int queryMediumHighRiskPatientsCount(Map map);

	List queryMediumHighRiskPatientsDeptCount(Map map);

	List queryLowMediumHighRiskPatientsDeptCount(Map map);

	List queryPatientVteAssessmentDictList(String assessmentId);
	
	
	
	List<VtePatientPojo> queryAllVtePatientAndHospitData(Map map);
	
	int countAllVtePatientAndHospitData(Map map);

	List<Map> queryMediumHighRiskPatientsListNp(Map map);

	int saveTbVtePatient(TbVtePatient patientInfo);
	
}
