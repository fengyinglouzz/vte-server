package com.insight.axiswevservice.service;

import org.springframework.stereotype.Service;

import com.insight.axiswevservice.ReturnPojo.SSOReturnPojo;
import com.insight.axiswevservice.ReturnPojo.SynReturnUrlPojo;
import com.insight.axiswevservice.pojo.SingleSignOnAndHospitInfo;
import com.insight.core.util.R;
@Service
public interface VteSingleSignOnService {

	public SynReturnUrlPojo singleSignOn(SingleSignOnAndHospitInfo signOnAndHospitInfo);
}
