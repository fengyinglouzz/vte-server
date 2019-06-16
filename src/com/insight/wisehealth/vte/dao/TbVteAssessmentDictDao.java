package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.loginpojo.LoginAssessmentDictPojo;
import com.insight.wisehealth.vte.persistence.TbVteAssessmentDict;
import com.insight.wisehealth.vte.pojo.VteAssessmentDictPojo;


/**
 * 
 * 描述:评分数据项字典Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbVteAssessmentDictDao {
	List selectMapByPrimaryKey(Map map);
	
	TbVteAssessmentDict selectByPrimaryKey(TbVteAssessmentDict vteAssessmentDict);
	
	int deleteByPrimaryKey(TbVteAssessmentDict vteAssessmentDict);

	int insert(TbVteAssessmentDict vteAssessmentDict);
	
	int insertSelective(TbVteAssessmentDict vteAssessmentDict);
	
	int updateByPrimaryKeySelective(TbVteAssessmentDict vteAssessmentDict);
	
	int updateByPrimaryKey(TbVteAssessmentDict vteAssessmentDict);
	
	int updateByFormMap(Map map);

	List queryAllVteAssessmentDict(Map map);
	
	int countAllVteAssessmentDict(Map map);
	
	List queryAllVteAssessmentDictNP(Map map);
	
	TbVteAssessmentDict queryVteAssessmentDictInfo(Map map);
	
	List<LoginAssessmentDictPojo> queryLoginAssessmentDictPojoList(Map map);
	
	List<VteAssessmentDictPojo> queryAllVteAssessmentDictPojo(Map map);
	
	
	
}
