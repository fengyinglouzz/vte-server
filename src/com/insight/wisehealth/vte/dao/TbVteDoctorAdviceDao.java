package com.insight.wisehealth.vte.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.insight.wisehealth.vte.persistence.TbVteDoctorAdvice;


/**
 * 
 * 描述:医嘱处理Dao
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Repository
public interface TbVteDoctorAdviceDao {
	List selectMapByPrimaryKey(Map map);
	
	TbVteDoctorAdvice selectByPrimaryKey(TbVteDoctorAdvice vteDoctorAdvice);
	
	int deleteByPrimaryKey(TbVteDoctorAdvice vteDoctorAdvice);

	int insert(TbVteDoctorAdvice vteDoctorAdvice);
	
	int insertSelective(TbVteDoctorAdvice vteDoctorAdvice);
	
	int updateByPrimaryKeySelective(TbVteDoctorAdvice vteDoctorAdvice);
	
	int updateByPrimaryKey(TbVteDoctorAdvice vteDoctorAdvice);
	
	int updateByFormMap(Map map);

	List queryAllVteDoctorAdvice(Map map);
	
	int countAllVteDoctorAdvice(Map map);
	
	List queryAllVteDoctorAdviceNP(Map map);
	
	TbVteDoctorAdvice queryVteDoctorAdviceInfo(Map map);

	void saveTbVteDoctorAdvice(TbVteDoctorAdvice doctorAdvice);
	
	void vteDoctorAdviceAfterInsert(Map map);
	
}
