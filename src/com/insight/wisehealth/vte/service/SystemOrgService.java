package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;






import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbSystemOrg;
import com.insight.wisehealth.vte.pojo.VteHospitalPojo;

/**
 * 
 * 描述:机构服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface SystemOrgService {
	
	/**
	 * 添加机构
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbSystemOrg saveSystemOrg(Map map) throws Exception ;
	
	
	/**
	 * 删除机构
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delSystemOrg(Map map) throws Exception ;
	
	/**
	 * 查询机构(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List querySystemOrgList(Map map) throws Exception;
	
	/**
	 * 查询机构(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countSystemOrgList(Map map) throws Exception;
	
	/**
	 * 查询全部机构(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllSystemOrgNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbSystemOrg querySystemOrgInfo(Map map) throws Exception;
	
	/**
	 * 查询VteHospitalPojo
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<VteHospitalPojo> queryVteHospitalPojoList(Map map) throws Exception;


	public List queryAllSystemDepartment(Map map) throws Exception;

}
