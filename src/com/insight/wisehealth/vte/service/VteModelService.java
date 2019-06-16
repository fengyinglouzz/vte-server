package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;





import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbVteModel;
import com.insight.wisehealth.vte.pojo.VteModelTreePojo;

/**
 * 
 * 描述:模块表服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface VteModelService {
	
	/**
	 * 添加模块表
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbVteModel saveVteModel(Map map) throws Exception ;
	
	
	/**
	 * 删除模块表
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delVteModel(Map map) throws Exception ;
	
	/**
	 * 查询模块表(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List queryVteModelList(Map map) throws Exception;
	
	/**
	 * 查询模块表(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countVteModelList(Map map) throws Exception;
	
	/**
	 * 查询全部模块表(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllVteModelNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbVteModel queryVteModelInfo(Map map) throws Exception;
	
	/**
	 * 查询模块树
	 * @return
	 * @throws Exception
	 */
	public List<VteModelTreePojo> queryVteModelTreePojo(Map map)throws Exception;

}
