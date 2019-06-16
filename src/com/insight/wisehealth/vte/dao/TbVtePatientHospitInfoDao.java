package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;


/**
 * 
 * 描述:住院信息Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbVtePatientHospitInfoDao {
	List selectMapByPrimaryKey(Map map);
	
	TbVtePatientHospitInfo selectByPrimaryKey(TbVtePatientHospitInfo vtePatientHospitInfo);
	
	int deleteByPrimaryKey(TbVtePatientHospitInfo vtePatientHospitInfo);

	int insert(TbVtePatientHospitInfo vtePatientHospitInfo);
	
	int insertSelective(TbVtePatientHospitInfo vtePatientHospitInfo);
	
	int updateByPrimaryKeySelective(TbVtePatientHospitInfo vtePatientHospitInfo);
	
	int updateByPrimaryKey(TbVtePatientHospitInfo vtePatientHospitInfo);
	
	int updateByFormMap(Map map);

	List queryAllVtePatientHospitInfo(Map map);
	
	int countAllVtePatientHospitInfo(Map map);
	
	List queryAllVtePatientHospitInfoNP(Map map);
	
	TbVtePatientHospitInfo queryVtePatientHospitInfoInfo(Map map);

	List<Map> queryNumberPatient(Map map);

	List<Map> queryOnsetOfNumberPatient(Map map);

	List<Map> queryVteRiskAssessmentPatient(Map map);

	List<Map> queryCapriniMiddleRiskList(Map map);

	List<Map> queryCapriniPaduaRiskList(Map map);

	List<Map> queryCapriniPaduaRiskSumList(Map map);

	List<Map> queryPreventiveNumberList(Map map);

	List<Map> queryRecentlyBleedingRiskSumList(Map map);

	List<Map> queryPrevalenceAssessment(Map map);

	List<Map> queryNwPrevalenceAssessment(Map map);
	
	Map queryVteDepartmentInfo(Map map);
	
	List<Integer> queryOatientHospitIdBypatientDepartment(Map map);

	List<Map> queryRecentlyBleedingRiskList(Map map);

	int saveTbVtePatientHospitInfo(TbVtePatientHospitInfo vtePatientHospitInfo);
	
}
