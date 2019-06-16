package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;




import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbVteDepartment;

/**
 * 
 * 描述:患者科室（用于方便日后统计使用）服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface VteDepartmentService {
	
	/**
	 * 添加患者科室（用于方便日后统计使用）
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbVteDepartment saveVteDepartment(Map map) throws Exception ;
	
	
	/**
	 * 删除患者科室（用于方便日后统计使用）
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delVteDepartment(Map map) throws Exception ;
	
	/**
	 * 查询患者科室（用于方便日后统计使用）(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List queryVteDepartmentList(Map map) throws Exception;
	
	/**
	 * 查询患者科室（用于方便日后统计使用）(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countVteDepartmentList(Map map) throws Exception;
	
	/**
	 * 查询全部患者科室（用于方便日后统计使用）(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllVteDepartmentNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbVteDepartment queryVteDepartmentInfo(Map map) throws Exception;

}
