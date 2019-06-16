package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbSystemDictData;


/**
 * 
 * 描述:字典数据Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbSystemDictDataDao {
	List selectMapByPrimaryKey(Map map);
	
	TbSystemDictData selectByPrimaryKey(TbSystemDictData systemDictData);
	
	int deleteByPrimaryKey(TbSystemDictData systemDictData);

	int insert(TbSystemDictData systemDictData);
	
	int insertSelective(TbSystemDictData systemDictData);
	
	int updateByPrimaryKeySelective(TbSystemDictData systemDictData);
	
	int updateByPrimaryKey(TbSystemDictData systemDictData);
	
	int updateByFormMap(Map map);

	List queryAllSystemDictData(Map map);
	
	int countAllSystemDictData(Map map);
	
	List queryAllSystemDictDataNP(Map map);
	
	TbSystemDictData querySystemDictDataInfo(Map map);
	
	
	
}
