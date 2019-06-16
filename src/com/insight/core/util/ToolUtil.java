package com.insight.core.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.insight.wisehealth.vte.persistence.TbVtePatientHospitInfo;

public class ToolUtil {
	
	/**
	 * map 转对象
	 * 
	 * @param map
	 * @param obj
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object converMapToObject(Map map, Class clazz) throws Exception {
		String jsonString = JsonUtil.getJSONString(map);
		return JsonUtil.getDTO(jsonString, clazz);
	}
	
	public static void main(String[] args) throws Exception {
		Map map = new HashMap();
    	map.put("patientCode", "00203");
    	map.put("patientName", "珠珠测试");
    	map.put("patientNumber", "珠珠测试1");
    	map.put("patientSex", "女");
    	map.put("patientInHospital", "2019-05-08");
    	SimpleDateFormat sf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	//System.out.println(sf.parse((String)map.get("patientInHospital")).toLocaleString());
    	TbVtePatientHospitInfo tbVtePatientHospitInfo = new TbVtePatientHospitInfo();
		tbVtePatientHospitInfo = (TbVtePatientHospitInfo) ToolUtil.converMapToObject(map,TbVtePatientHospitInfo.class);
		System.out.println(tbVtePatientHospitInfo.getPatientInHospital().toLocaleString());
	}
	

}
