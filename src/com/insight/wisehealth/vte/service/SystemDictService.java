package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;





import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbSystemDict;

/**
 * 
 * 描述:字典服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface SystemDictService {
	
	/**
	 * 添加字典
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbSystemDict saveSystemDict(Map map) throws Exception ;
	
	
	/**
	 * 删除字典
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delSystemDict(Map map) throws Exception ;
	
	/**
	 * 查询字典(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List querySystemDictList(Map map) throws Exception;
	
	/**
	 * 查询字典(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countSystemDictList(Map map) throws Exception;
	
	/**
	 * 查询全部字典(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllSystemDictNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbSystemDict querySystemDictInfo(Map map) throws Exception;
	
	/**
	 * 本地化字典
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public  Map queryLocalDictData(Map<String, Object> map) throws Exception;
}
