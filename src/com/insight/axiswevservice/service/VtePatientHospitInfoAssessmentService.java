package com.insight.axiswevservice.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.insight.axiswevservice.ReturnPojo.SynAssessmentReturnPojo;
import com.insight.axiswevservice.ReturnPojo.SynReturnPojo;
import com.insight.axiswevservice.ReturnPojo.SynReturnUrlPojo;
import com.insight.axiswevservice.pojo.BatchSynAssessmentPojo;
import com.insight.axiswevservice.pojo.BatchSynAssessmentPojoUrl;
import com.insight.axiswevservice.pojo.BatchSynDoctorAdvicePojo;
import com.insight.axiswevservice.pojo.BatchSynDoctorAdvicePojoUrl;
import com.insight.core.util.R;
@Service
public interface VtePatientHospitInfoAssessmentService {

	/**
	 * 版本1
	 * 风险评估数据同步。
	 * @return
	 * @throws Exception 
	 */
	public SynAssessmentReturnPojo batchSynAssessment(BatchSynAssessmentPojo batchSynAssessmentPojo) throws Exception;
	/**
	 * 版本1
	 * 医嘱处理数据同步。
	 * @return
	 * @throws Exception 
	 */
	public SynReturnPojo batchSynDoctorAdvice(BatchSynDoctorAdvicePojo batchSynDoctorAdvicePojo ) throws Exception;
	/**
	 * 版本2
	 * 风险评估数据同步。
	 * @return
	 * @throws Exception 
	 */
	public SynReturnUrlPojo batchSynAssessmentUrl(BatchSynAssessmentPojoUrl batchSynAssessmentPojo) throws Exception;
	/**
	 * 版本2
	 * 医嘱处理数据同步。
	 * @return
	 */
	public SynReturnUrlPojo batchSynDoctorAdviceUrl(BatchSynDoctorAdvicePojoUrl batchSynDoctorAdvicePojoUrl);
	
}
