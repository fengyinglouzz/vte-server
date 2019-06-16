package com.insight.axiswevservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.insight.axiswevservice.pojo.VteBatchPatientPojo;
import com.insight.axiswevservice.pojo.VtePatientHospitInfoList;
import com.insight.core.util.R;

@Service
public interface VteBatchPatientHospitInfoService {
	
	
	public R batchSynAssessment(List<VteBatchPatientPojo> patientList) throws Exception;
	
	
}
