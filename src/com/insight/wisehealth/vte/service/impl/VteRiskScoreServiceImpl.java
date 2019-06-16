package com.insight.wisehealth.vte.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;
import com.insight.wisehealth.vte.common.ConstantsDict;
import com.insight.wisehealth.vte.dao.TbVteRiskScoreDao;
import com.insight.wisehealth.vte.persistence.TbVteRiskScore;
import com.insight.wisehealth.vte.service.VteRiskScoreService;


/**
 * 
 * 描述:VTE风险分度服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public class VteRiskScoreServiceImpl  implements VteRiskScoreService{
	@Autowired
	TbVteRiskScoreDao vteRiskScoreMapper;
	@Override
	public TbVteRiskScore saveVteRiskScore(Map map) throws Exception{
		TbVteRiskScore tbVteRiskScore = new TbVteRiskScore();
		tbVteRiskScore = (TbVteRiskScore) ToolUtil.converMapToObject(map,TbVteRiskScore.class);
		if (null == tbVteRiskScore.getRiskScoreId()) {
			vteRiskScoreMapper.insert(tbVteRiskScore);
		} else {
			vteRiskScoreMapper.updateByFormMap(map);
		}
		return tbVteRiskScore;
	}

	@Override
	public void delVteRiskScore(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbVteRiskScore tbVteRiskScore = new TbVteRiskScore();
			tbVteRiskScore.setRiskScoreId(Integer.parseInt(id[i]));
			vteRiskScoreMapper.deleteByPrimaryKey(tbVteRiskScore);
		}
	}

	@Override
	public List queryVteRiskScoreList(Map map) throws Exception {
		List list = vteRiskScoreMapper.queryAllVteRiskScore(map);
		return list;
	}

	@Override
	public int countVteRiskScoreList(Map map) throws Exception {
		int count = (int)vteRiskScoreMapper.countAllVteRiskScore(map);
		return count;
	}

	@Override
	public List queryAllVteRiskScoreNp(Map map) throws Exception{
		List list = new ArrayList();
		list = vteRiskScoreMapper.queryAllVteRiskScoreNP( map);
		return list;
	}
	@Override
	public List queryVteRiskScoreInfoList(Map map) throws Exception{
		List list = new ArrayList();
		
		String modelCode = (String)map.get("modelCode");
		String riskScoreCode = ConstantsDict.ASSESSMENT_ITEM_MAP.get(modelCode);
		map.put("riskScoreCode", "assessment_item_" + riskScoreCode);
		
		list = vteRiskScoreMapper.queryAllVteRiskScoreNP( map);
		return list;
	}
	@Override
	public TbVteRiskScore queryVteRiskScoreInfo(Map map) throws Exception{
		TbVteRiskScore tbVteRiskScore =	(TbVteRiskScore)vteRiskScoreMapper.queryVteRiskScoreInfo(map);
		return tbVteRiskScore;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * VTE风险分度
	 * @param list
	 * @param deleteVteRiskScoreIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveVteRiskScoreByGroup (List list,String deleteVteRiskScoreIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteVteRiskScoreIds)){
			String deleteVteRiskScoreId[] =  deleteVteRiskScoreIds.split(",");
			for(int i=0;i<deleteVteRiskScoreId.length;i++){
				if(!StringUtil.isEmpty(deleteVteRiskScoreId[i])){
					TbVteRiskScore tbVteRiskScore = new TbVteRiskScore();
					tbVteRiskScore.setRiskScoreId(Integer.parseInt(deleteVteRiskScoreId[i]));
					vteRiskScoreMapper.deleteByPrimaryKey(tbVteRiskScore);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbVteRiskScore tbVteRiskScore
				= (TbVteRiskScore) ToolUtil.converMapToObject(map,TbVteRiskScore.class);
				if (null == tbVteRiskScore.getRiskScoreId()) {
					//TODO 设置Pid 
					vteRiskScoreMapper.insertSelective(tbVteRiskScore);
				} else {
					vteRiskScoreMapper.updateByFormMap(map);
				}
			}
		}
	}
}
