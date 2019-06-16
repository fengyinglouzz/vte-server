package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.loginpojo.LoginAssessmentDictTypePojo;
import com.insight.wisehealth.vte.loginpojo.LoginDepartmentPojo;
import com.insight.wisehealth.vte.loginpojo.LoginHospitalPojo;
import com.insight.wisehealth.vte.loginpojo.LoginModelPojo;
import com.insight.wisehealth.vte.loginpojo.LoginRolePojo;
import com.insight.wisehealth.vte.persistence.TbSystemUser;

/**
 * 
 * 描述:用户服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface SystemUserService {
	
	/**
	 * 添加用户
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbSystemUser saveSystemUser(Map map) throws Exception ;
	
	
	/**
	 * 删除用户
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delSystemUser(Map map) throws Exception ;
	
	/**
	 * 查询用户(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List querySystemUserList(Map map) throws Exception;
	
	/**
	 * 查询用户(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countSystemUserList(Map map) throws Exception;
	
	/**
	 * 查询全部用户(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllSystemUserNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbSystemUser querySystemUserInfo(Map map) throws Exception;
	
	public LoginRolePojo queryLoginRolePojo(Map map) throws Exception;
	
	public LoginHospitalPojo queryLoginHospitalPojo(Map map) throws Exception;
	
	public LoginDepartmentPojo queryLoginDepartmentPojo(Map map) throws Exception;
	
	public List<LoginModelPojo> queryLoginModelPojoList(Map map) throws Exception;
	
	public List<LoginAssessmentDictTypePojo>  queryLoginAssessmentDictPojoList(Map map) throws Exception;
	
	public List<String> queryAllowUrlList(Map map) throws Exception;
	
	

}
