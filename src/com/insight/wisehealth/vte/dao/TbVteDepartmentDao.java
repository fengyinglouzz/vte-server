package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbVteDepartment;


/**
 * 
 * 描述:患者科室（用于方便日后统计使用）Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbVteDepartmentDao {
	List selectMapByPrimaryKey(Map map);
	
	TbVteDepartment selectByPrimaryKey(TbVteDepartment vteDepartment);
	
	int deleteByPrimaryKey(TbVteDepartment vteDepartment);

	int insert(TbVteDepartment vteDepartment);
	
	int insertSelective(TbVteDepartment vteDepartment);
	
	int updateByPrimaryKeySelective(TbVteDepartment vteDepartment);
	
	int updateByPrimaryKey(TbVteDepartment vteDepartment);
	
	int updateByFormMap(Map map);

	List queryAllVteDepartment(Map map);
	
	int countAllVteDepartment(Map map);
	
	List queryAllVteDepartmentNP(Map map);
	
	TbVteDepartment queryVteDepartmentInfo(Map map);
	
	int insertVteDepartmentByName(Map map);
	
	int saveVteDepartmentByName(Map map);

	List<Map> queryAllVteDepartmentOrg(Map map);
	
}
