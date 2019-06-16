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
import com.insight.wisehealth.vte.dao.TbVteRoleModelDao;
import com.insight.wisehealth.vte.persistence.TbVteModel;
import com.insight.wisehealth.vte.persistence.TbVteRoleModel;
import com.insight.wisehealth.vte.service.VteRoleModelService;


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
public class VteRoleModelServiceImpl  implements VteRoleModelService{
	@Autowired
	TbVteRoleModelDao vteRoleModelMapper;
	@Override
	public TbVteRoleModel saveVteRoleModel(Map map) throws Exception{
		TbVteRoleModel tbVteRoleModel = new TbVteRoleModel();
		tbVteRoleModel = (TbVteRoleModel) ToolUtil.converMapToObject(map,TbVteRoleModel.class);
		if (null == tbVteRoleModel.getRoleModelId()) {
			vteRoleModelMapper.insert(tbVteRoleModel);
		} else {
			vteRoleModelMapper.updateByFormMap(map);
		}
		return tbVteRoleModel;
	}

	@Override
	public void delVteRoleModel(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbVteRoleModel tbVteRoleModel = new TbVteRoleModel();
			tbVteRoleModel.setRoleModelId(Integer.parseInt(id[i]));
			vteRoleModelMapper.deleteByPrimaryKey(tbVteRoleModel);
		}
	}

	@Override
	public List queryVteRoleModelList(Map map) throws Exception {
		List list = vteRoleModelMapper.queryAllVteRoleModel(map);
		return list;
	}

	@Override
	public int countVteRoleModelList(Map map) throws Exception {
		int count = (int)vteRoleModelMapper.countAllVteRoleModel(map);
		return count;
	}

	@Override
	public List queryAllVteRoleModelNp(Map map) throws Exception{
		List list = new ArrayList();
		list = vteRoleModelMapper.queryAllVteRoleModelNP( map);
		return list;
	}
	
	@Override
	public TbVteRoleModel queryVteRoleModelInfo(Map map) throws Exception{
		TbVteRoleModel tbVteRoleModel =	(TbVteRoleModel)vteRoleModelMapper.queryVteRoleModelInfo(map);
		return tbVteRoleModel;
	}
	
	@Override
	public List<Integer> queryVteModelByRoleId(Map map) throws Exception{
		List<Integer>  list = vteRoleModelMapper.queryVteModelByRoleId( map);
		return list;
	}
	
	@Override
	public void saveVteModelRoleMost(Map map) throws Exception{
		Integer roleId = (Integer)map.get("roleId");
		JSONArray modelIds = (JSONArray)map.get("modelIds");
		if(modelIds==null||modelIds.size()==0){
			//根据 roleId 进行删除 
			TbVteRoleModel tbVteRoleModel = new TbVteRoleModel();
			tbVteRoleModel.setRoleId(roleId);
			vteRoleModelMapper.deleteByPrimaryKey(tbVteRoleModel);
		}else{
			//查询当前roleId 已经配置的modelId
			List<Integer>  list = vteRoleModelMapper.queryVteModelByRoleId( map);
			//将当前modelId进行分类
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Integer modelId = list.get(i);
					if(modelIds.contains(modelId)){
						list.remove(i);
						i--;
						modelIds.remove(modelId);
					}
				}
			}
			//查找出需要添加的 modelIds
			if(modelIds!=null&&modelIds.size()>0){
				for(int i=0;i<modelIds.size();i++){
					Integer modelId = (Integer) modelIds.get(i);
					TbVteRoleModel tbVteRoleModel = new TbVteRoleModel();
					tbVteRoleModel.setModelId(modelId);
					tbVteRoleModel.setRoleId(roleId);
					vteRoleModelMapper.insert(tbVteRoleModel);
				}
			}
			//查找出需要删除的 list
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Integer modelId = list.get(i);
					TbVteRoleModel tbVteRoleModel = new TbVteRoleModel();
					tbVteRoleModel.setModelId(modelId);
					tbVteRoleModel.setRoleId(roleId);
					vteRoleModelMapper.deleteByPrimaryKey(tbVteRoleModel);
				}
			}
		}
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 角色模块权限
	 * @param list
	 * @param deleteVteRoleModelIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveVteRoleModelByGroup (List list,String deleteVteRoleModelIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteVteRoleModelIds)){
			String deleteVteRoleModelId[] =  deleteVteRoleModelIds.split(",");
			for(int i=0;i<deleteVteRoleModelId.length;i++){
				if(!StringUtil.isEmpty(deleteVteRoleModelId[i])){
					TbVteRoleModel tbVteRoleModel = new TbVteRoleModel();
					tbVteRoleModel.setRoleModelId(Integer.parseInt(deleteVteRoleModelId[i]));
					vteRoleModelMapper.deleteByPrimaryKey(tbVteRoleModel);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbVteRoleModel tbVteRoleModel
				= (TbVteRoleModel) ToolUtil.converMapToObject(map,TbVteRoleModel.class);
				if (null == tbVteRoleModel.getRoleModelId()) {
					//TODO 设置Pid 
					vteRoleModelMapper.insertSelective(tbVteRoleModel);
				} else {
					vteRoleModelMapper.updateByFormMap(map);
				}
			}
		}
	}
}
