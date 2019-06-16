package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbSystemUser;


/**
 * 
 * 描述:用户Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbSystemUserDao {
	List selectMapByPrimaryKey(Map map);
	
	TbSystemUser selectByPrimaryKey(TbSystemUser systemUser);
	
	int deleteByPrimaryKey(TbSystemUser systemUser);

	int insert(TbSystemUser systemUser);
	
	int insertSelective(TbSystemUser systemUser);
	
	int updateByPrimaryKeySelective(TbSystemUser systemUser);
	
	int updateByPrimaryKey(TbSystemUser systemUser);
	
	int updateByFormMap(Map map);

	List queryAllSystemUser(Map map);
	
	List queryAllSystemUserPojo(Map map);
	
	int countAllSystemUser(Map map);
	
	List queryAllSystemUserNP(Map map);
	
	TbSystemUser querySystemUserInfo(Map map);

	int saveTbSystemUser(TbSystemUser querySystemUserInfo);
	
	
	
}
