package test.insight.wisehealth.vte.controller.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.insight.axiswevservice.VTEService;
import com.insight.core.config.MyMVCConfig;
import com.insight.core.config.SpringMvcContext;
import com.insight.core.config.WebInitializer;
import com.insight.core.util.JsonUtil;
import com.insight.wisehealth.vte.common.CachedDict;
import com.insight.wisehealth.vte.persistence.TbVteDoctorAdvice;
import com.insight.wisehealth.vte.persistencepojo.TbVteDoctorAdvicePojo;
import com.insight.wisehealth.vte.service.SystemDictService;

@RunWith(SpringJUnit4ClassRunner.class)     //调用Spring单元测试类
@WebAppConfiguration
@ContextConfiguration(classes = {WebInitializer.class, MyMVCConfig.class})
public class CachedDictTest {
	

	@Test
	public void test() throws Exception {
		VTEService systemDictService = SpringMvcContext.getBean(VTEService.class); 
		System.out.println(systemDictService.toString());
		//Cache  ehCache = SpringMvcContext.getBean(Cache.class); 
		/*Map map = new HashMap();
		map.put("orgId",1);
		map.put("dictCode","patient_is_risk");
		List<Map> dictDataList = CachedDict.getDictDataList(map);
		for(int i=0;i<dictDataList.size();i++){
			Map dictMap=dictDataList.get(i);
			System.out.println(dictMap.get("dictName"));
		}*/
		//System.out.println(CachedDict.dictDataNameToDictDataValue(1, "zh_CN", "patient_is_risk", "是"));
		//System.out.println(CachedDict.gainDictMapKey(1, "zh_CN", "patient_is_risk"));
			
	/*	Map map1 = new HashMap();
		map1.put("patient_is_risk", "patientIsRisk");
		List dataList =new ArrayList();
		Map dMap=new HashMap();
		dMap.put("patientIsRisk", "1");
		dataList.add(dMap);
		//CachedDict.dictDataValue2DictDataName(1, "zh_CN", dataList , map1);
		//System.out.println(dataList+"======");
		
		CachedDict.dictDataValueToDictDataName(1, "zh_CN", dMap , map1);
		System.out.println(dMap+"********");
		
		System.out.println(CachedDict.dictDataValueToDictDataName(1, "zh_CN", "patient_is_risk", "1")+"%%%%%%%");
		
		System.out.println("&&&&&&&&&"+CachedDict.gainDictMapKey(1, "zh_CN", "patient_is_risk"));
		
		System.out.println(CachedDict.finddictMap(1, "zh_CN"));*/
		
		/*Map map1 = new HashMap();
		map1.put("orgId", 1);
		map1.put("dictInternational", "zh_CN");
		
		System.out.println(JsonUtil.getJSONString(CachedDict.getDictData(map1)));
		*/
		
		/*Map dictCodeFieldMap = new HashMap();
		dictCodeFieldMap.put("doctor_advice_result_explain", "doctorAdviceResultExplain");
		dictCodeFieldMap.put("doctor_advice_risk_explain", "doctorAdviceRiskExplain");
		List dataList=new ArrayList();
		TbVteDoctorAdvice tp=new TbVteDoctorAdvice();
		tp.setDoctorAdviceResult("1");
		tp.setDoctorAdviceRisk("1,2");
		tp.setDoctorAdviceCent("xxxxxxxxxxxxxxxxxxxxx");
		TbVteDoctorAdvicePojo tp1=new TbVteDoctorAdvicePojo();
		tp1.setDoctorAdviceResult("2");
		tp1.setDoctorAdviceRisk("2");
		tp1.setDoctorAdviceCent("fffffffffffffffffffff");
		dataList.add(tp1);
		dataList.add(tp);
		CachedDict.dictDataValue2DictDataNameForObj(1, "zh_CN", dataList, dictCodeFieldMap);
		//System.out.println(tp1.getDoctorAdviceResultExplain()+"------"+tp.getDoctorAdviceRiskExplain());
		TbVteDoctorAdvice tp=new TbVteDoctorAdvice();
		tp.setDoctorAdviceResult("1");
		tp.setDoctorAdviceRisk("1,2");
		tp.setDoctorAdviceCent("xxxxxxxxxxxxxxxxxxxxx");
		TbVteDoctorAdvicePojo tp1=new TbVteDoctorAdvicePojo();
		 CachedDict.dictDataValueToDictDataNameTranObject(1, "zh_CN", tp, dictCodeFieldMap, tp1);*/
		 Map map = new HashMap();
			map.put("orgId",1);
			map.put("dictInternational","zh_CN");
			Map returnMap = CachedDict.getDictData(map);
		System.out.println(JsonUtil.getJSONString(returnMap));
		
	}

}
