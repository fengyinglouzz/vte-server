package com.insight.wisehealth.vte.common;

import com.insight.wisehealth.vte.loginpojo.LoginUserPojo;


public class SessionUser {
	
	private static ThreadLocal<LoginUserPojo> currentUser = new ThreadLocal<LoginUserPojo>();  
      
    public static void setCurrentUser(LoginUserPojo loginUserPojo) {  
    	currentUser.set(loginUserPojo);  
    }  
      
    public static LoginUserPojo getCurrentUser(){
        return currentUser.get();  
    }  

}
