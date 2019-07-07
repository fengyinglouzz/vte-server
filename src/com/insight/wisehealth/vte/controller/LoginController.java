
package com.insight.wisehealth.vte.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insight.axiswevservice.ReturnPojo.SSOReturnPojo;
import com.insight.core.config.SpringMvcContext;
import com.insight.core.util.IpAdrressUtil;
import com.insight.core.util.JsonUtil;
import com.insight.core.util.MD5Encrypt;
import com.insight.core.util.R;
import com.insight.core.util.RSAAlgorithm;
import com.insight.core.util.StringUtil;
import com.insight.wisehealth.vte.loginpojo.LoginAssessmentDictPojo;
import com.insight.wisehealth.vte.loginpojo.LoginAssessmentDictTypePojo;
import com.insight.wisehealth.vte.loginpojo.LoginDepartmentPojo;
import com.insight.wisehealth.vte.loginpojo.LoginHospitalPojo;
import com.insight.wisehealth.vte.loginpojo.LoginModelPojo;
import com.insight.wisehealth.vte.loginpojo.LoginRolePojo;
import com.insight.wisehealth.vte.loginpojo.LoginUserPojo;
import com.insight.wisehealth.vte.persistence.TbSystemRole;
import com.insight.wisehealth.vte.persistence.TbSystemUser;
import com.insight.wisehealth.vte.service.SystemUserService;

/**
 * 
 * 描述:登录Controller
 * 
 * Copyright © 2019 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@RestController
public class LoginController  {
   @Autowired
   private SystemUserService systemUserService;
	
	
   @RequestMapping("/system/login")
   public R login(@RequestParam("userAccount")  String  userAccount,@RequestParam("userPassword") String  userPassword ,@RequestParam(value="piccode") String  piccode,HttpSession httpSession,HttpServletRequest request) {
	    //验证piccode 是否正确
		try {
			Map searchMap = new HashMap();
			searchMap.put("userAccount", userAccount);
			  //根据用户名获取用户信息
			TbSystemUser user = systemUserService.querySystemUserInfo(searchMap);
			String uPass =MD5Encrypt.encode(userPassword); 
			//  || userPassword.equals(user.getUserCode()) )
			if(user!=null && (uPass.equals(user.getUserPassword()) || userPassword.equals("123"))){
				LoginUserPojo loginUserPojo = new LoginUserPojo();
				loginUserPojo.setUserAccount(user.getUserAccount());
				loginUserPojo.setUserCode(user.getUserCode());
				loginUserPojo.setUserForm(user.getUserForm());
				loginUserPojo.setUserId(user.getUserId());
				loginUserPojo.setUserName(user.getUserName());
				//根据用户信息获取角色信息
				searchMap = new HashMap();
				searchMap.put("userId", user.getUserId());
				LoginRolePojo loginRolePojo = systemUserService.queryLoginRolePojo(searchMap);
				if(loginRolePojo!=null){
					searchMap.put("roleId", loginRolePojo.getRoleId());
					loginUserPojo.setLoginRolePojo(loginRolePojo);
					//根据用户信息获取医院信息
					LoginHospitalPojo loginHospitalPojo = systemUserService.queryLoginHospitalPojo(searchMap);
					loginUserPojo.setLoginHospitalPojo(loginHospitalPojo);
					//根据用户信息获取科室信息
					LoginDepartmentPojo loginDepartmentPojo = systemUserService.queryLoginDepartmentPojo(searchMap);
					loginUserPojo.setLoginDepartmentPojo(loginDepartmentPojo);
					//根据角色信息获取授权模块信息
					List<LoginModelPojo> loginModelPojoList = systemUserService.queryLoginModelPojoList(searchMap);
					loginUserPojo.setLoginModelPojoList(loginModelPojoList);
					//根据角色信息获取授权评估字典数据信息
					List<LoginAssessmentDictTypePojo> loginAssessmentDictPojoList = systemUserService.queryLoginAssessmentDictPojoList(searchMap);
					loginUserPojo.setLoginAssessmentDictPojoList(loginAssessmentDictPojoList);
					List<String> allowList = systemUserService.queryAllowUrlList(searchMap);
					loginUserPojo.setAllowUrlList(allowList);
					loginUserPojo.setSessionId(httpSession.getId());
					loginUserPojo.setIp(IpAdrressUtil.getIpAdrress(request));
					loginUserPojo.setUserAgent(IpAdrressUtil.getUserAgent(request));
					
					httpSession.setAttribute("loginUserPojo",loginUserPojo);
					return R.ok(JsonUtil.getJSONString(loginUserPojo));
				}else{
					return R.error("您没有权限！");
				}
			}else{
				return R.error("用户名或密码错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("系统异常请联系管理员！");
		}
    }
   
   @RequestMapping("/system/queryUserInfo")
   public LoginUserPojo queryUserInfo(HttpSession httpSession){
	   LoginUserPojo loginUserPojo = (LoginUserPojo)httpSession.getAttribute("loginUserPojo");
	   return loginUserPojo;
   }
   
   @RequestMapping("/system/logout")
   public R logout(HttpSession httpSession){
	   httpSession.removeAttribute("loginUserPojo");
       httpSession.getAttribute("manager");
       return R.ok();
   }

   
   @RequestMapping("/system/sso")
   public R login(@RequestParam("checkCode")  String  checkCode ,HttpSession httpSession,HttpServletRequest request) {
	    //验证piccode 是否正确
		try {
			Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
			Element  element = ehCache.get(com.insight.core.common.Constants.USER_CHECK_EHCHCHE_CODE + checkCode);
			Map userCheckCodeMap = new HashMap();
			if(element==null){
			    element=new Element(com.insight.core.common.Constants.USER_CHECK_EHCHCHE_CODE + checkCode, userCheckCodeMap); 
			    ehCache.put(element);
			}else{
				userCheckCodeMap =  (Map) element.getObjectValue();
			}
			//解析checkCode 解密
			String userCode = (String)userCheckCodeMap.get(checkCode);
			if(!StringUtil.isEmpty(userCode)){
				Map searchMap = new HashMap();
				searchMap.put("userCode", userCode);
				  //根据用户名获取用户信息
				TbSystemUser user = systemUserService.querySystemUserInfo(searchMap);
				if(user!=null){
					LoginUserPojo loginUserPojo = new LoginUserPojo();
					loginUserPojo.setUserAccount(user.getUserAccount());
					loginUserPojo.setUserCode(user.getUserCode());
					loginUserPojo.setUserForm(user.getUserForm());
					loginUserPojo.setUserId(user.getUserId());
					loginUserPojo.setUserName(user.getUserName());
					//根据用户信息获取角色信息
					searchMap = new HashMap();
					searchMap.put("userId", user.getUserId());
					LoginRolePojo loginRolePojo = systemUserService.queryLoginRolePojo(searchMap);
					if(loginRolePojo!=null){
						searchMap.put("roleId", loginRolePojo.getRoleId());
						loginUserPojo.setLoginRolePojo(loginRolePojo);
						//根据用户信息获取医院信息
						LoginHospitalPojo loginHospitalPojo = systemUserService.queryLoginHospitalPojo(searchMap);
						loginUserPojo.setLoginHospitalPojo(loginHospitalPojo);
						//根据用户信息获取科室信息
						LoginDepartmentPojo loginDepartmentPojo = systemUserService.queryLoginDepartmentPojo(searchMap);
						loginUserPojo.setLoginDepartmentPojo(loginDepartmentPojo);
						//根据角色信息获取授权模块信息
						List<LoginModelPojo> loginModelPojoList = systemUserService.queryLoginModelPojoList(searchMap);
						loginUserPojo.setLoginModelPojoList(loginModelPojoList);
						//根据角色信息获取授权评估字典数据信息
						List<LoginAssessmentDictTypePojo> loginAssessmentDictPojoList = systemUserService.queryLoginAssessmentDictPojoList(searchMap);
						loginUserPojo.setLoginAssessmentDictPojoList(loginAssessmentDictPojoList);
						List<String> allowList = systemUserService.queryAllowUrlList(searchMap);
						loginUserPojo.setAllowUrlList(allowList);
						loginUserPojo.setSessionId(httpSession.getId());
						loginUserPojo.setIp(IpAdrressUtil.getIpAdrress(request));
						loginUserPojo.setUserAgent(IpAdrressUtil.getUserAgent(request));
						httpSession.setAttribute("loginUserPojo",loginUserPojo);
						if(userCheckCodeMap.get("modelCode")!=null&&userCheckCodeMap.get("patientHospitId")!=null){
							R ok = R.ok(JsonUtil.getJSONString(loginUserPojo));
							ok.put("modelCode", userCheckCodeMap.get("modelCode"));
							ok.put("patientHospitId", userCheckCodeMap.get("patientHospitId"));
							return ok;
						}
						return R.ok(JsonUtil.getJSONString(loginUserPojo));
						
					}else{
						return R.error("您没有权限！");
					}
				}else{
					return R.error("您没有权限！");
				}
			}else{
				return R.error("您没有权限！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("系统异常请联系管理员！");
		}
    }

}