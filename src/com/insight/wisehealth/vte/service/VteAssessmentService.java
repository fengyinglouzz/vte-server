package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;













import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.loginpojo.LoginAssessmentDictTypePojo;
import com.insight.wisehealth.vte.loginpojo.LoginModelPojo;
import com.insight.wisehealth.vte.persistence.TbVteAssessment;
import com.insight.wisehealth.vte.persistencepojo.TbVteAssessmentPojo;
import com.insight.wisehealth.vte.pojo.VteAssessmentAndListPojo;
import com.insight.wisehealth.vte.pojo.VteAssessmentStrategyPojo;
import com.insight.wisehealth.vte.pojo.VtePatientAssessmentPojo;

/**
 * 
 * 描述:风险评估服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface VteAssessmentService {
	
	/**
	 * 添加风险评估
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbVteAssessment saveVteAssessment(Map map) throws Exception ;
	
	
	/**
	 * 删除风险评估
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delVteAssessment(Map map) throws Exception ;
	
	/**
	 * 查询风险评估(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List queryVteAssessmentList(Map map) throws Exception;
	
	/**
	 * 查询风险评估(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countVteAssessmentList(Map map) throws Exception;
	/**
	 * 查询风险评估包括医嘱信息(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List queryVteAssessmentListWithAdvice(Map map) throws Exception;
	
	/**
	 * 查询风险评估包括医嘱信息(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countVteAssessmentListWithAdvice(Map map) throws Exception;
	
	/**
	 * 查询全部风险评估(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllVteAssessmentNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbVteAssessment queryVteAssessmentInfo(Map map) throws Exception;
	/**
	 * 查询最后一次评估信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbVteAssessmentPojo queryVteAssessmentInfoLastTime(Map map) throws Exception;
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public VtePatientAssessmentPojo queryPatientAssessment(Map map, List<LoginModelPojo> loginAssessmentList) throws Exception;
	
	/**
	 * 查询全部风险评估(导出)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryVteAssessmentAdviceExport(Map map) throws Exception;
	/**
	 * 查询详情策略
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public VteAssessmentStrategyPojo queryAssessmentStrategy(Map map) throws Exception;
	/**
	 * 列表点击详情
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public VteAssessmentAndListPojo queryAssessment(Map map) throws Exception;
	/**
	 * 列表点击详情
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public VteAssessmentAndListPojo queryAssessmentView(Map map) throws Exception;
	

}
