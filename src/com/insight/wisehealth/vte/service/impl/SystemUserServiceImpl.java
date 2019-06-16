package com.insight.wisehealth.vte.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.core.util.MD5Encrypt;
import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;
import com.insight.wisehealth.vte.common.CachedDict;
import com.insight.wisehealth.vte.dao.TbSystemOrgDao;
import com.insight.wisehealth.vte.dao.TbSystemRoleDao;
import com.insight.wisehealth.vte.dao.TbSystemUserDao;
import com.insight.wisehealth.vte.dao.TbVteAssessmentDictDao;
import com.insight.wisehealth.vte.dao.TbVteModelDao;
import com.insight.wisehealth.vte.loginpojo.LoginAssessmentDictPojo;
import com.insight.wisehealth.vte.loginpojo.LoginAssessmentDictTypePojo;
import com.insight.wisehealth.vte.loginpojo.LoginDepartmentPojo;
import com.insight.wisehealth.vte.loginpojo.LoginHospitalPojo;
import com.insight.wisehealth.vte.loginpojo.LoginModelPojo;
import com.insight.wisehealth.vte.loginpojo.LoginRolePojo;
import com.insight.wisehealth.vte.persistence.TbSystemUser;
import com.insight.wisehealth.vte.persistence.TbVteAssessmentDict;
import com.insight.wisehealth.vte.persistence.TbVteModel;
import com.insight.wisehealth.vte.pojo.VteAssessmentDictPojo;
import com.insight.wisehealth.vte.pojo.VteAssessmentDictTreePojo;
import com.insight.wisehealth.vte.pojo.VteModelTreePojo;
import com.insight.wisehealth.vte.service.SystemUserService;


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
public class SystemUserServiceImpl  implements SystemUserService{
	@Autowired
	TbSystemUserDao systemUserMapper;
	@Autowired
	TbSystemRoleDao systemRoleMapper;
	@Autowired
	TbSystemOrgDao systemOrgMapper;
	@Autowired
	TbVteModelDao vteModelMapper;
	@Autowired
	TbVteAssessmentDictDao vteAssessmentDictMapper;
	
	
	@Override
	public TbSystemUser saveSystemUser(Map map) throws Exception{
		TbSystemUser tbSystemUser = new TbSystemUser();
		tbSystemUser = (TbSystemUser) ToolUtil.converMapToObject(map,TbSystemUser.class);
		if (null == tbSystemUser.getUserId()) {
			String userPassword = tbSystemUser.getUserPassword();
			tbSystemUser.setUserPassword(MD5Encrypt.encode(userPassword));
			systemUserMapper.insert(tbSystemUser);
		} else {
			String userPassword = tbSystemUser.getUserPassword();
			if(userPassword.isEmpty()){
				map.remove("userPassword");
			}else{
				map.put("userPassword", MD5Encrypt.encode(userPassword));
			}
			systemUserMapper.updateByFormMap(map);
		}
		return tbSystemUser;
	}

	@Override
	public void delSystemUser(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbSystemUser tbSystemUser = new TbSystemUser();
			tbSystemUser.setUserId(Integer.parseInt(id[i]));
			systemUserMapper.deleteByPrimaryKey(tbSystemUser);
		}
	}

	@Override
	public List querySystemUserList(Map map) throws Exception {
		List list = systemUserMapper.queryAllSystemUserPojo(map);
		return list;
	}

	@Override
	public int countSystemUserList(Map map) throws Exception {
		int count = (int)systemUserMapper.countAllSystemUser(map);
		return count;
	}

	@Override
	public List queryAllSystemUserNp(Map map) throws Exception{
		List list = new ArrayList();
		list = systemUserMapper.queryAllSystemUserNP( map);
		return list;
	}
	
	@Override
	public TbSystemUser querySystemUserInfo(Map map) throws Exception{
		TbSystemUser tbSystemUser =	(TbSystemUser)systemUserMapper.querySystemUserInfo(map);
		return tbSystemUser;
	}
	
	@Override
	public LoginRolePojo queryLoginRolePojo(Map map) throws Exception {
		return systemRoleMapper.queryLoginRolePojo(map);
	}

	@Override
	public LoginHospitalPojo queryLoginHospitalPojo(Map map) throws Exception {
		LoginHospitalPojo loginHospitalPojo =  new LoginHospitalPojo();
		loginHospitalPojo = systemOrgMapper.queryLoginHospitalPojo(map);
		return loginHospitalPojo;
	}

	@Override
	public LoginDepartmentPojo queryLoginDepartmentPojo(Map map)
			throws Exception {
		LoginDepartmentPojo loginDepartmentPojo = new LoginDepartmentPojo();
		loginDepartmentPojo = systemOrgMapper.queryLoginDepartmentPojo(map);
		return loginDepartmentPojo;
	}

	@Override
	public List<LoginModelPojo> queryLoginModelPojoList(Map map)
			throws Exception {
		 List<LoginModelPojo> list  = vteModelMapper.queryLoginModelPojoList(map);
		 List<LoginModelPojo>  loginModelPojoList = new ArrayList();
		 if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					LoginModelPojo  tbVteModel = list.get(i);
					String modelCode = tbVteModel.getModelCode();
					String[] modelCs = modelCode.split("-");
					if(modelCs.length==1){
						LoginModelPojo modelpojo = new LoginModelPojo();
						modelpojo.setModelId(tbVteModel.getModelId());
						modelpojo.setModelCode(tbVteModel.getModelCode());
						modelpojo.setModelName(tbVteModel.getModelName());
						modelpojo.setLoginModelPojoList(new ArrayList());
						loginModelPojoList.add(modelpojo);
					}else{
						List<LoginModelPojo> cList = loginModelPojoList;
						StringBuffer codes = new StringBuffer();
						for(int j=0;j<modelCs.length-1;j++){
							if(j!=0){
								codes.append("-");
							}
							codes.append(modelCs[j]);
							LoginModelPojo pPojo = findLoginModelPojoListFormList(cList, codes.toString());
							cList = pPojo.getLoginModelPojoList();
						}
						LoginModelPojo modelpojo = new LoginModelPojo();
						modelpojo.setModelId(tbVteModel.getModelId());
						modelpojo.setModelCode(tbVteModel.getModelCode());
						modelpojo.setModelName(tbVteModel.getModelName());
						modelpojo.setLoginModelPojoList(new ArrayList());
						cList.add(modelpojo);
					}
				}
			}
		return loginModelPojoList;
	}
	
	private  LoginModelPojo findLoginModelPojoListFormList(List<LoginModelPojo> loginModelPojoList,String modelCode){
		LoginModelPojo loginModelPojo = new LoginModelPojo();
		loginModelPojo.setModelCode(modelCode);
		if(loginModelPojoList!=null&&loginModelPojoList.size()>0){
			for(int i=0;i<loginModelPojoList.size();i++){
				LoginModelPojo modelPojo= loginModelPojoList.get(i);
				if(modelPojo.getModelCode().equals(modelCode)){
					if(modelPojo.getLoginModelPojoList()==null){
						modelPojo.setLoginModelPojoList(new ArrayList());
					}
					return modelPojo;
				}
			}
		}
		loginModelPojo.setLoginModelPojoList(new ArrayList());
		return  loginModelPojo;
	}

	@Override
	public List<LoginAssessmentDictTypePojo> queryLoginAssessmentDictPojoList(
			Map map) throws Exception {
			List<LoginAssessmentDictTypePojo>  loginAssessmentDictPojoTreeList = new ArrayList();
			//查询字典编码为assessment_type的字典数据 
			Map dictMap = new HashMap();
			dictMap.put("orgId", 1);
			dictMap.put("dictInternational","zh_CN");
			dictMap.put("dictCode", "assessment_item");
			List<Map> dictdata = CachedDict.getDictDataList(dictMap);
			//查询全部的评估数据
			List<LoginAssessmentDictPojo> list  = vteAssessmentDictMapper.queryLoginAssessmentDictPojoList(map);
			//遍历字典数据
			//将编码一致的数据封装进去
			for(int i=0;i<dictdata.size();i++){
				LoginAssessmentDictTypePojo loginAssessmentDictTypePojo = new LoginAssessmentDictTypePojo();
				Map dictdataMap = dictdata.get(i);
				String dictDataName = (String)dictdataMap.get("dictDataName");
				String dictDataValue = (String)dictdataMap.get("dictDataValue");
				loginAssessmentDictTypePojo.setAssessmentItem(dictDataValue);
				loginAssessmentDictTypePojo.setAssessmentItemExplain(dictDataName);
				List<LoginAssessmentDictPojo> vteAssessmentDictPojoList = new ArrayList();
				if(list!=null&&list.size()>0){
					for(int j=0;j<list.size();j++){
						LoginAssessmentDictPojo tbVteAssessmentDict = (LoginAssessmentDictPojo)list.get(j);
						if(tbVteAssessmentDict.getAssessmentItem().equals(dictDataValue)){
							vteAssessmentDictPojoList.add(tbVteAssessmentDict);
							list.remove(j);
							j--;
						}
					}
				}
				loginAssessmentDictTypePojo.setLoginAssessmentDictPojoList(vteAssessmentDictPojoList);
				loginAssessmentDictPojoTreeList.add(loginAssessmentDictTypePojo);
			}
			return loginAssessmentDictPojoTreeList;
		
	}
	
	@Override
	public List<String> queryAllowUrlList(Map map) throws Exception{
		List<String>  allowUrlList  = vteModelMapper.queryAllowUrlList(map);
		List<String> returnList = new ArrayList(); 
		if(allowUrlList!=null&&allowUrlList.size()>0){
			for(int i=0;i<allowUrlList.size();i++){
				String allowUrl = allowUrlList.get(i);
				if(!StringUtil.isEmpty(allowUrl)){
					String[] allowUrls = allowUrl.split(";");
					for(int j=0;j<allowUrls.length;j++){
						String allowUrlt = allowUrls[j].trim();
						if(!StringUtil.isEmpty(allowUrlt)){
							if(!returnList.contains(allowUrlt)){
								returnList.add(allowUrlt);
							}
						}
					}
				}
			}
		}
		return returnList;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////

	/**
	 * 添加分组数据私有方法
	 * 用户
	 * @param list
	 * @param deleteSystemUserIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveSystemUserByGroup (List list,String deleteSystemUserIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteSystemUserIds)){
			String deleteSystemUserId[] =  deleteSystemUserIds.split(",");
			for(int i=0;i<deleteSystemUserId.length;i++){
				if(!StringUtil.isEmpty(deleteSystemUserId[i])){
					TbSystemUser tbSystemUser = new TbSystemUser();
					tbSystemUser.setUserId(Integer.parseInt(deleteSystemUserId[i]));
					systemUserMapper.deleteByPrimaryKey(tbSystemUser);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbSystemUser tbSystemUser
				= (TbSystemUser) ToolUtil.converMapToObject(map,TbSystemUser.class);
				if (null == tbSystemUser.getUserId()) {
					//TODO 设置Pid 
					systemUserMapper.insertSelective(tbSystemUser);
				} else {
					systemUserMapper.updateByFormMap(map);
				}
			}
		}
	}

	
}
