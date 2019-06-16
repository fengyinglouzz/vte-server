package test.insight.wisehealth.vte.common;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class LoginUtil {
	
	public static MvcResult Login(MockMvc mockMvc) throws Exception{
		String userAccount = "xinnei";
		String userPassword = "123";
    	//发送请求
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/system/login")
                .accept(MediaType.APPLICATION_JSON).param("userAccount",userAccount).param("userPassword",userPassword).param("piccode","1111"));
        MvcResult mvcResult = resultActions.andReturn();
        return mvcResult;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
