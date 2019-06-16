package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.loginpojo.LoginRolePojo;
import com.insight.wisehealth.vte.persistence.TbSystemRole;


/**
 * 
 * 描述:角色Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbSystemRoleDao {
	List selectMapByPrimaryKey(Map map);
	
	TbSystemRole selectByPrimaryKey(TbSystemRole systemRole);
	
	int deleteByPrimaryKey(TbSystemRole systemRole);

	int insert(TbSystemRole systemRole);
	
	int insertSelective(TbSystemRole systemRole);
	
	int updateByPrimaryKeySelective(TbSystemRole systemRole);
	
	int updateByPrimaryKey(TbSystemRole systemRole);
	
	int updateByFormMap(Map map);

	List queryAllSystemRole(Map map);
	
	int countAllSystemRole(Map map);
	
	List queryAllSystemRoleNP(Map map);
	
	TbSystemRole querySystemRoleInfo(Map map);
	
	LoginRolePojo queryLoginRolePojo(Map map);
	
	List  checkRoleCodeAndName(Map map);
	
	
	
}
