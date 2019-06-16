package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;






import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbVteRoleModel;

/**
 * 
 * 描述:角色模块权限服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface VteRoleModelService {
	
	/**
	 * 添加角色模块权限
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbVteRoleModel saveVteRoleModel(Map map) throws Exception ;
	
	
	/**
	 * 删除角色模块权限
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delVteRoleModel(Map map) throws Exception ;
	
	/**
	 * 查询角色模块权限(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List queryVteRoleModelList(Map map) throws Exception;
	
	/**
	 * 查询角色模块权限(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countVteRoleModelList(Map map) throws Exception;
	
	/**
	 * 查询全部角色模块权限(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllVteRoleModelNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbVteRoleModel queryVteRoleModelInfo(Map map) throws Exception;
	
	public List<Integer> queryVteModelByRoleId(Map map) throws Exception;
	
	public void saveVteModelRoleMost(Map map) throws Exception;

}
