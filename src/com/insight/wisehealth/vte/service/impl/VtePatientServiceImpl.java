package com.insight.wisehealth.vte.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;
import com.insight.wisehealth.vte.common.CachedDict;
import com.insight.wisehealth.vte.common.ConstantsDict;
import com.insight.wisehealth.vte.dao.TbVteAssessmentDictDao;
import com.insight.wisehealth.vte.dao.TbVteDepartmentDao;
import com.insight.wisehealth.vte.dao.TbVteDoctorAdviceDao;
import com.insight.wisehealth.vte.dao.TbVteAssessmentDao;
import com.insight.wisehealth.vte.dao.TbVtePatientDao;
import com.insight.wisehealth.vte.dao.TbVtePatientHospitInfoDao;
import com.insight.wisehealth.vte.persistence.TbVteAssessment;
import com.insight.wisehealth.vte.persistence.TbVteAssessmentDict;
import com.insight.wisehealth.vte.persistence.TbVteDepartment;
import com.insight.wisehealth.vte.persistence.TbVteDoctorAdvice;
import com.insight.wisehealth.vte.persistence.TbVtePatient;
import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;
import com.insight.wisehealth.vte.persistencepojo.TbVteDoctorAdvicePojo;
import com.insight.wisehealth.vte.pojo.MediumHighRiskPatients;
import com.insight.wisehealth.vte.pojo.MediumHighRiskPatientsCountPojo;
import com.insight.wisehealth.vte.service.VtePatientService;
import com.sun.org.apache.bcel.internal.generic.IFNULL;


/**
 * 
 * 描述:患者数据表服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public class VtePatientServiceImpl  implements VtePatientService{
	@Autowired
	TbVtePatientDao vtePatientMapper;
	@Autowired
	TbVteDoctorAdviceDao vteDoctorAdviceMapper;
	@Autowired
	TbVteAssessmentDao vteAssessmentMapper;
	@Autowired
	TbVteAssessmentDictDao vteAssessmentDictMapper;
	@Autowired
	TbVtePatientHospitInfoDao vtePatientHospitInfoMapper;
	@Autowired
	TbVteDepartmentDao  vteDepartmentMapper;
	@Override
	public TbVtePatient saveVtePatient(Map map) throws Exception{
		TbVtePatient tbVtePatient = new TbVtePatient();
		tbVtePatient = (TbVtePatient) ToolUtil.converMapToObject(map,TbVtePatient.class);
		//校验住院号是否存在
		Map searchMap = new HashMap();
		searchMap.put("patientCode",tbVtePatient.getPatientCode());
		TbVtePatient searchVtePatient =	(TbVtePatient)vtePatientMapper.queryVtePatientInfo(searchMap);
		if(searchVtePatient!=null){//如果存在  更新患者姓名，性别，身份证号，民族
			if(!StringUtil.isEmpty(tbVtePatient.getPatientName())){
				searchVtePatient.setPatientName(tbVtePatient.getPatientName());
			}
			if(!StringUtil.isEmpty(tbVtePatient.getPatientSex())){
				searchVtePatient.setPatientSex(tbVtePatient.getPatientSex());
			}
			if(!StringUtil.isEmpty(tbVtePatient.getPatientNation())){
				searchVtePatient.setPatientNation(tbVtePatient.getPatientNation());
			}
			if(!StringUtil.isEmpty(tbVtePatient.getPatientIdCode())){
				searchVtePatient.setPatientIdCode(tbVtePatient.getPatientIdCode());
			}
			vtePatientMapper.updateByPrimaryKey(searchVtePatient);
		}else{//如果不存在 存入患者姓名 性别 身份证号 民族信息
			
			vtePatientMapper.insert(tbVtePatient);
		}
		//存入患者入院信息
		TbVtePatientHospitInfo tbVtePatientHospitInfo = new TbVtePatientHospitInfo();
		tbVtePatientHospitInfo = (TbVtePatientHospitInfo) ToolUtil.converMapToObject(map,TbVtePatientHospitInfo.class);
		String department = tbVtePatientHospitInfo.getPatientDepartment();
		if (null == tbVtePatientHospitInfo.getPatientHospitId()) {//查询住院表id是否存在 
			
			tbVtePatientHospitInfo.setPatientFrom("0");
			tbVtePatientHospitInfo.setPatientIsRisk("2");
			vtePatientHospitInfoMapper.insert(tbVtePatientHospitInfo);
			insertVteDepartmentByName(department);
		} else {//如果存在那么进行修改操作 如果不存在 进行插入操作
			//查询旧数据 科室信息是否需要修改  如果存在修改 查看旧的科室信息是否存在 其他的患者，不存在并且编码为空 则将其移除
			//查询科室信息 
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
			vtePatientHospitInfoMapper.updateByFormMap(map);
		}
		return tbVtePatient;
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
	}

	@Override
	public void delVtePatient(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbVtePatient tbVtePatient = new TbVtePatient();
			tbVtePatient.setPatientId(Integer.parseInt(id[i]));
			vtePatientMapper.deleteByPrimaryKey(tbVtePatient);
		}
	}

	@Override
	public List queryVtePatientList(Map map) throws Exception {
		List list = vtePatientMapper.queryAllVtePatientAndHospitData(map);
		
		//翻译
		Map dictCodeFieldMap = new HashMap();
		dictCodeFieldMap.put("patient_is_risk_Explain", "patientIsRiskExplain");
		dictCodeFieldMap.put("patient_padua_grade_Explain", "patientPaduaGradeExplain");
		dictCodeFieldMap.put("patient_caprini_grade_Explain", "patientCapriniGradeExplain");
		
		CachedDict.dictDataValue2DictDataNameForObj(1, "zh_CN", list, dictCodeFieldMap);
		
		return list;
	}

	/**
	 * 查询中高危检测患者列表(分页)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<MediumHighRiskPatients> queryMediumHighRiskPatientsList(Map map) {
		map.put("assessmentType", ConstantsDict.ASSESSMENT_TYPE_VTE1);
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		map.put("assessmentResult1", ConstantsDict.ASSESSMENT_RESULT_VTE2);
		map.put("assessmentResult2", ConstantsDict.ASSESSMENT_RESULT_VTE3);
		List<Map> list = vtePatientMapper.queryMediumHighRiskPatientsList(map);
		Map dictCodeFieldMap=new HashMap();
		dictCodeFieldMap.put("assessment_result", "assessmentResult");
		dictCodeFieldMap.put("assessment_item", "assessmentItem");
		CachedDict.dictDataValue2DictDataName(ConstantsDict.ORG_ID, "zh_CN",  list, dictCodeFieldMap);
		List<MediumHighRiskPatients> listM=new ArrayList();
		Map maplist=null;
		MediumHighRiskPatients mediumHighRiskPatients=null;
		for(int i=0;i<list.size();i++){
			Map mapA=new HashMap();
			mediumHighRiskPatients=new MediumHighRiskPatients();
			maplist=(Map) list.get(i);
			//最新出血风险评估
			mapA.put("patientHospitId", maplist.get("patientHospitId"));
			mapA.put("assessmentType", ConstantsDict.ASSESSMENT_TYPE_VTE2);//使用类型
			//出血风险评估数据id
			Integer assessmentTypeId=vtePatientMapper.queryLatestBleedingRiskVteAssessment(mapA);
			if(assessmentTypeId==0){
				assessmentTypeId=null;
			}
			mapA.put("assessmentType", null);
			mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE5);//使用项目
			//药物预防禁忌评估id
			Integer assessmentId2=vtePatientMapper.queryLatestBleedingRiskVteAssessment(mapA);
			mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE6);//使用项目
			//机械预防禁忌评估id
			Integer assessmentId3=vtePatientMapper.queryLatestBleedingRiskVteAssessment(mapA);
			String assessmentItemId=assessmentId2+","+assessmentId3;
			String [] assessmentItemIds=assessmentItemId.split(",");
			String  assessmentItemIdss = "";
			for(int j=0;j<assessmentItemIds.length;j++){
				if(!assessmentItemIds[j].equals("0")){
					assessmentItemIdss=assessmentItemIdss+assessmentItemIds[j]+",";
				}
			}
			if(assessmentItemIdss.length()>0){
				assessmentItemIdss=assessmentItemIdss.substring(0,assessmentItemIdss.length()-1);
			}
			//预防禁忌评估
			if(assessmentItemId!=null){
				assessmentItemId.substring(0, assessmentItemId.length()-1);
			}
			mediumHighRiskPatients.setPatientId(Integer.parseInt(maplist.get("patientId").toString()));
			mediumHighRiskPatients.setPatientCode(maplist.get("patientCode")!=null?maplist.get("patientCode").toString():null);
			mediumHighRiskPatients.setPatientName(maplist.get("patientName")!=null?maplist.get("patientName").toString():null);
			mediumHighRiskPatients.setPatientSex(maplist.get("patientSex")!=null?maplist.get("patientSex").toString():null);
			mediumHighRiskPatients.setPatientAge(maplist.get("patientAge")!=null?maplist.get("patientAge").toString():null);
			mediumHighRiskPatients.setPatientNation(maplist.get("patientNation")!=null?maplist.get("patientNation").toString():null);
			mediumHighRiskPatients.setPatientIdCode(maplist.get("patientIdCode")!=null?maplist.get("patientIdCode").toString():null);
			mediumHighRiskPatients.setPatientHospitId(maplist.get("patientHospitId")!=null?maplist.get("patientHospitId").toString():null);
			if(maplist.get("patientLastRiskDate")!=null&&maplist.get("patientLastRiskDate")!=""){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss"); 
		        Date d = null;
				try {
					d = format.parse(maplist.get("patientLastRiskDate").toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mediumHighRiskPatients.setPatientLastRiskDate(d);
			}else{
				mediumHighRiskPatients.setPatientLastRiskDate(null);

			}
			mediumHighRiskPatients.setPatientNumber(maplist.get("patientNumber")!=null?maplist.get("patientNumber").toString():null);
			mediumHighRiskPatients.setPatientArea(maplist.get("patientArea")!=null?maplist.get("patientArea").toString():null);
			mediumHighRiskPatients.setPatientBed(maplist.get("patientBed")!=null?maplist.get("patientBed").toString():null);
			mediumHighRiskPatients.setPatientLastRiskTypeExplain(maplist.get("assessmentItemExplain")!=null?maplist.get("assessmentItemExplain").toString():null);
			mediumHighRiskPatients.setAssessmentResultExplain(maplist.get("assessmentResultExplain")!=null?maplist.get("assessmentResultExplain").toString():null);
			mediumHighRiskPatients.setAssessmentTypeId(assessmentTypeId);
			mediumHighRiskPatients.setAssessmentItemId(assessmentItemIdss);
			Integer doctorAdviceId=vtePatientMapper.queryLatestBleedingRiskVteDoctorAdvice(mapA);
			if(doctorAdviceId==0){
				doctorAdviceId=null;
			}
			mediumHighRiskPatients.setDoctorAdviceId(doctorAdviceId);
			listM.add(mediumHighRiskPatients);
		}
		return listM;
	}
	/**
	 * 查询中高危检测患者数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public int queryMediumHighRiskPatientsCount(Map map) {
		map.put("assessmentType", ConstantsDict.ASSESSMENT_TYPE_VTE1);
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		map.put("assessmentResult1", ConstantsDict.ASSESSMENT_RESULT_VTE2);
		map.put("assessmentResult2", ConstantsDict.ASSESSMENT_RESULT_VTE3);
		int count=vtePatientMapper.queryMediumHighRiskPatientsCount(map);
		return count;
	}
	/**
	 * 查询各科室中高危检测患者数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<MediumHighRiskPatientsCountPojo> queryMediumHighRiskPatientsDeptCount(Map map) {
		map.put("assessmentType", ConstantsDict.ASSESSMENT_TYPE_VTE1);
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		map.put("assessmentResult1", ConstantsDict.ASSESSMENT_RESULT_VTE2);
		map.put("assessmentResult2", ConstantsDict.ASSESSMENT_RESULT_VTE3);
		List<MediumHighRiskPatientsCountPojo> list=vtePatientMapper.queryMediumHighRiskPatientsDeptCount(map);
		return list;
	}
	
	/**
	 * 查询最新出血风险评估
	 */
	@Override
	public List<TbVteAssessmentDict> queryPatientVteAssessmentDictList(String assessmentId) {
		String [] assessmentIds=assessmentId.split(",");
		TbVteAssessment tbVteAssessment=null;
		Map map=null;
		List<TbVteAssessmentDict> list=new ArrayList();
		for(int i=0;i<assessmentIds.length;i++){
			map=new HashMap();
			tbVteAssessment=new TbVteAssessment();
			tbVteAssessment.setAssessmentId(Integer.parseInt(assessmentIds[i]));
			TbVteAssessment vteAssessment=vteAssessmentMapper.selectByPrimaryKey(tbVteAssessment);
			TbVteAssessmentDict tbVteAssessmentDict=null;
			if(vteAssessment.getAssessmentSelectData()!=null){
				String [] vteAssessmentDictid=vteAssessment.getAssessmentSelectData().split(",");
					for(int j=0;j<vteAssessmentDictid.length;j++){
						tbVteAssessmentDict=new TbVteAssessmentDict();
						tbVteAssessmentDict.setAssessmentDictId(Integer.parseInt(vteAssessmentDictid[j]));
						TbVteAssessmentDict vteAssessmentDict=vteAssessmentDictMapper.selectByPrimaryKey(tbVteAssessmentDict);
						list.add(vteAssessmentDict);
					}
			}
			
		}
		return list;
	}
	
	 /**
	    * 查询最新最新医嘱处理
	    */
	@Override
	public TbVteDoctorAdvicePojo queryPatientvteDoctorAdvice(Integer doctorAdviceId) {
		TbVteDoctorAdvicePojo tbVteDoctorAdvicePojo=new TbVteDoctorAdvicePojo();
		TbVteDoctorAdvice tbVteDoctorAdvice=new TbVteDoctorAdvice();
		tbVteDoctorAdvice.setDoctorAdviceId(doctorAdviceId);
		TbVteDoctorAdvice vteDoctorAdvice=vteDoctorAdviceMapper.selectByPrimaryKey(tbVteDoctorAdvice);
		if(vteDoctorAdvice!=null){
			Map dictCodeFieldMap=new HashMap();
			dictCodeFieldMap.put("doctor_advice_result_explain", "doctorAdviceResultExplain");
			dictCodeFieldMap.put("doctor_advice_risk_explain", "doctorAdviceRiskExplain");
			CachedDict.dictDataValueToDictDataNameTranObject(ConstantsDict.ORG_ID, "zh_CN",  vteDoctorAdvice, dictCodeFieldMap,tbVteDoctorAdvicePojo);
		}
		return tbVteDoctorAdvicePojo;
	}
	
	@Override
	public int countVtePatientList(Map map) throws Exception {
		int count = (int)vtePatientMapper.countAllVtePatientAndHospitData(map);
		return count;
	}

	@Override
	public List queryAllVtePatientNp(Map map) throws Exception{
		List list = new ArrayList();
		list = vtePatientMapper.queryAllVtePatientNP( map);
		return list;
	}
	
	@Override
	public TbVtePatient queryVtePatientInfo(Map map) throws Exception{
		TbVtePatient tbVtePatient =	(TbVtePatient)vtePatientMapper.queryVtePatientInfo(map);
		return tbVtePatient;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 患者数据表
	 * @param list
	 * @param deleteVtePatientIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveVtePatientByGroup (List list,String deleteVtePatientIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteVtePatientIds)){
			String deleteVtePatientId[] =  deleteVtePatientIds.split(",");
			for(int i=0;i<deleteVtePatientId.length;i++){
				if(!StringUtil.isEmpty(deleteVtePatientId[i])){
					TbVtePatient tbVtePatient = new TbVtePatient();
					tbVtePatient.setPatientId(Integer.parseInt(deleteVtePatientId[i]));
					vtePatientMapper.deleteByPrimaryKey(tbVtePatient);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbVtePatient tbVtePatient
				= (TbVtePatient) ToolUtil.converMapToObject(map,TbVtePatient.class);
				if (null == tbVtePatient.getPatientId()) {
					//TODO 设置Pid 
					vtePatientMapper.insertSelective(tbVtePatient);
				} else {
					vtePatientMapper.updateByFormMap(map);
				}
			}
		}
	}

}
