package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbVteAssessmentDictRole;


/**
 * 
 * 描述:角色-评分数据字典关联表Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbVteAssessmentDictRoleDao {
	List selectMapByPrimaryKey(Map map);
	
	TbVteAssessmentDictRole selectByPrimaryKey(TbVteAssessmentDictRole vteAssessmentDictRole);
	
	int deleteByPrimaryKey(TbVteAssessmentDictRole vteAssessmentDictRole);

	int insert(TbVteAssessmentDictRole vteAssessmentDictRole);
	
	int insertSelective(TbVteAssessmentDictRole vteAssessmentDictRole);
	
	int updateByPrimaryKeySelective(TbVteAssessmentDictRole vteAssessmentDictRole);
	
	int updateByPrimaryKey(TbVteAssessmentDictRole vteAssessmentDictRole);
	
	int updateByFormMap(Map map);

	List queryAllVteAssessmentDictRole(Map map);
	
	int countAllVteAssessmentDictRole(Map map);
	
	List queryAllVteAssessmentDictRoleNP(Map map);
	
	TbVteAssessmentDictRole queryVteAssessmentDictRoleInfo(Map map);
	
	List<Integer> queryAssessmentDicByRoleId(Map map);
}
