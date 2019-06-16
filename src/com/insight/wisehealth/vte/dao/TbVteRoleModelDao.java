package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbVteRoleModel;


/**
 * 
 * 描述:角色模块权限Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbVteRoleModelDao {
	List selectMapByPrimaryKey(Map map);
	
	TbVteRoleModel selectByPrimaryKey(TbVteRoleModel vteRoleModel);
	
	int deleteByPrimaryKey(TbVteRoleModel vteRoleModel);

	int insert(TbVteRoleModel vteRoleModel);
	
	int insertSelective(TbVteRoleModel vteRoleModel);
	
	int updateByPrimaryKeySelective(TbVteRoleModel vteRoleModel);
	
	int updateByPrimaryKey(TbVteRoleModel vteRoleModel);
	
	int updateByFormMap(Map map);

	List queryAllVteRoleModel(Map map);
	
	int countAllVteRoleModel(Map map);
	
	List queryAllVteRoleModelNP(Map map);
	
	TbVteRoleModel queryVteRoleModelInfo(Map map);
	
	List<Integer> queryVteModelByRoleId(Map map);
	
	
	
}
