
package com.insight.wisehealth.vte.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insight.axiswevservice.pojo.AllForExport;
import com.insight.core.config.ExportConfig;
import com.insight.core.util.CSVUtils;
import com.insight.core.util.DateUtil;
import com.insight.core.util.JsonUtil;
import com.insight.core.util.R;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.common.SessionUser;
import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;
import com.insight.wisehealth.vte.persistencepojo.TbVteDoctorAdvicePojo;
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
 * 描述:住院信息Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class VtePatientHospitInfoController {
	@Autowired
	private VtePatientHospitInfoService vtePatientHospitInfoService;

	@RequestMapping("/vtePatientHospitInfo/queryList")
	public List queryList(@RequestParam("offset") Integer start, @RequestParam("limit") Integer limit,
			@RequestParam(value = "jsonString", required = false) String jsonString) {
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		Map map = JsonUtil.getMapFromJson(jsonString);
		map.put("start", start);
		map.put("limit", limit);
		List list = new ArrayList();
		try {
			list = vtePatientHospitInfoService.queryVtePatientHospitInfoList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping("/vtePatientHospitInfo/queryCount")
	public R queryCount(@RequestParam(value = "jsonString", required = false) String jsonString) {
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		Map map = JsonUtil.getMapFromJson(jsonString);
		int count = 0;
		try {
			count = vtePatientHospitInfoService.countVtePatientHospitInfoList(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return R.ok().put("count", count);
	}

	/**
	 * @param jsonString
	 * @return
	 */
	@RequestMapping("/vtePatientHospitInfo/saveVtePatientHospitInfoInfo")
	public R saveVtePatientHospitInfoInfo(@RequestParam(value = "jsonString", required = false) String jsonString) {
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		Map map = JsonUtil.getMapFromJson(jsonString);
		TbVtePatientHospitInfo saveVtePatientHospitInfo;
		try {
			saveVtePatientHospitInfo = vtePatientHospitInfoService.saveVtePatientHospitInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return R.ok();
	}

	/**
	 * @param jsonString
	 * @return
	 */
	@RequestMapping("/vtePatientHospitInfo/delVtePatientHospitInfoInfo")
	public R delVtePatientHospitInfoInfo(@RequestParam(value = "jsonString", required = false) String jsonString) {
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		Map map = JsonUtil.getMapFromJson(jsonString);

		try {
			vtePatientHospitInfoService.delVtePatientHospitInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return R.ok();
	}

	/**
	 * @param jsonString
	 * @return
	 */
	@RequestMapping("/vtePatientHospitInfo/queryVtePatientHospitInfoInfo")
	public TbVtePatientHospitInfo queryVtePatientHospitInfoInfo(
			@RequestParam(value = "jsonString", required = false) String jsonString) {
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		Map map = JsonUtil.getMapFromJson(jsonString);
		TbVtePatientHospitInfo queryVtePatientHospitInfoInfo = null;
		try {
			queryVtePatientHospitInfoInfo = vtePatientHospitInfoService.queryVtePatientHospitInfoInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return queryVtePatientHospitInfoInfo;
	}

	/**
	 * zzy 导出所有质控
	 */
	@RequestMapping("/vtePatientHospitInfo/exportAll")
	public R exportAll(@RequestParam(value = "jsonString", required = false) String jsonString, HttpServletRequest request) {
		String excelStr = ".xlsx";
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		String strForDP = jsonString.substring(0, jsonString.length() - 1) + "," + "\"resultType\"" + ":" + "\"VTE\"}";
		String strForD = jsonString.substring(0, jsonString.length() - 1) + "," + "\"resultType\"" + ":" + "\"DVT\"}";
		String strForP = jsonString.substring(0, jsonString.length() - 1) + "," + "\"resultType\"" + ":" + "\"PTE\"}";
		Map resultMap = new HashMap();
		Map map = JsonUtil.getMapFromJson(jsonString);
		Map mapForDP = JsonUtil.getMapFromJson(strForDP);
		Map mapForD = JsonUtil.getMapFromJson(strForD);
		Map mapForP = JsonUtil.getMapFromJson(strForP);
		PatientQualityViewKpiPojo patientQualityViewKpi = null;
		PreventionForMiddleHighRiskPatientPojo preventionPatient = null;
		PreventiveRatePatientPojo preventiveRatePatient = null;
		QualityRiskAssessmentPojo qualityRiskAssessment = null;
		BleedingQualityRiskAssessmentPojo bleedingQualityRiskAssessment = null;
		PrevalenceAssessmentPojo prevalenceAssessmentDP = null;
		PrevalenceAssessmentPojo prevalenceAssessmentD = null;
		PrevalenceAssessmentPojo prevalenceAssessmentP = null;

		patientQualityViewKpi = vtePatientHospitInfoService.queryPatientQualityViewKpi(map);
		preventionPatient = vtePatientHospitInfoService.queryPreventionForMiddleHighRiskPatients(map);
		preventiveRatePatient = vtePatientHospitInfoService.queryPreventiveRatePatients(map);
		qualityRiskAssessment = vtePatientHospitInfoService.queryVteQualityRiskAssessment(map);
		bleedingQualityRiskAssessment = vtePatientHospitInfoService.queryBleedingQualityRiskAssessment(map);
		
		
		prevalenceAssessmentDP = vtePatientHospitInfoService
				.queryPrevalenceAssessment(mapForDP);
		prevalenceAssessmentD = vtePatientHospitInfoService
				.queryPrevalenceAssessment(mapForD);
		prevalenceAssessmentP = vtePatientHospitInfoService
				.queryPrevalenceAssessment(mapForP);
		String templateFilePath = ExportConfig.templateFilePath + ExportConfig.templateFileInnerPath;
		List headList = new ArrayList();
		headList.add("科室");
		headList.add("患者人数");
		headList.add("VTE发病率");
		headList.add("VTE风险评估率");
		headList.add("完成VTE风险评估患者人数");
		headList.add("出血风险评估率");
		headList.add("完成出血风险评估患者人数");
		headList.add("Caprini中危人数");
		headList.add("Caprini高危人数");
		headList.add("Padua 高危人数");
//		headList.add("药物预防措施人数");
		headList.add("药物预防率");
//		headList.add("机械预防措施人数");
		headList.add("机械预防率");
		headList.add("VTE风险评估中高危率");
		headList.add("VTE风险评估及时率");
		headList.add("出血风险评估高危率");
		headList.add("出血风险评估及时率");
		headList.add("确诊DVT+PTE患者人数");
		headList.add("确诊DVT患者人数");
		headList.add("确诊PTE患者人数");
		String[] cols = { "department", "numberPatient", "onsetOfNumberPatient", "vteRiskAssessmentPatient",
				"vteRiskAssessmentPatientSum", "bleedingRiskAssessmentPatient", "bleedingSum", "capriniMiddleRisk",
				"capriniHighRisk", "paduaHighRisk", "medicinePreventiveRate", "mechanicalPreventiveRate",
				"middleHighRiskRate", "oneDayVteRiskAssessmentRate", "recentlyBleedingRate", "oneDayBleedingRate",
				"countDP", "countD", "countP" };
		List<AllForExport> allForExportList = generateExportDataList(patientQualityViewKpi, preventionPatient,
				preventiveRatePatient, qualityRiskAssessment, bleedingQualityRiskAssessment, prevalenceAssessmentDP,
				prevalenceAssessmentD, prevalenceAssessmentP);
		File filePath = outputExcelFile(allForExportList, excelStr, templateFilePath, headList, cols);
		StringBuffer url = request.getRequestURL(); 
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString(); 
		String canonicalPath = filePath.getAbsolutePath();
		String oldStr = ExportConfig.templateFilePath;
		resultMap.put("filePath", tempContextUrl + canonicalPath.substring(oldStr.length()-1, canonicalPath.length()));
		System.out.println(tempContextUrl + canonicalPath.substring(oldStr.length()-1, canonicalPath.length()));
		return R.ok(resultMap);

	}

	/**
	 * 把字符串变成List
	 * 
	 * @author zzy
	 */
	public List<String> stringToList(String listStr) {
		List<String> result = new ArrayList<>();
		Collections.addAll(result, listStr.split(","));
		return result;
	}

	/**
	 * 合体
	 * 
	 * @author zzy
	 */
	public List<AllForExport> generateExportDataList(PatientQualityViewKpiPojo patientQualityViewKpi,
			PreventionForMiddleHighRiskPatientPojo preventionPatient, PreventiveRatePatientPojo preventiveRatePatient,
			QualityRiskAssessmentPojo qualityRiskAssessment,
			BleedingQualityRiskAssessmentPojo bleedingQualityRiskAssessment,
			PrevalenceAssessmentPojo prevalenceAssessmentDP, PrevalenceAssessmentPojo prevalenceAssessmentD,
			PrevalenceAssessmentPojo prevalenceAssessmentP) {

		List<AllForExport> allForExportList = new ArrayList<>();
		AllForExport allForExport = new AllForExport();

		/**
		 * 把每一个对象都变成若干个list
		 */
		// patientQualityViewKpi
		String departmentStr = patientQualityViewKpi.getDepartment();
		List<String> departmentList = stringToList(departmentStr);
		String numberPatientStr = patientQualityViewKpi.getNumberPatient();
		List<String> numberPatientList = stringToList(numberPatientStr);
		String onsetOfNumberPatientStr = patientQualityViewKpi.getOnsetOfNumberPatient();
		List<String> onsetOfNumberPatientList = stringToList(onsetOfNumberPatientStr);
		String vteRiskAssessmentPatientStr = patientQualityViewKpi.getVteRiskAssessmentPatient();
		List<String> vteRiskAssessmentPatientList = stringToList(vteRiskAssessmentPatientStr);
		String bleedingRiskAssessmentPatientStr = patientQualityViewKpi.getBleedingRiskAssessmentPatient();
		List<String> bleedingRiskAssessmentPatientList = stringToList(bleedingRiskAssessmentPatientStr);

		// preventionPatient
		String capriniMiddleRiskStr = preventionPatient.getCapriniMiddleRisk();
		List<String> capriniMiddleRiskList = stringToList(capriniMiddleRiskStr);
		String capriniHighRiskStr = preventionPatient.getCapriniHighRisk();
		List<String> capriniHighRiskList = stringToList(capriniHighRiskStr);
		String paduaHighRiskStr = preventionPatient.getPaduaHighRisk();
		List<String> paduaHighRiskList = stringToList(paduaHighRiskStr);

		// preventiveRatePatient
		String medicinePreventiveRateStr = preventiveRatePatient.getMedicinePreventiveRate();
		List<String> medicinePreventiveRateList = stringToList(medicinePreventiveRateStr);
		String mechanicalPreventiveRateStr = preventiveRatePatient.getMechanicalPreventiveRate();
		List<String> mechanicalPreventiveRateList = stringToList(mechanicalPreventiveRateStr);

		for (int i = 0; i < departmentList.size(); i++) {
			allForExport.setId(i + "");
			allForExport.setDepartment(departmentList.get(i));
			allForExport.setNumberPatient(numberPatientList.get(i));
			allForExport.setOnsetOfNumberPatient(onsetOfNumberPatientList.get(i));
			allForExport.setVteRiskAssessmentPatient(vteRiskAssessmentPatientList.get(i));
			allForExport.setBleedingRiskAssessmentPatient(bleedingRiskAssessmentPatientList.get(i));
			allForExport.setCapriniMiddleRisk(capriniMiddleRiskList.get(i));
			allForExport.setCapriniHighRisk(capriniHighRiskList.get(i));
			allForExport.setPaduaHighRisk(paduaHighRiskList.get(i));
			allForExport.setMedicinePreventiveRate(medicinePreventiveRateList.get(i));
			allForExport.setMechanicalPreventiveRate(mechanicalPreventiveRateList.get(i));

			allForExportList.add(allForExport);
			allForExport = new AllForExport();
		}

		// prevalenceAssessmentDP prevalenceAssessmentD prevalenceAssessmentP
		departmentStr = prevalenceAssessmentDP.getDepartment();
		departmentList = stringToList(departmentStr);
		String countStr = prevalenceAssessmentDP.getCount();
		List<String> countList = stringToList(countStr);
		for (int i = 0; i < departmentList.size(); i++) {
			departmentStr = departmentList.get(i);
			for (int j = 0; j < allForExportList.size(); j++) {
				if (allForExportList.get(j).getDepartment().equals(departmentStr)) {
					allForExportList.get(j).setCountDP(countList.get(i));
				}
			}
		}
		
		departmentStr = prevalenceAssessmentD.getDepartment();
		departmentList = stringToList(departmentStr);
		countStr = prevalenceAssessmentD.getCount();
		countList = stringToList(countStr);
		for (int i = 0; i < departmentList.size(); i++) {
			departmentStr = departmentList.get(i);
			for (int j = 0; j < allForExportList.size(); j++) {
				if (allForExportList.get(j).getDepartment().equals(departmentStr)) {
					allForExportList.get(j).setCountD(countList.get(i));
				}
			}
		}
		
		departmentStr = prevalenceAssessmentP.getDepartment();
		departmentList = stringToList(departmentStr);
		countStr = prevalenceAssessmentP.getCount();
		countList = stringToList(countStr);
		for (int i = 0; i < departmentList.size(); i++) {
			departmentStr = departmentList.get(i);
			for (int j = 0; j < allForExportList.size(); j++) {
				if (allForExportList.get(j).getDepartment().equals(departmentStr)) {
					allForExportList.get(j).setCountP(countList.get(i));
				}
			}
		}
		
		

		// qualityRiskAssessment
		// 完成VTE评估患者人数
		String vteRiskAssessmentPatientSumStr = qualityRiskAssessment.getVteRiskAssessmentPatientSum();
		List<String> vteRiskAssessmentPatientSumList = stringToList(vteRiskAssessmentPatientSumStr);
		// VTE风险评估及时率
		String oneDayVteRiskAssessmentRateStr = qualityRiskAssessment.getOneDayVteRiskAssessmentRate();
		List<String> oneDayVteRiskAssessmentRateList = stringToList(oneDayVteRiskAssessmentRateStr);
		// VTE风险评估中高危率
		String middleHighRiskRateStr = qualityRiskAssessment.getMiddleHighRiskRate();
		List<String> middleHighRiskRateList = stringToList(middleHighRiskRateStr);

		departmentStr = qualityRiskAssessment.getDepartment();
		departmentList = stringToList(departmentStr);

		for (int i = 0; i < departmentList.size(); i++) {
			departmentStr = departmentList.get(i);
			for (int j = 0; j < allForExportList.size(); j++) {
				if (allForExportList.get(j).getDepartment().equals(departmentStr)) {
					allForExportList.get(j).setVteRiskAssessmentPatientSum(vteRiskAssessmentPatientSumList.get(i));
					allForExportList.get(j).setOneDayVteRiskAssessmentRate(oneDayVteRiskAssessmentRateList.get(i));
					allForExportList.get(j).setMiddleHighRiskRate(middleHighRiskRateList.get(i));
				}
			}
		}

		// bleedingQualityRiskAssessment
		// 完成出血评估总数
		String bleedingSumStr = bleedingQualityRiskAssessment.getBleedingSum();
		List<String> bleedingSumList = stringToList(bleedingSumStr);

		// 出血高危率
		String recentlyBleedingRateStr = bleedingQualityRiskAssessment.getRecentlyBleedingRate();
		List<String> recentlyBleedingRateList = stringToList(recentlyBleedingRateStr);

		// 出血及时率
		String oneDayBleedingRateStr = bleedingQualityRiskAssessment.getOneDayBleedingRate();
		List<String> oneDayBleedingRateList = stringToList(oneDayBleedingRateStr);

		departmentStr = bleedingQualityRiskAssessment.getDepartment();
		departmentList = stringToList(departmentStr);

		for (int i = 0; i < departmentList.size(); i++) {
			departmentStr = departmentList.get(i);
			for (int j = 0; j < allForExportList.size(); j++) {
				if (allForExportList.get(j).getDepartment().equals(departmentStr)) {
					allForExportList.get(j).setBleedingSum(bleedingSumList.get(i));
					allForExportList.get(j).setRecentlyBleedingRate(recentlyBleedingRateList.get(i));
					allForExportList.get(j).setOneDayBleedingRate(oneDayBleedingRateList.get(i));
				}
			}
		}
		return allForExportList;
	}

	/**
	 * 导出全部
	 * 
	 * @author zzy
	 * @param dataList
	 * @param excelStr
	 * @param templateFilePath
	 * @param headList
	 * @param cols
	 * @return
	 */
	protected File outputExcelFile(List<AllForExport> allForExportList, String excelStr, String templateFilePath,
			List headList, String[] cols) {

		BufferedInputStream bufferInputStream = null;
		try {
			File file = new File(templateFilePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String createFilePathName = templateFilePath + DateUtil.getYYYYMMDDHHMMSSDate(new Date()) + excelStr;
			// 产生导出文件
			File downloadFile = new File(createFilePathName);
			CSVUtils.writeAllInExcel(downloadFile, headList, allForExportList, cols);
			// 设置response的编码方式
			// response.setContentType("application/x-msdownload");
			// 写明要下载的文件的大小
			// response.setContentLength((int) downloadFile.length());
			// 解决中文乱码
			// response.setHeader("Content-Disposition", "attachment;filename="
			// + encode(fileName + suffix));
			bufferInputStream = new BufferedInputStream(new FileInputStream(downloadFile));
			return downloadFile;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferInputStream != null) {
				try {
					bufferInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 质控视图KPI检测
	 */
	@RequestMapping("/qualityView/queryPatientQualityViewKpi")
	public PatientQualityViewKpiPojo queryPatientQualityViewKpi(
			@RequestParam(value = "jsonString", required = false) String jsonString) {
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		Map map = JsonUtil.getMapFromJson(jsonString);
		PatientQualityViewKpiPojo patientQualityViewKpi = null;
		try {
			patientQualityViewKpi = vtePatientHospitInfoService.queryPatientQualityViewKpi(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patientQualityViewKpi;
	}

	/**
	 * 质控视图KPI检测右侧单独科室数据
	 */
	@RequestMapping("/qualityView/queryPatientQualityViewKpiRight")
	public PatientQualityViewKpiRightPojo queryPatientQualityViewKpiRight(
			@RequestParam(value = "jsonString", required = false) String jsonString) {
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		Map map = JsonUtil.getMapFromJson(jsonString);
		PatientQualityViewKpiRightPojo patientQualityViewKpiRight = null;
		try {
			patientQualityViewKpiRight = vtePatientHospitInfoService.queryPatientQualityViewKpiRight(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patientQualityViewKpiRight;
	}

	/**
	 * 质控视图中高危患者预防-中高危患者数
	 */
	@RequestMapping("/qualityView/queryPreventionForMiddleHighRiskPatients")
	public PreventionForMiddleHighRiskPatientPojo queryPreventionForMiddleHighRiskPatients(
			@RequestParam(value = "jsonString", required = false) String jsonString) {
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		Map map = JsonUtil.getMapFromJson(jsonString);
		PreventionForMiddleHighRiskPatientPojo preventionPatient = null;
		try {
			preventionPatient = vtePatientHospitInfoService.queryPreventionForMiddleHighRiskPatients(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return preventionPatient;
	}

	/**
	 * 质控视图中高危患者预防-预防措施率
	 */
	@RequestMapping("/qualityView/queryPreventiveRatePatients")
	public PreventiveRatePatientPojo queryPreventiveRatePatients(
			@RequestParam(value = "jsonString", required = false) String jsonString) {
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		Map map = JsonUtil.getMapFromJson(jsonString);
		PreventiveRatePatientPojo preventiveRatePatient = null;
		try {
			preventiveRatePatient = vtePatientHospitInfoService.queryPreventiveRatePatients(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return preventiveRatePatient;
	}

	/**
	 * 质控视图VTE风险评估质量
	 */
	@RequestMapping("/qualityView/queryVteQualityRiskAssessment")
	public QualityRiskAssessmentPojo queryVteQualityRiskAssessment(
			@RequestParam(value = "jsonString", required = false) String jsonString) {
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		Map map = JsonUtil.getMapFromJson(jsonString);
		QualityRiskAssessmentPojo qualityRiskAssessment = null;
		try {
			qualityRiskAssessment = vtePatientHospitInfoService.queryVteQualityRiskAssessment(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qualityRiskAssessment;
	}

	/**
	 * 质控视图出血风险评估质量
	 */
	@RequestMapping("/qualityView/queryBleedingQualityRiskAssessment")
	public BleedingQualityRiskAssessmentPojo queryBleedingQualityRiskAssessment(
			@RequestParam(value = "jsonString", required = false) String jsonString) {
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		Map map = JsonUtil.getMapFromJson(jsonString);
		BleedingQualityRiskAssessmentPojo bleedingQualityRiskAssessment = null;
		try {
			bleedingQualityRiskAssessment = vtePatientHospitInfoService.queryBleedingQualityRiskAssessment(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bleedingQualityRiskAssessment;
	}

	/**
	 * 质控视图患病情况
	 */
	@RequestMapping("/qualityView/queryPrevalenceAssessment")
	public PrevalenceAssessmentPojo queryPrevalenceAssessment(
			@RequestParam(value = "jsonString", required = false) String jsonString) {
		if (StringUtil.isEmpty(jsonString)) {
			jsonString = "{}";
		}
		Map map = JsonUtil.getMapFromJson(jsonString);
		PrevalenceAssessmentPojo prevalenceAssessment = null;
		try {
			prevalenceAssessment = vtePatientHospitInfoService.queryPrevalenceAssessment(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prevalenceAssessment;
	}
}