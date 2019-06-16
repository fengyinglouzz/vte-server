package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.loginpojo.LoginModelPojo;
import com.insight.wisehealth.vte.persistence.TbVteModel;
import com.insight.wisehealth.vte.pojo.VteModelTreePojo;


/**
 * 
 * 描述:模块表Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbVteModelDao {
	List selectMapByPrimaryKey(Map map);
	
	TbVteModel selectByPrimaryKey(TbVteModel vteModel);
	
	int deleteByPrimaryKey(TbVteModel vteModel);

	int insert(TbVteModel vteModel);
	
	int insertSelective(TbVteModel vteModel);
	
	int updateByPrimaryKeySelective(TbVteModel vteModel);
	
	int updateByPrimaryKey(TbVteModel vteModel);
	
	int updateByFormMap(Map map);

	List queryAllVteModel(Map map);
	
	int countAllVteModel(Map map);
	
	List queryAllVteModelNP(Map map);
	
	TbVteModel queryVteModelInfo(Map map);
	
	List<LoginModelPojo> queryLoginModelPojoList(Map map);
	
	String queryMaxCodeByPaCode(Map map);
	
	List<VteModelTreePojo>queryAllModelByRole(Map map);
	
	List<String>queryAllowUrlList(Map map);
	
	
	
}
