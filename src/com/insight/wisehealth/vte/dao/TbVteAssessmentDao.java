package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbVteAssessment;
import com.insight.wisehealth.vte.persistencepojo.TbVteAssessmentPojo;
import com.insight.wisehealth.vte.pojo.VteAssessmentAndListPojo;
import com.insight.wisehealth.vte.pojo.VtePatientAssessmentPojo;


/**
 * 
 * 描述:风险评估Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbVteAssessmentDao {
	List selectMapByPrimaryKey(Map map);
	
	TbVteAssessment selectByPrimaryKey(TbVteAssessment vteAssessment);
	
	int deleteByPrimaryKey(TbVteAssessment vteAssessment);

	int insert(TbVteAssessment vteAssessment);
	
	int insertSelective(TbVteAssessment vteAssessment);
	
	int updateByPrimaryKeySelective(TbVteAssessment vteAssessment);
	
	int updateByPrimaryKey(TbVteAssessment vteAssessment);
	
	int updateByFormMap(Map map);

	List queryAllVteAssessment(Map map);
	
	List queryAllVteAssessmentWithAdvice(Map map);
	
	List queryAllVteOnlyAssessment(Map map);
	
	List queryAllVteOnlyAdvice(Map map);
	
	int countAllVteAssessment(Map map);
	
	int countAllVteAssessmentWithAdvice(Map map);
	
	int countAllVteOnlyAssessment(Map map);
	
	int countAllVteOnlyAdvice(Map map);
	
	List queryAllVteAssessmentNP(Map map);
	
	TbVteAssessmentPojo queryVteAssessmentInfo(Map map);
	
	VtePatientAssessmentPojo queryPatientAssessmentInfo(Map map);
	
	int countAllVteAssessmentForDegree(Map map);
	
	List queryVteAssessmentTypePojoNP(List list);
	
	List queryAllVteAssessmentWithAdviceForExport(Map map);
	
	List queryAllVteOnlyAssessmentForExport(Map map);
	
	List queryAllVteOnlyAdviceForExport(Map map);
	
	
	VteAssessmentAndListPojo queryAssessmentViewForAssessment(Map map);
	
	VteAssessmentAndListPojo queryAssessmentViewForAdvice(Map map);

	Map queryVteAssessmentAnalysisResults(Map mapA);

	void saveTbVteAssessment(TbVteAssessment assessment);
	
	void saveVteAssessmentAfterInsert(Map map);

	Map queryLruVteAssessmentAnalysisResults(Map mapA);
	
}
