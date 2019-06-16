package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;




import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbVteDvtAdvice;

/**
 * 
 * 描述:DVT诊断流程建议服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface VteDvtAdviceService {
	
	/**
	 * 添加DVT诊断流程建议
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbVteDvtAdvice saveVteDvtAdvice(Map map) throws Exception ;
	
	
	/**
	 * 删除DVT诊断流程建议
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delVteDvtAdvice(Map map) throws Exception ;
	
	/**
	 * 查询DVT诊断流程建议(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List queryVteDvtAdviceList(Map map) throws Exception;
	
	/**
	 * 查询DVT诊断流程建议(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countVteDvtAdviceList(Map map) throws Exception;
	
	/**
	 * 查询全部DVT诊断流程建议(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllVteDvtAdviceNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbVteDvtAdvice queryVteDvtAdviceInfo(Map map) throws Exception;

}
