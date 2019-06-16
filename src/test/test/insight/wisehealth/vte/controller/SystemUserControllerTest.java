package test.insight.wisehealth.vte.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.insight.core.config.MyMVCConfig;
import com.insight.core.config.WebInitializer;
import com.insight.wisehealth.vte.controller.SystemUserController;

@RunWith(SpringJUnit4ClassRunner.class)     //调用Spring单元测试类
@WebAppConfiguration
@ContextConfiguration(classes = {WebInitializer.class, MyMVCConfig.class})
public class SystemUserControllerTest {
	@Autowired
    private SystemUserController controller;       //需要测试的Controller
	
	@Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;        //SpringMVC提供的Controller测试类
    
    @Before
    public void setup(){
        mockMvc =  MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void saveUserInfoTest() throws Exception {
    	 //准备参数
     //   String postJson = "{ \"userAccount\": \"BeJson12322\",\"userPassword\": \"1223456\",\"userName\": \"赵四\",\"userCode\": \"222222\",\"userForm\": \"1\",\"isEnable\": \"1\",\"createBy\": \"1\"}";
    	 String postJson = "{\"userId\": \"3\", \"userAccount\": \"xxxxxx\",\"userPassword\": \"2345678\",\"userName\": \"赵四\",\"userCode\": \"222222\",\"userForm\": \"1\"}";
    	
    	//发送请求
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/systemUser/saveUserInfo")
                .accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }
    
    @Test
    public void delUserInfoTest() throws Exception {
    	 //准备参数
        String postJson = "{ \"ids\": \"4\"}";
        //发送请求
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/systemUser/delUserInfo")
                .accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }

    @Test
    public void queryUserInfoTest() throws Exception {
    	 //准备参数
        String postJson = "{ \"userAccount\": \"xxxxxx\"}";
        //发送请求
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/systemUser/queryUserInfo")
                .accept(MediaType.APPLICATION_JSON).param("jsonString",postJson));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }
    
    @Test
    public void queryListTest() throws Exception {
    	 //准备参数
        String postJson = "{ \"userForm\": \"1\"}";
        //发送请求
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/systemUser/queryList")
                .accept(MediaType.APPLICATION_JSON).param("jsonString",postJson).param("limit", "10").param("offset", "0"));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("*====客户端获得反馈数据:" + result);
    }
}