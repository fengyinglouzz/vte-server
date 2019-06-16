package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;




import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbSystemUserRole;

/**
 * 
 * 描述:用户角色服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface SystemUserRoleService {
	
	/**
	 * 添加用户角色
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbSystemUserRole saveSystemUserRole(Map map) throws Exception ;
	
	
	/**
	 * 删除用户角色
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delSystemUserRole(Map map) throws Exception ;
	
	/**
	 * 查询用户角色(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List querySystemUserRoleList(Map map) throws Exception;
	
	/**
	 * 查询用户角色(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countSystemUserRoleList(Map map) throws Exception;
	
	/**
	 * 查询全部用户角色(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllSystemUserRoleNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbSystemUserRole querySystemUserRoleInfo(Map map) throws Exception;

}
