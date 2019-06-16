package com.insight.wisehealth.vte.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;
import com.insight.wisehealth.vte.common.CachedDict;
import com.insight.wisehealth.vte.dao.TbSystemRoleDao;
import com.insight.wisehealth.vte.persistence.TbSystemRole;
import com.insight.wisehealth.vte.service.SystemRoleService;


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
public class SystemRoleServiceImpl  implements SystemRoleService{
	@Autowired
	TbSystemRoleDao systemRoleMapper;
	@Override
	public TbSystemRole saveSystemRole(Map map) throws Exception{
		TbSystemRole tbSystemRole = new TbSystemRole();
		tbSystemRole = (TbSystemRole) ToolUtil.converMapToObject(map,TbSystemRole.class);
		//校验角色名称和角色编码不能重复
		Map searchmap = new HashMap();
		searchmap.put("roleCode", tbSystemRole.getRoleCode());
		searchmap.put("roleName", tbSystemRole.getRoleName());
		searchmap.put("roleId", tbSystemRole.getRoleId());
		List roleCodelist = systemRoleMapper.checkRoleCodeAndName( searchmap);
		if(roleCodelist.size()>0){
			Map checkmap = (Map)roleCodelist.get(0);
			String roleCode = (String)checkmap.get("roleCode");
			String roleName = (String)checkmap.get("roleName");
			if(roleCode.equals(tbSystemRole.getRoleCode())){
				throw new Exception("角色编码已经存在！");
			}
			if(roleName.equals(tbSystemRole.getRoleName())){
				throw new Exception("角色名称已经存在！");
			}
		}
		if (null == tbSystemRole.getRoleId()) {
			systemRoleMapper.insert(tbSystemRole);
		} else {
			systemRoleMapper.updateByFormMap(map);
		}
		return tbSystemRole;
	}

	@Override
	public void delSystemRole(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbSystemRole tbSystemRole = new TbSystemRole();
			tbSystemRole.setRoleId(Integer.parseInt(id[i]));
			systemRoleMapper.deleteByPrimaryKey(tbSystemRole);
		}
	}

	@Override
	public List querySystemRoleList(Map map) throws Exception {
		List list = systemRoleMapper.queryAllSystemRole(map);
		Map dictCodeFieldMap=new HashMap();
		dictCodeFieldMap.put("role_power_explain", "rolePowerExplain");
		CachedDict.dictDataValue2DictDataNameForObj(1, "zh_CN", list, dictCodeFieldMap);
		return list;
	}

	@Override
	public int countSystemRoleList(Map map) throws Exception {
		int count = (int)systemRoleMapper.countAllSystemRole(map);
		return count;
	}

	@Override
	public List queryAllSystemRoleNp(Map map) throws Exception{
		List list = new ArrayList();
		list = systemRoleMapper.queryAllSystemRoleNP( map);
		return list;
	}
	
	@Override
	public TbSystemRole querySystemRoleInfo(Map map) throws Exception{
		TbSystemRole tbSystemRole =	(TbSystemRole)systemRoleMapper.querySystemRoleInfo(map);
		return tbSystemRole;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 角色
	 * @param list
	 * @param deleteSystemRoleIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveSystemRoleByGroup (List list,String deleteSystemRoleIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteSystemRoleIds)){
			String deleteSystemRoleId[] =  deleteSystemRoleIds.split(",");
			for(int i=0;i<deleteSystemRoleId.length;i++){
				if(!StringUtil.isEmpty(deleteSystemRoleId[i])){
					TbSystemRole tbSystemRole = new TbSystemRole();
					tbSystemRole.setRoleId(Integer.parseInt(deleteSystemRoleId[i]));
					systemRoleMapper.deleteByPrimaryKey(tbSystemRole);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbSystemRole tbSystemRole
				= (TbSystemRole) ToolUtil.converMapToObject(map,TbSystemRole.class);
				if (null == tbSystemRole.getRoleId()) {
					//TODO 设置Pid 
					systemRoleMapper.insertSelective(tbSystemRole);
				} else {
					systemRoleMapper.updateByFormMap(map);
				}
			}
		}
	}
}
