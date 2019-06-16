package com.insight.wisehealth.vte.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;
import com.insight.wisehealth.vte.dao.TbVteAssessmentDictRoleDao;
import com.insight.wisehealth.vte.persistence.TbVteAssessmentDictRole;
import com.insight.wisehealth.vte.persistence.TbVteRoleModel;
import com.insight.wisehealth.vte.service.VteAssessmentDictRoleService;


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
public class VteAssessmentDictRoleServiceImpl  implements VteAssessmentDictRoleService{
	@Autowired
	TbVteAssessmentDictRoleDao vteAssessmentDictRoleMapper;
	@Override
	public TbVteAssessmentDictRole saveVteAssessmentDictRole(Map map) throws Exception{
		TbVteAssessmentDictRole tbVteAssessmentDictRole = new TbVteAssessmentDictRole();
		tbVteAssessmentDictRole = (TbVteAssessmentDictRole) ToolUtil.converMapToObject(map,TbVteAssessmentDictRole.class);
		if (null == tbVteAssessmentDictRole.getAssessmentDictRoleId()) {
			vteAssessmentDictRoleMapper.insert(tbVteAssessmentDictRole);
		} else {
			vteAssessmentDictRoleMapper.updateByFormMap(map);
		}
		return tbVteAssessmentDictRole;
	}

	@Override
	public void delVteAssessmentDictRole(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbVteAssessmentDictRole tbVteAssessmentDictRole = new TbVteAssessmentDictRole();
			tbVteAssessmentDictRole.setAssessmentDictRoleId(Integer.parseInt(id[i]));
			vteAssessmentDictRoleMapper.deleteByPrimaryKey(tbVteAssessmentDictRole);
		}
	}

	@Override
	public List queryVteAssessmentDictRoleList(Map map) throws Exception {
		List list = vteAssessmentDictRoleMapper.queryAllVteAssessmentDictRole(map);
		return list;
	}

	@Override
	public int countVteAssessmentDictRoleList(Map map) throws Exception {
		int count = (int)vteAssessmentDictRoleMapper.countAllVteAssessmentDictRole(map);
		return count;
	}

	@Override
	public List queryAllVteAssessmentDictRoleNp(Map map) throws Exception{
		List list = new ArrayList();
		list = vteAssessmentDictRoleMapper.queryAllVteAssessmentDictRoleNP( map);
		return list;
	}
	
	@Override
	public TbVteAssessmentDictRole queryVteAssessmentDictRoleInfo(Map map) throws Exception{
		TbVteAssessmentDictRole tbVteAssessmentDictRole =	(TbVteAssessmentDictRole)vteAssessmentDictRoleMapper.queryVteAssessmentDictRoleInfo(map);
		return tbVteAssessmentDictRole;
	}
	
	public  List<Integer> queryAssessmentDicByRoleId(Map map) throws Exception{
		List<Integer> list =  vteAssessmentDictRoleMapper.queryAssessmentDicByRoleId( map);
		return list;
	}
	
	@Override
	public void saveVteAssessmentDictRoleMost(Map map) throws Exception{
		Integer roleId = (Integer)map.get("roleId");
		JSONArray assessmentDictIds = (JSONArray)map.get("assessmentDictIds");
		if(assessmentDictIds==null||assessmentDictIds.size()==0){
			//根据 roleId 进行删除 
			TbVteAssessmentDictRole tbVteAssessmentDictRole = new TbVteAssessmentDictRole();
			tbVteAssessmentDictRole.setRoleId(roleId);
			vteAssessmentDictRoleMapper.deleteByPrimaryKey(tbVteAssessmentDictRole);
		}else{
			//查询当前roleId 已经配置的modelId
			List<Integer>  list = vteAssessmentDictRoleMapper.queryAssessmentDicByRoleId( map);
			//将当前modelId进行分类
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Integer modelId = list.get(i);
					if(assessmentDictIds.contains(modelId)){
						list.remove(i);
						i--;
						assessmentDictIds.remove(modelId);
					}
				}
			}
			//查找出需要添加的 modelIds
			if(assessmentDictIds!=null&&assessmentDictIds.size()>0){
				for(int i=0;i<assessmentDictIds.size();i++){
					Integer assessmentDictId = (Integer) assessmentDictIds.get(i);
					if(assessmentDictId!=null){
						TbVteAssessmentDictRole tbVteAssessmentDictRole = new TbVteAssessmentDictRole();
						tbVteAssessmentDictRole.setAssessmentDictId(assessmentDictId);
						tbVteAssessmentDictRole.setRoleId(roleId);
						vteAssessmentDictRoleMapper.insert(tbVteAssessmentDictRole);
					}
				}
			}
			//查找出需要删除的 list
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Integer modelId = list.get(i);
					TbVteAssessmentDictRole tbVteAssessmentDictRole = new TbVteAssessmentDictRole();
					tbVteAssessmentDictRole.setAssessmentDictId(modelId);
					tbVteAssessmentDictRole.setRoleId(roleId);
					vteAssessmentDictRoleMapper.deleteByPrimaryKey(tbVteAssessmentDictRole);
				}
			}
		}
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 角色-评分数据字典关联表
	 * @param list
	 * @param deleteVteAssessmentDictRoleIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveVteAssessmentDictRoleByGroup (List list,String deleteVteAssessmentDictRoleIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteVteAssessmentDictRoleIds)){
			String deleteVteAssessmentDictRoleId[] =  deleteVteAssessmentDictRoleIds.split(",");
			for(int i=0;i<deleteVteAssessmentDictRoleId.length;i++){
				if(!StringUtil.isEmpty(deleteVteAssessmentDictRoleId[i])){
					TbVteAssessmentDictRole tbVteAssessmentDictRole = new TbVteAssessmentDictRole();
					tbVteAssessmentDictRole.setAssessmentDictRoleId(Integer.parseInt(deleteVteAssessmentDictRoleId[i]));
					vteAssessmentDictRoleMapper.deleteByPrimaryKey(tbVteAssessmentDictRole);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbVteAssessmentDictRole tbVteAssessmentDictRole
				= (TbVteAssessmentDictRole) ToolUtil.converMapToObject(map,TbVteAssessmentDictRole.class);
				if (null == tbVteAssessmentDictRole.getAssessmentDictRoleId()) {
					//TODO 设置Pid 
					vteAssessmentDictRoleMapper.insertSelective(tbVteAssessmentDictRole);
				} else {
					vteAssessmentDictRoleMapper.updateByFormMap(map);
				}
			}
		}
	}
}
