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
import com.insight.wisehealth.vte.dao.TbVteAssessmentDictDao;
import com.insight.wisehealth.vte.persistence.TbVteAssessmentDict;
import com.insight.wisehealth.vte.persistence.TbVteModel;
import com.insight.wisehealth.vte.persistencepojo.TbVteAssessmentDictPojo;
import com.insight.wisehealth.vte.pojo.VteAssessmentDictPojo;
import com.insight.wisehealth.vte.pojo.VteAssessmentDictTreePojo;
import com.insight.wisehealth.vte.service.VteAssessmentDictService;


/**
 * 
 * 描述:评分数据项字典服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public class VteAssessmentDictServiceImpl  implements VteAssessmentDictService{
	@Autowired
	TbVteAssessmentDictDao vteAssessmentDictMapper;
	@Override
	public TbVteAssessmentDict saveVteAssessmentDict(Map map) throws Exception{
		TbVteAssessmentDict tbVteAssessmentDict = new TbVteAssessmentDict();
		tbVteAssessmentDict = (TbVteAssessmentDict) ToolUtil.converMapToObject(map,TbVteAssessmentDict.class);
		if (null == tbVteAssessmentDict.getAssessmentDictId()) {
			vteAssessmentDictMapper.insert(tbVteAssessmentDict);
		} else {
			vteAssessmentDictMapper.updateByFormMap(map);
		}
		return tbVteAssessmentDict;
	}

	@Override
	public void delVteAssessmentDict(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbVteAssessmentDict tbVteAssessmentDict = new TbVteAssessmentDict();
			tbVteAssessmentDict.setAssessmentDictId(Integer.parseInt(id[i]));
			vteAssessmentDictMapper.deleteByPrimaryKey(tbVteAssessmentDict);
		}
	}

	@Override
	public List queryVteAssessmentDictList(Map map) throws Exception {
		List list = vteAssessmentDictMapper.queryAllVteAssessmentDict(map);
		Map dictCodeFieldMap=new HashMap();
		dictCodeFieldMap.put("assessment_type_explain", "assessmentTypeExplain");
		dictCodeFieldMap.put("assessment_item_explain", "assessmentItemExplain");
		CachedDict.dictDataValue2DictDataNameForObj(1, "zh_CN", list, dictCodeFieldMap);
		return list;
	}

	@Override
	public int countVteAssessmentDictList(Map map) throws Exception {
		int count = (int)vteAssessmentDictMapper.countAllVteAssessmentDict(map);
		return count;
	}

	@Override
	public List queryAllVteAssessmentDictNp(Map map) throws Exception{
		List list = new ArrayList();
		list = vteAssessmentDictMapper.queryAllVteAssessmentDictNP( map);
		return list;
	}
	
	@Override
	public TbVteAssessmentDict queryVteAssessmentDictInfo(Map map) throws Exception{
		TbVteAssessmentDict tbVteAssessmentDict =	(TbVteAssessmentDict)vteAssessmentDictMapper.queryVteAssessmentDictInfo(map);
		return tbVteAssessmentDict;
	}
	@Override
	public List<VteAssessmentDictTreePojo> queryVteAssessmentDictTreePojo(Map map)throws Exception{
		List<VteAssessmentDictTreePojo>  vteAssessmentDictTreePojoList = new ArrayList();
		//查询字典编码为assessment_type的字典数据 
		Map dictMap = new HashMap();
		dictMap.put("orgId", 1);
		dictMap.put("dictInternational","zh_CN");
		dictMap.put("dictCode", "assessment_item");
		List<Map> dictdata = CachedDict.getDictDataList(dictMap);
		//查询全部的评估数据
		List<VteAssessmentDictPojo> list = vteAssessmentDictMapper.queryAllVteAssessmentDictPojo( map);
		//遍历字典数据
		//将编码一致的数据封装进去
		for(int i=0;i<dictdata.size();i++){
			VteAssessmentDictTreePojo vteAssessmentDictTreePojo = new VteAssessmentDictTreePojo();
			Map dictdataMap = dictdata.get(i);
			String dictDataName = (String)dictdataMap.get("dictDataName");
			String dictDataValue = (String)dictdataMap.get("dictDataValue");
			vteAssessmentDictTreePojo.setAssessmentItem(dictDataValue);
			vteAssessmentDictTreePojo.setAssessmentItemExplain(dictDataName);
			List<VteAssessmentDictPojo> vteAssessmentDictPojoList = new ArrayList();
			if(list!=null&&list.size()>0){
				for(int j=0;j<list.size();j++){
					VteAssessmentDictPojo tbVteAssessmentDict = list.get(j);
					if(tbVteAssessmentDict.getAssessmentItem().equals(dictDataValue)){
						vteAssessmentDictPojoList.add(tbVteAssessmentDict);
						list.remove(j);
						j--;
					}
				}
			}
			vteAssessmentDictTreePojo.setVteAssessmentDictPojoList(vteAssessmentDictPojoList);
			vteAssessmentDictTreePojoList.add(vteAssessmentDictTreePojo);
		}
		return vteAssessmentDictTreePojoList;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 评分数据项字典
	 * @param list
	 * @param deleteVteAssessmentDictIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveVteAssessmentDictByGroup (List list,String deleteVteAssessmentDictIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteVteAssessmentDictIds)){
			String deleteVteAssessmentDictId[] =  deleteVteAssessmentDictIds.split(",");
			for(int i=0;i<deleteVteAssessmentDictId.length;i++){
				if(!StringUtil.isEmpty(deleteVteAssessmentDictId[i])){
					TbVteAssessmentDict tbVteAssessmentDict = new TbVteAssessmentDict();
					tbVteAssessmentDict.setAssessmentDictId(Integer.parseInt(deleteVteAssessmentDictId[i]));
					vteAssessmentDictMapper.deleteByPrimaryKey(tbVteAssessmentDict);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbVteAssessmentDict tbVteAssessmentDict
				= (TbVteAssessmentDict) ToolUtil.converMapToObject(map,TbVteAssessmentDict.class);
				if (null == tbVteAssessmentDict.getAssessmentDictId()) {
					//TODO 设置Pid 
					vteAssessmentDictMapper.insertSelective(tbVteAssessmentDict);
				} else {
					vteAssessmentDictMapper.updateByFormMap(map);
				}
			}
		}
	}
}
