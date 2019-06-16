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
import com.insight.wisehealth.vte.dao.TbSystemOrgDao;
import com.insight.wisehealth.vte.dao.TbVteDepartmentDao;
import com.insight.wisehealth.vte.persistence.TbSystemOrg;
import com.insight.wisehealth.vte.persistence.TbVteDepartment;
import com.insight.wisehealth.vte.pojo.VteDepartmentPojo;
import com.insight.wisehealth.vte.pojo.VteHospitalPojo;
import com.insight.wisehealth.vte.service.SystemOrgService;
import com.insight.wisehealth.vte.service.VteDepartmentService;


/**
 * 
 * 描述:机构服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public class SystemOrgServiceImpl  implements SystemOrgService{
	@Autowired
	TbSystemOrgDao systemOrgMapper;
	@Autowired
	TbVteDepartmentDao VteDepartmentMapper;
	@Override
	public TbSystemOrg saveSystemOrg(Map map) throws Exception{
		TbSystemOrg tbSystemOrg = new TbSystemOrg();
		tbSystemOrg = (TbSystemOrg) ToolUtil.converMapToObject(map,TbSystemOrg.class);
		if (null == tbSystemOrg.getOrgId()) {
			String modelFatherCode = (String)map.get("orgCode");
			String modelCode = gainModelCode(modelFatherCode);
			if(StringUtils.isEmpty(tbSystemOrg.getOrgType())){
				tbSystemOrg.setOrgType("department");
			}
			if(tbSystemOrg.getOrgType().contains("department")){//代表着添加的是科室 需要同步到科室管理
				TbVteDepartment tbVteDepartment=new TbVteDepartment();
				tbVteDepartment.setDepartmentCode(modelCode);
				tbVteDepartment.setDepartmentName(tbSystemOrg.getOrgName());
				VteDepartmentMapper.insert(tbVteDepartment);
			}
			tbSystemOrg.setOrgCode(modelCode);
			systemOrgMapper.insert(tbSystemOrg);
		} else {
			systemOrgMapper.updateByFormMap(map);
		}
		return tbSystemOrg;
	}

	@Override
	public void delSystemOrg(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbSystemOrg tbSystemOrg = new TbSystemOrg();
			tbSystemOrg.setOrgId(Integer.parseInt(id[i]));
			systemOrgMapper.deleteByPrimaryKey(tbSystemOrg);
		}
	}

	@Override
	public List querySystemOrgList(Map map) throws Exception {
		List list = systemOrgMapper.queryAllSystemOrg(map);
		return list;
	}

	@Override
	public int countSystemOrgList(Map map) throws Exception {
		int count = (int)systemOrgMapper.countAllSystemOrg(map);
		return count;
	}

	@Override
	public List queryAllSystemOrgNp(Map map) throws Exception{
		List list = new ArrayList();
		list = systemOrgMapper.queryAllSystemOrgNP( map);
		return list;
	}
	
	@Override
	public TbSystemOrg querySystemOrgInfo(Map map) throws Exception{
		TbSystemOrg tbSystemOrg =	(TbSystemOrg)systemOrgMapper.querySystemOrgInfo(map);
		return tbSystemOrg;
	}
	
	@Override
	public List<VteHospitalPojo> queryVteHospitalPojoList(Map map) throws Exception{
		Map hospitalSearchMap = new HashMap();
		hospitalSearchMap.put("orgType", "hospital");
		//查询全部医院
		List<TbSystemOrg> hospitalList = systemOrgMapper.queryAllSystemOrgNP( hospitalSearchMap);
		Map departmentSearchMap = new HashMap();
		departmentSearchMap.put("orgType", "department");
		//查询全部科室
		List<TbSystemOrg> departmentList = systemOrgMapper.queryAllSystemOrgNP( departmentSearchMap);
		List<VteHospitalPojo> vteHospitalPojoList = new ArrayList();
		if(hospitalList!=null)
		for(int i=0;i<hospitalList.size();i++){
			VteHospitalPojo vteHospitalPojo = new VteHospitalPojo();
			TbSystemOrg hospitalOrg = hospitalList.get(i);
			vteHospitalPojo.setHospitalId(hospitalOrg.getOrgId());
			vteHospitalPojo.setHospitalCode(hospitalOrg.getOrgCode());
			vteHospitalPojo.setHospitalName(hospitalOrg.getOrgName());
			if(departmentList!=null){
				List<VteDepartmentPojo> vteDepartmentList = vteHospitalPojo.getVteDepartmentList();
				if(vteDepartmentList==null){
					vteDepartmentList = new ArrayList();
				}
				for(int j=0;j<departmentList.size();j++){
					TbSystemOrg departmentOrg = departmentList.get(j);
					if(departmentOrg.getOrgCode().startsWith(hospitalOrg.getOrgCode())){
						VteDepartmentPojo vteDepartment =new VteDepartmentPojo();
						vteDepartment.setDepartmentId(departmentOrg.getOrgId());
						vteDepartment.setDepartmentCode(departmentOrg.getOrgCode());
						vteDepartment.setDepartmentName(departmentOrg.getOrgName());
						vteDepartmentList.add(vteDepartment);
					}
				}
				vteHospitalPojo.setVteDepartmentList(vteDepartmentList);
			}
			vteHospitalPojoList.add(vteHospitalPojo);
		}
		return vteHospitalPojoList;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 机构
	 * @param list
	 * @param deleteSystemOrgIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveSystemOrgByGroup (List list,String deleteSystemOrgIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteSystemOrgIds)){
			String deleteSystemOrgId[] =  deleteSystemOrgIds.split(",");
			for(int i=0;i<deleteSystemOrgId.length;i++){
				if(!StringUtil.isEmpty(deleteSystemOrgId[i])){
					TbSystemOrg tbSystemOrg = new TbSystemOrg();
					tbSystemOrg.setOrgId(Integer.parseInt(deleteSystemOrgId[i]));
					systemOrgMapper.deleteByPrimaryKey(tbSystemOrg);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbSystemOrg tbSystemOrg
				= (TbSystemOrg) ToolUtil.converMapToObject(map,TbSystemOrg.class);
				if (null == tbSystemOrg.getOrgId()) {
					//TODO 设置Pid 
					systemOrgMapper.insertSelective(tbSystemOrg);
				} else {
					systemOrgMapper.updateByFormMap(map);
				}
			}
		}
	}
	
	private String gainModelCode(String modelFatherCode){
		String modelCode = new String();
		if(StringUtil.isEmpty(modelFatherCode)){
			modelFatherCode = "";
		}else{
			modelFatherCode = modelFatherCode+"-";
		}
		String regexpStrng =  "^"+modelFatherCode+"[0-9]{1,5}$";
		Map map = new HashMap();
		map.put("regexpStrng", regexpStrng);
		String maxmodelCode = systemOrgMapper.queryMaxCodeByPaCode(map);
		int lastnum = 0;
		if(!StringUtil.isEmpty(maxmodelCode)){
			String lastcode = maxmodelCode.substring(maxmodelCode.lastIndexOf("-")+1);
			lastnum = Integer.valueOf(lastcode);
		}
		lastnum ++;
		if(lastnum<10){
			modelCode = modelFatherCode+"00"+lastnum;
		}else if(lastnum<100){
			modelCode = modelFatherCode+"0"+lastnum;
		}else{
			modelCode = modelFatherCode+""+lastnum;
		}
		return modelCode;
	}
	@Override
	public List queryAllSystemDepartment(Map map) throws Exception{
			List queryAllSystemDepartmentNP = systemOrgMapper.queryAllSystemDepartmentNP(map);
		return queryAllSystemDepartmentNP;
	}
}
