package com.insight.wisehealth.vte.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;
import com.insight.wisehealth.vte.dao.TbSystemDictDao;
import com.insight.wisehealth.vte.persistence.TbSystemDict;
import com.insight.wisehealth.vte.service.SystemDictService;


/**
 * 
 * 描述:字典服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public class SystemDictServiceImpl  implements SystemDictService{
	@Autowired
	TbSystemDictDao systemDictMapper;
	@Override
	public TbSystemDict saveSystemDict(Map map) throws Exception{
		TbSystemDict tbSystemDict = new TbSystemDict();
		tbSystemDict = (TbSystemDict) ToolUtil.converMapToObject(map,TbSystemDict.class);
		if (null == tbSystemDict.getDictId()) {
			systemDictMapper.insert(tbSystemDict);
		} else {
			systemDictMapper.updateByFormMap(map);
		}
		return tbSystemDict;
	}

	@Override
	public void delSystemDict(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbSystemDict tbSystemDict = new TbSystemDict();
			tbSystemDict.setDictId(Integer.parseInt(id[i]));
			systemDictMapper.deleteByPrimaryKey(tbSystemDict);
		}
	}

	@Override
	public List querySystemDictList(Map map) throws Exception {
		List list = systemDictMapper.queryAllSystemDict(map);
		return list;
	}

	@Override
	public int countSystemDictList(Map map) throws Exception {
		int count = (int)systemDictMapper.countAllSystemDict(map);
		return count;
	}

	@Override
	public List queryAllSystemDictNp(Map map) throws Exception{
		List list = new ArrayList();
		list = systemDictMapper.queryAllSystemDictNP( map);
		return list;
	}
	
	@Override
	public TbSystemDict querySystemDictInfo(Map map) throws Exception{
		TbSystemDict tbSystemDict =	(TbSystemDict)systemDictMapper.querySystemDictInfo(map);
		return tbSystemDict;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 字典
	 * @param list
	 * @param deleteSystemDictIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveSystemDictByGroup (List list,String deleteSystemDictIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteSystemDictIds)){
			String deleteSystemDictId[] =  deleteSystemDictIds.split(",");
			for(int i=0;i<deleteSystemDictId.length;i++){
				if(!StringUtil.isEmpty(deleteSystemDictId[i])){
					TbSystemDict tbSystemDict = new TbSystemDict();
					tbSystemDict.setDictId(Integer.parseInt(deleteSystemDictId[i]));
					systemDictMapper.deleteByPrimaryKey(tbSystemDict);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbSystemDict tbSystemDict
				= (TbSystemDict) ToolUtil.converMapToObject(map,TbSystemDict.class);
				if (null == tbSystemDict.getDictId()) {
					//TODO 设置Pid 
					systemDictMapper.insertSelective(tbSystemDict);
				} else {
					systemDictMapper.updateByFormMap(map);
				}
			}
		}
	}

	@Override
	public Map queryLocalDictData(Map<String, Object> map) throws Exception {

		
		Map result = new HashMap();
			List<Map> dictCodeList = systemDictMapper.queryLocalDictCode(map);
			List<Map> dictDataList = systemDictMapper.queryLocalDictData(map);
			for (int i = 0  ; i < dictCodeList.size() ; i++){
				List list = new ArrayList();
				Map code = dictCodeList.get(i);
				for(int j = 0 ; j < dictDataList.size(); j++){
					Map data = dictDataList.get(j);
					if(code.get("dictCode").equals(data.get("dictCode"))){
						list.add(data);
					}
				}
				result.put(columnToProperty((String)code.get("dictCode")), list);
				for(int j = 0 ; j < list.size(); j++){
					Map data = dictDataList.get(j);
					if(data.get("dictDataValue").equals(code.get("dictDefaultValue"))){
						result.put(columnToProperty((String)code.get("dictCode"))+"Default",list.get(j));
					}
				}
			}
		return result;
	
	}
	
	/**
	 * 下划线转驼峰格式命名的类名
	 * 
	 * @param column
	 * @return
	 */
	protected static String columnToProperty(String column) {
		StringBuilder className = new StringBuilder();
		if (StringUtils.isNotEmpty(column)) {
			String[] t = column.split("_");

			for (int i = 0; i < t.length; i++) {
				t[i] = t[i].toLowerCase();
			}
			className = new StringBuilder(t[0]);
			for (int i = 1; i < t.length; i++) {
				className.append(StringUtils.capitalize(t[i]));
			}

		}

		return className.toString();

	}
}
