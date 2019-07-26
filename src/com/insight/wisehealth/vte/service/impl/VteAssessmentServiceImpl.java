package com.insight.wisehealth.vte.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.common.ConstantsDict;
import com.insight.core.config.ExportConfig;
import com.insight.core.config.SpringMvcContext;
import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;
import com.insight.wisehealth.vte.common.AssessmentDict;
import com.insight.wisehealth.vte.common.CachedDict;
import com.insight.wisehealth.vte.dao.TbVteAssessmentDao;
import com.insight.wisehealth.vte.dao.TbVteDoctorAdviceDao;
import com.insight.wisehealth.vte.dao.TbVtePatientHospitInfoDao;
import com.insight.wisehealth.vte.loginpojo.LoginAssessmentDictTypePojo;
import com.insight.wisehealth.vte.loginpojo.LoginModelPojo;
import com.insight.wisehealth.vte.persistence.TbVteAssessment;
import com.insight.wisehealth.vte.persistence.TbVteDoctorAdvice;
import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;
import com.insight.wisehealth.vte.persistencepojo.TbVteAssessmentPojo;
import com.insight.wisehealth.vte.pojo.VteAssessmentAndAdvicePojo;
import com.insight.wisehealth.vte.pojo.VteAssessmentAndListPojo;
import com.insight.wisehealth.vte.pojo.VteAssessmentStrategyPojo;
import com.insight.wisehealth.vte.pojo.VteAssessmentTypePojo;
import com.insight.wisehealth.vte.pojo.VtePatientAssessmentPojo;
import com.insight.wisehealth.vte.pojo.VteRiskScoreCodePojo;
import com.insight.wisehealth.vte.service.SystemDictService;
import com.insight.wisehealth.vte.service.VteAssessmentService;
import com.insight.wisehealth.vte.strategyUtil.PreventionStrategy;
import com.insight.wisehealth.vte.strategyUtil.PreventionStrategyFactory;
import com.mysql.fabric.xmlrpc.base.Data;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;


/**
 * 
 * 描述:风险评估服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public class VteAssessmentServiceImpl  implements VteAssessmentService{
	private static final String String = null;
	@Autowired
	TbVteAssessmentDao vteAssessmentMapper;
	@Autowired
	TbVtePatientHospitInfoDao vtePatientHospitInfoMapper;
	@Autowired
	TbVteDoctorAdviceDao vteDoctorAdviceMapper;
	
	@Override
	public TbVteAssessment saveVteAssessment(Map map) throws Exception{
		TbVteAssessment tbVteAssessment = new TbVteAssessment();
		
		
		//查询患者信息
		TbVtePatientHospitInfo vtePatientHospitInfoInfo = vtePatientHospitInfoMapper.queryVtePatientHospitInfoInfo(map);
		map.put("patientCode", vtePatientHospitInfoInfo.getPatientCode());
		
		String modelCode = (String)map.get("modelCode");
		String assessmentItem = ConstantsDict.ASSESSMENT_ITEM_MAP.get(modelCode);
		map.put("assessmentItem", assessmentItem);
		String  typeCode = modelCode.substring(0, modelCode.length()-4);
		String assessmentType = ConstantsDict.ASSESSMENT_TYPE_MAP.get(typeCode);
		map.put("assessmentType", assessmentType);
		
		tbVteAssessment = (TbVteAssessment) ToolUtil.converMapToObject(map,TbVteAssessment.class);
		
		String assessmentSelectData = tbVteAssessment.getAssessmentSelectData();
		VteRiskScoreCodePojo codePojo = AssessmentDict.calculateAssessmentResultAndScore(assessmentItem, assessmentSelectData);
		
		tbVteAssessment.setAssessmentResult(codePojo.getAssessmentResult());
		map.put("assessmentResult", codePojo.getAssessmentResult());
		tbVteAssessment.setAssessmentScore(codePojo.getAssessmentScore());
		map.put("assessmentScore", codePojo.getAssessmentScore());
		
		if (null == tbVteAssessment.getAssessmentId()) {
			
			tbVteAssessment.setAssessmentFrom("0");
			int insert = vteAssessmentMapper.insert(tbVteAssessment);
			
			
		} else {
			vteAssessmentMapper.updateByFormMap(map);
		}
		
		Map insertMap = new HashMap();
		insertMap.put("patientHospitId", tbVteAssessment.getPatientHospitId());
		insertMap.put("assessmentItem",  assessmentItem);
		insertMap.put("assessmentType",  assessmentType);
		
		vteAssessmentMapper.saveVteAssessmentAfterInsert(insertMap);
		if(assessmentType.equals("1")){
			vtePatientHospitInfoInfo.setPatientIsRisk("1");
		}
		String assessmentResult = tbVteAssessment.getAssessmentResult();
		if(assessmentItem.equals("1")){
			vtePatientHospitInfoInfo.setPatientCapriniGrade(assessmentResult);
		}else if(assessmentItem.equals("2")){
			vtePatientHospitInfoInfo.setPatientPaduaGrade(assessmentResult);
		}
		
		map.put("patientLastRiskUser", map.get("userName"));
		map.put("patientLastRiskType", assessmentItem);
		map.put("patientLastRiskDate", new Date() );
		vtePatientHospitInfoInfo.setPatientLastRiskUser(map.get("userName").toString());
		vtePatientHospitInfoInfo.setPatientLastRiskType(assessmentItem);
		vtePatientHospitInfoInfo.setPatientLastRiskDate(new Date());
		vtePatientHospitInfoMapper.updateByPrimaryKeySelective(vtePatientHospitInfoInfo);
		
		
		return tbVteAssessment;
	}

	@Override
	public void delVteAssessment(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbVteAssessment tbVteAssessment = new TbVteAssessment();
			tbVteAssessment.setAssessmentId(Integer.parseInt(id[i]));
			vteAssessmentMapper.deleteByPrimaryKey(tbVteAssessment);
		}
	}

	@Override
	public List queryVteAssessmentList(Map map) throws Exception {
		List list = vteAssessmentMapper.queryAllVteAssessment(map);
		return list;
	}
	
	@Override
	public List queryVteAssessmentListWithAdvice(Map map) throws Exception {
		String judgeAssessmentOrAdvice = judgeAssessmentOrAdvice(map);
		List list = new ArrayList<VteAssessmentAndAdvicePojo>();
		if(judgeAssessmentOrAdvice.equals("All")){
			list = vteAssessmentMapper.queryAllVteAssessmentWithAdvice(map);
		}else if(judgeAssessmentOrAdvice.equals("Assessment")){
			list = vteAssessmentMapper.queryAllVteOnlyAssessment(map);
		}else if(judgeAssessmentOrAdvice.equals("Advice")){
			list = vteAssessmentMapper.queryAllVteOnlyAdvice(map);
		}
		Map dictCodeFieldMap = new HashMap();
		dictCodeFieldMap.put("assessment_stage_Explain", "assessmentStageExplain");
		dictCodeFieldMap.put("assessment_type_Explain", "assessmentTypeExplain");
		dictCodeFieldMap.put("assessment_item_Explain", "assessmentItemExplain");
		dictCodeFieldMap.put("assessment_result_Explain", "assessmentResultExplain");
		dictCodeFieldMap.put("doctor_advice_result_Explain", "doctorAdviceResultExplain");
		dictCodeFieldMap.put("doctor_advice_risk_Explain", "doctorAdviceRiskExplain");
		//字典翻译
		for (Object object : list) {
			VteAssessmentAndAdvicePojo assessmentAndAdvicePojo  = (VteAssessmentAndAdvicePojo)object;
			CachedDict.dictDataValueToDictDataNameTranObject(1, "zh_CN", assessmentAndAdvicePojo, dictCodeFieldMap, assessmentAndAdvicePojo);
			if(assessmentAndAdvicePojo.getModelName().equals("assessment")){
				assessmentAndAdvicePojo.setResultExplain(assessmentAndAdvicePojo.getAssessmentResultExplain());
			}else{
				if(StringUtil.isEmpty(assessmentAndAdvicePojo.getDoctorAdviceResultExplain())){
					assessmentAndAdvicePojo.setResultExplain(assessmentAndAdvicePojo.getDoctorAdviceRiskExplain());
				}else{
					assessmentAndAdvicePojo.setResultExplain(assessmentAndAdvicePojo.getDoctorAdviceResultExplain());
				}
			}
		}
		return list;
	}
	
	@Override
	public int countVteAssessmentListWithAdvice(Map map) throws Exception {
		
		String judgeAssessmentOrAdvice = judgeAssessmentOrAdvice(map);
		int count = 0;
		if(judgeAssessmentOrAdvice.equals("All")){
			count = (int)vteAssessmentMapper.countAllVteAssessmentWithAdvice(map);
		}else if(judgeAssessmentOrAdvice.equals("Assessment")){
			count = (int)vteAssessmentMapper.countAllVteOnlyAssessment(map);
		}else if(judgeAssessmentOrAdvice.equals("Advice")){
			count = (int)vteAssessmentMapper.countAllVteOnlyAdvice(map);
		}
		
		return count;
	}

	@Override
	public int countVteAssessmentList(Map map) throws Exception {
		int count = (int)vteAssessmentMapper.countAllVteAssessment(map);
		return count;
	}
	@Override
	public List queryAllVteAssessmentNp(Map map) throws Exception{
		List list = new ArrayList();
		list = vteAssessmentMapper.queryAllVteAssessmentNP( map);
		return list;
	}
	
	@Override
	public TbVteAssessment queryVteAssessmentInfo(Map map) throws Exception{
		TbVteAssessment tbVteAssessment =	(TbVteAssessment)vteAssessmentMapper.queryVteAssessmentInfo(map);
		return tbVteAssessment;
	}
	@Override
	public TbVteAssessmentPojo queryVteAssessmentInfoLastTime(Map map) throws Exception{
		
		String modelCode = (String)map.get("modelCode");
		String assessmentItem = ConstantsDict.ASSESSMENT_ITEM_MAP.get(modelCode);
		
		if(map.get("superModelCode")!=null&&map.get("superModelCode")!=""){
			String assessmentType = ConstantsDict.ASSESSMENT_TYPE_MAP.get(modelCode.substring(0, modelCode.length()-4));
			map.put("assessmentType", assessmentType);
		}else{
			map.put("assessmentItem", assessmentItem);
		}
	
		map.put("pageSort", " createDt desc ");
		
		TbVteAssessmentPojo tbVteAssessment =	(TbVteAssessmentPojo)vteAssessmentMapper.queryVteAssessmentInfo(map);
		Map transMap = new HashMap();
		transMap.put("assessment_result_Explain", "assessmentResultExplain");
		CachedDict.dictDataValueToDictDataNameTranObject(1, "zh_CN", tbVteAssessment, transMap, tbVteAssessment);
		
		return tbVteAssessment;
	}
	@Override
	public VtePatientAssessmentPojo queryPatientAssessment(Map map,List<LoginModelPojo> loginAssessmentList) throws Exception{
		VtePatientAssessmentPojo patientAssessmentPojo =(VtePatientAssessmentPojo)vteAssessmentMapper.queryPatientAssessmentInfo(map);
		//翻译
		Map transMap = new HashMap();
		transMap.put("assessment_result_Explain", "assessmentResultExplain");
		CachedDict.dictDataValueToDictDataNameTranObject(1, "zh_CN", patientAssessmentPojo, transMap, patientAssessmentPojo);
		
		List searchList = new ArrayList();
		int countSize = 0;
		for (LoginModelPojo LoginModelPojo : loginAssessmentList) {
			String modelCode = LoginModelPojo.getModelCode();
			if(modelCode.equals("1")){
				List<LoginModelPojo> loginModelPojoList = LoginModelPojo.getLoginModelPojoList();
				for (LoginModelPojo loginModelPojo2 : loginModelPojoList) {
					String innerModelCode = loginModelPojo2.getModelCode();
					if(innerModelCode.equals("1-001")){
						
						List<LoginModelPojo> loginModelPojoList2 = loginModelPojo2.getLoginModelPojoList();
						for (LoginModelPojo loginModelPojo3 : loginModelPojoList2) {
							Map searchMap = new HashMap();
							searchMap.put("assessmentTypeCode", ConstantsDict.ASSESSMENT_TYPE_MAP.get(loginModelPojo3.getModelCode()));
							searchMap.put("assessmentType",  loginModelPojo3.getModelName());
							searchMap.put("patientHospitId", map.get("patientHospitId"));
							searchList.add(searchMap);
							countSize ++;
						}
					}
				}
				
			}
		}
		
		List assessmentTypeList = vteAssessmentMapper.queryVteAssessmentTypePojoNP(searchList);
		//查询返回assessmentTypeList
		//遍历计算评分
		
		int  count = 0;
		
		for (Object object : assessmentTypeList) {
			VteAssessmentTypePojo vteAssessmentTypePojo = (VteAssessmentTypePojo)object;
			Integer assessmentTypeValue = vteAssessmentTypePojo.getAssessmentTypeValue();
			if(assessmentTypeValue!=null && !assessmentTypeValue.equals(0)){
				vteAssessmentTypePojo.setAssessmentTypeValue(1);
				vteAssessmentTypePojo.setAssessmentCls(ExportConfig.assessmentTypeValCls);
				count ++;
			}else{
				vteAssessmentTypePojo.setAssessmentTypeValue(0);
				vteAssessmentTypePojo.setAssessmentCls(ExportConfig.assessmentTypeCls);
			}
		}
		String assessmentCompletionDegree = "";
		//int 
		 int value = count * 100 / countSize;
		 System.out.println(value);
		 assessmentCompletionDegree = value + "%";
		patientAssessmentPojo.setAssessmentCompletionDegree(assessmentCompletionDegree);
		patientAssessmentPojo.setAssessmentTypeList(assessmentTypeList);
		return patientAssessmentPojo;
	}
	
	@Override
	public List queryVteAssessmentAdviceExport(Map map) throws Exception {
		String judgeAssessmentOrAdvice = judgeAssessmentOrAdvice(map);
		List list = new ArrayList();
		if(judgeAssessmentOrAdvice.equals("All")){
			list = vteAssessmentMapper.queryAllVteAssessmentWithAdviceForExport(map);
		}else if(judgeAssessmentOrAdvice.equals("Assessment")){
			list = vteAssessmentMapper.queryAllVteOnlyAssessmentForExport(map);
		}else if(judgeAssessmentOrAdvice.equals("Advice")){
			list = vteAssessmentMapper.queryAllVteOnlyAdviceForExport(map);
		}
		Map dictCodeFieldMap = new HashMap();
		dictCodeFieldMap.put("assessment_stage", "assessmentStage");
		dictCodeFieldMap.put("assessment_type", "assessmentType");
		dictCodeFieldMap.put("assessment_item", "assessmentItem");
		dictCodeFieldMap.put("assessment_result", "assessmentResult");
		dictCodeFieldMap.put("doctor_advice_result", "doctorAdviceResult");
		dictCodeFieldMap.put("doctor_advice_risk", "doctorAdviceRisk");
		
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
		
		//字典翻译
		for (Object object : list) {
			Map resultMap  = (Map)object;
			CachedDict.dictDataValueToDictDataName(dictMap, resultMap, dictCodeFieldMap);
			if(resultMap.get("modelName").equals("assessment")){
				resultMap.put("resultExplain", resultMap.get("assessmentResultExplain"));
			}else{
				if(resultMap.get("doctorAdviceResultExplain")==null||resultMap.get("doctorAdviceResultExplain").equals("")){
					resultMap.put("resultExplain", resultMap.get("doctorAdviceRiskExplain"));
				}else{
					resultMap.put("resultExplain", resultMap.get("doctorAdviceResultExplain"));
				}
			}
		}
		return list;
	}
	@Override
	public VteAssessmentStrategyPojo queryAssessmentStrategy(Map map) throws Exception{
		//方法体
		//查询评估
		VteAssessmentStrategyPojo vteAssessmentStrategyPojo = new VteAssessmentStrategyPojo();
		Integer patientHospitId = (Integer)map.get("patientHospitId");
		if(patientHospitId!=null){
			PreventionStrategy preventionStrategy = PreventionStrategyFactory.getPreventionStrategy();
			vteAssessmentStrategyPojo = preventionStrategy.AssessmentOfPreventionStrategies(patientHospitId);
		}
		
		
		return vteAssessmentStrategyPojo;
	}
	@Override
	public VteAssessmentAndListPojo queryAssessment(Map map) throws Exception{
		
		//AssessmentDict.setAssessmentDictListToCache();
		
		//方法体
		VteAssessmentAndListPojo assessmentAndListPojo = new VteAssessmentAndListPojo();
		Map dictCodeFieldMap = new HashMap();
		
		dictCodeFieldMap.put("assessment_item_Explain", "assessmentItemExplain");
		if(map.get("modelName").equals("assessment")){  //评估，查询评估列表
			//查询结果
			assessmentAndListPojo =(VteAssessmentAndListPojo)vteAssessmentMapper.queryAssessmentViewForAssessment(map);
			
			dictCodeFieldMap.put("assessment_result_Explain", "assessmentResultExplain");
			CachedDict.dictDataValueToDictDataNameTranObject(1, "zh_CN", assessmentAndListPojo, dictCodeFieldMap, assessmentAndListPojo);
			AssessmentDict.dictDataValueTranObjectSpecial(assessmentAndListPojo);
			
			
		}else if(map.get("modelName").equals("advice")){ //医嘱，查询医嘱列表
			//查询结果
			assessmentAndListPojo = (VteAssessmentAndListPojo)vteAssessmentMapper.queryAssessmentViewForAdvice(map);
			dictCodeFieldMap.put("doctor_advice_risk_Explain", "doctorAdviceRiskExplain");
			dictCodeFieldMap.put("doctor_advice_result_Explain", "doctorAdviceResultExplain");
			CachedDict.dictDataValueToDictDataNameTranObject(1, "zh_CN", assessmentAndListPojo, dictCodeFieldMap, assessmentAndListPojo);
			
		}
		
		return assessmentAndListPojo;
	}
	@Override
	public VteAssessmentAndListPojo queryAssessmentView(Map map) throws Exception{
		
		//AssessmentDict.setAssessmentDictListToCache();
		
		//方法体
		VteAssessmentAndListPojo assessmentAndListPojo = new VteAssessmentAndListPojo();
		Map dictCodeFieldMap = new HashMap();
		map.put("pageSort", " createDt desc ");
		if(map.get("modelName").equals("assessment")){  //评估，查询评估列表
			//查询结果
			TbVteAssessmentPojo tbVteAssessment =	(TbVteAssessmentPojo)vteAssessmentMapper.queryVteAssessmentInfo(map);
			assessmentAndListPojo.setAssessmentResult(tbVteAssessment.getAssessmentResult());
			assessmentAndListPojo.setAssessmentScore(tbVteAssessment.getAssessmentScore());
			assessmentAndListPojo.setAssessmentSelectData(tbVteAssessment.getAssessmentSelectData());
			
			dictCodeFieldMap.put("assessment_result_Explain", "assessmentResultExplain");
			CachedDict.dictDataValueToDictDataNameTranObject(1, "zh_CN", assessmentAndListPojo, dictCodeFieldMap, assessmentAndListPojo);
			AssessmentDict.dictDataValueTranObjectSpecial(assessmentAndListPojo);
			
			
		}else if(map.get("modelName").equals("advice")){ //医嘱，查询医嘱列表
			//查询结果
			
			TbVteDoctorAdvice tbVteDoctorAdvice =	(TbVteDoctorAdvice)vteDoctorAdviceMapper.queryVteDoctorAdviceInfo(map);
			assessmentAndListPojo.setDoctorAdviceResult(tbVteDoctorAdvice.getDoctorAdviceResult());
			assessmentAndListPojo.setDoctorAdviceRisk(tbVteDoctorAdvice.getDoctorAdviceRisk());
			assessmentAndListPojo.setDoctorAdviceCent(tbVteDoctorAdvice.getDoctorAdviceCent());
			
			dictCodeFieldMap.put("doctor_advice_risk_Explain", "doctorAdviceRiskExplain");
			dictCodeFieldMap.put("doctor_advice_result_Explain", "doctorAdviceResultExplain");
			CachedDict.dictDataValueToDictDataNameTranObject(1, "zh_CN", assessmentAndListPojo, dictCodeFieldMap, assessmentAndListPojo);
			
		}
		
		return assessmentAndListPojo;
	}
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////
	private String judgeAssessmentOrAdvice(Map map){
		String returnStr = "All";
		Object assessmentStage = map.get("assessmentStage");
		Object assessmentType = map.get("assessmentType");
		Object assessmentItem = map.get("assessmentItem");
		if(assessmentStage==null || assessmentStage.equals("")){ //评估阶段为空
			//判断类型是否为空
			if(assessmentType==null || assessmentType.equals("")){ //类型为空
				//判断项目是否为空，为空查询全部
				if(assessmentItem==null || assessmentItem.equals("")){
					returnStr = "All";
				}else if( assessmentItem.equals("10")){ //是否为医嘱，是，查询医嘱
					returnStr = "Advice";
				}else{
					returnStr = "Assessment"; //不是，查询评估
				}
			}else if( assessmentType.equals("6")){  //类型不为空
				if(assessmentItem==null || assessmentItem.equals("")|| assessmentItem.equals("10")){ //判断项目是否为空，或者为医嘱，查询医嘱
					returnStr = "Advice";
				}else{
					returnStr = "Null"; //不是，返回空
				}
			}else{
				if(assessmentItem!=null && assessmentItem.equals("10")){
					returnStr = "Null";
				}else{
					returnStr = "Assessment"; //不是，查询评估
				}
			}
		}else {  //评估阶段不为空
			//查看类型和项目是否为医嘱，如果为，则返回空
			if(assessmentType!=null && assessmentType.equals("6")){ // 类型为医嘱，返回空
				returnStr = "Null"; //返回空
			}else if(assessmentItem !=null && assessmentItem.equals("10")){// 类型为项目，返回空
				returnStr = "Null"; //返回空
			}else{//如果都不为医嘱，那么查询评估
				returnStr = "Assessment"; 
			}
		}
		return returnStr;
	}
	
	
	/**
	 * 添加分组数据私有方法
	 * 风险评估
	 * @param list
	 * @param deleteVteAssessmentIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveVteAssessmentByGroup (List list,String deleteVteAssessmentIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteVteAssessmentIds)){
			String deleteVteAssessmentId[] =  deleteVteAssessmentIds.split(",");
			for(int i=0;i<deleteVteAssessmentId.length;i++){
				if(!StringUtil.isEmpty(deleteVteAssessmentId[i])){
					TbVteAssessment tbVteAssessment = new TbVteAssessment();
					tbVteAssessment.setAssessmentId(Integer.parseInt(deleteVteAssessmentId[i]));
					vteAssessmentMapper.deleteByPrimaryKey(tbVteAssessment);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbVteAssessment tbVteAssessment
				= (TbVteAssessment) ToolUtil.converMapToObject(map,TbVteAssessment.class);
				if (null == tbVteAssessment.getAssessmentId()) {
					//TODO 设置Pid 
					vteAssessmentMapper.insertSelective(tbVteAssessment);
				} else {
					vteAssessmentMapper.updateByFormMap(map);
				}
			}
		}
	}
	/**
	 * 添加分组数据私有方法
	 * 风险评估
	 * @param list
	 * @param deleteVteAssessmentIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void translationAssessmentSelectData () throws Exception {
		
	}
}
