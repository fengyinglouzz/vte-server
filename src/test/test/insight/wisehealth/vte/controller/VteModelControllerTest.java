package test.insight.wisehealth.vte.controller;

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

@RunWith(SpringJUnit4ClassRunner.class)     //调用Spring单元测试类
@WebAppConfiguration
@ContextConfiguration(classes = {WebInitializer.class, MyMVCConfig.class})
public class VteModelControllerTest {
	
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
        //发送请求
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/vteModel/queryVteModelTree").session((MockHttpSession) loginResult.getRequest().getSession())  
                .accept(MediaType.APPLICATION_JSON).param("roleId", "1"));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }
    
    
    @Test
    public void saveVteModelInfoTest() throws Exception {
    	MvcResult loginResult = LoginUtil.Login(mockMvc);
    	Map map = new HashMap();
    	map.put("modelFatherCode", "2");
    	map.put("modelName","测试模块");
        //发送请求
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/vteModel/saveVteModelInfo").session((MockHttpSession) loginResult.getRequest().getSession())  
                .accept(MediaType.APPLICATION_JSON).param("jsonString", JsonUtil.getJSONString(map)));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }
    
}
