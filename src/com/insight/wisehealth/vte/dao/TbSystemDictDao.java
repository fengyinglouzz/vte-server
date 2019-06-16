package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbSystemDict;


/**
 * 
 * 描述:字典Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbSystemDictDao {
	List selectMapByPrimaryKey(Map map);
	
	TbSystemDict selectByPrimaryKey(TbSystemDict systemDict);
	
	int deleteByPrimaryKey(TbSystemDict systemDict);

	int insert(TbSystemDict systemDict);
	
	int insertSelective(TbSystemDict systemDict);
	
	int updateByPrimaryKeySelective(TbSystemDict systemDict);
	
	int updateByPrimaryKey(TbSystemDict systemDict);
	
	int updateByFormMap(Map map);

	List queryAllSystemDict(Map map);
	
	int countAllSystemDict(Map map);
	
	List queryAllSystemDictNP(Map map);
	
	TbSystemDict querySystemDictInfo(Map map);
	
	List<Map> queryLocalDictCode(Map map);
	
	List<Map> queryLocalDictData(Map map);
	
}
