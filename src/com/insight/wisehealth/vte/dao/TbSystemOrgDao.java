package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.loginpojo.LoginDepartmentPojo;
import com.insight.wisehealth.vte.loginpojo.LoginHospitalPojo;
import com.insight.wisehealth.vte.persistence.TbSystemOrg;


/**
 * 
 * 描述:机构Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbSystemOrgDao {
	List selectMapByPrimaryKey(Map map);
	
	TbSystemOrg selectByPrimaryKey(TbSystemOrg systemOrg);
	
	int deleteByPrimaryKey(TbSystemOrg systemOrg);

	int insert(TbSystemOrg systemOrg);
	
	int insertSelective(TbSystemOrg systemOrg);
	
	int updateByPrimaryKeySelective(TbSystemOrg systemOrg);
	
	int updateByPrimaryKey(TbSystemOrg systemOrg);
	
	int updateByFormMap(Map map);

	List queryAllSystemOrg(Map map);
	
	int countAllSystemOrg(Map map);
	
	List queryAllSystemOrgNP(Map map);
	
	List queryAllSystemDepartmentNP(Map map);
	
	TbSystemOrg querySystemOrgInfo(Map map);
	
	LoginDepartmentPojo queryLoginDepartmentPojo(Map map);
	
	LoginHospitalPojo queryLoginHospitalPojo(Map map);
	
	String queryMaxCodeByPaCode(Map map);

	void saveTbSystemOrg(TbSystemOrg tbSystemOrg);
	
	
	
}
