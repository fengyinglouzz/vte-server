package com.insight.wisehealth.vte.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;

import com.insight.wisehealth.vte.dao.TbSystemDictDataDao;
import com.insight.wisehealth.vte.persistence.TbSystemDictData;
import com.insight.wisehealth.vte.service.SystemDictDataService;


/**
 * 
 * 描述:字典数据服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public class SystemDictDataServiceImpl  implements SystemDictDataService{
	@Autowired
	TbSystemDictDataDao systemDictDataMapper;
	@Override
	public TbSystemDictData saveSystemDictData(Map map) throws Exception{
		TbSystemDictData tbSystemDictData = new TbSystemDictData();
		tbSystemDictData = (TbSystemDictData) ToolUtil.converMapToObject(map,TbSystemDictData.class);
		if (null == tbSystemDictData.getDictDataId()) {
			systemDictDataMapper.insert(tbSystemDictData);
		} else {
			systemDictDataMapper.updateByFormMap(map);
		}
		return tbSystemDictData;
	}

	@Override
	public void delSystemDictData(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbSystemDictData tbSystemDictData = new TbSystemDictData();
			tbSystemDictData.setDictDataId(Integer.parseInt(id[i]));
			systemDictDataMapper.deleteByPrimaryKey(tbSystemDictData);
		}
	}

	@Override
	public List querySystemDictDataList(Map map) throws Exception {
		List list = systemDictDataMapper.queryAllSystemDictData(map);
		return list;
	}

	@Override
	public int countSystemDictDataList(Map map) throws Exception {
		int count = (int)systemDictDataMapper.countAllSystemDictData(map);
		return count;
	}

	@Override
	public List queryAllSystemDictDataNp(Map map) throws Exception{
		List list = new ArrayList();
		list = systemDictDataMapper.queryAllSystemDictDataNP( map);
		return list;
	}
	
	@Override
	public TbSystemDictData querySystemDictDataInfo(Map map) throws Exception{
		TbSystemDictData tbSystemDictData =	(TbSystemDictData)systemDictDataMapper.querySystemDictDataInfo(map);
		return tbSystemDictData;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 字典数据
	 * @param list
	 * @param deleteSystemDictDataIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveSystemDictDataByGroup (List list,String deleteSystemDictDataIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteSystemDictDataIds)){
			String deleteSystemDictDataId[] =  deleteSystemDictDataIds.split(",");
			for(int i=0;i<deleteSystemDictDataId.length;i++){
				if(!StringUtil.isEmpty(deleteSystemDictDataId[i])){
					TbSystemDictData tbSystemDictData = new TbSystemDictData();
					tbSystemDictData.setDictDataId(Integer.parseInt(deleteSystemDictDataId[i]));
					systemDictDataMapper.deleteByPrimaryKey(tbSystemDictData);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbSystemDictData tbSystemDictData
				= (TbSystemDictData) ToolUtil.converMapToObject(map,TbSystemDictData.class);
				if (null == tbSystemDictData.getDictDataId()) {
					//TODO 设置Pid 
					systemDictDataMapper.insertSelective(tbSystemDictData);
				} else {
					systemDictDataMapper.updateByFormMap(map);
				}
			}
		}
	}
}
