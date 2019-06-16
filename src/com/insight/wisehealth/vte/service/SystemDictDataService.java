package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;




import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbSystemDictData;

/**
 * 
 * 描述:字典数据服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface SystemDictDataService {
	
	/**
	 * 添加字典数据
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbSystemDictData saveSystemDictData(Map map) throws Exception ;
	
	
	/**
	 * 删除字典数据
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delSystemDictData(Map map) throws Exception ;
	
	/**
	 * 查询字典数据(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List querySystemDictDataList(Map map) throws Exception;
	
	/**
	 * 查询字典数据(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countSystemDictDataList(Map map) throws Exception;
	
	/**
	 * 查询全部字典数据(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllSystemDictDataNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbSystemDictData querySystemDictDataInfo(Map map) throws Exception;

}
