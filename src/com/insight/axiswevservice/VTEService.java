package com.insight.axiswevservice;

import java.security.interfaces.RSAPublicKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.insight.axiswevservice.ReturnPojo.BatchPrintCheckReturnPojo;
import com.insight.axiswevservice.ReturnPojo.BatchPrintSingleReturnPojo;
import com.insight.axiswevservice.ReturnPojo.BatchPrintStatisticsDataReturnPojo;
import com.insight.axiswevservice.ReturnPojo.PublicKeyInfo;
import com.insight.axiswevservice.ReturnPojo.SSOReturnPojo;
import com.insight.axiswevservice.ReturnPojo.SynAssessmentReturnPojo;
import com.insight.axiswevservice.ReturnPojo.SynPatientReturnPojo;
import com.insight.axiswevservice.ReturnPojo.SynReturnUrlPojo;
import com.insight.axiswevservice.ReturnPojo.SynUserInfoReturnPojo;
import com.insight.axiswevservice.pojo.BatchSynAssessmentPojo;
import com.insight.axiswevservice.pojo.BatchSynAssessmentPojoUrl;
import com.insight.axiswevservice.pojo.BatchSynDoctorAdvicePojo;
import com.insight.axiswevservice.pojo.BatchSynDoctorAdvicePojoUrl;
import com.insight.axiswevservice.pojo.BatchSystemUsryPojo;
import com.insight.axiswevservice.pojo.SingleSignOnAndHospitInfo;
import com.insight.axiswevservice.pojo.SynDoctorAdviceReturnPojo;
import com.insight.axiswevservice.pojo.VteBatchPatientPojo;
import com.insight.axiswevservice.service.VteBatchPatientHospitInfoService;
import com.insight.axiswevservice.service.VteBatchUserService;
import com.insight.axiswevservice.service.VtePatientHospitInfoAnalysisResultsService;
import com.insight.axiswevservice.service.VtePatientHospitInfoAssessmentService;
import com.insight.axiswevservice.service.VteSingleSignOnService;
import com.insight.core.config.SpringMvcContext;
import com.insight.core.util.RSAAlgorithm;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.pojo.BatchPrintStatisticsDataPojo;
import com.insight.wisehealth.vte.pojo.MediumHighRiskPatientsAnalysisResultsPojo;
import com.insight.wisehealth.vte.pojo.OneLruAssessmentResultPojo;



@Controller("vteService")
public class VTEService {
	@Autowired
	VteBatchPatientHospitInfoService vteBatchPatientHospitInfoService;
	@Autowired
	VtePatientHospitInfoAssessmentService vtePatientHospitInfoAssessmentService;
	@Autowired
	VtePatientHospitInfoAnalysisResultsService vtePatientHospitInfoAnalysisResultsService;
	@Autowired
	VteBatchUserService vteBatchUserService;
	@Autowired
	VteSingleSignOnService vteSingleSignOnService;
	@PostConstruct  
    private void  init(){  
		//初始化枚举
    }
	
	/**
	 * 获取非对称加密公钥
	 * @param authenticationCode
	 * @return
	 */
	public PublicKeyInfo getPublicKey(String authenticationCode){
		PublicKeyInfo publicKeyInfo = new PublicKeyInfo();
		boolean flag = WebserviceConfig.checkAuthenticationCode(authenticationCode);
	    if(flag){
	    	publicKeyInfo.setMessage("获取成功！");
	    	publicKeyInfo.setStatus("1");
	    	publicKeyInfo.setPublicKeyCode(Base64.encodeBase64String(RSAAlgorithm.getRsaPublicKey().getEncoded()));
	    }else{
	    	publicKeyInfo.setMessage("无访问权限!");
	    	publicKeyInfo.setMessage("0");//返回失败
	    }
		return publicKeyInfo;
	}
	
	/**
	 * 患者信息批量传入
	 * @param vtePatientHospitInfoList
	 * @return
	 */
	public SynPatientReturnPojo batchSynPatient(String[] patientPojoList){
		SynPatientReturnPojo returnPojo = new SynPatientReturnPojo();
		try{
			java.util.List<VteBatchPatientPojo> vtePatientHospitInfoList =  new ArrayList();
			if(patientPojoList!=null){
				for(String data:patientPojoList){
					if(WebserviceConfig.isEncryption()){////是否需要数据解密
						//需要数据解密
						//将解密数据
						data = RSAAlgorithm.RSADecode(data, RSAAlgorithm.getRsaPrivateKey());
					}
					//转化为VteBatchPatientPojo 对象
					JSONObject jsonObject=JSONObject.fromObject(data);
					dealApierrorWord(jsonObject);
					VteBatchPatientPojo painfo=(VteBatchPatientPojo)JSONObject.toBean(jsonObject, VteBatchPatientPojo.class);
					DateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(jsonObject.get("patientInHospital") != null && !jsonObject.get("patientInHospital").equals(null) && !jsonObject.get("patientInHospital").equals("")) {
						String dateIn =  jsonObject.get("patientInHospital").toString().replace("T", " ");
						Date inHospital = formatter.parse(dateIn);
						painfo.setPatientInHospital(inHospital);
					} else {
						painfo.setPatientInHospital(null);
					}
					if(jsonObject.get("patientOutHospital") != null && !jsonObject.get("patientOutHospital").equals(null) && !jsonObject.get("patientOutHospital").equals("")) {
						String dateOut =  jsonObject.get("patientOutHospital").toString().replace("T", " ");
						Date outHospital = formatter.parse(dateOut);
						painfo.setPatientOutHospital(outHospital);
					} else {
						painfo.setPatientOutHospital(null);
					}
					//将该对象数据存放到vtePatientHospitInfoList中
					vtePatientHospitInfoList.add(painfo);
				}
			}
			vteBatchPatientHospitInfoService.batchSynAssessment(vtePatientHospitInfoList);
			returnPojo.setMessage("数据传输成功！");
			returnPojo.setStatus("1");
		}catch(Exception e){
			returnPojo.setMessage("数据传送失败！");
			returnPojo.setStatus("2");
		}
		return returnPojo;
	}
	
	/**
	 * 评估数据同步
	 * @param batchSynAssessmentPojo
	 * @return
	 */
	public SynAssessmentReturnPojo   batchSynAssessment(String batchSynAssessmentPojoData){
		SynAssessmentReturnPojo returnPojo = new SynAssessmentReturnPojo();
		try{
			if(WebserviceConfig.isEncryption()){////是否需要数据解密
				//需要数据解密
				//将解密数据
				batchSynAssessmentPojoData = RSAAlgorithm.RSADecode(batchSynAssessmentPojoData, RSAAlgorithm.getRsaPrivateKey());
			}
			JSONObject jsonObject=JSONObject.fromObject(batchSynAssessmentPojoData);
			dealApierrorWord(jsonObject);
			BatchSynAssessmentPojo batchSynAssessmentPojo=(BatchSynAssessmentPojo)JSONObject.toBean(jsonObject, BatchSynAssessmentPojo.class);
			vtePatientHospitInfoAssessmentService.batchSynAssessment(batchSynAssessmentPojo);
			returnPojo.setMessage("数据传输成功！");
			returnPojo.setStatus("1");
		}catch(Exception e){
			returnPojo.setMessage("数据传送失败！");
			returnPojo.setStatus("2");
		}
		return returnPojo;
	}
	
	/**
	 * 部分评估数据同步
	 * @param batchSynAssessmentPojo
	 * @return
	 */
	public SynDoctorAdviceReturnPojo  batchSynDoctorAdvice(String  batchSynDoctorAdvicePojoData){
		SynDoctorAdviceReturnPojo returnPojo = new SynDoctorAdviceReturnPojo();
		try{
			if(WebserviceConfig.isEncryption()){////是否需要数据解密
				//需要数据解密
				//将解密数据
				batchSynDoctorAdvicePojoData = RSAAlgorithm.RSADecode(batchSynDoctorAdvicePojoData, RSAAlgorithm.getRsaPrivateKey());
			}
			JSONObject jsonObject=JSONObject.fromObject(batchSynDoctorAdvicePojoData);
			dealApierrorWord(jsonObject);
			BatchSynDoctorAdvicePojo batchSynDoctorAdvicePojo =(BatchSynDoctorAdvicePojo)JSONObject.toBean(jsonObject, BatchSynDoctorAdvicePojo.class);
			vtePatientHospitInfoAssessmentService.batchSynDoctorAdvice(batchSynDoctorAdvicePojo);
			returnPojo.setMessage("数据传输成功！");
			returnPojo.setStatus("1");
		}catch(Exception e){
			returnPojo.setMessage("数据传送失败！");
			returnPojo.setStatus("2");
		}
		return returnPojo;
	}
	
	/**
	 * 评估数据同步
	 * @param batchSynAssessmentPojo
	 * @return
	 */
	public SynReturnUrlPojo   batchSynAssessmentUrl(String batchSynAssessmentPojoData){
		SynReturnUrlPojo returnPojo = new SynReturnUrlPojo();
		try{
			if(WebserviceConfig.isEncryption()){////是否需要数据解密
				//需要数据解密
				//将解密数据
				batchSynAssessmentPojoData = RSAAlgorithm.RSADecode(batchSynAssessmentPojoData, RSAAlgorithm.getRsaPrivateKey());
			}
			JSONObject jsonObject=JSONObject.fromObject(batchSynAssessmentPojoData);
			dealApierrorWord(jsonObject);
			BatchSynAssessmentPojoUrl batchSynAssessmentPojo=(BatchSynAssessmentPojoUrl)JSONObject.toBean(jsonObject, BatchSynAssessmentPojoUrl.class);
			SynReturnUrlPojo batchSynAssessmentUrl = vtePatientHospitInfoAssessmentService.batchSynAssessmentUrl(batchSynAssessmentPojo);
			returnPojo.setMessage("数据传输成功！");
			returnPojo.setNextVisitUrl(batchSynAssessmentUrl.getNextVisitUrl());
			returnPojo.setStatus("1");
		}catch(Exception e){
			e.printStackTrace();
			returnPojo.setMessage("数据传送失败！");
			returnPojo.setStatus("2");
		}
		return returnPojo;
	}
	/**
	 * 评估数据同步
	 * @param batchSynAssessmentPojo
	 * @return
	 */
	public SynReturnUrlPojo   batchSynDoctorAdviceUrl(String batchSynAssessmentPojoData){
		SynReturnUrlPojo returnPojo = new SynReturnUrlPojo();
		try{
			if(WebserviceConfig.isEncryption()){////是否需要数据解密
				//需要数据解密
				//将解密数据
				batchSynAssessmentPojoData = RSAAlgorithm.RSADecode(batchSynAssessmentPojoData, RSAAlgorithm.getRsaPrivateKey());
			}
			JSONObject jsonObject=JSONObject.fromObject(batchSynAssessmentPojoData);
			dealApierrorWord(jsonObject);
			BatchSynDoctorAdvicePojoUrl batchSynDoctorAdvicePojo=(BatchSynDoctorAdvicePojoUrl)JSONObject.toBean(jsonObject, BatchSynDoctorAdvicePojoUrl.class);
			SynReturnUrlPojo batchSynAssessmentUrl = vtePatientHospitInfoAssessmentService.batchSynDoctorAdviceUrl(batchSynDoctorAdvicePojo);
			returnPojo.setMessage("数据传输成功！");
			returnPojo.setNextVisitUrl(batchSynAssessmentUrl.getNextVisitUrl());
			returnPojo.setStatus("1");
		}catch(Exception e){
			e.printStackTrace();
			returnPojo.setMessage("数据传送失败！");
			returnPojo.setStatus("2");
		}
		return returnPojo;
	}
	/**
	 * 患者评估信息输出
	 * @param customerPublicKeyCode
	 * @param isInHospital
	 * @return
	 */
	public BatchPrintCheckReturnPojo  batchPrintCheck(String customerPublicKeyCode,String  isInHospital){
		BatchPrintCheckReturnPojo returnPojo = new BatchPrintCheckReturnPojo();
		List<String> returnList = new ArrayList();
		try{
			Map map = new HashMap();
			map.put("isInHospital", isInHospital);
			List<MediumHighRiskPatientsAnalysisResultsPojo> list = vtePatientHospitInfoAnalysisResultsService.batchPrintCheck(map);
			if(list!=null){
				RSAPublicKey publickey = RSAAlgorithm.getRSAPublidKeyBybase64(customerPublicKeyCode);
				for(MediumHighRiskPatientsAnalysisResultsPojo data:list){
					JSONObject json = JSONObject.fromObject(data);
					String strJson = json.toString();
					if(WebserviceConfig.isEncryption()){////是否需要数据解密
						strJson = RSAAlgorithm.RSAEncode(strJson, publickey) ;
					}
					returnList.add(strJson);
				}
			}
			returnPojo.setData(returnList);
			returnPojo.setMessage("数据传输成功！");
			returnPojo.setStatus("1");
		}catch(Exception e){
			returnPojo.setData(returnList);
			returnPojo.setMessage("数据传送失败！");
			returnPojo.setStatus("2");
		}
		return returnPojo;
	}
	/**
	 * 患者评估信息输出
	 * @param customerPublicKeyCode
	 * @param isInHospital
	 * @return
	 */
	public BatchPrintSingleReturnPojo  batchPrintSingle(String patientCode){
		BatchPrintSingleReturnPojo returnPojo = new BatchPrintSingleReturnPojo();
		try{
			Map map = new HashMap();
			map.put("patientCode", patientCode);
			OneLruAssessmentResultPojo oneLruAssessmentResultPojo = vtePatientHospitInfoAnalysisResultsService.batchPrintSingle(map);
			JSONObject json = JSONObject.fromObject(oneLruAssessmentResultPojo);
			String strJson = json.toString();
			returnPojo.setData(strJson);
			returnPojo.setMessage("数据传输成功！");
			returnPojo.setStatus("1");
		}catch(Exception e){
			returnPojo.setData("");
			returnPojo.setMessage("数据传送失败！");
			returnPojo.setStatus("2");
		}
		return returnPojo;
	}
	
	/**
	 * 患者评估信息输出
	 * @param customerPublicKeyCode
	 * @param isInHospital
	 * @return
	 */
	public BatchPrintStatisticsDataReturnPojo  batchPrintStatisticsData(String customerPublicKeyCode,String  startDate,String endDate){
		BatchPrintStatisticsDataReturnPojo returnPojo = new BatchPrintStatisticsDataReturnPojo();
		List<String> returnList = new ArrayList();
		try{
			Map map = new HashMap();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			List<BatchPrintStatisticsDataPojo> list = vtePatientHospitInfoAnalysisResultsService.batchPrintStatisticsData(map);
			if(list!=null){
				RSAPublicKey publickey = RSAAlgorithm.getRSAPublidKeyBybase64(customerPublicKeyCode);
				for(BatchPrintStatisticsDataPojo data:list){
					JSONObject json = JSONObject.fromObject(data);
					String strJson = json.toString();
					if(WebserviceConfig.isEncryption()){////是否需要数据解密
						strJson = RSAAlgorithm.RSAEncode(strJson, publickey) ;
					}
					returnList.add(strJson);
				}
			}
			returnPojo.setData(returnList);
			returnPojo.setMessage("数据传输成功！");
			returnPojo.setStatus("1");
		}catch(Exception e){
			returnPojo.setData(returnList);
			returnPojo.setMessage("数据传送失败！");
			returnPojo.setStatus("2");
		}
		return returnPojo;
	}
	
	/**
	 * 用户信息同步
	 * @param vtePatientHospitInfoList
	 * @return
	 */
	public SynUserInfoReturnPojo batchSynUserInfo(String[] userPojoList){
		SynUserInfoReturnPojo returnPojo = new SynUserInfoReturnPojo();
		try{
			List<BatchSystemUsryPojo> systemUserPojoList = new ArrayList();
			if(userPojoList!=null){
				for(String data:userPojoList){
					if(WebserviceConfig.isEncryption()){////是否需要数据解密
						//需要数据解密
						//将解密数据
						data = RSAAlgorithm.RSADecode(data, RSAAlgorithm.getRsaPrivateKey());
					}
					//转化为VteBatchPatientPojo 对象
					JSONObject jsonObject=JSONObject.fromObject(data);
					BatchSystemUsryPojo painfo=(BatchSystemUsryPojo)JSONObject.toBean(jsonObject, BatchSystemUsryPojo.class);
					//将该对象数据存放到vtePatientHospitInfoList中
					systemUserPojoList.add(painfo);
				}
			}
			vteBatchUserService.batchSynUserInfo(systemUserPojoList);
			returnPojo.setMessage("数据传输成功！");
			returnPojo.setStatus("1");
		}catch(Exception e){
			returnPojo.setMessage("数据传送失败！");
			returnPojo.setStatus("2");
		}
		return returnPojo;
	}
	
	/**
	 * SSOReturnPojo
	 * @return
	 */
	public SSOReturnPojo ssoUrl(SingleSignOnAndHospitInfo signOnAndHospitInfo){
		Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
		Element  element = ehCache.get(com.insight.core.common.Constants.USER_CHECK_EHCHCHE_CODE);
		Map userCheckCodeMap = new HashMap();
		if(element==null){
		    element=new Element(com.insight.core.common.Constants.USER_CHECK_EHCHCHE_CODE, userCheckCodeMap); 
		    ehCache.put(element);
		}else{
			userCheckCodeMap =  (Map) element.getObjectValue();
		}
		SSOReturnPojo returnPojo = new SSOReturnPojo();
		String userCode = "admin";
		String data = UUID.randomUUID().toString().replaceAll("-","");  
		userCheckCodeMap.put(data,userCode);
		returnPojo.setMessage("数据传输成功！");
		returnPojo.setStatus("1");
		returnPojo.setUrl("http://localhost:8002/#/sso?checkCode="+data);
		return returnPojo;
	
	}
	public SynReturnUrlPojo singleSignOn(String batchSynAssessmentPojoData){
		Date dateForLog = new Date();
		System.out.println("接口singlesignon 调用时间: " + dateForLog);
		System.out.println("入参: " + batchSynAssessmentPojoData);
		SynReturnUrlPojo returnPojo = new SynReturnUrlPojo();
		try{
			if(WebserviceConfig.isEncryption()){////是否需要数据解密
				//需要数据解密
				//将解密数据
				batchSynAssessmentPojoData = RSAAlgorithm.RSADecode(batchSynAssessmentPojoData, RSAAlgorithm.getRsaPrivateKey());
			}
			JSONObject jsonObject=JSONObject.fromObject(batchSynAssessmentPojoData);
			dealApierrorWord(jsonObject);
			System.out.println("转化为jsonObject: " + jsonObject);
			SingleSignOnAndHospitInfo signOnAndHospitInfo=(SingleSignOnAndHospitInfo)JSONObject.toBean(jsonObject, SingleSignOnAndHospitInfo.class);
			if(jsonObject.get("patientInHospital") != null && !jsonObject.get("patientInHospital").equals(null) && !jsonObject.get("patientInHospital").equals("")) { 
				DateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date =  jsonObject.get("patientInHospital").toString().replace("T", " ");
				Date inHospital = formatter.parse(date);
				signOnAndHospitInfo.setPatientInHospital(inHospital);
			} else {
				signOnAndHospitInfo.setPatientInHospital(null);
			}
			if(jsonObject.get("patientOutHospital") != null && !jsonObject.get("patientOutHospital").equals(null) && !jsonObject.get("patientOutHospital").equals("")) { 
				DateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date =  jsonObject.get("patientOutHospital").toString().replace("T", " ");
				Date outHospital = formatter.parse(date);
				signOnAndHospitInfo.setPatientOutHospital(outHospital);
			} else {
				signOnAndHospitInfo.setPatientOutHospital(null);
			}
			

			SynReturnUrlPojo batchSynAssessmentUrl = vteSingleSignOnService.singleSignOn(signOnAndHospitInfo);
			returnPojo.setMessage("数据传输成功！");
			returnPojo.setNextVisitUrl( batchSynAssessmentUrl.getNextVisitUrl());
			returnPojo.setStatus("1");
		}catch(Exception e){
			e.printStackTrace();
			returnPojo.setMessage("数据传送失败！");
			returnPojo.setStatus("2");
		}
		return returnPojo;
		
	}
	
	private void dealApierrorWord(JSONObject jsonObject){
		if(jsonObject!=null){
			String patientPhoneNumber = (String)jsonObject.get("patientPhoneNumber");
			if(StringUtil.isEmpty(patientPhoneNumber)&&(patientPhoneNumber == "")){
				jsonObject.accumulate("patientPhoneNumber", jsonObject.get("patienthoneNumber"));
			}
//			String patientNativePlace = (String)jsonObject.get("patientNativePlace");
//			if(StringUtil.isEmpty(patientPhoneNumber)){
//				jsonObject.accumulate("patientNativePlace", jsonObject.get("patientNativePlace"));
//			}
		}
	}
	
}