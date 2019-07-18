package com.insight.axiswevservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.axiswevservice.ReturnPojo.SSOReturnPojo;
import com.insight.axiswevservice.ReturnPojo.SynReturnUrlPojo;
import com.insight.axiswevservice.pojo.BatchPatientHospitInfo;
import com.insight.axiswevservice.pojo.SingleSignOnAndHospitInfo;
import com.insight.axiswevservice.service.VteSingleSignOnService;
import com.insight.core.config.ExportConfig;
import com.insight.core.config.SpringMvcContext;
import com.insight.core.util.R;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.dao.TbSystemOrgDao;
import com.insight.wisehealth.vte.dao.TbSystemRoleDao;
import com.insight.wisehealth.vte.dao.TbSystemUserDao;
import com.insight.wisehealth.vte.dao.TbSystemUserRoleDao;
import com.insight.wisehealth.vte.dao.TbVteDepartmentDao;
import com.insight.wisehealth.vte.dao.TbVtePatientDao;
import com.insight.wisehealth.vte.dao.TbVtePatientHospitInfoDao;
import com.insight.wisehealth.vte.persistence.TbSystemOrg;
import com.insight.wisehealth.vte.persistence.TbSystemRole;
import com.insight.wisehealth.vte.persistence.TbSystemUser;
import com.insight.wisehealth.vte.persistence.TbSystemUserRole;
import com.insight.wisehealth.vte.persistence.TbVteDepartment;
import com.insight.wisehealth.vte.persistence.TbVtePatient;
import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;
@Service
public class VteSingleSignOnServiceImpl implements VteSingleSignOnService{
	@Autowired
	TbVtePatientDao vtePatientMapper;
	@Autowired
	TbVtePatientHospitInfoDao vtePatientHospitInfoMapper;
	@Autowired
	TbVteDepartmentDao vteDepartmentMapper;
	@Autowired
	TbSystemOrgDao systemOrgMapper;
	@Autowired
	TbSystemUserDao systemUserMapper;
	@Autowired
	TbSystemRoleDao systemRoleMapper;
	@Autowired
	TbSystemUserRoleDao systemUserRoleMapper;
	
	@Override
	public SynReturnUrlPojo singleSignOn(SingleSignOnAndHospitInfo signOnAndHospitInfo) {
		
		SynReturnUrlPojo returnPojo = new SynReturnUrlPojo();
		
		// TODO Auto-generated method stub
		String userDepartment = signOnAndHospitInfo.getUserDepartment();
		String userCode = signOnAndHospitInfo.getUserCode();
		String userName = signOnAndHospitInfo.getUserName();
		String userRole = signOnAndHospitInfo.getUserRole();
		//处理用户信息
		Integer orgId = insertVteOrgByName(userDepartment);
		Map repeatMap=new HashMap();
		repeatMap.put("userCode", userCode); 
		TbSystemUser querySystemUserInfo =	(TbSystemUser)systemUserMapper.querySystemUserInfo(repeatMap);
		if(querySystemUserInfo==null){
			repeatMap.remove("userCode");
			repeatMap.put("userAccount", userCode);
			querySystemUserInfo =	(TbSystemUser)systemUserMapper.querySystemUserInfo(repeatMap);
		}
		if(querySystemUserInfo!=null){ //用户已经存在
			
		}else{  //用户不存在，创建新用户
			querySystemUserInfo = new TbSystemUser();
			querySystemUserInfo.setUserCode(userCode);
			querySystemUserInfo.setUserName(userName);
			querySystemUserInfo.setUserPassword(userCode);
			querySystemUserInfo.setUserAccount(userCode);
			querySystemUserInfo.setHospitalId(2);
			querySystemUserInfo.setUserForm("0");
			querySystemUserInfo.setOrgId(orgId);
			
			systemUserMapper.saveTbSystemUser(querySystemUserInfo);
			Integer userId = querySystemUserInfo.getUserId();
			
			//查询角色，配置权限
			Map searchMap = new HashMap();
			searchMap.put("roleName", userRole);
			TbSystemRole tbSystemRole =	(TbSystemRole)systemRoleMapper.querySystemRoleInfo(searchMap);
			if(tbSystemRole == null){
				searchMap.remove("roleName");
				searchMap.put("roleCode", userRole);
				tbSystemRole =	(TbSystemRole)systemRoleMapper.querySystemRoleInfo(searchMap);
			}
			if(tbSystemRole == null){
				returnPojo.setStatus("0");
				returnPojo.setMessage("操作失败");
				return returnPojo;
			}
			
			TbSystemUserRole tbSystemUserRole = new TbSystemUserRole();
			tbSystemUserRole.setRoleId(tbSystemRole.getRoleId());
			tbSystemUserRole.setUserId(userId);
			systemUserRoleMapper.saveTbSystemUserRole(tbSystemUserRole);
		}
		String data = UUID.randomUUID().toString().replaceAll("-","");  
		
		
		
		
		Integer patientHospitId = insertPatientHospitInfo(signOnAndHospitInfo);
		if(patientHospitId == null){
			returnPojo.setStatus("0");
			returnPojo.setMessage("操作失败");
			return returnPojo;
		}
		
		String modelCode = signOnAndHospitInfo.getModelCode();
		Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
		Map userCheckCodeMap = new HashMap();
		userCheckCodeMap.put(data, userCode);
		userCheckCodeMap.put("modelCode", modelCode);
		userCheckCodeMap.put("patientHospitId", patientHospitId);
		Element element=new Element(com.insight.core.common.Constants.USER_CHECK_EHCHCHE_CODE+data, userCheckCodeMap); 
		element.setTimeToLive(1800);
		ehCache.put(element);
		String nextVisitUrl = ExportConfig.nextVisitUrl + data;
		
		returnPojo.setStatus("1");
		returnPojo.setMessage("操作成功");
		returnPojo.setNextVisitUrl(nextVisitUrl);
		
		return returnPojo;
	}
	
	
	private Integer insertPatientHospitInfo(BatchPatientHospitInfo batchPatientHospitInfo){
		Map searchMap = new HashMap();
		searchMap.put("patientCode", batchPatientHospitInfo.getPatientCode());
		//查询是否有患者
		TbVtePatient patientInfo = vtePatientMapper.queryVtePatientInfo(searchMap);
		
		if(patientInfo == null){
			patientInfo = new TbVtePatient();
		}
		//存在该患者，进行更新
		Integer patientId = patientInfo.getPatientId();
		patientInfo.setPatientIdCode(batchPatientHospitInfo.getPatientIdCode());
		patientInfo.setPatientSex(batchPatientHospitInfo.getPatientSex());
		patientInfo.setPatientNation(batchPatientHospitInfo.getPatientNation());
		patientInfo.setPatientName(batchPatientHospitInfo.getPatientName());
		patientInfo.setPatientCode(batchPatientHospitInfo.getPatientCode());
		Integer patientHospitId = null;
		
		if(patientId!=null){
			vtePatientMapper.updateByPrimaryKeySelective(patientInfo);
		}else{
			vtePatientMapper.saveTbVtePatient(patientInfo);
		}
		
		//查询是否有本次病案
		searchMap.put("patientNumber", batchPatientHospitInfo.getPatientNumber());
		TbVtePatientHospitInfo patientHospitInfo = vtePatientHospitInfoMapper.queryVtePatientHospitInfoInfo(searchMap);
		
		if(patientHospitInfo == null){
			patientHospitInfo = new TbVtePatientHospitInfo();
		}
		
		patientHospitId = patientHospitInfo.getPatientHospitId();
		patientHospitInfo.setPatientCode(batchPatientHospitInfo.getPatientCode());
		patientHospitInfo.setPatientNumber(batchPatientHospitInfo.getPatientNumber());
		patientHospitInfo.setPatientInHospital(batchPatientHospitInfo.getPatientInHospital());
		patientHospitInfo.setPatientOutHospital(batchPatientHospitInfo.getPatientOutHospital());
		patientHospitInfo.setPatientAge(batchPatientHospitInfo.getPatientAge());
		patientHospitInfo.setPatientNativePlace(batchPatientHospitInfo.getPatientNativePlace());
		patientHospitInfo.setPatientJob(batchPatientHospitInfo.getPatientJob());
		patientHospitInfo.setPatientMarital(batchPatientHospitInfo.getPatientMarital());
		patientHospitInfo.setPatientPhoneNumber(batchPatientHospitInfo.getPatientPhoneNumber());
		patientHospitInfo.setPatientDepartment(batchPatientHospitInfo.getPatientDepartment());
		patientHospitInfo.setPatientArea(batchPatientHospitInfo.getPatientArea());
		patientHospitInfo.setPatientBed(batchPatientHospitInfo.getPatientBed());
		patientHospitInfo.setPatientDoctor(batchPatientHospitInfo.getPatientDoctor());
		
		String department = batchPatientHospitInfo.getPatientDepartment();
		if(patientHospitId != null ){
			updateVteDepartmentByName(patientHospitId,department);
			vtePatientHospitInfoMapper.updateByPrimaryKeySelective(patientHospitInfo);
		}else {
			patientHospitInfo.setPatientFrom("1");
			patientHospitInfo.setPatientIsRisk("2");
			vtePatientHospitInfoMapper.saveTbVtePatientHospitInfo(patientHospitInfo);
			
		    patientHospitId = patientHospitInfo.getPatientHospitId();
			insertVteDepartmentByName(department);
		}
		return patientHospitId;
		
	}
	
	private void insertVteDepartmentByName(String department){
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
				insertVteDepartmentByName(department);
			}
		}else{
			insertVteDepartmentByName(department);
		}
	}
	
	
	private Integer insertVteOrgByName(String department){
		Integer orgId  = null;
		if(!StringUtil.isEmpty(department)){
			Map orgMap = new HashMap();
			orgMap.put("orgName",department);
			TbSystemOrg tbSystemOrg =	(TbSystemOrg)systemOrgMapper.querySystemOrgInfo(orgMap);
			if(null == tbSystemOrg){
				tbSystemOrg = new TbSystemOrg();
				String modelFatherCode = ExportConfig.orgParentCode ;
				String modelCode = gainModelCode(modelFatherCode);
				if(StringUtils.isEmpty(tbSystemOrg.getOrgType())){
					tbSystemOrg.setOrgType("department");
				}
				if(tbSystemOrg.getOrgType().contains("department")){//代表着添加的是科室 需要同步到科室管理
					
					insertVteDepartmentByName(department,modelCode);
				}
				tbSystemOrg.setOrgCode(modelCode);
				tbSystemOrg.setOrgName(department);
				systemOrgMapper.saveTbSystemOrg(tbSystemOrg);
				orgId = tbSystemOrg.getOrgId();
			}else {
				orgId = tbSystemOrg.getOrgId();
			}
		}
		return orgId ;
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
	private void insertVteDepartmentByName(String department,String modelCode){
		if(!StringUtil.isEmpty(department)){
			Map departmentMap = new HashMap();
			departmentMap.put("departmentName",department);
			int num = vteDepartmentMapper.countAllVteDepartment(departmentMap);
			if(num==0){
				TbVteDepartment tbVteDepartment=new TbVteDepartment();
				tbVteDepartment.setDepartmentQualitycontrol(1);
				tbVteDepartment.setDepartmentCode(modelCode);
				tbVteDepartment.setDepartmentName(department);
				vteDepartmentMapper.insert(tbVteDepartment);
			}
		}
	}
}
