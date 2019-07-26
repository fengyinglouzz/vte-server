package com.insight.axiswevservice.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.axiswevservice.service.VtePatientHospitInfoAnalysisResultsService;
import com.insight.core.config.ExportConfig;
import com.insight.core.config.SpringMvcContext;
import com.insight.wisehealth.vte.common.CachedDict;
import com.insight.wisehealth.vte.common.ConstantsDict;
import com.insight.wisehealth.vte.dao.TbVteAssessmentDao;
import com.insight.wisehealth.vte.dao.TbVteDepartmentDao;
import com.insight.wisehealth.vte.dao.TbVtePatientDao;
import com.insight.wisehealth.vte.dao.TbVtePatientHospitInfoDao;
import com.insight.wisehealth.vte.persistence.TbVteDepartment;
import com.insight.wisehealth.vte.pojo.BatchPrintStatisticsDataPojo;
import com.insight.wisehealth.vte.pojo.MediumHighRiskPatientsAnalysisResultsPojo;
import com.insight.wisehealth.vte.pojo.MediumHighRiskPatientsCountPojo;
import com.insight.wisehealth.vte.pojo.MediumHighRiskPatientsSubsetPojo;
import com.insight.wisehealth.vte.pojo.OneLruAssessmentResultPojo;
import com.insight.wisehealth.vte.service.SystemDictService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service
public class VtePatientHospitInfoAnalysisResultsServiceImpl implements VtePatientHospitInfoAnalysisResultsService{
	@Autowired
	TbVtePatientDao vtePatientMapper;
	@Autowired
	TbVteAssessmentDao vteAssessmentMapper;
	@Autowired
	TbVtePatientHospitInfoDao vtePatientHospitInfoMapper;
	@Autowired
	TbVteDepartmentDao vteDepartmentMapper;
	/**
	 * ����������Ϣ���
	 * @param map
	 * @return 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<MediumHighRiskPatientsAnalysisResultsPojo> batchPrintCheck(Map map) {
		//�����һ�����
		map.put("assessmentType", ConstantsDict.ASSESSMENT_TYPE_VTE1);
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		map.put("assessmentResult1", ConstantsDict.ASSESSMENT_RESULT_VTE2);
		map.put("assessmentResult2", ConstantsDict.ASSESSMENT_RESULT_VTE3);
		map.put("assessmentResult3", ConstantsDict.ASSESSMENT_RESULT_VTE1);
		map.put("patientLastRiskDate", 1);
		map.put("patientOutHospital", map.get("isInHospital"));
		map.put("patientLastRisk", ExportConfig.patientLastRisk);
		List<MediumHighRiskPatientsCountPojo> listDc=vtePatientMapper.queryLowMediumHighRiskPatientsDeptCount(map);
		Map dictCodeFieldMap=new HashMap();
		dictCodeFieldMap.put("assessment_stage", "assessmentStage");
		dictCodeFieldMap.put("assessment_result", "assessmentResult");
		MediumHighRiskPatientsAnalysisResultsPojo mediumHighRiskPatientsAnalysisResults=null;
		List<MediumHighRiskPatientsAnalysisResultsPojo> listR=new ArrayList();
		
		// 处理constantdict 和 zhcn
		Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
		SystemDictService systemDictService = SpringMvcContext.getBean(SystemDictService.class); 
		String key = new StringBuffer(ConstantsDict.ORG_ID.toString()).append("zh_CN").toString();
		Element  element = ehCache.get(key);
		Map dictMap = null;
		if(element==null){
			Map searchMap = new HashMap();
			searchMap.put("orgId", ConstantsDict.ORG_ID);
			searchMap.put("dictInternational", "zh_CN");
			try {
				dictMap = systemDictService.queryLocalDictData(searchMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		    element=new Element(ConstantsDict.ORG_ID+"zh_CN", dictMap); 
		    ehCache.put(element);
		}else{
			dictMap = (Map) element.getObjectValue();
		}

		//������Ϣ
		for(int n=0;n<listDc.size();n++){

			mediumHighRiskPatientsAnalysisResults=new MediumHighRiskPatientsAnalysisResultsPojo();
			map.put("patientDepartment", listDc.get(n).getPatientDepartment());
			List<Map> list = vtePatientMapper.queryMediumHighRiskPatientsListNp(map);
			List<MediumHighRiskPatientsSubsetPojo> listM=new ArrayList();
			Map maplist=null;
			Map mapA=new HashMap();
			MediumHighRiskPatientsSubsetPojo mediumHighRiskPatientsSubset=null;
			for(int i=0;i<list.size();i++){
				mediumHighRiskPatientsSubset=new MediumHighRiskPatientsSubsetPojo();
				maplist=(Map) list.get(i);
				mediumHighRiskPatientsSubset.setPatientCode(maplist.get("patientCode")!=null?maplist.get("patientCode").toString():null);
				mediumHighRiskPatientsSubset.setPatientName(maplist.get("patientName")!=null?maplist.get("patientName").toString():null);
				mapA.put("patientHospitId", maplist.get("patientHospitId"));
				mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE1);
				Map mapC=vteAssessmentMapper.queryVteAssessmentAnalysisResults(mapA);
				if(mapC!=null){
					CachedDict.dictDataValueToDictDataName(dictMap,  mapC, dictCodeFieldMap);
					mediumHighRiskPatientsSubset.setCaprinAssessmentUser(mapC.get("userName")!=null?mapC.get("userName").toString():null);
					mediumHighRiskPatientsSubset.setCaprinAssessmentTime(mapC.get("createDt").toString());
					mediumHighRiskPatientsSubset.setCaprinAssessmentStage(mapC.get("assessmentStageExplain")!=null?mapC.get("assessmentStageExplain").toString():null);
					mediumHighRiskPatientsSubset.setCaprinAssessmentGrade(mapC.get("assessmentResultExplain")!=null?mapC.get("assessmentResultExplain").toString():null);
					mediumHighRiskPatientsSubset.setCaprinAssessmentSdata(mapC.get("assessmentSelectData")!=null?mapC.get("assessmentSelectData").toString():null);
					mediumHighRiskPatientsSubset.setCaprinAssessmentScore(mapC.get("assessmentScore")!=null?mapC.get("assessmentScore").toString():null);
				}
				mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE2);
				Map mapP=vteAssessmentMapper.queryVteAssessmentAnalysisResults(mapA);
				if(mapP!=null){
					CachedDict.dictDataValueToDictDataName(dictMap,  mapP, dictCodeFieldMap);
					mediumHighRiskPatientsSubset.setPaduaAssessmentUser(mapP.get("userName")!=null?mapP.get("userName").toString():null);
					mediumHighRiskPatientsSubset.setPaduaAssessmentTime(mapP.get("createDt").toString());
					mediumHighRiskPatientsSubset.setPaduaAssessmentStage(mapP.get("assessmentStageExplain")!=null?mapP.get("assessmentStageExplain").toString():null);
					mediumHighRiskPatientsSubset.setPaduaAssessmentGrade(mapP.get("assessmentResultExplain")!=null?mapP.get("assessmentResultExplain").toString():null);
					mediumHighRiskPatientsSubset.setPaduaAssessmentSdata(mapP.get("assessmentSelectData")!=null?mapP.get("assessmentSelectData").toString():null);
				}
				mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE3);
				Map mapW=vteAssessmentMapper.queryVteAssessmentAnalysisResults(mapA);
				if(mapW!=null){
					CachedDict.dictDataValueToDictDataName(dictMap,  mapW, dictCodeFieldMap);
					mediumHighRiskPatientsSubset.setSurgicalHemorrhageAssessmentUser(mapW.get("userName")!=null?mapW.get("userName").toString():null);
					mediumHighRiskPatientsSubset.setSurgicalHemorrhageAssessmentTime(mapW.get("createDt").toString());
					mediumHighRiskPatientsSubset.setSurgicalHemorrhageAssessmentResult(mapW.get("assessmentResultExplain")!=null?mapW.get("assessmentResultExplain").toString():null);
					mediumHighRiskPatientsSubset.setSurgicalHemorrhageAssessmentSdata(mapW.get("assessmentSelectData")!=null?mapW.get("assessmentSelectData").toString():null);
				}
				mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE4);
				Map mapN=vteAssessmentMapper.queryVteAssessmentAnalysisResults(mapA);
				if(mapN!=null){
					CachedDict.dictDataValueToDictDataName(dictMap,  mapN, dictCodeFieldMap);
					mediumHighRiskPatientsSubset.setMedicalBleedAssessmentUser(mapN.get("userName")!=null?mapN.get("userName").toString():null);
					mediumHighRiskPatientsSubset.setMedicalBleedAssessmentTime(mapN.get("createDt").toString());
					mediumHighRiskPatientsSubset.setMedicalBleedAssessmentResult(mapN.get("assessmentResultExplain")!=null?mapN.get("assessmentResultExplain").toString():null);
					mediumHighRiskPatientsSubset.setMedicalBleedAssessmentSdata(mapN.get("assessmentSelectData")!=null?mapN.get("assessmentSelectData").toString():null);
				}
				mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE5);
				Map mapY=vteAssessmentMapper.queryVteAssessmentAnalysisResults(mapA);
				if(mapY!=null){
					CachedDict.dictDataValueToDictDataName(dictMap,  mapY, dictCodeFieldMap);
					mediumHighRiskPatientsSubset.setDrugAssessmentUser(mapY.get("userName")!=null?mapY.get("userName").toString():null);
					mediumHighRiskPatientsSubset.setDrugAssessmentTime(mapY.get("createDt").toString());
					mediumHighRiskPatientsSubset.setDrugAssessmentResult(mapY.get("assessmentResultExplain")!=null?mapY.get("assessmentResultExplain").toString():null);
					mediumHighRiskPatientsSubset.setDrugAssessmentSdata(mapY.get("assessmentSelectData")!=null?mapY.get("assessmentSelectData").toString():null);
				}
				mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE6);
				Map mapJ=vteAssessmentMapper.queryVteAssessmentAnalysisResults(mapA);
				if(mapJ!=null){
					CachedDict.dictDataValueToDictDataName(dictMap,  mapJ, dictCodeFieldMap);
					mediumHighRiskPatientsSubset.setMachineAssessmentUser(mapJ.get("userName")!=null?mapJ.get("userName").toString():null);
					mediumHighRiskPatientsSubset.setMachineAssessmentTime(mapJ.get("createDt").toString());
					mediumHighRiskPatientsSubset.setMachineAssessmentResult(mapJ.get("assessmentResultExplain")!=null?mapJ.get("assessmentResultExplain").toString():null);
					mediumHighRiskPatientsSubset.setMachineAssessmentSdata(mapJ.get("assessmentSelectData")!=null?mapJ.get("assessmentSelectData").toString():null);
				}
				listM.add(mediumHighRiskPatientsSubset);
			}
			mediumHighRiskPatientsAnalysisResults.setAssessmentNum(listDc.get(n).getCount());
			mediumHighRiskPatientsAnalysisResults.setDepartmentName(listDc.get(n).getPatientDepartment());
			mediumHighRiskPatientsAnalysisResults.setList(listM);
			listR.add(mediumHighRiskPatientsAnalysisResults);
		}
		return listR;
	}
	
	/**
	 * @author zzy
	 */
	@Override
	public OneLruAssessmentResultPojo batchPrintSingle(Map map) {
		
		// 处理constantdict 和 zhcn
		Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
		SystemDictService systemDictService = SpringMvcContext.getBean(SystemDictService.class); 
		String key = new StringBuffer(ConstantsDict.ORG_ID.toString()).append("zh_CN").toString();
		Element  element = ehCache.get(key);
		Map dictMap = null;
		if(element==null){
			Map searchMap = new HashMap();
			searchMap.put("orgId", ConstantsDict.ORG_ID);
			searchMap.put("dictInternational", "zh_CN");
			try {
				dictMap = systemDictService.queryLocalDictData(searchMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		    element=new Element(ConstantsDict.ORG_ID+"zh_CN", dictMap); 
		    ehCache.put(element);
		}else{
			dictMap = (Map) element.getObjectValue();
		}
		
		OneLruAssessmentResultPojo oneLruAssessmentResultPojo = new OneLruAssessmentResultPojo();
		oneLruAssessmentResultPojo.setPatientCode(map.get("patientCode").toString());
		Map mapA = new HashMap();
		Map dictCodeFieldMap=new HashMap();
		dictCodeFieldMap.put("assessment_stage", "assessmentStage");
		dictCodeFieldMap.put("assessment_result", "assessmentResult");
		mapA.put("patientCode", map.get("patientCode"));
		mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		Map mapC=vteAssessmentMapper.queryLruVteAssessmentAnalysisResults(mapA);
		if(mapC!=null){
			CachedDict.dictDataValueToDictDataName(dictMap,  mapC, dictCodeFieldMap);
			oneLruAssessmentResultPojo.setCaprinAssessmentUser(mapC.get("userName")!=null?mapC.get("userName").toString():null);
			oneLruAssessmentResultPojo.setCaprinAssessmentTime(mapC.get("createDt").toString());
			oneLruAssessmentResultPojo.setCaprinAssessmentStage(mapC.get("assessmentStageExplain")!=null?mapC.get("assessmentStageExplain").toString():null);
			oneLruAssessmentResultPojo.setCaprinAssessmentGrade(mapC.get("assessmentResultExplain")!=null?mapC.get("assessmentResultExplain").toString():null);
			oneLruAssessmentResultPojo.setCaprinAssessmentSdata(mapC.get("assessmentSelectData")!=null?mapC.get("assessmentSelectData").toString():null);
			oneLruAssessmentResultPojo.setCaprinAssessmentScore(mapC.get("assessmentScore")!=null?mapC.get("assessmentScore").toString():null);
		}
		mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		Map mapP=vteAssessmentMapper.queryLruVteAssessmentAnalysisResults(mapA);
		if(mapP!=null){
			CachedDict.dictDataValueToDictDataName(dictMap,  mapP, dictCodeFieldMap);
			oneLruAssessmentResultPojo.setPaduaAssessmentUser(mapP.get("userName")!=null?mapP.get("userName").toString():null);
			oneLruAssessmentResultPojo.setPaduaAssessmentTime(mapP.get("createDt").toString());
			oneLruAssessmentResultPojo.setPaduaAssessmentStage(mapP.get("assessmentStageExplain")!=null?mapP.get("assessmentStageExplain").toString():null);
			oneLruAssessmentResultPojo.setPaduaAssessmentGrade(mapP.get("assessmentResultExplain")!=null?mapP.get("assessmentResultExplain").toString():null);
			oneLruAssessmentResultPojo.setPaduaAssessmentSdata(mapP.get("assessmentSelectData")!=null?mapP.get("assessmentSelectData").toString():null);
			oneLruAssessmentResultPojo.setPaduaAssessmentScore(mapP.get("assessmentScore")!=null?mapP.get("assessmentScore").toString():null);
		}
		mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE3);
		Map mapW=vteAssessmentMapper.queryLruVteAssessmentAnalysisResults(mapA);
		if(mapW!=null){
			CachedDict.dictDataValueToDictDataName(dictMap,  mapW, dictCodeFieldMap);
			oneLruAssessmentResultPojo.setSurgicalHemorrhageAssessmentUser(mapW.get("userName")!=null?mapW.get("userName").toString():null);
			oneLruAssessmentResultPojo.setSurgicalHemorrhageAssessmentTime(mapW.get("createDt").toString());
			oneLruAssessmentResultPojo.setSurgicalHemorrhageAssessmentResult(mapW.get("assessmentResultExplain")!=null?mapW.get("assessmentResultExplain").toString():null);
			oneLruAssessmentResultPojo.setSurgicalHemorrhageAssessmentSdata(mapW.get("assessmentSelectData")!=null?mapW.get("assessmentSelectData").toString():null);
		}
		mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE4);
		Map mapN=vteAssessmentMapper.queryLruVteAssessmentAnalysisResults(mapA);
		if(mapN!=null){
			CachedDict.dictDataValueToDictDataName(dictMap,  mapN, dictCodeFieldMap);
			oneLruAssessmentResultPojo.setMedicalBleedAssessmentUser(mapN.get("userName")!=null?mapN.get("userName").toString():null);
			oneLruAssessmentResultPojo.setMedicalBleedAssessmentTime(mapN.get("createDt").toString());
			oneLruAssessmentResultPojo.setMedicalBleedAssessmentResult(mapN.get("assessmentResultExplain")!=null?mapN.get("assessmentResultExplain").toString():null);
			oneLruAssessmentResultPojo.setMedicalBleedAssessmentSdata(mapN.get("assessmentSelectData")!=null?mapN.get("assessmentSelectData").toString():null);
		}
		mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE5);
		Map mapY=vteAssessmentMapper.queryLruVteAssessmentAnalysisResults(mapA);
		if(mapY!=null){
			CachedDict.dictDataValueToDictDataName(dictMap,  mapY, dictCodeFieldMap);
			oneLruAssessmentResultPojo.setDrugAssessmentUser(mapY.get("userName")!=null?mapY.get("userName").toString():null);
			oneLruAssessmentResultPojo.setDrugAssessmentTime(mapY.get("createDt").toString());
			oneLruAssessmentResultPojo.setDrugAssessmentResult(mapY.get("assessmentResultExplain")!=null?mapY.get("assessmentResultExplain").toString():null);
			oneLruAssessmentResultPojo.setDrugAssessmentSdata(mapY.get("assessmentSelectData")!=null?mapY.get("assessmentSelectData").toString():null);
		}
		mapA.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE6);
		Map mapJ=vteAssessmentMapper.queryLruVteAssessmentAnalysisResults(mapA);
		if(mapJ!=null){
			CachedDict.dictDataValueToDictDataName(dictMap,  mapJ, dictCodeFieldMap);
			oneLruAssessmentResultPojo.setMachineAssessmentUser(mapJ.get("userName")!=null?mapJ.get("userName").toString():null);
			oneLruAssessmentResultPojo.setMachineAssessmentTime(mapJ.get("createDt").toString());
			oneLruAssessmentResultPojo.setMachineAssessmentResult(mapJ.get("assessmentResultExplain")!=null?mapJ.get("assessmentResultExplain").toString():null);
			oneLruAssessmentResultPojo.setMachineAssessmentSdata(mapJ.get("assessmentSelectData")!=null?mapJ.get("assessmentSelectData").toString():null);
		}
		return oneLruAssessmentResultPojo;
	}
	
	/**
    * �ʿط������
	 * @return 
    */
	@Override
	public List<BatchPrintStatisticsDataPojo> batchPrintStatisticsData(Map map) {
		map.put("dateS", map.get("startDate"));
		map.put("dateE", map.get("endDate"));
		map.put("date", "1");
		map.put("dateType", 3);
		//��������
		List<Map> mapNumberPatientList=vtePatientHospitInfoMapper.queryNumberPatient(map);
		//VTE��������
		map.put("doctorAdviceResult1", ConstantsDict.DOCTOR_ADVICE_RESULT1);
		map.put("doctorAdviceResult2", ConstantsDict.DOCTOR_ADVICE_RESULT2);
		List<Map> mapOnsetOfNumberPatientList=vtePatientHospitInfoMapper.queryOnsetOfNumberPatient(map);
		//���VTE����������������
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		List<Map> mapVteRiskAssessmentPatientList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		//��Ѫ������������
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE3);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE4);
		List<Map> mapBleedingRiskAssessmentPatientList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		//Caprini��Σ����
		map.put("assessmentType", ConstantsDict.ASSESSMENT_TYPE_VTE1);
		map.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		map.put("assessmentResult", ConstantsDict.ASSESSMENT_RESULT_VTE2);
		List<Map> capriniMiddleRiskList=vtePatientHospitInfoMapper.queryCapriniPaduaRiskList(map);
		//Caprini��Σ����
		map.put("assessmentResult", ConstantsDict.ASSESSMENT_RESULT_VTE3);
		List<Map> capriniHighRiskList=vtePatientHospitInfoMapper.queryCapriniPaduaRiskList(map);
		//Padua��Σ����
		map.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		List<Map> paduaHighRiskList=vtePatientHospitInfoMapper.queryCapriniPaduaRiskList(map);
		//���и�Σ�����������һ��VTE��������ΪCaprini�����ҽ��Ϊ��Σ���Σ�Ĳ�������+���һ��VTE��������ΪPadua�����ҽ��Ϊ��Σ�Ĳ�������������Ԥ����ʩ�ʣ����������ʣ�
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		map.put("assessmentResult1", ConstantsDict.ASSESSMENT_RESULT_VTE2);
		map.put("assessmentResult2", ConstantsDict.ASSESSMENT_RESULT_VTE3);
		List<Map> middleHighRiskList=vtePatientHospitInfoMapper.queryCapriniPaduaRiskSumList(map);
		//������ҩ��Ԥ������
		map.put("doctorAdviceRisk", ConstantsDict.DOCTOR_ADVICE_RISK2);
		List<Map> medicinePreventiveNumber=vtePatientHospitInfoMapper.queryPreventiveNumberList(map);
		//�����Ż�еԤ������
		map.put("doctorAdviceRisk", ConstantsDict.DOCTOR_ADVICE_RISK3);
		List<Map> mechanicalPreventiveNumber=vtePatientHospitInfoMapper.queryPreventiveNumberList(map);
		//��Ժ24Сʱ�����VTE����������������
		map.put("createDtOneDay", "1");
		List<Map> mapOneDayVteRiskAssessmentPatientList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		//��Ѫ������������
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE3);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE4);
		map.put("createDtOneDay", "");
		List<Map> mapBleedingList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		//��Ժ24����ɳ�Ѫ������������
		map.put("createDtOneDay", "1");
		List<Map> mapOneDayBleedingList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		//���һ�γ�Ѫ��������Ϊ ��Ƴ�Ѫ���������ҽ��Ϊ �� �Ĳ�������+���һ�γ�Ѫ��������Ϊ �ڿƳ�Ѫ���������ҽ��Ϊ �� �Ĳ�������
		map.put("assessmentType", ConstantsDict.ASSESSMENT_TYPE_VTE2);
		map.put("assessmentResult", ConstantsDict.ASSESSMENT_RESULT_VTE4);
		List<Map> mapRecentlyBleedingList=vtePatientHospitInfoMapper.queryRecentlyBleedingRiskSumList(map);
		//��Ƴ�Ѫ���ո�Σ����
		map.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE3);
		List<Map> surgeryBleedingRiskList=vtePatientHospitInfoMapper.queryRecentlyBleedingRiskList(map);
		//�ڿƳ�Ѫ���ո�Σ����
		map.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE4);
		List<Map> medicineBleedingRiskList=vtePatientHospitInfoMapper.queryRecentlyBleedingRiskList(map);
		//ȷ��DVT��������
		map.put("doctorAdviceResult", ConstantsDict.DOCTOR_ADVICE_RESULT2);
		List<Map> listDvt=vtePatientHospitInfoMapper.queryPrevalenceAssessment(map);
		//ȷ��PTE��������
		map.put("doctorAdviceResult", ConstantsDict.DOCTOR_ADVICE_RESULT1);
		List<Map> listPte=vtePatientHospitInfoMapper.queryPrevalenceAssessment(map);
		//ȷ��DVT+PTE��������
		map.put("doctorAdviceResult1", ConstantsDict.DOCTOR_ADVICE_RESULT1);
		map.put("doctorAdviceResult2", ConstantsDict.DOCTOR_ADVICE_RESULT2);
		map.put("doctorAdviceResult", "");
		List<Map> listVte=vtePatientHospitInfoMapper.queryPrevalenceAssessment(map);
		//��ѯ���п���
		List<TbVteDepartment> listD=vteDepartmentMapper.queryAllVteDepartmentNP(map);
		BatchPrintStatisticsDataPojo batchPrintStatisticsData=null;
		DecimalFormat df = new DecimalFormat("0.00");
		List<BatchPrintStatisticsDataPojo> listM=new ArrayList();
		for(int i=0;i<listD.size();i++){
			batchPrintStatisticsData=new BatchPrintStatisticsDataPojo();
			String departmentName=listD.get(i).getDepartmentName();
			batchPrintStatisticsData.setDepartmentName(departmentName);
			for(int n=0;n<mapNumberPatientList.size();n++){
				if(departmentName.equals(mapNumberPatientList.get(n).get("patientDepartment"))){
					batchPrintStatisticsData.setPatientNum(mapNumberPatientList.get(n).get("count").toString());
					break;
				}
			}
			for(int n=0;n<mapOnsetOfNumberPatientList.size();n++){
				if(departmentName.equals(mapOnsetOfNumberPatientList.get(n).get("patientDepartment"))){
					Double rate= (double)Integer.parseInt(mapOnsetOfNumberPatientList.get(n).get("count").toString())*100/(double)Integer.parseInt(batchPrintStatisticsData.getPatientNum());
					batchPrintStatisticsData.setPatienRate(df.format(rate)+"%");
					break;
				}
			}
			for(int n=0;n<mapVteRiskAssessmentPatientList.size();n++){
				if(departmentName.equals(mapVteRiskAssessmentPatientList.get(n).get("patientDepartment"))){
					Double rate= (double)Integer.parseInt(mapVteRiskAssessmentPatientList.get(n).get("count").toString())*100/(double)Integer.parseInt(batchPrintStatisticsData.getPatientNum());
					batchPrintStatisticsData.setVteAssessmentRate(df.format(rate)+"%");
					batchPrintStatisticsData.setVteAssessmentNum(mapVteRiskAssessmentPatientList.get(n).get("count").toString());
					break;
				}
			}
			for(int n=0;n<mapBleedingRiskAssessmentPatientList.size();n++){
				if(departmentName.equals(mapBleedingRiskAssessmentPatientList.get(n).get("patientDepartment"))){
					Double rate= (double)Integer.parseInt(mapBleedingRiskAssessmentPatientList.get(n).get("count").toString())*100/(double)Integer.parseInt(batchPrintStatisticsData.getPatientNum());
					batchPrintStatisticsData.setBleedAssessmentRate(df.format(rate)+"%");
					batchPrintStatisticsData.setBleedAssessmentNum(mapBleedingRiskAssessmentPatientList.get(n).get("count").toString());
					break;
				}
			}
			for(int n=0;n<capriniMiddleRiskList.size();n++){
				if(departmentName.equals(capriniMiddleRiskList.get(n).get("patientDepartment"))){
					batchPrintStatisticsData.setCapriniMiddleNum(capriniMiddleRiskList.get(n).get("count").toString());
					break;
				}
			}
			for(int n=0;n<capriniHighRiskList.size();n++){
				if(departmentName.equals(capriniHighRiskList.get(n).get("patientDepartment"))){
					batchPrintStatisticsData.setCapriniHighNum(capriniHighRiskList.get(n).get("count").toString());
					break;
				}
			}
			for(int n=0;n<paduaHighRiskList.size();n++){
				if(departmentName.equals(paduaHighRiskList.get(n).get("patientDepartment"))){
					batchPrintStatisticsData.setPaduaHighNum(paduaHighRiskList.get(n).get("count").toString());
					break;
				}
			}
			//���һ��VTE��������ΪCaprini�����ҽ��Ϊ��Σ���Σ�Ĳ�������+���һ��VTE��������ΪPadua�����ҽ��Ϊ��Σ�Ĳ�������
			Double countRisk=0.00;
			for(int n=0;n<middleHighRiskList.size();n++){
				if(departmentName.equals(middleHighRiskList.get(n).get("patientDepartment"))){
					countRisk=Double.valueOf(middleHighRiskList.get(n).get("count").toString());
					break;
				}
			}
			for(int n=0;n<medicinePreventiveNumber.size();n++){
				if(departmentName.equals(medicinePreventiveNumber.get(n).get("patientDepartment"))){
					batchPrintStatisticsData.setDrugPreventionNum(medicinePreventiveNumber.get(n).get("count").toString());
					if(countRisk>0){
						Double rate= (double)Integer.parseInt(medicinePreventiveNumber.get(n).get("count").toString())*100/(double)countRisk;
						batchPrintStatisticsData.setDrugPreventionRate(df.format(rate)+"%");
					}
					break;
				}
			}
			for(int n=0;n<mechanicalPreventiveNumber.size();n++){
				if(departmentName.equals(mechanicalPreventiveNumber.get(n).get("patientDepartment"))){
					batchPrintStatisticsData.setMachinePreventionNum(mechanicalPreventiveNumber.get(n).get("count").toString());
					if(countRisk>0){
						Double rate= (double)Integer.parseInt(mechanicalPreventiveNumber.get(n).get("count").toString())*100/(double)countRisk;
						batchPrintStatisticsData.setMachinePreventionRate(df.format(rate)+"%");
					}else{
						batchPrintStatisticsData.setMachinePreventionRate(null);

					}
					break;
				}
			}
			if(batchPrintStatisticsData.getVteAssessmentNum()!=null){
				Double rate= countRisk*100/(double)Double.valueOf(batchPrintStatisticsData.getVteAssessmentNum());
				batchPrintStatisticsData.setVteAssessmentRiskRate(df.format(rate)+"%");
			}else{
				batchPrintStatisticsData.setVteAssessmentRiskRate(null);
			}
			for(int n=0;n<mapOneDayVteRiskAssessmentPatientList.size();n++){
				if(departmentName.equals(mapOneDayVteRiskAssessmentPatientList.get(n).get("patientDepartment"))){
					if(batchPrintStatisticsData.getVteAssessmentNum()!=null){
						Double rate= (double)Integer.parseInt(mapOneDayVteRiskAssessmentPatientList.get(n).get("count").toString())*100/(double)Double.valueOf(batchPrintStatisticsData.getVteAssessmentNum());
						batchPrintStatisticsData.setVteAssessmentTimelyRate(df.format(rate)+"%");
					}else{
						batchPrintStatisticsData.setVteAssessmentTimelyRate(null);

					}
					break;
				}
			}
			for(int n=0;n<mapRecentlyBleedingList.size();n++){
				if(departmentName.equals(mapRecentlyBleedingList.get(n).get("patientDepartment"))){
					if(batchPrintStatisticsData.getBleedAssessmentNum()!=null){
						Double rate= (double)Integer.parseInt(mapRecentlyBleedingList.get(n).get("count").toString())*100/(double)Double.valueOf(batchPrintStatisticsData.getVteAssessmentNum());
						batchPrintStatisticsData.setBleedAssessmentRate(df.format(rate)+"%");
					}else{
						batchPrintStatisticsData.setBleedAssessmentRate(null);
					}
					break;
				}
			}
			for(int n=0;n<mapOneDayBleedingList.size();n++){
				if(departmentName.equals(mapOneDayBleedingList.get(n).get("patientDepartment"))){
					if(batchPrintStatisticsData.getBleedAssessmentNum()!=null){
						Double rate= (double)Integer.parseInt(mapOneDayBleedingList.get(n).get("count").toString())*100/(double)Double.valueOf(batchPrintStatisticsData.getVteAssessmentNum());
						batchPrintStatisticsData.setBleedAssessmentTimelyRate(df.format(rate)+"%");
					}else{
						batchPrintStatisticsData.setBleedAssessmentTimelyRate(null);
					}
					break;
				}
			}
			for(int n=0;n<surgeryBleedingRiskList.size();n++){
				if(departmentName.equals(surgeryBleedingRiskList.get(n).get("patientDepartment"))){
					batchPrintStatisticsData.setSurgeryBleedAssessmentNum(surgeryBleedingRiskList.get(n).get("count").toString());
					break;
				}
			}
			for(int n=0;n<medicineBleedingRiskList.size();n++){
				if(departmentName.equals(medicineBleedingRiskList.get(n).get("patientDepartment"))){
					batchPrintStatisticsData.setInternalBleedAssessmentNum(medicineBleedingRiskList.get(n).get("count").toString());
					break;
				}
			}
			for(int n=0;n<listDvt.size();n++){
				if(departmentName.equals(listDvt.get(n).get("patientDepartment"))){
					batchPrintStatisticsData.setDvtPatientNum(listDvt.get(n).get("count").toString());
					break;
				}
			}
			for(int n=0;n<listPte.size();n++){
				if(departmentName.equals(listPte.get(n).get("patientDepartment"))){
					batchPrintStatisticsData.setPtePatientNum(listPte.get(n).get("count").toString());
					break;
				}
			}
			for(int n=0;n<listVte.size();n++){
				if(departmentName.equals(listVte.get(n).get("patientDepartment"))){
					batchPrintStatisticsData.setDvtandptePatientNum(listVte.get(n).get("count").toString());
					break;
				}
			}
			listM.add(batchPrintStatisticsData);
		}
		return listM;
	}
	
}
