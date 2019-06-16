package com.insight.wisehealth.vte.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;

import com.insight.wisehealth.vte.dao.TbSystemUserRoleDao;
import com.insight.wisehealth.vte.persistence.TbSystemUserRole;
import com.insight.wisehealth.vte.service.SystemUserRoleService;


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
public class SystemUserRoleServiceImpl  implements SystemUserRoleService{
	@Autowired
	TbSystemUserRoleDao systemUserRoleMapper;
	@Override
	public TbSystemUserRole saveSystemUserRole(Map map) throws Exception{
		TbSystemUserRole tbSystemUserRole = new TbSystemUserRole();
		tbSystemUserRole = (TbSystemUserRole) ToolUtil.converMapToObject(map,TbSystemUserRole.class);
		if (null == tbSystemUserRole.getUserRoleId()) {
			systemUserRoleMapper.insert(tbSystemUserRole);
		} else {
			systemUserRoleMapper.updateByFormMap(map);
		}
		return tbSystemUserRole;
	}

	@Override
	public void delSystemUserRole(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbSystemUserRole tbSystemUserRole = new TbSystemUserRole();
			tbSystemUserRole.setUserRoleId(Integer.parseInt(id[i]));
			systemUserRoleMapper.deleteByPrimaryKey(tbSystemUserRole);
		}
	}

	@Override
	public List querySystemUserRoleList(Map map) throws Exception {
		List list = systemUserRoleMapper.queryAllSystemUserRole(map);
		return list;
	}

	@Override
	public int countSystemUserRoleList(Map map) throws Exception {
		int count = (int)systemUserRoleMapper.countAllSystemUserRole(map);
		return count;
	}

	@Override
	public List queryAllSystemUserRoleNp(Map map) throws Exception{
		List list = new ArrayList();
		list = systemUserRoleMapper.queryAllSystemUserRoleNP( map);
		return list;
	}
	
	@Override
	public TbSystemUserRole querySystemUserRoleInfo(Map map) throws Exception{
		TbSystemUserRole tbSystemUserRole =	(TbSystemUserRole)systemUserRoleMapper.querySystemUserRoleInfo(map);
		return tbSystemUserRole;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 用户角色
	 * @param list
	 * @param deleteSystemUserRoleIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveSystemUserRoleByGroup (List list,String deleteSystemUserRoleIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteSystemUserRoleIds)){
			String deleteSystemUserRoleId[] =  deleteSystemUserRoleIds.split(",");
			for(int i=0;i<deleteSystemUserRoleId.length;i++){
				if(!StringUtil.isEmpty(deleteSystemUserRoleId[i])){
					TbSystemUserRole tbSystemUserRole = new TbSystemUserRole();
					tbSystemUserRole.setUserRoleId(Integer.parseInt(deleteSystemUserRoleId[i]));
					systemUserRoleMapper.deleteByPrimaryKey(tbSystemUserRole);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbSystemUserRole tbSystemUserRole
				= (TbSystemUserRole) ToolUtil.converMapToObject(map,TbSystemUserRole.class);
				if (null == tbSystemUserRole.getUserRoleId()) {
					//TODO 设置Pid 
					systemUserRoleMapper.insertSelective(tbSystemUserRole);
				} else {
					systemUserRoleMapper.updateByFormMap(map);
				}
			}
		}
	}
}
