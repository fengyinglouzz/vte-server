package com.insight.wisehealth.vte.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;

import com.insight.wisehealth.vte.dao.TbVteDepartmentDao;
import com.insight.wisehealth.vte.persistence.TbVteDepartment;
import com.insight.wisehealth.vte.service.VteDepartmentService;


/**
 * 
 * 描述:患者科室（用于方便日后统计使用）服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public class VteDepartmentServiceImpl  implements VteDepartmentService{
	@Autowired
	TbVteDepartmentDao vteDepartmentMapper;
	@Override
	public TbVteDepartment saveVteDepartment(Map map) throws Exception{
		TbVteDepartment tbVteDepartment = new TbVteDepartment();
		tbVteDepartment = (TbVteDepartment) ToolUtil.converMapToObject(map,TbVteDepartment.class);
		if (null == tbVteDepartment.getDepartmentId()) {
			vteDepartmentMapper.insert(tbVteDepartment);
		} else {
			vteDepartmentMapper.updateByFormMap(map);
		}
		return tbVteDepartment;
	}

	@Override
	public void delVteDepartment(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbVteDepartment tbVteDepartment = new TbVteDepartment();
			tbVteDepartment.setDepartmentId(Integer.parseInt(id[i]));
			vteDepartmentMapper.deleteByPrimaryKey(tbVteDepartment);
		}
	}

	@Override
	public List queryVteDepartmentList(Map map) throws Exception {
		List list = vteDepartmentMapper.queryAllVteDepartment(map);
		return list;
	}

	@Override
	public int countVteDepartmentList(Map map) throws Exception {
		int count = (int)vteDepartmentMapper.countAllVteDepartment(map);
		return count;
	}

	@Override
	public List queryAllVteDepartmentNp(Map map) throws Exception{
		List list = new ArrayList();
		list = vteDepartmentMapper.queryAllVteDepartmentNP( map);
		return list;
	}
	
	@Override
	public TbVteDepartment queryVteDepartmentInfo(Map map) throws Exception{
		TbVteDepartment tbVteDepartment =	(TbVteDepartment)vteDepartmentMapper.queryVteDepartmentInfo(map);
		return tbVteDepartment;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 患者科室（用于方便日后统计使用）
	 * @param list
	 * @param deleteVteDepartmentIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveVteDepartmentByGroup (List list,String deleteVteDepartmentIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteVteDepartmentIds)){
			String deleteVteDepartmentId[] =  deleteVteDepartmentIds.split(",");
			for(int i=0;i<deleteVteDepartmentId.length;i++){
				if(!StringUtil.isEmpty(deleteVteDepartmentId[i])){
					TbVteDepartment tbVteDepartment = new TbVteDepartment();
					tbVteDepartment.setDepartmentId(Integer.parseInt(deleteVteDepartmentId[i]));
					vteDepartmentMapper.deleteByPrimaryKey(tbVteDepartment);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbVteDepartment tbVteDepartment
				= (TbVteDepartment) ToolUtil.converMapToObject(map,TbVteDepartment.class);
				if (null == tbVteDepartment.getDepartmentId()) {
					//TODO 设置Pid 
					vteDepartmentMapper.insertSelective(tbVteDepartment);
				} else {
					vteDepartmentMapper.updateByFormMap(map);
				}
			}
		}
	}
}
