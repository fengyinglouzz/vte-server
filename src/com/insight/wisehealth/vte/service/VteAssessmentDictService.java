package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;





import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbVteAssessmentDict;
import com.insight.wisehealth.vte.pojo.VteAssessmentDictTreePojo;

/**
 * 
 * 描述:评分数据项字典服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface VteAssessmentDictService {
	
	/**
	 * 添加评分数据项字典
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbVteAssessmentDict saveVteAssessmentDict(Map map) throws Exception ;
	
	
	/**
	 * 删除评分数据项字典
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delVteAssessmentDict(Map map) throws Exception ;
	
	/**
	 * 查询评分数据项字典(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List queryVteAssessmentDictList(Map map) throws Exception;
	
	/**
	 * 查询评分数据项字典(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countVteAssessmentDictList(Map map) throws Exception;
	
	/**
	 * 查询全部评分数据项字典(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllVteAssessmentDictNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbVteAssessmentDict queryVteAssessmentDictInfo(Map map) throws Exception;
	
	
	public List<VteAssessmentDictTreePojo> queryVteAssessmentDictTreePojo(Map map)throws Exception;

}
