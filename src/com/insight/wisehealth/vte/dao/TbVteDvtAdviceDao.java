package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbVteDvtAdvice;


/**
 * 
 * 描述:DVT诊断流程建议Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbVteDvtAdviceDao {
	List selectMapByPrimaryKey(Map map);
	
	TbVteDvtAdvice selectByPrimaryKey(TbVteDvtAdvice vteDvtAdvice);
	
	int deleteByPrimaryKey(TbVteDvtAdvice vteDvtAdvice);

	int insert(TbVteDvtAdvice vteDvtAdvice);
	
	int insertSelective(TbVteDvtAdvice vteDvtAdvice);
	
	int updateByPrimaryKeySelective(TbVteDvtAdvice vteDvtAdvice);
	
	int updateByPrimaryKey(TbVteDvtAdvice vteDvtAdvice);
	
	int updateByFormMap(Map map);

	List queryAllVteDvtAdvice(Map map);
	
	int countAllVteDvtAdvice(Map map);
	
	List queryAllVteDvtAdviceNP(Map map);
	
	TbVteDvtAdvice queryVteDvtAdviceInfo(Map map);
	
	
	
}
