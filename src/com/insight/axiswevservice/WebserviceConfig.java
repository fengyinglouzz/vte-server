package com.insight.axiswevservice;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.insight.core.util.StringUtil;
import com.insight.core.util.SymmetricEncoder;

/**
 * 
 */
@Configuration
public class WebserviceConfig {
	
	/**
	 * 是否进行非对称加密
	 */
	private static boolean encryption = false;
	
	private  static Map authenticationCodeMap = new HashMap(); 
	
	static{
		authenticationCodeMap.put("WysuoHOXEAJ3cJoRCHviyQ==","001");
	}

	public static Map getAuthenticationCodeMap() {
		return authenticationCodeMap;
	}
	
	public static boolean isEncryption() {
		return encryption;
	}

	public static boolean checkAuthenticationCode(String authenticationCode){
		 String orgCode = (String)WebserviceConfig.getAuthenticationCodeMap().get(authenticationCode);
	     if(!StringUtil.isEmpty(orgCode)){
	    	/*String  code = SymmetricEncoder.AESDncode(authenticationCode);
	    	String[] codes = code.split("-");
	    	if(codes.length>1){
	    		if(codes[1].equals(orgCode)){
	    			 return true;
	    		}else{
	    			return false;
	    		}
	    	}else{
	    		return false;
	    	}*/
	    	 return true;
	     }else{
	    	 return false;
	     }
	}
   
}
