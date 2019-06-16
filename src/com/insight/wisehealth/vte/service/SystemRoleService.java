package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;




import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbSystemRole;

/**
 * 
 * 描述:角色服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface SystemRoleService {
	
	/**
	 * 添加角色
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbSystemRole saveSystemRole(Map map) throws Exception ;
	
	
	/**
	 * 删除角色
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delSystemRole(Map map) throws Exception ;
	
	/**
	 * 查询角色(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List querySystemRoleList(Map map) throws Exception;
	
	/**
	 * 查询角色(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countSystemRoleList(Map map) throws Exception;
	
	/**
	 * 查询全部角色(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllSystemRoleNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbSystemRole querySystemRoleInfo(Map map) throws Exception;

}
