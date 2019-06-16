package com.insight.wisehealth.vte.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;

import com.insight.wisehealth.vte.dao.TbVteDvtAdviceDao;
import com.insight.wisehealth.vte.persistence.TbVteDvtAdvice;
import com.insight.wisehealth.vte.service.VteDvtAdviceService;


/**
 * 
 * 描述:DVT诊断流程建议服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public class VteDvtAdviceServiceImpl  implements VteDvtAdviceService{
	@Autowired
	TbVteDvtAdviceDao vteDvtAdviceMapper;
	@Override
	public TbVteDvtAdvice saveVteDvtAdvice(Map map) throws Exception{
		TbVteDvtAdvice tbVteDvtAdvice = new TbVteDvtAdvice();
		tbVteDvtAdvice = (TbVteDvtAdvice) ToolUtil.converMapToObject(map,TbVteDvtAdvice.class);
		if (null == tbVteDvtAdvice.getDvtAdviceId()) {
			vteDvtAdviceMapper.insert(tbVteDvtAdvice);
		} else {
			vteDvtAdviceMapper.updateByFormMap(map);
		}
		return tbVteDvtAdvice;
	}

	@Override
	public void delVteDvtAdvice(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbVteDvtAdvice tbVteDvtAdvice = new TbVteDvtAdvice();
			tbVteDvtAdvice.setDvtAdviceId(Integer.parseInt(id[i]));
			vteDvtAdviceMapper.deleteByPrimaryKey(tbVteDvtAdvice);
		}
	}

	@Override
	public List queryVteDvtAdviceList(Map map) throws Exception {
		List list = vteDvtAdviceMapper.queryAllVteDvtAdvice(map);
		return list;
	}

	@Override
	public int countVteDvtAdviceList(Map map) throws Exception {
		int count = (int)vteDvtAdviceMapper.countAllVteDvtAdvice(map);
		return count;
	}

	@Override
	public List queryAllVteDvtAdviceNp(Map map) throws Exception{
		List list = new ArrayList();
		list = vteDvtAdviceMapper.queryAllVteDvtAdviceNP( map);
		return list;
	}
	
	@Override
	public TbVteDvtAdvice queryVteDvtAdviceInfo(Map map) throws Exception{
		TbVteDvtAdvice tbVteDvtAdvice =	(TbVteDvtAdvice)vteDvtAdviceMapper.queryVteDvtAdviceInfo(map);
		return tbVteDvtAdvice;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * DVT诊断流程建议
	 * @param list
	 * @param deleteVteDvtAdviceIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveVteDvtAdviceByGroup (List list,String deleteVteDvtAdviceIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteVteDvtAdviceIds)){
			String deleteVteDvtAdviceId[] =  deleteVteDvtAdviceIds.split(",");
			for(int i=0;i<deleteVteDvtAdviceId.length;i++){
				if(!StringUtil.isEmpty(deleteVteDvtAdviceId[i])){
					TbVteDvtAdvice tbVteDvtAdvice = new TbVteDvtAdvice();
					tbVteDvtAdvice.setDvtAdviceId(Integer.parseInt(deleteVteDvtAdviceId[i]));
					vteDvtAdviceMapper.deleteByPrimaryKey(tbVteDvtAdvice);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbVteDvtAdvice tbVteDvtAdvice
				= (TbVteDvtAdvice) ToolUtil.converMapToObject(map,TbVteDvtAdvice.class);
				if (null == tbVteDvtAdvice.getDvtAdviceId()) {
					//TODO 设置Pid 
					vteDvtAdviceMapper.insertSelective(tbVteDvtAdvice);
				} else {
					vteDvtAdviceMapper.updateByFormMap(map);
				}
			}
		}
	}
}
