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

@RunWith(SpringJUnit4ClassRunner.class)     //调用Spring单元测试类
@WebAppConfiguration
@ContextConfiguration(classes = {WebInitializer.class, MyMVCConfig.class})
public class VteDoctorAdviceControllerTest {
	@Autowired
    private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;        //SpringMVC提供的Controller测试类
	
	 @Before
    public void setup(){
        mockMvc =  MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void queryListExport() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	 //准备参数
        String postJson = "{'patientHospitId':1,'patientCode':'10001',doctorAdviceResult:'3',doctorAdviceRisk :1,doctorAdviceCent : '2'}";
    	
      //发送请求
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/vteAssessmentAdviceSave/saveVteDoctorAdviceInfo").session((MockHttpSession) loginResult.getRequest().getSession())  
                .accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }
}
