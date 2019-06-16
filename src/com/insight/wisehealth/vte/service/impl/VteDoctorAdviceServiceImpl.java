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
import com.insight.wisehealth.vte.dao.TbVteDoctorAdviceDao;
import com.insight.wisehealth.vte.dao.TbVtePatientHospitInfoDao;
import com.insight.wisehealth.vte.persistence.TbVteDoctorAdvice;
import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;
import com.insight.wisehealth.vte.service.VteDoctorAdviceService;


/**
 * 
 * 描述:医嘱处理服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public class VteDoctorAdviceServiceImpl  implements VteDoctorAdviceService{
	@Autowired
	TbVteDoctorAdviceDao vteDoctorAdviceMapper;
	@Autowired
	TbVtePatientHospitInfoDao vtePatientHospitInfoMapper;
	@Override
	public TbVteDoctorAdvice saveVteDoctorAdvice(Map map) throws Exception{
		TbVteDoctorAdvice tbVteDoctorAdvice = new TbVteDoctorAdvice();
		//查询患者信息
		TbVtePatientHospitInfo vtePatientHospitInfoInfo = vtePatientHospitInfoMapper.queryVtePatientHospitInfoInfo(map);
		map.put("patientCode", vtePatientHospitInfoInfo.getPatientCode());
		
		tbVteDoctorAdvice = (TbVteDoctorAdvice) ToolUtil.converMapToObject(map,TbVteDoctorAdvice.class);
		if (null == tbVteDoctorAdvice.getDoctorAdviceId()) {
			
			tbVteDoctorAdvice.setDoctorAdviceFrom("0");
			vteDoctorAdviceMapper.insert(tbVteDoctorAdvice);
			Map insertMap = new HashMap();
			insertMap.put("patientHospitId", tbVteDoctorAdvice.getPatientHospitId());
			vteDoctorAdviceMapper.vteDoctorAdviceAfterInsert(insertMap);
			
		} else {
			vteDoctorAdviceMapper.updateByFormMap(map);
		}
		return tbVteDoctorAdvice;
	}

	@Override
	public void delVteDoctorAdvice(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbVteDoctorAdvice tbVteDoctorAdvice = new TbVteDoctorAdvice();
			tbVteDoctorAdvice.setDoctorAdviceId(Integer.parseInt(id[i]));
			vteDoctorAdviceMapper.deleteByPrimaryKey(tbVteDoctorAdvice);
		}
	}

	@Override
	public List queryVteDoctorAdviceList(Map map) throws Exception {
		List list = vteDoctorAdviceMapper.queryAllVteDoctorAdvice(map);
		return list;
	}

	@Override
	public int countVteDoctorAdviceList(Map map) throws Exception {
		int count = (int)vteDoctorAdviceMapper.countAllVteDoctorAdvice(map);
		return count;
	}

	@Override
	public List queryAllVteDoctorAdviceNp(Map map) throws Exception{
		List list = new ArrayList();
		list = vteDoctorAdviceMapper.queryAllVteDoctorAdviceNP( map);
		return list;
	}
	
	@Override
	public TbVteDoctorAdvice queryVteDoctorAdviceInfo(Map map) throws Exception{
		TbVteDoctorAdvice tbVteDoctorAdvice =	(TbVteDoctorAdvice)vteDoctorAdviceMapper.queryVteDoctorAdviceInfo(map);
		return tbVteDoctorAdvice;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 医嘱处理
	 * @param list
	 * @param deleteVteDoctorAdviceIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveVteDoctorAdviceByGroup (List list,String deleteVteDoctorAdviceIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteVteDoctorAdviceIds)){
			String deleteVteDoctorAdviceId[] =  deleteVteDoctorAdviceIds.split(",");
			for(int i=0;i<deleteVteDoctorAdviceId.length;i++){
				if(!StringUtil.isEmpty(deleteVteDoctorAdviceId[i])){
					TbVteDoctorAdvice tbVteDoctorAdvice = new TbVteDoctorAdvice();
					tbVteDoctorAdvice.setDoctorAdviceId(Integer.parseInt(deleteVteDoctorAdviceId[i]));
					vteDoctorAdviceMapper.deleteByPrimaryKey(tbVteDoctorAdvice);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbVteDoctorAdvice tbVteDoctorAdvice
				= (TbVteDoctorAdvice) ToolUtil.converMapToObject(map,TbVteDoctorAdvice.class);
				if (null == tbVteDoctorAdvice.getDoctorAdviceId()) {
					//TODO 设置Pid 
					vteDoctorAdviceMapper.insertSelective(tbVteDoctorAdvice);
				} else {
					vteDoctorAdviceMapper.updateByFormMap(map);
				}
			}
		}
	}
}
