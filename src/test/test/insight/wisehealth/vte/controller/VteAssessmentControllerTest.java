package test.insight.wisehealth.vte.controller;


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
import com.insight.wisehealth.vte.controller.VteAssessmentController;

@RunWith(SpringJUnit4ClassRunner.class)     //调用Spring单元测试类
@WebAppConfiguration
@ContextConfiguration(classes = {WebInitializer.class, MyMVCConfig.class})
public class VteAssessmentControllerTest {
	@Autowired
	VteAssessmentController controller;
	@Autowired
    private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;        //SpringMVC提供的Controller测试类
	    
	    @Before
	    public void setup(){
	        mockMvc =  MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	    }

	    //@Test
	    public void queryListExport() throws Exception {
	    	MvcResult loginResult = LoginUtil.Login(mockMvc);
	    	 //准备参数
	        String postJson = "{patientHospitId : 1 }";
	      //发送请求
	        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/vtePatientAssessment/queryListExport").session((MockHttpSession) loginResult.getRequest().getSession())  
	                .accept(MediaType.APPLICATION_JSON).param("jsonString",postJson).param("excelStr", ".xlsx"));
	        MvcResult mvcResult = resultActions.andReturn();
	        String result = mvcResult.getResponse().getContentAsString();
	        System.out.println("=====客户端获得反馈数据:" + result);
	    }
	    //@Test
	    public void queryList() throws Exception {
	    	MvcResult loginResult = LoginUtil.Login(mockMvc);
	    	 //准备参数
	        String postJson = "{'patientHospitId':1}";
	      //发送请求
	        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/vtePatientAssessment/queryList").session((MockHttpSession) loginResult.getRequest().getSession())
	        		.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson).param("start", "0").param("limit", "10"));
	        
	        MvcResult mvcResult = resultActions.andReturn();
	        String result = mvcResult.getResponse().getContentAsString();
	        System.out.println("=====客户端获得反馈数据:" + result);
	    }
	
	    //@Test
	    public void queryListCount() throws Exception {
	    	MvcResult loginResult = LoginUtil.Login(mockMvc);
	    	 //准备参数
	        String postJson = "{}";
	      //发送请求
	        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/vteAssessment/queryCount").session((MockHttpSession) loginResult.getRequest().getSession())
	        		.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
	        
	        MvcResult mvcResult = resultActions.andReturn();
	        String result = mvcResult.getResponse().getContentAsString();
	        System.out.println("=====客户端获得反馈数据:" + result);
	    }
	   // @Test
	    public void queryPatientAssessment() throws Exception {
	    	MvcResult loginResult = LoginUtil.Login(mockMvc);
	      //发送请求
	        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/vtePatientAssessment/queryPatientAssessment").session((MockHttpSession) loginResult.getRequest().getSession())
	        		.accept(MediaType.APPLICATION_JSON).param("patientHospitId","1"));
	        
	        MvcResult mvcResult = resultActions.andReturn();
	        String result = mvcResult.getResponse().getContentAsString();
	        System.out.println("=====客户端获得反馈数据:" + result);
	    }
	    //@Test
	    public void queryAssessment() throws Exception {
	    	MvcResult loginResult = LoginUtil.Login(mockMvc);
	      //发送请求
	        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/vtePatientAssessment/queryAssessment").session((MockHttpSession) loginResult.getRequest().getSession())
	        		.accept(MediaType.APPLICATION_JSON).param("assessmentId","1").param("modelName","assessment"));
	        
	        MvcResult mvcResult = resultActions.andReturn();
	        String result = mvcResult.getResponse().getContentAsString();
	        System.out.println("=====客户端获得反馈数据:" + result);
	    }
	    
	    //@Test
	    public void queryVteAssessmentLastTime() throws Exception {
	    	MvcResult loginResult = LoginUtil.Login(mockMvc);
	      //发送请求
	    	 String postJson = "{'patientHospitId':1,'modelCode':'1-001-001-001'}";
	    	
	        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/vteAssessmentAdviceSave/queryVteAssessmentLastTime").session((MockHttpSession) loginResult.getRequest().getSession())
	        		.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
	        
	        MvcResult mvcResult = resultActions.andReturn();
	        String result = mvcResult.getResponse().getContentAsString();
	        System.out.println("=====客户端获得反馈数据:" + result);
	    }

	    //@Test
	    public void saveVteAssessmentInfo() throws Exception {
	    	MvcResult loginResult = LoginUtil.Login(mockMvc);
	      //发送请求
	    	 String postJson = "{'patientHospitId':1,'modelCode':'1-001-001-001','assessmentStage':'2',assessmentResult:'3',assessmentScore :6,assessmentSelectData : '2,4,6,7'}";
	    	
	        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/vteAssessmentAdviceSave/saveVteAssessmentInfo").session((MockHttpSession) loginResult.getRequest().getSession())
	        		.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
	        
	        MvcResult mvcResult = resultActions.andReturn();
	        String result = mvcResult.getResponse().getContentAsString();
	        System.out.println("=====客户端获得反馈数据:" + result);
	    }
	    
	  //@Test
	    public void queryAssessmentStrategy() throws Exception {
	    	MvcResult loginResult = LoginUtil.Login(mockMvc);
	    	
	        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/vtePatientAssessment/queryAssessmentStrategy").session((MockHttpSession) loginResult.getRequest().getSession())
	        		.accept(MediaType.APPLICATION_JSON).param("patientHospitId","1"));
	        
	        MvcResult mvcResult = resultActions.andReturn();
	        String result = mvcResult.getResponse().getContentAsString();
	        System.out.println("=====客户端获得反馈数据:" + result);
	    }
}
