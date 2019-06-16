package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;




import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbVteRiskScore;

/**
 * 
 * 描述:VTE风险分度服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface VteRiskScoreService {
	
	/**
	 * 添加VTE风险分度
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbVteRiskScore saveVteRiskScore(Map map) throws Exception ;
	
	
	/**
	 * 删除VTE风险分度
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delVteRiskScore(Map map) throws Exception ;
	
	/**
	 * 查询VTE风险分度(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List queryVteRiskScoreList(Map map) throws Exception;
	
	/**
	 * 查询VTE风险分度(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countVteRiskScoreList(Map map) throws Exception;
	
	/**
	 * 查询全部VTE风险分度(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllVteRiskScoreNp(Map map) throws Exception ;
	/**
	 * 查询全部VTE风险分度(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryVteRiskScoreInfoList(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbVteRiskScore queryVteRiskScoreInfo(Map map) throws Exception;

}
