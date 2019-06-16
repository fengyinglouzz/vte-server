package test.insight.wisehealth.vte.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import test.insight.wisehealth.vte.common.LoginUtil;

import com.insight.core.config.MyMVCConfig;
import com.insight.core.config.WebInitializer;
import com.insight.core.util.JsonUtil;
import com.insight.wisehealth.vte.loginpojo.LoginUserPojo;

@RunWith(SpringJUnit4ClassRunner.class)     //调用Spring单元测试类
@WebAppConfiguration
@ContextConfiguration(classes = {WebInitializer.class, MyMVCConfig.class})
public class LoginControllerTest {
	
	@Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;        //SpringMVC提供的Controller测试类
    
    @Before
    public void setup(){
        mockMvc =  MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void login() throws Exception {
		/*String userAccount = "BeJson12322";
		String userPassword = "123";
    	//发送请求
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/system/login")
                .accept(MediaType.APPLICATION_JSON).param("userAccount",userAccount).param("userPassword",userPassword).param("piccode","1111"));
        MvcResult mvcResult = resultActions.andReturn();*/
    	MvcResult mvcResult = LoginUtil.Login(mockMvc);
        LoginUserPojo loginUserPojo = (LoginUserPojo) mvcResult.getRequest().getSession().getAttribute("loginUserPojo");
        System.out.println("=====登录session信息为:" + JsonUtil.getJSONString(loginUserPojo));
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
	}
}
