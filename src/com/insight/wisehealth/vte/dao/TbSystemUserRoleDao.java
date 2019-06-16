package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbSystemUserRole;


/**
 * 
 * 描述:用户角色Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbSystemUserRoleDao {
	List selectMapByPrimaryKey(Map map);
	
	TbSystemUserRole selectByPrimaryKey(TbSystemUserRole systemUserRole);
	
	int deleteByPrimaryKey(TbSystemUserRole systemUserRole);

	int insert(TbSystemUserRole systemUserRole);
	
	int insertSelective(TbSystemUserRole systemUserRole);
	
	int updateByPrimaryKeySelective(TbSystemUserRole systemUserRole);
	
	int updateByPrimaryKey(TbSystemUserRole systemUserRole);
	
	int updateByFormMap(Map map);

	List queryAllSystemUserRole(Map map);
	
	int countAllSystemUserRole(Map map);
	
	List queryAllSystemUserRoleNP(Map map);
	
	TbSystemUserRole querySystemUserRoleInfo(Map map);

	void saveTbSystemUserRole(TbSystemUserRole tbSystemUserRole);
	
	
	
}
