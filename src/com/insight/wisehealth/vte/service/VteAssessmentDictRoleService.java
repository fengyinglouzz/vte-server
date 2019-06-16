package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;






import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbVteAssessmentDictRole;

/**
 * 
 * 描述:角色-评分数据字典关联表服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface VteAssessmentDictRoleService {
	
	/**
	 * 添加角色-评分数据字典关联表
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbVteAssessmentDictRole saveVteAssessmentDictRole(Map map) throws Exception ;
	
	
	/**
	 * 删除角色-评分数据字典关联表
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delVteAssessmentDictRole(Map map) throws Exception ;
	
	/**
	 * 查询角色-评分数据字典关联表(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List queryVteAssessmentDictRoleList(Map map) throws Exception;
	
	/**
	 * 查询角色-评分数据字典关联表(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countVteAssessmentDictRoleList(Map map) throws Exception;
	
	/**
	 * 查询全部角色-评分数据字典关联表(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllVteAssessmentDictRoleNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbVteAssessmentDictRole queryVteAssessmentDictRoleInfo(Map map) throws Exception;
	
	public List<Integer> queryAssessmentDicByRoleId(Map map) throws Exception;
	
	public void saveVteAssessmentDictRoleMost(Map map) throws Exception;

}
