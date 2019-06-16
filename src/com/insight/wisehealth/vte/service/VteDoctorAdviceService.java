package com.insight.wisehealth.vte.service;

import java.util.List;
import java.util.Map;




import org.springframework.stereotype.Service;

import com.insight.wisehealth.vte.persistence.TbVteDoctorAdvice;

/**
 * 
 * 描述:医嘱处理服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public interface VteDoctorAdviceService {
	
	/**
	 * 添加医嘱处理
	 * 
	 * @param map
	 * @throws Exception
	 */
	public TbVteDoctorAdvice saveVteDoctorAdvice(Map map) throws Exception ;
	
	
	/**
	 * 删除医嘱处理
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void delVteDoctorAdvice(Map map) throws Exception ;
	
	/**
	 * 查询医嘱处理(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public List queryVteDoctorAdviceList(Map map) throws Exception;
	
	/**
	 * 查询医嘱处理(分页)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int countVteDoctorAdviceList(Map map) throws Exception;
	
	/**
	 * 查询全部医嘱处理(不分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List queryAllVteDoctorAdviceNp(Map map) throws Exception ;
	
	/**
	 * 查询详情信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public TbVteDoctorAdvice queryVteDoctorAdviceInfo(Map map) throws Exception;

}
