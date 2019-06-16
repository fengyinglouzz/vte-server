package com.insight.axiswevservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.wsdl.Constants;
import com.insight.axiswevservice.ReturnPojo.SSOReturnPojo;
import com.insight.axiswevservice.ReturnPojo.SynAssessmentReturnPojo;
import com.insight.axiswevservice.ReturnPojo.SynReturnPojo;
import com.insight.axiswevservice.ReturnPojo.SynReturnUrlPojo;
import com.insight.axiswevservice.enumpojo.AssessmentItem;
import com.insight.axiswevservice.pojo.BatchPatientHospitInfo;
import com.insight.axiswevservice.pojo.BatchSynAssessmentPojo;
import com.insight.axiswevservice.pojo.BatchSynAssessmentPojoUrl;
import com.insight.axiswevservice.pojo.BatchSynDoctorAdvicePojo;
import com.insight.axiswevservice.pojo.BatchSynDoctorAdvicePojoUrl;
import com.insight.axiswevservice.service.VtePatientHospitInfoAssessmentService;
import com.insight.core.config.ExportConfig;
import com.insight.core.config.SpringMvcContext;
import com.insight.core.util.R;
import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;
import com.insight.wisehealth.vte.common.AssessmentDict;
import com.insight.wisehealth.vte.common.ConstantsDict;
import com.insight.wisehealth.vte.dao.TbSystemOrgDao;
import com.insight.wisehealth.vte.dao.TbSystemRoleDao;
import com.insight.wisehealth.vte.dao.TbSystemUserDao;
import com.insight.wisehealth.vte.dao.TbSystemUserRoleDao;
import com.insight.wisehealth.vte.dao.TbVteAssessmentDao;
import com.insight.wisehealth.vte.dao.TbVteDepartmentDao;
import com.insight.wisehealth.vte.dao.TbVteDoctorAdviceDao;
import com.insight.wisehealth.vte.dao.TbVtePatientDao;
import com.insight.wisehealth.vte.dao.TbVtePatientHospitInfoDao;
import com.insight.wisehealth.vte.dao.TbVteRiskScoreDao;
import com.insight.wisehealth.vte.persistence.TbSystemOrg;
import com.insight.wisehealth.vte.persistence.TbSystemRole;
import com.insight.wisehealth.vte.persistence.TbSystemUser;
import com.insight.wisehealth.vte.persistence.TbSystemUserRole;
import com.insight.wisehealth.vte.persistence.TbVteAssessment;
import com.insight.wisehealth.vte.persistence.TbVteDepartment;
import com.insight.wisehealth.vte.persistence.TbVteDoctorAdvice;
import com.insight.wisehealth.vte.persistence.TbVtePatient;
import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;
import com.insight.wisehealth.vte.persistence.TbVteRiskScore;
import com.insight.wisehealth.vte.pojo.VteRiskScoreCodePojo;
@Service
public class VtePatientHospitInfoAssessmentServiceImpl implements VtePatientHospitInfoAssessmentService{
	@Autowired
	TbVtePatientDao vtePatientMapper;
	@Autowired
	TbVteAssessmentDao vteAssessmentMapper;
	@Autowired
	TbVtePatientHospitInfoDao vtePatientHospitInfoMapper;
	@Autowired
	TbVteDoctorAdviceDao vteDoctorAdviceMapper;
	@Autowired
	TbVteRiskScoreDao vteRiskScoreMapper;
	@Autowired
	TbVteDepartmentDao  vteDepartmentMapper;
	@Autowired
	TbSystemOrgDao systemOrgMapper;
	@Autowired
	TbSystemUserDao systemUserMapper;
	@Autowired
	TbSystemRoleDao systemRoleMapper;
	@Autowired
	TbSystemUserRoleDao systemUserRoleMapper;
	/**
	 * 版本1
	 * 风险评估数据同步。
	 * @return
	 * @throws Exception 
	 */
	@Override
	public SynAssessmentReturnPojo batchSynAssessment(BatchSynAssessmentPojo batchSynAssessmentPojo) throws Exception{
		
		SynAssessmentReturnPojo returnPojo = new SynAssessmentReturnPojo();
		Integer patientHospitId = insertPatientHospitInfo(batchSynAssessmentPojo);
		if(patientHospitId == null){
			returnPojo.setStatus("0");
			returnPojo.setStatus("操作失败");
			return returnPojo;
		}
		//保存评估
		TbVteAssessment assessment = new TbVteAssessment();
		
		assessment.setPatientHospitId(patientHospitId);
		assessment.setPatientCode(batchSynAssessmentPojo.getPatientCode());
		
		//转变成字典项
		String assessmentItem = batchSynAssessmentPojo.getAssessmentItem();
		assessment.setAssessmentType(ConstantsDict.ASSESSMENT_ITEM_TPYE_MAP.get(assessmentItem));
		assessment.setAssessmentItem(assessmentItem);
		assessment.setAssessmentStage(batchSynAssessmentPojo.getAssessmentStage());
		
		String[] assessmentSelectData = batchSynAssessmentPojo.getAssessmentSelectData();
		
		String assessmentSelect = "";
		for (String string : assessmentSelectData) {
			assessmentSelect = assessmentSelect + "," + string;
		}
		if(assessmentSelect.length()>0){
			assessmentSelect = assessmentSelect.substring(1, assessmentSelect.length());
		}
		assessment.setAssessmentSelectData(assessmentSelect);
		
		
		VteRiskScoreCodePojo codePojo = AssessmentDict.calculateAssessmentResultAndScore(batchSynAssessmentPojo.getAssessmentItem(),assessmentSelect);
		
		assessment.setAssessmentResult(codePojo.getAssessmentResult());
		assessment.setAssessmentScore(codePojo.getAssessmentScore());
		
		assessment.setAssessmentFrom("1");
		
		assessment.setCreateDt(batchSynAssessmentPojo.getAssessmentTime());
		assessment.setCreateBy(batchSynAssessmentPojo.getAssessmentUser());
		
		
		vteAssessmentMapper.saveTbVteAssessment(assessment);
		Map insertMap = new HashMap();
		insertMap.put("patientHospitId", assessment.getPatientHospitId());
		insertMap.put("assessmentItem",  assessment.getAssessmentItem());
		insertMap.put("assessmentType",  assessment.getAssessmentType());
		vteAssessmentMapper.saveVteAssessmentAfterInsert(insertMap);
		
		
		Map searchMap = new HashMap();
		//更新患者信息
		searchMap.put("patientHospitId", patientHospitId);
		
		searchMap.put("patientLastRiskDate", batchSynAssessmentPojo.getAssessmentTime());
		searchMap.put("patientLastRiskUser", batchSynAssessmentPojo.getAssessmentUser());
		searchMap.put("patientLastRiskType", batchSynAssessmentPojo.getAssessmentItem());
		
		if(assessment.getAssessmentType().equals("1")){
			searchMap.put("patientIsRisk", "1");
		}
		
		if(batchSynAssessmentPojo.getAssessmentItem().equals("1")){
			searchMap.put("patientCapriniGrade", assessment.getAssessmentResult());
		}else if(batchSynAssessmentPojo.getAssessmentItem().equals("2")){
			searchMap.put("patientPaduaGrade", assessment.getAssessmentResult());
		}
		vtePatientHospitInfoMapper.updateByFormMap(searchMap);
		
		
		Map returnMap = new HashMap();
		returnMap.put("assessmentResult", codePojo.getAssessmentResultExplain());
		returnMap.put("assessmentScore", assessment.getAssessmentScore());
		
		
	
		returnPojo.setStatus("1");
		returnPojo.setStatus("操作成功");
		returnPojo.setAssessmentScore(codePojo.getAssessmentScore());
		return returnPojo;
	}
	/**
	 * 版本1
	 * 医嘱处理数据同步。
	 * @return
	 * @throws Exception 
	 */
	@Override
	public SynReturnPojo batchSynDoctorAdvice(BatchSynDoctorAdvicePojo batchSynDoctorAdvicePojo ) throws Exception{
		SynReturnPojo returnPojo = new SynReturnPojo();
		Integer patientHospitId = insertPatientHospitInfo(batchSynDoctorAdvicePojo);
		if(patientHospitId == null){
			returnPojo.setStatus("0");
			returnPojo.setMessage("操作失败");
			return returnPojo;
		}
		//保存医嘱
		TbVteDoctorAdvice doctorAdvice = new TbVteDoctorAdvice();
		doctorAdvice.setPatientHospitId(patientHospitId);
		doctorAdvice.setPatientCode(batchSynDoctorAdvicePojo.getPatientCode());
		doctorAdvice.setDoctorAdviceCent(batchSynDoctorAdvicePojo.getDoctorAdviceCent());
		doctorAdvice.setCreateDt(batchSynDoctorAdvicePojo.getAssessmentTime());
		doctorAdvice.setCreateBy(batchSynDoctorAdvicePojo.getAssessmentUser());
		//特殊处理改成字典项
		doctorAdvice.setDoctorAdviceRisk(batchSynDoctorAdvicePojo.getDoctorAdviceRisk());
		doctorAdvice.setDoctorAdviceResult(batchSynDoctorAdvicePojo.getDoctorAdviceResult());
		doctorAdvice.setDoctorAdviceFrom("1");
		
		vteDoctorAdviceMapper.saveTbVteDoctorAdvice(doctorAdvice);
		Map insertMap = new HashMap();
		insertMap.put("patientHospitId", patientHospitId);
		vteDoctorAdviceMapper.vteDoctorAdviceAfterInsert(insertMap);
		
		returnPojo.setStatus("1");
		returnPojo.setMessage("操作成功");
		return returnPojo;
		
		
	}
	/**
	 * 版本2
	 * 风险评估数据同步。
	 * @return
	 * @throws Exception 
	 */
	@Override
	public SynReturnUrlPojo batchSynAssessmentUrl(BatchSynAssessmentPojoUrl batchSynAssessmentPojo) throws Exception{
		
		//处理用户登录信息，查询是否存在，存在登录；不存在，创建后登录
		
		String retrunStr = saveUserAndRole(batchSynAssessmentPojo.getUserCode(),batchSynAssessmentPojo.getUserName()
				,batchSynAssessmentPojo.getUserDepartment(),batchSynAssessmentPojo.getUserRole());
		
		SynReturnUrlPojo returnUrlPojo = new SynReturnUrlPojo();
		if(retrunStr==null){
			returnUrlPojo.setStatus("0");
			returnUrlPojo.setMessage("操作失败");
			return returnUrlPojo;
		}
		
		Integer patientHospitId = insertPatientHospitInfo(batchSynAssessmentPojo);
		if(patientHospitId==null){
			returnUrlPojo.setStatus("0");
			returnUrlPojo.setMessage("操作失败");
			return returnUrlPojo;
		}
		//保存评估
		TbVteAssessment assessment = new TbVteAssessment();
		
		assessment.setPatientHospitId(patientHospitId);
		assessment.setPatientCode(batchSynAssessmentPojo.getPatientCode());
		
		//转变成字典项
		
		String assessmentItem = batchSynAssessmentPojo.getAssessmentItem();
		assessment.setAssessmentType(ConstantsDict.ASSESSMENT_ITEM_TPYE_MAP.get(assessmentItem));
		assessment.setAssessmentItem(assessmentItem);
		assessment.setAssessmentStage(batchSynAssessmentPojo.getAssessmentStage());
		
		String[] assessmentSelectData = batchSynAssessmentPojo.getAssessmentSelectData();
		
		String assessmentSelect = "";
		for (String string : assessmentSelectData) {
			assessmentSelect = assessmentSelect + "," + string;
		}
		if(assessmentSelect.length()>0){
			assessmentSelect = assessmentSelect.substring(1, assessmentSelect.length());
		}
		assessment.setAssessmentSelectData(assessmentSelect);
		
		
		VteRiskScoreCodePojo codePojo = AssessmentDict.calculateAssessmentResultAndScore(batchSynAssessmentPojo.getAssessmentItem(),assessmentSelect);
		
		assessment.setAssessmentResult(codePojo.getAssessmentResult());
		assessment.setAssessmentScore(codePojo.getAssessmentScore());
		
		assessment.setAssessmentFrom("1");
		
		assessment.setCreateDt(batchSynAssessmentPojo.getAssessmentTime());
		assessment.setCreateBy(batchSynAssessmentPojo.getAssessmentUser());
		
		
		vteAssessmentMapper.saveTbVteAssessment(assessment);
		Map insertMap = new HashMap();
		insertMap.put("patientHospitId", assessment.getPatientHospitId());
		insertMap.put("assessmentItem",  assessment.getAssessmentItem());
		insertMap.put("assessmentType",  assessment.getAssessmentType());
		vteAssessmentMapper.saveVteAssessmentAfterInsert(insertMap);
		
		
		
		Map searchMap = new HashMap();
		//更新患者信息
		searchMap.put("patientHospitId", patientHospitId);
		
		searchMap.put("patientLastRiskDate", batchSynAssessmentPojo.getAssessmentTime());
		searchMap.put("patientLastRiskUser", batchSynAssessmentPojo.getAssessmentUser());
		searchMap.put("patientLastRiskType", batchSynAssessmentPojo.getAssessmentItem());
		
		if(assessment.getAssessmentType().equals("1")){
			searchMap.put("patientIsRisk", "1");
		}
		
		if(batchSynAssessmentPojo.getAssessmentItem().equals("1")){
			searchMap.put("patientCapriniGrade", assessment.getAssessmentResult());
		}else if(batchSynAssessmentPojo.getAssessmentItem().equals("2")){
			searchMap.put("patientPaduaGrade", assessment.getAssessmentResult());
		}
		vtePatientHospitInfoMapper.updateByFormMap(searchMap);
		
		
		Map returnMap = new HashMap();
		returnMap.put("assessmentResult", codePojo.getAssessmentResultExplain());
		returnMap.put("assessmentScore", assessment.getAssessmentScore());
		
		
		String nextVisitUrl = ExportConfig.nextVisitUrl + retrunStr;
		
		returnUrlPojo.setStatus("1");
		returnUrlPojo.setMessage("操作成功");
		returnUrlPojo.setNextVisitUrl(nextVisitUrl);
		
		return returnUrlPojo;
	}
	/**
	 * 版本2
	 * 医嘱处理数据同步。
	 * @return
	 */
	@Override
	public SynReturnUrlPojo batchSynDoctorAdviceUrl(BatchSynDoctorAdvicePojoUrl advicePojoUrl){
		
		//处理用户登录信息，查询是否存在，存在登录；不存在，创建后登录
		String retrunStr = saveUserAndRole(advicePojoUrl.getUserCode(),advicePojoUrl.getUserName(),advicePojoUrl.getUserDepartment(),advicePojoUrl.getUserRole());
		
		SynReturnUrlPojo returnUrlPojo = new SynReturnUrlPojo();
		if(retrunStr==null){
			returnUrlPojo.setStatus("0");
			returnUrlPojo.setMessage("操作失败");
			return returnUrlPojo;
		}
		
		
		Integer patientHospitId = insertPatientHospitInfo(advicePojoUrl);
		
		if(patientHospitId==null){
			returnUrlPojo.setStatus("0");
			returnUrlPojo.setMessage("操作失败");
			return returnUrlPojo;
		}
		
		
		//保存医嘱
		TbVteDoctorAdvice doctorAdvice = new TbVteDoctorAdvice();
		doctorAdvice.setPatientHospitId(patientHospitId);
		doctorAdvice.setPatientCode(advicePojoUrl.getPatientCode());
		doctorAdvice.setDoctorAdviceCent(advicePojoUrl.getDoctorAdviceCent());
		doctorAdvice.setCreateDt(advicePojoUrl.getAssessmentTime());
		doctorAdvice.setCreateBy(advicePojoUrl.getAssessmentUser());
		//特殊处理改成字典项
		doctorAdvice.setDoctorAdviceRisk(advicePojoUrl.getDoctorAdviceRisk());
		doctorAdvice.setDoctorAdviceResult(advicePojoUrl.getDoctorAdviceResult());
		doctorAdvice.setDoctorAdviceFrom("1");
		
		vteDoctorAdviceMapper.saveTbVteDoctorAdvice(doctorAdvice);
		Map insertMap = new HashMap();
		insertMap.put("patientHospitId", patientHospitId);
		vteDoctorAdviceMapper.vteDoctorAdviceAfterInsert(insertMap);
		
		
		String nextVisitUrl = ExportConfig.nextVisitUrl + retrunStr;
		
		returnUrlPojo.setStatus("1");
		returnUrlPojo.setMessage("操作成功");
		returnUrlPojo.setNextVisitUrl(nextVisitUrl);
		
		
		return returnUrlPojo;
		
		
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
			vtePatientHospitInfoMapper.saveTbVtePatientHospitInfo(patientHospitInfo);
			
		    patientHospitId = patientHospitInfo.getPatientHospitId();
			saveVteDepartmentByName(department);
		}
		return patientHospitId;
		
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
	private void saveVteDepartmentByName(String department,String modelCode){
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
					
					saveVteDepartmentByName(department,modelCode);
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
	
	private String saveUserAndRole(String userCode ,String userName ,String userDepartment,String userRole){
		
		
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
				return null;
			}
			
			TbSystemUserRole tbSystemUserRole = new TbSystemUserRole();
			tbSystemUserRole.setRoleId(tbSystemRole.getRoleId());
			tbSystemUserRole.setUserId(userId);
			systemUserRoleMapper.saveTbSystemUserRole(tbSystemUserRole);
		}
		String data = UUID.randomUUID().toString().replaceAll("-","");  
		
		Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
		Map userCheckCodeMap = new HashMap();
		userCheckCodeMap.put(data, userCode);
		Element element=new Element(com.insight.core.common.Constants.USER_CHECK_EHCHCHE_CODE+data, userCheckCodeMap); 
		element.setTimeToLive(1800);
		ehCache.put(element);
		
		return data ;
	}
	
}
