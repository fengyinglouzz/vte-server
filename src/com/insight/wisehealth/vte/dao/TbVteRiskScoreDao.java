package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbVteRiskScore;


/**
 * 
 * 描述:VTE风险分度Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbVteRiskScoreDao {
	List selectMapByPrimaryKey(Map map);
	
	TbVteRiskScore selectByPrimaryKey(TbVteRiskScore vteRiskScore);
	
	int deleteByPrimaryKey(TbVteRiskScore vteRiskScore);

	int insert(TbVteRiskScore vteRiskScore);
	
	int insertSelective(TbVteRiskScore vteRiskScore);
	
	int updateByPrimaryKeySelective(TbVteRiskScore vteRiskScore);
	
	int updateByPrimaryKey(TbVteRiskScore vteRiskScore);
	
	int updateByFormMap(Map map);

	List queryAllVteRiskScore(Map map);
	
	int countAllVteRiskScore(Map map);
	
	List queryAllVteRiskScoreNP(Map map);
	
	TbVteRiskScore queryVteRiskScoreInfo(Map map);
	
	
	
}
