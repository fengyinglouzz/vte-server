package com.insight.wisehealth.vte.common;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * 
 * 描述:
 * 
 * Copyright (c) 2015 by . Frank
 * 根据字典相关的常量表
 * @author Frank
 * @version 1.0
 */
public class ConstantsDict {
	
	//风险评估类型-VTE风险评估
	public final static String ASSESSMENT_TYPE_VTE1 = "1";
	//风险评估类型-出血风险评估
	public final static String ASSESSMENT_TYPE_VTE2 = "2";
	//风险评估类型-预防禁忌评估
	public final static String ASSESSMENT_TYPE_VTE3 = "3";
	
	//风险评估项目-Caprini评分
	public final static String ASSESSMENT_ITEM_VTE1 = "1";
	//风险评估项目-Padua评分
	public final static String ASSESSMENT_ITEM_VTE2 = "2";
	//风险评估项目-外科出血风险评估
	public final static String ASSESSMENT_ITEM_VTE3 = "3";
	//风险评估项目-内科出血风险评估
	public final static String ASSESSMENT_ITEM_VTE4 = "4";
	//风险评估项目-药物预防禁忌评估
	public final static String ASSESSMENT_ITEM_VTE5 = "5";
	//风险评估项目-机械预防禁忌评估
	public final static String ASSESSMENT_ITEM_VTE6 = "6";
	
	//风险评估结果-低危
	public final static String ASSESSMENT_RESULT_VTE1 = "1";
	//风险评估结果-中危
	public final static String ASSESSMENT_RESULT_VTE2 = "2";
	//风险评估结果-高危
	public final static String ASSESSMENT_RESULT_VTE3 = "3";
	//风险评估结果-有
	public final static String ASSESSMENT_RESULT_VTE4 = "4";
	//风险评估结果-无
	public final static String ASSESSMENT_RESULT_VTE5 = "5";
	
	//科室 查看数据权限
	public final static String DATA_POWER_CODE = "2";
	
	//风险评估类型
	public final static Map<String, String> ASSESSMENT_TYPE_MAP = new HashMap<String, String>();
	static {
    	ASSESSMENT_TYPE_MAP.put("1-001-001", "1"); 	//VTE风险评估
    	ASSESSMENT_TYPE_MAP.put("1-001-002", "2");	//出血风险评估
    	ASSESSMENT_TYPE_MAP.put("1-001-003", "3");	//预防禁忌评估
    	ASSESSMENT_TYPE_MAP.put("1-002-001", "4");	//DVT疑诊
    	ASSESSMENT_TYPE_MAP.put("1-002-002", "5");	//PTE疑诊
    	ASSESSMENT_TYPE_MAP.put("1-002-003", "6");	//诊断结果及医嘱
    }
	//风险评估项目
	public final static Map<String, String> ASSESSMENT_ITEM_MAP = new HashMap<String, String>();
	static {
		ASSESSMENT_ITEM_MAP.put("1-001-001-001", "1"); 	//Caprini评分
		ASSESSMENT_ITEM_MAP.put("1-001-001-002", "2");	//Padua评分
		ASSESSMENT_ITEM_MAP.put("1-001-002-001", "3");	//外科出血风险评估
		ASSESSMENT_ITEM_MAP.put("1-001-002-002", "4");	//内科出血风险评估
		ASSESSMENT_ITEM_MAP.put("1-001-003-001", "5");	//药物预防禁忌评估
		ASSESSMENT_ITEM_MAP.put("1-001-003-002", "6");	//机械预防禁忌评估
		ASSESSMENT_ITEM_MAP.put("1-002-001-001", "7");	//Wells评分
		ASSESSMENT_ITEM_MAP.put("1-002-002-001", "8");	//简化Wells评分
		ASSESSMENT_ITEM_MAP.put("1-002-002-002", "9");	//修订版Geneva评分
		ASSESSMENT_ITEM_MAP.put("1-002-003-001", "10");	//医嘱处理
    }   
	//医嘱处理诊断结果-PTE
	public final static String DOCTOR_ADVICE_RESULT1 = "1";
	//医嘱处理诊断结果-DVT
	public final static String DOCTOR_ADVICE_RESULT2 = "2";
	
	
	//风险处理措施-一般预防
	public final static String DOCTOR_ADVICE_RISK1 = "1";
	//风险处理措施-药物预防
	public final static String DOCTOR_ADVICE_RISK2 = "2";
	//风险处理措施-机械预防
	public final static String DOCTOR_ADVICE_RISK3 = "3";
	
	
	//内科
	public final static String DEPARTMENT_N = "department_n";
	//外科
	public final static String DEPARTMENT_W = "department_w";
	
	
	//风险评估项目
	public final static Map<String, String> RISK_SCORE_CODE_MAP = new HashMap<String, String>();
	static {
		RISK_SCORE_CODE_MAP.put("1", "1"); 	//低危
		RISK_SCORE_CODE_MAP.put("2", "2");	//中危
		RISK_SCORE_CODE_MAP.put("3", "3");	//高危
		RISK_SCORE_CODE_MAP.put("4", "1");	//低度危险
		RISK_SCORE_CODE_MAP.put("5", "3");	//高度危险
		RISK_SCORE_CODE_MAP.put("6", "3");	//高危
		RISK_SCORE_CODE_MAP.put("7", "1");	//低危
		RISK_SCORE_CODE_MAP.put("8", "11");	//低度可能
		RISK_SCORE_CODE_MAP.put("9", "13");	//中度可能
		RISK_SCORE_CODE_MAP.put("10", "12");//高度可能
		RISK_SCORE_CODE_MAP.put("11", "11");//低度可能
		RISK_SCORE_CODE_MAP.put("12", "12");//高度可能
		RISK_SCORE_CODE_MAP.put("13", "11");//低度可能
		RISK_SCORE_CODE_MAP.put("14", "12");//高度可能
    } 
	
	public final static Map<String, String> ASSESSMENT_ITEM_TPYE_MAP = new HashMap<String, String>();  //项目对应类型
	static {
		ASSESSMENT_ITEM_TPYE_MAP.put("1", "1"); 	//Caprini评分
		ASSESSMENT_ITEM_TPYE_MAP.put("2", "1");	//Padua评分
		ASSESSMENT_ITEM_TPYE_MAP.put("3", "2");	//外科出血风险评估
		ASSESSMENT_ITEM_TPYE_MAP.put("4", "2");	//内科出血风险评估
		ASSESSMENT_ITEM_TPYE_MAP.put("5", "3");	//药物预防禁忌评估
		ASSESSMENT_ITEM_TPYE_MAP.put("6", "3");	//机械预防禁忌评估
		ASSESSMENT_ITEM_TPYE_MAP.put("7", "4");	//Wells评分
		ASSESSMENT_ITEM_TPYE_MAP.put("8", "5");	//简化Wells评分
		ASSESSMENT_ITEM_TPYE_MAP.put("9", "5");	//修订版Geneva评分
    } 
	//是否进行质控视图
	public final static Integer DEPARTMENT_QUALITYCONTROL_YES = 1;
	public final static Integer DEPARTMENT_QUALITYCONTROL_NO = 2;
	//是否进行中高危检测
	public final static Integer DEPARTMENT_MONITOR_YES = 1;
	public final static Integer DEPARTMENT_MONITOR_NO = 2;
	//字典翻译使用机构id
	public final static Integer ORG_ID = 1;
}
