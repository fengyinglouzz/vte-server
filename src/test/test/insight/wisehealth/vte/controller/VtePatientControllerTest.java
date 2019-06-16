package test.insight.wisehealth.vte.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import test.insight.wisehealth.vte.common.LoginUtil;

import com.insight.core.config.MyMVCConfig;
import com.insight.core.config.WebInitializer;
import com.insight.core.util.JsonUtil;
import com.insight.wisehealth.vte.controller.VtePatientController;

@RunWith(SpringJUnit4ClassRunner.class)     //调用Spring单元测试类
@WebAppConfiguration
@ContextConfiguration(classes = {WebInitializer.class, MyMVCConfig.class})
public class VtePatientControllerTest {
	@Autowired
    private VtePatientController controller;       //需要测试的Controller
	
	@Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;        //SpringMVC提供的Controller测试类
    
    @Before
    public void setup(){
        mockMvc =  MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void queryCountTest() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	 //准备参数
        String postJson = "{}";
        //发送请求
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/vtePatient/queryCount").session((MockHttpSession) loginResult.getRequest().getSession())  
                .accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }
    
    @Test
    public void saveTest()throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	Map map = new HashMap();
    	map.put("patientCode", "00203");
    	map.put("patientName", "珠珠测试");
    	map.put("patientNumber", "珠珠测试1");
    	map.put("patientSex", "女");
    	map.put("patientInHospital", "2019-05-08 17:47:48");
   	   //准备参数
       String postJson = JsonUtil.getJSONString(map);
       //发送请求
       ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/vtePatient/addPatient").session((MockHttpSession) loginResult.getRequest().getSession())  
               .accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
       MvcResult mvcResult = resultActions.andReturn();
       String result = mvcResult.getResponse().getContentAsString();
       System.out.println("=====客户端获得反馈数据:" + result);
   }
    
    /**
     * 查询各科室中高危检测患者数
     */
    @Test
    public void queryMediumHighRiskPatientsDeptCountTest() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	//准备参数
    	String postJson = "{patientLastRiskDate:1,patientOutHospital:1}";
    	//发送请求
    	ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/vtePatient/queryMediumHighRiskPatientsDeptCount").session((MockHttpSession) loginResult.getRequest().getSession())
    			.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
    	MvcResult mvcResult = resultActions.andReturn();
    	String result = mvcResult.getResponse().getContentAsString();
    	System.out.println("=====客户端获得反馈数据:" + result);
    }
    
    /**
     * 查询中高危检测患者列表
     */
    @Test
    public void queryMediumHighRiskPatientsList() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	//准备参数
    	String postJson = "{patientLastRiskDate:1,patientOutHospital:1,patientDepartment:'心脏内科'}";
    	//发送请求
    	ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/vtePatient/queryMediumHighRiskPatientsList").session((MockHttpSession) loginResult.getRequest().getSession())
    			.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson).param("start", "0").param("limit", "10"));
    	MvcResult mvcResult = resultActions.andReturn();
    	String result = mvcResult.getResponse().getContentAsString();
    	System.out.println("=====客户端获得反馈数据:" + result);
    }
    
    /**
     * 查询中高危检测患者数
     */
    @Test
    public void queryMediumHighRiskPatientsCount() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	//准备参数
    	String postJson = "{patientLastRiskDate:1,patientOutHospital:1,patientDepartment:'心脏内科'}";
    	//发送请求
    	ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/vtePatient/queryMediumHighRiskPatientsCount").session((MockHttpSession) loginResult.getRequest().getSession())
    			.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
    	MvcResult mvcResult = resultActions.andReturn();
    	String result = mvcResult.getResponse().getContentAsString();
    	System.out.println("=====客户端获得反馈数据:" + result);
    }
    /**
     * 查询最新出血风险评估和最新预防禁忌评估
     */
    @Test
    public void queryPatientVteAssessmentDictList() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	//准备参数
    	String postJson = "{}";
    	//发送请求
    	ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/vtePatient/queryPatientVteAssessmentDictList").session((MockHttpSession) loginResult.getRequest().getSession())
    			.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson).param("assessmentId", "1,2"));
    	MvcResult mvcResult = resultActions.andReturn();
    	String result = mvcResult.getResponse().getContentAsString();
    	System.out.println("=====客户端获得反馈数据:" + result);
    }
    
    /**
     * 查询最新医助处理
     */
    @Test
    public void queryPatientvteDoctorAdvice() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	//准备参数
    	String postJson = "{}";
    	//发送请求
    	ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/vtePatient/queryPatientvteDoctorAdvice").session((MockHttpSession) loginResult.getRequest().getSession())
    			.accept(MediaType.APPLICATION_JSON).param("doctorAdviceId", "1"));
    	MvcResult mvcResult = resultActions.andReturn();
    	String result = mvcResult.getResponse().getContentAsString();
    	System.out.println("=====客户端获得反馈数据:" + result);
    }

}
