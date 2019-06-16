package com.insight.axiswevservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.insight.axiswevservice.pojo.BatchSystemUserList;
import com.insight.axiswevservice.pojo.BatchSystemUsryPojo;
import com.insight.core.util.R;
@Service
public interface VteBatchUserService {
	
	public R batchSynUserInfo(List<BatchSystemUsryPojo> systemUserPojoList) throws Exception ;
	
}
