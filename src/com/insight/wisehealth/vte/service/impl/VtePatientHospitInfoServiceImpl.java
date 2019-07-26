package com.insight.wisehealth.vte.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;
import com.insight.wisehealth.vte.common.ConstantsDict;
import com.insight.wisehealth.vte.dao.TbVteDepartmentDao;
import com.insight.wisehealth.vte.dao.TbVtePatientHospitInfoDao;
import com.insight.wisehealth.vte.persistence.TbVteDepartment;
import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;
import com.insight.wisehealth.vte.pojo.BleedingQualityRiskAssessmentPojo;
import com.insight.wisehealth.vte.pojo.PatientQualityViewKpiPojo;
import com.insight.wisehealth.vte.pojo.PatientQualityViewKpiRightPojo;
import com.insight.wisehealth.vte.pojo.PrevalenceAssessmentPojo;
import com.insight.wisehealth.vte.pojo.PreventionForMiddleHighRiskPatientPojo;
import com.insight.wisehealth.vte.pojo.PreventiveRatePatientPojo;
import com.insight.wisehealth.vte.pojo.QualityRiskAssessmentPojo;
import com.insight.wisehealth.vte.service.VtePatientHospitInfoService;


/**
 * 
 * 描述:住院信息服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public class VtePatientHospitInfoServiceImpl  implements VtePatientHospitInfoService{
	@Autowired
	TbVtePatientHospitInfoDao vtePatientHospitInfoMapper;
	@Autowired
	TbVteDepartmentDao vteDepartmentMapper;
	@Override
	public TbVtePatientHospitInfo saveVtePatientHospitInfo(Map map) throws Exception{
		TbVtePatientHospitInfo tbVtePatientHospitInfo = new TbVtePatientHospitInfo();
		tbVtePatientHospitInfo = (TbVtePatientHospitInfo) ToolUtil.converMapToObject(map,TbVtePatientHospitInfo.class);
		if (null == tbVtePatientHospitInfo.getPatientHospitId()) {
			vtePatientHospitInfoMapper.insert(tbVtePatientHospitInfo);
		} else {
			vtePatientHospitInfoMapper.updateByFormMap(map);
		}
		return tbVtePatientHospitInfo;
	}

	@Override
	public void delVtePatientHospitInfo(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbVtePatientHospitInfo tbVtePatientHospitInfo = new TbVtePatientHospitInfo();
			tbVtePatientHospitInfo.setPatientHospitId(Integer.parseInt(id[i]));
			vtePatientHospitInfoMapper.deleteByPrimaryKey(tbVtePatientHospitInfo);
		}
	}

	@Override
	public List queryVtePatientHospitInfoList(Map map) throws Exception {
		List list = vtePatientHospitInfoMapper.queryAllVtePatientHospitInfo(map);
		return list;
	}

	@Override
	public int countVtePatientHospitInfoList(Map map) throws Exception {
		int count = (int)vtePatientHospitInfoMapper.countAllVtePatientHospitInfo(map);
		return count;
	}

	@Override
	public List queryAllVtePatientHospitInfoNp(Map map) throws Exception{
		List list = new ArrayList();
		list = vtePatientHospitInfoMapper.queryAllVtePatientHospitInfoNP( map);
		return list;
	}
	
	@Override
	public TbVtePatientHospitInfo queryVtePatientHospitInfoInfo(Map map) throws Exception{
		TbVtePatientHospitInfo tbVtePatientHospitInfo =	(TbVtePatientHospitInfo)vtePatientHospitInfoMapper.queryVtePatientHospitInfoInfo(map);
		return tbVtePatientHospitInfo;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 住院信息
	 * @param list
	 * @param deleteVtePatientHospitInfoIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveVtePatientHospitInfoByGroup (List list,String deleteVtePatientHospitInfoIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteVtePatientHospitInfoIds)){
			String deleteVtePatientHospitInfoId[] =  deleteVtePatientHospitInfoIds.split(",");
			for(int i=0;i<deleteVtePatientHospitInfoId.length;i++){
				if(!StringUtil.isEmpty(deleteVtePatientHospitInfoId[i])){
					TbVtePatientHospitInfo tbVtePatientHospitInfo = new TbVtePatientHospitInfo();
					tbVtePatientHospitInfo.setPatientHospitId(Integer.parseInt(deleteVtePatientHospitInfoId[i]));
					vtePatientHospitInfoMapper.deleteByPrimaryKey(tbVtePatientHospitInfo);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbVtePatientHospitInfo tbVtePatientHospitInfo
				= (TbVtePatientHospitInfo) ToolUtil.converMapToObject(map,TbVtePatientHospitInfo.class);
				if (null == tbVtePatientHospitInfo.getPatientHospitId()) {
					//TODO 设置Pid 
					vtePatientHospitInfoMapper.insertSelective(tbVtePatientHospitInfo);
				} else {
					vtePatientHospitInfoMapper.updateByFormMap(map);
				}
			}
		}
	}

	/**
    * 质控视图KPI检测
    */
	@Override
	public PatientQualityViewKpiPojo queryPatientQualityViewKpi(Map map) {
		if(map.get("dateType").toString().equals("3")){
			if(!map.get("date").equals("")){
				Calendar cal = Calendar.getInstance();  
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
				Date time = null;
				try {
					time = sdf.parse(map.get("date").toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        cal.setTime(time); 
		        cal.setFirstDayOfWeek(Calendar.MONDAY);
		        cal.add(Calendar.DATE, 6);
		        map.put("dateS", map.get("date").toString());
		        map.put("dateE", sdf.format(cal.getTime()));
			}
		}
		//患者人数和科室
		List<Map> mapNumberPatientList=vtePatientHospitInfoMapper.queryNumberPatient(map);
		//VTE发病人数
		map.put("doctorAdviceResult1", ConstantsDict.DOCTOR_ADVICE_RESULT1);
		map.put("doctorAdviceResult2", ConstantsDict.DOCTOR_ADVICE_RESULT2);
		List<Map> mapOnsetOfNumberPatientList=vtePatientHospitInfoMapper.queryOnsetOfNumberPatient(map);
		//VTE风险评估人数
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		List<Map> mapVteRiskAssessmentPatientList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		//出血风险评估人数
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE3);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE4);
		List<Map> mapBleedingRiskAssessmentPatientList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		//查询所有科室
		map.put("departmentQualitycontrol", ConstantsDict.DEPARTMENT_QUALITYCONTROL_YES);
		
		List<TbVteDepartment> list=vteDepartmentMapper.queryAllVteDepartmentNP(map);
		String department="";
		String numberPatient="";
		String onsetOfNumberPatient="";
		String vteRiskAssessmentPatient="";
		String bleedingRiskAssessmentPatient="";
		Integer numberPatientSum=0;
		Double onsetOfNumberPatientSum=0.00;
		Double vteRiskAssessmentPatientSum=0.00;
		Double bleedingRiskAssessmentPatientSum=0.00;
		for(int i=0;i<list.size();i++){
			department=department+list.get(i).getDepartmentName()+",";
			int countN=0;
			for(int n=0;n<mapNumberPatientList.size();n++){
				if(list.get(i).getDepartmentName().equals(mapNumberPatientList.get(n).get("patientDepartment"))){
					numberPatient=numberPatient+mapNumberPatientList.get(n).get("count")+",";
					numberPatientSum=numberPatientSum+Integer.parseInt(mapNumberPatientList.get(n).get("count").toString());
					countN=countN+1;
					break;
				}
			}
			if(countN==0){
				numberPatient=numberPatient+"0,";
			}
			//VTE发病率
			int countj=0;
			for(int j=0;j<mapOnsetOfNumberPatientList.size();j++){
				if(list.get(i).getDepartmentName().equals(mapOnsetOfNumberPatientList.get(j).get("patientDepartment"))){
					onsetOfNumberPatient=onsetOfNumberPatient+mapOnsetOfNumberPatientList.get(j).get("count").toString()+",";
					onsetOfNumberPatientSum=onsetOfNumberPatientSum+Integer.parseInt(mapOnsetOfNumberPatientList.get(j).get("count").toString());
					countj=countj+1;
					break;
				}
			}
			if(countj==0){
				onsetOfNumberPatient=onsetOfNumberPatient+"0,";
			}
			//VTE风险评估率
			int countk=0;
			for(int k=0;k<mapVteRiskAssessmentPatientList.size();k++){
				if(list.get(i).getDepartmentName().equals(mapVteRiskAssessmentPatientList.get(k).get("patientDepartment"))){
					vteRiskAssessmentPatient=vteRiskAssessmentPatient+mapVteRiskAssessmentPatientList.get(k).get("count").toString()+",";
					vteRiskAssessmentPatientSum=vteRiskAssessmentPatientSum+Integer.parseInt(mapVteRiskAssessmentPatientList.get(k).get("count").toString());
					countk=countk+1;
					break;
				}
			}
			if(countk==0){
				vteRiskAssessmentPatient=vteRiskAssessmentPatient+"0,";
			}
			//出血风险评估估率
			int countm=0;
			for(int m=0;m<mapBleedingRiskAssessmentPatientList.size();m++){
				if(list.get(i).getDepartmentName().equals(mapBleedingRiskAssessmentPatientList.get(m).get("patientDepartment"))){
					bleedingRiskAssessmentPatient=bleedingRiskAssessmentPatient+mapBleedingRiskAssessmentPatientList.get(m).get("count").toString()+",";
					bleedingRiskAssessmentPatientSum=bleedingRiskAssessmentPatientSum+Integer.parseInt(mapBleedingRiskAssessmentPatientList.get(m).get("count").toString());
					countm=countm+1;
					break;
				}
			}
			if(countm==0){
				bleedingRiskAssessmentPatient=bleedingRiskAssessmentPatient+"0,";
			}
		}
		if(onsetOfNumberPatient.length()>0){
			onsetOfNumberPatient=onsetOfNumberPatient.substring(0,onsetOfNumberPatient.length()-1);
		}
		if(vteRiskAssessmentPatient.length()>0){
			vteRiskAssessmentPatient=vteRiskAssessmentPatient.substring(0,vteRiskAssessmentPatient.length()-1);
		}
		if(bleedingRiskAssessmentPatient.length()>0){
			bleedingRiskAssessmentPatient=bleedingRiskAssessmentPatient.substring(0,bleedingRiskAssessmentPatient.length()-1);
		}
		
		String[] numberPatients=numberPatient.split(",");
		String[] onsetOfNumberPatients=onsetOfNumberPatient.split(",");
		String[] vteRiskAssessmentPatients=vteRiskAssessmentPatient.split(",");
		String[] bleedingRiskAssessmentPatients=bleedingRiskAssessmentPatient.split(",");
		onsetOfNumberPatient="";
		vteRiskAssessmentPatient="";
		bleedingRiskAssessmentPatient="";
		Double onsetOfNumberPatientIncidence=0.00;
		Double vteRiskAssessmentPatientIncidence=0.00;
		Double bleedingRiskAssessmentPatientIncidence=0.00;
		DecimalFormat df = new DecimalFormat("0.00");
		for(int i=0;i<numberPatients.length;i++){
			if(!numberPatients[i].equals("0")&&numberPatients[i]!="0"&&!numberPatients[i].equals("")){
				onsetOfNumberPatientIncidence= (double)Integer.parseInt(onsetOfNumberPatients[i])*100/(double)Integer.parseInt(numberPatients[i]);
				vteRiskAssessmentPatientIncidence= (double)Integer.parseInt(vteRiskAssessmentPatients[i])*100/(double)Integer.parseInt(numberPatients[i]);
				bleedingRiskAssessmentPatientIncidence= (double)Integer.parseInt(bleedingRiskAssessmentPatients[i])*100/(double)Integer.parseInt(numberPatients[i]);
				onsetOfNumberPatient=onsetOfNumberPatient+df.format(onsetOfNumberPatientIncidence)+",";
				vteRiskAssessmentPatient=vteRiskAssessmentPatient+df.format(vteRiskAssessmentPatientIncidence)+",";
				bleedingRiskAssessmentPatient=bleedingRiskAssessmentPatient+df.format(bleedingRiskAssessmentPatientIncidence)+",";
			}else{
				onsetOfNumberPatient=onsetOfNumberPatient+"0.00,";
				vteRiskAssessmentPatient=vteRiskAssessmentPatient+"0.00,";
				bleedingRiskAssessmentPatient=bleedingRiskAssessmentPatient+"0.00,";
			}
		}
		PatientQualityViewKpiPojo patientQualityViewKpi=new PatientQualityViewKpiPojo();
		//视图数据
		patientQualityViewKpi.setDepartment(department.substring(0,department.length()-1));
		patientQualityViewKpi.setNumberPatient(numberPatient.substring(0,numberPatient.length()-1));
		patientQualityViewKpi.setOnsetOfNumberPatient(onsetOfNumberPatient.substring(0,onsetOfNumberPatient.length()-1));
		patientQualityViewKpi.setVteRiskAssessmentPatient(vteRiskAssessmentPatient.substring(0,vteRiskAssessmentPatient.length()-1));
		patientQualityViewKpi.setBleedingRiskAssessmentPatient(bleedingRiskAssessmentPatient.substring(0,bleedingRiskAssessmentPatient.length()-1));
		//右侧数据全院
		if(numberPatientSum>0){
			patientQualityViewKpi.setNumberPatientAll(numberPatientSum.toString());
			if(onsetOfNumberPatientSum>0){
				patientQualityViewKpi.setOnsetOfNumberPatientAll(df.format(onsetOfNumberPatientSum*100/(double)numberPatientSum));
			}else{
				patientQualityViewKpi.setOnsetOfNumberPatientAll("0");
			}
			if(vteRiskAssessmentPatientSum>0){
				patientQualityViewKpi.setVteRiskAssessmentPatientAll(df.format(vteRiskAssessmentPatientSum*100/(double)numberPatientSum));
			}else{
				patientQualityViewKpi.setVteRiskAssessmentPatientAll("0");
			}
			if(bleedingRiskAssessmentPatientSum>0){
				patientQualityViewKpi.setBleedingRiskAssessmentPatientAll(df.format(bleedingRiskAssessmentPatientSum*100/(double)numberPatientSum));
			}else{
				patientQualityViewKpi.setBleedingRiskAssessmentPatientAll("0");
			}
		}else{
			patientQualityViewKpi.setNumberPatientAll("0");
			patientQualityViewKpi.setOnsetOfNumberPatientAll("0");
			patientQualityViewKpi.setVteRiskAssessmentPatientAll("0");
			patientQualityViewKpi.setBleedingRiskAssessmentPatientAll("0");
		}
		return patientQualityViewKpi;
	}
	
	/**
	 * 质控视图KPI检测右侧数据
	 */
	@Override
	public PatientQualityViewKpiRightPojo queryPatientQualityViewKpiRight(Map map) {
		if(map.get("dateType").toString().equals("3")){
			if(!map.get("date").equals("")){
				Calendar cal = Calendar.getInstance();  
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
				Date time = null;
				try {
					time = sdf.parse(map.get("date").toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        cal.setTime(time); 
		        cal.setFirstDayOfWeek(Calendar.MONDAY);
		        cal.add(Calendar.DATE, 6);
		        map.put("dateS", map.get("date").toString());
		        map.put("dateE", sdf.format(cal.getTime()));
			}
		}
		//患者人数和科室
		map.put("departmentQualitycontrol", ConstantsDict.DEPARTMENT_QUALITYCONTROL_YES);
		List<Map> mapNumberPatientList=vtePatientHospitInfoMapper.queryNumberPatient(map);
		//VTE发病人数
		map.put("doctorAdviceResult1", ConstantsDict.DOCTOR_ADVICE_RESULT1);
		map.put("doctorAdviceResult2", ConstantsDict.DOCTOR_ADVICE_RESULT2);
		List<Map> mapOnsetOfNumberPatientList=vtePatientHospitInfoMapper.queryOnsetOfNumberPatient(map);
		//VTE风险评估人数
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		List<Map> mapVteRiskAssessmentPatientList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		//出血风险评估人数
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE3);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE4);
		List<Map> mapBleedingRiskAssessmentPatientList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		int numberPatientSum=0;
		int onsetOfNumberPatientSum=0;
		int vteRiskAssessmentPatientSum=0;
		int bleedingRiskAssessmentPatientSum=0;
		PatientQualityViewKpiRightPojo patientQualityViewKpiRight=new PatientQualityViewKpiRightPojo();
		if(mapNumberPatientList!=null&&mapNumberPatientList.size()>0&&Integer.parseInt(mapNumberPatientList.get(0).get("count").toString())!=0){
			numberPatientSum=Integer.parseInt(mapNumberPatientList.get(0).get("count").toString());
			DecimalFormat df = new DecimalFormat("0.00");
			//VTE发病人数
			if(mapOnsetOfNumberPatientList!=null&&mapOnsetOfNumberPatientList.size()>0){
				onsetOfNumberPatientSum=Integer.parseInt(mapOnsetOfNumberPatientList.get(0).get("count").toString());
			}
			//VTE风险评估人数
			if(mapVteRiskAssessmentPatientList!=null&&mapVteRiskAssessmentPatientList.size()>0){
				vteRiskAssessmentPatientSum=Integer.parseInt(mapVteRiskAssessmentPatientList.get(0).get("count").toString());
			}
			//出血风险评估人数
			if(mapBleedingRiskAssessmentPatientList!=null&&mapBleedingRiskAssessmentPatientList.size()>0){
				bleedingRiskAssessmentPatientSum=Integer.parseInt(mapBleedingRiskAssessmentPatientList.get(0).get("count").toString());
			}
			patientQualityViewKpiRight.setNumberPatientSum(numberPatientSum);
			patientQualityViewKpiRight.setOnsetOfNumberPatientSum(df.format((double)onsetOfNumberPatientSum*100/(double)numberPatientSum));
			patientQualityViewKpiRight.setVteRiskAssessmentPatientSum(df.format((double)vteRiskAssessmentPatientSum*100/(double)numberPatientSum));
			patientQualityViewKpiRight.setBleedingRiskAssessmentPatientSum(df.format((double)bleedingRiskAssessmentPatientSum*100/(double)numberPatientSum));
		}else{
			patientQualityViewKpiRight.setNumberPatientSum(0);
			patientQualityViewKpiRight.setOnsetOfNumberPatientSum("0.00");
			patientQualityViewKpiRight.setVteRiskAssessmentPatientSum("0.00");
			patientQualityViewKpiRight.setBleedingRiskAssessmentPatientSum("0.00");
		}
		return patientQualityViewKpiRight;
	}
	
	

	/**
    * 质控视图中高危患者预防-中高危患者数
    */
	@Override
	public PreventionForMiddleHighRiskPatientPojo queryPreventionForMiddleHighRiskPatients(Map map) {
		if(map.get("dateType").toString().equals("3")){
			if(!map.get("date").equals("")){
				Calendar cal = Calendar.getInstance();  
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
				Date time = null;
				try {
					time = sdf.parse(map.get("date").toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        cal.setTime(time); 
		        cal.setFirstDayOfWeek(Calendar.MONDAY);
		        cal.add(Calendar.DATE, 6);
		        map.put("dateS", map.get("date").toString());
		        map.put("dateE", sdf.format(cal.getTime()));
			}
		}
		//Caprini中危人数
		map.put("assessmentType", ConstantsDict.ASSESSMENT_TYPE_VTE1);
		map.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		map.put("assessmentResult", ConstantsDict.ASSESSMENT_RESULT_VTE2);
		List<Map> capriniMiddleRiskList=vtePatientHospitInfoMapper.queryCapriniPaduaRiskList(map);
		//Caprini高危人数
		map.put("assessmentResult", ConstantsDict.ASSESSMENT_RESULT_VTE3);
		List<Map> capriniHighRiskList=vtePatientHospitInfoMapper.queryCapriniPaduaRiskList(map);
		//Padua高危人数
		map.put("assessmentItem", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		map.put("assessmentResult", ConstantsDict.ASSESSMENT_RESULT_VTE3);
		List<Map> paduaHighRiskList=vtePatientHospitInfoMapper.queryCapriniPaduaRiskList(map);
		//查询所有科室
		map.put("departmentQualitycontrol", ConstantsDict.DEPARTMENT_QUALITYCONTROL_YES);
		
		List<TbVteDepartment> list=vteDepartmentMapper.queryAllVteDepartmentNP(map);
		String department="";
		String capriniMiddleRisk="";
		String capriniHighRisk="";
		String paduaHighRisk="";
		for(int i=0;i<list.size();i++){
			department=department+list.get(i).getDepartmentName()+",";
			int countj=0;
			for(int j=0;j<capriniMiddleRiskList.size();j++){
				if(list.get(i).getDepartmentName().equals(capriniMiddleRiskList.get(j).get("patientDepartment"))){
					capriniMiddleRisk=capriniMiddleRisk+capriniMiddleRiskList.get(j).get("count")+",";
					countj=countj+1;
					break;
				}
			}
			if(countj==0){
				capriniMiddleRisk=capriniMiddleRisk+"0,";
			}
			int countk=0;
			for(int k=0;k<capriniHighRiskList.size();k++){
				if(list.get(i).getDepartmentName().equals(capriniHighRiskList.get(k).get("patientDepartment"))){
					capriniHighRisk=capriniHighRisk+capriniHighRiskList.get(k).get("count")+",";
					countk=countk+1;
					break;
				}
			}
			if(countk==0){
				capriniHighRisk=capriniHighRisk+"0,";
			}
			int countm=0;
			for(int m=0;m<paduaHighRiskList.size();m++){
				if(list.get(i).getDepartmentName().equals(paduaHighRiskList.get(m).get("patientDepartment"))){
					paduaHighRisk=paduaHighRisk+paduaHighRiskList.get(m).get("count")+",";
					countm=countm+1;
					break;
				}
			}
			if(countm==0){
				paduaHighRisk=paduaHighRisk+"0,";
			}
		}
		if(department.length()>0){
			department=department.substring(0,department.length()-1);
		}
		if(capriniMiddleRisk.length()>0){
			capriniMiddleRisk=capriniMiddleRisk.substring(0,capriniMiddleRisk.length()-1);
		}
		if(capriniHighRisk.length()>0){
			capriniHighRisk=capriniHighRisk.substring(0,capriniHighRisk.length()-1);
		}
		if(paduaHighRisk.length()>0){
			paduaHighRisk=paduaHighRisk.substring(0,paduaHighRisk.length()-1);
		}
		PreventionForMiddleHighRiskPatientPojo preventionPatient=new PreventionForMiddleHighRiskPatientPojo();
		preventionPatient.setDepartment(department);
		preventionPatient.setCapriniMiddleRisk(capriniMiddleRisk);
		preventionPatient.setCapriniHighRisk(capriniHighRisk);
		preventionPatient.setPaduaHighRisk(paduaHighRisk);
		return preventionPatient;
	}

	/**
    * 质控视图中高危患者预防-中高危患者预防措施率
    */
	@Override
	public PreventiveRatePatientPojo queryPreventiveRatePatients(Map map) {
		if(map.get("dateType").toString().equals("3")){
			if(!map.get("date").equals("")){
				Calendar cal = Calendar.getInstance();  
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
				Date time = null;
				try {
					time = sdf.parse(map.get("date").toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        cal.setTime(time); 
		        cal.setFirstDayOfWeek(Calendar.MONDAY);
		        cal.add(Calendar.DATE, 6);
		        map.put("dateS", map.get("date").toString());
		        map.put("dateE", sdf.format(cal.getTime()));
			}
		}
		//最近一次VTE风险评估为Caprini评分且结果为中危或高危的病案号数+最近一次VTE风险评估为Padua评分且结果为高危的病案号数
		map.put("assessmentType", ConstantsDict.ASSESSMENT_TYPE_VTE1);
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		map.put("assessmentResult1", ConstantsDict.ASSESSMENT_RESULT_VTE2);
		map.put("assessmentResult2", ConstantsDict.ASSESSMENT_RESULT_VTE3);
		List<Map> middleHighRiskList=vtePatientHospitInfoMapper.queryCapriniPaduaRiskSumList(map);
		//各部门药物预防人数
		map.put("doctorAdviceRisk", ConstantsDict.DOCTOR_ADVICE_RISK2);
		List<Map> medicinePreventiveNumber=vtePatientHospitInfoMapper.queryPreventiveNumberList(map);
		//各部门机械预防人数
		map.put("doctorAdviceRisk", ConstantsDict.DOCTOR_ADVICE_RISK3);
		List<Map> mechanicalPreventiveNumber=vtePatientHospitInfoMapper.queryPreventiveNumberList(map);
		//查询所有科室
		map.put("departmentQualitycontrol", ConstantsDict.DEPARTMENT_QUALITYCONTROL_YES);
		
		List<TbVteDepartment> listD=vteDepartmentMapper.queryAllVteDepartmentNP(map);
		String department="";
		String middleHighRisk="";
		String medicinePreventiveRate="";
		String mechanicalPreventiveRate="";
		for(int i=0;i<listD.size();i++){
			department=department+listD.get(i).getDepartmentName()+",";
			int countj=0;
			for(int j=0;j<middleHighRiskList.size();j++){
				if(listD.get(i).getDepartmentName().equals(middleHighRiskList.get(j).get("patientDepartment"))){
					middleHighRisk=middleHighRisk+middleHighRiskList.get(j).get("count")+",";
					countj=countj+1;
					break;
				}
			}
			if(countj==0){
				middleHighRisk=middleHighRisk+"0,";
			}
		}
		String[] middleHighRiskSum=middleHighRisk.split(",");
		DecimalFormat df = new DecimalFormat("0.00");
		for(int i=0;i<listD.size();i++){
			int countj=0;
			for(int j=0;j<medicinePreventiveNumber.size();j++){
				if(listD.get(i).getDepartmentName().equals(medicinePreventiveNumber.get(j).get("patientDepartment"))&&Integer.parseInt(middleHighRiskSum[i])!=0){
					Double rate= (double)Integer.parseInt(medicinePreventiveNumber.get(j).get("count").toString())*100/(double)Integer.parseInt(middleHighRiskSum[i]);
					medicinePreventiveRate=medicinePreventiveRate+df.format(rate)+",";
					countj=countj+1;
					break;
				}
			}
			if(countj==0){
				medicinePreventiveRate=medicinePreventiveRate+"0,";
			}
			int countk=0;
			for(int k=0;k<mechanicalPreventiveNumber.size();k++){
				if(listD.get(i).getDepartmentName().equals(mechanicalPreventiveNumber.get(k).get("patientDepartment"))&&Integer.parseInt(middleHighRiskSum[i])!=0){
					Double rate= (double)Integer.parseInt(mechanicalPreventiveNumber.get(k).get("count").toString())*100/(double)Integer.parseInt(middleHighRiskSum[i]);
					mechanicalPreventiveRate=mechanicalPreventiveRate+df.format(rate)+",";
					countk=countk+1;
					break;
				}
			}
			if(countk==0){
				mechanicalPreventiveRate=mechanicalPreventiveRate+"0,";
			}
		}
		if(department.length()>0){
			department=department.substring(0,department.length()-1);
		}
		if(medicinePreventiveRate.length()>0){
			medicinePreventiveRate=medicinePreventiveRate.substring(0,medicinePreventiveRate.length()-1);
		}
		if(mechanicalPreventiveRate.length()>0){
			mechanicalPreventiveRate=mechanicalPreventiveRate.substring(0,mechanicalPreventiveRate.length()-1);
		}
		PreventiveRatePatientPojo preventiveRatePatient=new PreventiveRatePatientPojo();
		preventiveRatePatient.setDepartment(department);
		preventiveRatePatient.setMedicinePreventiveRate(medicinePreventiveRate);
		preventiveRatePatient.setMechanicalPreventiveRate(mechanicalPreventiveRate);
		return preventiveRatePatient;
	}

	/**
    * 质控视图VTE风险评估质量
    */
	@Override
	public QualityRiskAssessmentPojo queryVteQualityRiskAssessment(Map map) {
		if(map.get("dateType").toString().equals("3")){
			if(!map.get("date").equals("")){
				Calendar cal = Calendar.getInstance();  
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
				Date time = null;
				try {
					time = sdf.parse(map.get("date").toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        cal.setTime(time); 
		        cal.setFirstDayOfWeek(Calendar.MONDAY);
		        cal.add(Calendar.DATE, 6);
		        map.put("dateS", map.get("date").toString());
		        map.put("dateE", sdf.format(cal.getTime()));
			}
		}
		//完成VTE风险评估病案号数
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE1);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE2);
		List<Map> mapVteRiskAssessmentPatientList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		//入院24小时内完成VTE风险评估病案号数
		map.put("createDtOneDay", "1");
		List<Map> mapOneDayVteRiskAssessmentPatientList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		//最近一次VTE风险评估为Caprini评分且结果为中危或高危的病案号数+最近一次VTE风险评估为Padua评分且结果为高危的病案号数
		map.put("assessmentType", ConstantsDict.ASSESSMENT_TYPE_VTE1);
		map.put("assessmentResult1", ConstantsDict.ASSESSMENT_RESULT_VTE2);
		map.put("assessmentResult2", ConstantsDict.ASSESSMENT_RESULT_VTE3);
		List<Map> middleHighRiskList=vtePatientHospitInfoMapper.queryCapriniPaduaRiskSumList(map);
		
		//查询所有科室
		map.put("departmentQualitycontrol", ConstantsDict.DEPARTMENT_QUALITYCONTROL_YES);
		
		List<TbVteDepartment> listD=vteDepartmentMapper.queryAllVteDepartmentNP(map);
		String department="";
		String vteRiskAssessmentPatientSum="";
		String oneDayVteRiskAssessmentRate="";
		String middleHighRiskRate="";
		for(int i=0;i<listD.size();i++){
			for(int j=0;j<mapVteRiskAssessmentPatientList.size();j++){
				if(listD.get(i).getDepartmentName().equals(mapVteRiskAssessmentPatientList.get(j).get("patientDepartment"))){
					vteRiskAssessmentPatientSum=vteRiskAssessmentPatientSum+mapVteRiskAssessmentPatientList.get(j).get("count")+",";
					department=department+listD.get(i).getDepartmentName()+",";
					break;
				}
			}
		}
		String[] departmentS=department.split(",");
		String[] vteRiskAssessmentPatientSums=vteRiskAssessmentPatientSum.split(",");
		DecimalFormat df = new DecimalFormat("0.00");
		for(int i=0;i<departmentS.length;i++){
			int countJ=0;
			for(int j=0;j<mapOneDayVteRiskAssessmentPatientList.size();j++){
				if(departmentS[i].equals(mapOneDayVteRiskAssessmentPatientList.get(j).get("patientDepartment"))&&Integer.parseInt(vteRiskAssessmentPatientSums[i])!=0){
					Double rate= (double)Integer.parseInt(mapOneDayVteRiskAssessmentPatientList.get(j).get("count").toString())*100/(double)Integer.parseInt(vteRiskAssessmentPatientSums[i]);
					oneDayVteRiskAssessmentRate=oneDayVteRiskAssessmentRate+df.format(rate)+",";
					countJ=countJ+1;
					break;
				}
			}
			if(countJ==0){
				oneDayVteRiskAssessmentRate=oneDayVteRiskAssessmentRate+"0,";
			}
			int countK=0;
			for(int k=0;k<middleHighRiskList.size();k++){
				if(departmentS[i].equals(middleHighRiskList.get(k).get("patientDepartment"))&&Integer.parseInt(vteRiskAssessmentPatientSums[i])!=0){
					Double rate= (double)Integer.parseInt(middleHighRiskList.get(k).get("count").toString())*100/(double)Integer.parseInt(vteRiskAssessmentPatientSums[i]);
					middleHighRiskRate=middleHighRiskRate+df.format(rate)+",";
					countK=countK+1;
					break;
				}
			}
			if(countK==0){
				middleHighRiskRate=middleHighRiskRate+"0,";
			}
		}
		if(department.length()>0){
			department=department.substring(0,department.length()-1);
		}
		if(vteRiskAssessmentPatientSum.length()>0){
			vteRiskAssessmentPatientSum=vteRiskAssessmentPatientSum.substring(0,vteRiskAssessmentPatientSum.length()-1);
		}
		if(oneDayVteRiskAssessmentRate.length()>0){
			oneDayVteRiskAssessmentRate=oneDayVteRiskAssessmentRate.substring(0,oneDayVteRiskAssessmentRate.length()-1);
		}
		if(middleHighRiskRate.length()>0){
			middleHighRiskRate=middleHighRiskRate.substring(0,middleHighRiskRate.length()-1);
		}
		QualityRiskAssessmentPojo qualityRiskAssessment=new QualityRiskAssessmentPojo();
		qualityRiskAssessment.setDepartment(department);
		qualityRiskAssessment.setVteRiskAssessmentPatientSum(vteRiskAssessmentPatientSum);
		qualityRiskAssessment.setOneDayVteRiskAssessmentRate(oneDayVteRiskAssessmentRate);
		qualityRiskAssessment.setMiddleHighRiskRate(middleHighRiskRate);
		return qualityRiskAssessment;
	}
	/**
    * 质控视图出血风险评估质量
    */
	@Override
	public BleedingQualityRiskAssessmentPojo queryBleedingQualityRiskAssessment(Map map) {
		if(map.get("dateType").toString().equals("3")){
			if(!map.get("date").equals("")){
				Calendar cal = Calendar.getInstance();  
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
				Date time = null;
				try {
					time = sdf.parse(map.get("date").toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        cal.setTime(time); 
		        cal.setFirstDayOfWeek(Calendar.MONDAY);
		        cal.add(Calendar.DATE, 6);
		        map.put("dateS", map.get("date").toString());
		        map.put("dateE", sdf.format(cal.getTime()));
			}
		}
		//出血风险评估人数
		map.put("assessmentItem1", ConstantsDict.ASSESSMENT_ITEM_VTE3);
		map.put("assessmentItem2", ConstantsDict.ASSESSMENT_ITEM_VTE4);
		List<Map> mapBleedingList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		//入院24内完成出血风险评估人数
		map.put("createDtOneDay", "1");
		List<Map> mapOneDayBleedingList=vtePatientHospitInfoMapper.queryVteRiskAssessmentPatient(map);
		//最近一次出血风险评估为 外科出血风险评估且结果为 有 的病案号数+最近一次出血风险评估为 内科出血风险评估且结果为 有 的病案号数
		map.put("assessmentType", ConstantsDict.ASSESSMENT_TYPE_VTE2);///////////////////////////
		map.put("assessmentResult", ConstantsDict.ASSESSMENT_RESULT_VTE4);///////////////////////////
		List<Map> mapRecentlyBleedingList=vtePatientHospitInfoMapper.queryRecentlyBleedingRiskSumList(map);
		//查询所有科室
		map.put("departmentQualitycontrol", ConstantsDict.DEPARTMENT_QUALITYCONTROL_YES);
		
		List<TbVteDepartment> listD=vteDepartmentMapper.queryAllVteDepartmentNP(map);
		String department="";
		String bleedingSum="";
		String oneDayBleedingRate="";
		String recentlyBleedingRate="";
		for(int i=0;i<listD.size();i++){
			for(int j=0;j<mapBleedingList.size();j++){
				if(listD.get(i).getDepartmentName().equals(mapBleedingList.get(j).get("patientDepartment"))){
					bleedingSum=bleedingSum+mapBleedingList.get(j).get("count")+",";
					department=department+listD.get(i).getDepartmentName()+",";
					break;
				}
			}
		}
		String[] bleedingSums=bleedingSum.split(",");
		String[] departments=department.split(",");
		DecimalFormat df = new DecimalFormat("0.00");
		for(int i=0;i<departments.length;i++){
			int countJ=0;
			for(int j=0;j<mapOneDayBleedingList.size();j++){
				if(departments[i].equals(mapOneDayBleedingList.get(j).get("patientDepartment"))&&Integer.parseInt(bleedingSums[i])!=0){
					Double rate= (double)Integer.parseInt(mapOneDayBleedingList.get(j).get("count").toString())*100/(double)Integer.parseInt(bleedingSums[i]);
					oneDayBleedingRate=oneDayBleedingRate+df.format(rate)+",";
					countJ=countJ+1;
					break;
				}
			}
			if(countJ==0){
				oneDayBleedingRate=oneDayBleedingRate+"0,";
			}
			int countK=0;
			for(int k=0;k<mapRecentlyBleedingList.size();k++){
				if(departments[i].equals(mapRecentlyBleedingList.get(k).get("patientDepartment"))&&Integer.parseInt(bleedingSums[i])!=0){
					Double rate= (double)Integer.parseInt(mapRecentlyBleedingList.get(k).get("count").toString())*100/(double)Integer.parseInt(bleedingSums[i]);
					recentlyBleedingRate=recentlyBleedingRate+df.format(rate)+",";
					countK=countK+1;
					break;
				}
			}
			if(countK==0){
				recentlyBleedingRate=recentlyBleedingRate+"0,";
			}
		}
		if(department.length()>0){
			department=department.substring(0,department.length()-1);
		}
		if(bleedingSum.length()>0){
			bleedingSum=bleedingSum.substring(0,bleedingSum.length()-1);
		}
		if(oneDayBleedingRate.length()>0){
			oneDayBleedingRate=oneDayBleedingRate.substring(0,oneDayBleedingRate.length()-1);
		}
		if(recentlyBleedingRate.length()>0){
			recentlyBleedingRate=recentlyBleedingRate.substring(0,recentlyBleedingRate.length()-1);
		}
		BleedingQualityRiskAssessmentPojo bleedingQualityRiskAssessment=new BleedingQualityRiskAssessmentPojo();
		bleedingQualityRiskAssessment.setDepartment(department);
		bleedingQualityRiskAssessment.setBleedingSum(bleedingSum);
		bleedingQualityRiskAssessment.setOneDayBleedingRate(oneDayBleedingRate);
		bleedingQualityRiskAssessment.setRecentlyBleedingRate(recentlyBleedingRate);
		return bleedingQualityRiskAssessment;
	}

	/**
    * 质控视图患病情况
    */
	@Override
	public PrevalenceAssessmentPojo queryPrevalenceAssessment(Map map) {
		if(map.get("dateType").toString().equals("3")){
			if(!map.get("date").equals("")){
				Calendar cal = Calendar.getInstance();  
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
				Date time = null;
				try {
					time = sdf.parse(map.get("date").toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        cal.setTime(time); 
		        cal.setFirstDayOfWeek(Calendar.MONDAY);
		        cal.add(Calendar.DATE, 6);
		        map.put("dateS", map.get("date").toString());
		        map.put("dateE", sdf.format(cal.getTime()));
			}
		}
		// trick d4p5
		if(map.get("resultType").equals("DVT")){
			map.put("doctorAdviceResult", Integer.parseInt(ConstantsDict.DOCTOR_ADVICE_RESULT2) + 2 + "");
		}
		if(map.get("resultType").equals("PTE")){
			map.put("doctorAdviceResult", Integer.parseInt(ConstantsDict.DOCTOR_ADVICE_RESULT1) + 4 + "");
		}
		if(map.get("resultType").equals("VTE")){
			map.put("doctorAdviceResult1", Integer.parseInt(ConstantsDict.DOCTOR_ADVICE_RESULT2) + 2 + "");
			map.put("doctorAdviceResult2", Integer.parseInt(ConstantsDict.DOCTOR_ADVICE_RESULT1) + 4 + "");
		}
		//查询所有科室
		map.put("departmentQualitycontrol", ConstantsDict.DEPARTMENT_QUALITYCONTROL_YES);
		List<Map> listD=vteDepartmentMapper.queryAllVteDepartmentOrg(map);
		List<Map> list=vtePatientHospitInfoMapper.queryPrevalenceAssessment(map);
		String patientDepartment="";
		String count="";
		String patientDepartmentN="";
		String countN="";
		String patientDepartmentW="";
		String countW="";
		String patientDepartmentNw1="内科";
		int countNw1=0;
		String patientDepartmentNw2="外科";
		int countNw2=0;
		for(int i=0;i<listD.size();i++){
			for(int j=0;j<list.size();j++){
				if(listD.get(i).get("departmentName").equals(list.get(j).get("patientDepartment"))){
					patientDepartment=patientDepartment+listD.get(i).get("departmentName")+",";
					count=count+list.get(j).get("count")+",";
					break;
				}
			}
			if(listD.get(i).get("orgType")!=null&&listD.get(i).get("orgType").equals(ConstantsDict.DEPARTMENT_N)){
				for(int j=0;j<list.size();j++){
					if(listD.get(i).get("departmentName").equals(list.get(j).get("patientDepartment"))){
						patientDepartmentN=patientDepartmentN+listD.get(i).get("departmentName")+",";
						countN=countN+list.get(j).get("count")+",";
						countNw1=countNw1+Integer.parseInt(list.get(j).get("count").toString());
						break;
					}
				}
			}
			if(listD.get(i).get("orgType")!=null&&listD.get(i).get("orgType").equals(ConstantsDict.DEPARTMENT_W)){
				for(int j=0;j<list.size();j++){
					if(listD.get(i).get("departmentName").equals(list.get(j).get("patientDepartment"))){
						patientDepartmentW=patientDepartmentW+listD.get(i).get("departmentName")+",";
						countW=countW+list.get(j).get("count")+",";
						countNw2=countNw2+Integer.parseInt(list.get(j).get("count").toString());
						break;
					}
				}
			}
		}
		if(patientDepartment.length()>0){
			patientDepartment=patientDepartment.substring(0,patientDepartment.length()-1);
			count=count.substring(0,count.length()-1);
		}
		if(patientDepartmentN.length()>0){
			patientDepartmentN=patientDepartmentN.substring(0,patientDepartmentN.length()-1);
			countN=countN.substring(0,countN.length()-1);
		}
		if(patientDepartmentW.length()>0){
			patientDepartmentW=patientDepartmentW.substring(0,patientDepartmentW.length()-1);
			countW=countW.substring(0,countW.length()-1);
		}
		PrevalenceAssessmentPojo prevalenceAssessment=new PrevalenceAssessmentPojo();
		prevalenceAssessment.setDepartment(patientDepartment);
		prevalenceAssessment.setCount(count);
		prevalenceAssessment.setDepartmentN(patientDepartmentN);
		prevalenceAssessment.setCountN(countN);
		prevalenceAssessment.setDepartmentW(patientDepartmentW);
		prevalenceAssessment.setCountW(countW);
		String patientDepartmentNw=null;
		String countNw=null;
		if(countNw1>0){
			countNw=countNw1+"";
			patientDepartmentNw=patientDepartmentNw1;
		}
		if(countNw1>0&&countNw2>0){
			countNw=countNw+",";
			patientDepartmentNw=patientDepartmentNw+",";
		}
		if(countNw2>0){
			countNw=countNw+countNw2;
			patientDepartmentNw=patientDepartmentNw+patientDepartmentNw2;
		}
		prevalenceAssessment.setDepartmentNw(patientDepartmentNw);
		prevalenceAssessment.setCountNw(countNw);
		
		return prevalenceAssessment;
	}
}
