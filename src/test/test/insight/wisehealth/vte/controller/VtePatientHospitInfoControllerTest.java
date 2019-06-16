package test.insight.wisehealth.vte.controller;


import java.util.Date;

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
import com.insight.wisehealth.vte.controller.VtePatientHospitInfoController;

@RunWith(SpringJUnit4ClassRunner.class)     //调用Spring单元测试类
@WebAppConfiguration
@ContextConfiguration(classes = {WebInitializer.class, MyMVCConfig.class})
public class VtePatientHospitInfoControllerTest {
	@Autowired
    private VtePatientHospitInfoController controller;       //需要测试的Controller
	
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
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/vtePatientHospitInfo/queryCount").session((MockHttpSession) loginResult.getRequest().getSession())  
                .accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }
    
    /**
     * 质控视图KPI检测左侧视图
     */
    @Test
    public void queryPatientQualityViewKpiTest() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	//准备参数
    	String postJson = "{dateType:2,date:'2019-04'}";
    	//发送请求
    	ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/qualityView/queryPatientQualityViewKpi").session((MockHttpSession) loginResult.getRequest().getSession())
    			.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
    	MvcResult mvcResult = resultActions.andReturn();
    	String result = mvcResult.getResponse().getContentAsString();
    	System.out.println("=====客户端获得反馈数据:" + result);
    }
    /**
     * 质控视图KPI检测右测数据
     */
    @Test
    public void queryPatientQualityViewKpiRightTest() throws Exception {
    	Date d=new Date();
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	//准备参数
    	String postJson = "{startDate:'2019-01-01',endDate:'2019-04-30'}";
    	//发送请求
    	ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/qualityView/queryPatientQualityViewKpiRight").session((MockHttpSession) loginResult.getRequest().getSession())
    			.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
    	MvcResult mvcResult = resultActions.andReturn();
    	String result = mvcResult.getResponse().getContentAsString();
    	System.out.println("=====客户端获得反馈数据:" + result);
    	Date d2=new Date();
    	Long a=d2.getTime()-d.getTime();
    	System.out.println("==========================:"+a);
    }
    /**
     * 质控视图中高危患者预防-中高危患者数
     */
    @Test
    public void queryPreventionForMiddleHighRiskPatientsTest() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	//准备参数
    	String postJson = "{dateType:1}";
    	//发送请求
    	ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/qualityView/queryPreventionForMiddleHighRiskPatients").session((MockHttpSession) loginResult.getRequest().getSession())
    			.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
    	MvcResult mvcResult = resultActions.andReturn();
    	String result = mvcResult.getResponse().getContentAsString();
    	System.out.println("=====客户端获得反馈数据:" + result);
    }

    /**
     * 质控视图中高危患者预防-预防措施率
     */
    @Test
    public void queryPreventiveRatePatientsTest() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	//准备参数
    	String postJson = "{dateType:1}";
    	//发送请求
    	ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/qualityView/queryPreventiveRatePatients").session((MockHttpSession) loginResult.getRequest().getSession())
    			.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
    	MvcResult mvcResult = resultActions.andReturn();
    	String result = mvcResult.getResponse().getContentAsString();
    	System.out.println("=====客户端获得反馈数据:" + result);
    }
    /**
     * 质控视图VTE风险评估质量
     */
    @Test
    public void queryVteQualityRiskAssessmentTest() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	//准备参数
    	String postJson = "{dateType:1}";
    	//发送请求
    	ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/qualityView/queryVteQualityRiskAssessment").session((MockHttpSession) loginResult.getRequest().getSession())
    			.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
    	MvcResult mvcResult = resultActions.andReturn();
    	String result = mvcResult.getResponse().getContentAsString();
    	System.out.println("=====客户端获得反馈数据:" + result);
    }
    /**
     * 质控视图出血风险评估质量
     */
    @Test
    public void queryBleedingQualityRiskAssessmentTest() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	//准备参数
    	String postJson = "{dateType:1}";
    	//发送请求
    	ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/qualityView/queryBleedingQualityRiskAssessment").session((MockHttpSession) loginResult.getRequest().getSession())
    			.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
    	MvcResult mvcResult = resultActions.andReturn();
    	String result = mvcResult.getResponse().getContentAsString();
    	System.out.println("=====客户端获得反馈数据:" + result);
    }
    /**
     * 质控视图患病情况
     */
    @Test
    public void queryPrevalenceAssessmentTest() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	//准备参数
    	String postJson = "{dateType:1,resultType:'VTE'}";
    	//发送请求
    	ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/qualityView/queryPrevalenceAssessment").session((MockHttpSession) loginResult.getRequest().getSession())
    			.accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
    	MvcResult mvcResult = resultActions.andReturn();
    	String result = mvcResult.getResponse().getContentAsString();
    	System.out.println("=====客户端获得反馈数据:" + result);
    }
}
