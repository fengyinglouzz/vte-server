package com.insight.axiswevservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.axiswevservice.pojo.VteBatchPatientPojo;
import com.insight.axiswevservice.pojo.VtePatientHospitInfoList;
import com.insight.axiswevservice.service.VteBatchPatientHospitInfoService;
import com.insight.core.util.R;
import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;
import com.insight.wisehealth.vte.dao.TbVteDepartmentDao;
import com.insight.wisehealth.vte.dao.TbVtePatientDao;
import com.insight.wisehealth.vte.dao.TbVtePatientHospitInfoDao;
import com.insight.wisehealth.vte.persistence.TbVteDepartment;
import com.insight.wisehealth.vte.persistence.TbVtePatient;
import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;

@Service
public class VteBatchPatientHospitInfoServiceImpl implements VteBatchPatientHospitInfoService{
	@Autowired
	TbVtePatientDao vtePatientMapper;
	@Autowired
	TbVtePatientHospitInfoDao vtePatientHospitInfoMapper;
	@Autowired
	TbVteDepartmentDao vteDepartmentMapper;
	
	@Override
	public R batchSynAssessment(List<VteBatchPatientPojo> patientList) throws Exception{
		for (VteBatchPatientPojo patientPojo : patientList) {
			Map searchMap = new HashMap();
			searchMap.put("patientCode", patientPojo.getPatientCode());
			//查询是否有患者
			TbVtePatient patientInfo = vtePatientMapper.queryVtePatientInfo(searchMap);
			
			if(patientInfo == null){
				patientInfo = new TbVtePatient();
			}
			//存在该患者，进行更新
			Integer patientId = patientInfo.getPatientId();
			patientInfo.setPatientIdCode(patientPojo.getPatientIdCode());
			patientInfo.setPatientSex(patientPojo.getPatientSex());
			patientInfo.setPatientNation(patientPojo.getPatientNation());
			patientInfo.setPatientName(patientPojo.getPatientName());
			patientInfo.setPatientCode(patientPojo.getPatientCode());
			
			
			if(patientId!=null){
				vtePatientMapper.updateByPrimaryKeySelective(patientInfo);
				
			}else{
				vtePatientMapper.saveTbVtePatient(patientInfo);
			}
			
			Integer patientHospitId = null;
			//查询是否有本次病案
			searchMap.put("patientNumber", patientPojo.getPatientNumber());
			TbVtePatientHospitInfo patientHospitInfo = vtePatientHospitInfoMapper.queryVtePatientHospitInfoInfo(searchMap);
			
			if(patientHospitInfo == null){
				patientHospitInfo = new TbVtePatientHospitInfo();
			}
			
			patientHospitId = patientHospitInfo.getPatientHospitId();
			patientHospitInfo.setPatientCode(patientPojo.getPatientCode());
			patientHospitInfo.setPatientNumber(patientPojo.getPatientNumber());
			patientHospitInfo.setPatientInHospital(patientPojo.getPatientInHospital());
			patientHospitInfo.setPatientOutHospital(patientPojo.getPatientOutHospital());
			patientHospitInfo.setPatientAge(patientPojo.getPatientAge());
			patientHospitInfo.setPatientNativePlace(patientPojo.getPatientNativePlace());
			patientHospitInfo.setPatientJob(patientPojo.getPatientJob());
			patientHospitInfo.setPatientMarital(patientPojo.getPatientMarital());
			patientHospitInfo.setPatientPhoneNumber(patientPojo.getPatientPhoneNumber());
			patientHospitInfo.setPatientDepartment(patientPojo.getPatientDepartment());
			patientHospitInfo.setPatientArea(patientPojo.getPatientArea());
			patientHospitInfo.setPatientBed(patientPojo.getPatientBed());
			patientHospitInfo.setPatientDoctor(patientPojo.getPatientDoctor());
			
			String department = patientPojo.getPatientDepartment();
			if(patientHospitId!=null ){
				updateVteDepartmentByName(patientHospitId,department);
				
				vtePatientHospitInfoMapper.updateByPrimaryKeySelective(patientHospitInfo);
				
			}else {
				patientHospitInfo.setPatientFrom("1");
				patientHospitInfo.setPatientIsRisk("2");
				patientHospitId = vtePatientHospitInfoMapper.saveTbVtePatientHospitInfo(patientHospitInfo);
				saveVteDepartmentByName(department);
			}
		}
		
		return R.ok();
	}
	private void saveVteDepartmentByName(String department){
		if(!StringUtil.isEmpty(department)){
			Map departmentMap = new HashMap();
			departmentMap.put("departmentName",department);
			int num = vteDepartmentMapper.countAllVteDepartment(departmentMap);
			if(num==0){
				vteDepartmentMapper.saveVteDepartmentByName(departmentMap);
			}
		}
	}
	private void updateVteDepartmentByName(Integer patientHospitId,String department){
		Map map = new HashMap();
		map.put("patientHospitId", patientHospitId);
		Map departmentInfo = vtePatientHospitInfoMapper.queryVteDepartmentInfo(map);
		if(departmentInfo==null){
			departmentInfo = new HashMap();
		}
		String patientDepartment = (String)departmentInfo.get("patientDepartment");
		String departmentCode = (String)departmentInfo.get("departmentCode");
		Integer departmentId = (Integer)departmentInfo.get("departmentId");
		if(!StringUtil.isEmpty(patientDepartment)){
			if(!patientDepartment.equals(department)){
				if(StringUtil.isEmpty(departmentCode)){
					//如果编码为空 查看是否存在其他的住院信息 如果不存在 需要移除
					Map sMap =  new HashMap(); 
					sMap.put("patientDepartment", patientDepartment);
					List<Integer> patientHospitIdList = vtePatientHospitInfoMapper.queryOatientHospitIdBypatientDepartment(sMap);
					if(patientHospitIdList.size()<=1){
						TbVteDepartment vteDepartment = new TbVteDepartment();
						vteDepartment.setDepartmentId(departmentId);
						vteDepartmentMapper.deleteByPrimaryKey(vteDepartment);
					}
				}
				saveVteDepartmentByName(department);
			}
		}else{
			saveVteDepartmentByName(department);
		}
	}
}
