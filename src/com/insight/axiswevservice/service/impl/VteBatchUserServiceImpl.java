package com.insight.axiswevservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.axiswevservice.pojo.BatchSystemUserList;
import com.insight.axiswevservice.pojo.BatchSystemUsryPojo;
import com.insight.axiswevservice.service.VteBatchUserService;
import com.insight.core.util.R;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.dao.TbSystemOrgDao;
import com.insight.wisehealth.vte.dao.TbSystemRoleDao;
import com.insight.wisehealth.vte.dao.TbSystemUserDao;
import com.insight.wisehealth.vte.dao.TbSystemUserRoleDao;
import com.insight.wisehealth.vte.dao.TbVteDepartmentDao;
import com.insight.wisehealth.vte.persistence.TbSystemOrg;
import com.insight.wisehealth.vte.persistence.TbSystemRole;
import com.insight.wisehealth.vte.persistence.TbSystemUser;
import com.insight.wisehealth.vte.persistence.TbSystemUserRole;
import com.insight.wisehealth.vte.persistence.TbVteDepartment;
@Service
public class VteBatchUserServiceImpl implements VteBatchUserService{
	@Autowired
	TbSystemUserDao systemUserMapper;
	@Autowired
	TbSystemOrgDao systemOrgMapper;
	@Autowired
	TbSystemRoleDao systemRoleMapper;
	@Autowired
	TbSystemUserRoleDao systemUserRoleMapper;
	@Autowired
	TbVteDepartmentDao vteDepartmentMapper;

	@Override
	public R batchSynUserInfo(List<BatchSystemUsryPojo> systemUserPojoList) throws Exception {
		for (BatchSystemUsryPojo tbSystemUserPojo : systemUserPojoList) {
			String userCode = tbSystemUserPojo.getUserCode();
			String userName = tbSystemUserPojo.getUserName();
			
			Integer orgId = insertVteOrgByName(tbSystemUserPojo.getUserDepartment());
			
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
				querySystemUserInfo.setUserName(userName);
				querySystemUserInfo.setUserCode(userCode);
				querySystemUserInfo.setUserPassword(userCode);
				querySystemUserInfo.setHospitalId(2);
				querySystemUserInfo.setUserAccount(userCode);
				querySystemUserInfo.setUserForm("0");
				querySystemUserInfo.setOrgId(orgId);
				
				systemUserMapper.insert(querySystemUserInfo);
				int userId = querySystemUserInfo.getUserId();
				querySystemUserInfo.setUserId(querySystemUserInfo.getUserId());
				
				//查询角色，配置权限
				
				String userRole = tbSystemUserPojo.getUserRole();
				Map searchMap = new HashMap();
				searchMap.put("roleName", userRole);
				TbSystemRole tbSystemRole =	(TbSystemRole)systemRoleMapper.querySystemRoleInfo(searchMap);
				if(tbSystemRole == null){
					searchMap.remove("roleName");
					searchMap.put("roleCode", userRole);
					tbSystemRole =	(TbSystemRole)systemRoleMapper.querySystemRoleInfo(searchMap);
				}
				if(tbSystemRole == null){
					return R.error();
				}
				
				TbSystemUserRole tbSystemUserRole = new TbSystemUserRole();
				tbSystemUserRole.setRoleId(tbSystemRole.getRoleId());
				tbSystemUserRole.setUserId(userId);
				systemUserRoleMapper.insert(tbSystemUserRole);
			}
			
			
		}
		
		
		return R.ok();
	}
	private Integer insertVteOrgByName(String department){
		Integer orgId  = null;
		if(!StringUtil.isEmpty(department)){
			Map orgMap = new HashMap();
			orgMap.put("orgName",department);
			TbSystemOrg tbSystemOrg =	(TbSystemOrg)systemOrgMapper.querySystemOrgInfo(orgMap);
			if(null == tbSystemOrg){
				tbSystemOrg = new TbSystemOrg();
				String modelFatherCode = "001" ;
				String modelCode = gainModelCode(modelFatherCode);
				if(StringUtils.isEmpty(tbSystemOrg.getOrgType())){
					tbSystemOrg.setOrgType("department");
				}
				if(tbSystemOrg.getOrgType().contains("department")){//代表着添加的是科室 需要同步到科室管理
					
					insertVteDepartmentByName(department,modelCode);
					/*TbVteDepartment tbVteDepartment=new TbVteDepartment();
					tbVteDepartment.setDepartmentCode(modelCode);
					tbVteDepartment.setDepartmentName(tbSystemOrg.getOrgName());
					vteDepartmentMapper.insert(tbVteDepartment);*/
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
	private void insertVteDepartmentByName(String department){
		if(!StringUtil.isEmpty(department)){
			Map departmentMap = new HashMap();
			departmentMap.put("departmentName",department);
			int num = vteDepartmentMapper.countAllVteDepartment(departmentMap);
			if(num==0){
				vteDepartmentMapper.insertVteDepartmentByName(departmentMap);
			}
		}
	}private void insertVteDepartmentByName(String department,String modelCode){
		if(!StringUtil.isEmpty(department)){
			Map departmentMap = new HashMap();
			departmentMap.put("departmentName",department);
			int num = vteDepartmentMapper.countAllVteDepartment(departmentMap);
			if(num==0){
				TbVteDepartment tbVteDepartment=new TbVteDepartment();
				tbVteDepartment.setDepartmentCode(modelCode);
				tbVteDepartment.setDepartmentName(department);
				vteDepartmentMapper.insert(tbVteDepartment);
			}
		}
	}
}
